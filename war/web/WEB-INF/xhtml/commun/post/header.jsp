<%-- 
    Document   : header
    Created on : Jan 6, 2016, 9:12:37 AM
    Author     : Karl Lauret
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class='row postHeader'>
    <div class="pull-left "><img class='img-postHeader' src='${pageContext.request.contextPath}/resources/img/${requestScope.tier.author.profile.pictureProfile.mediaType.link}' /> </div>
    <div class="col-md-7">
        <div class="row">
            <div class="col-md-12">
                <h2> ${title}  </h2>
            </div>
            <div class="col-md-12 ">
                <h3> ${requestScope.tier.author.username}  </h3>
            </div>
        </div>
    </div>
    <div class="col-md-4">
        <div class="row">
            <div class="col-md-12  text-right postDateHeader"> ${requestScope.tier.createDateFormated}  </div>
            <div class="col-md-12  text-right postDateHeader">  ${requestScope.tier.createdTime}  </div>
        </div>
    </div>
</div>