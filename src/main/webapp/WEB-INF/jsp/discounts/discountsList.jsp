<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="discounts">
	<h2>Discounts</h2>

	<table id="discountsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 200px;">Product</th>
				<th style="width: 150px;">Percentage</th>
				<th>Quantity</th>
				<th>Provider</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${discounts}" var="discount">
			<c:if test="${discount.enabled}">
				<tr>
					<td><c:out value="${discount.product.name}" /></td>
					<td><c:out value="${discount.percentage}" /></td>
					<td><c:out value="${discount.quantity}" /></td>
					<td><c:out value="${discount.provider.name}"/></td>
					<td>

						<spring:url value="/discounts/edit/{discountId}" var="editDiscountUrl">
							<spring:param name="discountId" value="${discount.id}" />
						</spring:url>
						<a href="${fn:escapeXml(editDiscountUrl)}">Edit</a>

						<spring:url	value="/discounts/delete/{discountId}" var="deleteUrl">
							<spring:param name="discountId" value="${discount.id}" />
						</spring:url>
						<a href="${fn:escapeXml(deleteUrl)}">Delete</a>

					</td>
				</tr>
			</c:if>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="/discounts/new" var="newDiscountUrl" />
	<input type=button class="btn btn-default" onClick="location.href='${fn:escapeXml(newDiscountUrl)}'" value='Create'>
</petclinic:layout>
