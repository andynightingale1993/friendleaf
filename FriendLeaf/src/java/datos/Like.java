/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.ArrayList;

/**
 *
 * @author andrew
 * Clase que guarda los datos de un "Like" de la tabla
 * "likes".
 */
public class Like {
    private int id_usuario;
    private String[] musica;
    private String[] videojuegos;
    private String[] cine;
    private String[] literatura;
    private String[] arte;
    
    //Constructor vacío sin parámetros
    public Like() {
    
    }
    
    //Getters y Setters.
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String[] getMusica() {
        return musica;
    }

    public void setMusica(String[] musica) {
        this.musica = musica;
    }

    public String[] getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(String[] videojuegos) {
        this.videojuegos = videojuegos;
    }

    public String[] getCine() {
        return cine;
    }

    public void setCine(String[] cine) {
        this.cine = cine;
    }

    public String[] getLiteratura() {
        return literatura;
    }

    public void setLiteratura(String[] literatura) {
        this.literatura = literatura;
    }

    public String[] getArte() {
        return arte;
    }

    public void setArte(String[] arte) {
        this.arte = arte;
    }

   
}
