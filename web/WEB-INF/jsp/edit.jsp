<%@ page import="com.urise.webapp.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type == 'OBJECTIVE' || type=='PERSONAL'}">
                    <textarea cols="60" name="${type}"
                              rows="5"><%=((SimpleTextSection) section).getInformation()%></textarea><br/>
                </c:when>

                <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                    <textarea cols="60" name="${type}"
                              rows="5"><%=String.join("\n", ((ListTextSection) section).getInformation())%></textarea><br/>
                </c:when>

                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizationList()%>"
                               varStatus="counter">
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
                        <td>
                            <a href="resume?uuid=${resume.uuid}&action=remove-org&section=${type}&name=${organization.title}">Удалить
                                организацию</a></td>
                        <br><br>
                    </c:forEach>
                    <dt>Количество Ваших позиций в новой организации :</dt>
                    <dd>
                        <input type="text" name="${type}positionCount" size=10
                               value="">
                    </dd>
                </c:when>

            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>