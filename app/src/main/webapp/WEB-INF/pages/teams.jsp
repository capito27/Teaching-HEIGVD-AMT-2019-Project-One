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
    <title>Teams</title>
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
<c:if test="${error != null}">
    <p style="color: red;">${error}</p>
</c:if>
<c:if test="${confirmation != null}">
    <p style="color: green;">${confirmation}</p>
</c:if>
<form method="POST" action="team" id="teamForm">
<!-- Modal -->
<div class="modal fade" style="align: right;" id="teamModal" tabindex="-1" role="dialog" aria-labelledby="teamModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="teamModalLabel">Add team</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                    <input name="action" class="action" value="post" hidden>
                    <input name="id" class="id" hidden>
                    <div class="form-group">
                        <label for="nameTeam">Name</label>
                        <input name="name" type="text" class="form-control name" id="nameTeam" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="countryTeam">Country</label>
                        <input name="country" type="text" class="form-control country" id="countryTeam" placeholder="Switzerland">
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</form>
<span>
    <h1>Teams you entered</h1>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" style="align:right;" data-toggle="modal" data-target="#teamModal">
        Add team
    </button>
</span>
<table style="padding: 10px;" class="table">
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Country</th>
            <th scope="col">Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${teams}" var="team">
        <tr>
            <th>${team.getName()}</th>
            <td>${team.getCountry()}</td>
            <td>
                <button type="button" data-toggle="modal" data-target="#teamModal"
                        data-id="<c:out value="${team.getId()}"/>"
                        data-name="<c:out value="${team.getName()}"/>"
                        data-country="<c:out value="${team.getCountry()}"/>"
                        class="btn btn-success"><i class="material-icons">create</i>
                </button>
                <form style="display:inline" method="POST" action="team">
                    <input name="action" value="delete" hidden>
                    <input name="team" value="<c:out value="${team.getId()}"/>" hidden>
                    <button type="submit" class="btn btn-danger"><i class="material-icons">clear</i></button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>

<script>
    function form_submit() {
        document.getElementById("teamForm").submit();
    }

    $('#teamModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        if(button.data('id')) {
            var id = button.data('id');
            var nameTeam = button.data('name');
            var country = button.data('country');

            var modal = $(this);
            modal.find('.modal-body input.action').val("update");
            modal.find('.modal-body input.id').val(id);
            modal.find('.modal-body input.name').val(nameTeam);
            modal.find('.modal-body input.country').val(country);
        } else {
            var modal = $(this);
            modal.find('.modal-body input.action').val("post");
        }
    })
</script>
</body>
</html>
