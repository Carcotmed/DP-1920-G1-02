<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="orders">
	<h2>Orders</h2>

	<table id="orders" class="table table-striped">
		<thead>
			<tr>
				<th>Product</th>
				<th style="width: 200px;">Quantity</th>
				<th style="width: 150px;">Order Date</th>
				<th>Arrival Date</th>
				<th>Sent</th>
				<th>Provider</th>
				<th>Discount</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${orders}" var="order">
				<tr>
					<td><c:out value="${order.product.name}" /></td>
					<td><c:out value="${order.quantity}" /></td>
					<td><c:out value="${order.orderDate}" /></td>
					<td><c:out value="${order.arrivalDate}" /></td>
					<td><c:out value="${order.sent}" /></td>
					<td><c:out value="${order.provider.name}" /></td>
					<td><c:out value="${order.discount.percentage}" /></td>
					<td>
            
            <c:if test="${empty order.arrivalDate}">
							<spring:url value="/orders/edit/{orderId}" var="editUrl">
								<spring:param name="orderId" value="${order.id}" />
							</spring:url>
              
							<a href="${fn:escapeXml(editUrl)}">Edit</a>
              
						</c:if>
            
            <spring:url value="/orders/delete/{orderId}" var="deleteUrl">
							<spring:param name="orderId" value="${order.id}" />
            </spring:url>
            <a href="${fn:escapeXml(deleteUrl)}">Delete</a>
        </c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<spring:url value="/orders/new" var="newOrderUrl" />
	<input type=button class="btn btn-default"
		onClick="location.href='${fn:escapeXml(newOrderUrl)}'" value='Create'>
</petclinic:layout>
