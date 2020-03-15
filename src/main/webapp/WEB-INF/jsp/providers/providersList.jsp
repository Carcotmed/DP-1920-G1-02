<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="providersTable">
	<h2>Providers</h2>

	<table id="providersTable" class="table table-striped">
		<thead>
			<tr>
				<th>Provider Name</th>
				<th>Address</th>
				<th>Telephone</th>
				<th>E-Mail</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${providers}" var="provider">
				<tr>
					<td><c:out value="${provider.name}" /></td>
					<td><c:out value="${provider.address}" /></td>
					<td><c:out value="${provider.phone}" /></td>
					<td><c:out value="${provider.email}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<spring:url value="/providers/new" var="addUrl">
	</spring:url>
	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New
		Provider</a>
	
</petclinic:layout>
