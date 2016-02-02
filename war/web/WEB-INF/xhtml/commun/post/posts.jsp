<%-- 
    Document   : posts
    Created on : Jan 6, 2016, 11:20:56 AM
    Author     : Karl Lauret
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<c:if test="${posts.size() !=0}"> 
    <c:forEach items="${posts}" var="tier">
        <c:if test="${tier.getClass().name != 'dao.CommentEntity'}"> 
            <c:if test="${tier.getClass().name != 'dao.MediaEntity' || (tier.getClass().name == 'dao.MediaEntity' && tier.news == null )}">
                <div id="${tier.id}" class="post">
                    <div  class= "col-sm-12">
                        <c:set var="tier" value="${tier}" scope="request" />
                        <jsp:include page="post.jsp" />
                        
                    </div>
                    <div class="postComment col-sm-12">
                        
                        <c:forEach items="${tier.comments}" var="commentPost">
                            <c:set var="tier" value="${commentPost}" scope="request" />
                            <jsp:include page="post.jsp" />
                            
                        </c:forEach>
                    </div>

                    <c:if test="${canComment}">
                        <form:form action="${commentLinks}" method="POST" modelAttribute="newComment" class="form-horizontal">

                            <spring:bind path="postMain.id">
                                <form:hidden path="postMain.id" id="postMainId" value="${tier.id}"/>
                            </spring:bind>

                            <spring:bind path="postParent.id">
                                <form:hidden path="postParent.id" id="postParentId" value="${tier.id}"/>

                            </spring:bind>

                            <spring:bind path="body">
                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                    <div class="col-sm-12">
                                        <form:input path="body" class="form-control" id="bodyCommet" placeholder="Type your comment here!" />
                                        <form:errors path="body" class="control-label" />
                                    </div>
                                </div>
                            </spring:bind>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" class="btn btn-success pull-right">Send</button>
                                </div>
                            </div>
                        </form:form>

                    </c:if>
                </div>


            </c:if>
        </c:if> 
    </c:forEach>

</c:if>  
