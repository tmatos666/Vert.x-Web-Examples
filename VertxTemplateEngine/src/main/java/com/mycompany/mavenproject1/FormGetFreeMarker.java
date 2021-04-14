/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import classes.Car;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

/**
 *
 * @author ISEP
 */
public class FormGetFreeMarker extends AbstractVerticle {

    // Convenience method so you can run it in your IDE
    public static void main(String[] args) throws Exception {
        io.vertx.core.Launcher l = new Launcher();
        l.execute("run", "com.mycompany.mavenproject1.FormGetFreeMarker");
    }
    
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        //URL to test the engine: for example: http://localhost:8080/category/1
        router.get("/category/:productid").handler(this::exampleURLGET);

        // create the server
        createServer(startFuture, router);
    }

    public void exampleURLGET(RoutingContext ctx) {
        // create the template -- this case FreeMarker
        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();
        engine.setMaxCacheSize(0);/* no cache since we wan't hot-reload for templates
        just for template(server-side: html)*/

        // we define a hardcoded title for our application
        StaticHandler staticHandler = StaticHandler.create();
        staticHandler.setCachingEnabled(false);

        String value = ctx.request().getParam("productid");
        
        // data can be here
        Car c = new Car(11);

        // execute the context with data
        ctx.put("array", c);
        ctx.put("form", value);

        // and now delegate to the engine to render it.
        engine.render(ctx, "templates/getExample.ftl", res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });

    }

    private void createServer(Future<Void> startFuture, Router router) {
        // create the server
        HttpServer httpServer = vertx.createHttpServer().requestHandler(router::accept).listen(8080, ar -> {

            if (ar.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });
    }

}
