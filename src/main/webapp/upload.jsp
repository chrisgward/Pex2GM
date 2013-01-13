<%@ page import="com.chrisgward.pex2gm.YamlProcesser" %>
<%--
  Created by IntelliJ IDEA.
  User: 8381111
  Date: 13/01/13
  Time: 8:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    YamlProcesser processor = new YamlProcesser(request.getParameter("yaml")); %>
<%= processor.getGroups().getGroups().toString() %>
<html>
<head>
    <title>Processing...</title>
</head>
<body>
    Your file is being processed. Please wait.

</body>
</html>