<%@ page import="com.chrisgward.pex2gm.YamlProcesser" %>
<%@ page import="com.chrisgward.pex2gm.PexGroups" %>
<%--
  Created by IntelliJ IDEA.
  User: 8381111
  Date: 13/01/13
  Time: 8:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    YamlProcesser processor = new YamlProcesser(request.getParameter("yaml"), PexGroups.class); %>
<%= processor.newConfig %>