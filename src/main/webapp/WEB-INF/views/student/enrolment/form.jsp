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
	<acme:input-textbox code="student.enrolment.form.label.code" readonly="true" path="code"/>
	<acme:input-textarea code="student.enrolment.form.label.motivation" path="motivation"/>	
	<acme:input-textarea code="student.enrolment.form.label.someGoals" path="someGoals"/>	
	<acme:input-textbox code="student.enrolment.form.label.draftMode" readonly="true" path="draftMode"/>
<jstl:if test="${draftMode.equals(false)}">
	<acme:submit code="student.enrolment.form.button.update" action="/student/enrolment/update"/>
	<acme:submit code="student.enrolment.form.button.delete" action="/student/enrolment/delete"/>
</jstl:if>
<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="any.peep.form.button.create" action="/student/enrolment/create"/>
		</jstl:when>	
	</jstl:choose>
</acme:form>

