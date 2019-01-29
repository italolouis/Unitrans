package br.com.unitrans.mobile.model;

/**
 * Created by heloise on 28/09/16.
 */

public class Estudante {
    private int id;
    private String nome_aluno;
    private String data_de_nascimento;
    private String horario_de_ida;
    private String horario_de_volta;
    private String universidade;
    private String foto;
    private int status;

    public Estudante() {
    }

    public Estudante(int id, String nome_aluno, String data_de_nascimento, String horario_de_ida, String horario_de_volta, String universidade, String foto, int status) {
        this.id = id;
        this.nome_aluno = nome_aluno;
        this.data_de_nascimento = data_de_nascimento;
        this.horario_de_ida = horario_de_ida;
        this.horario_de_volta = horario_de_volta;
        this.universidade = universidade;
        this.foto = foto;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_aluno() {
        return nome_aluno;
    }

    public void setNome_aluno(String nome_aluno) {
        this.nome_aluno = nome_aluno;
    }

    public String getHorario_de_ida() {
        return horario_de_ida;
    }

    public void setHorario_de_ida(String horario_de_ida) {
        this.horario_de_ida = horario_de_ida;
    }

    public String getHorario_de_volta() {
        return horario_de_volta;
    }

    public void setHorario_de_volta(String horario_de_volta) {
        this.horario_de_volta = horario_de_volta;
    }

    public String getUniversidade() {
        return universidade;
    }

    public void setUniversidade(String universidade) {
        this.universidade = universidade;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData_de_nascimento() {
        return data_de_nascimento;
    }

    public void setData_de_nascimento(String data_de_nascimento) {
        this.data_de_nascimento = data_de_nascimento;
    }
}
