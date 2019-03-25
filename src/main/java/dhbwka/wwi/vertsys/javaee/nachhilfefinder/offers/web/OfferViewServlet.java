/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.web;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.OfferBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.SubjectBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Offer;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.OfferStatus;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
@WebServlet(urlPatterns = "/app/offers/offerview/*")
public class OfferViewServlet extends HttpServlet {

    @EJB
    OfferBean offerBean;

    @EJB
    UserBean userBean;
    
    @EJB
    SubjectBean subjectBean;

    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.setAttribute("subjects", this.subjectBean.findAllSorted());
        // Zu bearbeitendes Angebot einlesen
        HttpSession session = request.getSession();

        Offer offer = this.getRequestedOffer(request, response);
                                
        if (session.getAttribute("offer_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("offer_form", this.createOfferForm(offer));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/offers/view_offer.jsp").forward(request, response);
        
        session.removeAttribute("offer_form");
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }else if(action == "apply"){
            this.applyForOffer(request, response);
        }
    }

    /**
     * Aufgerufen in doPost(): Neue oder vorhandene Ausschreibung speichern
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void applyForOffer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String offerStatus = "OPEN";

        Offer offer = this.getRequestedOffer(request, response);
        offer.setStatus(OfferStatus.valueOf(offerStatus));
        
        this.validationBean.validate(offer, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.offerBean.update(offer);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/offers/list/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("offer_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    

    /**
     * Zu bearbeitende Ausschreibung aus der URL ermitteln und zurückgeben. Gibt
     * entweder einen vorhandenen Datensatz oder ein neues, leeres Objekt
     * zurück.
     *
     * @param request HTTP-Anfrage
     * @return Ausschreibung
     */
    private Offer getRequestedOffer(HttpServletRequest request, HttpServletResponse response) {
        Offer offer = new Offer();
        //offer.setOwner(this.userBean.getCurrentUser());
        //offer.setStartDate(new Date(System.currentTimeMillis()));

        // ID aus der URL herausschneiden
        String offerId = request.getPathInfo();

        if (offerId == null) {
            try {
                response.sendRedirect("/app/offers/offer/");
            } catch (IOException ex) {
                Logger.getLogger(OfferViewServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        offerId = offerId.substring(1);

        if (offerId.endsWith("/")) {
            offerId = offerId.substring(0, offerId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            offer = this.offerBean.findById(Long.parseLong(offerId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return offer;
    }

    /**
     * Neues FormValues-Objekt erzeugen und mit den Daten eines aus der
     * Datenbank eingelesenen Datensatzes füllen. Dadurch müssen in der JSP
     * keine hässlichen Fallunterscheidungen gemacht werden, ob die Werte im
     * Formular aus der Entity oder aus einer vorherigen Formulareingabe
     * stammen.
     *
     * @param offer Die zu bearbeitende Ausschreibung
     * @return Neues, gefülltes FormValues-Objekt
     */
    private FormValues createOfferForm(Offer offer) {
        Map<String, String[]> values = new HashMap<>();

        values.put("offer_owner", new String[]{
            offer.getOwner().getUsername()
        });
        
        if (offer.getSubject() != null) {
            values.put("offer_subject", new String[]{
                "" + offer.getSubject().getId()
            });
        }

        values.put("offer_start_date", new String[]{
            WebUtils.formatDate(offer.getStartDate())
        });

        values.put("offer_title", new String[]{
            offer.getTitle()
        });

        values.put("offer_description", new String[]{
            offer.getDescription()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}


