package controllers.feedback;

import entities.feedback.OldFbPi;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.feedback.OldFbPiFacade;

import java.io.Serializable;
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

@Named("oldFbPiController")
@SessionScoped
public class OldFbPiController implements Serializable {

    private OldFbPi current;
    private DataModel items = null;
    @EJB
    private beans.feedback.OldFbPiFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public OldFbPiController() {
    }

    public OldFbPi getSelected() {
        if (current == null) {
            current = new OldFbPi();
            current.setOldFbPiPK(new entities.feedback.OldFbPiPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private OldFbPiFacade getFacade() {
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
                    return new ListDataModel(getFacade().findAll());
                }
            };
        }
        return pagination;
    }
    
    public List<OldFbPi> getItemsByUserName(String userName){
        return getFacade().findByUserName(userName);
    }
    
    public DataModel getItemsByUserNameGroup() {
        return new ListDataModel(getFacade().findByUserNameGroup());
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (OldFbPi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new OldFbPi();
        current.setOldFbPiPK(new entities.feedback.OldFbPiPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFbPiCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (OldFbPi) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFbPiUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (OldFbPi) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFbPiDeleted"));
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

    public OldFbPi getOldFbPi(entities.feedback.OldFbPiPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = OldFbPi.class)
    public static class OldFbPiControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OldFbPiController controller = (OldFbPiController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oldFbPiController");
            return controller.getOldFbPi(getKey(value));
        }

        entities.feedback.OldFbPiPK getKey(String value) {
            entities.feedback.OldFbPiPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.feedback.OldFbPiPK();
            key.setFacId(values[0]);
            key.setSubId(values[1]);
            key.setDivision(values[2]);
            key.setBatch(Short.parseShort(values[3]));
            return key;
        }

        String getStringKey(entities.feedback.OldFbPiPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getFacId());
            sb.append(SEPARATOR);
            sb.append(value.getSubId());
            sb.append(SEPARATOR);
            sb.append(value.getDivision());
            sb.append(SEPARATOR);
            sb.append(value.getBatch());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof OldFbPi) {
                OldFbPi o = (OldFbPi) object;
                return getStringKey(o.getOldFbPiPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + OldFbPi.class.getName());
            }
        }
    }
}
