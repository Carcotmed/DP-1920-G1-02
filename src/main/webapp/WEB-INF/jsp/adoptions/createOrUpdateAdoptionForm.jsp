<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="adoptions">
    <jsp:attribute name="customScript">
        <script>
	        $(function () {
	            $("#end").datepicker({dateFormat: 'yy/mm/dd'});
	        });
        </script>
    </jsp:attribute>
    <jsp:body>
	    <h2>
	        <c:if test="${adoption['new']}">New </c:if> Adoption
	    </h2>
	    <form:form modelAttribute="adoption" class="form-horizontal" id="add-adoption-form">
	        <div class="form-group has-feedback">
	        	<p>Select when you want to give back the pet you are adopting, in case you want to hold it forever just leave the following field empty</p>
	            <petclinic:inputField label="End" name="end"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <c:choose>
	                    <c:when test="${adoption['new']}">
	                        <button class="btn btn-default" type="submit">Adopt</button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit">Update adoption</button>
	                    </c:otherwise>
	                </c:choose>
	            </div>
	        </div>
	    </form:form>
    </jsp:body>
</petclinic:layout>
