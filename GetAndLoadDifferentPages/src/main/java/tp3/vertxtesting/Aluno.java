/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3.vertxtesting;

public class Aluno {

    private String nome, email;
    private int numero;

    public Aluno(String nome, String email, int numero) {
        this.nome = nome;
        this.email = email;
        this.numero = numero;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return "{" + "\"nome\":\"" + nome + "\", \"email\":\"" + email + "\", \"numero\":\"" + numero + "\"}";
    }

}
