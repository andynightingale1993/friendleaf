/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andrew
 * Servlet que se encarga de, habiendo pasado como parámetro el usuario,
 * eliminarlo de la Base de Datos, de la tabla friends.
 */
@WebServlet(name = "EliminarAmigo", urlPatterns = {"/EliminarAmigo"})
public class EliminarAmigo extends HttpServlet {

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
        //Indicamos que la codificación es UTF-8.
        request.setCharacterEncoding("UTF-8");
        //Obtenemos el usuario que está conectado.
        HttpSession session = request.getSession();
        String usersession = (String) session.getAttribute("name");
        //Obtenemos el amigo que queremos eliminar de la tabla.
        String user = request.getParameter("amigo");
        //Página y error.
        String page = "AmigosServlet";
        String error = "";
        //A partir de su nick de usuario obtenemos su id.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Conectamos con la Base de Datos.
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            //Obtenemos las sentencias.
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Statement sentenciaid = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentenciaeliminar = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Obtenemos el ID del usuario conectado.
            ResultSet resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + usersession + "'");
            resultado.next();
            int iduser = resultado.getInt("id");
            //Obtenemos el ID del usuario amigo.
            ResultSet resultadofriend = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
            resultadofriend.next();
            int idfriend = resultadofriend.getInt("id");
            //Y en la tabla de friends borramos la relación de amistad donde el usuario b y el usuario a tengan ese valor.
            sentenciaeliminar.executeUpdate("DELETE FROM friends WHERE userb=" + idfriend + " AND usera=" + iduser);
            sentenciaeliminar.executeUpdate("DELETE FROM friends WHERE usera=" + idfriend + " AND userb=" + iduser);
            
            //Cerramos las conexiones de la Base de Datos
            resultadofriend.close();
            resultado.close();
            sentencia.close();
            sentenciaeliminar.close();
            sentencia.close();
            conexion.close();
        }
        //Si ha ocurrido algún error se lo indicamos al usuario en amigos.jsp.
        catch (ClassNotFoundException | SQLException ex) {
            error += "Ha ocurrido un error inesperado: " + ex.getMessage();
            page = "amigos.jsp";
        }      
        //Y finalmente nos redirigimos a la página indicada con el error indicado.
        finally {
            session.setAttribute("error", error);
            request.getRequestDispatcher(page).forward(request, response);
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
