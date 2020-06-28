<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <h2>Login</h2>
        <c:if test="${param.error != null}">
            <p><i>Please enter the correct user name and password!</i></p>
        </c:if>
        <c:if test="${param.logout != null}">
            <p><i>Logout successful</i></p>
        </c:if>
        <form method="POST" action="login">
            <label for="username"><b>Username</b></label><br />
            <input type="text" id="username" name="username" required="required" />
            <br><br />
            <label for="password"><b>Password</b></label><br />
            <input type="password" id="password" name="password" required="required" />
            <br><br />
            <input type="checkbox" id="remember-me" name="remember-me" label="Remember me" />
            <label for="remember-me">Remember-me</label>
            <br><br />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <input type="submit" value="Login" />
        </form>
        <br />
        <a href="<c:url value="/user/register" />">Register account</a><br><br />
        <a href="<c:url value="/topic/index" />">Click here for the unregistered users</a>
    </body>
</html>
