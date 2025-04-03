<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-moment code="administrator.tracking-log.form.label.updateMoment" path="updateMoment" readonly="true"/>
	<acme:input-textbox code="administrator.tracking-log.form.label.step" path="step"/>
	<acme:input-double code="administrator.tracking-log.form.label.resolutionPercentage" path="resolutionPercentage"/>
	<acme:input-select code="administrator.tracking-log.form.label.indicator" path="indicator" choices="${indicators}"/>
	<acme:input-textarea code="administrator.tracking-log.form.label.resolutionReason" path="resolutionReason"/>

	<jstl:choose>		
		<jstl:when test="${acme:anyOf(_command, 'show') && draftMode == true}">	
		</jstl:when>
	</jstl:choose>

</acme:form>