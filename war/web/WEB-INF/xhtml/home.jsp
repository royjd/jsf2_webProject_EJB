    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/sideBar/general.css"/>
<div class="container">
    <div class="row">
        <div class="col-sm-2">
            <%@ include file="home/leftSideBar.jsp" %>
        </div>
        <div class="col-sm-8 post-container">
            <jsp:include page="home/post.jsp" flush="true" />
        </div>
        <div class="col-sm-2">
            <jsp:include page="home/rightSideBar.jsp" flush="true"/>
        </div>

    </div>
</div>
