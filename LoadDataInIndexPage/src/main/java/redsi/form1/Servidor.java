package redsi.form1;

/**
 * @author Misterio
 */ 
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
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
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        // por pré-definição serve index.html
        router.route("/*").handler(StaticHandler.create(webRoot));
        router.route(HttpMethod.POST,"/alunos").handler(this::getAlunos);

        // cria servidor HTTP     
        HttpServerOptions options = new HttpServerOptions();
        options.setPort(8080); 
        vertx.createHttpServer(options)
                .requestHandler(router)
                .listen(res -> {
                    if (res.succeeded()) {
                        startFuture.complete();
                    } else {
                        startFuture.fail(res.cause());
                    }
                });
    }

    @Override
    public void stop() {
        System.out.println("---> REDSI stop! ");
    }
    
    private void getAlunos(RoutingContext rc) {
        Aluno aluno = new Aluno("telmo","tesr@fikfi.pt",1);

        HttpServerResponse response = rc.response();
        response.putHeader("content-type", "application/json; charset=utf-8");
        response.end(Json.encodePrettily(aluno));
        response.setStatusCode(200); // ok e recurso criado
       
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Servidor());
    }
}
