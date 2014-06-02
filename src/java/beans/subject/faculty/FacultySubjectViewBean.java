/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.subject.faculty;

/**
 *
 * @author Ashish
 */

import entities.subject.faculty.FacultySubjectViewModel;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

/**
 * THIS CLASS HAS TO BE REFACTORED DELETED or moved to controllers
 * @author Administrator
 */
@ManagedBean(name="customer")
    @SessionScoped
public class FacultySubjectViewBean implements Serializable{
 
	//resource injection
	//@Resource(name="jdbc/piit")
	private DataSource ds;
 
	//if resource injection is not support, you still can get it manually.
/*	public FacultySubjectViewBean(){
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("jdbc:mysql://localhost:3306/piit?zeroDateTimeBehavior=convertToNull");
		} catch (NamingException e) {
			e.printStackTrace();
		}
 
	}*/
 
	//connect to DB and get customer list

    /**
     *
     * @return
     * @throws SQLException
     */
    	public List<FacultySubjectViewModel> getCustomerList() throws SQLException{
 
	/*	if(ds==null)
			throw new SQLException("Can't get data source");*/
            try {
                //get database connection
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FacultySubjectViewBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(FacultySubjectViewBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(FacultySubjectViewBean.class.getName()).log(Level.SEVERE, null, ex);
            }
                String URL = "jdbc:mysql://localhost:3306/piit";
                String USER = "root";
                String PASS = "MES_90";
		Connection con = DriverManager.getConnection(URL,USER,PASS);
 
		if(con==null)
			throw new SQLException("Can't get database connection");
 
		PreparedStatement ps 
			= con.prepareStatement(
			   "select * from faculty_subject_view"); 
 
		//get customer data from database
		ResultSet result =  ps.executeQuery();
 
		List<FacultySubjectViewModel> list = new ArrayList<FacultySubjectViewModel>();
 
		while(result.next()){
			FacultySubjectViewModel cust = new FacultySubjectViewModel();

			cust.setBatch(result.getString("batch"));
			cust.setDivision(result.getString("division"));
			cust.setFaculty_title(result.getString("faculty_title"));
			cust.setFacutly_fname(result.getString("faculty_fname"));
			cust.setFacutly_lname(result.getString("faculty_lname"));
			cust.setId_course(result.getString("id_course"));
			cust.setId_faculty_subject(result.getInt("id_faculty_subject"));
			cust.setId_program(result.getString("id_program"));
			cust.setSemester(result.getShort("semester"));
			cust.setSubject_code(result.getString("subject_code"));
			cust.setSubject_name(result.getString("subject_name"));
			cust.setSubject_sr_no(result.getShort("subject_sr_no"));
 
			//store all data into a List
			list.add(cust);
		}
 
		return list;
	}
}
