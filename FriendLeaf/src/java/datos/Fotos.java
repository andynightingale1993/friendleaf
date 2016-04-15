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
 * Clase para obtener la lista de fotos que vamos a usar en un Bean
 * en fotos.jsp.
 */
public class Fotos {
    private ArrayList<String> fotos;
    
    public Fotos() {
        fotos = new ArrayList<>();
    }

    public ArrayList<String> getFotos() {
        return fotos;
    }

    public void setFotos(ArrayList<String> fotos) {
        this.fotos = fotos;
    }
    
    
}
