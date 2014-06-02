package controllers;

import entities.OldFbPiDetails;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.OldFbPiDetailsFacade;
import entities.OldFbPi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/**
 *JSF Backing bean for oldfbpidetails Entity
 * @author Administrator
 */
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
    private BigDecimal pi;
    private String userName;

    /**
     * creates the backing bean
     */
    public OldFbPiDetailsController() {
    }

    /**
     *Gets the selected oldfbpidetails entity
     * @return
     */
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
     *Sets the selected oldfbpidetails Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (OldFbPiDetails) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new oldfbpidetails Entity
     * @return
     */
    public String prepareCreate() {
        current = new OldFbPiDetails();
        current.setOldFbPiDetailsPK(new entities.OldFbPiDetailsPK());
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("OldFbPiDetailsCreated"));
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
        current = (OldFbPiDetails) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected oldfbpidetails entity in the database
     * @return
     */
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

    /**
     *Destroys the selected oldfbpidetails entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (OldFbPiDetails) getItems().getRowData();
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

    /**
     *Gets All oldfbpidetails entities as few items one at a time
     * @return
     */
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    /**
     *
     * @return
     */
    public BigDecimal getPi() {
        return pi;
    }

    /**
     *
     * @param pi
     */
    public void setPi(BigDecimal pi) {
        this.pi = pi;
    }

    /**
     *
     * @param fb
     * @return
     */
    public String getFeedbackDetails(OldFbPi fb) {
        this.userName = fb.getOldFbPiPK().getFacId();
        this.pi = fb.getPi();
        this.subID = fb.getOldFbPiPK().getSubId();
        this.div = fb.getOldFbPiPK().getDivision();
        this.ftype = fb.getFtype();
        this.batch = fb.getOldFbPiPK().getBatch();
        recreateModel();
        return "FeedbackDetails?faces-redirect=true";
    }

    /**
     *
     * @param fb
     * @return
     */
    public String getFeedbackDetailsAdmin(OldFbPi fb) {
        this.userName = fb.getOldFbPiPK().getFacId();
        this.pi = fb.getPi();
        this.subID = fb.getOldFbPiPK().getSubId();
        this.div = fb.getOldFbPiPK().getDivision();
        this.ftype = fb.getFtype();
        this.batch = fb.getOldFbPiPK().getBatch();
        recreateModel();
        return null;
    }

    /**
     *
     * @return
     */
    public String getSubID() {
        return subID;
    }

    /**
     *
     * @return
     */
    public short getBatch() {
        return batch;
    }

    /**
     *
     * @return
     */
    public String getDivision() {
        return div;
    }

    /**
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     *
     */
    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        detailsByFS = new ListDataModel(getFacade().getByFS(userName, div, ftype, batch, subID));
    }

    /**
     *
     * @return
     */
    public DataModel getDetailsByFS() {
        detailsByFS = new ListDataModel(getFacade().getByFS(userName, div, ftype, batch, subID));
        return detailsByFS;
    }
    
    /**
     *
     * @param fb
     * @return
     */
    public DataModel getDetailsByFSAdmin(OldFbPi fb) {
        this.userName = fb.getOldFbPiPK().getFacId();
        this.pi = fb.getPi();
        this.subID = fb.getOldFbPiPK().getSubId();
        this.div = fb.getOldFbPiPK().getDivision();
        this.ftype = fb.getFtype();
        this.batch = fb.getOldFbPiPK().getBatch();
        recreateModel();
        detailsByFS = new ListDataModel(getFacade().getByFS(userName, div, ftype, batch, subID));
        return detailsByFS;
    }

    /**
     *
     * @param detailsByFS
     */
    public void setDetailsByFS(DataModel detailsByFS) {
        this.detailsByFS = detailsByFS;
    }

    /**
     *
     * @param d
     * @return
     */
    public int getTotalT1(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) d.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa1();
        }

        return total;
    }

    /**
     *
     * @param d
     * @return
     */
    public int getTotalT2(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) d.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa2();
        }

        return total;
    }

    /**
     *
     * @param d
     * @return
     */
    public int getTotalT3(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) d.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa3();
        }

        return total;
    }

    /**
     *
     * @param d
     * @return
     */
    public int getTotalT4(DataModel d) {
        List<OldFbPiDetails> l = (List<OldFbPiDetails>) d.getWrappedData();
        int total = 0;

        for (OldFbPiDetails i : l) {
            total += i.getTa4();
        }

        return total;
    }

    /**
     *
     * @param d
     * @return
     */
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
     *Gets list of all oldfbpidetails entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all oldfbpidetails entities to be able to select one from it
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
    public OldFbPiDetails getOldFbPiDetails(entities.OldFbPiDetailsPK id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for oldfbpidetails Entity
     */
    @FacesConverter(forClass = OldFbPiDetails.class)
    public static class OldFbPiDetailsControllerConverter implements Converter {

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
            if (object instanceof OldFbPiDetails) {
                OldFbPiDetails o = (OldFbPiDetails) object;
                return getStringKey(o.getOldFbPiDetailsPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + OldFbPiDetails.class.getName());
            }
        }
    }
}
