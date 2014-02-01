/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.FacultySubject;
import entities.Lecture;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

/**
 *
 * @author piit
 */
@ManagedBean(name = "dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    private DashboardModel model;
    private CartesianChartModel categoryModel;
    private ScheduleModel eventModel;
    private FacultySubject facSub;
    private List<Lecture> lectures;

    public FacultySubject getFacSub() {
        return facSub;
    }

    public DashboardBean() {
        model = new DefaultDashboardModel();
        DashboardColumn column1 = new DefaultDashboardColumn();
        DashboardColumn column2 = new DefaultDashboardColumn();
        DashboardColumn column3 = new DefaultDashboardColumn();

        column1.addWidget("lectures");
        column1.addWidget("contentsDelivered");
        column1.addWidget("lectureReview");

        column2.addWidget("tplan");
        column2.addWidget("lectureSchedule");
        column2.addWidget("tPlanReview");

        column3.addWidget("cobjective");
        column3.addWidget("coutcome");
        column3.addWidget("syllabus");
        column3.addWidget("cbooks");
        column3.addWidget("cevaluation");
        


        model.addColumn(column1);
        model.addColumn(column2);
        model.addColumn(column3);
    }

    public void handleReorder(DashboardReorderEvent event) {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        message.setSummary("Reordered: " + event.getWidgetId());
        message.setDetail("Item index: " + event.getItemIndex() + ", Column index: " + event.getColumnIndex() + ", Sender index: " + event.getSenderColumnIndex());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String prepareDashboard(FacultySubject f) {
        facSub = f;
        return "FSDashBoard?faces-redirect=true";
    }

    public DashboardModel getModel() {
        return model;
    }

    public CartesianChartModel getCategoryModel() {
        categoryModel = new CartesianChartModel();
        FacesContext context = FacesContext.getCurrentInstance();
        AttendanceViewController attendanceViewController = (AttendanceViewController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "attendanceViewController");
        try {
            lectures = attendanceViewController.getAttendanceByFS(facSub);
        } catch (Exception e) {
            lectures = new ArrayList<Lecture>();
        }
        ChartSeries temp = new ChartSeries();
        int i = 1;
        for (Lecture item : lectures) {
            temp.set(i++ + "", attendanceViewController.getAttendanceCount(facSub, item));
        }
        categoryModel.addSeries(temp);
        return categoryModel;
    }

    public ScheduleModel getEventModel() {
        eventModel = new DefaultScheduleModel();
        FacesContext context = FacesContext.getCurrentInstance();
        AttendanceViewController attendanceViewController = (AttendanceViewController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "attendanceViewController");
        try {
            lectures = attendanceViewController.getAttendanceByFS(facSub);
        } catch (Exception e) {
            lectures = new ArrayList<Lecture>();
        }
        for (Lecture item : lectures) {
            eventModel.addEvent(new DefaultScheduleEvent("", getStartTime(item.getLectureDate(), item.getLectureStartTime()), getStartTime(item.getLectureDate(), item.getLectureStartTime())));
        }
        return eventModel;
    }
    public Date getStartTime(Date lectureDate, Date startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(lectureDate);
        calendar.set(Calendar.HOUR, startTime.getHours());
        return calendar.getTime();
    }
    public Date getEndTime(Date lectureDate, Date startTime) {
        Date temp = lectureDate;
        temp.setHours(startTime.getHours()+1);
        return temp;
    }
            
    public void itemSelect(ItemSelectEvent event) {
        Lecture lec = lectures.get(event.getItemIndex());
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selected",
                "Date: " + lec.getLectureDate().toString() + ", \nTime" + lec.getLectureStartTime().getHours() + "\nContents Delivered: " + lec.getContentDelivered());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void onDateSelect(SelectEvent selectEvent) {
        //event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        FacesContext context = FacesContext.getCurrentInstance();
        LectureController lectureController = (LectureController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "lectureController");
        lectureController.prepareCreateWithDate((Date) selectEvent.getObject(), facSub);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/user/Create.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }        //return "Create?faces-redirect=true";
    }
    
    public void onEventSelect(SelectEvent selectEvent) {
        ScheduleEvent event = (ScheduleEvent) selectEvent.getObject();
        System.out.println(selectEvent.getObject().toString());
    }
}
