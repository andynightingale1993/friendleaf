/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.File;
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
import org.apache.tomcat.util.http.fileupload.FileUtils;

/**
 *
 * @author andrew
 * Servlet que se encarga de eliminar una cuenta de la base de datos.
 */
@WebServlet(name = "EliminarCuentaServlet", urlPatterns = {"/EliminarCuentaServlet"})
public class EliminarCuentaServlet extends HttpServlet {

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
        //Página y Error.
        String page = "PerfilServlet";
        String error = "";
        
        //Obtenemos la sesión y ponemos el atributo error a null.
        HttpSession session = request.getSession();
        session.setAttribute("error", null);
        //Nos conectamos a la BDD...
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Eliminamos el directorio de las fotos y los vídeos del usuario.
            File directorio_actual = new File(".");
            File directorio_photos = new File(directorio_actual.getCanonicalPath() + "/webapps/FriendLeaf/photos/" + session.getAttribute("name"));
            File directorio_videos = new File(directorio_actual.getCanonicalPath() + "/webapps/FriendLeaf/videos/" + session.getAttribute("name"));
            FileUtils.deleteDirectory(directorio_photos);
            FileUtils.deleteDirectory(directorio_videos);
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //...y eliminamos la cuenta.
            sentencia.executeUpdate("DELETE FROM users WHERE user='" + session.getAttribute("name") + "'");
            request.setAttribute("mensaje", "Cuenta Elminada con Éxito");
            //Cerramos sesión y nos vamos a index.jsp.
            session.invalidate();
            page = "index.jsp";
            
            //Cerramos las conexiones a BDD.
            sentencia.close();
            conexion.close();
        //Si ha ocurrido algún error se lo indicamos al usuario.
        } catch (ClassNotFoundException | SQLException ex) {
           error += "Ha ocurrido un error inesperado: " + ex.getMessage() + "<br>";
        } finally {
           //Y finalmente nos vamos a la página indicada con el error indicado.
           session = request.getSession(false);
           if (session != null) session.setAttribute("error", error);
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
