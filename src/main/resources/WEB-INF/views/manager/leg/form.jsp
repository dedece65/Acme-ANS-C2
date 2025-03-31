<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="manager.form.label.flightNumber" path="flightNumber"/>
	<acme:input-textbox code="manager.form.label.scheduledDeparture" path="scheduledDeparture"/>
	<acme:input-textbox code="manager.form.label.scheduledArrival" path="scheduledArrival"/>
	<acme:input-select code="manager.form.label.status" path="legStatus" choices="${choices}"/>
	<acme:input-select code="manager.form.label.departureAirport" path="departureAirport" choices="${airportChoices}"/>
	<acme:input-select code="manager.form.label.arrivalAirport" path="arrivalAirport" choices="${airportChoices}"/>
	<acme:input-select code="manager.form.label.aircraft" path="aircraft" choices="${aircraftChoices}"/>
	<jstl:if  test="${acme:anyOf(_command,'create')}">
		<acme:submit code="manager.leg.form.button.create" action="/manager/leg/create?flightId=${flightId}"/>
	</jstl:if>
	<jstl:if test="${draftMode && acme:anyOf(_command,'show|update|publish|delete')}">
		<acme:input-double code="airline-manager.leg.form.label.duration" path="duration" readonly = "true"/>
		<acme:submit code="manager.leg.form.button.update" action="/manager/leg/update"/>	
		<acme:submit code="manager.leg.form.button.delete" action="/manager/leg/delete"/>	
		<acme:submit code="manager.leg.form.button.publish" action="/manager/leg/publish"/>	
	</jstl:if>
</acme:form>