<%-- 
    Document   : friendRequest
    Created on : Dec 10, 2015, 4:18:29 PM
    Author     : Karl Lauret
--%>
<div class="row sideBarBlock first">
    <div class="col-sm-12">
        <div class="border-bot">
           
            <h2>Friend Request</h2>
             <div class="row">
            <c:forEach items="${friendsToAccept}" var="tier" >
                <div class="col-sm-12">
                <a href="${tier.owner.username}.htm" class=""> @${tier.owner.username} </a>
                <div class="pull-right">
                <a href="acceptFriendship.htm?id=${tier.owner.id}" class="btn btn-xs btn-success glyphicon glyphicon-ok"> </a>
                <a href="deniedFriendship.htm?id=${tier.owner.id}" class="btn btn-xs btn-danger glyphicon glyphicon-remove"> </a>
                </div>
                </div>
            </c:forEach>  
            </div>
        </div>
    </div>
</div>

