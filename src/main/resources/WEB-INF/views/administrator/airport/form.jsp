<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="administrator.form.label.name" path="name"/>
	<acme:input-textbox code="administrator.form.label.iataCode" path="iataCode"/>
	<acme:input-select code="administrator.form.label.operationalScope" path="operationalScope" choices="${operationalScopes}"/>
	<acme:input-textbox code="administrator.form.label.city" path="city"/>
	<acme:input-textbox code="administrator.form.label.country" path="country"/>
	<acme:input-url code="administrator.form.label.website" path="website"/>
	<acme:input-textbox code="administrator.form.label.email" path="email"/>
	<acme:input-textbox code="administrator.form.label.address" path="address"/>
	<acme:input-textbox code="administrator.form.label.phoneNumber" path="phoneNumber"/>
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command,'show|update')}">
			<acme:submit code="administrator.airport.form.button.update" action="/administrator/airport/update"/>	
		</jstl:when>
		<jstl:when  test="${acme:anyOf(_command,'create')}">
			<acme:submit code="administrator.airport.form.button.create" action="/administrator/airport/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>