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
    <title>Stadiums</title>
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
</nav>
<form method="POST" action="stadium">
<!-- Modal -->
<div class="modal fade" style="align: right;" id="stadiumModal" tabindex="-1" role="dialog" aria-labelledby="stadiumModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="stadiumModalLabel">Add stadium</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                    <input name="action" class="action" value="post" hidden>
                    <input name="id" class="id" hidden>
                    <div class="form-group">
                        <label for="namestadium">Name</label>
                        <input name="name" type="text" class="form-control name" id="namestadium" placeholder="Name">
                    </div>
                    <div class="form-group">
                        <label for="locationstadium">Location</label>
                        <input name="location" type="text" class="form-control location" id="locationstadium" placeholder="Switzerland">
                    </div>
                    <div class="form-group">
                        <label for="viewersPlacesStadium">Viewers places</label>
                        <input name="viewers" type="number" class="form-control viewers" id="viewersPlacesStadium" placeholder="25000">
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
    <h1>Stadiums</h1>
    <!-- Button trigger modal -->
    <button type="button" class="btn btn-primary" style="align:right;" data-toggle="modal" data-target="#stadiumModal">
        Add Stadium
    </button>
</span>
<table style="padding: 10px;" class="table">
    <thead>
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Location</th>
            <th scope="col">Viewers places</th>
            <th scope="col">Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach items="${stadiums}" var="stadium">
        <tr>
            <th>${stadium.getName()}</th>
            <td>${stadium.getLocation()}</td>
            <td>${stadium.getViewerPlaces()}</td>
            <td>
                <button type="button" data-toggle="modal" data-target="#stadiumModal"
                        data-id="<c:out value="${stadium.getId()}"/>"
                        data-name="<c:out value="${stadium.getName()}"/>"
                        data-location="<c:out value="${stadium.getLocation()}"/>"
                        data-viewers="<c:out value="${stadium.getViewerPlaces()}"/>"
                        class="btn btn-success"><i class="material-icons">create</i>
                </button>
                <form style="display:inline" method="POST" action="stadium">
                    <input name="action" value="delete" hidden>
                    <input name="stadium" value="<c:out value="${stadium.getId()}"/>" hidden>
                    <button type="submit" class="btn btn-danger"><i class="material-icons">clear</i></button>
                </form>
            </td>
        </tr>
        </c:forEach>
    </tbody>
</table>
</body>

<script>

    function form_submit() {
        document.getElementById("stadiumForm").submit();
    }

    $('#stadiumModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget); // Button that triggered the modal
        if(button.data('id')) {
            var id = button.data('id');
            var name = button.data('name');
            var location = button.data('location');
            var viewers = button.data('viewers');

            var modal = $(this);
            modal.find('.modal-body input.action').val("update");
            modal.find('.modal-body input.id').val(id);
            modal.find('.modal-body input.name').val(name);
            modal.find('.modal-body input.location').val(location);
            modal.find('.modal-body input.viewers').val(viewers);
        } else {
            var modal = $(this);
            modal.find('.modal-body input.action').val("post");
        }
    })
</script>

</html>
