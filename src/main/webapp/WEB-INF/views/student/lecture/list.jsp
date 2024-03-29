<%--
- list.jsp
-
- Copyright (C) 2012-2023 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://www.the-acme-framework.org/"%>
<h1><acme:message code="student.course.lectures.title"/></h1>
<acme:list>
	<acme:list-column code="student.lecture.list.label.title" path="title"/>
	<acme:list-column code="student.lecture.list.label.resumen" path="resumen" />
	<acme:list-column code="student.lecture.list.label.lectureType" path="lectureType" />
	<acme:list-column code="student.lecture.list.label.estimatedTime" path="estimatedTime"/>
</acme:list>
