/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
 */
@WebServlet(name = "EliminarFotoServlet", urlPatterns = {"/EliminarFotoServlet"})
public class EliminarFotoServlet extends HttpServlet {

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
        //Obtenemos el usuario y la foto.
        String user = (String) session.getAttribute("name");
        String photo = request.getParameter("photo");
        
        //Página y error.
        String page = "FotosServlet";
        String error = "";

        //Con ese usuario lo borramos de la BDD.
        try {
            //Nos conectamos a la Base de Datos.
            Class.forName("com.mysql.jdbc.Driver");

            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ResultSet resultado_usuario = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
            resultado_usuario.next();
            //Obtenemos el ID para borrar su foto de la BDD.
            int id_usuario = resultado_usuario.getInt("id");

            resultado_usuario.close();
            //Eliminamos el archivo de la foto.
            //Obtenemos la ruta del archivo...
            String current = new java.io.File(".").getCanonicalPath();
            String path = current + "/webapps/FriendLeaf/" + photo;

            Path root = Paths.get(path);
            //...y lo eliminamos
            Files.delete(root);
            //Borramos la foto de la BDD.
            sentencia.executeUpdate("DELETE FROM photos WHERE user=" + id_usuario + " AND photo='" + photo + "'");

            //Cerramos las conexiones con la BDD.
            sentencia.close();
            conexion.close();
        } //Si ha ocurrido un error se lo indicamos al usuario.
        catch (ClassNotFoundException | SQLException e) {
            error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
            page = "fotos.jsp";
        } //Finalmente nos vamos a la página indicada con el error indicado.
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
