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
import java.util.Collections;

import java.util.List;

/**
 * Reference site: https://vertx.io/blog/some-rest-with-vert-x/
 *
 *
 * This is a verticle. A verticle is a _Vert.x component_. This verticle is
 * implemented in Java, but you can implement them in JavaScript, Groovy, Ruby
 * or Ceylon.
 */
public class MyFirstVerticle extends AbstractVerticle {

    private List<Whisky> list;

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
                (someData) -> startWebApp(
                        (http) -> completeStartup(http, fut)
                ),
                fut);
    }

    private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
        // Create a router object.
        Router router = Router.router(vertx);

        //making the path "/src/main/resources/assets/" as the webroot path alias assets
        router.route("/src/main/resources/assets/*").handler(StaticHandler.create("assets"));

        // Bind "/" to our index page.
        router.get("/").handler(ctx -> {
            ctx.response().sendFile("assets/index.html");
        });

        //get all brands
        router.get("/all").handler(this::getAll);
        //create brand
        router.route("/*").handler(BodyHandler.create());
        //add brand
        router.post("/add").handler(this::addOne);
        //update a specific brand
        router.put("/update/:id").handler(this::updateOne);
        //delete a brand
        router.delete("/delete/:id").handler(this::deleteOne);

        // Create the HTTP server and pass the "accept" method to the request handler.
        vertx
                .createHttpServer()
                .requestHandler(router::accept)
                .listen(
                        // Retrieve the port from the configuration,
                        // default to 8080.
                        config().getInteger("http.port", 8080),
                        //calling the next handle, i.e. the completeStartup method
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

    //getting a object based on the attribute id
    private Whisky getWhiskey(int id) {
        for (Whisky whisky1 : list) {
            if (whisky1.getId() == id) {
                return whisky1;
            }
        }
        return null;
    }

    //getting a new id for the new object
    private int getNewId(List<Whisky> list) {
        int i = 1;
        for (Whisky w : list) {
            if (getWhiskey(i) == null) {
                return i;
            }
            i++;
        }
        return i;
    }

    private void addOne(RoutingContext routingContext) {
        JsonObject j = routingContext.getBodyAsJson();
        Whisky whisky = new Whisky(getNewId(list), j.getString("name"), j.getString("origin"));
        list.add(whisky);

        routingContext.response()
                .setStatusCode(201)
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(whisky));
    }

    private void updateOne(RoutingContext routingContext) {
        final String id = routingContext.request().getParam("id");
        JsonObject json = routingContext.getBodyAsJson();
        if (id == null || json == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            Whisky w = getWhiskey(Integer.parseInt(id));
            if (w == null) {
                routingContext.response().setStatusCode(404).end();
            } else {
                w.setName(json.getString("name"));
                w.setOrigin(json.getString("origin"));
                routingContext.response()
                        .putHeader("content-type", "application/json; charset=utf-8")
                        .end(Json.encodePrettily(w));
            }
        }

    }

    private void deleteOne(RoutingContext routingContext) {
        String id = routingContext.request().getParam("id");
        if (id == null) {
            routingContext.response().setStatusCode(400).end();
        } else {
            list.remove(getWhiskey(Integer.parseInt(id)));
            routingContext.response().setStatusCode(204).end();

        }
    }

    private void getAll(RoutingContext routingContext) {
        Collections.sort(list);
        List<Whisky> whiskies = list;
        routingContext.response()
                .putHeader("content-type", "application/json; charset=utf-8")
                .end(Json.encodePrettily(whiskies));
    }

    private void createSomeData(Handler<AsyncResult<Void>> next, Future<Void> fut) {
        Whisky bowmore = new Whisky(1, "Bowmore 15 Years Laimrig", "Scotland, Island");
        Whisky talisker = new Whisky(2, "Talisker 10 North", "Scotland, Island");
        Whisky talisker1 = new Whisky(3, "Talisker 20 North", "Scotland, Island");
        Whisky talisker2 = new Whisky(4, "Blend 30 South", "Scotland, Island");
        list = new ArrayList<>();
        list.add(bowmore);
        list.add(talisker);
        list.add(talisker1);
        list.add(talisker2);
        next.handle(Future.<Void>succeededFuture());
    }
}
