<%-- 
    Document   : offer_error
    Created on : 29.03.2019, 17:31:45
    Author     : patrickguenther
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Keine Berechtigung für dieses Angebot
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="content">
        <p>
            Das hat leider nicht geklappt. 🐻
        </p>
    </jsp:attribute>
</template:base>
