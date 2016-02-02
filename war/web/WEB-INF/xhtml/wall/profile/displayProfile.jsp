<%-- 
    Document   : displayProfile
    Created on : 19 déc. 2015, 10:56:28
    Author     : zakaridia
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="col-sm-7">
    <c:if test="${not empty msg}">
        <div class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
            <strong>${msg}</strong>
        </div>
    </c:if>

    <div class="row">

        <div class="col-sm-4">
            <c:if test="${canModify}">
                <h4>Define profile picture<button id="addPicture" class="btn btn-primary btn-sm glyphicon glyphicon-plus"></button></h4>
                </c:if>

            <div id="addPictureProfilDiv" style="display:none">
                <form enctype="multipart/form-data" method="post" class="form-horizontal" role="form" id="pictureProfileForm" action="${pageContext.request.contextPath}/${username}/media/addPicture.htm">
                    <span class="btn btn-default btn-file">
                        Choose profile picture <input type="file" name="file"/>
                    </span>
                    <input name="type" type ="hidden" value="profile"/>
                    <div class="form-group">        
                        <div class="col-sm-10">
                            <button type="submit" class="btn btn-success">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
            <c:choose>
                <c:when test="${empty u.profile.pictureProfile}">

                </c:when>
                <c:otherwise>
                    <div class="img-overlay">
                        <img src="${pageContext.request.contextPath}/resources/img/${u.profile.pictureProfile.mediaType.link}" class="img-responsive"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="col-sm-8">
            <h2>${u.profile.firstName} ${u.profile.lastName}</h2>       


            <h3>
                Gender :  

                <small>
                    <c:choose>
                        <c:when test="${empty u.profile.physical.gender}">
                            Not define
                        </c:when>
                        <c:when test="${u.profile.physical.gender=='M'}">
                            Male
                        </c:when>
                        <c:otherwise>
                            Female
                        </c:otherwise>
                    </c:choose>
                </small>   
            </h3>


            <h3>
                Height : <small>${ empty u.profile.physical.height ? "Not define" : u.profile.physical.height}</small>

                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Weight : <small>${ empty u.profile.physical.weight ? "Not define" : u.profile.physical.weight}</small>
            </h3>

            <h3>
                Live in : 
                <small>
                    <c:choose>
                        <c:when test="${empty u.profile.country && empty u.profile.city}">
                            Not define
                        </c:when>
                        <c:otherwise>
                            ${u.profile.city} ${not empty u.profile.city && not empty u.profile.country ? " / " : ""}${u.profile.country}
                        </c:otherwise>
                    </c:choose>
                </small> 
            </h3>
        </div>

    </div>

    <hr>
    <h3>Brief Description</h3>
    <p class="well">
        ${ empty u.profile.description ? "No description" :u.profile.description}
    </p>

    <c:if  test="${canModify}">
        <div class="pull-right">
            <a href="${pageContext.request.contextPath}/${u.username}/profile/edit.htm"  class="btn btn-info" role="button">Edit profile</a>
        </div>
        <div class='clearfix'>

        </div>
    </c:if>

    <hr>
    <h3>Experiences</h3>
    <hr>
    <c:forEach items="${u.profile.experiences}" var="experience" >
        <div class="media">
            <div class="media-body">
                <h3 class="media-heading">${experience.title}</h3>

                ${experience.description}
                <p class="text-muted">
                    <small>
                        On ${experience.realisationDate}<br>

                        <c:if test="${not empty experience.localisation.street}">
                            ${experience.localisation.street}<br>
                        </c:if>
                        ${experience.localisation.zipcode} ${experience.localisation.city} - ${experience.localisation.stat} 
                    </small>
                </p>
            </div>

            <c:if test="${canModify}">
                <div class="pull-right">
                    <a href="${pageContext.request.contextPath}/${u.username}/experience/manage/${experience.id}.htm"  class="btn btn-info" role="button">Edit</a>
                </div>
            </c:if>
            <div class="clearfix"></div>
            <hr>
        </div>
    </c:forEach>

    <c:if test="${not empty u.profile.experiences}">
        <a href="${pageContext.request.contextPath}/${u.username}/experience.htm"  class="btn btn-info" role="button">See more</a>
    </c:if>

    <c:if test="${canModify}">
        <a href="${pageContext.request.contextPath}/${u.username}/experience/manage/0.htm"  class="pull-right btn btn-success" role="button">Add experience</a>
    </c:if>

</div>

<div class="col-sm-3 padding-25">

    <h4>
        Email : <small>${u.email}</small>
    </h4>
    <hr>

    <h4>
        @<small>${u.username}</small>
    </h4>
    <hr>

    <h4>
        Mobile Number : <small>${ empty u.profile.phone ? "Not define" : u.profile.phone}</small>
    </h4>
    <hr>

    <h4>
        Birth day : <small>Not define</small>
    </h4>

</div> 