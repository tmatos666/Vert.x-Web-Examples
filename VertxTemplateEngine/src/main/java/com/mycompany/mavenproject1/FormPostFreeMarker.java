/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import classes.Car;
import classes.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Launcher;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

/**
 *
 * @author ISEP
 */
public class FormPostFreeMarker extends AbstractVerticle {

    private User u;
     // Convenience method so you can run it in your IDE
    public static void main(String[] args) throws Exception {
        //Runner.runExample(Server.class);
        io.vertx.core.Launcher l = new Launcher();
        l.execute("run", "com.mycompany.mavenproject1.FormPostFreeMarker");
    }
    
    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        router.get("/").handler(this::home);
        router.get("/form").handler(this::form);

        router.route().handler(BodyHandler.create());
        router.post("/login").handler(this::dataForm);

        router.get("/afterForm").handler(this::afterForm);

        createServer(startFuture, router);
    }

    private void afterForm(RoutingContext ctx) {
        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();
        engine.setMaxCacheSize(0);

        StaticHandler staticHandler = StaticHandler.create();
        staticHandler.setCachingEnabled(false);

        ctx.put("form1", "after Form");
        ctx.put("form2", "Hello World");
        u.setId(10); 
        ctx.put("user1", u); 

//        // and now delegate to the engine to render it.
        engine.render(ctx, "templates/afterForm.ftl", res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }

    private void dataForm(RoutingContext ctx) {
        String user = ctx.request().getFormAttribute("username");//to read input named username.
        String pass = ctx.request().getFormAttribute("password");//to read input named password.

        u = new User(user, pass);

        System.out.println("####################: " + user);
        System.out.println("####################: " + pass);

        afterForm(ctx);

    }

    private void home(RoutingContext ctx) {
        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();
        engine.setMaxCacheSize(0);

        StaticHandler staticHandler = StaticHandler.create();
        staticHandler.setCachingEnabled(false);

        // data can be here
        Car c = new Car(11);

        // execute the context with data
        ctx.put("array", c);

        // and now delegate to the engine to render it.
        engine.render(ctx, "templates/index.ftl", res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }

    private void form(RoutingContext ctx) {
        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();
        engine.setMaxCacheSize(0);

        StaticHandler staticHandler = StaticHandler.create();
        staticHandler.setCachingEnabled(false);

        // data can be here
        Car c = new Car(11);

        // execute the context with data
        ctx.put("array", c);
        ctx.put("form", "Hello Form");

        // and now delegate to the engine to render it.
        engine.render(ctx, "templates/form.ftl", res -> {
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
