/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import datos.Like;
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
 * @author andrew Servlet que se encarga de sacar todos los "likes" de un
 * usuario para guardarlos en un "bean".
 */ 
@WebServlet(name = "GustosServlet", urlPatterns = {"/GustosServlet"})
public class GustosServlet extends HttpServlet {

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

        //Obtenemos de la sesión el nombre del usuario conectado.
        String user_online = (String) session.getAttribute("name");

        //Variables de error y página.
        String error = "";
        String page;
        if (request.getParameter("page") == null) page = "frames/anuncios.jsp";
        else page = "gustos.jsp";

        try {
            //Nos conectamos con la Base de Datos.
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Obtenemos el ID del usuario conectado.
            ResultSet resultado_usuario = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user_online + "'");
            resultado_usuario.next();
            int id_user = resultado_usuario.getInt("id");
            resultado_usuario.close();
            //Y de ese usuario conectado sacamos todos sus "likes".
            ResultSet resultado_likes = sentencia.executeQuery("SELECT musica, videojuegos, cine, literatura, arte FROM likes WHERE id_usuario=" + id_user);
            Like like = null;
            while (resultado_likes.next()) {
                //Creamos un objeto Like y guardamos en él todos los gustos.
                like = new Like();
                like.setId_usuario(id_user);
                like.setMusica(resultado_likes.getString("musica").split(","));
                like.setVideojuegos(resultado_likes.getString("videojuegos").split(","));
                like.setCine(resultado_likes.getString("cine").split(","));
                like.setLiteratura(resultado_likes.getString("literatura").split(","));
                like.setArte(resultado_likes.getString("arte").split(","));
            }
            //Guardamos el objeto Like en sesión.
            session.setAttribute("likes", like);
            
            //Y cerramos las conexiones.
            resultado_likes.close();
            sentencia.close();
            conexion.close();
        }
        //Si ha ocurrido un error se lo indicamos.
        catch (SQLException | ClassNotFoundException e) {
            error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
        }
        //Finalmente nos vamos a la página indicada con el error indicado.
        finally {
            session.setAttribute("error", error);
            response.sendRedirect(page);
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
