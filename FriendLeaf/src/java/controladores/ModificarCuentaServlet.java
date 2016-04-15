/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import static controladores.ControladorServlet.obtenerLetraDNIoNIE;
import datos.Perfil;
import datos.Validator;
import java.io.IOException;
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
import variables_de_entorno.Variables;

/**
 *
 * @author andrew Este Servlet modifica los datos según los que haya recibido
 * por parámetro.
 */
@WebServlet(name = "ModificarCuentaServlet", urlPatterns = {"/ModificarCuentaServlet"})
public class ModificarCuentaServlet extends HttpServlet {

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
        String page = "PerfilServlet";
        boolean hayerror = false;
        Validator validar = new Validator();
        HttpSession session = request.getSession();
        session.setAttribute("error", "");
        Perfil perfil = (Perfil) session.getAttribute("object");

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
            Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //Obtenemos los datos de perfil.jsp con request.
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String password = request.getParameter("password");
            String passwordr = request.getParameter("passwordr");
            String dnionie = request.getParameter("dnionie");
            String dni = request.getParameter("dni");
            String address = request.getParameter("address") + "\n" + request.getParameter("province");
            String email = request.getParameter("email");
            String telephone = request.getParameter("telephone");
            String webpage = request.getParameter("webpage");
            String answer1 = request.getParameter("answer1");
            String answer2 = request.getParameter("answer2");

            ResultSet resultado;
            /*
            Este Código no es necesario ya que el UPDATE nos modificará el usuario como él mismo.
            //Consultamos si existe ya el usuario.
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM users WHERE user='" + user + "'");
            String usuario = "";
            while (resultado.next()) {
                usuario = resultado.getString("user");
            }
            //Si existe hay un error y se lo indicamos.
            if (usuario.equals(user)) {
                hayerror = true;
                error += "El usuario ya existe. Escoja otro.<br>";
            }*/
            //Si las contraseñas no coinciden hay un error y se lo indicamos.
            if (!password.equals(passwordr)) {
                hayerror = true;
                error += "Las contraseñas no coinciden. Inténtelo de nuevo<br>";
            }
            //Según se haya elegido DNI, NIE o Pasaporte...
            switch (dnionie) {
                case "dni": {
                    //...comprobamos que el DNI es válido (módulo de 23)...
                    dni = dni.substring(0, 9).toUpperCase();
                    long numerodni = 0;
                    try {
                        numerodni = Long.parseLong(dni.substring(0, 8));
                    } catch (NumberFormatException ex) {
                        hayerror = true;
                        error += "El DNI introducido no es válido.<br>";
                    }
                    String letra = obtenerLetraDNIoNIE(numerodni);
                    if (!letra.equals(dni.substring(8, 9))) {
                        hayerror = true;
                        error += "EL DNI introducido no es válido.<br>";
                    }
                }
                break;
                //...si es NIE no hacemos comprobaciones...
                case "nie": {
                    dni = dni.substring(0, 9).toUpperCase();
                    /*int numerodni = Long.parseLong(dni.substring(1, 8));
                     String letra = obtenerLetraDNIoNIE(numerodni);
                     if (!letra.equals(dni.substring(8, 9))) {
                     hayerror = true;
                     error += "EL NIE introducido no es válido.<br>";
                     }*/
                }
                break;
                //...y si es Pasaporte comprobamos si es un número.
                case "pasaporte": {
                    try {
                        Long.parseLong(dni);
                    } catch (NumberFormatException e) {
                        hayerror = true;
                        error += "EL pasaporte introducido no es válido.<br>";
                    }
                }
                break;
                //Si se da otro caso entonces ha ocurrido un error.
                default: {
                    hayerror = true;
                    error += "Ha ocurrido un error inesperado en el DNI/NIE/Pasaporte.<br>";
                }
            }
            //Si el email se ha introducido pero no es válido...
            if (!email.equals("") && !validar.isEmail(email)) {
                //...indicamos que hay un error.
                hayerror = true;
                error += "El correo electrónico introducido no es válido.<br>";
            }
            //Intentamos convertir el teléfono a número y si no funciona imprimimos un error.
            try {
                Long.parseLong(telephone);
            } catch (NumberFormatException e) {
                hayerror = true;
                error += "EL teléfono introducido no es válido.<br>" + e.getMessage();
            }

            //Si la página Web se ha introducido y no es válida...
            if (!webpage.equals("") && !validar.isURL(webpage)) {
                //...indicamos el error producido.
                hayerror = true;
                error += "La página web introducida no es válida.<br>";
            }
            
            //Si las preguntas secretas no son las correctas...
            resultado = sentencia.executeQuery("SELECT answer1, answer2 FROM users WHERE user='" + session.getAttribute("name") + "'");
            resultado.next();
            String respuesta1 = resultado.getString("answer1");
            String respuesta2 = resultado.getString("answer2");
            if (!respuesta1.equals(answer1) || !respuesta2.equals(answer2)) {
                hayerror = true;
                error += "Una o más de las Respuestas Secretas no son correctas.<br>";
            }

            //Finalmente, si no hay ningún error insertamos los datos en la base
            //de datos.
            if (!hayerror) {
                //Obtenemos el valor del ID de usuario (ya que puede que modifiquemos el usuario en sí).
                resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + session.getAttribute("name") + "'");
                int id = 0;
                while (resultado.next()) {
                    id = resultado.getInt("id");
                }
                //Ponemos que el perfil ya no sea modificable.
                perfil.setModificar(false);
                session.setAttribute("object", perfil);
                //Un Update por cada parámetro que se reciba.
                sentencia.executeUpdate("UPDATE users SET name='" + name + "' WHERE id=" + id);
                sentencia.executeUpdate("UPDATE users SET surname='" + surname + "' WHERE id=" + id);
                sentencia.executeUpdate("UPDATE users SET password=ENCODE('" + password + "', '" + Variables.CIPHER_KEY + "') WHERE id=" + id);
                sentencia.executeUpdate("UPDATE users SET dni='" + dni + "' WHERE id=" + id);
                sentencia.executeUpdate("UPDATE users SET address='" + address + "' WHERE id=" + id);
                sentencia.executeUpdate("UPDATE users SET email='" + email + "' WHERE id=" + id);
                sentencia.executeUpdate("UPDATE users SET telephone=" + telephone + " WHERE id=" + id);
                sentencia.executeUpdate("UPDATE users SET webpage='" + webpage + "' WHERE id=" + id);
                
            }

            //Cerramos las conexiones de la Base de Datos.
            resultado.close();
            sentencia.close();
            conexion.close();

                //Si ha ocurrido alguna Excepción imprimimos el error indicado.
        } catch (SQLException | ClassNotFoundException ex) {
            error += "Ha ocurrido un error inesperado: " + ex.getMessage();
        } finally {
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
