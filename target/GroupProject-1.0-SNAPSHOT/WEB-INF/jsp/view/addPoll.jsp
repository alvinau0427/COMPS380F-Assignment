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
        </form>
        <h2>Create Forum Poll</h2>
        <form:form method="POST" modelAttribute="pollForm">
            <form:label path="question"><b>Question</b></form:label><br />
            <form:input type="text" path="question" required="required" style="width:400px" />
            <br><br />
            <form:label path="answer1"><b>Answer 1</b></form:label><br />
            <form:input type="text" path="answer1" required="required" />
            <br><br />
            <form:label path="answer2"><b>Answer 2</b></form:label><br />
            <form:input type="text" path="answer2" required="required" />
            <br><br />
            <form:label path="answer3"><b>Answer 3</b></form:label><br />
            <form:input type="text" path="answer3" required="required" />
            <br><br />
            <form:label path="answer4"><b>Answer 4</b></form:label><br />
            <form:input type="text" path="answer4" required="required" />
            <br><br />
            <input type="submit" value="Submit" />
            <input type="reset" value="Reset" />
        </form:form>
        <br />
        <a href="<c:url value="/poll/list" />">Return to polls list</a>
    </body>
</html>
