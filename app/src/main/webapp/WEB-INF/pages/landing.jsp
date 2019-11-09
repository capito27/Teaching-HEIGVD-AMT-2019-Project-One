<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Knights - Free Bootstrap 4 Template by Colorlib</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <link href="https://fonts.googleapis.com/css?family=Nunito+Sans:200,300,400,600,700,800,900" rel="stylesheet">

    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="./assets/knights/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="./assets/knights/css/animate.css">

    <link rel="stylesheet" href="./assets/knights/css/owl.carousel.min.css">
    <link rel="stylesheet" href="./assets/knights/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="./assets/knights/css/magnific-popup.css">

    <link rel="stylesheet" href="./assets/knights/css/aos.css">

    <link rel="stylesheet" href="./assets/knights/css/ionicons.min.css">

    <link rel="stylesheet" href="./assets/knights/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="./assets/knights/css/jquery.timepicker.css">


    <link rel="stylesheet" href="./assets/knights/css/flaticon.css">
    <link rel="stylesheet" href="./assets/knights/css/icomoon.css">
    <link rel="stylesheet" href="./assets/knights/css/style.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
    <div class="container">
        <a class="navbar-brand" href="index"><span>Knights</span></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav"
                aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="oi oi-menu"></span> Menu
        </button>

        <div class="collapse navbar-collapse" id="ftco-nav">
            <ul class="navbar-nav ml-auto">
                <c:if test="${user != null && user.isAdmin()}">
                    <li class="nav-item active"><a href="match" class="nav-link">Teams</a></li>
                    <li class="nav-item active"><a href="match" class="nav-link">Stadiums</a></li>
                </c:if>
                <c:if test="${user != null}"> <li class="nav-item active"><a href="match" class="nav-link">Games</a></li></c:if>
                <li class="nav-item cta">
                <c:choose>
                    <c:when test="${user != null}">
                        <a href="logout" class="nav-link">Logout</a>
                    </c:when>
                    <c:otherwise>
                        <a href="login" class="nav-link">Login / Register</a>
                    </c:otherwise>
                </c:choose>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!-- END nav -->

<section class="hero-wrap hero-wrap-2" style="background-image: url('./assets/knights/images/bg_2.jpg');"
         data-stellar-background-ratio="0.5">
    <div class="overlay"></div>
    <div class="container">
        <div class="row no-gutters slider-text align-items-end justify-content-center">
            <div class="col-md-9 ftco-animate pb-5 text-center">
                <h1 class="mb-3 bread">Games</h1>
                <p class="breadcrumbs"><span>Games <i class="ion-ios-arrow-forward"></i></span></p>
            </div>
        </div>
    </div>
</section>

<section class="ftco-section">
    <div class="container">
        <div class="row">
            <div>
                <div class="heading-section ftco-animate">
                    <span class="subheading">Game Report</span>
                    <h2 class="mb-4">Football Game Reports 2018</h2>
                </div>
                <c:forEach items="${matches}" var="match">
                    <div class="scoreboard mb-5 mb-lg-3">
                        <div class="divider text-center"><span>Tue. Feb 21, 2019; FIFA Champions League</span></div>
                        <div class="d-sm-flex mb-4">
                            <div class="sport-team d-flex align-items-center">
                                <div class="img logo"
                                     style="background-image: url(./assets/knights/images/team-<%=(int)(Math.random() * 6) + 1%>.jpg);"></div>
                                <div class="text-center px-1 px-md-3 desc">
                                    <c:choose>
                                        <c:when test="${match.getGoals1() > match.getGoals2()}">
                                            <h3 class="score win">
                                        </c:when>
                                        <c:otherwise>
                                            <h3 class="score lost">
                                        </c:otherwise>
                                    </c:choose>
                                    <span>${match.getGoals1()}</span></h3>
                                    <h4 class="team-name">${match.getTeam1().getName()}</h4>
                                </div>
                            </div>
                            <div class="sport-team d-flex align-items-center">
                                <div class="img logo order-sm-last"
                                     style="background-image: url(./assets/knights/images/team-<%=(int)(Math.random() * 6) + 1%>.jpg);"></div>
                                <div class="text-center px-1 px-md-3 desc">
                                    <c:choose>
                                        <c:when test="${match.getGoals1() < match.getGoals2()}">
                                            <h3 class="score win">
                                        </c:when>
                                        <c:otherwise>
                                            <h3 class="score lost">
                                        </c:otherwise>
                                        </c:choose>
                                        <span>${match.getGoals2()}</span></h3>
                                        <h4 class="team-name">${match.getTeam2().getName()}</h4>
                                </div>
                            </div>
                        </div>
                        <div class="text-center">
                            <p><a href="index#" class="btn btn-primary">More Details</a></p>
                        </div>
                    </div>
                </c:forEach>
                <div class="row mt-5">
                    <div class="col text-center">
                        <div class="block-27">
                            <ul>
                                <li><a href="index?matchListPage=${leftArrow}">&lt;</a></li>
                                <c:forEach items="${matchPageNumbers}" var="page">
                                    <c:choose>
                                        <c:when test="${page == currentMatchPage}">
                                            <li class="active"><span>${page}</span></li>
                                        </c:when>
                                        <c:otherwise>
                                            <li><a href="index?matchListPage=${page}">${page}</a></li>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                                <li><a href="index?matchListPage=${rightArrow}">&gt;</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<footer class="ftco-footer ftco-footer-2 ftco-section">
    <div class="container">
        <div class="row">
            <div class="col-md-12 text-center">

                <p><!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                    Copyright &copy;<script>document.write(new Date().getFullYear());</script>
                    All rights reserved | This template is made with <i class="icon-heart color-danger"
                                                                        aria-hidden="true"></i> by <a
                            href="https://colorlib.com" target="_blank">Colorlib</a>
                    <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. --></p>
            </div>
        </div>
    </div>
</footer>


<!-- loader -->
<div id="ftco-loader" class="show fullscreen">
    <svg class="circular" width="48px" height="48px">
        <circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee"/>
        <circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10"
                stroke="#F96D00"/>
    </svg>
</div>


<script src="./assets/knights/js/jquery.min.js"></script>
<script src="./assets/knights/js/jquery-migrate-3.0.1.min.js"></script>
<script src="./assets/knights/js/popper.min.js"></script>
<script src="./assets/knights/js/bootstrap.min.js"></script>
<script src="./assets/knights/js/jquery.easing.1.3.js"></script>
<script src="./assets/knights/js/jquery.waypoints.min.js"></script>
<script src="./assets/knights/js/jquery.stellar.min.js"></script>
<script src="./assets/knights/js/owl.carousel.min.js"></script>
<script src="./assets/knights/js/jquery.magnific-popup.min.js"></script>
<script src="./assets/knights/js/aos.js"></script>
<script src="./assets/knights/js/jquery.animateNumber.min.js"></script>
<script src="./assets/knights/js/bootstrap-datepicker.js"></script>
<script src="./assets/knights/js/scrollax.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
<script src="./assets/knights/js/google-map.js"></script>
<script src="./assets/knights/js/main.js"></script>

</body>
</html>
</html>