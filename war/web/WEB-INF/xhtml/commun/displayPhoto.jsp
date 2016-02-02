<%-- 
    Document   : displayPhoto
    Created on : 5 janv. 2016, 19:51:34
    Author     : zdiawara
--%>
<div class="img-overlay">
    
    <a href="#">
        <img src="${pageContext.request.contextPath}/resources/img/${param.link}" class="img-responsive"/>
    </a>
    <div class="text project-overlay" style="text-align:center;display:none;">
        <a href="#"><h4>${param.title}</h4></a>
        <p>${param.body}</p>
    </div>
</div>
   