/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author andrew
 */
public class Perfil {
    //Algunos atributos los uso en crearcuenta.jsp y otros en perfil.jsp
    //y además hay algunos atributos que son compartidos.
    private String name;
    private String surname;
    private String user;
    private String dni;
    private String password;
    private String address;
    private String addressline1;
    private String addressline2;
    private String email;
    private String telefono;
    private long telephone;
    private String webpage;
    private String country;
    private String question1;
    private String question2;
    private int pregunta1;
    private int pregunta2;
    private String answer1;
    private String answer2;
    private String photo;
    private boolean modificar;
    
    public Perfil() {
        modificar = false;
        name = "";
        surname = "";
        user = "";
        dni = "";
        password = "";
        addressline1 = "";
        addressline2 = "";
        email = "";
        telefono = "";
        pregunta1 = 1;
        pregunta2 = 2;
        webpage = "";
        answer1 = "";
        answer2 = "";
        
    }
    
    //Getters y Setters de todos los atributos.
    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getAddressline2() {
        return addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(int pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public int getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(int pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    //Puede que no queramos ver en algún momento la contraseña, si no caracteres.
    public String getPassword() {
        String retorno = "";
        for(int i=0; i<password.length(); i++) {
            retorno += "#";
        }
        return retorno;
    }
    
    public String getStringPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTelephone() {
        return telephone;
    }

    public void setTelephone(long telephone) {
        this.telephone = telephone;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public boolean isModificar() {
        return modificar;
    }

    public void setModificar(boolean modificar) {
        this.modificar = modificar;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    
    
    
    
}
