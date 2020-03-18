<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="events">

	<h2>Event Information</h2>


	<table class="table table-striped">
		<tr>
			<th>Date</th>
			<td><c:out value="${event.date}" /></td>
		</tr>
		<tr>
			<th>Description</th>
			<td><c:out value="${event.description}" /></td>
		</tr>
		<tr>
			<th>Published</th>
			<td><c:out value="${event.published}" /></td>
		</tr>
		<tr>
			<th>Place</th>
			<td><c:out value="${event.place}" /></td>
		</tr>
		<tr>
			<th>Reserved</th>
			<td><c:out value="${reserved} / ${event.capacity}" /></td>
		</tr>
	</table>
	<c:if test="${!event.published}">
		<spring:url value="/events/edit/{eventId}"
			var="eventUrl">
			<spring:param name="eventId" value="${event.id}" />
		</spring:url>
		<a href="${fn:escapeXml(eventUrl)}" class="btn btn-default">Edit
			Event</a>
	</c:if>
	<c:if test="${!event.published}">
		<spring:url value="/events/publish/{eventId}"
			var="eventUrl">
			<spring:param name="eventId" value="${event.id}" />
		</spring:url>
		<a href="${fn:escapeXml(eventUrl)}" class="btn btn-default">Publish Event</a>
	</c:if>
	<c:if test="${!event.published}">
		<spring:url value="/events/delete/{eventId}"
			var="eventUrl">
			<spring:param name="eventId" value="${event.id}" />
		</spring:url>
		<a href="${fn:escapeXml(eventUrl)}" class="btn btn-default">Delete Event</a>
	</c:if>
	<sec:authorize access="hasAuthority('owner')">
		<c:if test="${event.published}">
			<spring:url value="/events/newParticipation/{eventId}"
				var="participationUrl">
				<spring:param name="eventId" value="${event.id}" />
			</spring:url>
			<a href="${fn:escapeXml(participationUrl)}" class="btn btn-default">Participate in the Event</a>
		</c:if>
	</sec:authorize>
	<br>
	<c:out value="${error}"></c:out>
</petclinic:layout>
