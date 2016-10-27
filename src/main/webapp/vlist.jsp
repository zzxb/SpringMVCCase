<%@page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:forEach items="${lxrlist}" var="lxr">
    <c:out value="${lxr.lxrname}" />
</c:forEach>
</body>
</html>
