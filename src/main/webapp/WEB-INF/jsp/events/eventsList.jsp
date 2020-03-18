<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="events">
    <h2>Owners</h2>


    <table id="eventsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 130px;">Date</th>
            <th style="width: 250px;">Place</th>
            <th style="width: 100px">Capacity</th>
            <th>Description</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${events}" var="event">
	        <tr>
	            <td>
	                <c:out value="${event.date}"/>
	            </td>
	            <td>
	                <c:out value="${event.place}"/>
	            </td>
	            <td>
	                <c:out value="${event.capacity}"/>
	            </td>
	            <td>
	                <c:out value="${event.description}"/>
	            </td>
	            <td>
		                <spring:url value="/events/{eventId}" var="eventUrl">
		             		<spring:param name="eventId" value="${event.id}"/>
			         	</spring:url>
			          	<a href="${fn:escapeXml(eventUrl)}">Details</a>
	            </td>
	        </tr>
        </c:forEach>
        </tbody>
    </table>
    <sec:authorize access="hasAuthority('veterinarian')">
		<a class="btn btn-default" href='<spring:url value="/events/new" htmlEscape="true"/>'>Add Event</a>
	</sec:authorize>
</petclinic:layout>
