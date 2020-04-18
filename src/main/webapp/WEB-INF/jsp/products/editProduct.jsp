<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	<petclinic:layout pageName="Products">
	<jsp:body>
        <h2>Product</h2>

        <form:form modelAttribute="product" class="form-horizontal"
			id="product-edit-form">
            <div class="form-group has-feedback">
				<petclinic:inputField label="Name" name="name" />
                <petclinic:inputField label="Price" name="price" />
                <c:choose>
               		 <c:when test="${not empty product.quantity}"> <!-- comprueba que se esta editando el producto y no creandolo -->
                		<sec:authorize access="hasAuthority('admin')">
            	    <petclinic:inputField label="Quantity" name="quantity" />
               			 </sec:authorize>
               		 </c:when>
               		 <c:otherwise>   <!-- Si se esta creando, entonces ambos pueden rellenar el atributo-->
                <petclinic:inputField label="Quantity" name="quantity" />
                
             		   </c:otherwise>
                </c:choose>
                
                
                <petclinic:checkboxInput label="All Available"
					name="allAvailable" />
                <label class="col-sm-2 control-label">Provider</label>
                <select class="form-control" id="provider"
					name="provider">
                	<c:forEach items="${providers}" var="provider">
                		<option value="${provider.id}">${provider.name}</option>
                	</c:forEach>
                </select>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Save Product</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
