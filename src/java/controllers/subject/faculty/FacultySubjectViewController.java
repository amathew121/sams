package controllers.subject.faculty;

import entities.subject.faculty.FacultySubjectView;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.FacultySubjectViewFacade;
import entities.subject.Program;
import entities.users.Department;
import entities.users.Faculty;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

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
    private int academic_year;
    private Program program;
    private int semester;

    /**
     * creates the backing bean
     */
    public FacultySubjectViewController() {
    }

    /**
     * Gets the selected facultySubjectView entity
     *
     * @return
     */
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
        modelByUserName = new ListDataModel(getFacade().getFSViewById(userName));
        return modelByUserName;
    }

    /**
     *
     * @return
     */
    public DataModel getModelByUserNameCurrent() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();
        modelByUserName = new ListDataModel(getFacade().getFSViewByIdCurrent(userName));
        return modelByUserName;
    }

    /**
     *
     * @param userName
     * @return
     */
    public DataModel getModelByUserName(String userName) {
        modelByUserName = new ListDataModel(getFacade().getFSViewById(userName));
        return modelByUserName;
    }
    //TODO: CHECK USAGES

    /**
     *
     * @return
     */
    public DataModel getModelByUserNameGroup() {
        return new ListDataModel(getFacade().getFSViewByIdGroup());

    }

    /**
     *
     * @return
     */
    public String oddSem() {
        return "ListOdd?faces-redirect=true";
    }

    /**
     *
     * @param fac
     * @return
     */
    public List<FacultySubjectView> getListByDept(Faculty fac) {
        Department dept = deptSelected;
        Program prog = program;

        if (dept != null && prog != null) {
            return getFacade().getFSViewByDept(dept.getIdDepartment(), prog.getIdProgram(), academic_year, semester);
        } else {
            return null;
        }
    }

    /**
     *
     * @param sub
     * @return
     */
    public List<FacultySubjectView> getListByDeptSub(String sub) {
        return getFacade().getFSViewByDeptSub(sub, "CS");
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
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void prepareListAdmin() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/List.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void prepareListAdminFeedback() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/FeedbackBoard.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return
     */
    public String navList() {
        return "List?faces-redirect=true";
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
        current = (FacultySubjectView) getItems().getRowData();
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
        current = new FacultySubjectView();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("FacultySubjectViewCreated"));
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
        current = (FacultySubjectView) getItems().getRowData();
        return current.getIdFacultySubject();
    }

    /**
     * Sets the selected item for editing. Navigation case to Edit page.
     *
     * @return
     */
    public String prepareEdit() {
        current = (FacultySubjectView) getItems().getRowData();
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
     * Destroys the selected facultysubjectview entity, and deletes it from the
     * database
     *
     * @return
     */
    public String destroy() {
        current = (FacultySubjectView) getItems().getRowData();
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
        return JsfUtil.getSelectItems(ejbFacade.getFSViewById(userName), true);
    }

    public int getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(int academic_year) {
        this.academic_year = academic_year;
    }

    public FacultySubjectView getCurrent() {
        return current;
    }

    public void setCurrent(FacultySubjectView current) {
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

    /**
     * Converter Class for facultySubjectView Entity
     */
    @FacesConverter(forClass = FacultySubjectView.class)
    public static class FacultySubjectViewControllerConverter implements Converter {

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
            if (object instanceof FacultySubjectView) {
                FacultySubjectView o = (FacultySubjectView) object;
                return getStringKey(o.getIdFacultySubject());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + FacultySubjectView.class.getName());
            }
        }
    }
    // Constants ----------------------------------------------------------------------------------
    private static final int DEFAULT_BUFFER_SIZE = 1024000; // 1000KB.

    // Actions ------------------------------------------------------------------------------------
    public void downloadP2PFeedback() throws IOException {

        // Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        String filename = facesContext.getExternalContext().getRemoteUser() + ".pdf";
        //System.out.println(facesContext.getExternalContext().getRemoteUser() + " , " + filename);
        File file = new File("/usr/piit/p2pfeedback/", filename);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        if (!file.exists()) {
            p2pFileNotExist();
        } else {
            try {
                // Open file.
                input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);


                // Init servlet response.
                response.reset();
                response.setHeader("Content-Type", "application/pdf");
                response.setHeader("Content-Length", String.valueOf(file.length()));
                response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
                output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

                // Write file contents to response.
                byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                // Finalize task.
                output.flush();
            } finally {
                // Gently close streams.
                close(output);
                close(input);
            }
        }
        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();

    }

    // Helpers (can be refactored to public utility class) ----------------------------------------
    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException e) {
                // Do your thing with the exception. Print it, log it or mail it. It may be useful to 
                // know that this will generally only be thrown when the client aborted the download.
                e.printStackTrace();

            }
        }
    }

    public void p2pFileNotExist() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/user/p2pFileNotFound.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void downloadFECal() throws IOException {

        // Prepare.
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        String filename = "FE_Academic_Calendar_2015.pdf";
        File file = new File("/usr/piit/academicCal/", filename);
        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            // Open file.
            input = new BufferedInputStream(new FileInputStream(file), DEFAULT_BUFFER_SIZE);


            // Init servlet response.
            response.reset();
            response.setHeader("Content-Type", "application/pdf");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Finalize task.
            output.flush();
        } finally {
            // Gently close streams.
            close(output);
            close(input);
        }

        // Inform JSF that it doesn't need to handle response.
        // This is very important, otherwise you will get the following exception in the logs:
        // java.lang.IllegalStateException: Cannot forward after response has been committed.
        facesContext.responseComplete();

    }
}
