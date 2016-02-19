<%-- 
    Document   : search
    Created on : Dec 8, 2015, 2:09:46 PM
    Author     : Karl Lauret
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <h1>SEARCH</h1>

            <c:forEach items="${searchResult}" var="var">
                <c:if test="${var.username != user.username}"> 

                    <div class="col-sm-4">

                        <div class="panel panel-default" id="friend">
                            <div class="panel-body">
                                <div class="row">
                                    <div class="col-sm-4 btn-info">
                                           <img class="img-responsive" src="${pageContext.request.contextPath}/resources/img/${var.profile.pictureProfile.mediaType.link}"/>
                
                                    </div>
                                    <div class="col-sm-8">
                                        <h3> 
                                            <a href="${pageContext.request.contextPath}/${var.username}.htm"  class="" >${var.profile.firstName} ${var.profile.lastName}</a><br/>
                                            <a href="${pageContext.request.contextPath}/${var.username}.htm"  class="" ><small>@${var.username}</small></a>

                                        </h3>
                                        <a href="${pageContext.request.contextPath}/${var.username}.htm"  class="btn btn-info btn-sm pull-left" >See wall</a>
                                        <c:if test="${isFriendOrNot[var] != true && not empty user.username}"> 

                                            <a href="${pageContext.request.contextPath}/addFriend.htm?id=${var.id}"  class="pull-right btn btn-sm btn-primary" >Add_Friend</a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:if>


            </c:forEach>
        </div>
    </div>
</div>
