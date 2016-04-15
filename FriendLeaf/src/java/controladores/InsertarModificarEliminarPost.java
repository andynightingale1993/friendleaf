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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import datos.Feed;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author andrew
 */
@WebServlet(name = "InsertarModificarEliminarPost", urlPatterns = {"/InsertarModificarEliminarPost"})
@MultipartConfig
public class InsertarModificarEliminarPost extends HttpServlet {

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
        //Error y página.
        String error = "";
        String page = "feed.jsp";

        //Obtenemos la sesión
        HttpSession session = request.getSession();
        //Obtenemos el contenido que se ha metido por el request.
        String content = request.getParameter("content");

        //Obtenemos el valor de función del formulario.
        String funcion = request.getParameter("funcion");
        //Según el valor de esa función haremos una cosa u otra.
        switch (funcion) {
            //En el caso de insertar agregamos el post a la Base de Datos.
            case "insertar": {
                boolean hayerror = false;
                //Obtenemos quién es el usuario conectado y la fecha de hoy.
                /*SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String date = formato.format(new Date());*/
                String user = (String) session.getAttribute("name");
                //Buscamos a partir del nombre de usuario su ID.

                //Obtenemos la foto que se ha metido por el request.
                Part filePart = request.getPart("photo");
                String photo = getFileName(filePart);

                //Obtenemos el vídeo que se ha metido por el request.
                Part videoFilePart = request.getPart("video");
                String video = getFileName(videoFilePart);

                //Miramos si la foto a insertar es el formato jpg, gif o png (aunque el 
                //atributo accept de HTML debería valernos).
                if (photo != null && !photo.equals("")) {
                    if (!photo.endsWith(".jpg") && !photo.endsWith(".png") && !photo.endsWith(".gif")) {
                        error += "El formato de la foto no es válido.<br>";
                        session.setAttribute("error", error);
                        hayerror = true;
                        page = "feed.jsp";
                    }
                }

                //Miramos si el vídeo a insertar es el formato flv, mpeg, wmv, ogg o webm (aunque el 
                //atributo accept de HTML debería valernos).
                if (video != null && !video.equals("")) {
                    if (!video.endsWith(".flv") && !video.endsWith(".mpeg") && !video.endsWith(".wmv") && !video.endsWith(".ogg") && !video.endsWith(".webm") && !video.endsWith(".ogv")) {
                        error += "El formato del vídeo no es válido.<br>";
                        session.setAttribute("error", error);
                        hayerror = true;
                        page = "feed.jsp";
                    }
                }
                if (!hayerror) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet resultado_id = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                        resultado_id.next();
                        int id_usuario = resultado_id.getInt("id");
                        resultado_id.close();
                        //Si la foto y el vídeo existen entonces les agregamos el directorio
                        //relativo para que se recoja bien, en otro caso dejaremos los campos vacíos.
                        if (photo != null && !photo.equals("")) {
                            photo = "photos/" + session.getAttribute("name") + "/" + photo;
                            //Comprobamos si esa foto existe ya en la BDD.
                            ResultSet resultado_photo = sentencia.executeQuery("SELECT photo FROM photos WHERE user=" + id_usuario + " AND photo='" + photo + "'");
                            if (resultado_photo.next()) {
                                hayerror = true;
                                error += "Esa foto ya existe en tu perfil. Escoja otro o selecciónelo directamente.";
                                page = "feed.jsp";
                            }
                        }
                        if (!hayerror) {
                            if (video != null && !video.equals("")) {
                                video = "videos/" + session.getAttribute("name") + "/" + video;
                            }
                            //Y ahora con ese ID ya podemos insertar los datos.
                            sentencia.executeUpdate("INSERT INTO posts(user, date, content, photo, video) VALUES(" + id_usuario + ", (SELECT CURRENT_TIMESTAMP), '" + content + "', '" + photo + "', '" + video + "')");
                            if (photo != null && !photo.endsWith("/") && !photo.equals("")) {
                                sentencia.executeUpdate("INSERT INTO photos VALUES(" + id_usuario + ", '" + photo + "')");
                            }
                            //Ahora creamos los archivos en nuestro sistema (si es que existen).
                            if (photo != null && !photo.endsWith("/") && !photo.equals("")) {
                                String current = new java.io.File(".").getCanonicalPath();
                                String path = current + "/webapps/FriendLeaf/" + photo;
                                filePart = request.getPart("photo");

                                //Los Stream que necesitamos para leer y escribir el archivo.
                                OutputStream out = null;
                                InputStream filecontent = null;

                                //Intentamos escribir el fichero.
                                try {
                                    out = new FileOutputStream(new File(path));
                                    filecontent = filePart.getInputStream();

                                    int read = 0;
                                    //Vamos leyendo el archivo de 1024 en 1024 bytes.
                                    final byte[] bytes = new byte[1024];

                                    //Y vamos escribiendo en él.
                                    while ((read = filecontent.read(bytes)) != -1) {
                                        out.write(bytes, 0, read);
                                    }
                                    //Si ha ocurrido un error lo indicamos.
                                } catch (FileNotFoundException fne) {
                                    error += "Ha ocurrido un error con la creación del archivo: " + fne.getMessage() + "<br>";
                                } finally {
                                    //Finalmente cerramos el archivo.
                                    if (out != null) {
                                        out.close();
                                    }
                                    if (filecontent != null) {
                                        filecontent.close();
                                    }
                                }
                            }
                            if (video != null) {
                                String current = new java.io.File(".").getCanonicalPath();
                                String path = current + "/webapps/FriendLeaf/" + video;
                                filePart = request.getPart("video");

                                //Los Stream que necesitamos para leer y escribir el archivo.
                                OutputStream out = null;
                                InputStream filecontent = null;

                                //Intentamos escribir el fichero.
                                try {
                                    out = new FileOutputStream(new File(path));
                                    filecontent = filePart.getInputStream();

                                    int read = 0;
                                    //Vamos leyendo el archivo de 1024 en 1024 bytes.
                                    final byte[] bytes = new byte[1024];

                                    //Y vamos escribiendo en él.
                                    while ((read = filecontent.read(bytes)) != -1) {
                                        out.write(bytes, 0, read);
                                    }
                                    //Si ha ocurrido un error lo indicamos.
                                } catch (FileNotFoundException fne) {
                                    error += "Ha ocurrido un error con la creación del archivo: " + fne.getMessage() + "<br>";
                                } finally {
                                    //Finalmente cerramos el archivo.
                                    if (out != null) {
                                        out.close();
                                    }
                                    if (filecontent != null) {
                                        filecontent.close();
                                    }
                                }
                            }

                            //Si todo ha ido bien nos vamos a FeedServlet
                            page = "FeedServlet";
                        }

                        //Cerramos las conexiones con la BDD.
                        sentencia.close();
                        conexion.close();
                        //Si ha ocurrido algún error se lo indicamos al usuario.
                    } catch (ClassNotFoundException | SQLException e) {
                        error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                    } //Y finalmente nos vamos a la página indicada con el error indicado.
                    finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                } else {
                    session.setAttribute("error", error);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            }
            break;
            //En el caso de insertar sin archivo agregamos el post a la Base de Datos.
            case "insertar_sin_file": {
                boolean hayerror = false;
                //Obtenemos quién es el usuario conectado y la fecha de hoy.
                /*SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date fecha_actual = new Date();
                String date = formato.format(new Date());*/
                String user = (String) session.getAttribute("name");
                //Buscamos a partir del nombre de usuario su ID.

                //Obtenemos la foto que se ha metido por el request.
                String photo = request.getParameter("photo");

                //Obtenemos el vídeo que se ha metido por el request.
                Part videoFilePart = request.getPart("video");
                String video = getFileName(videoFilePart);

                //Miramos si la foto a insertar es el formato jpg, gif o png (aunque el 
                //atributo accept de HTML debería valernos).
                if (photo != null && !photo.equals("")) {
                    if (!photo.endsWith(".jpg") && !photo.endsWith(".png") && !photo.endsWith(".gif")) {
                        error += "El formato de la foto no es válido.<br>";
                        session.setAttribute("error", error);
                        hayerror = true;
                        page = "feed.jsp";
                    }
                }

                //Miramos si el vídeo a insertar es el formato flv, mpeg, wmv, ogg o webm (aunque el 
                //atributo accept de HTML debería valernos).
                if (video != null && !video.equals("")) {
                    if (!video.endsWith(".flv") && !video.endsWith(".mpeg") && !video.endsWith(".wmv") && !video.endsWith(".ogg") && !video.endsWith(".webm") && !video.endsWith(".ogv")) {
                        error += "El formato del vídeo no es válido.<br>";
                        session.setAttribute("error", error);
                        hayerror = true;
                        page = "feed.jsp";
                    }
                }
                if (!hayerror) {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");

                        Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                        Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                        ResultSet resultado_id = sentencia.executeQuery("SELECT id FROM users WHERE user='" + user + "'");
                        resultado_id.next();
                        int id_usuario = resultado_id.getInt("id");
                        resultado_id.close();
                        //Si la foto y el vídeo existen entonces les agregamos el directorio
                        //relativo para que se recoja bien, en otro caso dejaremos los campos vacíos.
                        if (photo != null && !photo.equals("")) {
                            //Comprobamos que esa foto existe en la BDD para ese usuario.
                            ResultSet resultado_photo = sentencia.executeQuery("SELECT photo FROM photos WHERE user=" + id_usuario + " AND photo='" + photo + "'");
                            if (!resultado_photo.next()) {
                                hayerror = true;
                                error += "Esa foto no existe. Escoja otra.<br>";
                                page = "feed.jsp";
                            }

                        }
                        if (!hayerror) {
                            if (video != null && !video.equals("")) {
                                video = "videos/" + session.getAttribute("name") + "/" + video;
                            }
                            //Y ahora con ese ID ya podemos insertar los datos.
                            sentencia.executeUpdate("INSERT INTO posts(user, date, content, photo, video) VALUES(" + id_usuario + ", (SELECT CURRENT_TIMESTAMP), '" + content + "', '" + photo + "', '" + video + "')");
                            //Ahora creamos los archivos en nuestro sistema (si es que existen).

                            if (video != null) {
                                String current = new java.io.File(".").getCanonicalPath();
                                String path = current + "/webapps/FriendLeaf/" + video;
                                Part filePart = request.getPart("video");
                                //Los Stream que necesitamos para leer y escribir el archivo.
                                OutputStream out = null;
                                InputStream filecontent = null;

                                //Intentamos escribir el fichero.
                                try {
                                    out = new FileOutputStream(new File(path));
                                    filecontent = filePart.getInputStream();

                                    int read = 0;
                                    //Vamos leyendo el archivo de 1024 en 1024 bytes.
                                    final byte[] bytes = new byte[1024];

                                    //Y vamos escribiendo en él.
                                    while ((read = filecontent.read(bytes)) != -1) {
                                        out.write(bytes, 0, read);
                                    }
                                    //Si ha ocurrido un error lo indicamos.
                                } catch (FileNotFoundException fne) {
                                    error += "Ha ocurrido un error con la creación del archivo: " + fne.getMessage() + "<br>";
                                } finally {
                                    //Finalmente cerramos el archivo.
                                    if (out != null) {
                                        out.close();
                                    }
                                    if (filecontent != null) {
                                        filecontent.close();
                                    }
                                }
                            }

                            //Si todo ha ido bien nos vamos a FeedServlet
                            page = "FeedServlet";
                        }
                        //Cerramos las conexiones con la BDD.
                        sentencia.close();
                        conexion.close();
                        //Si ha ocurrido algún error se lo indicamos al usuario.
                    } catch (ClassNotFoundException | SQLException e) {
                        error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                    } //Y finalmente nos vamos a la página indicada con el error indicado.
                    finally {
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                } else {
                    session.setAttribute("error", error);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            }
            break;
            //Si es el caso de modificar obtenemos los datos de ese post en concreto
            //y lo guardamos en un Bean que nos presentará en un formulario para modificar
            //(en verdad sólo se podrá presentar el contenido ya que los input file no
            //pueden tener value).
            case "modificar": {
                //Obtenemos el valor del id de post.
                int id_post = Integer.parseInt(request.getParameter("id"));
                //Obtenemos el contenido de ese post desde la BDD.
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                    Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    ResultSet resultado_contenido = sentencia.executeQuery("SELECT date, content FROM posts WHERE idpost=" + id_post);
                    resultado_contenido.next();
                    //Guardamos el contenido en sesión y nos vamos a modificar_feed.jsp.
                    page = "modificar_feed.jsp";
                    Feed feed = new Feed();
                    feed.setIdpost(id_post);
                    feed.setContent(resultado_contenido.getString("content"));
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    feed.setDate(formato.format(resultado_contenido.getTimestamp("date")));
                    feed.setUser((String) session.getAttribute("name"));
                    session.setAttribute("feed", feed);
                    //Si ha ocurrido algún error se lo indicamos al usuario.
                } catch (ClassNotFoundException | SQLException e) {
                    error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                } //Y finalmente nos vamos a la página indicada con el error indicado.
                finally {
                    session.setAttribute("error", error);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            }
            break;
            //En este caso tenemos que eliminar la fila indicada de la BDD.
            case "eliminar": {
                //Obtenemos el parámetro de ID del post.
                int id_post = Integer.parseInt(request.getParameter("id"));

                //Con esa ID nos conectamos a la BDD y eliminamos ese post.
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                    Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    //Obtenemos la foto y el video de la BDD.
                    ResultSet resultado_foto_video = sentencia.executeQuery("SELECT video FROM posts WHERE idpost=" + id_post);
                    resultado_foto_video.next();
                    String old_video = resultado_foto_video.getString("video");
                    //Si no son vacíos entonces habrá que eliminar esos archivos.
                    if (!old_video.equals("") && old_video.startsWith("videos")) {
                        //Obtenemos la ruta del archivo...
                        String current = new java.io.File(".").getCanonicalPath();
                        String path = current + "/webapps/FriendLeaf/";

                        path = path + File.separator + old_video;
                        Path root = Paths.get(path);
                        //...y lo eliminamos.
                        Files.delete(root);
                    }
                    sentencia.executeUpdate("DELETE FROM posts WHERE idpost=" + id_post);
                    //Si todo ha ido bien nos vamos a FeedServlet
                    page = "FeedServlet";
                    //Si ha ocurrido algún error se lo indicamos al usuario.
                } catch (ClassNotFoundException | SQLException e) {
                    error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                } //Y finalmente nos vamos a la página indicada con el error indicado.
                finally {
                    session.setAttribute("error", error);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            }
            break;
            //En el caso de modificacion necesitamos actualizar la fila de la BDD
            //con los nuevos valores recogidos en el formulario.
            case "modificacion": {
                //Obtenemos la foto que se ha metido por el request.
                Part filePart = request.getPart("photo");
                String photo = getFileName(filePart);

                //Obtenemos el vídeo que se ha metido por el request.
                Part videoFilePart = request.getPart("video");
                String video = getFileName(videoFilePart);

                //Miramos si la foto a insertar es el formato jpg, gif o png (aunque el 
                //atributo accept de HTML debería valernos).
                if (photo != null && !photo.equals("")) {
                    if (!photo.endsWith(".jpg") && !photo.endsWith(".png") && !photo.endsWith(".gif")) {
                        error += "El formato de la foto no es válido.<br>";
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }

                //Miramos si el vídeo a insertar es el formato flv, mpeg, wmv, ogg o webm (aunque el 
                //atributo accept de HTML debería valernos).
                if (video != null && !video.equals("")) {
                    if (!video.endsWith(".flv") && !video.endsWith(".mpeg") && !video.endsWith(".wmv") && !video.endsWith(".ogg") && !video.endsWith(".webm")) {
                        error += "El formato del vídeo no es válido.<br>";
                        session.setAttribute("error", error);
                        request.getRequestDispatcher(page).forward(request, response);
                    }
                }
                try {
                    Class.forName("com.mysql.jdbc.Driver");

                    Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                    Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    //Obtenemos el ID del post.
                    int id_post = Integer.parseInt(request.getParameter("id"));
                    //Obtenemos la foto y el video de la BDD.
                    ResultSet resultado_foto_video = sentencia.executeQuery("SELECT photo, video FROM posts WHERE idpost=" + id_post);
                    resultado_foto_video.next();
                    String old_photo = resultado_foto_video.getString("photo");
                    String old_video = resultado_foto_video.getString("video");
                    //Si no son vacíos entonces habrá que eliminar esos archivos.
                    if (!old_photo.equals("")) {
                        //Obtenemos la ruta del archivo...
                        String current = new java.io.File(".").getCanonicalPath();
                        String path = current + "/webapps/FriendLeaf/";

                        path = path + File.separator + old_photo;
                        Path root = Paths.get(path);
                        //...y lo eliminamos
                        Files.delete(root);
                    }
                    if (!old_video.equals("")) {
                        //Obtenemos la ruta del archivo...
                        String current = new java.io.File(".").getCanonicalPath();
                        String path = current + "/webapps/FriendLeaf/";

                        path = path + File.separator + old_video;
                        Path root = Paths.get(path);
                        //...y lo eliminamos.
                        Files.delete(root);
                    }

                    //Y ahora con ese ID ya podemos modificar los datos.
                    //Modificamos el contenido.
                    sentencia.executeUpdate("UPDATE posts SET content='" + request.getParameter("content") + "' WHERE idpost=" + id_post);
                    //Si hay foto modificamos la foto.
                    if (photo != null && !photo.equals("")) {
                        sentencia.executeUpdate("UPDATE posts set photo='photos/" + session.getAttribute("name") + "/" + photo + "' WHERE idpost=" + id_post);
                    }
                    //Si hay vídeo modificamos el vídeo.
                    if (video != null && !video.equals("")) {
                        sentencia.executeUpdate("UPDATE posts set video='videos/" + session.getAttribute("name") + "/" + video + "' WHERE idpost=" + id_post);
                    }
                    //Ahora creamos los archivos en nuestro sistema (si es que existen).
                    if (photo != null && !photo.equals("")) {
                        //...y creamos el archivo...
                        //Obtenemos la ruta del archivo.
                        String current = new java.io.File(".").getCanonicalPath();
                        String path = current + "/webapps/FriendLeaf/photos/" + session.getAttribute("name");
                        filePart = request.getPart("photo");
                        final String fileName = getFileName(filePart);
                        path = path + File.separator + fileName;

                        //Los Stream que necesitamos para leer y escribir el archivo.
                        OutputStream out = null;
                        InputStream filecontent = null;

                        //Intentamos escribir el fichero.
                        try {
                            out = new FileOutputStream(new File(path));
                            filecontent = filePart.getInputStream();

                            int read = 0;
                            //Vamos leyendo el archivo de 1024 en 1024 bytes.
                            final byte[] bytes = new byte[1024];

                            //Y vamos escribiendo en él.
                            while ((read = filecontent.read(bytes)) != -1) {
                                out.write(bytes, 0, read);
                            }
                            //Si ha ocurrido un error lo indicamos.
                        } catch (FileNotFoundException fne) {
                            error += "Ha ocurrido un error con la creación del archivo: " + fne.getMessage() + "<br>";
                        } finally {
                            //Finalmente cerramos el archivo.
                            if (out != null) {
                                out.close();
                            }
                            if (filecontent != null) {
                                filecontent.close();
                            }
                        }
                    }
                    if (video != null && !video.equals("")) {
                        //...y creamos el archivo...
                        //Obtenemos la ruta del archivo.
                        String current = new java.io.File(".").getCanonicalPath();
                        String path = current + "/webapps/FriendLeaf/videos/" + session.getAttribute("name");
                        filePart = request.getPart("video");
                        final String fileName = getFileName(filePart);
                        path = path + File.separator + fileName;

                        //Los Stream que necesitamos para leer y escribir el archivo.
                        OutputStream out = null;
                        InputStream filecontent = null;

                        //Intentamos escribir el fichero.
                        try {
                            out = new FileOutputStream(new File(path));
                            filecontent = filePart.getInputStream();

                            int read = 0;
                            //Vamos leyendo el archivo de 1024 en 1024 bytes.
                            final byte[] bytes = new byte[1024];

                            //Y vamos escribiendo en él.
                            while ((read = filecontent.read(bytes)) != -1) {
                                out.write(bytes, 0, read);
                            }
                            //Si ha ocurrido un error lo indicamos.
                        } catch (FileNotFoundException fne) {
                            error += "Ha ocurrido un error con la creación del archivo: " + fne.getMessage() + "<br>";
                        } finally {
                            //Finalmente cerramos el archivo.
                            if (out != null) {
                                out.close();
                            }
                            if (filecontent != null) {
                                filecontent.close();
                            }
                        }
                    }

                    //Si todo ha ido bien nos vamos a FeedServlet
                    page = "FeedServlet";

                    //Cerramos las conexiones con la BDD.
                    sentencia.close();
                    conexion.close();
                    //Si ha ocurrido algún error se lo indicamos al usuario.
                } catch (ClassNotFoundException | SQLException e) {
                    error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
                } //Y finalmente nos vamos a la página indicada con el error indicado.
                finally {
                    session.setAttribute("error", error);
                    request.getRequestDispatcher(page).forward(request, response);
                }
            }
        }
    }

    //Método que nos devuelve el nombre del archivo.
    private String getFileName(final Part part) {
        String retorno = null;
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                retorno = content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
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
