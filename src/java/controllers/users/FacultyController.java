package controllers.users;

import entities.users.Faculty;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.users.FacultyFacade;

import java.io.Serializable;
import java.util.List;
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
import org.apache.commons.codec.digest.DigestUtils;

@Named("facultyController")
@SessionScoped
public class FacultyController implements Serializable {

    private Faculty current;
    private DataModel items = null;
    private String oldPassword;
    private String newPassword;
    @EJB
    private beans.users.FacultyFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     *creates the backing bean
     */
    public FacultyController() {
    }

    /**
     * Gets the selected faculty entity
     * @return
     */
    public Faculty getSelected() {
        if (current == null) {
            current = new Faculty();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FacultyFacade getFacade() {
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
     *
     * @return
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     *
     * @param oldPassword
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     *
     * @return
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     *
     * @param newPassword
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Resets the list of items and navigates to List
     * @return
     */
    public String prepareList() {
        recreateModel();
        return "List";
    }

    /**
     *Sets the selected Faculty Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (Faculty) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     * Navigation case to Create page after initializing a new Faculty Entity
     * @return
     */
    public String prepareCreate() {
        current = new Faculty();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultyCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    public void updateGplus(Faculty f,String email, String token, String code) {
        current = f;
        current.setFacultyEmail(email);
        current.setOauthCode(code);
        current.setOauthToken(token);
        update();
    }
    
    public String prepareEdit() {
        current = (Faculty) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *
     * @return
     */
    public String prepareUserDetails() {
        Faculty f = new Faculty();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        f.setIdFaculty(userName);
        current = getFacade().find(userName);
        return "UserDetails?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String prepareUserDetailsEdit() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();

        current = getFacade().find(userName);
        return "UserDetailsEdit?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String prepareUserPasswordChange() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();

        current = getFacade().find(userName);
        return "UserPasswordChange?faces-redirect=true";
    }

    /**
     *Updates the selected faculty entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultyUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String update1() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultyUpdated"));
            return prepareUserDetails();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String update2() {
        try {
            final String hash = DigestUtils.sha256Hex(oldPassword);
            if(hash.equals(current.getFacultyPassword())){
                current.setFacultyPassword(newPassword);
                String oauthCode = DigestUtils.sha256Hex(current.getFacultyPassword()+DigestUtils.sha256Hex(current.getOauthToken()));
                current.setOauthCode(oauthCode);
            }
            else{
                JsfUtil.addErrorMessage("Old Password is wrong");
                return null;
            }
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultyUpdated"));
            return prepareUserDetails();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Destroys the selected faculty entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (Faculty) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultyDeleted"));
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

    public Faculty getFacultyByToken(String token) {
        return getFacade().findFacultyByToken(token);
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
     *Gets list of all faculty entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all faculty entities to be able to select one from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    /**
     *
     * @return
     */
    public List<Faculty> getAllFaculty(){
        return getFacade().findAll();
    }

    /**
     *
     * @param id
     * @return
     */
    public Faculty getFaculty(java.lang.String id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for faculty Entity
     */
    @FacesConverter(forClass = Faculty.class)
    public static class FacultyControllerConverter implements Converter {

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
            FacultyController controller = (FacultyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "facultyController");
            return controller.getFaculty(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
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
            if (object instanceof Faculty) {
                Faculty o = (Faculty) object;
                return getStringKey(o.getIdFaculty());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Faculty.class.getName());
            }
        }
    }
}
