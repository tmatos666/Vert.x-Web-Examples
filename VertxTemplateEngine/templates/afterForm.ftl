<!DOCTYPE html>

<html lang="en">
    <head>
        <title>
            Hello FreeMarker!!!!
        </title>
    </head>
    <body>
      <h1>Welcome FreeMarker!!</h1>
<h2>-${context.form1}</h2>
<h2>-${context.form2}</h2>
      <h2>User:</h2>
      <h2>-${context.user1.getUser()}</h2>
      <h2>-${context.user1.getPass()}</h2>
    </body>
</html>