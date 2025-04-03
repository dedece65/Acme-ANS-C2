<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.claim.list.label.registrationMoment" path="registrationMoment" width="5%"/>
	<acme:list-column code="administrator.claim.list.label.passengerEmail" path="passengerEmail" width="20%"/>
	<acme:list-column code="administrator.claim.list.label.description" path="description" width="40%"/>
	<acme:list-column code="administrator.claim.list.label.claimType" path="claimType" width="15%"/>
	<acme:list-column code="administrator.claim.list.label.indicator" path="indicator" width="15%"/>
	<acme:list-column code="administrator.claim.list.label.draftMode" path="draftMode" width="5%"/>
	
	<acme:list-payload path="payload"/>
</acme:list>
