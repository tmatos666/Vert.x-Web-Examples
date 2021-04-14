/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3.vertxtesting;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 *
 * @author ISEP
 */
public class ServerWithDifferentPages extends AbstractVerticle {

    Router router;
    Aluno aluno;
    ClassHandler handler;

    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new ServerWithDifferentPages());

    }

    @Override
    public void start(Future<Void> fut) throws Exception {
        router = Router.router(vertx);

        router.route("/*").handler(StaticHandler.create().setDefaultContentEncoding("windows-1252"));

        handler = new ClassHandler();
        handler.alunos(router);

        router.route("/alunos/:alunoid").handler(handler::getAluno);
        //ou ainda
        //router.route("/alunos/:alunoid").handler(new ClassHandler()::getAluno);

        //Fez todo o redirecionamento do caminho /alunos para a pagina2.html 
        router.route(HttpMethod.GET, "/alunos").handler(StaticHandler.create("webroot/" + "pagina2.html"));

        //Encamninha a informação do aluno
        router.route("/alunos").handler(this::showAluno);

        //Encamninha a informação do aluno
        router.route().handler(BodyHandler.create());
        router.route("/testData").handler(this::data);

        createServer(fut);
    }

    private void data(RoutingContext routingContext) {
        String x = routingContext.request().getParam("teste");
        System.out.println(x);

        routingContext.response().setStatusCode(303);
        routingContext.response().putHeader("Location", "/");
        routingContext.response().end();
    }

    private void showAluno(RoutingContext routingContext) {
        HttpServerResponse response = routingContext.response();
        response.putHeader("content-type", "application/json; charset=utf-8");
        response.end(Json.encodePrettily(handler.aluno));
        response.setStatusCode(200);
    }

    private void createServer(Future<Void> fut) {
        vertx.createHttpServer().requestHandler(router).listen(8080, result -> {
            if (result.succeeded()) {
                System.out.println("Connected!");
                fut.complete(); //concretiza criacao
            } else {
                fut.fail(result.cause()); // nao conseguiu criar o servidor
                System.err.println("Couldn't connect...");
            }
        });
    }
}
