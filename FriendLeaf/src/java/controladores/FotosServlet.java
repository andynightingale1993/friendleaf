/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import datos.Fotos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andrew
 * Servlet que se encarga de cargar las fotos de un usuario y guardarlas en
 * un Bean que será representado en fotos.jsp.
 */
@WebServlet(name = "FotosServlet", urlPatterns = {"/FotosServlet"})
public class FotosServlet extends HttpServlet {

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
        //Página y error.
        String page = "fotos.jsp";
        String error = "";
        //Obtenemos el usuario conectado y reiniciamos el error y objeto.
        HttpSession session = request.getSession();
        session.setAttribute("error", null);
        session.setAttribute("object", null);
        String user = (String) session.getAttribute("name");
        
        //Obtenemos su ID.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
            resultado.next();
            int iduser = resultado.getInt("id");
            //Obtenemos las fotos de ese usuario y las insertamos en una lista de tipo Fotos.
            Fotos fotos = new Fotos();
            ArrayList<String> listafotos = fotos.getFotos();
            //Obtenemos las fotos y la ruta del usuario.
            ResultSet resultadofotos = sentencia.executeQuery("SELECT photo FROM photos WHERE user=" + iduser);
            while (resultadofotos.next()) {
                listafotos.add(resultadofotos.getString("photo"));
            }
            fotos.setFotos(listafotos);
            session.setAttribute("object", fotos);
        }
        catch (ClassNotFoundException | SQLException ex) {
            error += "Ha ocurrido un error inesperado: " + ex.getMessage();
        }        
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
