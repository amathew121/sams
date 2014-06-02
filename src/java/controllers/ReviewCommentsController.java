package controllers;

import entities.ReviewComments;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.ReviewCommentsFacade;
import entities.Faculty;
import entities.FacultySubject;

import java.io.Serializable;
import java.util.Date;
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
 * JSF Backing bean for reviewcomments Entity
 * @author Administrator
 */
@Named("reviewCommentsController")
@SessionScoped
public class ReviewCommentsController implements Serializable {

    private ReviewComments current;
    private DataModel items = null;
    @EJB
    private beans.ReviewCommentsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     * creating a backing bean
     */
    public ReviewCommentsController() {
    }

    /**
     *Gets the selected attendance entity
     * @return
     */
    public ReviewComments getSelected() {
        if (current == null) {
            current = new ReviewComments();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ReviewCommentsFacade getFacade() {
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
     *Sets the selected reviewcomments Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (ReviewComments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new reviewcomments Entity
     * @return
     */
    public String prepareCreate() {
        current = new ReviewComments();
        selectedItemIndex = -1;
        return "Create";
    }
    
    /**
     *
     * @param idFacSub
     * @param type
     */
    public void ajaxCreate(FacultySubject idFacSub, short type){
        current.setIdFacultySubject(idFacSub);
        FacesContext context = FacesContext.getCurrentInstance();
        FacultyController facultyController = (FacultyController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "facultyController");
        Faculty reviewed_by = facultyController.getFaculty(context.getExternalContext().getRemoteUser());
        current.setReviewedBy(reviewed_by);
        current.setReviewedOn(new Date());
        current.setReviewType(type);
        current.setIdReviewComments(0);
        create();
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewCommentsCreated"));
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
        current = (ReviewComments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected Attendance entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewCommentsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected Attendance entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (ReviewComments) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ReviewCommentsDeleted"));
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
     *Gets All reviewcomments entities as few items one at a time
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
     * @param idFacSub
     * @param type
     * @return
     */
    public List<ReviewComments> getComments(FacultySubject idFacSub, short type) {
        return getFacade().getByIdFacSubType(idFacSub, type);
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
     *Gets list of all reviewcomments entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all reviewcomments entities to be able to select one from it
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
    public ReviewComments getReviewComments(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for reviewcomments Entity
     */
    @FacesConverter(forClass = ReviewComments.class)
    public static class ReviewCommentsControllerConverter implements Converter {

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
            ReviewCommentsController controller = (ReviewCommentsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "reviewCommentsController");
            return controller.getReviewComments(getKey(value));
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
            if (object instanceof ReviewComments) {
                ReviewComments o = (ReviewComments) object;
                return getStringKey(o.getIdReviewComments());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ReviewComments.class.getName());
            }
        }
    }
}
