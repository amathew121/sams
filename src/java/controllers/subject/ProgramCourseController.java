package controllers.subject;

import entities.subject.ProgramCourse;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.ProgramCourseFacade;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named("programCourseController")
@SessionScoped
public class ProgramCourseController implements Serializable {

    private ProgramCourse current;
    private DataModel items = null;
    @EJB
    private beans.subject.ProgramCourseFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public ProgramCourseController() {
    }

    public ProgramCourse getSelected() {
        if (current == null) {
            current = new ProgramCourse();
            current.setProgramCoursePK(new entities.subject.ProgramCoursePK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private ProgramCourseFacade getFacade() {
        return ejbFacade;
    }

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

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (ProgramCourse) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new ProgramCourse();
        current.setProgramCoursePK(new entities.subject.ProgramCoursePK());
        selectedItemIndex = -1;
        return "Create";
    }

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

    public String prepareEdit() {
        current = (ProgramCourse) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

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

    public String destroy() {
        current = (ProgramCourse) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

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

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public ProgramCourse getProgramCourse(entities.subject.ProgramCoursePK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = ProgramCourse.class)
    public static class ProgramCourseControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProgramCourseController controller = (ProgramCourseController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "programCourseController");
            return controller.getProgramCourse(getKey(value));
        }

        entities.subject.ProgramCoursePK getKey(String value) {
            entities.subject.ProgramCoursePK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.subject.ProgramCoursePK();
            key.setIdProgram(values[0]);
            key.setIdCourse(values[1]);
            return key;
        }

        String getStringKey(entities.subject.ProgramCoursePK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdProgram());
            sb.append(SEPARATOR);
            sb.append(value.getIdCourse());
            return sb.toString();
        }

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
