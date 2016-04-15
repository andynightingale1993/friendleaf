/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import datos.Feed;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import variables_de_entorno.Variables;

/**
 *
 * @author andrew Servlet que conecta a Base de Datos para obtener los datos del
 * perfil y mandarlos al perfil.jsp.
 */
@WebServlet(name = "PerfilServlet", urlPatterns = {"/PerfilServlet"})
public class PerfilServlet extends HttpServlet {

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
        String error = "";

        String page = "perfil.jsp";
        HttpSession session = request.getSession();
        if (session.getAttribute("error") != null) {
            error = (String) session.getAttribute("error");
        }
        try {
            //Reiniciamos el valor del objeto.
            session.setAttribute("object", null);

            //Obtenemos todos los datos del usuario menos su ID.
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_user = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_like = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Statement sentencia_usuario = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet resultado = sentencia.executeQuery("SELECT name, surname, user, dni, DECODE(password, '" + Variables.CIPHER_KEY + "') as 'pass', address, email, telephone, webpage, country, question1, question2, answer1, answer2, photo FROM users WHERE user='" + session.getAttribute("name") + "'");
            resultado.next();
            String name = resultado.getString("name");
            String surname = resultado.getString("surname");
            String user = resultado.getString("user");
            String dni = resultado.getString("dni");
            String password = resultado.getString("pass");
            String address = resultado.getString("address");
            String email = resultado.getString("email");
            long telephone = resultado.getLong("telephone");
            String webpage = resultado.getString("webpage");
            String country = resultado.getString("country");
            String question1 = resultado.getString("question1");
            String question2 = resultado.getString("question2");
            String answer1 = resultado.getString("answer1");
            String answer2 = resultado.getString("answer2");
            String photo = resultado.getString("photo");

            //Y guardamos todos esos valores en un objeto.
            Perfil perfil = new Perfil();
            perfil.setName(name);
            perfil.setSurname(surname);
            perfil.setUser(user);
            perfil.setDni(dni);
            perfil.setPassword(password);
            perfil.setAddress(address);
            perfil.setEmail(email);
            perfil.setTelephone(telephone);
            perfil.setWebpage(webpage);
            perfil.setCountry(country);
            perfil.setQuestion1(question1);
            perfil.setQuestion2(question2);
            perfil.setAnswer1(answer1);
            perfil.setAnswer2(answer2);
            perfil.setPhoto(photo);

            //Guardamos el objeto en sesión (que luego desde el JSP podremos llamar con un Bean).
            session.setAttribute("object", perfil);

            //Sacamos el ID del usuario amigo.
            ResultSet resultado_conectado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + session.getAttribute("name") + "'");
            resultado_conectado.next();
            int id_amigo = resultado_conectado.getInt("id");
            resultado_conectado.close();

            ResultSet resultado_posts = sentencia.executeQuery("SELECT idpost, user, date, content, photo, video FROM posts WHERE user=" + id_amigo + " ORDER BY date DESC LIMIT 20");
            ResultSet resultado_usuario = null;
            //Creamos una lista de Feeds.
            ArrayList<Feed> lista_feeds = new ArrayList<>();
            while (resultado_posts.next()) {
                //Obtenenos el nombre del usuario en vez del ID.
                resultado_usuario = sentencia_usuario.executeQuery("SELECT user FROM users WHERE id=" + resultado_posts.getInt("user"));
                resultado_usuario.next();
                String usuario = resultado_usuario.getString("user");

                //Creamos un nuevo objeto feed para cada fila y lo
                //agregamos a la lista de feeds.
                Feed feed = new Feed();
                feed.setUser(usuario);
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                feed.setDate(formato.format(resultado_posts.getTimestamp("date")));
                feed.setContent(resultado_posts.getString("content"));
                if (resultado_posts.getString("photo") != null) {
                    feed.setPhoto(resultado_posts.getString("photo"));
                } else {
                    feed.setPhoto("");
                }
                if (resultado_posts.getString("video") != null) {
                    feed.setVideo(resultado_posts.getString("video"));
                } else {
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
                if (resultado_user != null) {
                    resultado_user.close();
                }
                resultado_likes.close();
                lista_feeds.add(feed);
            }

            //Guardamos la lista de feeds como un Bean.
            session.setAttribute("lista_feeds", lista_feeds);
            //Cerramos las conexiones.
            if (resultado_usuario != null) {
                resultado_usuario.close();
            }
            resultado_posts.close();
            //Cerramos la conexión.
            resultado.close();
            sentencia_usuario.close();
            sentencia_like.close();
            sentencia_user.close();
            sentencia.close();
            conexion.close();

            //Si se ha producido algún error se lo indicamos al usuario.            
        } catch (ClassNotFoundException | SQLException ex) {
            error += "Ha ocurrido un error inesperado: " + ex.getMessage() + "<br>";
        } finally {
            //Y finalmente nos vamos a la página indicada con el error indicado.
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
