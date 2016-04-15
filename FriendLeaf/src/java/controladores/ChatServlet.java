/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import datos.Amigos;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * Servlet que se encarga de recoger los datos de los amigos de un usuario
 * y guardarlos en un "Bean"
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/ChatServlet"})
public class ChatServlet extends HttpServlet {

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
        //Reiniciamos el valor de object.
        session.setAttribute("friends", null);
        session.setAttribute("error", null);
        String page = "frames/chat.jsp";
        String error = "";
        
        //Nos conectamos con la Base de Datos
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentenciafriends = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia2 = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + session.getAttribute("name") + "'");
            ResultSet resultado2 = null;
            resultado.next();
            //Obtenemos el ID.
            int id = resultado.getInt("id");
            //Instanciamos un objeto amigos con su array.
            Amigos amigos = new Amigos();
            ArrayList<String> listaamigos = amigos.getAmigos();
            ArrayList<String> usuariosamigos = amigos.getUsuariosamigos();
            //Obtenemos todos los amigos del usuario.
            ResultSet resultadofriends = sentenciafriends.executeQuery("SELECT userb FROM friends WHERE usera=" + id);
            while(resultadofriends.next()) {
                //Para cada amigo...
                int idamigo = resultadofriends.getInt("userb");
                //...obtenemos su nombre y apellidos.
                resultado2 = sentencia2.executeQuery("SELECT name, surname, user FROM users WHERE id=" + idamigo);
                resultado2.next();
                //Y guardamos esos valores en el array.
                listaamigos.add(resultado2.getString("name") + " " + resultado2.getString("surname"));
                usuariosamigos.add(resultado2.getString("user"));
            }
            //Le damos la referencia nueva al array.
            amigos.setAmigos(listaamigos);
            amigos.setUsuariosamigos(usuariosamigos);
            //Y guardamos el objeto de Amigos en sesión.
            session.setAttribute("friends", amigos);
            
            if (resultado2 != null) resultado2.close();
            resultadofriends.close();
            resultado.close();
            sentencia2.close();
            sentenciafriends.close();
            sentencia.close();
            conexion.close();
            
        } 
        //Si ha ocurrido algún error lo indicamos.
        catch (SQLException | ClassNotFoundException ex) {
            error += "Ha ocurrido un error inesperado: " + ex.getMessage() + "<br>"; 
            ex.printStackTrace();
        }
        //Y al final ponemos el nuevo valor de error en sesión y nos vamos a amigos.jsp.
        finally {
            session.setAttribute("error", error);
            //request.getRequestDispatcher(page).forward(request, response);
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
