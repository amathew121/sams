package controllers;

import entities.Reviewer;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.ReviewerFacade;
import entities.Coordinator;
import entities.Faculty;
import entities.ProgramCourse;
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

@Named("reviewerController")
@SessionScoped
public class ReviewerController implements Serializable {

    private Reviewer current;
    private DataModel items = null;
    @EJB
    private beans.ReviewerFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private ProgramCourse pc;

    public ReviewerController() {
    }

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
        current = (Reviewer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public ProgramCourse getPc() {
        return pc;
    }

    public void setPc(ProgramCourse pc) {
        this.pc = pc;
    }
    
    public List<Reviewer> getLoggedUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacultyController facultyController = (FacultyController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "facultyController");
        Faculty idFaculty = facultyController.getFaculty(context.getExternalContext().getRemoteUser());
        return getFacade().getItemsByFaculty(idFaculty);
    }
    
    public void handleChange(ValueChangeEvent event) {
        System.out.println("here " + event.getNewValue());
    }

    public String prepareCreate() {
        current = new Reviewer();
        selectedItemIndex = -1;
        return "Create";
    }

    public void prepareCreateR() {
        prepareCreate();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/phcoe/faces/admin/Reviewer.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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

    public String createR() {
        create();
        return "Reviewer?faces-redirect=true";
    }

    public String prepareEdit() {
        current = (Reviewer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

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

    public String destroy() {
        current = (Reviewer) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }
    public String destroy(Reviewer item)
    {
        current=item;
        performDestroy();
        prepareCreateR();
        return "Reviewer";
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

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public DataModel getItemsByFaculty(Faculty idFaculty) {
        return new ListDataModel(getFacade().getItemsByFaculty(idFaculty));
    }
    
    public DataModel getItemsGroupByFaculty(){
        return new ListDataModel(getFacade().getReviewers());
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

    public Reviewer getReviewer(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Reviewer.class)
    public static class ReviewerControllerConverter implements Converter {

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
