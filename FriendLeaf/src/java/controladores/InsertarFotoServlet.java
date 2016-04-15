/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

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
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author andrew Servlet que se encarga simplemente de agregar el archivo
 * subido a la base de datos y meter el archivo en la carpeta del usuario.
 */
@WebServlet(name = "InsertarFotoServlet", urlPatterns = {"/InsertarFotoServlet"})
@MultipartConfig
public class InsertarFotoServlet extends HttpServlet {

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
        boolean hayerror = false;
        String page = "insertar_foto.jsp";
        String error = "";

        //Obtenemos la sesión.
        HttpSession session = request.getSession();

        //Obtenemos el archivo que se ha metido por el request.
        Part filePart = request.getPart("photo");
        String photo = getFileName(filePart);

        //Miramos si la foto a insertar es el formato jpg, gif o png (aunque el 
        //atributo accept de HTML debería valernos).
        if (!photo.endsWith(".jpeg") && !photo.endsWith(".jpg") && !photo.endsWith(".png") && !photo.endsWith(".gif")) {
            error += "El formato de la foto no es válido.<br>";
            session.setAttribute("error", error);
            request.getRequestDispatcher(page).forward(request, response);
        } else {
        //Miramos si existe ya ese nombre de archivo para pedir
            //que lo cambie.
            try {
                Class.forName("com.mysql.jdbc.Driver");

                //Nos conectamos con la BDD.
                Connection conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/socialnetwork", "root", "whisky");
                Statement sentencia = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                //Obtenemos el ID de usuario.
                ResultSet resultado_usuario = sentencia.executeQuery("SELECT id FROM users WHERE user='" + session.getAttribute("name") + "'");
                resultado_usuario.next();
                int id_usuario = resultado_usuario.getInt("id");
                resultado_usuario.close();
                //Miramos si existe en la BDD una foto con ese nombre.
                ResultSet resultado_foto = sentencia.executeQuery("SELECT photo FROM photos WHERE photo='" + photo + "' AND user=" + id_usuario);
                String photo_user = "";
                while (resultado_foto.next()) {
                    photo_user = resultado_foto.getString("photo");
                }
                resultado_foto.close();
                //Si esa foto ya existe en la BDD...
                if (!photo_user.equals("")) {
                //...entonces no podemos insertarla porque no podemos tener 2 archivos
                    //del mismo nombre.
                    error += "Ese nombre de foto ya existe en tus fotos, escoja otro.<br>";
                    hayerror = true;
                }
                //Si no hay ningún error seguimos.
                if (!hayerror) {
                    //Insertamos esa foto en la BDD...
                    sentencia.executeUpdate("INSERT INTO photos VALUES(" + id_usuario + ", 'photos/" + session.getAttribute("name") + "/" + photo + "')");
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
                        //Si todo ha ido bien nos vamos a FotosServlet.
                        page = "FotosServlet";
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
                //Cerramos las conexiones a BDD.
                sentencia.close();
                conexion.close();
            } //Si ha ocurrido algún error se lo indicamos al usuario.
            catch (SQLException | ClassNotFoundException e) {
                error += "Ha ocurrido un error inesperado: " + e.getMessage() + "<br>";
            } //Finalmente nos vamos a la página indicada con el error indicado.
            finally {
                session.setAttribute("error", error);
                request.getRequestDispatcher(page).forward(request, response);
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
