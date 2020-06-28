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
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            <security:authorize access="hasRole('ROLE_ADMIN')">
                [<a href="<c:url value="/topic/delete/${topic.id}" />">Delete</a>]
            </security:authorize>
        </form>
        <h2>Edit Message Topic #${topic.id}: <c:out value="${topic.title}" /></h2>
        <form:form method="POST" enctype="multipart/form-data" modelAttribute="topicForm">
            <form:label path="username"><b>Username</b></form:label><br />
            <form:input type="text" path="username"  disabled="true" />
            <br><br />
            <form:label path="category"><b>Category</b></form:label><br/>
            <security:authorize access="principal.username=='${topic.userName}'">
                <form:input type="text" path="category" disabled="true" />
                &nbsp;
            </security:authorize>
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <form:select path="category">
                    <form:option value="lecture" label="Lecture" />
                    <form:option value="laboratory" label="Laboratory" />
                    <form:option value="others" label="Others" />
                </form:select>
            </security:authorize>
            <br><br />
            <form:label path="title"><b>Title</b></form:label><br />
            <form:input type="text" path="title" required="required" />
            <br><br />
            <form:label path="content"><b>Content</b></form:label><br />
            <form:textarea path="content" rows="5" cols="50" required="required" />
            <br><br />
            <c:if test="${fn:length(topic.attachments) > 0}">
                <b>Attachments</b><br />
                <ul>
                    <c:forEach items="${topic.attachments}" var="attachment">
                        <li>
                            <c:out value="${attachment.name}" />
                            [<a href="<c:url value="/topic/${topic.id}/delete/${attachment.name}" />">Delete</a>]
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <b>Add attachments</b><br />
            <input type="file" name="attachments" multiple="multiple" /><br><br />
            <input type="submit" value="Save" />
        </form:form>
        <br />
        <a href="<c:url value="/topic/view/${topic.id}" />">Return to topic view</a>
    </body>
</html>