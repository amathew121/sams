package controllers.feedback;

import entities.feedback.Feedback2013Comments;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.feedback.Feedback2013CommentsFacade;
import entities.subject.faculty.FacultySubject;
import entities.feedback.FeedbackType;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
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
 * JSF Backing bean for feedback2013comments Entity
 * @author Administrator
 */
@Named("feedback2013CommentsController")
@SessionScoped
public class Feedback2013CommentsController implements Serializable {

    private Feedback2013Comments current;
    private DataModel items = null;
    @EJB
    private beans.feedback.Feedback2013CommentsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private List feedback2013CommentsList;
    private FacultySubject idFacultySubject;

    /**
     * creates the backing bean
     */
    public Feedback2013CommentsController() {
    }

    /**
     *Gets the selected feedback2013comments entity
     * @return
     */
    public Feedback2013Comments getSelected() {
        if (current == null) {
            current = new Feedback2013Comments();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Feedback2013CommentsFacade getFacade() {
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
     *Resets the list of items and navigates to List
     * @return
     */
    public String prepareList() {
        recreateModel();
        return "List";
    }

    /**
     *Sets the selected feedback2013comments Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (Feedback2013Comments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     * Navigation case to Create page after initializing a new feedback2013comments Entity
     * @return
     */
    public String prepareCreate() {
        current = new Feedback2013Comments();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *
     * @return
     */
    public List<Feedback2013Comments> getFeedback2013CommentsList() {
        return feedback2013CommentsList;
    }

    /**
     *
     * @return
     */
    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }
    
    public String getByUserName(FacultySubject idFacSub, FeedbackType fType) {
        this.idFacultySubject = idFacSub;
        feedback2013CommentsList = getFacade().getByUserName(idFacSub, fType);
        //return "Feedback2013Comments.xhtml?faces-redirect=true";
        return null;
    }
    public List<Feedback2013Comments> getByUserNameComments(FacultySubject idFacSub, FeedbackType fType) {
        this.idFacultySubject = idFacSub;
        feedback2013CommentsList = getFacade().getByUserName(idFacSub, fType);
        //return "Feedback2013Comments.xhtml?faces-redirect=true";
        return feedback2013CommentsList;
    }
    
    /**
     * Creates a new record in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013CommentsCreated"));
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
        current = (Feedback2013Comments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected feedback2013comments entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013CommentsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Destroys the selected feedback2013comments entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (Feedback2013Comments) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013CommentsDeleted"));
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
     *Gets All feedback2013comments entities as few items one at a time
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
     * Navigation case to previous page with previous items
     * @return
     */
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    /**
     *Gets list of all feedback2013comments entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all feedback2013comments entities to be able to select one from it
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
    public Feedback2013Comments getFeedback2013Comments(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for feedback2013comments Entity
     */
    @FacesConverter(forClass = Feedback2013Comments.class)
    public static class Feedback2013CommentsControllerConverter implements Converter {

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
            Feedback2013CommentsController controller = (Feedback2013CommentsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "feedback2013CommentsController");
            return controller.getFeedback2013Comments(getKey(value));
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
            if (object instanceof Feedback2013Comments) {
                Feedback2013Comments o = (Feedback2013Comments) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Feedback2013Comments.class.getName());
            }
        }
    }
}
