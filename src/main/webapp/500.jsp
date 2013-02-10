<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% if(request.getAttribute("javax.servlet.error.exception") == null)
    response.sendRedirect("/");
Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
    boolean tab = t.getMessage().contains("\\t");
    %>
<html>
<head>
    <title>Error!</title>
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
        <h1>Well... this is awkward (HTTP 500)</h1>
        <% if (tab) { %>
            <p>Your YAML file could not be parsed by Pex2GM. This is caused by the use of tabs in your YML file.<br/>
            Please use <a href="http://yaml-online-parser.appspot.com/">a yaml parser</a> to find and repair the problem, then try again.<br/>
            Details on where the error is can be found in the error stack below.</p>

            <p>Want to try again?
            <form method="post" action="/upload/">
                <textarea id="yaml" name="yaml" style="height: 50px"><%= request.getParameter("yaml")%></textarea>
                <input type="submit" value="Submit">
            </form> </p>

            <p>If you are getting this error regularly or do not understand what it means, please send an email with the error stack below and your yaml file to <a href="mailto:chris+pex2gm@chrisgward.com">chris+pex2gm@chrisgward.com</a> and I will get back to you within the next 2-3 days.</p>
            </p>
            <p>&nbsp</p>
            <h2>Error Stack:</h2>
            <%StringWriter writer = new StringWriter();
                t.printStackTrace(new PrintWriter(writer));%>
            <pre><%= t.getClass().getName() %> was thrown <%= writer %></pre>
        <% } else { %>
            <p>Congratulations, you broke Pex2GM :D This is usually caused by malformed YML code. Please make sure that your code is valid using <a href="http://yaml-online-parser.appspot.com/">a yaml parser</a> and try again. Also, make sure that you selected the correct permissions input type next to the submit button.<br/>
            <h2>Error Message:</h2>
            <pre><%= t.getMessage() %></pre></p>
        <p>Want to try again?
        <form method="post" action="/upload/">
            <textarea id="yaml" name="yaml" style="height: 50px"><%= request.getParameter("yaml")%></textarea>
            <input type="submit" value="Submit">
        </form> </p>

        <p>If you are getting this error regularly or do not understand what it means, please send an email with the error stack below and your yaml file to <a href="mailto:chris+pex2gm@chrisgward.com">chris+pex2gm@chrisgward.com</a> and I will get back to you within the next 2-3 days.</p>
        </p>
        <p>&nbsp</p>
        <h2>Error Stack:</h2>
        <%StringWriter writer = new StringWriter();
            t.printStackTrace(new PrintWriter(writer));%>
            <pre><%= t.getClass().getName() %> was thrown <%= writer %></pre>
        <% } %>
    </div>
</body>
</html>