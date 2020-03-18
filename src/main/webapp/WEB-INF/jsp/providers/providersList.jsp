<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="providersTable">
	<h2>Providers</h2>

	<c:out value="${deleteError}" />
	<c:remove var="deleteError" />

	<table id="providersTable" class="table table-striped">
		<thead>
			<tr>
				<th>Provider Name</th>
				<th>Address</th>
				<th>Telephone</th>
				<th>E-Mail</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${providers}" var="provider">
				<tr>
					<td><c:out value="${provider.name}" /></td>
					<td><c:out value="${provider.address}" /></td>
					<td><c:out value="${provider.phone}" /></td>
					<td><c:out value="${provider.email}" /></td>

					<td>
						<spring:url value="/providers/{providerId}/edit" var="editUrl">
							<spring:param name="providerId" value="${provider.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editUrl)}">Edit</a>
						<spring:url	value="/providers/{providerId}/delete" var="deleteUrl">
							<spring:param name="providerId" value="${provider.id}" />
						</spring:url>
						<a href="${fn:escapeXml(deleteUrl)}">Delete</a>
					</td>
				</tr>

			</c:forEach>
		</tbody>
	</table>

	<spring:url value="/providers/new" var="addUrl" />
	<spring:url value="/discounts" var="discountsUrl" />
	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New Provider</a>
	<a href="${fn:escapeXml(discountsUrl)}" class="btn btn-default">Discounts</a>
	

</petclinic:layout>
