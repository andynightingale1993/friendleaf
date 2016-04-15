/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import datos.Feed;
import datos.Fotos;
import java.io.IOException;
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
 * Servlet por el que hay que pasar antes de ir a feed.jsp
 * para recoger los datos de la BDD (en el orden de la fecha
 * descendiente).
 */
@WebServlet(name = "FeedServlet", urlPatterns = {"/FeedServlet"})
public class FeedServlet extends HttpServlet {

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
        String page = "feed.jsp";
        String error = "";
        
        //Obtenemos la sesión.
        HttpSession session = request.getSession();
        
        //Nos conectamos a la Base de Datos.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_usuario = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_like = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_user = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //Sacamos el ID del usuario conectado.
            ResultSet resultado_conectado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + session.getAttribute("name") + "'");
            resultado_conectado.next();
            int id_conectado = resultado_conectado.getInt("id");
            resultado_conectado.close();
            
            ResultSet resultado_posts = sentencia.executeQuery("SELECT idpost, user, date, content, photo, video FROM posts WHERE user IN (SELECT userb FROM friends WHERE usera=" + id_conectado + ") OR user= " + id_conectado + " ORDER BY date DESC LIMIT 20");
            ResultSet resultado_usuario = null;
            //Creamos una lista de Feeds.
            ArrayList<Feed> lista_feeds = new ArrayList<>();
            while (resultado_posts.next()) {
                //Obtenenos el nombre del usuario en vez del ID.
                resultado_usuario = sentencia_usuario.executeQuery("SELECT user FROM users WHERE id=" + resultado_posts.getInt("user"));
                resultado_usuario.next();
                String usuario = resultado_usuario.getString("user");
                String ruta = "photos/" + usuario + "/";
                String ruta_video = "videos/" + usuario + "/";
                
                //Creamos un nuevo objeto feed para cada fila y lo
                //agregamos a la lista de feeds.
                Feed feed = new Feed();
                feed.setIdpost(resultado_posts.getInt("idpost"));
                feed.setUser(usuario);
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                feed.setDate(formato.format(resultado_posts.getTimestamp("date")));
                feed.setContent(resultado_posts.getString("content"));
                if (resultado_posts.getString("photo") != null) {
                    feed.setPhoto(resultado_posts.getString("photo"));
                }
                else {
                    feed.setPhoto("");
                }
                if (resultado_posts.getString("video") != null) {
                    feed.setVideo(resultado_posts.getString("video"));
                }
                else {
                    feed.setVideo("");
                }
                //Obtenemos los "likes" de ese post y los insertamos a un conjunto de enteros.
                ResultSet resultado_likes = sentencia_like.executeQuery("SELECT iduser FROM post_likes WHERE idpost=" + resultado_posts.getInt("idpost"));
                ResultSet resultado_user = null;
                ArrayList<String> lista_likes = new ArrayList<>();
                //Recorremos esos resultados y guardamos los enteros en el array.
                while (resultado_likes.next()) {
                    Integer user_like = resultado_likes.getInt("iduser");
                    //Para ese entero obtengo su nombre.
                    resultado_user = sentencia_user.executeQuery("SELECT user FROM users WHERE id=" + user_like);
                    resultado_user.next();
                    String user_like_add = resultado_user.getString("user");
                    resultado_user.close();
                    lista_likes.add(user_like_add);
                }
                feed.setLikes(lista_likes);
                if (resultado_user != null) resultado_user.close();
                resultado_likes.close();
                lista_feeds.add(feed);
            }
            //Obtenemos las fotos de ese usuario y las insertamos en una lista de tipo Fotos.
            Fotos fotos = new Fotos();
            ArrayList<String> listafotos = fotos.getFotos();
            //Obtenemos las fotos y la ruta del usuario.
            ResultSet resultadofotos = sentencia.executeQuery("SELECT photo FROM photos WHERE user=" + id_conectado);
            while (resultadofotos.next()) {
                listafotos.add(resultadofotos.getString("photo"));
            }
            fotos.setFotos(listafotos);
            session.setAttribute("photos", fotos);
            //Guardamos la lista de feeds como un Bean.
            session.setAttribute("lista_feeds", lista_feeds);
            
            //Cerramos las conexiones de BDD.
            if (resultado_usuario != null) resultado_usuario.close();
            resultado_posts.close();
            sentencia_user.close();
            sentencia_like.close();
            sentencia_usuario.close();
            sentencia.close();
            conexion.close();
        }
        //Si ha ocurrido algún error se lo indicamos a usuario.
        catch (SQLException | ClassNotFoundException e) {
            error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
        }
        //Finalmente nos vamos a la página indicada con el error indicado.
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
