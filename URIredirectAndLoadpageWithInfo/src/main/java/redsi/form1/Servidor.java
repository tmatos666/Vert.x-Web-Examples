package redsi.form1;

/**
 * @author Misterio
 */
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.file.FileSystemOptions;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import static io.vertx.ext.web.handler.StaticHandler.DEFAULT_WEB_ROOT;

public class Servidor extends AbstractVerticle {

    private String webRoot = DEFAULT_WEB_ROOT;

    @Override
    public void start(Promise<Void> promise) throws Exception {
        Router router = Router.router(vertx);

        // por pré-definição serve index.html
        router.route("/*").handler(StaticHandler.create(webRoot));

        router.route("/page2").handler(StaticHandler.create(webRoot + "/" + "page2.html"));

        router.get("/aluno/*").handler((this::paginaAluno));
        
        router.post("/aluno/:id").handler((this::adicionaAluno));
        
        router.route("/teste").handler(this::redireciona);
        
        initServer(router, promise);
    }


    private void paginaAluno(RoutingContext context) {
        context.response().putHeader("content-type", "text/html");
        context.response().sendFile(webRoot+"/"+"page2.html");
        context.response().setStatusCode(202); //202 Accepted
        context.response().end();

    }

    private void adicionaAluno(RoutingContext rc) {
        int idAluno = Integer.parseInt(rc.request().getParam("id"));
        Aluno aluno = new Aluno("teste", "teste1", idAluno);
        System.out.println(aluno);
        HttpServerResponse response = rc.response();
        response.putHeader("content-type", "application/json; charset=utf-8");
        response.setStatusCode(200); // ok e recurso criado
        response.end(Json.encodePrettily(aluno));

    }
    
    private void redireciona(RoutingContext rc){
        HttpServerResponse response = rc.response();
        response.setStatusCode(303); // O servidor manda essa resposta para instruir ao cliente buscar o recurso requisitado em outra URI com uma requisição GET.
        response.putHeader("Location", "/");
        response.end();
    }

    private void initServer(Router router, Promise<Void> promise){// cria servidor HTTP     
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
    
    public static void main(String[] args) {
        FileSystemOptions fsOptions = new FileSystemOptions();
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Servidor());
    }
}
