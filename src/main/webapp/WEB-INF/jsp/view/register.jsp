<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <h2>User Registration</h2>
        <form:form method="POST" modelAttribute="topicUser">
            <form:label path="username"><b>User name</b></form:label><br />
            <form:input type="text" path="username" required="required" /><br><br />
            <form:label path="password"><b>Password</b></form:label><br />
            <form:input type="text" path="password" required="required" /><br><br />
            <form:input type="hidden" path="roles" value="ROLE_USER" />
            <br />
            <input type="submit" value="Submit" id="sumbit" />
            <input type="reset" value="Reset" />
        </form:form>
        <br />
        <a href="<c:url value="/login" />">Return to login page</a>
    </body>
</html>
