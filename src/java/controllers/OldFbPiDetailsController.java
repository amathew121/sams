package controllers;

import entities.OldFbPiDetails;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.OldFbPiDetailsFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
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

@Named("oldFbPiDetailsController")
@SessionScoped
public class OldFbPiDetailsController implements Serializable {

    private OldFbPiDetails current;
    private DataModel items = null;
    
    @EJB
    private beans.OldFbPiDetailsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private DataModel detailsByFS;
    private String subID;
    private short batch;
    private short ftype;
    private String div;

    public OldFbPiDetailsController() {
    }

    public OldFbPiDetails getSelected() {
        if (current == null) {
            current = new OldFbPiDetails();
            current.setOldFbPiDetailsPK(new entities.OldFbPiDetailsPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private OldFbPiDetailsFacade getFacade() {
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
        current = (OldFbPiDetails) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new OldFbPiDetails();
        current.setOldFbPiDetailsPK(new entities.OldFbPiDetailsPK());
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFbPiDetailsCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (OldFbPiDetails) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFbPiDetailsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (OldFbPiDetails) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFbPiDetailsDeleted"));
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
    
    public String getFeedbackDetails(String subID, String div,short ftype,short batch)
    {
        this.subID = subID;
        this.div = div;
        this.ftype = ftype;
        this.batch = batch;
        recreateModel();
        return "FeedbackDetails?faces-redirect=true";
                
    }
    
@PostConstruct
public void init(){
    FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
    detailsByFS = new ListDataModel(getFacade().getByFS(userName, div, ftype, batch, subID));
}
        
    public DataModel getDetailsByFS() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        detailsByFS = new ListDataModel(getFacade().getByFS(userName, div, ftype, batch, subID));
        return detailsByFS;
    }

    public void setDetailsByFS(DataModel detailsByFS) {
        this.detailsByFS = detailsByFS;
    }
    
    public int getTotalT1 (DataModel d){
        List<OldFbPiDetails> l =  (List<OldFbPiDetails>)d.getWrappedData();
        int total = 0;
        
        for(OldFbPiDetails i : l) {
            total+=i.getTa1();
        }
        
        return total;
    }
    public int getTotalT2(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) d.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa2();
        }

        return total;
    }

    public int getTotalT3(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) d.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa3();
        }

        return total;
    }

    public int getTotalT4(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) d.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa4();
        }

        return total;
    }

    public int getTotalT5(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) detailsByFS.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa5();
        }

        return total;
    }

    private void recreateModel() {
        detailsByFS = null;
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

    public OldFbPiDetails getOldFbPiDetails(entities.OldFbPiDetailsPK id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = OldFbPiDetails.class)
    public static class OldFbPiDetailsControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OldFbPiDetailsController controller = (OldFbPiDetailsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "oldFbPiDetailsController");
            return controller.getOldFbPiDetails(getKey(value));
        }

        entities.OldFbPiDetailsPK getKey(String value) {
            entities.OldFbPiDetailsPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.OldFbPiDetailsPK();
            key.setFacId(values[0]);
            key.setSubId(values[1]);
            key.setCourseId(values[2]);
            key.setDivision(values[3]);
            key.setBatch(Short.parseShort(values[4]));
            key.setQno(Short.parseShort(values[5]));
            return key;
        }

        String getStringKey(entities.OldFbPiDetailsPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getFacId());
            sb.append(SEPARATOR);
            sb.append(value.getSubId());
            sb.append(SEPARATOR);
            sb.append(value.getCourseId());
            sb.append(SEPARATOR);
            sb.append(value.getDivision());
            sb.append(SEPARATOR);
            sb.append(value.getBatch());
            sb.append(SEPARATOR);
            sb.append(value.getQno());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof OldFbPiDetails) {
                OldFbPiDetails o = (OldFbPiDetails) object;
                return getStringKey(o.getOldFbPiDetailsPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + OldFbPiDetails.class.getName());
            }
        }
    }
}
