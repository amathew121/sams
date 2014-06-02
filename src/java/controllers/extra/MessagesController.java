/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.extra;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

@Named 
@RequestScoped
public class MessagesController {

    /**
     *
     * @param actionEvent
     */
    public void addInfo(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Sample info message", "PrimeFaces rocks!"));
	}

    /**
     *
     * @param actionEvent
     */
    public void addWarn(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Sample warn message", "Watch out for PrimeFaces!"));
	}

    /**
     *
     * @param actionEvent
     */
    public void addError(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Sample error message", "PrimeFaces makes no mistakes"));
	}

    /**
     *
     * @param actionEvent
     */
    public void addFatal(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,"Sample fatal message", "Fatal Error in System"));
	}
}
