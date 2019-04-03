<%-- 
    Document   : all_offer_list
    Created on : 25.03.2019, 09:38:21
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
    </jsp:attribute>

    <jsp:attribute name="content">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Beschreibung"/>

            <select name="search_subject">
                <option value="">Alle Kategorien</option>

                <c:forEach items="${subjects}" var="subject">
                    <option value="${subject.id}" ${param.search_subject == subject.id ? 'selected' : ''}>
                        <c:out value="${subject.name}" />
                    </option>
                </c:forEach>
            </select>

            <select name="search_status">
                <option value="">Alle Stati</option>

                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" ${param.search_status == status ? 'selected' : ''}>
                        <c:out value="${status.label}"/>
                    </option>
                </c:forEach>
            </select>

            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

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
                            <th>Eigentümer</th>
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
                                <c:out value="${offer.owner.username}"/>
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
