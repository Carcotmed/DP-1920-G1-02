<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="Products">
    <jsp:body>
        <h2>Product</h2>

        <form:form modelAttribute="product" class="form-horizontal" action="/products/save">
            <div class="form-group has-feedback">
				<petclinic:inputField label="Name" name="name"/>
                <petclinic:inputField label="Price" name="price"/>
                <petclinic:inputField label="Quantity" name="quantity"/>
                <petclinic:checkboxInput label="All Available" name="allAvailable"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="providerId" value="${provider.id}"/>
                    <button class="btn btn-default" type="submit">Save Product</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
