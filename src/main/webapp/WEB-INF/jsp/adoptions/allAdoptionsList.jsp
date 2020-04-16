<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="adoptions">
    <h2>Adoptions history</h2>
    
	<c:out value="${error}"></c:out>


    <table id="adoptionsTable" class="table table-striped">
        <thead>
        <tr>
            <th>Pet</th>
            <th>Owner</th>
            <th>Date</th>
            <th>End date</th>
            <sec:authorize access="hasAuthority('admin')">
            	<th>Actions</th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptions}" var="adoption">
	        <tr>
	            <td>
	                <c:out value="${adoption.pet.name}"/>
	            </td>
	            <td>
	                <c:out value="${adoption.owner.firstName} "/> <c:out value="${adoption.owner.lastName}"/>
	            </td>
	            <td>
	                <c:out value="${adoption.date}"/>
	            </td>
	            <td>
	                <c:out value="${adoption.end}"/>
	            </td>
	            <sec:authorize access="hasAuthority('admin')">
	            	<td>
						<spring:url value="/adoptions/delete/{adoptionId}" var="adoptionUrl">
							<spring:param name="adoptionId" value="${adoption.id}" />
						</spring:url>
						<a href="${fn:escapeXml(adoptionUrl)}">Delete</a>
	            	</td>
	            </sec:authorize>
	        </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href='<spring:url value="/adoptions" htmlEscape="true"/>'>Return</a>
</petclinic:layout>
