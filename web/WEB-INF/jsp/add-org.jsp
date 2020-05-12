<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="uuid" type="java.lang.String" scope="request"/>
    <jsp:useBean id="organization" type="com.urise.webapp.model.Organization" scope="request"/>
    <title>Новая организация</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${uuid}">

        <h3>
            <dt>${organization.title}</dt>
        </h3>
        <dl>
            <dt>Имя организации :</dt>
            <dd><input type="text" name="${type}" size=50 value="${organization.title}"></dd>
        </dl>
        <dl>
            <dt>Сайт организации :</dt>
            <dd><input type="text" name="${type}link" size=50 value="${organization.link}"></dd>
        </dl>
        <c:forEach var="position" items="${organization.positionList}">
            <jsp:useBean id="position" type="com.urise.webapp.model.Position"/>
            <dl>
                <dt>Дата начала работы :</dt>
                <dd>
                    <input type="text" name="${type}${counter.index}startDate" size=10
                           value="<%=position.getDateStart().toString()%>" placeholder="MM/yyyy">
                </dd>
            </dl>
            <dl>
                <dt>Дата увольнения :</dt>
                <dd>
                    <input type="text" name="${type}${counter.index}endDate" size=10
                           value="<%=position.getDateEnd().toString()%>" placeholder="MM/yyyy">
            </dl>
            <dl>
                <dt>Должность:</dt>
                <dd><input type="text" name='${type}${counter.index}information' size=75
                           value="${position.information}">
            </dl>
            <dl>
                <dt>Описание:</dt>
                <dd><textarea name="${type}${counter.index}description" rows=5
                              cols=75>${position.description}</textarea></dd>
            </dl>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>