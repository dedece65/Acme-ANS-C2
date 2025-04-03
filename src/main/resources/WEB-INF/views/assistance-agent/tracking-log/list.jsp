<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistance-agent.tracking-log.list.label.updateMoment" path="updateMoment" width="20%"/>
	<acme:list-column code="assistance-agent.tracking-log.list.label.resolutionReason" path="resolutionReason" width="20%"/>
	<acme:list-column code="assistance-agent.tracking-log.list.label.step" path="step" width="20%"/>
	<acme:list-column code="assistance-agent.tracking-log.list.label.indicator" path="indicator" width="20%"/>
	<acme:list-column code="assistance-agent.tracking-log.list.label.resolutionPercentage" path="resolutionPercentage" width="20%"/>
	<acme:list-column code="assistance-agent.tracking-log.list.label.draftMode" path="draftMode" width="5%"/>
	<acme:list-payload path="payload"/>

</acme:list>

<acme:button code="assistance-agent.tracking-log.list.button.create" action="/assistance-agent/tracking-log/create"/>

	