<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="providers">
    <h2>
        <c:if test="${provider['new']}">New </c:if> Provider
    </h2>
    <form:form modelAttribute="provider" class="form-horizontal" id="add-provider-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Provider Name" name="name"/>
            <petclinic:inputField label="Address" name="address"/>
            <petclinic:inputField label="Telephone" name="phone"/>
            <petclinic:inputField label="E-Mail" name="email"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${provider['new']}">
                        <button class="btn btn-default" type="submit">Add Provider</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Provider</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
