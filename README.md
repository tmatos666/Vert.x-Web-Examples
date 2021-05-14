# Vert.x-Web-Examples
Simple web examples using the Vert.x framework (https://vertx.io/docs/vertx-web/java/)

Examples consists of:

 1 - Authentication: Simple class from authentication. More sophisticated authentication examples can be seen in https://vertx.io/docs/3.9.2/vertx-auth-common/java/#_authentication.
 
 2 - GetAndLoadDifferentPages: Get and Load from different pages using uri roting.
 
 3 - LoadDataInIndexPage: Load data in the index.html through onload javascript.
 
 4 - OnclickSubmitFormLoadData: Submitting a form. The server work with form data. The server send data again to the same HTML page.
 
 5 - VertxTemplateEngine: Vert.x examples using Freemarker tamplete engine.
 
 6 - CRUDApplication: A simple CRUD application using Vert.x with REST examples (based on https://vertx.io/blog/some-rest-with-vert-x/)
 
 7 - VertxRedirectToPageHandler: A simple application using Vert.x. Sending data from cliente to server using form and Json. Simple demonstration for redirecting pages using "putHeader("content-type", "text/html")"
 
 8 - URIredirectAndLoadpageWithInfo: URI redirect and load page with Information based on ID. Index.html redirect to page2.html (aluno/1) and this page has an onload javascript function that based on the uri fetch info from the server
 
 9 - VertxMultipleDatainJsonObject: Creates an JsonObject with multiple java objects and loads it in the index page apon an onload event
 
 10 - VertxUploadFiles: Upload and save file (or image). This file will be renamed automatically.