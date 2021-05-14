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
import io.vertx.ext.web.FileUpload;
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

        router.route().handler(BodyHandler.create());

        router.route("/loadimage").handler(this::loadImage);

        initServer(router, promise);
    }

    private void loadImage(RoutingContext ctx) {
        Aluno a = null;
        System.out.println(ctx.fileUploads());
        for (FileUpload f : ctx.fileUploads()) {
            System.out.println("Filename: " + f.fileName());
            System.out.println("Size: " + f.size());
            System.out.println("Size: " + f.uploadedFileName());
            a = new Aluno(f.uploadedFileName(), "tpm@isep.ipp.pt", 1);
        }
       HttpServerResponse response = ctx.response();
        response.putHeader("content-type", "application/json; charset=windows-1252");
        response.end(Json.encodePrettily(a));
        response.setStatusCode(200); // ok e recurso criado
    }

    private void initServer(Router router, Promise<Void> promise) {// cria servidor HTTP     
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
