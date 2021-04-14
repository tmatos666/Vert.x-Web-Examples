/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3.vertxtesting;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

/**
 *
 * @author ISEP
 */
public class ClassHandler {
    Aluno aluno;
    
    public void alunos(Router router){
        router.route("/alunos/:alunoid").handler(this::getAluno);
    }
    
    public void getAluno(RoutingContext routingContext) {
        int alunoId = Integer.parseInt(routingContext.request().getParam("alunoid"));
        aluno = new Aluno("telmo", "ola", alunoId);

        routingContext.response().setStatusCode(303);
        routingContext.response().putHeader("Location", "/alunos");
        routingContext.response().end();
    }
}
