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
    <a class="nav-link active" href="#">Matches</a>
    <a class="nav-link" href="/team">Teams</a>
    <a class="nav-link" href="/stadium">Stadiums</a>
</nav>

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
                    <input name="action" class="action" value="post" hidden>
                    <input name="id" class="id" hidden>
                    <div class="form-group">
                        <label for="team1formControlSelect">Team1</label>
                        <select name="team1" class="form-control team1" id="team1formControlSelect">
                            <c:forEach items="${teams}" var="team"><option value="<c:out value="${team.getId()}"/>">${team.getName()}</option></c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="team2formControlSelect">Team2</label>
                        <select name="team2" class="form-control team2" id="team2formControlSelect">
                            <c:forEach items="${teams}" var="team"><option value="<c:out value="${team.getId()}"/>">${team.getName()}</option></c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="stadiumformControlSelect">Stadium</label>
                        <select name="stadium" class="form-control stadium" id="stadiumformControlSelect">
                            <c:forEach items="${stadiums}" var="stadium"><option value="<c:out value="${stadium.getId()}"/>"> ${stadium.getName()}, ${stadium.getLocation()}</option></c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="score1Input">Score1</label>
                        <input name="score1" type="number" class="form-control score1" id="score1Input" placeholder="10">
                    </div>
                    <div class="form-group">
                        <label for="score2Input">Score2</label>
                        <input name="score2" type="number" class="form-control score2" id="score2Input" placeholder="10">
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
<span>
    <h1>Matches you entered</h1>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" style="align:right;" data-toggle="modal" data-target="#matchModal">
        Add Match
    </button>
</span>
<table style="padding: 10px;" class="table">
    <thead>
        <tr>
            <th scope="col">Team1</th>
            <th scope="col">Team2</th>
            <th scope="col">Score1</th>
            <th scope="col">Score2</th>
            <th scope="col">Stadium</th>
            <th scope="col">Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${matches}" var="match">
        <tr>
            <th>${match.getTeam1().getName()}</th>
            <td>${match.getTeam2().getName()}</td>
            <td>${match.getGoals1()}</td>
            <td>${match.getGoals2()}</td>
            <td>${match.getLocation().getName()}</td>
            <td>
                <button type="button" data-toggle="modal" data-target="#matchModal"
                        data-id="<c:out value="${match.getId()}"/>"
                        data-team1="<c:out value="${match.getTeam1().getId()}"/>"
                        data-team2="<c:out value="${match.getTeam2().getId()}"/>"
                        data-score1="<c:out value="${match.getGoals1()}"/>"
                        data-score2="<c:out value="${match.getGoals2()}"/>"
                        data-stadium="<c:out value="${match.getLocation().getId()}"/>"
                        class="btn btn-success"><i class="material-icons">create</i>
                </button>
                <form style="display:inline" method="POST" action="match">
                    <input name="action" value="delete" hidden>
                    <input name="match" value="<c:out value="${match.getId()}"/>" hidden>
                    <button type="submit" class="btn btn-danger"><i class="material-icons">clear</i></button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>

<nav style="align:center;" aria-label="...">
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

<script>
    $('#matchModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        if(button.data('id')) {
            var id = button.data('id');
            var team1 = button.data('team1');
            var team2 = button.data('team2');
            var score1 = button.data('score1');
            var score2 = button.data('score2');
            var stadium = button.data('stadium');

            var modal = $(this);
            modal.find('.modal-body input.action').val("update");
            modal.find('.modal-body input.id').val(id);
            modal.find('.modal-body input.score1').val(score1);
            modal.find('.modal-body input.score2').val(score2);
            modal.find('.modal-body select.stadium').val(stadium);
            modal.find('.modal-body select.team1').val(team1);
            modal.find('.modal-body select.team2').val(team2);
        } else {
            var modal = $(this);
            modal.find('.modal-body input.action').val("post");
        }
    })
</script>

</html>
