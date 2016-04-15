/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import datos.Chat;
import datos.Feed;
import datos.Fotos;
import datos.ListaChats;
import datos.Perfil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
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
 * Servlet que se encarga de obtener los datos de un amigo e imprimirlo en
 * datosamigo.jsp
 */
@WebServlet(name = "ObtenerDatosChat", urlPatterns = {"/ObtenerDatosChat"})
public class ObtenerDatosChat extends HttpServlet {

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
        //Obtenemos el parámetro del usuario amigo
        String usuarioamigo = request.getParameter("amigo");
        
        //Datos de página y error.
        String page = "frames/chatamigo.jsp";
        String error = "";
        
        //Obtenemos la sesión
        HttpSession session = request.getSession();
        session.setAttribute("friend", null);
        //Nos conectamos con la BDD.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_friend = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_chats = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_chats_user = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Obtenemos todos los datos del chat del usuario amigo y mío (pero para ello necesito obtener los IDs).
            ResultSet resultado_id = sentencia.executeQuery("SELECT id FROM users WHERE user='" + session.getAttribute("name") + "'");
            resultado_id.next();
            int id_conectado = resultado_id.getInt("id");
            resultado_id.close();
            ResultSet resultado_id_amigo = sentencia_friend.executeQuery("SELECT id FROM users WHERE user='" + usuarioamigo + "'");
            resultado_id_amigo.next();
            int id_amigo = resultado_id_amigo.getInt("id");
            //Con estos dos IDs ya podemos obtener todos los chats entre los dos.
            ResultSet resultado_chats = sentencia_chats.executeQuery("SELECT usera, userb, id_chat, date, message FROM chats WHERE usera= " + id_conectado + " AND userb=" + id_amigo + " OR usera=" + id_amigo + " AND userb=" + id_conectado + " ORDER BY id_chat DESC LIMIT 10");
            //Creamos una lista donde vamos a guardar todos estos chats.
            ListaChats lista_chats = new ListaChats();
            //Guardamos en él el amigo.
            lista_chats.setAmigo(usuarioamigo);
            //Creamos una lista de objetos Chat.
            ArrayList<Chat> chats = new ArrayList<>();
            ResultSet resultado_usuario_chat = null;
            while (resultado_chats.next()) {
                //De cada usera sacamos el nombre de usuario para guardarlo en el chat.
                resultado_usuario_chat = sentencia_chats_user.executeQuery("SELECT user FROM users WHERE id=" + resultado_chats.getInt("usera"));
                resultado_usuario_chat.next();
                String usuario_chat = resultado_usuario_chat.getString("user");
                resultado_usuario_chat.close();
                //Creamos un objeto chat.
                Chat chat = new Chat();
                chat.setId_chat(resultado_chats.getInt("id_chat"));
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                chat.setDate(formato.format(resultado_chats.getTimestamp("date")));
                chat.setUsuario(usuario_chat);
                chat.setMessage(resultado_chats.getString("message"));
                //Añadimos ese chat a la lista.
                chats.add(chat);
            }
            
            //Asignamos al objeto ListaChats la lista de los chats.
            lista_chats.setLista_chats(chats);
            
            //Guardamos la lista de chats como un Bean.
            session.setAttribute("lista_chats", lista_chats);
            //Cerramos las conexiones.
            if (resultado_usuario_chat != null) resultado_usuario_chat.close();
            resultado_chats.close();
            sentencia_chats.close();
            sentencia_friend.close();
            sentencia.close();
            conexion.close();
        }
        //Si ha ocurrido algún error se lo indicamos al usuario en amigos.jsp.
        catch (ClassNotFoundException | SQLException ex) {
            error += "Ha ocurrido un error inesperado: " + ex.getMessage();
            page = "frames/chat.jsp";
        }
        //Y finalmente nos redirigimos a la página que sea según lo que haya pasado.
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
