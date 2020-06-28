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
        <h2>Create Message Topic</h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="topicForm">
            <form:label path="category"><b>Category</b></form:label><br />
            <form:select path="category">
                <form:option value="lecture" label="Lecture" />
                <form:option value="laboratory" label="Laboratory" />
                <form:option value="others" label="Others" />
            </form:select>
            <br><br />
            <form:label path="title"><b>Title</b></form:label><br />
            <form:input type="text" path="title" required="required" />
            <br><br />
            <form:label path="content"><b>Content</b></form:label><br />
            <form:textarea path="content" rows="5" cols="50" required="required" />
            <br><br />
            <b>Attachments</b><br />
            <input type="file" name="attachments" multiple="multiple" /><br><br />
            <input type="submit" value="Submit" />
            <input type="reset" value="Reset" />
        </form:form>
        <br />
        <a href="<c:url value="/topic/index" />">Return to categories index</a>
    </body>
</html>
