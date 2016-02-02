

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${not empty user.username}">
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="${pageContext.request.contextPath}/home.htm" class="glyphicon glyphicon-home"></a></li>
                            <li><a href="${pageContext.request.contextPath}/${user.username}.htm" class="">${user.username}</a></li>
                            <li><a href="${pageContext.request.contextPath}/message.htm" class="glyphicon glyphicon-envelope"></a></li>
                            <li><a href="${pageContext.request.contextPath}/notification.htm" class="glyphicon glyphicon-bell"></a></li>
                        </ul>
                    </div>
                    <div class="col-sm-6">
                        <ul class="nav navbar-nav pull-right">
                            <a class="navbar-brand pull-right" href="${pageContext.request.contextPath}/home.htm">FanFare</a>
                            <li class="dropdown pull-right">
                                <a class="dropdown-toggle glyphicon glyphicon-user" data-toggle="dropdown" href="#"><span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li> <a href="${pageContext.request.contextPath}/${user.username}.htm"> ${user.username} </a></li>
                                    <li> <a href="${pageContext.request.contextPath}/logout.htm"> Logout </a></li>
                                </ul>
                            </li>
                            <form method="post" class="navbar-form pull-right" role="form" action="${pageContext.request.contextPath}/search.htm">

                                <input type="text" name="searchParam" class="form-control" id="friendSearch" placeholder="Search...">

                                <button type="submit" class="btn btn-success">Go</button>
                            </form>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>

        <script type="text/javascript">
            $(document).ready(function () {
                $('#friendSearch')
                        // don't navigate away from the field on tab when selecting an item
                        .bind("keydown", function (event) {
                            if (event.keyCode === $.ui.keyCode.TAB &&
                                    $(this).autocomplete("instance").menu.active) {
                                event.preventDefault();
                            }
                        })
                        .autocomplete({
                            minLength: 0,
                            source: function (request, response) {
                                // delegate back to autocomplete, but extract the last term
                                // get the form values
                                $.ajax({
                                    type: "POST",
                                    url: "${pageContext.request.contextPath}/messageajaxTest.htm",
                                    data: "email=" + request.term,
                                    success: function (resp) {
                                        availableTags = [];
                                        console.log(resp);
                                        resp = JSON.parse(resp);
                                        console.log(resp);
                                        $.each(resp.user, function (i, item) {
                                            availableTags.push(item);
                                        });

                                        response(availableTags);
                                    },
                                    error: function (e) {
                                        alert('Error: ' + e);
                                        console.log(e);
                                    }
                                });
                            },
                            focus: function (event, ui) {
                                // prevent value inserted on focus
                                //$("#name").val(ui.item.email);
                                return false;
                            },
                            select: function (event, ui) {
                            }
                        })
                        //to personalise the display in the list        
                        .autocomplete("instance")._renderItem = function (ul, item) {
                    //alert('OMG');
                    return $("<li>")
                            .append("<a href='${pageContext.request.contextPath}/" + item.username + ".htm'>" + item.email + "<br>" + item.username + "</a>")
                            .appendTo(ul);
                };
            });
        </script>
    </c:when>

    <c:otherwise>
        <%@ include file="../index/header.jsp" %>
    </c:otherwise>
</c:choose>
