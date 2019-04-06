/*
 * Copyright © 2019 Jan Leyendecker, Kevin Hartmann, Patrick Günther
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.nachhilfefinder.offers.web;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.UserBean;
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
@WebServlet(urlPatterns = {"/app/offers/ownlist/"})
public class OwnOfferListServlet extends HttpServlet {

    @EJB
    private SubjectBean subjectBean;
    
    @EJB
    private OfferBean offerBean;
    
    @EJB
    private UserBean userBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Offer> offers = this.offerBean.findByUsername(this.userBean.getCurrentUser().getUsername());
        request.setAttribute("offers", offers);

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/offers/own_offer_list.jsp").forward(request, response);
    }
}



