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
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <security:authorize access="hasRole('ROLE_ADMIN') or principal.username=='${topic.userName}'">
                    [<a href="<c:url value="/topic/edit/${topic.id}" />">Edit</a>]
                    [<a href="<c:url value="/topic/comment/${topic.id}" />">Add comment</a>]
                </security:authorize>
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    [<a href="<c:url value="/topic/delete/${topic.id}" />">Delete</a>]
                </security:authorize>
            </form>
        </security:authorize>

        <h2>Message Topic #${topic.id}: <c:out value="${topic.title}" /></h2>
        <b>Category: </b><c:out value="${topic.category}" /><br />
        <b>User: </b><c:out value="${topic.userName}" /><i> ( updated: <fmt:formatDate value="${topic.timestamp}" pattern="dd-MM-yyyy HH:mm" /> )</i>
        <br><br />
        <b>Content</b><br />
        <fieldset style="width:500px">
            <c:out value="${topic.content}" />
        </fieldset>
        <br />
        
        <b>Attachments</b><br />
        <c:if test="${fn:length(topic.attachments) == 0}">
            <i>None</i><br><br />
        </c:if>
        <c:if test="${fn:length(topic.attachments) > 0}">
            <c:forEach items="${topic.attachments}" var="attachment" varStatus="status">
                <c:if test="${!status.first}">, </c:if>
                <security:authorize access="isAnonymous()">
                    <c:out value="${attachment.name}" />
                </security:authorize>

                <security:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                    <a href="<c:url value="/topic/${topic.id}/attachment/${attachment.name}" />">
                        <c:out value="${attachment.name}" />
                    </a>
                </security:authorize>
            </c:forEach>
            <br/>
        </c:if>
        <br />
        
        <b>Total comment: </b><c:out value="${fn:length(comment)}"/><br><br />
        <b>Comment</b><br />    
        <c:if test="${fn:length(comment) == 0}">
            <i>None</i><br><br />
        </c:if>
        <c:if test="${fn:length(comment) > 0}">
            <c:set var="count" value="0" scope="page" />
            <c:forEach var="comment" items="${comment}" >        
            <c:set var="count" value="${count + 1}" scope="page"/>
            #<c:out value = "${count}"/>: <c:out value="${comment.userName}" /><i> ( time: <fmt:formatDate value="${comment.timestamp}" pattern="dd-MM-yyyy HH:mm" /> )</i>
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    [<a href="<c:url value="/topic/delete/comment/${topic.id}/${comment.id}" />">Delete</a>]
                </security:authorize>
                    <fieldset style="width:500px"><c:out value="${comment.commentdt}" /></fieldset><br />
                    <b>Attachments</b><br />
                        <c:if test="${fn:length(comment.attachments) == 0}">
                            <i>None</i><br><br />
                        </c:if>
                        <c:if test="${fn:length(comment.attachments) > 0}">
                            <c:forEach items="${comment.attachments}" var="attachment" varStatus="status">
                                <c:if test="${!status.first}">, </c:if>
                                <security:authorize access="isAnonymous()">
                                    <c:out value="${attachment.name}" />
                                </security:authorize>

                                <security:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                                    <a href="<c:url value="/topic/comment/${comment.id}/attachment/${attachment.name}" />">
                                        <c:out value="${attachment.name}" />
                                    </a>
                                </security:authorize>
                            </c:forEach>
                            <br/>
                        </c:if>
                    <br />
            </c:forEach>
        </c:if>
        <security:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
            <a href="<c:url value="/topic/comment/${topic.id}" />">Add comment</a>
        </security:authorize>
        <br><br/>
        
        <a href="<c:url value="/topic/${topic.category}" />">Return to topics list</a>
    </body>
</html>
