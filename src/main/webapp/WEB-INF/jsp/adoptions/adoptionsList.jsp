<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="adoptions">
    <h2>Adoptable pets</h2>
    
	<c:out value="${error}"></c:out>


    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Name</th>
            <th>Birth date</th>
            <th>Type</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
	        <tr>
	            <td>
	                <c:out value="${pet.name}"/>
	            </td>
	            <td>
	                <c:out value="${pet.birthDate}"/>
	            </td>
	            <td>
	                <c:out value="${pet.type}"/>
	            </td>
	            <td>
		                <spring:url value="/owners/{ownerId}/pets/{petId}" var="petUrl">
		             		<spring:param name="ownerId" value="${pet.owner.id}"/>
		             		<spring:param name="petId" value="${pet.id}"/>
			         	</spring:url>
			          	<a href="${fn:escapeXml(petUrl)}">Details</a>
	            </td>
	        </tr>
        </c:forEach>
        </tbody>
    </table>
    <sec:authorize access="hasAuthority('veterinarian')">
		<a class="btn btn-default" href='<spring:url value="/adoptions/newAdoptable" htmlEscape="true"/>'>Add Pet</a>
		<a class="btn btn-default" href='<spring:url value="/adoptions/allAdoptions" htmlEscape="true"/>'>List all adoptions</a>
	</sec:authorize>
    <sec:authorize access="hasAuthority('owner')">
		<a class="btn btn-default" href='<spring:url value="/adoptions/myAdoptions" htmlEscape="true"/>'>List my adoptions</a>
	</sec:authorize>
</petclinic:layout>
