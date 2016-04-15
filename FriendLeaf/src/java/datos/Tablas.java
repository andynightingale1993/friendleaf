/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author andrew
 */
public class Tablas {

    public static ArrayList<ArrayList<Object[]>> sacarTablas(String ruta, String usuario, String password) {
        //El objeto que va a guardar todas nuestras tablas.
        ArrayList<ArrayList<Object[]>> tablas = new ArrayList<>();
            //El objeto que nos va a guardar los nombres de todas nuestras
        //tablas.
        ArrayList<String> nombre_tablas = new ArrayList<>();
        //Obtenemos el nombre de las tablas.
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conexion = DriverManager.getConnection(ruta, usuario, password);
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            DatabaseMetaData metadatos_bd = conexion.getMetaData();
            ResultSet resultado_tablas = metadatos_bd.getTables(null, null, null, null);
            while (resultado_tablas.next()) {
                //Obtenemos el nombre de esa tabla.
                String nombre_tabla = resultado_tablas.getObject(3).toString();
                    //out.println(nombre_tabla);
                //Guardamos esa tabla en la lista.
                nombre_tablas.add(nombre_tabla);
                //Y para cada una de esas tablas vamos a obtener sus datos.
                ResultSet resultado_datos_tabla = sentencia.executeQuery("SELECT * FROM " + nombre_tabla);
                //Obtenemos los MetaDatos de ese resultado.
                ResultSetMetaData metadatos_tabla = resultado_datos_tabla.getMetaData();
                int columnas = metadatos_tabla.getColumnCount();
                //Creamos un ArrayList<Object[]>
                ArrayList<Object[]> tabla_actual = new ArrayList<>();
                Object[] datos_fila = new Object[columnas];
                    //Antes de agregar los datos al array primero obtenemos los
                //nombres de las columnas y agregamos esas.
                //out.println("COLUMNAS");
                for (int i = 0; i < columnas; i++) {
                    datos_fila[i] = metadatos_tabla.getColumnName(i + 1);
                }
                //Guardamos ese array en la tabla actual.
                tabla_actual.add(datos_fila);
                //Recorremos la tabla y obtenemos todos sus valores.
                while (resultado_datos_tabla.next()) {
                    //Reiniciamos el array de objetos.
                    datos_fila = new Object[columnas];
                    //out.println("DATOS");
                    for (int i = 0; i < columnas; i++) {
                        datos_fila[i] = resultado_datos_tabla.getObject(i + 1);
                        //out.println(resultado_datos_tabla.getObject(i + 1));
                    }
                    //Y añadimos esa fila al array de Object
                    tabla_actual.add(datos_fila);
                }
                //Cuando ya hemos obtenido esa tabla la añadimos a la lista de tablas.
                tablas.add(tabla_actual);
                //Cerramos el ResultSet
                resultado_datos_tabla.close();
            }
            //Cerramos el ResultSet y las conexiones a BDD.
            resultado_tablas.close();
            sentencia.close();
            conexion.close();

        } //Si ha ocurrido un error lo indicamos.
        catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
        }
        return tablas;
    }
}
