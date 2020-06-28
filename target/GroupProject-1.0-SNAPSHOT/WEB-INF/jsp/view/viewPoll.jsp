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
            [<a href="<c:url value="/poll/edit/${poll.id}" />">Edit</a>]
            [<a href="<c:url value="/poll/delete/${poll.id}" />">Delete</a>]
        </form>
        <h2>Forum Poll #${poll.id}: <c:out value="${poll.question}" /></h2>
        <b>User: </b><c:out value="${poll.userName}" /><i> ( updated: <fmt:formatDate value="${poll.timestamp}" pattern="dd-MM-yyyy HH:mm" /> )</i>
        <br><br />
        <b>Question: </b>${poll.question}
        <br><br />
        <b>Answer 1: </b>${poll.answer1}
        <br><br />
        <b>Answer 2: </b>${poll.answer2}
        <br><br />
        <b>Answer 3: </b>${poll.answer3}
        <br><br />
        <b>Answer 4: </b>${poll.answer4}
        <br><br />
        <b>Response: </b>
        <br><br />
        <c:choose>
            <c:when test="${count == 0}">
                <i>There are no response for this poll</i>
            </c:when>
            <c:otherwise>
                <i>There are total ${count} vote of this question ( Choice 1: ${answer1Count}, Choice 2: ${answer2Count}, Choice 3: ${answer3Count}, Choice 4: ${answer4Count} )</i>
                <br><br />
                <c:forEach var="response" items="${responseDatabase}">
                    Response # ${response.id}, username: ${response.userName}, choice: ${response.choice}, created: <fmt:formatDate value="${response.timestamp}" pattern="dd-MM-yyyy HH:mm" />
                    <br><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br><br />
        <a href="<c:url value="/poll/list" />">Return to polls list</a>
    </body>
</html>
