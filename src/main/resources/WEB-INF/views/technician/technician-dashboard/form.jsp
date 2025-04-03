<%--
- form.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    
    <acme:input-select code = "technician.dashboard.form.label.maintenanceStatus" path = "maintenanceStatus" choices="${maintenanceStatus}"/>
    <acme:input-moment code = "technician.dashboard.form.label.nearestInspectionDue" path = "nearestInspectionMaintenanceRecord"/>
    <acme:input-money code = "technician.dashboard.form.label.averageEstimatedCost" path = "averageEstimatedCost"/>
    <acme:input-money code = "technician.dashboard.form.label.deviationEstimatedCost" path = "deviationEstimatedCost"/>
    <acme:input-money code = "technician.dashboard.form.label.minEstimatedCost" path = "minEstimatedCost"/>
    <acme:input-money code = "technician.dashboard.form.label.maxEstimatedCost" path = "maxEstimatedCost"/>
    <acme:input-money code = "technician.dashboard.form.label.averageEstimatedDuration" path = "averageEstimatedDuration"/>
    <acme:input-money code = "technician.dashboard.form.label.deviationEstimatedDuration" path = "deviationEstimatedDuration"/>
    <acme:input-money code = "technician.dashboard.form.label.minEstimatedDuration" path = "minEstimatedDuration"/>
    <acme:input-money code = "technician.dashboard.form.label.maxEstimatedDuration" path = "maxEstimatedDuration"/>
    <acme:input-textbox code = "technician.dashboard.form.label.topFiveAircrafts" path = "topFiveAircrafts"/>

</acme:form>
