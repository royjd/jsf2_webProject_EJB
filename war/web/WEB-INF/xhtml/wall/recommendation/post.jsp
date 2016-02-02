<%-- 
    Document   : post
    Created on : Dec 28, 2015, 10:15:15 AM
    Author     : Karl Lauret
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="${pageContext.request.contextPath}/resources/js/limitlessPostScroll.js"></script>
<div class="row">
    <c:if test="${user.username != username && canRecommended == true}"> 
        <form method="post" class="form-horizontal" role="form" id="newsForm" action="${pageContext.request.contextPath}/${username}/recommendation/addRecommentdation.htm">
            <div class="form-group">
                <div class="col-sm-12">
                    <input  type="TEXT" class="form-control" name="title" rows="5" id="title" placeholder="Title here"></input>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-12">
                    <textarea  type="TEXT" class="form-control" name="body" rows="5" id="body" placeholder="Type your message here"></textarea>
                </div>
            </div> 
            <div class="form-group">        
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-success pull-right">Submit</button>
                </div>
            </div>
        </form>
    </c:if> 





</div>
    
    <jsp:include page="../..//commun/post/posts.jsp" />
    <div id="loadmore" path="/fanfare/ajax_recommendation_post/${username}/" class="loadmore">LOADING OMG</div>
<script type="text/javascript">
    $(document).ready(function () {
        $('.repplyCommentBtn').click(function () {
            $('.repplyCommentInput').remove();
            $(this).after(
                    '<div class="row repplyCommentInput">' +
                    '<form action="${commentLinks}" method="POST" modelAttribute="newComment" class="form-horizontal">' +
                    '<input style="display:none" path="postMain.id" name="postMainId" id="postMainId" value="' + $(this).attr("mainid") + '"/>' +
                    '<input style="display:none" path="postParent.id" name="postParentId" id="postParentId" value="' + $(this).attr("parentid") + '"/>' +
                    '<div class="col-xs-12">' +
                    '<input path="body" class="form-control" id="bodyCommet" name="bodyCommet" placeholder="Type your comment here!" />' +
                    '</div>' +
                    '<div class="form-group">' +
                    '<div class="col-xs-12">' +
                    '<button type="submit" class="btn btn-success pull-right">Send</button>' +
                    '</div>' +
                    '</div>' +
                    '</form>' +
                    '</div>'

                    );
        });
    });
</script>