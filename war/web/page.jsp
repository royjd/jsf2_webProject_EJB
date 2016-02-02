
<html lang="fr">

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/library/bootstrap/css/css.css"/>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/resources/library/bootstrap/js/bootstrap.js"></script>
    <link rel="stylesheet" type="text/css" href='${pageContext.request.contextPath}/resources/library/jquery-ui/jquery-ui.css'/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/post/comment.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/post/post.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/general.css"/>
    <script src="${pageContext.request.contextPath}/resources/library/jquery-ui/jquery-ui.js"></script>

    <head>
        <title>Fanfare</title>
    </head>
    <body style="padding-top: 70px">
        <%@ include file="commun/header.jsp" %>

        <jsp:include page="${content}.jsp" flush="true" />
    </body>
</html>