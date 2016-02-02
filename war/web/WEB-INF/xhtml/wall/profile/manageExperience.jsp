<%-- 
    Document   : addExperience
    Created on : 20 déc. 2015, 00:38:34
    Author     : Diawara
--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-sm-7 padding-25">
    <c:if test="${not empty msg}">
        <div class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <h3>${Title}</h3>

    <form:form action="${pageContext.request.contextPath}/${user.username}/experience/manage.htm" method="POST" modelAttribute="newExperience" class="form-horizontal">

        <spring:bind path="id">
            <form:hidden path="id" id="id"/>
        </spring:bind>
        <spring:bind path="localisation.id">
            <form:hidden path="localisation.id" id="localisationid"/>
        </spring:bind>

        <spring:bind path="title">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-10">
                    <form:input path="title" class="form-control" id="title" placeholder="Title" />
                    <form:errors path="title" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="realisationDate">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-10">
                    <form:input path="realisationDate" class="form-control" id="realisation" placeholder="Realisation date ( dd / mm / yyyy )" />
                    <form:errors path="realisationDate" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="localisation.city">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4">
                    <form:input path="localisation.city" class="form-control" id="city" placeholder="City" />
                    <form:errors path="localisation.city" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="localisation.stat">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4">
                    <form:input path="localisation.stat" class="form-control" id="stat" placeholder="Stat" />
                    <form:errors path="localisation.stat" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="localisation.street">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4">
                    <form:input path="localisation.street" class="form-control" id="street" placeholder="Street" />
                    <form:errors path="localisation.street" class="control-label" />
                </div>
            </div>
        </spring:bind>

        <spring:bind path="localisation.zipcode">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-4">
                    <form:input path="localisation.zipcode" class="form-control" id="zipcode" placeholder="Zipcode" />
                    <form:errors path="localisation.zipcode" class="control-label" />
                </div>
            </div>
        </spring:bind>


        <spring:bind path="description">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <label class="col-sm-2 control-label"></label>
                <div class="col-sm-10">
                    <form:textarea path="description" class="form-control" id="description" placeholder="Experience description" />
                    <form:errors path="description" class="control-label" />
                </div>
            </div>
        </spring:bind>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-success pull-right">${submitValue}</button>
                <a href="${pageContext.request.contextPath}/${user.username}/experience.htm"  class="btn btn-primary" role="button">Cancel</a>
            </div>
        </div>
    </form:form>

</div>
<div class="col-sm-3">
</div>