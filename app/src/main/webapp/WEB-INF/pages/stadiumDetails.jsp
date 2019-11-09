<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: mbonjour
  Date: 04.11.19
  Time: 22:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Matches</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body>
<nav class="nav">
    <a class="nav-link" href="/Project-One/index">Home</a>
    <c:if test="${user.isAdmin() == true}">
        <a class="nav-link active" href="/Project-One/team">Teams</a>
        <a class="nav-link" href="/Project-One/stadium">Stadiums</a>
    </c:if>
    <a class="nav-link" href="/Project-One/match">Matches</a>
    <a class="nav-link" href="/Project-One/logout">Logout</a>
</nav>

<h1>Stadium Details</h1>
<h2>
    Name
</h2>
<p>${stadium.getName()}</p>
<h2>
    Location
</h2>
<p>${stadium.getLocation()}</p>

<h2>
    Viewer places
</h2>
<p>${stadium.getViewerPlaces()}</p>
</body>
</html>
