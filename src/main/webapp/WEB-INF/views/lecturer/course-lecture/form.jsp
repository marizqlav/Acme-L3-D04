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

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:input-textbox code="lecturer.courseLecture.label.course" path="courseTitle" readonly="true" />
			<acme:input-select code="lecturer.courseLecture.label.lecture" path="lecture" choices="${lectures}"/>
			<acme:submit code="lecturer.courseLecture.button.create" action="/lecturer/course-lecture/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'show'}">
			<h3><acme:message code="lecturer.course-lecture.form.label.lectureDelete"/></h3>
			<acme:input-textbox code="lecturer.course-lecture.form.label.lecture" path="lecture" readonly="true"/>
			<h3><acme:message code="lecturer.course-lecture.form.label.courseDelete"/></h3>
			<acme:input-textbox code="lecturer.course-lecture.form.label.course" path="course" readonly="true"/>
			<acme:submit code="lecturer.courseLecture.button.delete" action="/lecturer/course-lecture/delete"/>
			
		</jstl:when>
	</jstl:choose>

</acme:form>