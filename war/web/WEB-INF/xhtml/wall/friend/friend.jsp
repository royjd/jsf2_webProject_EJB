<%-- 
    Document   : friend
    Created on : 25 déc. 2015, 22:34:41
    Author     : Diawara
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class = "panel panel-default">
    <div class ="panel-heading">
        <h4> List friends </h4>
        <ul class="list-inline">
            <li><a href="#">All friends</a></li>
            <li><a href="#">Birth Day</a></li>
        </ul>
    </div>
    <div class="panel-body">
            <c:forEach items="${friends}" var="var">
                <div class="col-sm-4">

                    <c:choose> 
                        <c:when test="${var.friend.username == username}"> 
                            <c:set var="friend" scope="session" value="${var.owner}"/>
                        </c:when>
                        <c:otherwise>
                            <c:set var="friend" scope="session" value="${var.friend}"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="panel panel-default" id="friend">
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-sm-4 btn-info">
                                    <img src="${pageContext.request.contextPath}/resources/img/${friend.profile.pictureProfile.mediaType.link}" class="img-responsive"></img>
                                </div>
                                <div class="col-sm-8">
                                    <h3> 
                                        <a href="${pageContext.request.contextPath}/${friend.username}.htm"  class="" >${friend.profile.firstName} ${friend.profile.lastName}</a><br/>
                                        <a href="${pageContext.request.contextPath}/${friend.username}.htm"  class="" ><small>@${friend.username}</small></a>

                                    </h3>

                                    <a href="${pageContext.request.contextPath}/${friend.username}.htm"  class="btn btn-info btn-sm pull-left" >See wall</a>
                                    <c:if test="${canRemove}">
                                        <a href="${pageContext.request.contextPath}/${user.username}/removeFriend.htm?id=${var.id}"  class="pull-right btn btn-sm btn-danger" >Remove</a>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>



            </c:forEach>
        </div>

    </div>
</div>