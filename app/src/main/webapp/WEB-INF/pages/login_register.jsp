<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Login Form</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="./assets/login_register/style.css">
</head>
<body>
<!-- partial:index.partial.html -->
<div class="login-page">
    <div class="form">
        <button style="background: #868e96; margin-bottom: 10px" onclick="location.href = './index';">Return to main website</button>
        <form class="register-form" <c:if test="${login==true}">style="display: none;"</c:if> method="post" action="register">
            <input type="text" id="username_register" name="username" value="<c:out value="${username}"/>" placeholder="username"/>
            <input type="text" id="firstname_register" name="firstname" value="<c:out value="${firstname}"/>" placeholder="Firstname"/>
            <input type="text" id="lastname_register" name="lastname" value="<c:out value="${lastname}"/>" placeholder="Lastname"/>
            <input type="password" id="password_register" name="password" placeholder="password"/>
            <input type="text" id="email_register" name="email" value="<c:out value="${email}"/>" placeholder="email address"/>
            <button>create</button>
            <p class="message">Already registered? <a href="./login#">Sign In</a></p>
        </form>
        <form class="login-form" <c:if test="${register==true}">style="display: none;"</c:if> method="post" action="login">
            <input type="text" id="username_login" name="username" value="<c:out value="${username}"/>" placeholder="username"/>
            <input type="password" id="password_login" name="password" placeholder="password"/>
            <button>login</button>
            <p class="message">Not registered? <a href="./login#">Create an account</a></p>
        </form>
        <p class="error">${error}</p>
    </div>
</div>
<!-- partial -->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script src="./assets/login_register/script.js"></script>

</body>
</html>