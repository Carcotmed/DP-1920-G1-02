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
            <th>Date</th>
            <th>End date</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptions}" var="adoption">
	        <tr>
	            <td>
	                <c:out value="${adoption.pet.name}"/>
	            </td>
	            <td>
	                <c:out value="${adoption.date}"/>
	            </td>
	            <td>
	                <c:out value="${adoption.end}"/>
	            </td>
	        </tr>
        </c:forEach>
        </tbody>
    </table>
	<a class="btn btn-default" href='<spring:url value="/adoptions" htmlEscape="true"/>'>Return</a>
</petclinic:layout>
