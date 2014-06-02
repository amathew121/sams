/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * backing bean for authentication
 * @author Ashish
 */
@ManagedBean
@RequestScoped
public class AuthBackingBean {

    private static final Logger log = Logger.getLogger(AuthBackingBean.class.getName());

    /**
     * logout method
     * redirects to the index page after logging out
     * redirects to error page if logout is unsuccessful
     * @return
     */
    public String logout() {
        String result = "/index?faces-redirect=true";

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
        } catch (ServletException e) {
            log.log(Level.SEVERE, "Failed to logout user!", e);
            result = "/loginError?faces-redirect=true";
        }

        return result;
    }
}
