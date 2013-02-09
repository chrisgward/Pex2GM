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
        <h1>Pex2GM Converter</h1>
        1. Paste in your PermissionsEx permissions.yml and press submit.<br/>
        2. ???<br/>
        3. Profit!
        <form method="post" action="/upload/">
            <textarea id="yaml" name="yaml"></textarea>
            <input type="submit" value="Submit">
        </form>

        <p><a href="https://github.com/chrisgward/Pex2GM">Source Code</a> - Written by Chris Ward</p>
        <p>If you are having trouble with this website, please let me know through email <a href="mailto:chris@chrisgward.com">here</a>.</p>
    </body>
</html>