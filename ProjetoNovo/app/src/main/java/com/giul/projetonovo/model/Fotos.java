package com.giul.projetonovo.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

/**
 * Created by giul on 16/11/2019.
 */

public class Fotos implements Serializable {
    private List<String> fotos;
    private String idFoto;


    public Fotos() {
        DatabaseReference anuncioRef = com.giul.projetonovo.config.ConfiguracaoFirebase.getFirebase()
                .child("minhas_fotos");
        setIdFoto( anuncioRef.push().getKey() );
    }

    public void salvar(){

        String idUsuario = com.giul.projetonovo.config.ConfiguracaoFirebase.getIdUsuario();
        DatabaseReference anuncioRef = com.giul.projetonovo.config.ConfiguracaoFirebase.getFirebase()
                .child("minhas_fotos");

        anuncioRef.child(idUsuario)
                .child(getIdFoto())
                .setValue(this);



    }


    public String getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(String idFoto) {
        this.idFoto = idFoto;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }
}
