<%--
- form.jsp
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


<acme:form>
<h1><acme:message code="student.lecture.data"/></h1>
	<acme:input-textbox code="student.lecture.form.label.title" readonly="true" path="title"/>	
	<acme:input-textarea code="student.lecture.form.label.resumen" readonly="true" path="resumen"/>
	<acme:input-textbox code="student.lecture.form.lecture.type" readonly="true" path="lectureType"/>	
	<acme:input-double code="student.lecture.form.estimated.time" readonly="true" path="estimatedTime"/>
	<jstl:if test="${!body.isEmpty()}">
		<acme:input-textarea code="student.lecture.form.label.body" readonly="true" path="body"/>
	</jstl:if>

	</acme:form>
	
