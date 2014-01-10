/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import beans.FacultySubjectViewBean;
import beans.FeedbackFacade;
import entities.FacultySubjectViewModel;
import entities.OldFbPi;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author piit
 */
@ManagedBean(name = "feedbackController")
@SessionScoped
public class FeedbackController implements Serializable {

    private Connection con;
    private beans.FeedbackFacade ejbFacade;
    private List<OldFbPi> OldFbPiList;

    @PostConstruct
    public void init() {
        OldFbPiList = new ArrayList<OldFbPi>();
    }

    private FeedbackFacade getFacade() {
        return ejbFacade;
    }
    
    public List<OldFbPi> getFeedbackPIList() {
        FacesContext facesContext = FacesContext.getCurrentInstance() ;
        String userName = facesContext.getExternalContext().getRemoteUser();
        OldFbPiList = getFacade().findByStaff(userName);
        return OldFbPiList;
    }
   

    public List<OldFbPi> getFbPiList() throws SQLException {
        
        try {
            //get database connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            String URL = "jdbc:mysql://192.168.4.90:3306/piit";
            String USER = "root";
            String PASS = "MES_90";
            con = DriverManager.getConnection(URL, USER, PASS);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FacultySubjectViewBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FacultySubjectViewBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FacultySubjectViewBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FeedbackController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (con == null) {
            throw new SQLException("Can't get database connection");
        }

        PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM old_fb_pi ORDER BY sub_id, division, batch");

        //get customer data from database
        ResultSet result = ps.executeQuery();

        List<OldFbPi> list = new ArrayList<OldFbPi>();

        while (result.next()) {
            OldFbPi c = new OldFbPi();
            c.getOldFbPiPK().setSubId(result.getString("sub_id"));
            c.getOldFbPiPK().setDivision(result.getString("division"));
            c.setFtype(result.getShort("ftype"));
            c.getOldFbPiPK().setBatch(result.getShort("batch"));
            c.setPi(result.getBigDecimal("pi"));

            //store all data into a List
            list.add(c);
        }

        return list;

    }
    
    
    

    public void prepareList() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/user/Feedback2013.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
