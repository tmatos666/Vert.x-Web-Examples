
package tp3.vertxtesting;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import static io.vertx.core.http.HttpHeaders.CONTENT_TYPE;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 *
 * @author ISEP
 */

public class SimpleWebVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleWebVerticle());
    }
    static final String template = "Hello, %s!";
    static final String userPass = "Hello, %s with pass %s!";
    private Authentication authProvider;
    boolean isAuthenticated = false;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        //routes
            router.get("/api/greeting").handler(this::greeting);

            router.get("/login").handler(ctx -> {
                ctx.response().sendFile("src/webroot/login.html");
                ctx.response().end();
            });

            router.post("/submitLoginForm").handler(this::login);

            router.route().failureHandler(this::failureResponse);
            
            router.get("/private/web").handler(this::privateDomain);
            
            router.get("/*").handler(StaticHandler.create().setWebRoot("src/webroot").setCachingEnabled(false));
        
        //HTTP Basic Authentication redirection
            router.get("/private/*").handler(ctx -> {
                if(!isAuthenticated){
                    ctx.response().sendFile("src/webroot/login.html");
                    ctx.response().end();
                }else{
                    ctx.next();
                }
            });
            
        router.get("/private/web").handler(this::privateDomain);
        router.get("/*").handler(StaticHandler.create().setWebRoot("src/webroot").setCachingEnabled(false));
        
        //Start Server
            vertx.createHttpServer().requestHandler(router::accept).listen(8080, ar -> {
                if (ar.succeeded()) {
                    startFuture.complete();
                } else {
                    startFuture.fail(ar.cause());
                }
            });
    }

    private void privateDomain(RoutingContext ctx) {
        
        if(!isAuthenticated){
           ctx.response().sendFile("src/webroot/login.html");
           ctx.response().end();
        }else{
            System.out.println("user authenticated!");
            ctx.response().sendFile("src/webroot/private.html");
            ctx.response().end();
            
        }
    }

    private void login(RoutingContext rc) {
        System.out.println("Entrei");
        String uname = rc.request().getParam("uname");
        String psw = rc.request().getParam("psw");
        System.out.println(uname + " - " + psw);
        
        authProvider = new Authentication(uname,psw);
        isAuthenticated=true;
        JsonObject response = new JsonObject()
                .put("content", String.format(userPass, uname, psw));

        rc.response()
                .putHeader(CONTENT_TYPE, "application/json; charset=utf-8")
                .end(response.encodePrettily());
    }

    private void failureResponse(RoutingContext rc) {
        if (rc.statusCode() == 404) {
            rc.response().sendFile("src/webroot/404_not_found.html");//na raiz do projeto
        }
    }

    private void greeting(RoutingContext rc) {

        String name = rc.request().getParam("name");
        
        if (name == null) {
            name = "World";
        }

        JsonObject response = new JsonObject()
                .put("content", String.format(template, name));

        rc.response()
                .putHeader(CONTENT_TYPE, "application/json; charset=utf-8")
                .end(response.encodePrettily());

    }
}
