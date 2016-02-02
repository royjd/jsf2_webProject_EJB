<%-- 
    Document   : friendRequest
    Created on : Dec 10, 2015, 4:39:44 PM
    Author     : Karl Lauret
--%>

<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <div class="bg-primary">
                <h2>Friend Request</h2>
                <c:forEach items="${friendToAccept}" var="tier" >
                    ${tier.email}      
                    <a href="acceptFriendship.htm?id=${tier.id}" class="bg-info"> Accept </a>
                    <a href="deniedFriendship.htm?id=${tier.id}" class="bg-info"> Denied </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
