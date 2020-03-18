<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="Orders">
<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#orderDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#arrivalDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
	<jsp:body>
        <h2>Orders</h2>

        <form:form modelAttribute="order" class="form-horizontal" id="edit-order-form">
            <div class="form-group has-feedback">
            
				<div class="form-group has-feedback">
				<label class="col-sm-2 control-label">Product</label>
				<select class="form-control" id="product" name="product">
                	<c:forEach items="${products}" var="product">
                		<option value="${product.id}">${product.name}</option>
                	</c:forEach>
                </select> 
                </div>				
                <petclinic:inputField label="Quantity" name="quantity" />
                <petclinic:inputField label="Order Date" name="orderDate" />
                <petclinic:inputField label="Arrival Date" name="arrivalDate" />
                <petclinic:checkboxInput label="Sent" name="sent" />
                <div class="form-group has-feedback">
				<label class="col-sm-2 control-label">Provider</label>
				<select class="form-control" id="provider" name="provider">
                	<c:forEach items="${providers}" var="provider">
                		<option value="${provider.id}">${provider.name}</option>
                	</c:forEach>
                </select> 
                </div>
                <div class="form-group has-feedback">
				<label class="col-sm-2 control-label">Discount</label>
				<select class="form-control" id="discount" name="discount">
                	<c:forEach items="${discounts}" var="discount">
                		<option value="${discount.id}">${discount.percentage}</option>
                	</c:forEach>
                </select> 
                </div>
                
					<c:out value="${createError}" />
					<c:remove var="createError" />

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Save Order</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
