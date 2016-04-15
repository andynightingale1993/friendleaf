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
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ubuntu
 */
@WebServlet(name = "AgregarAmigo", urlPatterns = {"/AgregarAmigo"})
public class AgregarAmigo extends HttpServlet {

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
        session.setAttribute("error", null);
        //Página y error.
        String page = "AmigosServlet";
        String error = "";
        boolean hayerror = false;
        boolean noinsertarrequest = false;
        //Obtenemos la cadena del usuario que queremos añadir.
        String usuarioagregar = request.getParameter("amigo");
        String usuarioconectado = (String) session.getAttribute("name");
        //Variables para guardar las IDs.
        int idagregar = 0, idconectado;
        //Comprobamos si existe ese amigo en la base de datos.
        try {
            //Si intento agregar a mí mismo que salte el error.
            if (usuarioagregar.equals(usuarioconectado)) {
                error += "No puedes agregarte a ti mismo!<br>";
                page = "amigos.jsp";
            } else {
                Class.forName("com.mysql.jdbc.Driver");
                //Obtenemos el ID del usuario que queremos agregar.
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet resultadousuario = sentencia.executeQuery("SELECT id FROM users WHERE user='" + usuarioagregar + "'");
                //Si ese ID existe...
                if (resultadousuario.next()) {
                    //...lo obtenemos...
                    idagregar = resultadousuario.getInt("id");
                    //...en otro caso...
                } else {
                    //...el usuario ha metido un amigo incorrecto.
                    hayerror = true;
                    error += "Ese usuario no existe! Inténtelo de nuevo.<br>";
                    page = "amigos.jsp";
                }
                resultadousuario.close();
                //Obtenemos el ID del usuario conectado.
                //También hay que comprobar que ese amigo no es el administrador.
                if (!hayerror) {
                    ResultSet resultado_admin = sentencia.executeQuery("SELECT role FROM users WHERE id=" + idagregar);
                    resultado_admin.next();
                    String rol = resultado_admin.getString("role");
                    //Si el usuario a agregar es el administrador entonces decimos que
                    //no existe (nadie puede agregar al administrador como amigo).
                    if (rol.equals("admin")) {
                        hayerror = true;
                        error += "Ese usuario no existe! Inténtelo de nuevo.<br>";
                        page = "amigos.jsp";
                    }
                    resultado_admin.close();
                }

                //Ahora tenemos que ver si existe ya esa relación de amistad (sólo si no hay errores).
                if (!hayerror) {
                    ResultSet resultadousuarioconectado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + usuarioconectado + "'");
                    resultadousuarioconectado.next();
                    idconectado = resultadousuarioconectado.getInt("id");
                    resultadousuarioconectado.close();
                    ResultSet resultadoamigo = sentencia.executeQuery("SELECT usera, userb FROM friends WHERE usera=" + idconectado + " AND userb=" + idagregar);
                    //Si ya existe...
                    if (resultadoamigo.next()) {
                        //...indicamos el error.
                        hayerror = true;
                        error += "Esa relación de amistad ya existe! Inténtelo de nuevo.<br>";
                        page = "amigos.jsp";
                        //En otro caso...
                    } else {
                        //Comprobamos si existe la petición, y si existe la eliminamos.
                        ResultSet resultadoamigo_request = sentencia.executeQuery("SELECT usera, userb FROM friend_requests WHERE usera=" + idconectado + " AND userb=" + idagregar);
                        //Si existe...
                        if (resultadoamigo_request.next()) {
                            //...lo eliminamos.
                            sentencia.executeUpdate("DELETE FROM friend_requests WHERE usera=" + idconectado + " AND userb=" + idagregar);
                            noinsertarrequest = true;

                        }
                        resultadoamigo_request.close();
                        //...insertamos las nuevas filas de amistad en la Base de Datos y hemos terminado.
                        if (!noinsertarrequest) {
                            sentencia.executeUpdate("INSERT INTO friend_requests VALUES(" + idagregar + ", " + idconectado + ")");
                        }
                        sentencia.executeUpdate("INSERT INTO friends VALUES(" + idconectado + ", " + idagregar + ")");
                        session.setAttribute("message", "<script type=\"text/javascript\">alert('Amigo agregado con éxito!')</script>");
                    }
                    resultadoamigo.close();
                }

                sentencia.close();
                conexion.close();
            }
        } //Si ha ocurrido algún error se lo indicamos al usuario en amigos.jsp.
        catch (ClassNotFoundException | SQLException ex) {
            error += "Ha ocurrido un error inesperado: " + ex.getMessage();
            page = "amigos.jsp";
            ex.printStackTrace();
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
