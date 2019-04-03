<%-- 
    Document   : view_offer
    Created on : 25.03.2019, 18:02:05
    Author     : patrickguenther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Angebot bearbeiten
            </c:when>
            <c:otherwise>
                Angebot anlegen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/offer_edit.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>
        
        <div class="menuitem">
            <a href="<c:url value="/app/offers/list/"/>">Liste aller Angebote</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <label for="offer_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <p>${offer_form.values["offer_owner"][0]}</p>
                </div>
                
                <label for="offer_subject">Fach:</label>
                <div class="side-by-side">
                    <p>${offer_form.values["offer_subject"][0]}</p>
                </div>
                

                

                <!--<label for="task_status">
                    Status:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side margin">
                    <select name="task_status">
                        <c:forEach items="${statuses}" var="status">
                            <option value="${status}" ${offer_form.values["offer_status"][0] == status ? 'selected' : ''}>
                                <c:out value="${status.label}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>-->

                <label for="offer_title">Titel:</label>
                <div class="side-by-side">
                    <p>${offer_form.values["offer_title"][0]}</p>
                </div>
                
                <label for="offer_price">
                    Preis:
                </label>
                <div class="side-by-side">
                    <p>${offer_form.values["offer_price"][0]}</p>
                </div>

                <label for="offer_description">
                    Angebotsbeschreibung:
                </label>
                <div class="side-by-side">
                    <p>${offer_form.values["offer_description"][0]}</p>
                </div>

    </jsp:attribute>
</template:base>