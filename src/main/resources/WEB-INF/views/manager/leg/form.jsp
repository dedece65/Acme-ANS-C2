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
		<jstl:when test="${acme:anyOf(_command,'show|update|publish|delete') && draftMode == true}">
			<acme:submit code="manager.leg.form.button.update" action="/manager/leg/update"/>	
			<acme:submit code="manager.leg.form.button.publish" action="/manager/leg/publish"/>	
			<acme:submit code="manager.leg.form.button.delete" action="/manager/leg/delete"/>	
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command,'show|update|publish|delete')}">
			<acme:submit code="manager.leg.form.button.update" action="/manager/leg/update"/>	
			<acme:submit code="manager.leg.form.button.publish" action="/manager/leg/publish"/>	
			<acme:submit code="manager.leg.form.button.delete" action="/manager/leg/delete"/>
		</jstl:when>
		<jstl:when  test="${acme:anyOf(_command,'create')}">
			<acme:submit code="manager.leg.form.button.create" action="/manager/leg/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>