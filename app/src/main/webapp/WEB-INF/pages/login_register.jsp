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
        <button style="background: #868e96; margin-bottom: 10px" onclick="location.href = './index';">Return to main website</button>
        <form class="register-form" method="post" action="register">
            <input type="text" id="username_register" name="username" placeholder="username"/>
            <input type="text" id="firstname_register" name="firstname"  placeholder="Firstname"/>
            <input type="text" id="lastname_register" name="lastname"  placeholder="Lastname"/>
            <input type="password" id="password_register" name="password"  placeholder="password"/>
            <input type="text" id="email_register" name="email"  placeholder="email address"/>
            <button>create</button>
            <p class="message">Already registered? <a href="./login#">Sign In</a></p>
        </form>
        <form class="login-form" method="post" action="login">
            <input type="text" id="username_login" name="username" placeholder="username"/>
            <input type="password" id="password_login" name="password" placeholder="password"/>
            <button>login</button>
            <p class="message">Not registered? <a href="./login#">Create an account</a></p>
        </form>
    </div>
</div>
<!-- partial -->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script  src="./assets/login_register/script.js"></script>

</body>
</html>