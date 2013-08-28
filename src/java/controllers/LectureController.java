package controllers;

import entities.Lecture;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.LectureFacade;
import entities.Attendance;
import entities.CurrentStudent;
import entities.FacultySubject;
import entities.Subject;
import entities.TeachingPlan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "lectureController")
@SessionScoped
public class LectureController implements Serializable {

    private Lecture current;
    private DataModel lectureByFS;
    private CurrentStudent[] selectList;
    private String topicsDelivered;
    private DataModel items = null;
    private List<Lecture> lectureList;
    private Date startDate;
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    

    public CurrentStudent[] getSelectList() {
        return selectList;
    }

    public void setSelectList(CurrentStudent[] selectList) {
        this.selectList = selectList;
    }
    @ManagedProperty(value = "#{currentStudentController}")
    private CurrentStudentController currentStudentController;

    public CurrentStudentController getCurrentStudentController() {
        return currentStudentController;
    }

    public void setCurrentStudentController(CurrentStudentController currentStudentController) {
        this.currentStudentController = currentStudentController;
    }
    @EJB
    private beans.LectureFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idFacSub;
    private FacultySubject facSub;

    public FacultySubject getFacSub() {
        return facSub;
    }

    public void setFacSub(FacultySubject facSub) {
        this.facSub = facSub;
    }

    @PostConstruct
    public void init() {
        current = new Lecture();
        facSub = new FacultySubject();
    }

    public LectureController() {
    }

    public Lecture getLectureByLecID(Integer idLecture) {
        return getFacade().getLectureByIdLecture(idLecture);
    }

    public DataModel getLectureByFS() {
        lectureByFS = new ListDataModel(getFacade().getLectureByIdFaculty(facSub));
        return lectureByFS;
    }

    public void setLectureByFS(DataModel lectureByFS) {
        this.lectureByFS = lectureByFS;
    }

    public String getTopicsDelivered() {
        return topicsDelivered;
    }

    public void setTopicsDelivered(String topicsDelivered) {
        this.topicsDelivered = topicsDelivered;
    }

    public List<Lecture> getLectureList() {
        return lectureList;
    }

    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    
    public Lecture getSelected() {
        if (current == null) {
            current = new Lecture();
            selectedItemIndex = -1;
        }
        return current;
    }

    private LectureFacade getFacade() {
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

    public String prepareView() {
        current = (Lecture) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreateWithId() {
        prepareCreate();
        return "Create?foo=" + idFacSub + "&faces-redirect=true";
    }

    public String prepareMultipleRange() throws Exception {
        return "MultipleDateRange?foo=" + idFacSub + "&faces-redirect=true";
    }
    
    public String prepareCreateMultipleWithId() throws Exception{
        lectureList = getLectureByFSList(facSub, startDate, endDate);
        for(Lecture lec : lectureList){
            currentStudentController.setLec(lec);
            lec.changeMap(lec.getChecked(), Boolean.FALSE);
            List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
            for (int i = 0; i < attendance.size(); i++) {
                lec.getChecked().put(attendance.get(i).getIdCurrentStudent().getIdCurrentStudent(), Boolean.TRUE);
            }
        }
        prepareCreate();
        return "CreateMultiple?foo=" + idFacSub + "&faces-redirect=true";
    }

    public String prepareViewWithId(int i) {
        idFacSub = i;
        getFacSubject(idFacSub);
        recreateModel();
        return "View?foo=" + i + "&faces-redirect=true";
    }

    public FacultySubject getFacSubject(int i) {
        facSub = getFacade().getFSById(i);
        return facSub;
    }

    public String prepareCreate() {
        current = new Lecture();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public List<Lecture> getLectureByFSList(FacultySubject facSub){
        return getFacade().getLectureByIdFaculty(facSub);
    }
    public List<Lecture> getLectureByFSList(FacultySubject facSub, Date startDate, Date endDate) {
        return getFacade().getLectureByIdFacultyDateRange(facSub, startDate, endDate);
    }

    public int getLectureByFSListTotal(){
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController csc = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
        FacultySubjectController fsc = (FacultySubjectController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "facultySubjectController");
        int total = 0;
        for (Subject s : csc.subject) {
            FacultySubject facsub = fsc.getIdFacSub(csc.getDivision(), (short) 0, s);
            total += getFacade().getLectureByIdFaculty(facsub).size();

        }

        return total;
    }

    public String createA() {
        current.setIdFacultySubject(facSub);
        Lecture temp = current;
        try {
            getFacade().create(temp);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureCreated"));

            currentStudentController.setLec(temp);
            recreateModel();
            //  return "CreateAttendance?faces-redirect=true";
            currentStudentController.createAttendance();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("No Students Selected! Lecture Not created");

        } finally {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceCreated"));
            prepareCreate();
            return "View?faces-redirect=true";
        }
    }

    public String createAll() throws Exception {

        for (Lecture lec : lectureList) {
            List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
            for (int i = 0; i < attendance.size(); i++) {
                currentStudentController.getAttendanceController().createEntry(attendance.get(i));
                currentStudentController.getAttendanceController().destroyA();
            }
            lec.setIdFacultySubject(facSub);
            Lecture temp = lec;
            currentStudentController.setChecked(temp.getChecked());
            try {
                currentStudentController.setLec(temp);
                recreateModel();
                //  return "CreateAttendance?faces-redirect=true";
                currentStudentController.createAttendance();
            } catch (Exception e) {
                e.printStackTrace();
                JsfUtil.addErrorMessage("No Students Selected! Lecture Not created");

            } finally {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceCreated"));
                prepareCreate();
            }
        }
        return "View?faces-redirect=true";

    }

    public String prepareEdit() {
        current = (Lecture) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String prepareListTP(FacultySubject f) {
        facSub = f;
        recreateModel();
        return "FSLec?faces-redirect=true";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Lecture) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyLectureRestrict(Lecture lec) throws Exception {
        List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
        for (int i = 0; i < attendance.size(); i++) {
            currentStudentController.getAttendanceController().createEntry(attendance.get(i));
            currentStudentController.getAttendanceController().destroyA();
        }
        current = lec;
        performDestroy();
        current = null;
        recreatePagination();
        recreateModel();
        return "View?faces-redirect=true";
    }

    public String prepareUpdateLectureRestrict(Lecture lec) throws Exception {
        if (lec != null) {
            current = lec;
            currentStudentController.setLec(lec);
            currentStudentController.changeMap(currentStudentController.getChecked(), Boolean.FALSE);
            List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
            for (int i = 0; i < attendance.size(); i++) {
                currentStudentController.getChecked().put(attendance.get(i).getIdCurrentStudent().getIdCurrentStudent(), Boolean.TRUE);
            }

            return "UpdateLecture?faces-redirect=true";
        } else {
            return null;
        }
    }

    public String updateLectureRestrict() throws Exception {
        List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(currentStudentController.getLec());
        for (int i = 0; i < attendance.size(); i++) {
            currentStudentController.getAttendanceController().createEntry(attendance.get(i));
            currentStudentController.getAttendanceController().destroyA();
        }
        currentStudentController.createAttendance();
        update();

        return "View?faces-redirect=true";
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureDeleted"));
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

    private void recreateModel() {
        items = null;
        lectureByFS = null;
        lectureList = null;
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

    @FacesConverter(forClass = Lecture.class)
    public static class LectureControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LectureController controller = (LectureController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lectureController");
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
            if (object instanceof Lecture) {
                Lecture o = (Lecture) object;
                return getStringKey(o.getIdLecture());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Lecture.class.getName());
            }
        }
    }
}
