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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andrew
 * Este Servlet se encarga de agregar o eliminar un like
 * de la tabla "likes" según lo que le venga como parámetro (true o false).
 * Para saber quién es el usuario que tengo que eliminar lo obtengo de sesión,
 * y para saber el post lo obtengo de request.
 */
@WebServlet(name = "AgregarEliminarLike", urlPatterns = {"/AgregarEliminarLike"})
public class AgregarEliminarLike extends HttpServlet {

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
        //Obtenemos la sesión.
        HttpSession session = request.getSession();
        
        //Página y error.
        String page = "FeedServlet";
        String error = "";
        
        String like = request.getParameter("like");
        int id_post = Integer.parseInt(request.getParameter("idpost"));
        String user = (String) session.getAttribute("name");
        //Si es true tenemos que eliminar.
        if (like.equals("true")) {
            //Nos conectamos con la BDD
            try {
                Class.forName("com.mysql.jdbc.Driver");
                           
                
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //Obtenemos el ID del usuario.
                ResultSet resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                resultado.next();
                int id_user = resultado.getInt("id");
                //Eliminamos ese post de la tabla post_likes.
                sentencia.executeUpdate("DELETE FROM post_likes WHERE iduser=" + id_user + " AND idpost=" + id_post);
            } 
            //Si ha ocurrido un error lo indicamos al usuario.
            catch (SQLException | ClassNotFoundException e) {
                error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                page = "feed.jsp";
            }
            //Y finalmente nos vamos a la página indicada con el error indicado.
            finally {
                session.setAttribute("error", error);
                request.getRequestDispatcher(page).forward(request, response);
            }
        //Y en otro caso tenemos que insertar.
        } else {
            //Nos conectamos con la BDD
            try {
                Class.forName("com.mysql.jdbc.Driver");
                           
                
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //Obtenemos el ID del usuario.
                ResultSet resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                resultado.next();
                int id_user = resultado.getInt("id");
                //Insertamos ese post de la tabla post_likes.
                sentencia.executeUpdate("INSERT INTO post_likes(iduser, idpost) VALUES(" + id_user + ", " + id_post + ")");
            } 
            //Si ha ocurrido un error lo indicamos al usuario.
            catch (SQLException | ClassNotFoundException e) {
                error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                page = "feed.jsp";
            }
            //Y finalmente nos vamos a la página indicada con el error indicado.
            finally {
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
