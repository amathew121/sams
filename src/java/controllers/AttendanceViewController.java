package controllers;

import entities.AttendanceView;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.AttendanceViewFacade;
import entities.FacultySubject;
import entities.Lecture;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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
 *JSF Backing bean for Attendanceview Entity
 * @author Administrator
 */
@ManagedBean(name = "attendanceViewController")
@SessionScoped
public class AttendanceViewController implements Serializable {

    private AttendanceView current;
    private DataModel items = null;
    @EJB
    private beans.AttendanceViewFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     * creating a backing bean
     */
    public AttendanceViewController() {
    }

    /**
     *Gets the selected attendanceview entity
     * @return
     */
    public AttendanceView getSelected() {
        if (current == null) {
            current = new AttendanceView();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AttendanceViewFacade getFacade() {
        return ejbFacade;
    }

    /**
     *Gets Pagination Helper to fetch range of items according to page.
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
     *Resets the list of items and navigates to List
     * @return
     */
    public String prepareList() {
        recreateModel();
        return "List";
    }

    /**
     *Sets the selected Attendanceview Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (AttendanceView) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new Attendanceview Entity
     * @return
     */
    public String prepareCreate() {
        current = new AttendanceView();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceViewCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @param facSub
     * @return
     * @throws Exception
     */
    public List<Lecture> getAttendanceByFS(FacultySubject facSub) throws Exception {

        List<AttendanceView> l = getFacade().getAttendanceByFS(facSub);
        FacesContext context = FacesContext.getCurrentInstance();
        LectureController lc = (LectureController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "lectureController");

        List<Lecture> ll = lc.getLectureByFSList(facSub);
        Map<Integer, Long> hm = new HashMap<Integer, Long>();
        
        for (Lecture item : ll) {
            hm.put(item.getIdLecture(), 0L);
        }
        
        for (AttendanceView item : l) {
            Lecture lec = lc.getLectureByLecID(item.getIdLecture());
            hm.put(item.getIdLecture(), getAttendanceCount(facSub,lec));
        }
        
        for (Lecture item : ll) {
            item.setAttendanceCount(hm.get(item.getIdLecture()));
        }


        return ll;
    }

    /**
     *
     * @param facSub
     * @param lec
     * @return
     */
    public Long getAttendanceCount(FacultySubject facSub, Lecture lec) {
        return getFacade().getAttendanceByFSCount(facSub, lec);
    }

    ///NOT USED

    /**
     *
     * @param facSub
     * @param lec
     * @return
     */
        public List<AttendanceView> getAttendanceByFSLec(FacultySubject facSub, Lecture lec) {
        return getFacade().findAll();
    }

    /**
     *Sets the selected item for editing.
     * @return
     */
    public String prepareEdit() {
        current = (AttendanceView) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected Attendanceview entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceViewUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected Attendanceview entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (AttendanceView) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceViewDeleted"));
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
     *Gets All Attendanceview entities as few items one at a time
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
     *Navigation case to next page with next items
     * @return
     */
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    /**
     *Navigation case to previous page with previous items
     * @return
     */
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    /**
     *Gets list of all Attendanceview entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all Attendanceview entities to be able to select one from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    /**
     *Converter Class for Attendanceview Entity
     */
    @FacesConverter(forClass = AttendanceView.class)
    public static class AttendanceViewControllerConverter implements Converter {

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
            AttendanceViewController controller = (AttendanceViewController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "attendanceViewController");
            return controller.ejbFacade.find(getKey(value));
        }

        long getKey(String value) {
            long key;
            key = Long.parseLong(value);
            return key;
        }

        String getStringKey(BigInteger value) {
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
            if (object instanceof AttendanceView) {
                AttendanceView o = (AttendanceView) object;
                return getStringKey(o.getIdAttendance());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + AttendanceView.class.getName());
            }
        }
    }
}
