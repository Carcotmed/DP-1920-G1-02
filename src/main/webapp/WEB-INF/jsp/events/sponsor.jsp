<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="events">
    <jsp:body>
	    <h2>
	        New sponsor
	    </h2>
	    <form:form modelAttribute="event" class="form-horizontal" id="add-event-form">
	        <div class="form-group has-feedback">
            	<petclinic:selectProviderField name="sponsor" label="Sponsor" names="${sponsors}" size="5"/>
				<c:out value="${error}"></c:out>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
                	<button class="btn btn-default" type="submit">Select sponsor</button>
	            </div>
	        </div>
	    </form:form>
    </jsp:body>
</petclinic:layout>
