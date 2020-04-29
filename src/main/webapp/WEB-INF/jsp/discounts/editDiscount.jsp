<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="Discounts">
	<jsp:body>
        <h2>Discounts</h2>

        <form:form modelAttribute="discount" class="form-horizontal"
			id="discount-edit-form">
            <div class="form-group has-feedback">
                <petclinic:inputField label="Percentage"
					name="percentage" />
				<petclinic:inputField label="Quantity" name="quantity" />
				<div class="form-group has-feedback">
				<label class="col-sm-2 control-label">Provider</label>
				<select class="form-control" id="provider" name="provider">
                	<c:forEach items="${providers}" var="providerVar">
                		<c:choose>
                			<c:when test="${providerVar.id eq discount.provider.id}">
                				<option selected value="${providerVar.id}">${providerVar.name}</option>
                			</c:when>
                			<c:otherwise>
                				<option value="${providerVar.id}">${providerVar.name}</option>
                			</c:otherwise>
                			</c:choose>
                	</c:forEach>
                </select> 
                </div>
                <div class="form-group has-feedback">
                <label class="col-sm-2 control-label">Product</label>
			    <select class="form-control" id="product" name="product">
                <c:forEach items="${products}" var="productVar">
                		<c:choose>
                			<c:when test="${productVar.id eq discount.product.id}">
                				<option selected value="${productVar.id}">${productVar.name}</option>
                			</c:when>
                			<c:otherwise>
                				<option value="${productVar.id}">${productVar.name}</option>
                			</c:otherwise>
                			</c:choose>
                	</c:forEach>
                </select>
                                 <input type="hidden" value="true"
						name="enabled" />
                
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
