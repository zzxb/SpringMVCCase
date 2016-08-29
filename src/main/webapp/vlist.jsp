<%@ page import="java.util.List" %>
<%@ page import="me.zzxb.pojo.Txls" %><%--
  Created by IntelliJ IDEA.
  User: zzxb
  Date: 16/8/29
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    List<Txls> data = (List<Txls>)request.getAttribute("lxrlist");
    System.out.println(data.size());
%>
</body>
</html>
