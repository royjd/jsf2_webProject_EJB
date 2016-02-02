<%-- 
    Document   : navBar
    Created on : Dec 10, 2015, 6:22:08 PM
    Author     : Karl Lauret
--%>

<nav class="navbar navbar-default">
  <div class="container">
    <div class="navbar-header">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/${username}/profile.htm">${username}</a>

    </div>
    <div>
      <ul class="nav navbar-nav">
        <li class="active"><a href="${pageContext.request.contextPath}/${username}.htm">Wall</a></li>
        <li><a href="${pageContext.request.contextPath}/${username}/recommendation.htm">Recommendation</a></li>
        <li><a href="${pageContext.request.contextPath}/${username}/friends.htm">Friends</a></li>
        <li><a href="${pageContext.request.contextPath}/${username}/media.htm">Medias</a></li>
        <li><a href="${pageContext.request.contextPath}/${username}/profile.htm">About Me</a></li>
      </ul>
    </div>
  </div>
</nav>