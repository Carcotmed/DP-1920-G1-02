<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="Discounts">
	<jsp:body>
        <h2>Discounts</h2>

        <form:form modelAttribute="discount" class="form-horizontal" id="discount-edit-form">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Percentage" name="percentage" />
				<petclinic:inputField label="Quantity" name="quantity" />
				<div class="form-group has-feedback">
				<label class="col-sm-2 control-label">Provider</label>
				<select class="form-control" id="provider" name="provider">
                	<c:forEach items="${providers}" var="provider">
                		<option value="${provider.id}">${provider.name}</option>
                	</c:forEach>
                </select> 
                </div>
                <div class="form-group has-feedback">
                <label class="col-sm-2 control-label">Product</label>
			    <select class="form-control" id="product" name="product">
                	<c:forEach items="${products}" var="product">
                		<option value="${product.id}">${product.name}</option>
                	</c:forEach>
                </select>
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Save Discount</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
