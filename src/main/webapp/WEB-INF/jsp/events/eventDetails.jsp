<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

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

</petclinic:layout>
