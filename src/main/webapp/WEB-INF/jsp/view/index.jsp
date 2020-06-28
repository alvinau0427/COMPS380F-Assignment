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

        <h2>Course Discussion Forum Poll</h2>
        <c:choose>
            <c:when test="${fn:length(pollDatabase) == 0}">
                <i>There are no poll in the system</i><br><br />
            </c:when>
            <c:otherwise>
                <b>Forum Poll # ${poll.id}: ${poll.question}</b> ( <i>updated: <fmt:formatDate value="${poll.timestamp}" pattern="dd-MM-yyyy HH:mm" /></i> )
                <br><br />
                <security:authorize access="isAnonymous()">
                    Choice 1: ${poll.answer1}<br />
                    Choice 2: ${poll.answer2}<br />
                    Choice 3: ${poll.answer3}<br />
                    Choice 4: ${poll.answer4}<br />
                </security:authorize>

                <security:authorize access="hasAnyRole('ROLE_USER','ROLE_ADMIN')">
                    <c:if test="${count >= 4}">
                            Choice 1: ${poll.answer1}<br />
                            Choice 2: ${poll.answer2}<br />
                            Choice 3: ${poll.answer3}<br />
                            Choice 4: ${poll.answer4}<br />
                    </c:if>
                            
                    <c:if test="${count < 4}">     
                        <c:if test="${check == []}">
                            <form:form method="POST" modelAttribute="pollForm">
                                <form:input type="hidden" path="pollId" value ="${poll.id}" />
                                <form:input type="hidden" path="question" value="${poll.question}" />
                                <input type="radio" id="answer1" name="choice" value="${poll.answer1}" checked="checked" />
                                <label for="answe1">${poll.answer1}</label><br />
                                <input type="radio" id="answer2" name="choice" value="${poll.answer2}" />
                                <label for="answe1">${poll.answer2}</label><br />
                                <input type="radio" id="answer3" name="choice" value="${poll.answer3}" />
                                <label for="answe1">${poll.answer3}</label><br />
                                <input type="radio" id="answer4" name="choice" value="${poll.answer4}" />
                                <label for="answe1">${poll.answer4}</label>
                                <br><br />
                                <input type="submit" value="Submit" id="sumbit" />
                            </form:form>
                        </c:if>
                        <c:if test="${check != []}">
                            Choice 1: ${poll.answer1}<br />
                            Choice 2: ${poll.answer2}<br />
                            Choice 3: ${poll.answer3}<br />
                            Choice 4: ${poll.answer4}<br />
                        </c:if>
                    </c:if>
                </security:authorize>
                <br />
                <i>P.S. There are total ${count} vote of this question ( Choice 1: ${answer1Count}, Choice 2: ${answer2Count}, Choice 3: ${answer3Count}, Choice 4: ${answer4Count} )</i>    
            </c:otherwise>
        </c:choose>

        <h2>Course Discussion Forum Categories</h2>
        <ul>
            <li><a href="<c:url value="/topic/lecture" />">Lecture</a></li>
            <br />
            <li><a href="<c:url value="/topic/laboratory" />">Laboratory</a></li>
            <br />
            <li><a href="<c:url value="/topic/others" />">Others</a></li>
        </ul>
    </body>
</html>
