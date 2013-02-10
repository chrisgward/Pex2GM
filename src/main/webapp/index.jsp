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
        <script type="text/javascript">

            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-38182146-1']);
            _gaq.push(['_trackPageview']);

            (function() {
                var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
            })();

        </script>
    </head>
    <body>
        <div id="wrap">
            <h1>Pex2GM Converter</h1>
            Simply paste in your PermissionsEx permissions.yml file and press submit.<br/>
            Your file will be converted to GroupManager standards. This includes users, groups and global groups.<br/>
            If you find a bug, please report it by either using the button on the error page or by opening a ticket on <a href="https://github.com/chrisgward/Pex2GM">GitHub</a>.<br/>
            <br/>
            <form method="post" action="/upload/">
                <textarea id="yaml" name="yaml"></textarea>
                <input type="submit" value="Submit">
            </form>
            <br/>
            <small><a href="https://github.com/chrisgward/Pex2GM">Source Code</a> - Written by Chris Ward</small>
        </div>
    </body>
</html>