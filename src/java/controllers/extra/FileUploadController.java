/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.extra;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.model.UploadedFile;

/**
 *JSF Backing bean for fileupload Entity
 * @author piit
 */
@Named("fileUploadController")
@RequestScoped
public class FileUploadController {

    private String destination = "/home/piit/Desktop/";
    private UploadedFile file;  
  
    /**
     *
     * @return
     */
    public UploadedFile getFile() {  
        return file;  
    }  
  
    /**
     *
     * @param file
     */
    public void setFile(UploadedFile file) {  
        this.file = file;  
    }  

    /**
     *
     */
    public void upload() {
        
        if (file != null) {
            FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            try {
                copyFile(file.getFileName(), file.getInputstream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * @param fileName
     * @param in
     */
    public void copyFile(String fileName, InputStream in) {
        try {


            // write the inputStream to a FileOutputStream
            OutputStream out = new FileOutputStream(new File(destination + fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
