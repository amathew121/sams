package controllers;

import entities.Lecture;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.LectureFacade;
import entities.Attendance;
import entities.CurrentStudent;
import entities.FacultySubject;
import entities.TeachingPlan;

import java.io.Serializable;
import java.util.ArrayList;
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

    public String createA() throws Exception {
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
            destroy();
            JsfUtil.addErrorMessage("No Students Selected! Lecture Not created");

        } finally {
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceCreated"));
            prepareCreate();
            return "View?faces-redirect=true";
        }
    }

    public String prepareEdit() {
        current = (Lecture) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
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
        /*   List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
         for (int i = 0; i < attendance.size(); i++) {
         currentStudentController.getAttendanceController().createEntry(attendance.get(i));
         currentStudentController.getAttendanceController().destroy();
         }
         current = lec;
         performDestroy();
         current = null;
         recreatePagination();
         recreateModel();
         return "View?faces-redirect=true";*/
        current = lec;
        List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
        for (int i = 0; i < attendance.size(); i++) {
            currentStudentController.getChecked().put(attendance.get(i).getIdCurrentStudent().getIdCurrentStudent(), Boolean.TRUE);
            currentStudentController.getAttendanceController().createEntry(attendance.get(i));
            currentStudentController.getAttendanceController().destroyA();
        }

        return "UpdateLecture?faces-redirect=true";
    }

    public String updateLectureRestrict() throws Exception {
        update();
        currentStudentController.createAttendance();

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
