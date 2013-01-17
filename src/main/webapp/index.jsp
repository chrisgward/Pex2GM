<%--
  Created by IntelliJ IDEA.
  User: 8381111
  Date: 13/01/13
  Time: 7:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Pex2GM Converter</title>
        <link rel="stylesheet" href="/stylesheet.css"/>
    </head>
    <body>
        <h1>Pex2GM Converter</h1>
        1. Paste in your PermissionsEx permissions.yml and press submit.<br/>
        2. ???<br/>
        3. Profit!
        <form method="post" action="/upload/">
            <textarea id="yaml" name="yaml"></textarea>
            <input type="submit">
        </form>
    </body>
</html>