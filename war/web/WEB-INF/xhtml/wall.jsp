<%-- 
    Document   : wall
    Created on : Dec 10, 2015, 4:37:32 PM
    Author     : Karl Lauret
--%>
<%@ include file="wall/header.jsp" %>
<%@ include file="wall/navBar.jsp" %>
<div class="container">
    <div class="col-sm-12">
        <jsp:include page="wall/${wallContent}.jsp" flush="true" />
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("#addPicture").click(function () {
            $("#addPictureProfilDiv").toggle();
            if ($(this).hasClass("glyphicon-plus")) {
                $(this).removeClass("glyphicon-plus btn-primary").addClass("btn-danger glyphicon-remove");
            } else {
                $(this).removeClass("btn-danger glyphicon-remove").addClass("glyphicon-plus btn-primary");
            }
        });

        $("#addCover").click(function () {
            $("#addCoverDiv").toggle();
            if ($(this).hasClass("glyphicon-plus")) {
                $(this).removeClass("glyphicon-plus btn-primary").addClass("btn-danger glyphicon-remove");
            } else {
                $(this).removeClass("btn-danger glyphicon-remove").addClass("glyphicon-plus btn-primary");
            }
        });

    });

</script>    