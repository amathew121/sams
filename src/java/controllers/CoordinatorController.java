package controllers;

import entities.Coordinator;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.CoordinatorFacade;
import entities.Faculty;

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

@Named("coordinatorController")
@SessionScoped
public class CoordinatorController implements Serializable {

    private Coordinator current;
    private DataModel items = null;
    @EJB
    private beans.CoordinatorFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public CoordinatorController() {
    }

    public Coordinator getSelected() {
        if (current == null) {
            current = new Coordinator();
            current.setCoordinatorPK(new entities.CoordinatorPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private CoordinatorFacade getFacade() {
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

    public Coordinator getLoggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacultyController facultyController = (FacultyController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "facultyController");
        Faculty idFaculty = facultyController.getFaculty(context.getExternalContext().getRemoteUser());
        return getFacade().findByUser(idFaculty);
    }
    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Coordinator) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Coordinator();
        current.setCoordinatorPK(new entities.CoordinatorPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            current.getCoordinatorPK().setIdFaculty(current.getFaculty().getIdFaculty());
            current.getCoordinatorPK().setIdCourse(current.getProgramCourse().getProgramCoursePK().getIdCourse());
            current.getCoordinatorPK().setIdProgram(current.getProgramCourse().getProgramCoursePK().getIdProgram());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CoordinatorCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Coordinator) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            current.getCoordinatorPK().setIdFaculty(current.getFaculty().getIdFaculty());
            current.getCoordinatorPK().setIdCourse(current.getProgramCourse().getProgramCoursePK().getIdCourse());
            current.getCoordinatorPK().setIdProgram(current.getProgramCourse().getProgramCoursePK().getIdProgram());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CoordinatorUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Coordinator) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("CoordinatorDeleted"));
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

    public Coordinator getCoordinator(entities.CoordinatorPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Coordinator.class)
    public static class CoordinatorControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CoordinatorController controller = (CoordinatorController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "coordinatorController");
            return controller.getCoordinator(getKey(value));
        }

        entities.CoordinatorPK getKey(String value) {
            entities.CoordinatorPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.CoordinatorPK();
            key.setIdFaculty(values[0]);
            key.setIdProgram(values[1]);
            key.setIdCourse(values[2]);
            key.setSemester(Short.parseShort(values[3]));
            key.setDivision(values[4]);
            return key;
        }

        String getStringKey(entities.CoordinatorPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdFaculty());
            sb.append(SEPARATOR);
            sb.append(value.getIdProgram());
            sb.append(SEPARATOR);
            sb.append(value.getIdCourse());
            sb.append(SEPARATOR);
            sb.append(value.getSemester());
            sb.append(SEPARATOR);
            sb.append(value.getDivision());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Coordinator) {
                Coordinator o = (Coordinator) object;
                return getStringKey(o.getCoordinatorPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Coordinator.class.getName());
            }
        }
    }
}
