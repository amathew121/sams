package controllers.users;

import controllers.users.FacultyController;
import entities.users.Reviewer;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.users.ReviewerFacade;
import controllers.subject.faculty.FacultySubjectViewController;
import entities.users.Coordinator;
import entities.users.Faculty;
import entities.subject.ProgramCourse;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *JSF Backing bean for reviewer Entity
 * @author Administrator
 */
@Named("reviewerController")
@SessionScoped
public class ReviewerController implements Serializable {

    private Reviewer current;
    private DataModel items = null;
    @EJB
    private beans.users.ReviewerFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private ProgramCourse pc;

    /**
     *creates the backing bean
     */
    public ReviewerController() {
    }

    /**
     *Gets the selected reviewer entity
     * @return
     */
    public Reviewer getSelected() {
        if (current == null) {
            current = new Reviewer();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ReviewerFacade getFacade() {
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
     *Sets the selected Attendance Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (Reviewer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *
     * @return
     */
    public ProgramCourse getPc() {
        return pc;
    }

    /**
     *
     * @param pc
     */
    public void setPc(ProgramCourse pc) {
        this.pc = pc;
    }
    
    /**
     *
     * @return
     */
    public List<Reviewer> getLoggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacultyController facultyController = (FacultyController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "facultyController");
        Faculty idFaculty = facultyController.getFaculty(context.getExternalContext().getRemoteUser());
        return getFacade().getItemsByFaculty(idFaculty);
    }
    
    /**
     *
     * @param event
     */
    public void handleChange(ValueChangeEvent event) {
        System.out.println("here " + event.getNewValue());
    }

    /**
     *Navigation case to Create page after initializing a new reviewer Entity
     * @return
     */
    public String prepareCreate() {
        current = new Reviewer();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *
     */
    public void prepareCreateR() {
        prepareCreate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/Reviewer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            current.setIdReviewer(0);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewerCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String createR() {
        create();
        return "Reviewer?faces-redirect=true";
    }

    /**
     *Sets the selected item for editing.
     * Navigation case to Edit page.
     * @return
     */
    public String prepareEdit() {
        current = (Reviewer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected reviewer entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewerUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected reviewer entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (Reviewer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    /**
     *
     * @param item
     * @return
     */
    public String destroy(Reviewer item)
    {
        current=item;
        performDestroy();
        prepareCreateR();
        return "Reviewer";
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewerDeleted"));
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
     *Gets All reviewer entities as few items one at a time
     * @return
     */
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    /**
     *
     * @param idFaculty
     * @return
     */
    public DataModel getItemsByFaculty(Faculty idFaculty) {
        return new ListDataModel(getFacade().getItemsByFaculty(idFaculty));
    }
    
    /**
     *
     * @return
     */
    public DataModel getItemsGroupByFaculty(){
        return new ListDataModel(getFacade().getReviewers());
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
     *Gets list of all reviewer entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all reviewer entities to be able to select one from it
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
    public Reviewer getReviewer(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     *
     */
    @FacesConverter(forClass = Reviewer.class)
    public static class ReviewerControllerConverter implements Converter {

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
            ReviewerController controller = (ReviewerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reviewerController");
            return controller.getReviewer(getKey(value));
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
            if (object instanceof Reviewer) {
                Reviewer o = (Reviewer) object;
                return getStringKey(o.getIdReviewer());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Reviewer.class.getName());
            }
        }
    }
}
