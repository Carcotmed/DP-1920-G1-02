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
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${discounts}" var="discount">
				<tr>
					<td><c:out value="${discount.product.name}" /></td>
					<td><c:out value="${discount.percentage}" /></td>
					<td><c:out value="${discount.quantity}" /></td>
					<td><c:out value="${discount.provider.name}"/></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
