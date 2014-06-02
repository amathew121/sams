package controllers;

import entities.Feedback2013;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.Feedback2013Facade;
import entities.FacultySubject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 *JSF Backing bean for feedback2013 Entity
 * @author Administrator
 */
@Named("feedback2013Controller")
@SessionScoped
public class Feedback2013Controller implements Serializable {

    private Feedback2013 current;
    private DataModel items = null;
    @EJB
    private beans.Feedback2013Facade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int ra[] = new int[6];
    private double performanceIndex;
    private List<Feedback2013> feedback2013List;
    private FacultySubject idFacultySubject;

    /**
     *creates the backing bean
     */
    public Feedback2013Controller() {
    }

    /**
     * Gets the selected feedback2013 entity
     * @return
     */
    public Feedback2013 getSelected() {
        if (current == null) {
            current = new Feedback2013();
            selectedItemIndex = -1;
        }
        return current;
    }

    private Feedback2013Facade getFacade() {
        return ejbFacade;
    }

    /**
     * Gets Pagination Helper to fetch range of items according to page.
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
     *Sets the selected feedback2013 Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (Feedback2013) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new feedback2013 Entity
     * @return
     */
    public String prepareCreate() {
        current = new Feedback2013();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *
     * @return
     */
    public double getPerformanceIndex() {
        return performanceIndex;
    }

    /**
     *
     * @param performanceIndex
     */
    public void setPerformanceIndex(double performanceIndex) {
        this.performanceIndex = performanceIndex;
    }

    /**
     *
     * @return
     */
    public int[] getRa() {
        return ra;
    }

    /**
     *
     * @param ra
     */
    public void setRa(int[] ra) {
        this.ra = ra;
    }

    /**
     *
     * @return
     */
    public List<Feedback2013> getFeedback2013List() {
        return feedback2013List;
    }

    /**
     *
     * @param feedback2013List
     */
    public void setFeedback2013List(List<Feedback2013> feedback2013List) {
        this.feedback2013List = feedback2013List;
    }

    /**
     *
     * @return
     */
    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    /**
     *
     * @param idFacultySubject
     * @return
     */
    public String getByUserName(FacultySubject idFacultySubject) {
        
        this.idFacultySubject = idFacultySubject;
        
        List<Feedback2013> temp = getFacade().getByUserName(idFacultySubject);
        Map<Integer, Short> ra0 = new HashMap<Integer, Short>();
        Map<Integer, Short> ra1 = new HashMap<Integer, Short>();
        Map<Integer, Short> ra2 = new HashMap<Integer, Short>();
        Map<Integer, Short> ra3 = new HashMap<Integer, Short>();
        Map<Integer, Short> ra4 = new HashMap<Integer, Short>();
        Map<Integer, Short> ra5 = new HashMap<Integer, Short>();
        feedback2013List = getFacade().getRating(idFacultySubject);
        for (Feedback2013 item : feedback2013List) {
            ra0.put(item.getQid().getQid(), (short) 0);
            ra1.put(item.getQid().getQid(), (short) 0);
            ra2.put(item.getQid().getQid(), (short) 0);
            ra3.put(item.getQid().getQid(), (short) 0);
            ra4.put(item.getQid().getQid(), (short) 0);
            ra5.put(item.getQid().getQid(), (short) 0);

        }
        ra[0]=ra[1]=ra[2]=ra[3]=ra[4]=ra[5] =0;

        for (Feedback2013 item : temp) {
            //System.out.println(item.getQid().getQtext() + " " + item.getIdAnswer());

            if (item.getIdAnswer() == 5) {
                ra5.put(item.getQid().getQid(), (short) (ra5.get(item.getQid().getQid()) + 1));
            } else if (item.getIdAnswer() == 4) {
                ra4.put(item.getQid().getQid(), (short) (ra4.get(item.getQid().getQid()) + 1));
            } else if (item.getIdAnswer() == 3) {
                ra3.put(item.getQid().getQid(), (short) (ra3.get(item.getQid().getQid()) + 1));
            } else if (item.getIdAnswer() == 1) {
                ra1.put(item.getQid().getQid(), (short) (ra1.get(item.getQid().getQid()) + 1));
            } else if (item.getIdAnswer() == 2) {
                ra2.put(item.getQid().getQid(), (short) (ra2.get(item.getQid().getQid()) + 1));
            } else {
                ra0.put(item.getQid().getQid(), (short) (ra0.get(item.getQid().getQid()) + 1));

            }



        }

        for (Feedback2013 item : feedback2013List) {
            int x = ra0.get(item.getQid().getQid());
            item.setRa0(x);
            ra[0]= ra[0]+x;
            
            x = ra1.get(item.getQid().getQid());
            item.setRa1(x);
            ra[1]= ra[1]+x;
            
            x = ra2.get(item.getQid().getQid());
            item.setRa2(x);
            ra[2]= ra[2]+x;

            x = ra3.get(item.getQid().getQid());
            item.setRa3(x);
            ra[3]= ra[3]+x;

            x = ra4.get(item.getQid().getQid());
            item.setRa4(x);
            ra[4]= ra[4]+x;

            x = ra5.get(item.getQid().getQid());
            item.setRa5(x);
            ra[5]= ra[5]+x;

        }
        
        performanceIndex = (5.0*ra[5]+3.0*ra[4]+1.0*ra[3]-3.0*ra[2]-5.0*ra[1])/(ra[0]+ra[1]+ra[2]+ra[3]+ra[4]+ra[5]);

        //return "Feedback2013Details?faces-redirect=true";
        return null;
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013Created"));
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
        current = (Feedback2013) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected feedback2013 entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013Updated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected feedback2013 entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (Feedback2013) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("Feedback2013Deleted"));
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
     *Gets All feedback2013 entities as few items one at a time
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
     * Navigation case to next page with next items
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
     *Gets list of all feedback2013 entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all feedback2013 entities to be able to select one from it
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
    public Feedback2013 getFeedback2013(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for feedback2013 Entity
     */
    @FacesConverter(forClass = Feedback2013.class)
    public static class Feedback2013ControllerConverter implements Converter {

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
            Feedback2013Controller controller = (Feedback2013Controller) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "feedback2013Controller");
            return controller.getFeedback2013(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
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
            if (object instanceof Feedback2013) {
                Feedback2013 o = (Feedback2013) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Feedback2013.class.getName());
            }
        }
    }
}
class FeedbackDetail {

    private int qid;
    private String qtext;
    private Long r5;
    private int r4;
    private int r3;
    private int r2;
    private int r1;

    public FeedbackDetail() {
    }

    public FeedbackDetail(int qid, String qtext, Long r5) {
        this.qid = qid;
        this.qtext = qtext;
        this.r5 = r5;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getQtext() {
        return qtext;
    }

    public void setQtext(String qtext) {
        this.qtext = qtext;
    }

    public Long getR5() {
        return r5;
    }

    public void setR5(Long r5) {
        this.r5 = r5;
    }

    public int getR4() {
        return r4;
    }

    public void setR4(int r4) {
        this.r4 = r4;
    }

    public int getR3() {
        return r3;
    }

    public void setR3(int r3) {
        this.r3 = r3;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }
}