package io.vertx.blog.first;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import java.util.ArrayList;

import java.util.List;

/**
 * This is a verticle. A verticle is a _Vert.x component_. This verticle is
 * implemented in Java, but you can implement them in JavaScript, Groovy, Ruby
 * or Ceylon.
 */
public class MyFirstVerticle extends AbstractVerticle {

    public static final String COLLECTION = "whiskies";
    List<Whisky> list;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MyFirstVerticle());
    }

    /**
     * This method is called when the verticle is deployed. It creates a HTTP
     * server and registers a simple request handler.
     * <p/>
     * Notice the `listen` method. It passes a lambda checking the port binding
     * result. When the HTTP server has been bound on the port, it call the
     * `complete` method to inform that the starting has completed. Else it
     * reports the error.
     *
     * @param fut the future
     */
    @Override
    public void start(Future<Void> fut) {

        createSomeData(
                (nothing) -> startWebApp(
                        (http) -> completeStartup(http, fut)
                ), fut);
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        // Create a router object.
        Router router = Router.router(vertx);

        router.route("/src/main/resources/assets/*").handler(StaticHandler.create("assets"));
        // Bind "/" to our hello message.
        router.get("/").handler(ctx -> {
            ctx.response().sendFile("assets/index.html");
        });

        router.get("/all").handler(this::getAll);
        router.route("/*").handler(BodyHandler.create());
        router.post("/add").handler(this::addOne);
        router.get("/get/:id").handler(this::getOne);
        router.put("/update/:id").handler(this::updateOne);
        router.delete("/delete/:id").handler(this::deleteOne);

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8080),
                        next::handle
                );
    }

    private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
        if (http.succeeded()) {
            fut.complete();
            System.out.println("Connected!"); 
       } else {
            fut.fail(http.cause());
        }
    }

    private int getIdWhiskey(int id) {
        int i = 0;
        for (Whisky whisky1 : list) {
            if (whisky1.getId() == id) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private void addOne(RoutingContext routingContext) {
        final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(),
                Whisky.class);
        whisky.setId(list.size());
        list.add(whisky);

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(whisky));
    }

    private void getOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
//            mongo.findOne(COLLECTION, new JsonObject().put("_id", id), null, ar -> {
//                if (ar.succeeded()) {
//                    if (ar.result() == null) {
//                        routingContext.response().setStatusCode(404).end();
//                        return;
//                    }
//                    routingContext.response()
//                            .setStatusCode(200)
//                            .putHeader("content-type", "application/json; charset=utf-8")
//                            .end(Json.encodePrettily(getIdWhiskey(Integer.parseInt(id))));
//                } else {
//                    routingContext.response().setStatusCode(404).end();
//                }
//            });
        }
    }

    private void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
//            mongo.update(COLLECTION,
//                    new JsonObject().put("_id", id), // Select a unique document
//                    // The update syntax: {$set, the json object containing the fields to update}
//                    new JsonObject()
//                            .put("$set", json),
//                    v -> {
//                        if (v.failed()) {
//                            routingContext.response().setStatusCode(404).end();
//                        } else {
//                            Whisky whiskey = list.get(getIdWhiskey(Integer.parseInt(id)));
//                            whiskey.setName( json.getString("name"));
//                            whiskey.setOrigin(json.getString("origin"));
//                            routingContext.response()
//                                    .putHeader("content-type", "application/json; charset=utf-8")
//                                    .end(Json.encodePrettily(whiskey));
//                        }
//                    });
        }
    }

    private void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            list.remove(getIdWhiskey(Integer.parseInt(id)));
            routingContext.response().setStatusCode(204).end();
            
        }
    }

    private void getAll(RoutingContext routingContext) {
        List<Whisky> whiskies = list;
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(whiskies));
    }

    private void createSomeData(Handler<AsyncResult<Void>> next, Future<Void> fut) {
        Whisky bowmore = new Whisky(1,"Bowmore 15 Years Laimrig", "Scotland, Island");
        Whisky talisker = new Whisky(2,"Talisker 10 North", "Scotland, Island");
        Whisky talisker1 = new Whisky(3,"Talisker 20 North", "Scotland, Island");
        Whisky talisker2 = new Whisky(4,"Blend 30 South", "Scotland, Island");
        list = new ArrayList<>();
        list.add(bowmore);
        list.add(talisker);
        list.add(talisker1);
        list.add(talisker2);
//        System.out.println(bowmore.toJson());

        next.handle(Future.<Void>succeededFuture());
    }
}
