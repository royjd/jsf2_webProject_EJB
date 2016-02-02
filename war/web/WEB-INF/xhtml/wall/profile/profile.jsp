<%-- 
    Document   : init
    Created on : 11 déc. 2015, 17:38:16
    Author     : zakaridia
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class = "panel panel-default">
    <div class ="panel-heading">
        About Me
    </div>
    <div class="panel-content">
        <div class="row">
            <div class="col-sm-2">              
                <div class="list-group padding-25">
                    <a href="${pageContext.request.contextPath}/${username}/profile.htm" class="list-group-item">Resume </a>
                    <a href="${pageContext.request.contextPath}/${username}/experience.htm" class="list-group-item">Experiences</a>
                </div>
            </div>

            <jsp:include page="${profileContent}.jsp"/>
      

        </div>
    </div>
</div>
