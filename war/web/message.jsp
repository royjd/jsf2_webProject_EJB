<%-- 
    Document   : message
    Created on : Dec 10, 2015, 4:37:59 PM
    Author     : Karl Lauret
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="container">
    <div class="row">
        <div class="col-xs-4 bg-success">
            <button type="button" class="btn btn-info btn-lg messageNew" data-toggle="modal" data-target="#myModal">New Message</button>
            <c:forEach items="${groupList}" var="groupMessage">
                <a href="${pageContext.request.contextPath}/message/${groupMessage.key}.htm">
                     
                    <c:choose> 
                        <c:when test="${groupMessage.key == currentGroupMessage}"> 
                            <h3 id="activeGroupMessage" class="bg-primary">
                                <div class="emails" style="display:none;">
                                    <c:forEach items="${groupMessage.value}" var="tier" >
                                        <c:if test="${tier.user.email != user.email || fn:length(groupMessage.value) == 1}"> 
                                            ${tier.user.email},
                                          
                                        </c:if> 
                                    </c:forEach>
                                </div>
                                <c:forEach items="${groupMessage.value}" var="tier" >
                                    @${tier.user.username}
                                </c:forEach>
                            </h3>
                        </c:when>
                        <c:otherwise>
                            <h4 id="${groupMessage.key}" class="bg-danger"> 
                                <c:forEach items="${groupMessage.value}" var="tier" >
                                    @${tier.user.username}
                                <!--${tier.user.getClass().name}-->
                                </c:forEach>
                                <c:if test="${newMessageGroupList[groupMessage.key]}"> 
                                    *
                                </c:if> 



                            </h4>
                        </c:otherwise>
                    </c:choose>


                </a>

            </c:forEach>

        </div>
        <div class="col-xs-8 bg-danger">
            <div class="row">
                <c:forEach items="${messages}" var="tier" >
                    <div class="col-xs-12 h4">${tier.sendBy.email}</div>
                    <p class="col-xs-12">${tier.content}</p>
                    <div class="col-xs-12 bg-primary"></div>
                </c:forEach>
            </div>
            <!-- Trigger the modal with a button -->
            <c:if test="${fn:length(messages) > 0}">
                <div class="row">
                    <form method="post" class="form-horizontal" role="form" id="replyForm" action="${pageContext.request.contextPath}/sendMessage.htm">
                        <div class="form-group"  style="display:none;">
                            <label class="control-label col-sm-2" for="emails">Email:</label>
                            <div class="col-sm-10 ui-front"><!-- need the ui-front here or the uatocompletion won't work-->
                                <input type="TEXT" class="form-control" name="emails" id="replyEmail" placeholder="To">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-2" for="message">Message:</label>
                            <div class="col-sm-10">
                                <textarea  type="TEXT" class="form-control" name="message" rows="5" id="replyMessage" placeholder="message"></textarea>
                            </div>
                        </div>
                        <div class="form-group">        
                            <div class="col-sm-offset-2 col-sm-10">
                                <button id="replyFormSubmit" type="submit" class="btn btn-success pull-right" action="${pageContext.request.contextPath}/sendMessage.htm">Reply</button>
                            </div>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>


        <!-- Modal -->
        <div id="myModal" class="modal fade" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body">
                        <form method="post" class="form-horizontal" role="form" id="sendMessageForm" action="${pageContext.request.contextPath}/sendMessage.htm">
                            <div class="form-group" >
                                <label class="control-label col-sm-2" for="emails">Email:</label>
                                <div class="col-sm-10 ui-front"><!-- need the ui-front here or the uatocompletion won't work-->
                                    <input type="TEXT" class="form-control" name="emails" id="emails" placeholder="To">
                                </div>
                            </div>
                            <!--<div class="form-group">
                                <label class="control-label col-sm-2" for="subject">Subject:</label>
                                <div class="col-sm-10">
                                    <input type="TEXT" class="form-control" name="subject" id="subject" placeholder="subject">
                                </div>
                            </div>-->
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="message">Message:</label>
                                <div class="col-sm-10">
                                    <textarea  type="TEXT" class="form-control" name="message" rows="5" id="message" placeholder="message"></textarea>
                                </div>
                            </div>
                            <div class="form-group">        
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button id="sendMessageSubmit" type="submit" class="btn btn-success pull-right" action="${pageContext.request.contextPath}/sendMessage.htm">Submit</button>
                                </div>
                            </div>
                        </form>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal" >Close</button>
                    </div>

                </div>

            </div>
        </div>
        <!--<div class="col-xs-12 bg-danger">
            <form method="post" class="form-horizontal" role="form" action="${pageContext.request.contextPath}/sendMessage.htm">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="emails">Email:</label>
                    <div class="col-sm-10">
                        <input type="TEXT" class="form-control" name="emails" id="emails" placeholder="To">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="subject">Subject:</label>
                    <div class="col-sm-10">
                        <input type="TEXT" class="form-control" name="subject" id="subject" placeholder="subject">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="message">Message:</label>
                    <div class="col-sm-10">
                        <textarea  type="TEXT" class="form-control" name="message" rows="5" id="message" placeholder="message"></textarea>
                    </div>
                </div>
                <div class="form-group">        
                    <div class="col-sm-offset-2 col-sm-10">
                        <button type="submit" class="btn btn-default">Submit</button>
                    </div>
                </div>
            </form>
        </div>-->

    </div>
</div>
<script type="text/javascript">
    $(document).ready(function () {
        $("button#sendMessageSubmit").click(function (event) {
            var that = $(this);
            //event.preventDefault();
            /*$.ajax({
             type: "POST",
             url: $(that).attr("action"), //process to mail
             data: $('form#sendMessageForm').serialize(),
             success: function (msg) {
             $("#myModal").modal('hide'); //hide popup  
             },
             error: function () {
             alert("failure");
             }
             });*/
        });
    });
    $('#replyEmail').val($('#activeGroupMessage >.emails').html().replace(/ /g, ''));
    $('.messageNew').click(function () {
        $('.modal-title').html("New Message");
        $('#emails').val("");
    });

</script>
<script type="text/javascript">
    $(document).ready(function () {
        function split(val) {
            return val.split(/,\s*/);
        }
        function extractLast(term) {
            return split(term).pop();
        }
        $('#emails')
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
                            url: "/fanfare/messageajaxTest.htm",
                            data: "email=" + extractLast(request.term),
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
                        var terms = split(this.value);
                        // remove the current input
                        terms.pop();
                        // add the selected item
                        terms.push(ui.item.email);
                        // add placeholder to get the comma-and-space at the end
                        terms.push("");
                        this.value = terms.join(", ");
                        return false;
                    }
                })
                //to personalise the display in the list        
                .autocomplete("instance")._renderItem = function (ul, item) {
            //alert('OMG');
            return $("<li>")
                    .append("<a>" + item.email + "<br>" + item.username + "</a>")
                    .appendTo(ul);
        };
    });
</script>