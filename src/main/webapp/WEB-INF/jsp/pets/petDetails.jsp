<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="pets">

	<h2>Pet Information</h2>

	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Birth Date</th>
			<th>Pet Type</th>
			<th>Owner Name</th>


		</tr>

		<tr>
			<td><b><c:out value="${pet.name}" /></b></td>
			<td><petclinic:localDate date="${pet.birthDate}"
					pattern="dd-mm-yyyy" /></td>
			<td><b><c:out value="${pet.type.name}" /></b></td>
			<td><b><c:out
						value="${pet.owner.firstName} ${pet.owner.lastName}" /></b></td>
		</tr>


	</table>

	<spring:url value="{petId}/edit" var="editUrl">
		<spring:param name="petId" value="${pet.id}" />
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Pet</a>



	<br />
	<br />
	<br />
	<h2>Visits</h2>


	<table class="table table-striped">
		<thead>
			<tr>
				<th>Visit Date</th>
				<th>Description</th>
				<th>Intervention</th>
				<th>Vet</th>

			</tr>
		</thead>
		<c:forEach var="visit" items="${pet.visits}">
			<tr>
				<td><petclinic:localDate date="${visit.date}"
						pattern="yyyy-MM-dd" /></td>
				<td><c:out value="${visit.description}" /></td>

				<c:if test="${not empty visit.intervention}">

					<td><c:out value="${visit.intervention.name}" /></td>
					<td><c:out
							value="${visit.intervention.vet.firstName} ${visit.intervention.vet.lastName}" /></td>


				</c:if>

				<c:if test="${empty visit.intervention}">

					<td><spring:url
							value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new"
							var="visitUrl">
							<spring:param name="ownerId" value="${owner.id}" />
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(visitUrl)}">Add Intervention</a></td>

					<td><c:out value="No vet" /></td>

				</c:if>

			</tr>

		</c:forEach>
	</table>
	<spring:url value="/owners/{ownerId}/pets/{petId}/visits/new"
		var="visitUrl">
		<spring:param name="ownerId" value="${owner.id}" />
		<spring:param name="petId" value="${pet.id}" />
	</spring:url>
	<a href="${fn:escapeXml(visitUrl)}" class="btn btn-default">Add
		Visit</a>


</petclinic:layout>
