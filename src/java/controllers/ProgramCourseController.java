package controllers;

import entities.ProgramCourse;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.ProgramCourseFacade;

import java.io.Serializable;
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
 *JSF Backing bean for programcourse Entity
 * @author Administrator
 */
@ManagedBean(name = "programCourseController")
@SessionScoped
public class ProgramCourseController implements Serializable {

    private ProgramCourse current;
    private DataModel items = null;
    @EJB
    private beans.ProgramCourseFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     *creates a backing bean
     */
    public ProgramCourseController() {
    }

    /**
     *Gets the selected programcourse entity
     * @return
     */
    public ProgramCourse getSelected() {
        if (current == null) {
            current = new ProgramCourse();
            current.setProgramCoursePK(new entities.ProgramCoursePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ProgramCourseFacade getFacade() {
        return ejbFacade;
    }

    /**
     *Gets Pagination Helper to fetch range of items according to page.
     * gets 10 items at a time
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
     *Sets the selected programcourse Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (ProgramCourse) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new programcourse Entity
     * @return
     */
    public String prepareCreate() {
        current = new ProgramCourse();
        current.setProgramCoursePK(new entities.ProgramCoursePK());
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            current.getProgramCoursePK().setIdCourse(current.getCourse().getIdCourse());
            current.getProgramCoursePK().setIdProgram(current.getProgram().getIdProgram());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProgramCourseCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Sets the selected item for editing
     * @return
     */
    public String prepareEdit() {
        current = (ProgramCourse) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected programcourse entity in the database
     * @return
     */
    public String update() {
        try {
            current.getProgramCoursePK().setIdCourse(current.getCourse().getIdCourse());
            current.getProgramCoursePK().setIdProgram(current.getProgram().getIdProgram());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProgramCourseUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected programcourse entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (ProgramCourse) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ProgramCourseDeleted"));
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
     *Gets All programcourse entities as few items one at a time
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
     *Gets list of all programcourse entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all programcourse entities to be able to select one from it
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
    public ProgramCourse getProgramCourse(entities.ProgramCoursePK id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for programcourse Entity
     */
    @FacesConverter(forClass = ProgramCourse.class)
    public static class ProgramCourseControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

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
            ProgramCourseController controller = (ProgramCourseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "programCourseController");
            return controller.getProgramCourse(getKey(value));
        }

        entities.ProgramCoursePK getKey(String value) {
            entities.ProgramCoursePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.ProgramCoursePK();
            key.setIdProgram(values[0]);
            key.setIdCourse(values[1]);
            return key;
        }

        String getStringKey(entities.ProgramCoursePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdProgram());
            sb.append(SEPARATOR);
            sb.append(value.getIdCourse());
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
            if (object instanceof ProgramCourse) {
                ProgramCourse o = (ProgramCourse) object;
                return getStringKey(o.getProgramCoursePK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ProgramCourse.class.getName());
            }
        }
    }
}
