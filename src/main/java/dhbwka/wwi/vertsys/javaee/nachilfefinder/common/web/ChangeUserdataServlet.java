/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.nachilfefinder.common.web;

import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.ejb.ValidationBean;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.jpa.User;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.web.FormValues;
import dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.web.WebUtils;
import java.io.IOException;
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
@WebServlet(urlPatterns = "/app/user/*")
public class ChangeUserdataServlet extends HttpServlet {

    @EJB
    UserBean userBean;
    @EJB
    ValidationBean validationBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            request.setAttribute("user_form", createUserForm(this.userBean.getCurrentUser()));
            // Anfrage an die JSP weiterleiten
            request.getRequestDispatcher("/WEB-INF/user/change_userdata.jsp").forward(request, response);
        
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        saveUser(request, response);
    }
    
    private void saveUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String firstname = request.getParameter("user_firstname");
        String lastname = request.getParameter("user_lastname");
        String oldpassword = request.getParameter("user_oldpassword");
        String newpassword = request.getParameter("user_newpassword");

        User user = this.userBean.getCurrentUser();
        
        try {
            this.userBean.changePassword(user, oldpassword, newpassword);
            System.out.println("altes passwort: " + oldpassword + " neues passwort: " + newpassword );
        } catch (UserBean.InvalidCredentialsException ex) {
            errors.add("Fehler bei Passwort"); 
        }

        user.setFirstName(firstname);
        user.setLastName(lastname);

        this.validationBean.validate(user, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.userBean.update(user);
        }
        

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/dashboard/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("user_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
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
    private FormValues createUserForm(User user) {
        Map<String, String[]> values = new HashMap<>();

        values.put("user_username", new String[]{
            user.getUsername()
        });
        
        values.put("user_firstname", new String[]{
            user.getFirstName()
        });
        values.put("user_lastname", new String[]{
            user.getLastName()
        });
        values.put("user_username", new String[]{
            user.getUsername()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}

