<!DOCTYPE html>

<html lang="en">
    <head>
        <title>
            Getting started
        </title>
    </head>
    <body>
      <h1>Welcome FreeMarker!!</h1>
      <h3>Hello FreeMarker!!!! (${context.array.getId()})</h1>
      <h3>${context.array.getArray()[1]}</h1>
      <#list context.array.getArray() as i>
        <p>${i}</p>
      </#list>
      <a href="/form">form</a>
      
    </body>
</html>