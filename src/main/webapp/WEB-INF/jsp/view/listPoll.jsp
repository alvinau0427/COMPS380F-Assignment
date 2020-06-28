<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <i>User: ${username}</i>
        <c:url var="logoutUrl" value="/logout" />
        <form method="POST" action="${logoutUrl}">
            <input type="submit" value="Logout" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            [<a href="<c:url value="/poll/create" />">Create fourm poll</a>]
        </form>
        <h2>Fourm Polls</h2>
        <i>P.S. There are total ${count} polls</i><br><br />
        <c:choose>
            <c:when test="${fn:length(pollDatabase) == 0}">
                <i>There are no polls in the system</i><br><br />
            </c:when>
            <c:otherwise>
                <c:forEach var="poll" items="${pollDatabase}">
                    Forum Poll # ${poll.id}:
                    <a href="<c:url value="/poll/view/${poll.id}" />"><c:out value="${poll.question}" /></a>
                    (<i>created by: <c:out value="${poll.userName}" />, updated: <fmt:formatDate value="${poll.timestamp}" pattern="dd-MM-yyyy HH:mm" /></i>)
                    [<a href="<c:url value="/poll/edit/${poll.id}" />">Edit</a>]
                    [<a href="<c:url value="/poll/delete/${poll.id}" />">Delete</a>]
                    <br><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br />
        <a href="<c:url value="/topic/index" />">Return to categories index</a>
    </body>
</html>
