/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function setCurrentId(postID) {
    alert(postID);
    $.get("ajaxPage/post.xhtml?id=" + postID, function (data) {
        $("#TEST1").html(data);
    });
}