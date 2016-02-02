<%-- 
    Document   : profileHeader
    Created on : Dec 10, 2015, 3:50:53 PM
    Author     : Karl Lauret
--%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/sideBar/profileHeader.css"/>
<div class="row sideBarBlock first">
    <div class="col-sm-12">
        <div class="">
            <a href="${pageContext.request.contextPath}/${user.username}/profile.htm">
                <img id="profileHeaderPicture" src="${pageContext.request.contextPath}/resources/img/${photoProfile}" class="img-responsive center-block"/>
            </a>
             
        </div>

    </div>
</div>
