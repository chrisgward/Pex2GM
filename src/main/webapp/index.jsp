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
        <title>Convert2GM Converter</title>
        <link rel="stylesheet" href="/stylesheet.css"/>
        <script src="http://code.jquery.com/jquery-1.9.1.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            var _gaq = _gaq || [];
            _gaq.push(['_setAccount', 'UA-38182146-1']);
            _gaq.push(['_trackPageview']);

            (function() {
                var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
                ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
                var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
            })();

            $(document).ready(function() {
                $("select").change(function(event) {
                    window.location = "#" + $(this).val();
                    $("#pex").css("display", window.location.hash == "#pex" ? "block" : "none");
                    $("#bperms").css("display", window.location.hash == "#bperms" ? "block" : "none");
                    $("#privs").css("display", window.location.hash == "#privs" ? "block" : "none");
                });

                if(window.location.hash)
                {
                    $("#pex").css("display", window.location.hash == "#pex" ? "block" : "none");
                    $("#bperms").css("display", window.location.hash == "#bperms" ? "block" : "none");
                    $("#privs").css("display", window.location.hash == "#privs" ? "block" : "none");
                    if(window.location.hash == "#pex") {
                        $("select").val("pex");
                    } else if(window.location.hash == "#bperms") {
                        $("select").val("bperms");
                    } else  if(window.location.hash == "#privs") {
                        $("select").val("privs");
                    }
                }
            });

        </script>
    </head>
    <body>
        <div id="wrap">
            <h1>Convert2GM Converter</h1>
            <p>
                Please select a file format then follow the prompts.<br/>
                Your file will be converted to GroupManager standards. This includes users, groups and global groups.<br/>
                If you find a bug, please report it by sending me an email at <a href="mailto:chris+convert2gm@chrisgward.com">chris+convert2gm@chrisgward.com</a>.
            </p>

            <p>
                Please select your <strong>current</strong> permissions plugin<br/>
                <select name="method">
                    <option value="pex">PermissionsEx</option>
                    <option value="bperms">BPermissions</option>
                    <option value="privs">Privileges</option>
                </select>
            </p>
            <form id="pex" method="post" action="/upload/">
                Please copy and paste in your PermissionsEx permissions.yml into the box below and press submit
                <table>
                    <tr>
                        <td>
                            <textarea class="text-full" name="yaml"></textarea>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="method" value="pex"/>
                <input type="submit" value="Submit">
            </form>
            <form id="bperms" method="post" action="/upload/" style="display: none">
                Please copy and paste in your BPermissions groups.yml into the box below and press submit
                <table>
                    <tr>
                        <td>
                            <textarea class="text-full" name="yaml"></textarea>
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="method" value="bperms"/>
                <input type="submit" value="Submit">
            </form>
            <form id="privs" method="post" action="/upload/" style="display: none">
                Please copy and paste in your Privileges files into the specified boxes and press submit
                <table>
                    <tr>
                        <td><textarea class="text-half" name="groups"></textarea></td>
                        <td><textarea class="text-half" name="users"></textarea></td>
                    </tr>
                    <tr>
                        <td>groups.yml</td>
                        <td>users.yml</td>
                    </tr>
                </table>
                <input type="hidden" name="method" value="privs"/>
                <input type="submit" value="Submit">
            </form>

            <br/>
            <small><a href="https://github.com/chrisgward/Pex2GM">Source Code</a> - Written by Chris Ward</small>
        </div>
    </body>
</html>