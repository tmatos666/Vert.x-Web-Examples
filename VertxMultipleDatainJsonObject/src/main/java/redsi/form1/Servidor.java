/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redsi.form1;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 *
 * @author RAMP
 */
public class Servidor extends AbstractVerticle {

    Router router;

    public static void main(String[] args) throws Exception {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new Servidor());
    }

    @Override
    public void start(Future<Void> fut) throws Exception {

        router = Router.router(vertx);
        router.route("/*").handler(StaticHandler.create());
        router.route("/*").handler(this::indexPage);

        createServer(fut);
    }

    private void indexPage(RoutingContext rc) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        alunos.add(new Aluno("Telmo", "tpm@isep.ipp.pt", 1));
        alunos.add(new Aluno("Alberto", "acs@isep.ipp.pt", 2));
        alunos.add(new Aluno("Telmo Matos", "tpm@isep.ipp.pt", 3));
        alunos.add(new Aluno("Alberto Sampaio", "acs@isep.ipp.pt", 4));

        JSONObject json2 = new JSONObject();
        int i = 1;
        for (Aluno aluno : alunos) {
            JSONObject json1 = new JSONObject();
            json1.put("nome", aluno.getNome());
            json1.put("email", aluno.getEmail());
            json1.put("numero", aluno.getNumero());
            json2.put(i, json1);
            i++;
        }

        JSONObject finalJson = new JSONObject();
        finalJson.put("title", "Exemplo de uma Página Index");
        finalJson.put("listaAlunos", json2);

//        System.out.println(finalJson.toJSONString());

        HttpServerResponse response = rc.response();
        response.putHeader("content-type", "application/json; charset=windows-1252");
        response.end(finalJson.toJSONString());
        response.setStatusCode(200); // ok e recurso criado
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
