package controllers;

import entities.Feedback2013Student;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.Feedback2013StudentFacade;
import entities.Coordinator;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *JSF Backing bean for feedback2013student Entity
 * @author Administrator
 */
@Named("feedback2013StudentController")
@SessionScoped
public class Feedback2013StudentController implements Serializable {

    private Feedback2013Student current;
    private DataModel items = null;
    @EJB
    private beans.Feedback2013StudentFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Coordinator c;

    /**
     *creates a backing bean
     */
    public Feedback2013StudentController() {
    }

    /**
     *Gets the selected feedback2013student entity
     * @return
     */
    public Feedback2013Student getSelected() {
        if (current == null) {
            current = new Feedback2013Student();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Feedback2013StudentFacade getFacade() {
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
     *Sets the selected feedback2013student Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (Feedback2013Student) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new feedback2013student Entity
     * @return
     */
    public String prepareCreate() {
        current = new Feedback2013Student();
        selectedItemIndex = -1;
        return "Create";
    }
    
    /**
     *
     */
    public void prepareStudentList() {
        FacesContext context = FacesContext.getCurrentInstance();
        CoordinatorController coordinatorController = (CoordinatorController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "coordinatorController");
        c = coordinatorController.getLoggedUser();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/FeedbackUID.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @return
     */
    public List<Feedback2013Student> getStudentList() {
        
        if (c != null)
            return getFacade().getStudentList(c);
        else
            return null;
    }

    /**
     *
     * @return
     */
    public Coordinator getC() {
        return c;
    }

    /**
     *
     * @param c
     */
    public void setC(Coordinator c) {
        this.c = c;
    }
    
    /**
     *
     * @return
     */
    public String navSame() {
        return "FeedbackUID?faces-redirect=true";
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013StudentCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Sets the selected item for editing.
     * Navigation case to Edit page.
     * @return
     */
    public String prepareEdit() {
        current = (Feedback2013Student) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected feedback2013student entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013StudentUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected feedback2013student entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (Feedback2013Student) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013StudentDeleted"));
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
     *Gets All feedback2013student entities as few items one at a time
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
     *Gets list of all feedback2013student entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all feedback2013student entities to be able to select one from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    /**
     *
     * @param id
     * @return
     */
    public Feedback2013Student getFeedback2013Student(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for feedback2013student Entity
     */
    @FacesConverter(forClass = Feedback2013Student.class)
    public static class Feedback2013StudentControllerConverter implements Converter {

        /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            Feedback2013StudentController controller = (Feedback2013StudentController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "feedback2013StudentController");
            return controller.getFeedback2013Student(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
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
        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Feedback2013Student) {
                Feedback2013Student o = (Feedback2013Student) object;
                return getStringKey(o.getUid());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Feedback2013Student.class.getName());
            }
        }
    }
}
