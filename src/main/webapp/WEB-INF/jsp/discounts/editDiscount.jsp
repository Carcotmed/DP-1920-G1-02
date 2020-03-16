<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="Discounts">
	<jsp:body>
        <h2>Discounts</h2>

        <form:form modelAttribute="discount" class="form-horizontal"
			action="/discounts/save">
            <div class="form-group has-feedback">
			    <petclinic:inputField label="Product" name=""></petclinic:inputField>
                <petclinic:inputField label="Percentage"
					name="percentage" />
                <div class="control-group">
              	  <petclinic:selectField label="Provider" name="provider" size="100" names="${providers}" />
            	</div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button class="btn btn-default" type="submit">Save Product</button>
                </div>
            </div>
        </form:form>
    </jsp:body>

</petclinic:layout>
