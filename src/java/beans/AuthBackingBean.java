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
 *
 * @author Ashish
 */
@ManagedBean
@RequestScoped
public class AuthBackingBean {

private static final Logger log = Logger.getLogger(AuthBackingBean.class.getName());

FacesContext context = FacesContext.getCurrentInstance();
HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();

public String logout() {
String result="/login?faces-redirect=true";


try {
request.logout();
request.getSession().invalidate();
} catch (ServletException e) {
log.log(Level.SEVERE, "Failed to logout user!", e);
result = "/error?faces-redirect=true";
}

return result;
}
}