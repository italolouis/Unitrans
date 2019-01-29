package br.com.unitrans.mobile.model;

/**
 * Created by Felipe on 28/08/2016.
 */

//Classe que representa o PONTO de ônibus

public class Ponto {
    private int id;
    private String endereco;
    private String bairro;
    private String  cidade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    @Override
    public String toString() {
        return id + " - " + endereco + " - " + bairro  + " - " + cidade;
    }
}
