<%-- 
    Document   : addAlbum
    Created on : 31 déc. 2015, 09:45:05
    Author     : Diawara
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="col-sm-7">
    <c:if test="${selectMsg || extensionMsg}">
        <div class="alert alert-danger alert-dismissible col-sm-offset-3" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>Error ${extensionMsg ? "Format jpg , jpeg , png are supported" : ""}</strong>
        </div>
    </c:if>

    <c:choose> 
        <c:when test="${val == 0}"> 
            Create a new Album
        </c:when>
        <c:otherwise>
            Add a new photo
        </c:otherwise>
    </c:choose> 
    <hr>
    <form:form action="${pageContext.request.contextPath}/${username}/media/add.htm" method="POST" modelAttribute="album" class="form-horizontal" enctype="multipart/form-data">

        <spring:bind path="id">
            <form:hidden path="id" id="id"/>
        </spring:bind>

        <c:if test="${val==-1 || val==0 }">
            <c:set var="tier" value="${true}" />
        </c:if>

        <c:if test="${tier}">
            <spring:bind path="title">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-offset-3">
                        <form:input path="title" class="form-control" id="title" placeholder="Title" />
                        <form:errors path="title" class="control-label" />
                    </div>
                </div>
            </spring:bind>
        </c:if>



        <div class="form-group ${status.error ? 'has-error' : ''}">
            <div class="col-sm-offset-3">
                <span class="btn btn-default btn-file">
                    Choose photo(s) <input type="file" name="file" multiple/>
                </span>
                <c:if test="${not empty  notFileMsg}">
                    <span class="text-danger">Please, select photo(s)</span>
                </c:if> 
            </div>
        </div>

        <c:if test="${tier}">
            <spring:bind path="localisation">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-offset-3">
                        <form:input path="localisation" class="form-control" id="localisation" placeholder="Localisation" />
                        <form:errors path="localisation" class="control-label" />
                    </div>
                </div>
            </spring:bind>
        </c:if>

        <c:if test="${tier}">
            <spring:bind path="body">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <div class="col-sm-offset-3">
                        <form:textarea path="body" class="form-control" id="body" placeholder="Description ... " rows="5" />
                        <form:errors path="body" class="control-label" />
                    </div>
                </div>
            </spring:bind>
            <c:set var="tier" value="${true}" />
        </c:if>

        <div class="form-group">
            <div class="col-sm-offset-3">
                <button type="submit" class="btn btn-success ${tier? "pull-right" : ""}">
                    <c:choose> 
                        <c:when test="${val == 0}"> 
                            Create Album
                        </c:when>
                        <c:otherwise>
                            Add photo
                        </c:otherwise>
                    </c:choose> 
                </button>
                <a href="#"  class="btn btn-info" role="button">Cancel</a>
            </div>
        </div>

    </form:form>

</div>