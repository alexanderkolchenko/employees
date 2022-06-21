<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  errorPage="error_page.jsp" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<div>
    <form method="post" action="login_filter">
        <label for="login">Login</label><br>
        <input type="text" id="login"
               value="admin" name="login"/><br>
        <label for="password">Password</label><br>
        <input type="password" id="password" name="password"/><br>
        <input type="submit" value="Login"/>
    </form>
</div>

</body>
</html>
