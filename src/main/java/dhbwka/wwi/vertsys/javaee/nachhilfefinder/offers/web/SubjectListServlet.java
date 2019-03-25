/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.web;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.OfferBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.SubjectBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Offer;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Subject;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.tasks.ejb.TaskBean;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author patrickguenther
 */
@WebServlet(urlPatterns = {"/app/offers/subjects/"})
public class SubjectListServlet extends HttpServlet {

    @EJB
    SubjectBean subjectBean;

    @EJB
    OfferBean offerBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Alle vorhandenen Kategorien ermitteln
        request.setAttribute("subjects", this.subjectBean.findAllSorted());

        // Anfrage an dazugerhörige JSP weiterleiten
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/offers/subject_list.jsp");
        dispatcher.forward(request, response);

        // Alte Formulardaten aus der Session entfernen
        HttpSession session = request.getSession();
        session.removeAttribute("categories_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen        
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "create":
                this.createSubject(request, response);
                break;
            case "delete":
                this.deleteSubjects(request, response);
                break;
        }
    }

    /**
     * Aufgerufen in doPost(): Neue Kategorie anlegen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void createSubject(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        String name = request.getParameter("name");

        Subject subject = new Subject(name);
        List<String> errors = this.validationBean.validate(subject);

        // Neue Kategorie anlegen
        if (errors.isEmpty()) {
            this.subjectBean.saveNew(subject);
        }

        // Browser auffordern, die Seite neuzuladen
        if (!errors.isEmpty()) {
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("subjects_form", formValues);
        }

        response.sendRedirect(request.getRequestURI());
    }

    /**
     * Aufgerufen in doPost(): Markierte Kategorien löschen
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void deleteSubjects(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Markierte Kategorie IDs auslesen
        String[] subjectIds = request.getParameterValues("subject");

        if (subjectIds == null) {
            subjectIds = new String[0];
        }

        // Kategorien löschen
        for (String subjectId : subjectIds) {
            // Zu löschende Kategorie ermitteln
            Subject subject;

            try {
                subject = this.subjectBean.findById(Long.parseLong(subjectId));
            } catch (NumberFormatException ex) {
                continue;
            }

            if (subject == null) {
                continue;
            }

            // Bei allen betroffenen Aufgaben, den Bezug zur Kategorie aufheben
            List<Offer> offers = subject.getOffers();

            if (offers != null) {
                offers.forEach((Offer offer) -> {
                    offer.setSubject(null);
                    this.offerBean.update(offer);
                });
            }

            // Und weg damit
            this.subjectBean.delete(subject);
        }

        // Browser auffordern, die Seite neuzuladen
        response.sendRedirect(request.getRequestURI());
    }

}
