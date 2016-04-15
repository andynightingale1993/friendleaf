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
 * Este Servlet se encarga, simplemente, de recoger
 * todas las fotos que tenemos en una lista para presentárselos
 * al usuario en un JSP en un select
 */
@WebServlet(name = "ModificarFotoPerfilServlet", urlPatterns = {"/ModificarFotoPerfilServlet"})
public class ModificarFotoPerfilServlet extends HttpServlet {

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
        //Pagina y error.
        String page = "perfil.jsp";
        String error = "";
        
        //Obtenemos la sesión.
        HttpSession session = request.getSession();
        
        //Obtenemos el parámetro photo.
        String photo = request.getParameter("photo");
        
        //Nos conectamos con la BDD.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Y lo único que tenemos que hacer es modificar la foto de perfil en la tabla users.
            sentencia.executeUpdate("UPDATE users SET photo='" + photo + "' WHERE user='" + session.getAttribute("name") + "'");
            
            //Si todo ha ido bien nos vamos al perfil para ver los cambios.
            page = "PerfilServlet";
        }
        //Si ha ocurrido un error se lo indicamos al usuario.
        catch (SQLException | ClassNotFoundException e) {
            error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
        }
        //Y finalmente nos vamos a la página indicada con el error indicado.
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
