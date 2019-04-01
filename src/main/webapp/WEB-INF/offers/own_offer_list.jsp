<%-- 
    Document   : own_offer_list
    Created on : 01.04.2019, 10:18:50
    Author     : patrickguenther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Aufgaben
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/offer_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/offers/offer/new/"/>">Aufgabe anlegen</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/offers/subjects/"/>">Kategorien bearbeiten</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <%-- Gefundene Angebote --%>
        <c:choose>
            <c:when test="${empty offers}">
                <p>
                    Es wurden keine Angebote gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.nachhilfefinder.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Titel</th>
                            <th>Fach</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <c:forEach items="${offers}" var="offer">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/offers/offerview/${offer.id}/"/>">
                                    <c:out value="${offer.title}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${offer.subject.name}"/>
                            </td>
                            <td>
                                <c:out value="${offer.status.label}"/>
                            </td>                           
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>
