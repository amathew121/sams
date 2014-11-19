/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.extra;

import controllers.feedback.Feedback2013Controller;
import controllers.feedback.FeedbackTypeController;
import controllers.subject.faculty.FacultySubjectViewController;
import controllers.subject.faculty.lecture.LectureController;
import controllers.subject.faculty.lecture.AttendanceViewController;
import controllers.subject.faculty.lecture.CurrentStudentController;
import entities.feedback.FeedbackType;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.lecture.Lecture;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.event.FileUploadEvent;
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
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ashish Mathew
 */
@Named("dashboardBean")
@SessionScoped
public class DashboardBean implements Serializable {

    private DashboardModel model;
    private CartesianChartModel lectureLineModel;
    private BarChartModel feedbackBarChartModel;
    private ScheduleModel eventModel;
    private FacultySubject facSub;
    private List<Lecture> lectures;
    private UploadedFile file;

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void handleFileUpload(FileUploadEvent event) throws Exception {
        file = event.getFile();
        if (file != null) {

            //File targetFolder = new File("/home/piit/uploaded/");
            String fileName = event.getFile().getFileName();
            InputStream in = event.getFile().getInputstream();
            //   OutputStream out = new FileOutputStream(new File(targetFolder, fileName));
            OutputStream out = new FileOutputStream("/usr/piit/uploaded/" + fileName);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
            FacesMessage message = new FacesMessage("Successful!!", fileName + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);


        }
    }
    // Constants ----------------------------------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 102400; // 100KB.

    // Actions ------------------------------------------------------------------------------------
    public void downloadPDF() throws IOException {

        // Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        String filename = facSub.getIdFacultySubject() + ".pdf";
        File file = new File("/usr/piit/uploaded/", filename);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);


            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        }  finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();

    }

    // Helpers (can be refactored to public utility class) ----------------------------------------
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it. It may be useful to 
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();
            }
        }
    }

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
        column1.addWidget("tPlanReview");
        column1.addWidget("lectureReview");

        column2.addWidget("feedback");
        column2.addWidget("tplan");
        column2.addWidget("lectureSchedule");
        column2.addWidget("students");

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

    public CartesianChartModel getLectureLineModel() {
        lectureLineModel = new CartesianChartModel();
        FacesContext context = FacesContext.getCurrentInstance();
        AttendanceViewController attendanceViewController = (AttendanceViewController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "attendanceViewController");
        CurrentStudentController currentStudentController = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
        int total = currentStudentController.getAttendanceByDiv(facSub).size();
        try {
            lectures = attendanceViewController.getAttendanceByFS(facSub);
        } catch (Exception e) {
            lectures = new ArrayList<Lecture>();
        }

        ChartSeries temp = new ChartSeries();
        ChartSeries max = new ChartSeries();
        int i = 1;
        for (Lecture item : lectures) {
            temp.set(i++ + "", attendanceViewController.getAttendanceCount(facSub, item));
            max.set(i + "", total);
        }
        lectureLineModel.addSeries(temp);
        lectureLineModel.addSeries(max);
        return lectureLineModel;
    }

    public BarChartModel getFeedbackBarChartModel() {
        feedbackBarChartModel = new BarChartModel();
        feedbackBarChartModel.setTitle("Feedback Chart");
        feedbackBarChartModel.setLegendPosition("nw");
        ChartSeries midTerm = new ChartSeries();
        midTerm.setLabel("Mid Term");
        ChartSeries endTerm = new ChartSeries();
        endTerm.setLabel("End Term");

        FacesContext context = FacesContext.getCurrentInstance();
        Feedback2013Controller feedback2013Controller = (Feedback2013Controller) context.getELContext().getELResolver().getValue(context.getELContext(), null, "feedback2013Controller");
        FeedbackTypeController feedbackTypeController = (FeedbackTypeController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "feedbackTypeController");

        FeedbackType type = feedbackTypeController.getFeedbackType(2);
        feedback2013Controller.getByUserName(facSub, type);
        int[] ra = feedback2013Controller.getRa();

        midTerm.set("SD", ra[1]);
        midTerm.set("DD", ra[2]);
        midTerm.set("NN", ra[3]);
        midTerm.set("AA", ra[4]);
        midTerm.set("SA", ra[5]);

        type = feedbackTypeController.getFeedbackType(3);
        feedback2013Controller.getByUserName(facSub, type);
        ra = feedback2013Controller.getRa();

        endTerm.set("SD", ra[1]);
        endTerm.set("DD", ra[2]);
        endTerm.set("NN", ra[3]);
        endTerm.set("AA", ra[4]);
        endTerm.set("SA", ra[5]);

        feedbackBarChartModel.addSeries(midTerm);
        feedbackBarChartModel.addSeries(endTerm);

        return feedbackBarChartModel;
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
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        calendar.setTime(lectureDate);
        calendar.set(Calendar.HOUR, startTime.getHours());

        return calendar.getTime();
    }

    public Date getEndTime(Date lectureDate, Date startTime) {
        Date temp = lectureDate;
        temp.setHours(startTime.getHours() + 1);
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
