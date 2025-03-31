<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="customer.booking.list.label.locatorCode" path="locatorCode" width="40%"/>
	<acme:list-column code="customer.booking.list.label.travelClass" path="travelClass" width="30%"/>
	<acme:list-column code="customer.booking.list.label.price" path="price" width="30%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<jstl:if test="${_command == 'list'}">
	<acme:button code="customer.booking.list.button.create" action="/customer/booking/create"/>
</jstl:if>	