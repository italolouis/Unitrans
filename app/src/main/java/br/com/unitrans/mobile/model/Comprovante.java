package br.com.unitrans.mobile.model;

import java.util.Date;

/**
 * Created by heloi on 25/08/2016.
 */
public class Comprovante {

    // private variables
    private int id;
    private int fk_id_estudante;
    private Date data;
    private String name;
    private byte[] image;
    private int status;

    // Empty constructor
    public Comprovante() {

    }

    public Comprovante(String name, byte[] image) {
        this.name = name;
        this.image = image;
    }

    public int getFk_id_estudante() {
        return fk_id_estudante;
    }

    public void setFk_id_estudante(int fk_id_estudante) {
        this.fk_id_estudante = fk_id_estudante;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getID() {
        return this.id;
    }

    public void setID(int keyId) {
        this.id = keyId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
