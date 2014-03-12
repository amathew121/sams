/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author phcoe
 */
public class AttendancePage {
    private List<String> id_student;
    private String id_faculty_subject;
    private int id_lecture;
    private Date lecture_date;

    public List<String> getId_student() {
        return id_student;
    }

    public void setId_student(List<String> id_student) {
        this.id_student = id_student;
    }

    public String getId_faculty_subject() {
        return id_faculty_subject;
    }

    public void setId_faculty_subject(String id_faculty_subject) {
        this.id_faculty_subject = id_faculty_subject;
    }

    public int getId_lecture() {
        return id_lecture;
    }

    public void setId_lecture(int id_lecture) {
        this.id_lecture = id_lecture;
    }

    public Date getLecture_date() {
        return lecture_date;
    }

    public void setLecture_date(Date lecture_date) {
        this.lecture_date = lecture_date;
    }
    
    
}
