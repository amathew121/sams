package controllers.subject.faculty;

import controllers.users.CoordinatorController;
import controllers.users.ReviewerController;
import entities.subject.faculty.FacultySubject;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.FacultySubjectFacade;
import entities.users.Coordinator;
import entities.users.Faculty;
import entities.users.Reviewer;
import entities.subject.Subject;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

@Named("facultySubjectController")
@SessionScoped
public class FacultySubjectController implements Serializable {

    private FacultySubject current;
    private Integer ac_yr;
    private DataModel items = null;
    @EJB
    private beans.subject.faculty.FacultySubjectFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private FacultySubject idFacultySubject;
    private Coordinator c;

    /**
     *creates the backing bean
     */
    public FacultySubjectController() {
    }

    /**
     *Gets the selected facultySubject entity
     * @return
     */
    public FacultySubject getSelected() {
        if (current == null) {
            current = new FacultySubject();
            selectedItemIndex = -1;
        }
        return current;
    }

    private FacultySubjectFacade getFacade() {
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
                    return new ListDataModel(getFacade().findAll());
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
     *Sets the selected Attendance Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (FacultySubject) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new facultysubject Entity
     * @return
     */
    public String prepareCreate() {
        prepareList();
        current = new FacultySubject();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *reates a new record in the database for the selected entity
     * @return
     */
    public String create() {
        current.setIdFacultySubject(0);
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultySubjectCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    
    /**
     *
     * @return
     */
    public List<FacultySubject> getBatchesByReviewer() {
        List<FacultySubject> list = new ArrayList<FacultySubject>();
        FacesContext context = FacesContext.getCurrentInstance();
        ReviewerController reviewerController = (ReviewerController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "reviewerController");
        List<Reviewer> reviewSubjects = reviewerController.getLoggedUser();
        for(Reviewer item : reviewSubjects){
            List<FacultySubject> list2 = getFacade().getFSBySubject(item.getIdSubject());
            list.addAll(list2);
        };
        return list;
        
    }
    
    /**
     *
     */
    public void prepareBatchesBySemDiv() {
        FacesContext context = FacesContext.getCurrentInstance();
        CoordinatorController coordinatorController = (CoordinatorController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "coordinatorController");
        c = coordinatorController.getLoggedUser();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/FacultyBatches.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     *
     * @return
     */
    public List<FacultySubject> getBatchesBySemDivOLD(){

        try {
        return getFacade().getFSBySemDivSub(c.getCoordinatorPK().getSemester(), c.getCoordinatorPK().getDivision(), c.getProgramCourse());
        }
        catch(Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
    
   /* public List<Date> getFSAcademicYear(){

        try {
        return getFacade().getFSByAcademicYr();
        }
        catch(Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List<FacultySubject> getFSByAcDate(Date ac_date){
        try {
        return getFacade().getFSListByAcademicYr(ac_date);
        }
        catch(Exception e ) {
            e.printStackTrace();
            return null;
        }
    }*/
    
    public List<FacultySubject> getBatchesBySemDiv(){

        ac_yr = 2014;
        try {
        return getFacade().getFSBySemDivSubYr(c.getCoordinatorPK().getSemester(), c.getCoordinatorPK().getDivision(), c.getProgramCourse(),ac_yr);
        }
        catch(Exception e ) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @return
     */
    public String navSame() {
        return "FacultyBatches?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public Coordinator getC() {
        return c;
    }

    /**
     *
     * @param c
     */
    public void setC(Coordinator c) {
        this.c = c;
    }
    
    public List<FacultySubject> getItemsByYear(Faculty idFaculty, int year, boolean even){
        return getFacade().getFSByYear(idFaculty, year, even);
    }
    
    public int getAcYear(int year, boolean even){
        return getFacade().getAcYear( year, even);
    }
        
    public String prepareEdit() {
        current = (FacultySubject) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected Attendance entity in the database
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultySubjectUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected Attendance entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (FacultySubject) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultySubjectDeleted"));
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
     *Gets All facultysubject entities as few items one at a time
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
     *Gets list of all facultysubject entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all facultysubject entities to be able to select one from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    /**
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOneByUserName() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        Faculty fac = getFacade().getFacById(userName);
        return JsfUtil.getSelectItems(ejbFacade.getFSByIdFac(fac), true);
    }

    /**
     *
     * @param fac
     * @return
     */
    public List<FacultySubject> getFacultySubjectByFaculty(Faculty fac) {
        return getFacade().getFSByIdFac(fac);
    }
    
    /**
     *
     * @param idFacSub
     * @return
     */
    public FacultySubject getIdFacSub(int idFacSub) {

        return getFacade().getFSById(idFacSub);
    }

    /**
     *
     * @param division
     * @param semester
     * @param batch
     * @param idSubject
     * @return
     */
    public FacultySubject getIdFacSub(String division, short semester, short batch, Subject idSubject) {

        return getFacade().getFSBySemDivBatchSub(division, semester, batch, idSubject);
    }
    
    public FacultySubject getIdFacSubYr(String division, short semester, short batch, Subject idSubject) {

        return getFacade().getFSBySemDivBatchSubYr(division, semester, batch, idSubject);
    }
    public FacultySubject getIdFacSubYrFINAL(String division, short semester, Subject idSubject) {

        return getFacade().getFSBySemDivBatchSubYrFINAL(division, semester, idSubject);
    }

   /* public FacultySubject getIdFacSubYrDate(String division, short semester, short batch, Subject idSubject, Date acDate) {

        return getFacade().getFSBySemDivBatchSubYrDate(division, semester, batch, idSubject, acDate);
    }*/
    public Integer getAc_yr() {
        return ac_yr;
    }

    public void setAc_yr(Integer ac_yr) {
        this.ac_yr = ac_yr;
    }

    /**
     *Converter Class for facultySubject Entity
     */
    @FacesConverter(forClass = FacultySubject.class)
    public static class FacultySubjectControllerConverter implements Converter {

        /**
         *
         * @param facesContext
         * @param component
         * @param value
         * @return
         */
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FacultySubjectController controller = (FacultySubjectController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "facultySubjectController");
            return controller.ejbFacade.find(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuffer sb = new StringBuffer();
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
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FacultySubject) {
                FacultySubject o = (FacultySubject) object;
                return getStringKey(o.getIdFacultySubject());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FacultySubject.class.getName());
            }
        }
    }
}
