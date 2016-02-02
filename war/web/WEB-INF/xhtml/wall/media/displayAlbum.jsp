<%-- 
    Document   : displayAlbum
    Created on : 2 janv. 2016, 21:59:12
    Author     : Diawara
--%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
    <c:if test="${user.username == username}">
        
        <a href="${pageContext.request.contextPath}/${username}/media/add.htm?val=${album.id}">
            Add photo
            <c:choose>
                <c:when test="${empty album.title}">
                    to your album
                </c:when> 
                <c:otherwise>
                    to ${album.title} 
                </c:otherwise>
            </c:choose>

        </a>
    </c:if>
</div>
<br>    
<c:forEach items="${album.medias}" var="photo">
    <div class="col-xs-12 col-sm-6 col-md-3">
        <jsp:include page="../../commun/displayPhoto.jsp" >
            <jsp:param name="link" value="${photo.mediaType.link}" />
            <jsp:param name="title" value="${photo.title}" />
            <jsp:param name="body" value="${photo.body}" />
        </jsp:include>

    </div>
</c:forEach>