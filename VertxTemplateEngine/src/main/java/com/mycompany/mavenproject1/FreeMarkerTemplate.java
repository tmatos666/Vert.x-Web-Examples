/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mavenproject1;

import classes.Car;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

/**
 *
 * @author ISEP
 */
public class FreeMarkerTemplate extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        // create the template -- this case FreeMarker
        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();
        engine.setMaxCacheSize(0);/* no cache since we wan't hot-reload for templates
        just for template(server-side: html)*/

        // In order to use a template we first need to create an engine
        // Entry point to the application, this will render a custom template.
        router.get().handler((RoutingContext ctx) -> {
            // we define a hardcoded title for our application
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
        });

        // create the server
        vertx.createHttpServer().requestHandler(router::accept).listen(8080, ar -> {
            if (ar.succeeded()) {
                startFuture.complete();
            } else {
                startFuture.fail(ar.cause());
            }
        });
    }
}
