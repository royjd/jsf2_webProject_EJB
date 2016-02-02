<%-- 
    Document   : displayExperience
    Created on : 20 déc. 2015, 11:00:15
    Author     : Diawara
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
    String addExp = "";
    addExp = request.getContextPath() + "/" + (String) request.getAttribute("username") + "/experience/add.htm";
    addExp = "<a href=\"" + addExp + "\"> Ajouter une experience</a>";
    request.setAttribute("addExp", addExp);
%>

<div class="col-sm-7 padding-25">
    <c:if test="${not empty msg}">
        <div class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <c:if test="${empty experiences}">
        <p class="well">
            No experiences
        </p>
        <c:if test="${canModify}">
            <a href="${pageContext.request.contextPath}/${user.username}/experience/manage/0.htm"  class="btn btn-info" role="button">Add experience</a>
        </c:if>
    </c:if>

    <c:forEach items="${experiences}" var="experience">
        <div class="media">
            <div class="media-body">
                <h4 class="media-heading">${experience.title}</h4>
                ${experience.description}
                <p class="text-muted">
                    <small>
                        On ${experience.realisationDate}<br>

                        <c:if test="${not empty experience.localisation.street}">
                            ${experience.localisation.street}<br>
                        </c:if>
                        ${experience.localisation.zipcode} ${experience.localisation.city} - ${experience.localisation.stat} 
                    </small>
                </p>
            </div>
            <c:if test="${canModify}">
                <div class="pull-right">
                    <a href="${pageContext.request.contextPath}/${user.username}/experience/manage/${experience.id}.htm"  class="btn btn-info" role="button">Edit</a>
                    <a href="${pageContext.request.contextPath}/${user.username}/experience/delete/${experience.id}.htm"  class="btn btn-danger" role="button">Remove</a>
                </div>
            </c:if>
            <div class="clearfix"></div>
        </div>
        <hr>
    </c:forEach>

</div>
<div class="col-sm-3 padding-70">
    <c:if test="${canModify}">
        <a href="${pageContext.request.contextPath}/${user.username}/experience/manage/0.htm" >Add experience</a>
    </c:if>
</div>