<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.ratioResolvedClaims" path="ratioResolvedClaims" />
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.ratioRejectedClaims" path="ratioRejectedClaims"/>

    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.topThreeMonths" path="topThreeMonthsWithMostClaims"/>

    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.averageClaimLogs" path="averageClaimLogs"/>
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.deviationClaimLogs" path="deviationClaimLogs"/>
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.minClaimLogs" path="minClaimLogs"/>
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.maxClaimLogs" path="maxClaimLogs"/>

    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.averageClaimsAssisted" path="averageClaimsAssisted"/>
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.deviationClaimsAssisted" path="deviationClaimsAssisted"/>
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.minClaimsAssisted" path="minClaimsAssisted" />
    <acme:list-column code="assistance-agent.assistance-agent-dashboard.form.maxClaimsAssisted" path="maxClaimsAssisted"/>
</acme:list>
