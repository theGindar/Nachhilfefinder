<%-- 
    Document   : offer_edit
    Created on : Mar 9, 2019, 5:36:47 PM
    Author     : Patrick Günther
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
            <a href="<c:url value="/app/offers/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">

                <%-- Eingabefelder --%>
                <label for="offer_owner">Eigentümer:</label>
                <div class="side-by-side">
                    <input type="text" name="offer_owner" value="${offer_form.values["offer_owner"][0]}" readonly="readonly">
                </div>
                
                <label for="offer_subject">Fach:</label>
                <div class="side-by-side">
                    <select name="offer_subject">
                        <option value="">Keine Kategorie</option>

                        <c:forEach items="${subjects}" var="subject">
                            <option value="${subject.id}" ${offer_form.values["offer_subject"][0] == subject.id.toString() ? 'selected' : ''}>
                                <c:out value="${subject.name}" />
                            </option>
                        </c:forEach>
                    </select>
                    <a href="<c:url value="/app/tasks/categories/"/>"><button type="button">Neues Fach anlegen</button></a>
                </div>
                

                <label for="offer_start_date">
                    Angebot gültig ab:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="offer_start_date" value="${offer_form.values["offer_start_date"][0]}">
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

                <label for="offer_title">
                    Titel:
                    <span class="required">*</span>
                </label>
                <div class="side-by-side">
                    <input type="text" name="offer_title" value="${offer_form.values["offer_title"][0]}">
                </div>
                
                <label for="offer_description">
                    Preis:
                </label>
                <div class="side-by-side">
                    <input type="number" name="offer_price"><c:out value="${offer_form.values['offer_price'][0]}"/></input>
                </div>

                <label for="offer_description">
                    Angebotsbeschreibung:
                </label>
                <div class="side-by-side">
                    <textarea name="offer_description"><c:out value="${offer_form.values['offer_description'][0]}"/></textarea>
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Sichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty offer_form.errors}">
                <ul class="errors">
                    <c:forEach items="${offer_form.errors}" var="error">
                        <li>${error}</li>
                    </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>