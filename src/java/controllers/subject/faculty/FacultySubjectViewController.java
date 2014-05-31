package controllers.subject.faculty;

import entities.subject.faculty.FacultySubjectView;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.FacultySubjectViewFacade;
import entities.users.Department;
import entities.users.Faculty;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Named("facultySubjectViewController")
@SessionScoped
public class FacultySubjectViewController implements Serializable {

    private FacultySubjectView current;
    private DataModel items = null;
    private DataModel modelByUserName = null;
    @EJB
    private beans.subject.faculty.FacultySubjectViewFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Department deptSelected;

    public FacultySubjectViewController() {
    }

    public FacultySubjectView getSelected() {
        if (current == null) {
            current = new FacultySubjectView();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FacultySubjectViewFacade getFacade() {
        return ejbFacade;
    }

    public Department getDeptSelected() {
        return deptSelected;
    }

    public void setDeptSelected(Department deptSelected) {
        this.deptSelected = deptSelected;
    }

    public DataModel getModelByUserName() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        modelByUserName = new ListDataModel(getFacade().getFSViewById(userName));
        return modelByUserName;
    }
        public DataModel getModelByUserNameEven() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        modelByUserName = new ListDataModel(getFacade().getFSViewByIdEven(userName));
        return modelByUserName;
    }
    
    public DataModel getModelByUserName(String userName) {
        modelByUserName = new ListDataModel(getFacade().getFSViewById(userName));
        return modelByUserName;
    }
    
    public DataModel getModelByUserNameGroup() {
        return new ListDataModel(getFacade().getFSViewByIdGroup());

    }
    public String oddSem() {
        return "ListOdd?faces-redirect=true";
    }
    public List<FacultySubjectView> getListByDept(Faculty fac) {
            Department dept = deptSelected;

        if (dept != null) {
            return getFacade().getFSViewByDept(dept.getIdDepartment());
        } else {
            return null;
        }
    }

    public List<FacultySubjectView> getListByDeptSub(String sub) {
        return getFacade().getFSViewByDeptSub(sub, "CS");
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                @Override
                public int getItemsCount() {
                    return getFacade().getCount();
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

    public void prepareListUser() {
        recreateModel();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/user/List.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void prepareListAdmin() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/List.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void prepareListAdminFeedback() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/FeedbackBoard.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String navList(){
        return "List?faces-redirect=true";
    }
    public String navReport() {
        return "Report?faces-redirect=true";
    }

    public String prepareView() {
        current = (FacultySubjectView) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new FacultySubjectView();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultySubjectViewCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public int getIdFacSub() {
        current = (FacultySubjectView) getItems().getRowData();
        return current.getIdFacultySubject();
    }

    public String prepareEdit() {
        current = (FacultySubjectView) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultySubjectViewUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (FacultySubjectView) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultySubjectViewDeleted"));
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
        modelByUserName = null;
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

    public SelectItem[] getItemsAvailableSelectOneByUserName() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        return JsfUtil.getSelectItems(ejbFacade.getFSViewById(userName), true);
    }

    @FacesConverter(forClass = FacultySubjectView.class)
    public static class FacultySubjectViewControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FacultySubjectViewController controller = (FacultySubjectViewController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "facultySubjectViewController");
            return controller.ejbFacade.find(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuffer sb = new StringBuffer();
            sb.append(value);
            return sb.toString();
        }

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FacultySubjectView) {
                FacultySubjectView o = (FacultySubjectView) object;
                return getStringKey(o.getIdFacultySubject());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FacultySubjectView.class.getName());
            }
        }
    }
}
