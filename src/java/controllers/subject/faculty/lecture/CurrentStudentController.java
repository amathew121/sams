package controllers.subject.faculty.lecture;

import controllers.subject.ProgramCourseController;
import controllers.subject.SubjectController;
import entities.subject.faculty.lecture.CurrentStudent;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.lecture.CurrentStudentFacade;
import controllers.subject.faculty.FacultySubjectController;
import controllers.subject.faculty.StudentTestController;
import entities.subject.faculty.lecture.Attendance;
import entities.subject.faculty.lecture.AttendanceReport;
import entities.subject.Course;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.lecture.Lecture;
import entities.subject.Program;
import entities.subject.ProgramCourse;
import entities.subject.ProgramCoursePK;
import entities.subject.Subject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Named("currentStudentController")
@SessionScoped
public class CurrentStudentController implements Serializable {

    private CurrentStudent current;
    private DataModel items = null;
    private List<CurrentStudent> attendanceByDiv;
    private Lecture lec;
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
    @EJB
    private beans.subject.faculty.lecture.CurrentStudentFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private boolean selectAll;
    private ProgramCourse pc = new ProgramCourse();
    private Course course;
    private Program program;
    private short semester;
    private String division;
    List<Subject> subject = new ArrayList();

    @PostConstruct
    public void Init() {
        attendanceByDiv = new ArrayList<CurrentStudent>();
    }

    public String navList() {
        FacesContext context = FacesContext.getCurrentInstance();
        ProgramCourseController pcll = (ProgramCourseController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "programCourseController");
        SubjectController sc = (SubjectController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "subjectController");
        AttendanceReportController arc = (AttendanceReportController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "attendanceReportController");

        ProgramCoursePK pcpk = new ProgramCoursePK();
        /**
         * The following area is very susceptible to change :D
         */
        pcpk.setIdProgram(program.getIdProgram());
        pcpk.setIdCourse(course.getIdCourse());
        pc = pcll.getProgramCourse(pcpk);
        subject = sc.getSubjectBySemester(pc, semester);
        return "ReportAllNew?faces-redirect=true";
    }

    public CurrentStudentController() {
    }
    @ManagedProperty(value = "#{attendanceController}")
    private AttendanceController attendanceController;

    public AttendanceController getAttendanceController() {
        return attendanceController;
    }

    public void setAttendanceController(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }

    public Lecture getLec() {
        return lec;
    }

    public void setLec(Lecture lec) {
        this.lec = lec;
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }

    public boolean isSelectAll() {
        return selectAll;
    }

    public void setSelectAll(boolean selectAll) {
        this.selectAll = selectAll;
    }

    public void selectAllComponents(ValueChangeEvent event) {
        if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {
            if (selectAll) {
                changeMap(checked, true);
                setSelectAll(true);
            } else // If the button is unchecked, unselect all the checkboxes
            {
                changeMap(checked, false);
                setSelectAll(false);
            }
        }
    }

    public void changeMap(Map<Integer, Boolean> selectedComponentMap, Boolean blnValue) {
        if (selectedComponentMap != null) {
            /* Iterator<Integer> itr = selectedComponentMap.keySet().iterator();
             selectedComponentMap.put(attendanceByDiv.get(0).getIdCurrentStudent(),true);
             while (itr.hasNext()) {
             selectedComponentMap.put(itr.next(), true);
             } */
            for (CurrentStudent item : attendanceByDiv) {
                selectedComponentMap.put(item.getIdCurrentStudent(), blnValue);
            }
            setChecked(selectedComponentMap);
        }
    }

    public String createAttendance() {

        List<CurrentStudent> checkedItems = new ArrayList<CurrentStudent>();

        for (CurrentStudent item : attendanceByDiv) {
            if (checked.get(item.getIdCurrentStudent())) {
                checkedItems.add(item);
            }
        }

        List<Attendance> att = new ArrayList<Attendance>();
        /*    if (checkedItems.isEmpty()) {
         JsfUtil.addErrorMessage("No Students Selected");
         throw new NullPointerException();
         } */
        for (int i = 0; i < checkedItems.size(); i++) {

            Attendance ae = new Attendance();


            ae.setIdAttendance(Long.MIN_VALUE);
            ae.setIdCurrentStudent(checkedItems.get(i));
            ae.setIdLecture(lec);
            att.add(ae);
            try {
                attendanceController.createEntry(ae);
            } catch (Exception ex) {
                Logger.getLogger(CurrentStudentController.class.getName()).log(Level.SEVERE, null, ex);
            }
            attendanceController.create();


        }
        JsfUtil.addSuccessMessage("Attendance Successfully Created");
        return "View?faces-redirect=true";
    }

    public List<CurrentStudent> getAttendanceByDiv(FacultySubject f) {
        String div = f.getDivision();
        short batch = f.getBatch();
        short semester = f.getIdSubject().getSemester();
        ProgramCourse programCourse = f.getIdSubject().getProgramCourse();
        if (batch == 0) {
            attendanceByDiv = getFacade().getCurrentStudentByDivTheory(programCourse, semester, div);
            return attendanceByDiv;
        } else {

            attendanceByDiv = getFacade().getCurrentStudentByDiv(programCourse, semester, div, batch);
            return attendanceByDiv;
        }
    }

    public List<CurrentStudent> getAttendanceList() {


        FacesContext context = FacesContext.getCurrentInstance();
        AttendanceReportController arc = (AttendanceReportController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "attendanceReportController");
        FacultySubjectController fsc = (FacultySubjectController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "facultySubjectController");

        List<CurrentStudent> lcs = getFacade().getCurrentStudentByDivTheory(pc, semester, division);


        for (Subject item : subject) {
            Map<Integer, Integer> theory = new HashMap<Integer, Integer>();
            Map<Integer, Integer> pracs = new HashMap<Integer, Integer>();

            List<Object[]> theoryObj = arc.getStudentAttendanceByIdSubjectSemDiv(pc, semester, division, item.getIdSubject());
            Map<Integer, BigDecimal> marks = new HashMap<Integer, BigDecimal>();
            Map<Integer, BigDecimal> marks2 = new HashMap<Integer, BigDecimal>();

            StudentTestController stc = (StudentTestController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "studentTestController");


            for (CurrentStudent cs : lcs) {
                theory.put(cs.getIdCurrentStudent(), 0);
                marks.put(cs.getIdCurrentStudent(), new BigDecimal(0));

            }

            for (Object[] ar : theoryObj) {
                if(((AttendanceReport) ar[0]).getFsBatch() == 0)
                    theory.put(((AttendanceReport) ar[0]).getIdCurrentStudent(), ((Number) ar[1]).intValue());
                else
                    pracs.put(((AttendanceReport) ar[0]).getIdCurrentStudent(), ((Number) ar[1]).intValue());

            }
            for (CurrentStudent cs : lcs) {
                int[] theoryCount = cs.getTheoryCount();
                int[] pracsCount = cs.getPracsCount();

                theoryCount[item.getSubjectSrNo()] = theory.get(cs.getIdCurrentStudent());
                if(pracs.get(cs.getIdCurrentStudent()) != null)
                pracsCount[item.getSubjectSrNo()] = pracs.get(cs.getIdCurrentStudent());

                cs.setTheoryCount(theoryCount);
                cs.setPracsCount(pracsCount);
                cs.setCount(theory.get(cs.getIdCurrentStudent()));
            }

            LectureController lc = (LectureController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "lectureController");
            item.setLectureTotal(lc.getLectureByFSList(fsc.getIdFacSub(division, semester, (short) 0, item)).size());

            try {
                List<CurrentStudent> st = stc.getTestDetails(fsc.getIdFacSub(division, semester, (short) 0, item));

                for (CurrentStudent cs : st) {
                    marks.put(cs.getIdCurrentStudent(), cs.getMarks());
                }
                for (CurrentStudent cs : st) {
                    marks2.put(cs.getIdCurrentStudent(), cs.getMarks2());
                }

            } catch (Exception e) {
                e.printStackTrace();
            } 
            for (CurrentStudent cs : lcs) {
                BigDecimal[] marksAll = cs.getMarksAll();

                marksAll[item.getSubjectSrNo()] = marks.get(cs.getIdCurrentStudent());
                cs.setMarksAll(marksAll);
                cs.setMarks(marks.get(cs.getIdCurrentStudent()));
            }
            for (CurrentStudent cs : lcs) {
                BigDecimal[] marksAll = cs.getMarksAll2();

                marksAll[item.getSubjectSrNo()] = marks2.get(cs.getIdCurrentStudent());
                cs.setMarksAll2(marksAll);
                cs.setMarks2(marks2.get(cs.getIdCurrentStudent()));
            }


        }
        return lcs;
    }

    public void attendanceReportXlsExport() {
        List<CurrentStudent> currentStudent;
        currentStudent = getAttendanceList();
        Map beans = new HashMap();
        beans.put("currentStudent", currentStudent);
        beans.put("subject", subject);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=Report"+course.getIdCourse()+semester+division+".xls");

        XLSTransformer transformer = new XLSTransformer();
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/templateAttendance.xls");
            ServletOutputStream out = response.getOutputStream();
            HSSFWorkbook workbook =  transformer.transformXLS(input, beans);
            workbook.write(out);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(CurrentStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CurrentStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<CurrentStudent> getAttendanceByDiv() {
        return attendanceByDiv;
    }

    public void setAttendanceByDiv(List<CurrentStudent> attendanceByDiv) {
        this.attendanceByDiv = attendanceByDiv;
    }

    public CurrentStudent getSelected() {
        if (current == null) {
            current = new CurrentStudent();
            selectedItemIndex = -1;
        }
        return current;
    }

    private CurrentStudentFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareListA() {
        recreateModel();
        return "ListA";
    }

    public String prepareView() {
        current = (CurrentStudent) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new CurrentStudent();
        Date d = new Date();
        d.setMonth(6);
        d.setDate(1);
        d.setYear(113);
        current.setAcademicYear(d);
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CurrentStudentCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (CurrentStudent) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            Date d = new Date();
            d = current.getAcademicYear();
            d.setMonth(6);
            current.setAcademicYear(d);
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CurrentStudentUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (CurrentStudent) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CurrentStudentDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public CurrentStudent getCurrentStudentByID(Integer idCurrentStudent) {
        return getFacade().find(idCurrentStudent);
    }

    private void recreateModel() {
        items = null;
        attendanceByDiv = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    //<editor-fold defaultstate="collapsed" desc="Getter-Setters">
    public Course getCourse() {
        return course;
    }
    
    public void setCourse(Course course) {
        this.course = course;
    }
    
    public Program getProgram() {
        return program;
    }
    
    public void setProgram(Program program) {
        this.program = program;
    }
    public List<Subject> getSubject() {
        return subject;
    }
    
    public void setSubject(List<Subject> subject) {
        this.subject = subject;
    }
    
    public ProgramCourse getPc() {
        return pc;
    }
    
    public void setPc(ProgramCourse pc) {
        this.pc = pc;
    }
    
    public short getSemester() {
        return semester;
    }
    
    public void setSemester(short semester) {
        this.semester = semester;
    }
    
    public String getDivision() {
        return division;
    }
    
    public void setDivision(String division) {
        this.division = division;
    }
    //</editor-fold>

    @FacesConverter(forClass = CurrentStudent.class)
    public static class CurrentStudentControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CurrentStudentController controller = (CurrentStudentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "currentStudentController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof CurrentStudent) {
                CurrentStudent o = (CurrentStudent) object;
                return getStringKey(o.getIdCurrentStudent());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + CurrentStudent.class.getName());
            }
        }
    }
}