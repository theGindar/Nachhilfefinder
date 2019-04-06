/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.web;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.OfferBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.ejb.SubjectBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Offer;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.OfferStatus;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.jpa.Subject;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author patrickguenther
 */
@WebServlet(urlPatterns = {"/app/offers/list/"})
public class OfferListServlet extends HttpServlet {

    @EJB
    private SubjectBean subjectBean;
    
    @EJB
    private OfferBean offerBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verfügbare Fächer und Stati für die Suchfelder ermitteln
        request.setAttribute("subjects", this.subjectBean.findAllSorted());
        request.setAttribute("statuses", OfferStatus.values());

        // Suchparameter aus der URL auslesen
        String searchText = request.getParameter("search_text");
        String searchSubject = request.getParameter("search_subject");
        String searchStatus = request.getParameter("search_status");

        // Anzuzeigende Angebote suchen
        Subject subject = null;
        OfferStatus status = null;

        if (searchSubject != null) {
            try {
                subject = this.subjectBean.findById(Long.parseLong(searchSubject));
            } catch (NumberFormatException ex) {
                subject = null;
            }
        }

        if (searchStatus != null) {
            try {
                status = OfferStatus.valueOf(searchStatus);
            } catch (IllegalArgumentException ex) {
                status = null;
            }

        }

        List<Offer> offers = this.offerBean.search(searchText, subject, status);
        request.setAttribute("offers", offers);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/offers/all_offer_list.jsp").forward(request, response);
    }
}

