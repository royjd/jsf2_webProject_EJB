<%-- 
    Document   : displayAlbum
    Created on : 7 janv. 2016, 22:47:55
    Author     : zdiawara
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

    

    <img src="${pageContext.request.contextPath}/resources/img/${param.link}" class="img-responsive"/>
    
    <div  style="text-align:center;">
        <a href="#"><h4>${param.title}</h4></a>
        <p>${param.body}</p>
    </div>