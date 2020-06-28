<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <i>User: ${username}</i>
        <c:url var="logoutUrl" value="/logout" />
        <form method="POST" action="${logoutUrl}">
            <input type="submit" value="Logout" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        </form>
        <h2>Create user</h2>
        <form:form method="POST" modelAttribute="topicUserForm">
            <form:label path="username"><b>Username</b></form:label><br />
            <form:input type="text" path="username" required="required" />
            <br><br />
            <form:label path="password"><b>Password</b></form:label><br />
            <form:input type="text" path="password" required="required" />
            <br><br />
            <form:label path="roles"><b>Roles</b></form:label><br />
            <form:checkbox path="roles" value="ROLE_USER" label="ROLE_USER" checked="checked" required="required" />
            <form:checkbox path="roles" value="ROLE_ADMIN" label="ROLE_ADMIN" />
            <br><br />
            <input type="submit" value="Submit" id="sumbit" />
            <input type="reset" value="Reset" />
        </form:form>
        <br />
        <a href="<c:url value="/user/list" />">Return to users list</a>
    </body>
</html>


