<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code = "administrator.airport.list.label.iataCode" path ="iataCode" width="20%"/>
	<acme:list-column code = "administrator.airport.list.label.name" path ="name" width="20%"/>
	<acme:list-column code = "administrator.airport.list.label.phoneNumber" path ="phoneNumber" width="20%"/>
</acme:list>
<acme:button code="administrator.airport.form.button.create" action="/administrator/airport/create"/>