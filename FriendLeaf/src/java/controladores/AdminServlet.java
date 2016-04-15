/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import static controladores.ControladorServlet.obtenerLetraDNIoNIE;
import datos.Chat;
import datos.Feed;
import datos.Perfil;
import datos.Validator;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
;
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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import variables_de_entorno.Variables;

/**
 *
 * @author andrew
 */


@WebServlet(name = "AdminServlet", urlPatterns = {"/AdminServlet"})
public class AdminServlet extends HttpServlet {

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
        //Variables de página y error.
        String page = "panel_admin.jsp";
        String error = "";
        //Obtenemos el parámetro de la página de la que ha venido.
        String pagina = request.getParameter("page");
        //Si hemos venido de index necesitamos sacar la tabla de los
        //usuarios para que el administrador pueda ver tanto sus 
        //datos como sus posts, fotos y chats para eliminar cualquier
        //cosa que vea que no debería estar allí, o eliminar directamente
        //un usuario de la Base de Datos.
        if (pagina.equals("index.jsp")) {
            //Obtenemos la lista de los usuarios de la BD.
            try {
                Class.forName("com.mysql.jdbc.Driver");

                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                //Obtenemos todos los usuarios que NO sean el administrador.
                ResultSet resultado_usuarios = sentencia.executeQuery("SELECT user FROM users WHERE role NOT LIKE '%admin%'");
                //Vamos a guardar esos usuarios en un ArrayList.
                ArrayList<String> lista_usuarios = new ArrayList<>();
                while (resultado_usuarios.next()) {
                    //Añadimos los usuarios al ArrayList.
                    lista_usuarios.add(resultado_usuarios.getString("user"));
                }
                //Guardamos esa lista en sesión.
                session.setAttribute("lista_usuarios", lista_usuarios);
            } //Si ha ocurrido algún error se lo indicamos al usuario.
            catch (ClassNotFoundException | SQLException ex) {
                error += "Ha ocurrido un error inesperado: " + ex.getMessage() + "<br>";
            } //Y finalmente nos vamos a la página indicada con el error indicado.
            finally {
                session.setAttribute("error", error);
                request.getRequestDispatcher(page).forward(request, response);
            }
        } //Sin embargo, si hemos venido de panel_admin.jsp entonces tenemos
        //que obtener los datos de un usuario además de las tres tablas de
        //fotos, posts y chats para que podamos modificarlos o eliminarlos.
        //Estas cosas las presentaremos en admin_usuario.jsp.
        else if (pagina.equals("panel_admin.jsp")) {
            //Obtenemos el usuario.
            String user = request.getParameter("user");
            //A partir de ese usuario tenemos que obtener sus datos.
            try {
                //Nos conectamos a la Base de Datos.
                Class.forName("com.mysql.jdbc.Driver");

                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ResultSet resultado_usuario = sentencia.executeQuery("SELECT id, name, surname, dni, DECODE(password, '" + Variables.CIPHER_KEY + "') as 'pass', address, email, telephone, webpage, country, photo FROM users WHERE user='" + user + "'");
                resultado_usuario.next();
                //Vamos a necesitar el ID para seleccionar los datos de las otras tablas.
                int id_usuario = resultado_usuario.getInt("id");
                //Creamos un objeto perfil y guardamos en él los datos del usuario.
                Perfil perfil = new Perfil();
                perfil.setName(resultado_usuario.getString("name"));
                perfil.setSurname(resultado_usuario.getString("surname"));
                perfil.setUser(user);
                perfil.setDni(resultado_usuario.getString("dni"));
                perfil.setAddress(resultado_usuario.getString("address"));
                perfil.setEmail(resultado_usuario.getString("email"));
                perfil.setTelephone(resultado_usuario.getLong("telephone"));
                perfil.setWebpage(resultado_usuario.getString("webpage"));
                perfil.setCountry(resultado_usuario.getString("country"));
                perfil.setPassword(resultado_usuario.getString("pass"));
                perfil.setPhoto(resultado_usuario.getString("photo"));
                resultado_usuario.close();
                //Guardamos el objeto perfil en session.
                session.setAttribute("user", perfil);
                //Ahora obtenemos todas las fotos de ese usuario.
                ResultSet resultado_fotos = sentencia.executeQuery("SELECT * FROM photos WHERE user=" + id_usuario);
                //Guardamos todas las fotos en una lista de String.
                ArrayList<String> lista_fotos = new ArrayList<>();
                while (resultado_fotos.next()) {
                    lista_fotos.add(resultado_fotos.getString("photo"));
                }
                resultado_fotos.close();
                //Guardamos esa lista en sesión.
                session.setAttribute("lista_fotos", lista_fotos);

                //Ahora obtenemos los posts.
                ResultSet resultado_posts = sentencia.executeQuery("SELECT * FROM posts WHERE user=" + id_usuario);
                //Vamos a guardar los posts en una lista de objetos Feed.
                ArrayList<Feed> lista_posts = new ArrayList<>();
                //Recorremos el resultado y vamos guardando cada feed en la lista.
                while (resultado_posts.next()) {
                    Feed feed = new Feed();
                    feed.setIdpost(resultado_posts.getInt("idpost"));
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    feed.setDate(formato.format(resultado_posts.getTimestamp("date")));
                    feed.setContent(resultado_posts.getString("content"));
                    feed.setPhoto(resultado_posts.getString("photo"));
                    feed.setVideo(resultado_posts.getString("video"));
                    lista_posts.add(feed);
                }
                //Guardamos los feeds en sesión y cerramos el ResultSet.
                session.setAttribute("lista_posts", lista_posts);
                resultado_posts.close();

                //Ahora sólo queda obtener los chats.
                ResultSet resultado_chats = sentencia.executeQuery("SELECT * FROM chats WHERE usera=" + id_usuario);
                //Vamos a guardar los chats en una lista de objetos Chat.
                ArrayList<Chat> lista_chats = new ArrayList<>();
                while (resultado_chats.next()) {
                    Chat chat = new Chat();
                    chat.setId_chat(resultado_chats.getInt("id_chat"));
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    chat.setDate(formato.format(resultado_chats.getTimestamp("date")));
                    chat.setMessage(resultado_chats.getString("message"));
                    lista_chats.add(chat);
                }
                //Guardamos los chats en sesión y cerramos el ResultSet.
                session.setAttribute("lista_chats", lista_chats);
                resultado_chats.close();

                //Si todo ha ido bien iremos a modificar_usuario.jsp.
                page = "modificar_usuario.jsp";
                //Cerramos las conexiones con la BDD.
                sentencia.close();
                conexion.close();
            } //Si ha ocurrido un error se lo indicamos al usuario.
            catch (ClassNotFoundException | SQLException e) {
                error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
            } //Finalmente nos vamos a la página indicada con el error indicado.
            finally {
                session.setAttribute("error", error);
                request.getRequestDispatcher(page).forward(request, response);
            }
        } //Si hemos venido de modificar_usuario.jsp entonces tendremos que modificar
        //el usuario según los parámetros que nos vengan de la página.
        else if (pagina.equals("modificar_usuario.jsp")) {

            //TODO Modificar este código.
            //Obtenemos la función.
            String funcion = request.getParameter("funcion");
            //Según esa función haremos una cosa u otra.
            switch (funcion) {
                case "delete_user": {
                    //Si hay que borrar el usuario simplemente 
                    //ejecutamos el DELETE correspondiente.
                    //Obtenemos el usuario.
                    String user = request.getParameter("user");

                    //Con ese usuario lo borramos de la BDD.
                    try {
                        //Nos conectamos a la Base de Datos.
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        //Eliminamos el directorio de las fotos y los vídeos del usuario.
                        File directorio_actual = new File(".");
                        File directorio_photos = new File(directorio_actual.getCanonicalPath() + "/webapps/FriendLeaf/photos/" + user);
                        File directorio_videos = new File(directorio_actual.getCanonicalPath() + "/webapps/FriendLeaf/videos/" + user);
                        FileUtils.deleteDirectory(directorio_photos);
                        FileUtils.deleteDirectory(directorio_videos);

                        ResultSet resultado_usuario = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                        resultado_usuario.next();
                        //Obtenemos el ID para borrar ese ID de la BDD.
                        int id_usuario = resultado_usuario.getInt("id");

                        resultado_usuario.close();

                        sentencia.executeUpdate("DELETE FROM users WHERE id=" + id_usuario);

                        session.setAttribute("correcto", "Usuario borrado con éxito.");

                        //Si todo ha ido bien iremos a Admin Servlet para que nos saque la lista
                        //de usuarios actualizada.
                        page = "AdminServlet?page=index.jsp";
                        //Cerramos las conexiones con la BDD.
                        sentencia.close();
                        conexion.close();
                    } //Si ha ocurrido un error se lo indicamos al usuario.
                    catch (ClassNotFoundException | SQLException e) {
                        error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                    } //Finalmente nos vamos a la página indicada con el error indicado.
                    finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }
                break;
                case "delete_profile_photo": {
                    //Si hay que borrar la foto obtenemos el usuario
                    //de request y simplemente borramos su foto de perfil.
                    //Obtenemos el usuario.
                    String user = request.getParameter("user");

                    //Con ese usuario lo borramos de la BDD.
                    try {
                        //Nos conectamos a la Base de Datos.
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ResultSet resultado_usuario = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                        resultado_usuario.next();
                        //Obtenemos el ID para borrar su foto de la BDD.
                        int id_usuario = resultado_usuario.getInt("id");

                        resultado_usuario.close();

                        //Ponemos la foto a '' para decir que ya no hay foto.
                        sentencia.executeUpdate("UPDATE users SET photo='' WHERE id=" + id_usuario);

                        //Si todo ha ido bien iremos al AdminServlet del usuario indicado.
                        page = "AdminServlet?page=panel_admin.jsp&user=" + user;
                        //Cerramos las conexiones con la BDD.
                        sentencia.close();
                        conexion.close();
                    } //Si ha ocurrido un error se lo indicamos al usuario.
                    catch (ClassNotFoundException | SQLException e) {
                        error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                    } //Finalmente nos vamos a la página indicada con el error indicado.
                    finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }
                break;
                case "delete_photo": {
                    //Si lo que queremos es borrar la foto de un usuario, entonces
                    //debemos simplemente obtener el usuario y la foto por request
                    //y borrar esa fila de la BDD.
                    //Obtenemos el usuario y la foto.
                    String user = request.getParameter("user");
                    String photo = request.getParameter("photo");

                    //Con ese usuario lo borramos de la BDD.
                    try {
                        //Nos conectamos a la Base de Datos.
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        ResultSet resultado_usuario = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                        resultado_usuario.next();
                        //Obtenemos el ID para borrar su foto de la BDD.
                        int id_usuario = resultado_usuario.getInt("id");

                        resultado_usuario.close();
                        //Eliminamos el archivo de la foto.
                        //Obtenemos la ruta del archivo...
                        String current = new java.io.File(".").getCanonicalPath();
                        String path = current + "/webapps/FriendLeaf/" + photo;

                        Path root = Paths.get(path);
                        //...y lo eliminamos
                        Files.delete(root);
                        //Borramos la foto de la BDD.
                        sentencia.executeUpdate("DELETE FROM photos WHERE user=" + id_usuario + " AND photo='" + photo + "'");

                        //Si todo ha ido bien iremos al AdminServlet del usuario indicado.
                        page = "AdminServlet?page=panel_admin.jsp&user=" + user;
                        //Cerramos las conexiones con la BDD.
                        sentencia.close();
                        conexion.close();
                    } //Si ha ocurrido un error se lo indicamos al usuario.
                    catch (ClassNotFoundException | SQLException e) {
                        error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                    } //Finalmente nos vamos a la página indicada con el error indicado.
                    finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }
                break;
                case "delete_post": {
                    //Para borrar un post sólo tenemos que obtener
                    //el ID del post del request y borrarlo.
                    //Obtenemos el id del post y el usuario.
                    int id_post = Integer.parseInt(request.getParameter("id_post"));
                    String user = request.getParameter("user");

                    //Con ese id del post lo borramos de la BDD.
                    try {
                        //Nos conectamos a la Base de Datos.
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        //Obtenemos la foto y el video de la BDD.
                        ResultSet resultado_foto_video = sentencia.executeQuery("SELECT photo, video FROM posts WHERE idpost=" + id_post);
                        resultado_foto_video.next();
                        String photo = resultado_foto_video.getString("photo");
                        String video = resultado_foto_video.getString("video");
                        //Si no son vacíos entonces habrá que eliminar esos archivos.
                        if (!photo.equals("")) {
                            //Obtenemos la ruta del archivo...
                            String current = new java.io.File(".").getCanonicalPath();
                            String path = current + "/webapps/FriendLeaf/photos/" + user;

                            path = path + File.separator + photo;
                            Path root = Paths.get(path);
                            //...y lo eliminamos
                            Files.delete(root);
                        }
                        if (!video.equals("")) {
                            //Obtenemos la ruta del archivo...
                            String current = new java.io.File(".").getCanonicalPath();
                            String path = current + "/webapps/FriendLeaf/videos/" + user;

                            path = path + File.separator + video;
                            Path root = Paths.get(path);
                            //...y lo eliminamos.
                            Files.delete(root);
                        }
                        //Borramos el post de la BDD.
                        sentencia.executeUpdate("DELETE FROM posts WHERE idpost=" + id_post);

                        //Si todo ha ido bien iremos al AdminServlet del usuario indicado.
                        page = "AdminServlet?page=panel_admin.jsp&user=" + user;
                        //Cerramos las conexiones con la BDD.
                        sentencia.close();
                        conexion.close();
                    } //Si ha ocurrido un error se lo indicamos al usuario.
                    catch (ClassNotFoundException | SQLException e) {
                        error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                    } //Finalmente nos vamos a la página indicada con el error indicado.
                    finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }
                break;
                case "delete_chat": {
                    //En el caso de eliminar un chat obtenemos el ID desde
                    //el request y lo borramos de la BDD.
                    //Obtenemos el id del chat y el usuario.
                    int id_chat = Integer.parseInt(request.getParameter("id_chat"));
                    String user = request.getParameter("user");

                    //Con ese id del post lo borramos de la BDD.
                    try {
                        //Nos conectamos a la Base de Datos.
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        //Borramos la foto de la BDD.
                        sentencia.executeUpdate("DELETE FROM chats WHERE id_chat=" + id_chat);

                        //Si todo ha ido bien iremos al AdminServlet del usuario indicado.
                        page = "AdminServlet?page=panel_admin.jsp&user=" + user;
                        //Cerramos las conexiones con la BDD.
                        sentencia.close();
                        conexion.close();
                    } //Si ha ocurrido un error se lo indicamos al usuario.
                    catch (ClassNotFoundException | SQLException e) {
                        error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                    } //Finalmente nos vamos a la página indicada con el error indicado.
                    finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }
                break;
                case "update_user": {
                    //Si tenemos que actualizar el usuario necesitamos obtener
                    //todos los datos del formulario y actualizarlos.
                    boolean hayerror = false;
                    Validator validar = new Validator();
                    page = "modificar_usuario.jsp";
                    try {
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                        //Obtenemos los datos de perfil.jsp con request.
                        String name = request.getParameter("name");
                        String surname = request.getParameter("surname");
                        String dnionie = request.getParameter("dnionie");
                        String dni = request.getParameter("dni");
                        String address = request.getParameter("address") + "\n" + request.getParameter("province");
                        String email = request.getParameter("email");
                        String telephone = request.getParameter("telephone");
                        String webpage = request.getParameter("webpage");
                        String answer1 = request.getParameter("answer1");
                        String answer2 = request.getParameter("answer2");

                        ResultSet resultado = null;

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
                                /*
                                int numerodni = Long.parseLong(dni.substring(1, 7));
                                String letra = obtenerLetraDNIoNIE(numerodni);
                                if (!letra.equals(dni.substring(7, 8))) {
                                hayerror = true;
                                error += "EL NIE introducido no es válido.<br>";
                                }
                                 */
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

                        //Finalmente, si no hay ningún error insertamos los datos en la base
                        //de datos.
                        if (!hayerror) {
                            //Obtenemos el valor del ID de usuario (ya que puede que modifiquemos el usuario en sí).
                            resultado = sentencia.executeQuery("SELECT id FROM users WHERE user='" + request.getParameter("user") + "'");
                            int id = 0;
                            while (resultado.next()) {
                                id = resultado.getInt("id");
                            }
                            //Un Update por cada parámetro que se reciba.
                            sentencia.executeUpdate("UPDATE users SET name='" + name + "' WHERE id=" + id);
                            sentencia.executeUpdate("UPDATE users SET surname='" + surname + "' WHERE id=" + id);
                            sentencia.executeUpdate("UPDATE users SET dni='" + dni + "' WHERE id=" + id);
                            sentencia.executeUpdate("UPDATE users SET address='" + address + "' WHERE id=" + id);
                            sentencia.executeUpdate("UPDATE users SET email='" + email + "' WHERE id=" + id);
                            sentencia.executeUpdate("UPDATE users SET telephone=" + telephone + " WHERE id=" + id);
                            sentencia.executeUpdate("UPDATE users SET webpage='" + webpage + "' WHERE id=" + id);

                        }

                        //Cerramos las conexiones de la Base de Datos.
                        if (resultado != null) {
                            resultado.close();
                        }
                        sentencia.close();
                        conexion.close();

                        //Y nos vamos al AdminServlet con la página panel_admin.jsp.
                        page = "AdminServlet?page=panel_admin.jsp&user=" + request.getParameter("user");
                        //Si ha ocurrido alguna Excepción imprimimos el error indicado.
                    } catch (SQLException | ClassNotFoundException ex) {
                        error += "Ha ocurrido un error inesperado: " + ex.getMessage();
                    } finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }
                break;

            }

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
