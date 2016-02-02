<%-- 
    Document   : album
    Created on : Jan 6, 2016, 9:23:59 AM
    Author     : Karl Lauret
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:choose> 
    <c:when test="${requestScope.tier.getClass().name == 'dao.AlbumEntity'}"> 
        <div>

            <c:set var="tier" value="${requestScope.tier}" scope="request" />
            <jsp:include page="header.jsp" />
            <p>${requestScope.tier.body}<p>
                <a href="#">
                    <div class="img-overlay">
                        <img src="${pageContext.request.contextPath}/resources/img/${requestScope.tier.cover.mediaType.link}" class="img-responsive"/>
                    </div>
                </a>
        </div>
    </c:when>
    <c:when test="${requestScope.tier.getClass().name == 'dao.MediaEntity'}"> 
        <div>

            <c:set var="tier" value="${requestScope.tier}" scope="request" />
            <jsp:include page="header.jsp" />
            <p>${requestScope.tier.body}<p>
                <a href="#">
                    <div class="img-overlay">
                        <img src="${pageContext.request.contextPath}/resources/img/${requestScope.tier.mediaType.link}" class="img-responsive"/>
                    </div>
                </a>
        </div>
    </c:when>
    <c:when test="${requestScope.tier.getClass().name == 'dao.RecomendationEntity'}"> 
        <div>

            <c:set var="tier" value="${requestScope.tier}" scope="request" />
            <jsp:include page="header.jsp" />
            <p>${requestScope.tier.body}<p>
        </div>
    </c:when>
    <c:when test="${requestScope.tier.getClass().name == 'dao.NewsEntity'}"> 
        <div>

            <c:set var="tier" value="${requestScope.tier}" scope="request" />
            <jsp:include page="header.jsp" />
            <p>${requestScope.tier.body}<p>
                <c:if test="${not empty requestScope.tier.media.mediaType.link}">
                    <img class="img-responsive" src="${pageContext.request.contextPath}/resources/img/${requestScope.tier.media.mediaType.link}"/>
                </c:if>
        </div>
    </c:when>
    <c:when test="${requestScope.tier.getClass().name == 'dao.CommentEntity'}"> 
        <div id="post${requestScope.tier.id}" class='col-xs-12 postComments'>
            <div class='row postComment'>
                <div class="col-xs-12">
                    <div class="pull-left"><img class='img-postHeader' src='${pageContext.request.contextPath}/resources/img/${requestScope.tier.author.profile.pictureProfile.mediaType.link}' /> </div>
                    <div class="col-xs-7">
                        <div class="row">
                            <div class="col-xs-12 "><h3>
                                    ${requestScope.tier.author.username}  : ${requestScope.tier.body}
                                </h3></div>
                            <div class="col-xs-12">
                                <c:if test="${canComment}">
                                    <button class='pull-left btn btn-xs btn-primary repplyCommentBtn' parentId='${requestScope.tier.id}' mainId='${requestScope.tier.postMain}'>Reply</button>
                                </c:if>

                            </div>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="row">
                            <div class="col-md-12  text-right postDateHeader"> ${requestScope.tier.createDateFormated}  </div>
                            <div class="col-md-12  text-right postDateHeader">  ${requestScope.tier.createdTime}  </div>
                        </div>
                    </div>
                </div>
            </div>
            <c:forEach items="${requestScope.tier.comments}" var="comments">
                <c:set var="tier" value="${comments}" scope="request"/>
                <jsp:include page="post.jsp" />
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>

    </c:otherwise>
</c:choose>





