package controllers.subject.faculty;

import controllers.subject.faculty.lecture.*;
import entities.subject.faculty.ViewpiltAvg;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.ViewPiLTAvgFacade;
import controllers.users.FacultyController;
import entities.subject.Program;
import entities.subject.faculty.ViewpiNegative;
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

@Named("viewPiLTAvgController")
@SessionScoped
public class ViewPiLTAvgController implements Serializable {

    private ViewpiltAvg current;
    private DataModel items = null;
    private DataModel modelByUserName = null;
    @EJB
    private beans.subject.faculty.ViewPiLTAvgFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Department deptSelected;
    private Program program;
    private int semester;
    private short batch;

    /**
     * creates the backing bean
     */
    public ViewPiLTAvgController() {
        if(current != null)
            current.getFsv().setIdFacultySubject(current.getIdFacultySubject());
    }

    /**
     * Gets the selected facultySubjectView entity
     *
     * @return
     */
    public ViewpiltAvg getSelected() {
        if (current == null) {
            current = new ViewpiltAvg();
            selectedItemIndex = -1;
        }
        return current;
    }

    private ViewPiLTAvgFacade getFacade() {
        return ejbFacade;
    }

    /**
     *
     * @return
     */
    public Department getDeptSelected() {
        return deptSelected;
    }

    /**
     *
     * @param deptSelected
     */
    public void setDeptSelected(Department deptSelected) {
        this.deptSelected = deptSelected;
    }

    /**
     *
     * @return
     */
    public DataModel getModelByUserName() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        modelByUserName = new ListDataModel(getFacade().getViewPiltAvgByFaculty(userName));
        return modelByUserName;
    }

    /**
     *
     *
     * /**
     *
     * @return
     */
    public String oddSem() {
        return "ListOdd?faces-redirect=true";
    }

    public List<ViewpiltAvg> getListByDept() {
        Department dept = deptSelected;
        Program prog = program;

        if (dept != null && prog != null) {
            return getFacade().getViewByDept(dept.getIdDepartment(), prog.getIdProgram(), semester, batch);
        } else {
            return null;
        }
    }

    public List<ViewpiNegative> getListByDeptNegative() {
       
        
        return getFacade().getViewByDeptNegative();
        
    }
    
    public List<ViewpiltAvg> getListByFaculty(Faculty fac) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacultyController facultyController = (FacultyController) context.getApplication().getELResolver().getValue(context.getELContext(), null, "facultyController");
        Faculty idFaculty = facultyController.getFaculty(context.getExternalContext().getRemoteUser());
       
        return getFacade().getViewPiltAvgByFaculty(fac.getIdFaculty());

    }
/**
 * Gets Pagination Helper to fetch range of items according to page Gets 10
 * items at a time.
 *
 * @return
 */
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

    /**
     * Resets the list of items and navigates to List
     *
     * @return
     */
    public String prepareList() {
        recreateModel();
        return "List";
    }

    /**
     *
     */
    public void prepareListUser() {
        recreateModel();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/user/List.xhtml");
        

} catch (IOException ex) {
            Logger.getLogger(ViewLectureNotTakenController.class  

.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void prepareListAdmin() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/List.xhtml");
        

} catch (IOException ex) {
            Logger.getLogger(ViewLectureNotTakenController.class  

.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void prepareListAdminFeedback() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/FeedbackBoard.xhtml");
        

} catch (IOException ex) {
            Logger.getLogger(ViewLectureNotTakenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void prepareViewLNT() {
     try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/ViewLNT.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ViewLectureNotTakenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void prepareViewLNTUser() {
     try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/user/ViewLNT.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ViewLectureNotTakenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *
     * @return
     */
    public String navList() {
        return "View_Pi_LT_Avg?faces-redirect=true";
    }
    public String navListNegative() {
        return "View_Pi_Negative?faces-redirect=true";
    }

    /**
     *
     * @return
     */
    public String navReport() {
        return "Report?faces-redirect=true";
    }

    /**
     * Sets the selected facultysubjectview Entity to view more
     * details.Navigation case to View
     *
     * @return
     */
    public String prepareView() {
        current = (ViewpiltAvg) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     * Navigation case to Create page after initializing a new
     * facultysubjectview Entity
     *
     * @return
     */
    public String prepareCreate() {
        current = new ViewpiltAvg();
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     * Creates a new recored in the database for the selected entity
     *
     * @return
     */
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ViewPiLTAvgCreated"));
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
    public int getIdFacSub() {
        current = (ViewpiltAvg) getItems().getRowData();
        return current.getIdFacultySubject();
    }

    /**
     * Sets the selected item for editing. Navigation case to Edit page.
     *
     * @return
     */
    public String prepareEdit() {
        current = (ViewpiltAvg) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     * Updates the selected facultysubjectview entity in the database
     *
     * @return
     */
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

    /**
     * Destroys the selected viewpiLTAvg entity, and deletes it from the
     * database
     *
     * @return
     */
    public String destroy() {
        current = (ViewpiltAvg) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("ViewPiLTAvgDeleted"));
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
     * Gets All facultysubjectview entities as few items one at a time
     *
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
        modelByUserName = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    /**
     * Navigation case to next page with next items
     *
     * @return
     */
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    /**
     * Navigation case to previous page with previous items
     *
     * @return
     */
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    /**
     * Gets list of all facultysubjectview entities to be able to select many
     * from it
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     * Gets list of all facultysubjectview entities to be able to select one
     * from it
     *
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
        return JsfUtil.getSelectItems(ejbFacade.getViewPiltAvgByFaculty(userName), true);
    }

    

    public ViewpiltAvg getCurrent() {
        return current;
    }

    public void setCurrent(ViewpiltAvg current) {
        this.current = current;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    

}

    public short getBatch() {
        return batch;
    }

    public void setBatch(short batch) {
        this.batch = batch;
    }

   

    /**
     * Converter Class for facultySubjectView Entity
     */
    @FacesConverter(forClass = ViewpiltAvg.class)
public static class ViewLectureNotTakenControllerConverter implements Converter {

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
        ViewPiLTAvgController controller = (ViewPiLTAvgController) facesContext.getApplication().getELResolver().
                getValue(facesContext.getELContext(), null, "viewPiLTAvgController");
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
        if (object instanceof ViewpiltAvg) {
            ViewpiltAvg o = (ViewpiltAvg) object;
            return getStringKey(o.getIdFacultySubject());
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + ViewpiltAvg.class.getName());
        }
    }
}
}
