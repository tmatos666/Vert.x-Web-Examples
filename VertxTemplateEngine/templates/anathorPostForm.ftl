<!DOCTYPE html>
<html lang="en">
    <head>
        <title>
            Hello FreeMarker!!!!
        </title>
        <script type="text/javascript">
            <#include "jscript.js">
        </script>
    </head>
    <body>
        <h1>Welcome FreeMarker!!</h1>
        <button type="button" onclick="alertFunction()">Click Me!</button>

        <form id="login" action="/login" method="POST">
            <fieldset >
                <legend>Login</legend>
                <label for='username' >UserName: </label>
                <input type='text' name='username' id='username'  maxlength="50"/>
                <input type='submit' name='Submit' value='Submit' />
            </fieldset>
        </form>
    </body>
</html>