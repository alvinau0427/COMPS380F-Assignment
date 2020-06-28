<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <i>User: ${username}</i>
        <c:url var="logoutUrl" value="/logout"/>
        <form method="POST" action="${logoutUrl}">
            <input type="submit" value="Logout" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
        <h2>Add comment for Message Topic #${topic.id}:<c:out value="${topic.title}" /></h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="commentForm">
            <form:input type="hidden" path="topicId" value ="${topic.id}" />
            <form:input type="hidden" path="userName" value ="${username}" />  
            <form:label path="commentdt"><b>Comment</b></form:label><br />
            <form:textarea path="commentdt" rows="5" cols="50" required="required"  />
            <br><br />
            <b>Attachments</b><br />
            <input type="file" name="attachments" multiple="multiple" /><br><br />
            <input type="submit" value="Submit" />
            <input type="reset" value="Reset" />
        </form:form>
        <br />
        <a href="<c:url value="/topic/view/${topic.id}" />">Return to categories index</a>
    </body>
</html>
