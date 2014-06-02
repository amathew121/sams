package controllers;

import entities.Attendance;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.AttendanceFacade;
import entities.FacultySubject;
import entities.Lecture;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 * JSF Backing bean for Attendance Entity
 * @author Administrator
 */
@ManagedBean(name = "attendanceController")
@SessionScoped
public class AttendanceController implements Serializable {

    private Attendance current;
    private DataModel items = null; 
    @EJB
    private beans.AttendanceFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     * Creates the backing bean
     */
    public AttendanceController() {
    }
    
    /**
     * Runs once after the constructor is called at the initialization of the bean
     */
    @PostConstruct
    public void init()
    {
        current= new Attendance();
    }

    /**
     * Gets the selected attendance entity
     * @return
     */
    public Attendance getSelected() {
        if (current == null) {
            current = new Attendance();
            selectedItemIndex = -1;
        }
        return current;
    }

    /**
     *
     * @param c
     * @throws Exception
     */
    public void createEntry(Attendance c) throws Exception
    {
        current = new Attendance();
        current = c;
    }
    
    private AttendanceFacade getFacade() {
        return ejbFacade;
    }

    /**
     * Gets Pagination Helper to fetch range of items according to page.
     * Gets 10 items at a time.
     * @return
     */
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

    /**
     * Resets the list of items and navigates to List
     * @return
     */
    public String prepareList() {
        recreateModel();
        return "List";
    }

    /**
     * Sets the selected Attendance Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (Attendance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     * Navigation case to Create page after initializing a new Attendance Entity
     * @return
     */
    public String prepareCreate() {
        current = new Attendance();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     * Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Sets the selected item for editing.
     * Navigation case to Edit page.
     * @return
     */
    public String prepareEdit() {
        current = (Attendance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *
     * @param lec
     * @return
     */
    public List<Attendance> getAttendanceByFSLec(Lecture lec) {
        return getFacade().getAttendanceByFSLec(lec);
    }

    /**
     * Updates the selected Attendance entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Destroys the selected Attendance entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (Attendance) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }
    
    /**
     *
     * @return
     */
    public String destroyA() {
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    /**
     * 
     * @return
     */
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceDeleted"));
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

    /**
     * Gets All Attendance entities as few items one at a time
     * @return
     */
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

    /**
     * Navigation case to next page with next items
     * @return
     */
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    /**
     * Navigation case to previous page with previous items
     * @return
     */
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    /**
     * Gets list of all Attendance entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     * Gets list of all Attendance entities to be able to select one from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    /**
     * Converter Class for Attendance Entity
     */
    @FacesConverter(forClass = Attendance.class)
    public static class AttendanceControllerConverter implements Converter {

        /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AttendanceController controller = (AttendanceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "attendanceController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        /**
         *
         * @param facesContext
         * @param component
         * @param object
         * @return
         */
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Attendance) {
                Attendance o = (Attendance) object;
                return getStringKey(o.getIdAttendance());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Attendance.class.getName());
            }
        }
    }
}
