<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:input-select code="customer.booking.form.label.flight" path="flight" choices="${flights}" readonly="${published}"/>
	<acme:input-textarea code="customer.booking.form.label.locatorCode" path="locatorCode" readonly="true"/>
	<acme:input-textbox code="customer.booking.form.label.purchaseMoment" path="purchaseMoment" readonly="true"/>
	<acme:input-select code="customer.booking.form.label.travelClass" path="travelClass" choices="${travelClass}" readonly="${isPublished}"/>	
	<acme:input-textarea code="customer.booking.form.label.price" path="price" readonly="true"/>
	<acme:input-textarea code="customer.booking.form.label.lastNibble" path="lastNibble" readonly="${published}"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update') && published == false}">
			<acme:submit code="customer.booking.form.button.update" action="/customer/booking/update"/>
			<acme:submit code="customer.booking.form.button.publish" action="/customer/booking/publish"/>
			<acme:submit code="customer.booking.form.button.bookingRecord" action="/customer/bookingRecord/create"/>
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="customer.booking.form.button.create" action="/customer/booking/create"/>
		</jstl:when>		
	</jstl:choose>
	
	<jstl:if test="${passengers.size() != 0 && _command != 'create'}">
		<acme:submit code="customer.booking.form.button.passenger" action="/customer/passenger/list?bookingId=${id}"/>
	</jstl:if>
</acme:form>
