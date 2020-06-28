<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Course Discussion Forum</title>
    </head>
    <body>
        <security:authorize access="isAnonymous()">
            <i>User: Anonymous user</i><br />
            [<a href="<c:url value="/login" />">Login</a>]
            [<a href="<c:url value="/user/register" />">Register account</a>]
        </security:authorize>
            
        <security:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
            <i>User: ${username}</i>
            <c:url var="logoutUrl" value="/logout" />
            <form method="POST" action="${logoutUrl}">
                <input type="submit" value="Logout" />
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    [<a href="<c:url value="/user/list" />">Manage user accounts</a>]
                    [<a href="<c:url value="/poll/list" />">Manage forum polls</a>]
                </security:authorize>
                [<a href="<c:url value="/topic/create" />">Create message topic</a>]
            </form>
        </security:authorize>
        
        <h2>Message Topics - ${categories}</h2>
        <i>P.S. There are total ${count} message topics</i><br><br />
        <c:choose>
            <c:when test="${fn:length(topicDatabase) == 0}">
                <i>There are no message topics in the system</i><br><br />
            </c:when>
            <c:otherwise>
                <c:forEach var="topic" items="${topicDatabase}">
                    Message Topic # ${topic.id}:
                    <a href="<c:url value="/topic/view/${topic.id}" />">
                        <c:out value="${topic.title}" />
                    </a>
                    <security:authorize access="isAnonymous()">
                        (<i>user: <c:out value="${topic.userName}" />, updated: <fmt:formatDate value="${topic.timestamp}" pattern="dd-MM-yyyy HH:mm" /></i>)
                    </security:authorize>

                    <security:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                        (<i>user: <c:out value="${topic.userName}" />, updated: <fmt:formatDate value="${topic.timestamp}" pattern="dd-MM-yyyy HH:mm" /></i>)
                        <security:authorize access="hasRole('ADMIN') or principal.username=='${topic.userName}'">
                            [<a href="<c:url value="/topic/edit/${topic.id}" />">Edit</a>]
                        </security:authorize>
                        <security:authorize access="hasRole('ADMIN')">
                            [<a href="<c:url value="/topic/delete/${topic.id}" />">Delete</a>]
                        </security:authorize>
                    </security:authorize>
                    <br><br />
                </c:forEach>
            </c:otherwise>
        </c:choose>
        <br />
        <a href="<c:url value="/topic/index" />">Return to categories index</a>
    </body>
</html>
