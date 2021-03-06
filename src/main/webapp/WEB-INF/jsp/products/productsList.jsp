<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="products">
	<h2>Products</h2>

	<table id="productsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Price (EUR)</th>
				<sec:authorize access="hasAnyAuthority('admin','veterinarian')">
					<th>Quantity</th>
				</sec:authorize>
				<th>All Available</th>
				<th>Provider</th>

				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${products}" var="product">
			<c:if test="${product.enabled}">
				<tr>
					<td><c:out value="${product.name}" /></td>
					<td><c:out value="${product.price}" /></td>
					<sec:authorize access="hasAnyAuthority('admin','veterinarian')">
						<td><c:out value="${product.quantity}" /></td>
					</sec:authorize>
					<td><c:out value="${product.allAvailable}" /></td>
					<td><c:out value="${product.provider.name}" /></td>
					<td>
					<sec:authorize access="hasAuthority('admin')">
					
					
					<spring:url value="/products/edit/{productId}" var="editProductUrl">
							<spring:param name="productId" value="${product.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editProductUrl)}">Edit</a>
					</sec:authorize>
						<spring:url value="/products/delete/{productId}" var="productUrl">

							<spring:param name="productId" value="${product.id}" />
						</spring:url> <a href="${fn:escapeXml(productUrl)}">Delete</a></td>

				</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="/products/new" var="productUrl" />
	<input type=button class="btn btn-default"
		onClick="location.href='${fn:escapeXml(productUrl)}'" value='Create'>
</petclinic:layout>
