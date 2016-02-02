<%-- 
    Document   : notification
    Created on : Dec 10, 2015, 4:38:17 PM
    Author     : Karl Lauret
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container">
  
    <div class="row">
        <div class="col-sm-12">
            <h1> Notification </h1>
            <c:forEach items="${notifs}" var="tier" >
                <c:if test="${tier.message.post.getClass().name != 'dao.MediaEntity' || (tier.message.post.getClass().name == 'dao.MediaEntity' && tier.message.post.news == null )}">

                    <c:if test="${tier.newMessage == true}"> 
                        <div class="col-xs-12 bg-success">
                            <a href="${pageContext.request.contextPath}/notification/${tier.message.type}/${tier.message.post.id}/${tier.id}.htm">${tier.message.content}</a>
                        </div>
                    </c:if>
                    <c:if test="${tier.newMessage == false}">
                        <div class="col-xs-12 bg-danger">
                            <a href="${pageContext.request.contextPath}/notification/${tier.message.type}/${tier.message.post.id}/${tier.id}.htm">${tier.message.content}</a>
                        </div>
                    </c:if>

                    <div class="col-xs-12 bg-primary"></div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
