<!DOCTYPE html>
<html lang="en">
    <head>
        <title>
            Hello FreeMarker!!!!
        </title>
    </head>
    <body>
        <h1>Welcome FreeMarker!!</h1>
        <h2>Hello FreeMarker!!!! (${context.array.getId()})</h2>
        <h2>${context.array.getArray()[1]}</h2>
      
        <#list context.array.getArray() as i>
            <p>${i}</p>
        </#list>
        
        <h3>GET VALUE: ${context.form}<h3>
    </body>
</html>