/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import variables_de_entorno.Variables;

/**
 *
 * @author andrew
 */
@WebServlet(name = "AceptarEliminarPeticionAmistad", urlPatterns = {"/AceptarEliminarPeticionAmistad"})
public class AceptarEliminarPeticionAmistad extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Obtenemos la sesión.
        HttpSession session = request.getSession();

        //Página y error.
        String page = "amigos.jsp";
        String error = "";

        //Obtenemos la función
        String funcion = request.getParameter("funcion");

        //Según esa función aceptaremos o eliminaremos la petición de amistad.
        switch (funcion) {
            //Si queremos aceptar eliminamos la fila correspondiente de friend_requests
            //y lo insertamos a friends.
            case "aceptar": {
                //Obtenemos el usuario que me ha agregado y el usuario conectado.
                String usuario_amigo = request.getParameter("amigo");
                String conectado = (String) session.getAttribute("name");
                
                ///A partir de ahí tenemos que obtener los IDs de cada uno y eliminar la
                //fila que corresponda e insertar en friends la nueva amistad.
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(Variables.RUTA_BDD, Variables.USER_BDD, Variables.PASS_BDD);
                    Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    
                    ResultSet resultado_conectado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + conectado + "'");
                    resultado_conectado.next();
                    int id_conectado = resultado_conectado.getInt("id");
                    resultado_conectado.close();
                    
                    ResultSet resultado_amigo = sentencia.executeQuery("SELECT id FROM users WHERE user='" + usuario_amigo + "'");
                    resultado_amigo.next();
                    int id_amigo = resultado_amigo.getInt("id");
                    resultado_amigo.close();
                    
                    //Y finalmente eliminamos la fila e insertamos la nueva fila a friends.
                    sentencia.executeUpdate("DELETE FROM friend_requests WHERE usera=" + id_conectado + " AND userb=" + id_amigo);
                    sentencia.executeUpdate("INSERT INTO friends VALUES(" + id_conectado + ", " + id_amigo + ")");
                    
                    //Si todo ha ido bien nos vamos a AmigosSerlvet.
                    page = "AmigosServlet";
                    
                    //Cerramos las conexiones de BDD.
                    sentencia.close();
                    conexion.close();
                //Si ha ocurrido un error se lo indicamos al usuario.
                } catch (SQLException | ClassNotFoundException e) {
                    error += "Ha ocurrido un error inesperado: " + e.getMessage();
                //Y finalmente nos vamos a la página indicada con el error indicado.
                } finally {
                    session.setAttribute("error", error);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            }
            break;
            //Si queremos eliminar simplemente eliminamos la fila correspondiente de friend_requests.
            case "eliminar": {
                //Obtenemos el usuario que me ha agregado y el usuario conectado.
                String usuario_amigo = request.getParameter("amigo");
                String conectado = (String) session.getAttribute("name");
                
                //A partir de ahí tenemos que obtener los IDs de cada uno y eliminar la
                //fila que corresponda.
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conexion = DriverManager.getConnection(Variables.RUTA_BDD, Variables.USER_BDD, Variables.PASS_BDD);
                    Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    
                    ResultSet resultado_conectado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + conectado + "'");
                    resultado_conectado.next();
                    int id_conectado = resultado_conectado.getInt("id");
                    resultado_conectado.close();
                    
                    ResultSet resultado_amigo = sentencia.executeQuery("SELECT id FROM users WHERE user='" + usuario_amigo + "'");
                    resultado_amigo.next();
                    int id_amigo = resultado_amigo.getInt("id");
                    resultado_amigo.close();
                    
                    //Y finalmente eliminamos la fila.
                    sentencia.executeUpdate("DELETE FROM friend_requests WHERE usera=" + id_conectado + " AND userb=" + id_amigo);
                    
                    //Si todo ha ido bien nos vamos a AmigosSerlvet.
                    page = "AmigosServlet";
                    
                    //Cerramos las conexiones de BDD.
                    sentencia.close();
                    conexion.close();
                //Si ha ocurrido un error se lo indicamos al usuario.
                } catch (SQLException | ClassNotFoundException e) {
                    error += "Ha ocurrido un error inesperado: " + e.getMessage();
                //Y finalmente nos vamos a la página indicada con el error indicado.
                } finally {
                    session.setAttribute("error", error);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            }
            default: {
                error += "No puedes acceder a eso.";
                session.setAttribute("error", error);
                request.getRequestDispatcher(page).forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
