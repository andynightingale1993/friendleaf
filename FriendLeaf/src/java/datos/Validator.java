/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

/**
 *
 * @author
 * http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 * 
 * Clase usada para validar un Email o URL en el formulario.
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private Pattern pattern, urlpattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String URL_PATTERN = "^http(s{0,1})://[a-zA-Z0-9_/\\-\\.]+\\.([A-Za-z/]{2,5})[a-zA-Z0-9_/\\&\\?\\=\\-\\.\\~\\%]*";

    public Validator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
        urlpattern = Pattern.compile(URL_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean isEmail(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }

    public boolean isURL(String url) {
        matcher = urlpattern.matcher(url);
        return matcher.matches();
    }
}
