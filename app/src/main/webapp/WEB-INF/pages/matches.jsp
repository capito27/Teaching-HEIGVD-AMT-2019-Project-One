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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
</head>
<body>
<nav class="nav">
    <a class="nav-link active" href="#">Matches</a>
    <a class="nav-link" href="/team">Teams</a>
    <a class="nav-link" href="/stadium">Stadiums</a>
</nav>

<!-- Button trigger modal -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#matchModal">
    Add Match
</button>

<!-- Modal -->
<div class="modal fade" style="align: right;" id="matchModal" tabindex="-1" role="dialog" aria-labelledby="matchModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="matchModalLabel">Add Match</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form method="POST" target="match">
                    <div class="form-group">
                        <label for="team1formControlSelect">Team1</label>
                        <select name="team1" class="form-control" id="team1formControlSelect">
                            <c:forEach items="${teams}" var="team"><option value="<c:out value="${team.getId()}"/>">${team.getName()}</option></c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="team2formControlSelect">Team2</label>
                        <select name="team2" class="form-control" id="team2formControlSelect">
                            <c:forEach items="${teams}" var="team"><option value="<c:out value="${team.getId()}"/>">${team.getName()}</option></c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="stadiumformControlSelect">Stadium</label>
                        <select name="stadium" class="form-control" id="stadiumformControlSelect">
                            <c:forEach items="${stadiums}" var="stadium"><option value="<c:out value="${stadium.getId()}"/>"> ${stadium.getName()}, ${stadium.getLocation()}</option></c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="score1Input">Score1</label>
                        <input name="score1" type="number" class="form-control" id="score1Input" placeholder="10">
                    </div>
                    <div class="form-group">
                        <label for="score2Input">Score2</label>
                        <input name="score2" type="number" class="form-control" id="score2Input" placeholder="10">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<ul class="list-group">
    <c:forEach items="${matches}" var="match">
        <li class="list-group-item">${match.getTeam1().getName()} VS ${match.getTeam2().getName()} Scores : ${match.getGoals1()} : ${match.getGoals2()}</li>
    </c:forEach>
</ul>

<nav aria-label="...">
    <ul class="pagination">
        <li class="page-item"><a class="page-link" href="match?matchListPage=${leftArrow}">&lt;</a></li>
            <c:forEach items="${matchPageNumbers}" var="page">
                <c:choose>
                    <c:when test="${page == currentMatchPage}">
                        <li class="page-item active"><a class="page-link" href="#">${page}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item"><a class="page-link" href="match?matchListPage=${page}">${page}</a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        <li class="page-item"><a class="page-link" href="match?matchListPage=${rightArrow}">&gt;</a></li>
    </ul>
</nav>
</body>
</html>
