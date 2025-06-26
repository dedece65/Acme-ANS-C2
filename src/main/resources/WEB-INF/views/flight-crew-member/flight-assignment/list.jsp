<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="flight-crew-member.flight-assignment.list.label.duty" path="duty" width="25%"/>
	<acme:list-column code="flight-crew-member.flight-assignment.list.label.moment" path="lastUpdate" width="25%"/>
	<acme:list-column code="flight-crew-member.flight-assignment.list.label.current-status" path="status" width="25%"/>	
	<acme:list-column code="flight-crew-member.flight-assignment.list.label.draft-mode" path="draftMode" width="25%"/>	
	<acme:list-payload path="payload"/>
</acme:list>	
	
<jstl:if test="${acme:anyOf(_command, 'planned-list|completed-list')}">
	<acme:button code="flight-crew-member.flight-assignment.list.button.create" action="/flight-crew-member/flight-assignment/create"/>
</jstl:if>	