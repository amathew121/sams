package controllers;

import entities.SubjectEvaluation;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.SubjectEvaluationFacade;
import entities.FacultySubject;
import entities.Subject;
import entities.SubjectObjective;

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
 *JSF Backing bean for subjectevaluation Entity
 * @author Administrator
 */
@Named("subjectEvaluationController")
@SessionScoped
public class SubjectEvaluationController implements Serializable {

    private SubjectEvaluation current;
    private DataModel items = null;
    @EJB
    private beans.SubjectEvaluationFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Subject sub;

    /**
     * creating a backing bean
     */
    public SubjectEvaluationController() {
    }

    /**
     *
     * @return
     */
    public Subject getSub() {
        return sub;
    }

    /**
     *Gets the selected subjectevaluation entity
     * @return
     */
    public SubjectEvaluation getSelected() {
        if (current == null) {
            current = new SubjectEvaluation();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SubjectEvaluationFacade getFacade() {
        return ejbFacade;
    }

    /**
     *
     * @return
     */
    public DataModel getItemsUser() {
        return new ListDataModel(getFacade().getByIdSubject(sub));
    }

    /**
     *
     * @param facSub
     * @return
     */
    public DataModel getItemsUser(FacultySubject facSub) {
        return new ListDataModel(getFacade().getByIdSubject(facSub.getIdSubject()));
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
     *
     * @param sub
     * @return
     */
    public String prepareSubjectEvaluation(Subject sub) {
        this.sub = sub;
        prepareCreate();

        return "SubjectEvaluation?faces-redirect=true";
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
     *Sets the selected subjectevaluation Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (SubjectEvaluation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new subjectevaluation Entity
     * @return
     */
    public String prepareCreate() {
        current = new SubjectEvaluation();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *
     */
    public void createA() {
        current.setIdSubject(sub);
        current.setIdSubjectEvaluation(0);
        create();
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubjectEvaluationCreated"));
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
        current = (SubjectEvaluation) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected subjectevaluation entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubjectEvaluationUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @param subjectEvaluation
     * @return
     */
    public String destroyObjective(SubjectEvaluation subjectEvaluation) {
        current = subjectEvaluation;
        performDestroy();
        prepareCreate();
        return "SubjectEvaluation?faces-redirect=true";
    }

    /**
     *Destroys the selected subjectevaluation entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (SubjectEvaluation) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubjectEvaluationDeleted"));
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
     *Gets All subjectevaluation entities as few items one at a time
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
     *Gets list of all subjectevaluation entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all subjectevaluation entities to be able to select one from it
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
    public SubjectEvaluation getSubjectEvaluation(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for subjectevaluation Entity
     */
    @FacesConverter(forClass = SubjectEvaluation.class)
    public static class SubjectEvaluationControllerConverter implements Converter {

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
            SubjectEvaluationController controller = (SubjectEvaluationController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "subjectEvaluationController");
            return controller.getSubjectEvaluation(getKey(value));
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
            if (object instanceof SubjectEvaluation) {
                SubjectEvaluation o = (SubjectEvaluation) object;
                return getStringKey(o.getIdSubjectEvaluation());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SubjectEvaluation.class.getName());
            }
        }
    }
}
