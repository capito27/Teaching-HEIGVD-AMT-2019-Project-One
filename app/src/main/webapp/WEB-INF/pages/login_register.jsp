<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Flat HTML5/CSS3 Login Form</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="./assets/login_register/style.css">
</head>
<body>
<!-- partial:index.partial.html -->
<div class="login-page">
    <div class="form">
        <form class="register-form">
            <input type="text" placeholder="name"/>
            <input type="password" placeholder="password"/>
            <input type="text" placeholder="email address"/>
            <button>create</button>
            <p class="message">Already registered? <a href="./registration#">Sign In</a></p>
        </form>
        <form class="login-form">
            <input type="text" placeholder="username"/>
            <input type="password" placeholder="password"/>
            <button>login</button>
            <p class="message">Not registered? <a href="./registration#">Create an account</a></p>
        </form>
    </div>
</div>
<!-- partial -->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script  src="./assets/login_register/script.js"></script>

</body>
</html>