<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="images">
	<h2>
		New
		Image
	</h2>
	<form:form modelAttribute="image" class="form-horizontal"
		id="add-image-form" enctype='multipart/form-data'>
		<div class="form-group has-feedback">
			<input type="file" id="image" name="image"
				accept="image/png, image/jpeg" required="required">
			<button class="btn btn-default" type="submit">Add image</button>


		</div>
	</form:form>
</petclinic:layout>
