<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:form> 
	<acme:input-moment code="assistance-agent.claim.form.label.registrationMoment" path="registrationMoment" readonly="true"/>
	<acme:input-email code="assistance-agent.claim.form.label.passengerEmail" path="passengerEmail"/>
	<acme:input-textarea code="assistance-agent.claim.form.label.description" path="description"/>
	<acme:input-select code="assistance-agent.claim.form.label.claimType" path="claimType" choices="${claimTypes}"/>
	<acme:input-select code="assistance-agent.claim.form.label.indicator" path="indicator" choices="${indicators}"/>
	<acme:input-select code="assistance-agent.claim.form.label.legs" path="legs" choices="${legs}"/>

	
	<jstl:choose>
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="assistance-agent.claim.form.button.tracking-logs" action="/assistance-agent/tracking-log/list?claimId=${id}"/>		
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="assistance-agent.claim.form.button.tracking-logs" action="/assistance-agent/tracking-log/list?claimId=${id}"/>	
			<acme:submit code="assistance-agent.claim.form.button.update" action="/assistance-agent/claim/update"/>
			<acme:submit code="assistance-agent.claim.form.button.delete" action="/assistance-agent/claim/delete"/>
			<acme:submit code="assistance-agent.claim.form.button.publish" action="/assistance-agent/claim/publish"/>
		</jstl:when>
		
		<jstl:when test="${_command == 'create'}">
				
				<acme:submit code="assistance-agent.claim.form.button.create" action="/assistance-agent/claim/create"/>
		</jstl:when>	
	</jstl:choose>

</acme:form>