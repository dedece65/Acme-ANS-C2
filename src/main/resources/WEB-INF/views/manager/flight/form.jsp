<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.form.label.tag" path="tag"/>
	<acme:input-textbox code="manager.form.label.requiresSelfTransfer" path="requiresSelfTransfer"/>
	<acme:input-textbox code="manager.form.label.cost" path="cost"/>
	<acme:input-textbox code="manager.form.label.description" path="description"/>
	<acme:input-textbox code="manager.form.label.scheduledDeparture" path="scheduledDeparture"/>
	<acme:input-textbox code="manager.form.label.scheduledArrival" path="scheduledArrival"/>
	<acme:input-textbox code="manager.form.label.originCity" path="originCity"/>
	<acme:input-textbox code="manager.form.label.destinationCity" path="destinationCity"/>
	<acme:input-textbox code="manager.form.label.layovers" path="layovers"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command,'show|update|publish|delete') && draftMode == true && hasUserStories}">
			<acme:submit code="manager.flight.form.button.update" action="/manager/flight/update"/>	
			<acme:submit code="manager.flight.form.button.publish" action="/manager/flight/publish"/>	
			<acme:submit code="manager.flight.form.button.delete" action="/manager/flight/delete"/>	
			<acme:button code="manager.flight.form.button.legs" action="/manager/legs/list?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command,'show|update|publish|delete') && draftMode == true}">
			<acme:submit code="manager.flight.form.button.update" action="/manager/flight/update"/>	
			<acme:submit code="manager.flight.form.button.publish" action="/manager/flight/publish"/>	
			<acme:submit code="manager.flight.form.button.delete" action="/manager/flight/delete"/>
		</jstl:when>
		<jstl:when  test="${acme:anyOf(_command,'create')}">
			<acme:submit code="manager.flight.form.button.create" action="/manager/flight/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>