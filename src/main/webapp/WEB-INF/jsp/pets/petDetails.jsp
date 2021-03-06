<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="pets">

	<h2>Pet Information</h2>

	<c:out value="${error}"></c:out>

	<c:if test="${not empty pet.imageURL}">

		<img src="${pet.imageURL}" height="200" width="200">

	</c:if>
	<c:if test="${empty pet.imageURL}">
		<spring:url value="/owners/{ownerId}/pets/{petId}/images/uploadImage"
			var="addImageURL">
			<spring:param name="ownerId" value="${owner.id}" />
			<spring:param name="petId" value="${pet.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addImageURL)}" class="btn btn-default">Add
			Image</a>
	</c:if>

	<c:if test="${not empty pet.imageDeleteHash}">
		<spring:url value="/owners/{ownerId}/pets/{petId}/images/deleteImage"
			var="deleteImageURL">
			<spring:param name="ownerId" value="${owner.id}" />
			<spring:param name="petId" value="${pet.id}" />
		</spring:url>
		<a href="${fn:escapeXml(deleteImageURL)}" class="btn btn-default">Delete
			Image</a>
	</c:if>

	<table class="table table-striped">
		<tr>
			<th>Name</th>
			<th>Birth Date</th>
			<th>Pet Type</th>
			<th>Owner Name</th>
			<th></th>


		</tr>

		<tr>
			<td><b><c:out value="${pet.name}" /></b></td>
			<td><petclinic:localDate date="${pet.birthDate}"
					pattern="dd-mm-yyyy" /></td>
			<td><b><c:out value="${pet.type.name}" /></b></td>
			<td><b><c:out
						value="${pet.owner.firstName} ${pet.owner.lastName}" /></b></td>
			<c:if test="${isAdoptable}">
				<sec:authorize access="hasAuthority('owner')">
					<td><spring:url value="/adoptions/new/{petId}"
							var="adoptionUrl">
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(adoptionUrl)}" class="btn btn-default">Adopt</a>
					</td>
				</sec:authorize>
			</c:if>
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


	<table id="visitInterventionsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Visit Date</th>
				<th>Visit Description</th>
				<th>Bringer</th>
				<th>Intervention</th>
				<th>Vet</th>
				<th>Actions</th>

			</tr>
		</thead>
		<c:forEach var="vi" items="${visitInterventions}">
			<tr>
				<td><petclinic:localDate date="${vi.visitDate}"
						pattern="yyyy-MM-dd" /></td>
				<td><c:out value="${vi.visitDescription}" /></td>
				<td><c:out value="${vi.visitBringer}" /></td>

				<c:if test="${not empty vi.interventionName}">

					<td><c:out value="${vi.interventionName}" /></td>
					<td><c:out
							value="${vi.interventionFirstName} ${vi.interventionLastName}" /></td>

					<td><spring:url
							value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit"
							var="editUrl">
							<spring:param name="interventionId"
								value="${vi.interventionId}" />
							<spring:param name="ownerId" value="${owner.id}" />
							<spring:param name="visitId" value="${vi.visitId}" />
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(editUrl)}">Edit</a> <spring:url
							value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/delete"
							var="deleteUrl">
							<spring:param name="interventionId"
								value="${vi.interventionId}" />
							<spring:param name="ownerId" value="${owner.id}" />
							<spring:param name="visitId" value="${vi.visitId}" />
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(deleteUrl)}">Delete</a></td>

				</c:if>

				<c:if test="${empty vi.interventionName}">

					<td><c:out value="No intervention" /></td>

					<td><c:out value="No vet" /></td>

					<td><spring:url
							value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new"
							var="newInterventionUrl">
							<spring:param name="ownerId" value="${owner.id}" />
							<spring:param name="visitId" value="${vi.visitId}" />
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(newInterventionUrl)}">Add
							Intervention</a></td>

				</c:if>

			</tr>

		</c:forEach>
	</table>
	
	<!-- Pre-Projection -->
	<!-- 
	<table id="interventionsTable" class="table table-striped">
		<thead>
			<tr>
				<th>Visit Date</th>
				<th>Visit Description</th>
				<th>Bringer</th>
				<th>Intervention</th>
				<th>Vet</th>
				<th>Actions</th>

			</tr>
		</thead>
		<c:forEach var="visit" items="${pet.visits}">
			<tr>
				<td><petclinic:localDate date="${visit.date}"
						pattern="yyyy-MM-dd" /></td>
				<td><c:out value="${visit.description}" /></td>
				<td><c:out value="${visit.bringer}" /></td>

				<c:if test="${not empty visit.intervention}">

					<td><c:out value="${visit.intervention.name}" /></td>
					<td><c:out
							value="${visit.intervention.vet.firstName} ${visit.intervention.vet.lastName}" /></td>

					<td><spring:url
							value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/edit"
							var="editUrl">
							<spring:param name="interventionId"
								value="${visit.intervention.id}" />
							<spring:param name="ownerId" value="${owner.id}" />
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(editUrl)}">Edit</a> <spring:url
							value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/{interventionId}/delete"
							var="deleteUrl">
							<spring:param name="interventionId"
								value="${visit.intervention.id}" />
							<spring:param name="ownerId" value="${owner.id}" />
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(deleteUrl)}">Delete</a></td>

				</c:if>

				<c:if test="${empty visit.intervention}">

					<td><c:out value="No intervention" /></td>

					<td><c:out value="No vet" /></td>

					<td><spring:url
							value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/interventions/new"
							var="newInterventionUrl">
							<spring:param name="ownerId" value="${owner.id}" />
							<spring:param name="visitId" value="${visit.id}" />
							<spring:param name="petId" value="${pet.id}" />
						</spring:url> <a href="${fn:escapeXml(newInterventionUrl)}">Add
							Intervention</a></td>

				</c:if>

			</tr>

		</c:forEach>
	</table>
	 -->
	
	
	
	
	<spring:url value="/owners/{ownerId}/pets/{petId}/visits/new"
		var="visitUrl">
		<spring:param name="ownerId" value="${owner.id}" />
		<spring:param name="petId" value="${pet.id}" />
	</spring:url>
	<a href="${fn:escapeXml(visitUrl)}" class="btn btn-default">Add
		Visit</a>



	<spring:url
		value="/owners/{ownerId}/pets/{petId}/visits/addUrgentVisit"
		var="visitUrgentUrl">
		<spring:param name="ownerId" value="${owner.id}" />
		<spring:param name="petId" value="${pet.id}" />
	</spring:url>
	<a href="${fn:escapeXml(visitUrgentUrl)}" class="btn btn-default">Urgent
		Visit</a>
</petclinic:layout>
