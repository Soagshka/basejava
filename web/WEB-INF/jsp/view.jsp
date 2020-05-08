<%@ page import="com.urise.webapp.model.ListTextSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.model.SimpleTextSection" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contactMap}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sectionMap}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.AbstractSection>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="com.urise.webapp.model.AbstractSection"/>
            <c:choose>

            <c:when test="${type == 'OBJECTIVE' || type=='PERSONAL'}">
                <c:set var="sectionValue" value="<%=((SimpleTextSection) section).getInformation()%>"/>
            <c:if test="${(sectionValue != '') && (sectionValue != null)}">
    <h2><a>${type.title}</a></h2>
    <%=((SimpleTextSection) section).getInformation()%>
    </c:if>
    </c:when>

    <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
        <c:set var="sectionValue" value='<%=((ListTextSection) section).getInformation()%>'/>
        <c:if test="${(sectionValue != '') && (sectionValue != null)}">
            <h2><a>${type.title}</a></h2>
            <%=String.join("\n", ((ListTextSection) section).getInformation())%>
        </c:if>
    </c:when>

    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
        <c:set var="organizationList" value='<%=((OrganizationSection) section).getOrganizationList()%>'/>
        <c:if test="${(organizationList != null)}">
            <h2><a>${type.title}</a></h2>
            <c:forEach var="organization" items="<%=((OrganizationSection) section).getOrganizationList()%>">
                <c:choose>
                    <c:when test="${organization.link != ''}">
                        <h3><a href="${organization.link}">${organization.title}</a></h3>
                    </c:when>
                    <c:otherwise>
                        <h3><a>${organization.title}</a></h3>
                    </c:otherwise>
                </c:choose>
                <c:forEach var="position" items="${organization.positionList}">
                    <a>${position.dateStart.toString()}
                        - ${position.dateEnd.toString()} </br> ${position.information} </br> ${position.description} </br> </a>
                </c:forEach>
            </c:forEach>
        </c:if>
    </c:when>

    </c:choose>
    </c:forEach>
    <p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>