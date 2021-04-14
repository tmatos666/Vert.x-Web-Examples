<!DOCTYPE html>

<html lang="en">
    <head>
        <title>
            Hello FreeMarker!!!! (${context.form})
        </title>
    </head>
    <body>
      <h1>Welcome FreeMarker!! (${context.form})</h1>
      
      <form id="login" action="/login" method="POST">
        <fieldset >
            <legend>Login</legend>
            <input type='hidden' name='submitted' id='submitted' value='1'/>

            <label for='username' >UserName: </label>
            <input type='text' name='username' id='username'  maxlength="50"/>

            <label for='password' >Password: </label>
            <input type='password' name='password' id='password' maxlength="50"/>

            <input type='submit' name='Submit' value='Submit' />
        </fieldset>
    </form>
      
    </body>
</html>