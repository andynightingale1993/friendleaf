/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import datos.Perfil;
import datos.Validator;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import variables_de_entorno.Variables;

/**
 *
 * @author andrew Servlet Principal que recoge los datos de los JSP para
 * procesar y reenviar. (según desde la página que le entre hará unas cosas u
 * otras).
 */
@WebServlet(name = "ControladorServlet", urlPatterns = {"/ControladorServlet"})
public class ControladorServlet extends HttpServlet {

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
        //Creamos un nuevo Validator que se encarga de validar URLS y EMAILS.
        Validator validar = new Validator();
        String url = "";
        //Si la página que nos viene de request no es nula entonces la obtenemos.
        if (request.getParameter("page") != null) {
            url = request.getParameter("page");
        }
        //Obtenemos la sesión.
        HttpSession session = request.getSession();
        //Si la página es crearcuenta...
        if (url.equals("crearcuenta.jsp")) {
            String page = "crearcuenta.jsp";
            //Reiniciamos el valor de object en session
            session.setAttribute("object", null);

            //Comprobaciones de que los parámetros estén bien.
            boolean hayerror = false;
            session.setAttribute("error", "");
            session.setAttribute("errorC", "");
            String error = "";

            //Obtenemos todos los parámetros....
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String user = request.getParameter("user").toLowerCase();
            String password = request.getParameter("password");
            String passwordr = request.getParameter("passwordr");
            String dnionie = request.getParameter("dnionie");
            String dni = request.getParameter("dni");
            String address = request.getParameter("address1") + "\n" + request.getParameter("address2") + "\n" + request.getParameter("province");
            String email = request.getParameter("email");
            String telephone;
            //Si el telefono está vacío (ya que es opcional) le damos el valor vacío.
            if (request.getParameter("telephone").equals("")) {
                telephone = "";
                //Si no le damos el valor del código internacional + número.
            } else {
                telephone = request.getParameter("countrycode") + request.getParameter("telephone");
            }
            String webpage = request.getParameter("webpage");
            String country = request.getParameter("country");
            String answer1 = request.getParameter("answer1");
            String answer2 = request.getParameter("answer2");
            int question1 = Integer.parseInt(request.getParameter("question1"));
            int question2 = Integer.parseInt(request.getParameter("question2"));
            //Creamos un objeto de tipo Perfil y lo guardamos en sesión.
            Perfil perfil = new Perfil();
            perfil.setName(name);
            perfil.setSurname(surname);
            perfil.setUser(user);
            perfil.setPassword(password);
            perfil.setDni(dni);
            perfil.setAddressline1(request.getParameter("address1"));
            perfil.setAddressline2(request.getParameter("address2"));
            perfil.setEmail(email);
            perfil.setTelefono(telephone);
            perfil.setWebpage(webpage);
            perfil.setAnswer1(answer1);
            perfil.setAnswer2(answer2);
            perfil.setPregunta1(question1);
            perfil.setPregunta2(question2);
            session.setAttribute("object", perfil);
            String pregunta1, pregunta2;
            //Según el número de la pregunta guardaremos una cadena u otra.
            switch (question1) {
                case 1: {
                    pregunta1 = "¿Cuál es el nombre de tu mascota favorita?";
                }
                break;
                case 2: {
                    pregunta1 = "¿Cuál fue tu primer teléfono?";
                }
                break;
                case 3: {
                    pregunta1 = "¿Cuál es el nombre de tu madre?";
                }
                break;
                case 4: {
                    pregunta1 = "¿Quién es tu mejor amigo?";
                }
                break;
                default: {
                    pregunta1 = "¿Cuál es tu género favorito de película?";
                }
            }

            switch (question2) {
                case 1: {
                    pregunta2 = "¿Cuál es el nombre de tu mascota favorita?";
                }
                break;
                case 2: {
                    pregunta2 = "¿Cuál fue tu primer teléfono?";
                }
                break;
                case 3: {
                    pregunta2 = "¿Cuál es el nombre de tu madre?";
                }
                break;
                case 4: {
                    pregunta2 = "¿Quién es tu mejor amigo?";
                }
                break;
                default: {
                    pregunta2 = "¿Cuál es tu género favorito de película?";
                }
            }

            //Conectamos a la base de datos e insertamos los datos.
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky"); //Cambiar estos datos según la BD
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
                }
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
                        long numerodni = Long.parseLong(dni.substring(0, 8));
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
                if (!telephone.equals("")) {
                    try {
                        Long.parseLong(telephone);
                    } catch (NumberFormatException e) {
                        hayerror = true;
                        error += "EL teléfono introducido no es válido.<br>" + e.getMessage();
                    }
                }

                //Si la página Web se ha introducido y no es válida...
                if (!webpage.equals("") && !validar.isURL(webpage)) {
                    //...indicamos el error producido.
                    hayerror = true;
                    error += "La página web introducida no es válida.<br>";
                }

                //Finalmente, si no hay ningún error insertamos los datos en la base
                //de datos.
                if (!hayerror) {
                    //Si no ha ocurrido ningún error creamos un directorio de fotos para el usuario.
                    //IMPORTANTE!! ESTE DIRECTORIO HABRÁ QUE CAMBIARLO!!
                    File directorioactual = new File(".");
                    String ruta = directorioactual.getCanonicalPath();
                    File directoriocrear = new File(ruta + "/webapps/FriendLeaf/photos/" + user);
                    if (!directoriocrear.mkdirs()) {
                        hayerror = true;
                        error += "Error al crear el directorio " + directoriocrear + " para el usuario " + user;
                    } 
                    directoriocrear = new File(ruta + "/webapps/FriendLeaf/videos/" + user);
                    if (!directoriocrear.mkdirs()) {
                        hayerror = true;
                        error += "Error al crear el directorio " + directoriocrear + " para el usuario " + user;
                    }
                    if (!hayerror) {
                        session.setAttribute("name", user);
                        sentencia.executeUpdate("INSERT INTO users (name, surname, user, dni, password, address, email, telephone, webpage, country, question1, question2, answer1, answer2, photo, role) VALUES('" + name + "', '" + surname + "', '" + user + "', '" + dni + "', (SELECT ENCODE('" + password + "', '" + Variables.CIPHER_KEY + "')), '" + address + "', '" + email + "', " + (telephone.equals("") ? 0 : Long.parseLong(telephone)) + ", '" + webpage + "', '" + country + "', '" + pregunta1 + "', '" + pregunta2 + "', '" + answer1 + "', '" + answer2 + "', '', 'user');");
                        page = "gustos.jsp";
                    }

                }

                //Cerramos las conexiones de la Base de Datos.
                resultado.close();
                sentencia.close();
                conexion.close();

                //Si ha ocurrido alguna Excepción imprimimos el error indicado.
            } catch (ClassNotFoundException | SQLException | NumberFormatException e) {
                error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                e.printStackTrace();
            } finally {
                //Y después de todo asignamos el error y nos vamos a la página indicada.
                session.setAttribute("errorC", error);
                request.getRequestDispatcher(page).forward(request, response);
                //response.sendRedirect(page);
            }
            //Si hemos venido desde gustos.jsp...
        } else if (url.equals("gustos.jsp")) {
            //...hay que comprobar los valores de los Checkbox e introducir los datos
            //en la base de datos.
            session.setAttribute("error", "");
            String error = "";
            String page = "feed.jsp";
            try {
                //Si hemos venido desde gustos.jsp entonces tendremos que guardar
                //estos datos en la tabla de gustos.
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky"); //Cambiar estos datos según la BD
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String user = (String) session.getAttribute("name");
                ResultSet resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                int idusuario = 0;
                //Obtenemos los valores del usuario para poder insertar ese ID.
                while (resultado.next()) {
                    idusuario = resultado.getInt("id");
                }
                //Obtenemos los parámetros del formulario.
                String[] generos = request.getParameterValues("genero");
                String[] videojuegos = request.getParameterValues("videojuegos");
                String[] cine = request.getParameterValues("cine");
                String[] literatura = request.getParameterValues("literatura");
                String[] arte = request.getParameterValues("arte");
                //Insertamos los datos a la tabla likes (primero borramos la que existe si
                //existe).
                sentencia.executeUpdate("DELETE FROM likes WHERE id_usuario=" + idusuario);
                sentencia.executeUpdate("INSERT INTO likes VALUES(" + idusuario + ", '"
                        + Arrays.toString(generos) + "', '"
                        + Arrays.toString(videojuegos) + "', '"
                        + Arrays.toString(cine) + "', '"
                        + Arrays.toString(literatura) + "', '"
                        + Arrays.toString(arte) + "')");
                page = "feed.jsp";
                //Cerramos las conexiones a BDD.
                resultado.close();
                sentencia.close();
                conexion.close();
                //Si ha ocurrido algún error se lo indicamos al usuario.
            } catch (ClassNotFoundException | SQLException ex) {
                error += "Ha ocurrido un error inesperado: " + ex.getMessage() + "<br>";
                page = "index.jsp";
            } finally {
                //Y finalmente nos vamos a la página indicado con el error (si es que la hay).
                session.setAttribute("error", error);
                request.getRequestDispatcher(page).forward(request, response);
                //response.sendRedirect(page);

            }
        } else if (url.equals("index.jsp")) {
            String page = "index.jsp";
            String error = "";
            try {
                //Si hemos venido desde index.jsp entonces tenemos que ver si
                //el usuario y contraseña introducidos son correctos.
                Class.forName("com.mysql.jdbc.Driver");
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky"); //Cambiar estos datos según la BD
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                Statement sentencia_admin = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                String user = request.getParameter("name");
                //Obtenemos la contraseña.
                ResultSet resultado = sentencia.executeQuery("SELECT DECODE(password, '" + Variables.CIPHER_KEY + "') as 'pass' FROM users WHERE user='" + user + "'");
                String password = "";
                String passwordindex = request.getParameter("password");
                while (resultado.next()) {
                    password = resultado.getString("pass");
                }
                //Si la contraseña es correcta nos iremos al Feed del usuario.
                if (password.equals(passwordindex)) {
                    page = "FeedServlet";
                    session.setAttribute("name", user);
                    //Obtenemos el ROL del usuario.
                    ResultSet resultado_admin = sentencia_admin.executeQuery("SELECT role FROM users WHERE user='" + user + "'");
                    resultado_admin.next();
                    String rol = resultado_admin.getString("role");
                    //Si es administrador guardamos ese valor en sesión y nos vamos al
                    //Servlet que carga los datos del administrador.
                    if (rol.equals("admin")) {
                        session.setAttribute("admin", "admin");
                        page = "AdminServlet";
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
                session.setAttribute("error", error);
                request.getRequestDispatcher(page).forward(request, response);
            }
            //En cualquier otro caso de haber venido de otra página nos vamos directamente a index.jsp.
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    //Método para que, pasando el número de un DNI, se calcule su letra.
    public static String obtenerLetraDNIoNIE(long numero) {
        String retorno = "";
        int resto = (int) (numero % 23);
        switch (resto) {
            case 0: {
                retorno = "T";
            }
            break;
            case 1: {
                retorno = "R";
            }
            break;
            case 2: {
                retorno = "W";
            }
            break;
            case 3: {
                retorno = "A";
            }
            break;
            case 4: {
                retorno = "G";
            }
            break;
            case 5: {
                retorno = "M";
            }
            break;
            case 6: {
                retorno = "Y";
            }
            break;
            case 7: {
                retorno = "F";
            }
            break;
            case 8: {
                retorno = "P";
            }
            break;
            case 9: {
                retorno = "D";
            }
            break;
            case 10: {
                retorno = "X";
            }
            break;
            case 11: {
                retorno = "B";
            }
            break;
            case 12: {
                retorno = "N";
            }
            break;
            case 13: {
                retorno = "J";
            }
            break;
            case 14: {
                retorno = "Z";
            }
            break;
            case 15: {
                retorno = "S";
            }
            break;
            case 16: {
                retorno = "Q";
            }
            break;
            case 17: {
                retorno = "V";
            }
            break;
            case 18: {
                retorno = "H";
            }
            break;
            case 19: {
                retorno = "L";
            }
            break;
            case 20: {
                retorno = "C";
            }
            break;
            case 21: {
                retorno = "K";
            }
            break;
            case 22: {
                retorno = "E";
            }
            break;

        }
        return retorno;
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
