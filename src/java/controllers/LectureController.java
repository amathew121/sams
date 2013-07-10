package controllers;

import entities.Lecture;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.LectureFacade;
import entities.Attendance;
import entities.CurrentStudent;
import entities.FacultySubject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

@ManagedBean(name = "lectureController")
@SessionScoped
public class LectureController implements Serializable {

    private Lecture current;
    private List<Lecture> l;
    private List <CurrentStudent> selectedList = new ArrayList <CurrentStudent> ();
    private CurrentStudent[] selectList;
    private DataModel items = null;

    public CurrentStudent[] getSelectList() {
        return selectList;
    }

    public void setSelectList(CurrentStudent[] selectList) {
        this.selectList = selectList;
    }
    
    @ManagedProperty(value="#{attendanceController}")
    private AttendanceController attendanceController;

    public AttendanceController getAttendanceController() {
        return attendanceController;
    }

    public void setAttendanceController(AttendanceController attendanceController) {
        this.attendanceController = attendanceController;
    }
    
    @ManagedProperty(value="#{currentStudentController}")
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
    private FacultySubject facSub = null;

    public FacultySubject getFacSub() {
        return facSub;
    }

    public void setFacSub(FacultySubject facSub) {
        this.facSub = facSub;
    }

    public LectureController() {
    }


    public DataModel getL() {
        //l = getFacade().getLectureByIdFaculty();
        DataModel d = new ListDataModel(getFacade().getLectureByIdFaculty());
        return d;
    }

    public void setL(List<Lecture> l) {
        this.l = l;
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

    public String prepareCreateWithId(int i)
    {
       prepareCreate();
       idFacSub = i;
     //  FacultySubject facSub = new 
      // FacultySubjectController fsc = new FacultySubjectController();
      // fsc.getIdFacSub(idFacSub);
       getFacSubject(idFacSub);
       current.setIdFacultySubject(getFacSub());
       return "Create";
       
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


    public List<CurrentStudent> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<CurrentStudent> selectedList) {
        this.selectedList = selectedList;
    }
        
    

    public String createA() throws Exception{
        Lecture temp = current;
        create();
        List<CurrentStudent> csl = new ArrayList();
        csl = currentStudentController.getCurrentStudentList();
        for(int i=0; i< csl.size(); i++)
        {
            if(csl.get(i).isSelected())
                selectedList.add(csl.get(i));
            CurrentStudent t = csl.get(i);
            t.setSelected(false);
            csl.set(i, t);
            
        }
        currentStudentController.setCurrentStudentList(csl);
        
        List <Attendance> att = new ArrayList <Attendance>();
        for(int i = 0; i<selectedList.size();i++) {
           
            Attendance ae = new Attendance();
            
            
            ae.setIdAttendance(Long.MIN_VALUE);
            ae.setIdCurrentStudent(selectedList.get(i));
            ae.setIdLecture(temp);
            att.add(ae);
            
            attendanceController.createEntry(ae);
            attendanceController.create();
        }
        
        return prepareList();
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
