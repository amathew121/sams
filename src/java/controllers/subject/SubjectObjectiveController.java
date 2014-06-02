package controllers.subject;

import entities.subject.SubjectObjective;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.SubjectObjectiveFacade;
import entities.subject.faculty.FacultySubject;
import entities.subject.Subject;

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
 *JSF Backing bean for subjectobjective Entity
 * @author Administrator
 */
@Named("subjectObjectiveController")
@SessionScoped
public class SubjectObjectiveController implements Serializable {

    private SubjectObjective current;
    private DataModel items = null;
    @EJB
    private beans.subject.SubjectObjectiveFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Subject sub;

    /**
     *creating a backing bean
     */
    public SubjectObjectiveController() {
    }

    /**
     *
     * @return
     */
    public Subject getSub() {
        return sub;
    }

    /**
     *Gets the selected subjectobjective entity
     * @return
     */
    public SubjectObjective getSelected() {
        if (current == null) {
            current = new SubjectObjective();
            selectedItemIndex = -1;
        }
        return current;
    }

    private SubjectObjectiveFacade getFacade() {
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
     *Sets the selected subjectobjective Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (SubjectObjective) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new subjectobjective Entity
     * @return
     */
    public String prepareCreate() {
        current = new SubjectObjective();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *
     */
    public void createA() {
        current.setIdSubject(sub);
        current.setIdSubjectObjective(0);
        create();
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubjectObjectiveCreated"));
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
        current = (SubjectObjective) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected subjectobjective entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubjectObjectiveUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @param subjectObjective
     * @return
     */
    public String destroyObjective(SubjectObjective subjectObjective) {
        current = subjectObjective;
        performDestroy();
        prepareCreate();
        return "SubjectObjective?faces-redirect=true";
    }

    /**
     *Destroys the selected subjectobjective entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (SubjectObjective) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("SubjectObjectiveDeleted"));
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
     *
     * @param sub
     * @return
     */
    public String prepareSubjectObjective(Subject sub){
        this.sub = sub;
        prepareCreate();
        return "SubjectObjective?faces-redirect=true";
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
     *Gets All subjectobjective entities as few items one at a time
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
     *Gets list of all subjectobjective entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all subjectobjective entities to be able to select one from it
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
    public SubjectObjective getSubjectObjective(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for subjectobjective Entity
     */
    @FacesConverter(forClass = SubjectObjective.class)
    public static class SubjectObjectiveControllerConverter implements Converter {

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
            SubjectObjectiveController controller = (SubjectObjectiveController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "subjectObjectiveController");
            return controller.getSubjectObjective(getKey(value));
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
            if (object instanceof SubjectObjective) {
                SubjectObjective o = (SubjectObjective) object;
                return getStringKey(o.getIdSubjectObjective());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + SubjectObjective.class.getName());
            }
        }
    }
}
