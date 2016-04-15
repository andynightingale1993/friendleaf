/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desde_fuera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import javax.servlet.http.Part;
import variables_de_entorno.Variables;

/**
 *
 * @author andrew Este Servlet se encarga desde, desde fuera, hacer que una foto
 * o un video que haya encontrado una persona se pueda compartir.
 */
@WebServlet(name = "CompartirServlet", urlPatterns = {"/CompartirServlet"})
public class CompartirServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        String funcion = request.getParameter("funcion");
        String page = "compartir/inicio_sesion.jsp?compartir=true?";
        String error = "";
        HttpSession session = request.getSession();
        //Si el valor de funcion es autentificarse entonces tenemos que
        //comprobar que los datos que hayan metido sean correctos.
        if (funcion.equals("autentificarse")) {
            //Obtenemos el vídeo y la foto.
            String photo = request.getParameter("photo");
            String video = request.getParameter("video");
            try {

                //Si hemos venido desde index.jsp entonces tenemos que ver si
                //el usuario y contraseña introducidos son correctos.
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky"); //Cambiar estos datos según la BD
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement sentencia_admin = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //Obtenemos la contraseña.
                String user = request.getParameter("user");

                ResultSet resultado = sentencia.executeQuery("SELECT DECODE(password, '" + Variables.CIPHER_KEY + "') AS 'pass' FROM users WHERE user='" + user + "'");
                String password = "";
                String passwordindex = request.getParameter("password");
                while (resultado.next()) {
                    password = resultado.getString("pass");
                }
                //Si la contraseña es correcta nos iremos al Feed del usuario.
                if (password.equals(passwordindex)) {

                    page = "compartir/formulario.jsp?compartir=true";
                    //Obtenemos el ROL del usuario.
                    ResultSet resultado_admin = sentencia_admin.executeQuery("SELECT role FROM users WHERE user='" + user + "'");
                    resultado_admin.next();
                    String rol = resultado_admin.getString("role");
                    //Si es administrador entonces no puede compartir
                    if (rol.equals("admin")) {
                        page = "compartir/inicio_sesion.jsp?compartir=true";
                        error += "El administrador no puede compartir cosas";
                    } else {
                        session.setAttribute("usuario_compartir", user);
                    }
                    //Si no es correcta ha producido algún error y se lo indicamos.
                } else {
                    error += "Fallo de Usuario/Contraseña. Inténtelo de nuevo.<br>";
                }
                //Cerramos las conexiones a BDD.
                resultado.close();
                sentencia_admin.close();
                sentencia.close();
                conexion.close();
                //Si hay una excepción indicamos el error producido.
            } catch (ClassNotFoundException | SQLException ex) {
                error += "Ha ocurrido un error inesperado: " + ex.getMessage() + "<br>";
            } finally {
                //Y finalmente nos vamos a la página indicada con el error indicado (si es que lo hay).
                //Añadimos los atributos de photo y video al enlace para que no se pierdan datos.
                page += "&error=" + error + "&photo=" + photo + "&video=" + video;
                response.sendRedirect(page);
            }
            //Si la funcion es insertar_post entonces tenemos que crear los archivos
            //de foto y video (si es que los haya) y guardar la fila del post en la
            //Base de Datos.
        } else if (funcion.equals("insertar_post")) {
            
            //Obtenemos quién es el usuario conectado y la fecha de hoy.
            /*SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String date = formato.format(new Date());*/
            String user = (String) session.getAttribute("usuario_compartir");
            //Buscamos a partir del nombre de usuario su ID.

            //Obtenemos la foto que se ha metido por el request.
            String photo = request.getParameter("photo");

            //Obtenemos el vídeo que se ha metido por el request.
            String video = request.getParameter("video");
            
            String content = request.getParameter("content");
            page = "compartir/formulario.jsp?compartir=true&photo=" + photo + "&video=" + video;
            
            try {
                Class.forName("com.mysql.jdbc.Driver");

                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet resultado_id = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                resultado_id.next();
                int id_usuario = resultado_id.getInt("id");
                resultado_id.close();
                //Y ahora con ese ID ya podemos insertar los datos.
                sentencia.executeUpdate("INSERT INTO posts(user, date, content, photo, video) VALUES(" + id_usuario + ", (SELECT CURRENT_TIMESTAMP), '" + content + "', '" + (photo == null ? "" : photo) + "', '" + (video == null ? "" : video) + "')");
                

                //Si todo ha ido bien nos vamos a cerrar.jsp
                page = "compartir/cerrar.jsp?compartir=true";

                //Cerramos las conexiones con la BDD.
                sentencia.close();
                conexion.close();
                //Si ha ocurrido algún error se lo indicamos al usuario.
            } catch (ClassNotFoundException | SQLException e) {
                error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
            } //Y finalmente nos vamos a la página indicada con el error indicado.
            finally {
                session.setAttribute("error", error);
                response.sendRedirect(page);
            }
        }

    }
    
    //Método que devuelve el nombre de un archivo pasándole un enlace.
    public static String getFileName(String url) {
        return "";
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
