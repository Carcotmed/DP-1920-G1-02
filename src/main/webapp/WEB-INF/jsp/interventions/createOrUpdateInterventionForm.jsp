<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="interventions">
	<h2>
		<c:if test="${intervention['new']}">New </c:if>
		Intervention
	</h2>
	<form:form modelAttribute="intervention" class="form-horizontal"
		id="add-intervention-form">
		<div class="form-group has-feedback">
			<petclinic:inputField label="Name" name="name" />

			<petclinic:selectPersonField label="Vet" name="vet" size="5" names="${vets}" />
			
			<petclinic:selectMultipleField label="Products" name="requiredProducts" size="10" names="${products}"/>
			
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${intervention['new']}">
						<button class="btn btn-default" type="submit">Add
							Intervention</button>
					</c:when>
					<c:otherwise>
						<button class="btn btn-default" type="submit">Update
							Intervention</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</form:form>
</petclinic:layout>
