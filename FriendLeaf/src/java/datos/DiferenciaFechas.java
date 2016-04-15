/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author andrew
 * Esta clase proporciona un método que nos devuelve
 * la diferencia de dos fechas en días.
 */
public class DiferenciaFechas {
    
    public static int diferenciaFechas(Date fecha_actual, Date fecha_post) {
        int retorno = 0;
        Calendar calendario_actual = Calendar.getInstance();
        Calendar calendario_post = Calendar.getInstance();
        calendario_actual.setTime(fecha_actual);
        calendario_post.setTime(fecha_post);
        
        retorno = calendario_actual.compareTo(calendario_post);
        return Math.round(retorno / 1000 / 3600 / 24);
    }
} 
