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
 */
public class Amigos {
    private ArrayList<String> amigos;
    private ArrayList<String> usuariosamigos;
    private ArrayList<String> peticionesamigos;
    
    //Cuando creo el objeto instancio su array.
    public Amigos() {
        amigos = new ArrayList<>();
        usuariosamigos = new ArrayList<>();
        peticionesamigos = new ArrayList<>();
    }

    //Getter y Setter
    public ArrayList<String> getAmigos() {
        return amigos;
    }

    public void setAmigos(ArrayList<String> amigos) {
        this.amigos = amigos;
    }

    public ArrayList<String> getUsuariosamigos() {
        return usuariosamigos;
    }

    public void setUsuariosamigos(ArrayList<String> usuariosamigos) {
        this.usuariosamigos = usuariosamigos;
    }

    public ArrayList<String> getPeticionesamigos() {
        return peticionesamigos;
    }

    public void setPeticionesamigos(ArrayList<String> peticionesamigos) {
        this.peticionesamigos = peticionesamigos;
    }
    
    
    
    
}
