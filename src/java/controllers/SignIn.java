/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gson.Gson;
import controllers.util.JsfUtil;
import entities.Faculty;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Ashish
 */
@ManagedBean(name = "signIn", eager = true)
@ApplicationScoped
public class SignIn {

    /*
     * This is the Client ID that you generated in the API Console.
     */
    public static final String CLIENT_ID = "42085832964.apps.googleusercontent.com";

    /*
     * This is the Client Secret that you generated in the API Console.
     */
    public static final String CLIENT_SECRET = "hcuGilZPud8a5ULE20pXjFJ3";

    /*
     * Optionally replace this with your application's name.
     */
    public static final String APPLICATION_NAME = "PIIT ONLINE";
    private static final Gson GSON = new Gson();
    /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();
    private String username;
    private String password;
    private GoogleIdToken idToken;

    public String requestLogin() throws IOException {

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String tokenData = (String) request.getSession().getAttribute("token");

        GoogleTokenResponse token = JSON_FACTORY.fromString(tokenData, GoogleTokenResponse.class);
        idToken = token.parseIdToken();
        String gplusId = idToken.getPayload().getSubject();
        return login(gplusId);
    }

    public String normalLogin() {
        FacultyController facultyController = findBean("facultyController");
        Faculty f = facultyController.getFaculty(username);
            if (f != null) {
                if (f.getFacultyPassword().equals(password) && password != null && f.getFacultyPassword() != null) {
                    if(f.getOauthToken() != null){
                        return login(f.getOauthToken());
                    }

                else {
                    JsfUtil.addErrorMessage("Your account is not connected with MES Google account yet. Sign in with Google Plus once at the login page to continue");
                    return "/error.xhtml";
                }
                }
            }
                JsfUtil.addErrorMessage("Please Verify Username/Password");
                return "/error.xhtml";
    }
    
    private String login(String gplusId) {
        String s = "/user/List.xhtml?faces-redirect=true";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacultyController facultyController = findBean("facultyController");
        Faculty user = facultyController.getFacultyByToken(gplusId);
        if (user == null) {
            return "connectg?faces-redirect=true";
        } else {
            if (request.getUserPrincipal() == null) {
                try {
                    String pass = DigestUtils.sha256Hex(user.getOauthToken());
                    pass = user.getFacultyPassword() + pass;
                    request.login(user.getIdFaculty(), pass);
                    request.getServletContext().log("successfully logged in " + user.getIdFaculty());
                } catch (ServletException e) {
                JsfUtil.addErrorMessage(e,"Login Failed");
                    return "/error.xhtml";
                }
            } else {
                request.getServletContext().log("Skip logged because already logged in: " + user.getIdFaculty());
            }
        }
        return s;
    }

    public String connectLogin() throws IOException {
        FacultyController facultyController = findBean("facultyController");
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String gplusId = idToken.getPayload().getSubject();

        //Checking if the email id belongs to mes.ac.in domain
        String hd = idToken.getPayload().getHostedDomain();
        if ("mes.ac.in".equals(hd)) {
            Faculty f = facultyController.getFaculty(username);
            if (f != null) {
                if (f.getFacultyPassword().equals(password) && password != null && f.getFacultyPassword() != null) {
                     facultyController.updateGplus(f,idToken.getPayload().getEmail(), gplusId, DigestUtils.sha256Hex(f.getFacultyPassword()+DigestUtils.sha256Hex(gplusId)));
                }
            }
            else {
                JsfUtil.addErrorMessage("Existing Account Not found");
                return "/error.xhtml";
            }
        } else {
           JsfUtil.addErrorMessage("Existing Account can be connected only with MES email id");
           return "/error.xhtml";
        }
        return login(gplusId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.sha256Hex(password);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }
}
