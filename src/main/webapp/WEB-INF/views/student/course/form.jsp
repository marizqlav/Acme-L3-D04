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
	<h1><acme:message code="student.course.data"/></h1>
	<acme:input-textbox code="student.course.form.label.title" readonly="true" path="title"/>	
	<acme:input-textarea code="student.course.form.label.resumen" readonly="true" path="resumen"/>
	<acme:input-money code="student.course.form.retail.price" readonly="true" path="retailPrice"/>
	<acme:input-money code="student.course.form.label.retail.price.exchange.money" readonly="true" path="exchangeMoney"/>
	<jstl:if test="${link != null}">
		<acme:input-url code="student.course.form.label.link" readonly="true" path="link"/>
	</jstl:if>
	<h1><acme:message code="student.course.lecturer.data"/></h1>
	<acme:input-textbox code="student.course.form.label.lecturer.username" readonly="true" path="lecturerusername"/>
	<acme:input-textarea code="student.course.form.label.lecturer.alma.mater" readonly="true" path="lectureralmamater"/>
	<acme:input-textarea code="student.course.form.label.lecturer.resume" readonly="true" path="lecturerresume"/>
	<acme:input-textarea code="student.course.form.label.lecturer.qualifications" readonly="true" path="lecturerqualifications"/>
	<jstl:if test="${lecturerlink != null}">
		<acme:input-url code="student.course.form.label.lecturer.link" readonly="true" path="lecturerlink"/>
	</jstl:if>
	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<h1><acme:message code="student.course.lectures.data"/></h1>
			<acme:button code="student.course.lectures" action="/student/lecture/list?masterId=${id}"/>
			<br>
			<br>
			<jstl:if test="${enrolmented == false}">
			<acme:button code="student.enrolment.form.button.enrolment" action="/student/enrolment/create?masterId=${id}"/>
			</jstl:if>
			<hr/>
		</jstl:when>	
	</jstl:choose>
	</acme:form>
	
