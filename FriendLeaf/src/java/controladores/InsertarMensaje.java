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
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andrew
 * Servlet que se encarga de insertar un nuevo mensaje
 * a un Chat.
 */
@WebServlet(name = "InsertarMensaje", urlPatterns = {"/InsertarMensaje"})
public class InsertarMensaje extends HttpServlet {

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
        //Obtenemos el valor del nombre del amigo y el mensaje.
        String friend = request.getParameter("friend");
        String message = request.getParameter("message");
        String user = (String) session.getAttribute("name");
        
        //Variables de error y página.
        String error = "";
        String page = "frames/chat_contenedor.jsp?amigo=" + friend;
                
        //Nos conectamos a la Base de Datos.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia_conectado = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_friend = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_chat = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            //Obtenemos los IDs del usuario conectado y el amigo.
            ResultSet resultado_id_conectado = sentencia_conectado.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
            resultado_id_conectado.next();
            int id_conectado = resultado_id_conectado.getInt("id");
            resultado_id_conectado.close();
            ResultSet resultado_id_friend = sentencia_friend.executeQuery("SELECT id FROM users WHERE user='" + friend + "'");
            resultado_id_friend.next();
            int id_friend = resultado_id_friend.getInt("id");
            resultado_id_friend.close();
            //Con estos IDs ya podemos hacer nuestro INSERT.
            //Obtenemos la fecha actual.
            /*SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fecha_actual = formato.format(new Date());*/
            
            sentencia_chat.executeUpdate("INSERT INTO chats(usera, userb, date, message) VALUES(" + id_conectado + ", " + id_friend + ", (SELECT CURRENT_TIMESTAMP), '" + message + "')");
        }
        //Si ha ocurrido algún error se lo indicamos al usuario.
        catch (SQLException | ClassNotFoundException e) {
            error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
            e.printStackTrace();
        }
        //Y finalmente nos vamos a la página indicada con el error indicado.
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
