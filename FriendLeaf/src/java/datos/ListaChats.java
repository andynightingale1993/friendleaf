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
 * Objeto que guarda una lista de chats que tienen
 * dos usuarios.
 */
public class ListaChats {
    private String amigo;
    private ArrayList<Chat> lista_chats;
    
    //Constructor vacío sin parámetros.
    public ListaChats() {
    
    }
    
    //Getters y Setters.
    public String getAmigo() {
        return amigo;
    }

    public void setAmigo(String amigo) {
        this.amigo = amigo;
    }

    public ArrayList<Chat> getLista_chats() {
        return lista_chats;
    }

    public void setLista_chats(ArrayList<Chat> lista_chats) {
        this.lista_chats = lista_chats;
    }

    
}
