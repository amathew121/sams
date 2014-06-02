package controllers;

import entities.OldFacultyComments;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.OldFacultyCommentsFacade;
import entities.OldFbPi;

import java.io.Serializable;
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
 * JSF Backing bean for oldfacultycomments Entity
 * @author Administrator
 */
@Named("oldFacultyCommentsController")
@SessionScoped
public class OldFacultyCommentsController implements Serializable {

    private OldFacultyComments current;
    private DataModel items = null;
    @EJB
    private beans.OldFacultyCommentsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel detailsByFS;
    private String subID;
    private String div;
    private short ftype;
    private short batch;
    private String userName;

    /**
     * creates the backing bean
     */
    public OldFacultyCommentsController() {
    }

    /**
     *Gets the selected oldfacultycomments entity
     * @return
     */
    public OldFacultyComments getSelected() {
        if (current == null) {
            current = new OldFacultyComments();
            selectedItemIndex = -1;
        }
        return current;
    }

    private OldFacultyCommentsFacade getFacade() {
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
     *Sets the selected oldfacultycomments Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (OldFacultyComments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new oldfacultycomments Entity
     * @return
     */
    public String prepareCreate() {
        current = new OldFacultyComments();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFacultyCommentsCreated"));
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
        current = (OldFacultyComments) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected oldfacultycomments entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFacultyCommentsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected oldfacultycomments entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (OldFacultyComments) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFacultyCommentsDeleted"));
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
     *Gets All oldfacultycomments entities as few items one at a time
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
     * @param item
     * @return
     */
    public String getFeedbackComments(OldFbPi item) {
        this.userName = item.getOldFbPiPK().getFacId();
        this.subID = item.getOldFbPiPK().getSubId();
        this.div = item.getOldFbPiPK().getDivision();
        this.ftype = item.getFtype();
        this.batch = item.getOldFbPiPK().getBatch();
        recreateModel();
           return "FeedbackComments?faces-redirect=true";

    }

    /**
     *
     * @param item
     * @return
     */
    public String getFeedbackCommentsAdmin(OldFbPi item) {
        this.userName = item.getOldFbPiPK().getFacId();
        this.subID = item.getOldFbPiPK().getSubId();
        this.div = item.getOldFbPiPK().getDivision();
        this.ftype = item.getFtype();
        this.batch = item.getOldFbPiPK().getBatch();
        recreateModel();
        return null;
    }

    /**
     *
     * @return
     */
    public String getSubID() {
        return subID;
    }

    /**
     *
     * @return
     */
    public String getDivision() {
        return div;
    }

    /**
     *
     * @return
     */
    public short getBatch() {
        return batch;
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     *
     * @return
     */
    public DataModel getDetailsByFS() {
        detailsByFS = new ListDataModel(getFacade().getByFS(userName, div, ftype, batch, subID));
        return detailsByFS;
    }

    private void recreateModel() {
        items = null;
        detailsByFS = null;
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
     *Gets list of all oldfacultycomments entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all oldfacultycomments entities to be able to select one from it
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
    public OldFacultyComments getOldFacultyComments(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for oldfacultycomments Entity
     */
    @FacesConverter(forClass = OldFacultyComments.class)
    public static class OldFacultyCommentsControllerConverter implements Converter {

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
            OldFacultyCommentsController controller = (OldFacultyCommentsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oldFacultyCommentsController");
            return controller.getOldFacultyComments(getKey(value));
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
            if (object instanceof OldFacultyComments) {
                OldFacultyComments o = (OldFacultyComments) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + OldFacultyComments.class.getName());
            }
        }
    }
}
