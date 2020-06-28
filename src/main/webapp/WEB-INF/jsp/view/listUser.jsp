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
            [<a href="<c:url value="/user/create" />">Create user</a>]
        </form>
        
        <h2>Users</h2>
        <c:choose>
            <c:when test="${fn:length(topicUsers) == 0}">
                <i>There are no users in the system.</i>
            </c:when>
            <c:otherwise>
                <i>P.S. There are total ${count} users</i><br />
                <table cellpadding="10">
                    <tr>
                        <th>Username</th>
                        <th>Roles</th>
                        <th>Update User</th>
                        <th>Delete User</th>
                    </tr>
                    <c:forEach items="${topicUsers}" var="user">
                        <tr>
                            <td>${user.username}</td>
                            <td>
                                <c:forEach items="${user.roles}" var="role" varStatus="status">
                                    <c:if test="${!status.first}">, </c:if>
                                    ${role.role}
                                </c:forEach>
                            </td>
                            <td align="center">
                                [<a href="<c:url value="/user/edit/${user.username}" />">Update</a>]
                            </td>
                            <td align="center">
                                [<a href="<c:url value="/user/delete/${user.username}" />">Delete</a>]
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        <br />
        <a href="<c:url value="/topic/index" />">Return to categories index</a>
    </body>
</html>
