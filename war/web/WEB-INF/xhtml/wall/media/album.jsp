<%-- 
    Document   : post
    Created on : Dec 28, 2015, 10:15:15 AM
    Author     : Karl Lauret
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<div class="postMedia col-sm-12 bg-success">
    <c:forEach var="i" begin="1" end="${post.size()}" step="1">
        <c:set var="tier" value="${post[post.size()-i]}" />

        <c:if test="${tier.getClass().name == 'dao.AlbumEntity'}"> 
            <div class="col-xs-12 col-sm-6 col-md-3">
                <a href="${pageContext.request.contextPath}/${username}/media/displayAlbum.htm?albumId=${tier.id}">
                        <jsp:include page="../../commun/displayAlbum.jsp" >
                            <jsp:param name="link" value="${tier.cover.mediaType.link}" />
                            <jsp:param name="title" value="${tier.title}" />
                            <jsp:param name="body" value="${tier.body}" />
                        </jsp:include>

                </a>
            </div>

        </c:if> 
    </c:forEach>
</div>
