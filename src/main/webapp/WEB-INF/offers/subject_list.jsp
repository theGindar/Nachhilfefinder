<%-- 
    Document   : subject_list
    Created on : 25.03.2019, 11:00:06
    Author     : patrickguenther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Fächer bearbeiten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/subject_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/offers/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <%-- CSRF-Token --%>
            <input type="hidden" name="csrf_token" value="${csrf_token}">

            <%-- Feld zum Anlegen eines neuen Fachs --%>
            <div class="column margin">
                <label for="j_username">Neues Fach:</label>
                <input type="text" name="name" value="${categories_form.values["name"][0]}">

                <button type="submit" name="action" value="create" class="icon-pencil">
                    Anlegen
                </button>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty subjects_form.errors}">
                <ul class="errors margin">
                    <c:forEach items="${subjects_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>

            <%-- Vorhandene Fächer --%>
            <c:choose>
                <c:when test="${empty subjects}">
                    <p>
                        Es sind noch keine Fächer vorhanden. 🐏
                    </p>
                </c:when>
                <c:otherwise>
                    <div>
                        <div class="margin">
                            <c:forEach items="${subjects}" var="subject">
                                <input type="checkbox" name="subject" id="${'subject-'.concat(subject.id)}" value="${subject.id}" />
                                <label for="${'subject-'.concat(subject.id)}">
                                    <c:out value="${subject.name}"/>
                                </label>
                                <br />
                            </c:forEach>
                        </div>

                        <button type="submit" name="action" value="delete" class="icon-trash">
                            Markierte löschen
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </jsp:attribute>
</template:base>
