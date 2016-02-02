<%-- 
    Document   : friendList
    Created on : Dec 10, 2015, 4:20:14 PM
    Author     : Karl Lauret
--%>

<div class="row sideBarBlock">
    <div class="col-sm-12">
        <div class="border-bot">
            <h2><a href="${pageContext.request.contextPath}/${user.username}/friends.htm">Friend List</a></h2>
            <div class="row">
                <c:forEach items="${friends}" var="tier" >
                    <c:if test="${tier.accepted}">
                        <div class="col-sm-12">
                            <c:choose> 
                                <c:when test="${tier.friend.email == user.email}"> 
                                    <a href="${tier.owner.username}.htm" class=""> @${tier.owner.username} </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${tier.friend.username}.htm" class=""> @${tier.friend.username} </a>
                                </c:otherwise>
                            </c:choose>
                            <a href="removeFriend.htm?id=${tier.id}" class="btn btn-xs btn-danger glyphicon glyphicon-remove pull-right"></a>
                        </div>
                    </c:if>

                </c:forEach>  
            </div>

        </div>

    </div>
</div>