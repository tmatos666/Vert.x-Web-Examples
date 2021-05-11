package redsi.form1;

/**
 * @author Misterio
 */
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystemOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import static io.vertx.ext.web.handler.StaticHandler.DEFAULT_WEB_ROOT;

public class Servidor extends AbstractVerticle {

    private String webRoot = DEFAULT_WEB_ROOT;

    @Override
    public void start(Promise<Void> promise) throws Exception {
        Router router = Router.router(vertx);

        // por pré-definição serve index.html
        router.route("/*").handler(StaticHandler.create(webRoot));

        // pedido de recurso estático (página nova)
        router.route().handler(BodyHandler.create());
        router.route(HttpMethod.POST, "/alunos").handler(this::addAluno);
        router.route(HttpMethod.POST, "/novoAluno").handler(this::novoAluno);
        router.route("/page2").handler(this::redirectPage2);

        // cria servidor HTTP     
        HttpServerOptions options = new HttpServerOptions();
        options.setPort(8080);
        vertx.createHttpServer(options)
                .requestHandler(router)
                .listen(res -> {
                    if (res.succeeded()) {
                        promise.complete();
                    } else {
                        promise.fail(res.cause());
                    }
                });

    }

    @Override
    public void stop() {
        System.out.println("---> REDSI stop! ");
    }

    private void redirectPage2(RoutingContext context) {
        //pode ser desta forma mas o route acima deve ser do tipo router.get("/page2").handler(this::redirectPage2);
        //pois o setStatusCode(303) é para um pedido get e no URL mostra a página page2.html
//        context.response().setStatusCode(303);
//        context.response().putHeader("Location", "page2.html");
//        context.response().end();

        context.response().putHeader("content-type", "text/html");
        context.response().sendFile(webRoot+"/page2.html");//na raiz do projeto
        context.response().end(); 

    }

    private void novoAluno(RoutingContext rc) {
        //exemplo usando unicamente Json do lado do cliente
        JsonObject j = rc.getBodyAsJson();
        Aluno aluno = new Aluno(j.getString("name"), j.getString("email"), j.getInteger("id"));

        HttpServerResponse response = rc.response();
        response.putHeader("content-type", "application/json; charset=utf-8");
        response.setStatusCode(200); // ok e recurso criado
        response.end(Json.encodePrettily(aluno));
    }

    private void addAluno(RoutingContext rc) {
        //exemplo usando um form do lado do cliente
        String name = rc.request().getParam("fname");
        String email = rc.request().getParam("lname");
        Aluno aluno = new Aluno(name, email, 1);

        HttpServerResponse response = rc.response();
        response.putHeader("content-type", "application/json; charset=utf-8");
        response.setStatusCode(200); // ok e recurso criado
        response.end(Json.encodePrettily(aluno));
    }

    public static void main(String[] args) {
        FileSystemOptions fsOptions = new FileSystemOptions();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Servidor());
    }
}
