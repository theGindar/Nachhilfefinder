<%-- 
    Document   : change_userdata
    Created on : 05.04.2019, 21:36:57
    Author     : patrickguenther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Benutzerdaten ändern
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/change_userdata.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="user_username">Benutzername:</label>
                <div class="side-by-side">
                    <input type="text" name="user_username" value="${user_form.values["user_username"][0]}" readonly="readonly">
                </div>
                
                <label for="user_firstname">Vorname:</label>
                <div class="side-by-side">
                    <input type="text" name="user_firstname" value="${user_form.values["user_firstname"][0]}">
                </div>
                
                <label for="user_lastname">Nachname:</label>
                <div class="side-by-side">
                    <input type="text" name="user_lastname" value="${user_form.values["user_lastname"][0]}">
                </div>
                
                <label for="user_oldpassword">altes Passwort:</label>
                <div class="side-by-side">
                    <input type="password" name="user_oldpassword">
                </div>
                
                <label for="user_newpassword">neues Passwort:</label>
                <div class="side-by-side">
                    <input type="password" name="user_newpassword">
                </div>
                
                <button class="icon-pencil" type="submit" name="action" value="save">
                        Änderungen speichern
                </button>
                
                

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty user_form.errors}">
                <ul class="errors">
                    <c:forEach items="${user_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>
