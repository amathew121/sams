package controllers.subject.faculty;

import controllers.subject.faculty.lecture.CurrentStudentController;
import entities.subject.faculty.StudentTest;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.StudentTestFacade;
import entities.subject.faculty.lecture.CurrentStudent;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.StudentTestPK;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.primefaces.event.CellEditEvent;

/**
 * JSF Backing bean for studenttest Entity
 *
 * @author Administrator
 */
@Named("studentTestController")
@SessionScoped
public class StudentTestController implements Serializable {

    private StudentTest current;
    private DataModel items = null;
    @EJB
    private beans.subject.faculty.StudentTestFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private FacultySubject facSub;
    private int idFacSub;

    /**
     * creating a backing bean
     */
    public StudentTestController() {
    }

    /**
     *
     * @return
     */
    public FacultySubject getFacSub() {
        FacesContext context = FacesContext.getCurrentInstance();
        FacultySubjectController fsc = (FacultySubjectController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "facultySubjectController");
        facSub = fsc.getIdFacSub(idFacSub);
        return facSub;
    }

    /**
     *
     * @param facSub
     */
    public void setFacSub(FacultySubject facSub) {
        this.facSub = facSub;
    }

    /**
     * Gets the selected attendance entity
     *
     * @return
     */
    public StudentTest getSelected() {
        if (current == null) {
            current = new StudentTest();
            current.setStudentTestPK(new entities.subject.faculty.StudentTestPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private StudentTestFacade getFacade() {
        return ejbFacade;
    }

    /**
     *
     * @param i
     * @return
     */
    public String prepareViewWithId(int i) {
        idFacSub = i;
        recreateModel();
        return "Test?faces-redirect=true";
    }

    /**
     * Gets Pagination Helper to fetch range of items according to page. Gets 10
     * items at a time.
     *
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
     * Resets the list of items and navigates to List
     *
     * @return
     */
    public String prepareList() {
        recreateModel();
        return "List";
    }

    /**
     * Sets the selected studenttest Entity to view more details.Navigation case
     * to Vie
     *
     * @return
     */
    public String prepareView() {
        current = (StudentTest) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     * Navigation case to Create page after initializing a new studenttest
     * Entity
     *
     * @return
     */
    public String prepareCreate() {
        current = new StudentTest();
        current.setStudentTestPK(new entities.subject.faculty.StudentTestPK());
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
            current.getStudentTestPK().setIdSubject(current.getSubject().getIdSubject());
            current.getStudentTestPK().setIdCurrentStudent(current.getCurrentStudent().getIdCurrentStudent());
            getFacade().create(current);
            JsfUtil.addSuccessMessage("Marks added");
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Sets the selected item for editing. Navigation case to Edit page.
     *
     * @return
     */
    public String prepareEdit() {
        current = (StudentTest) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     * Updates the selected studenttest entity in the database
     *
     * @return
     */
    public String update() {
        try {
            current.getStudentTestPK().setIdSubject(current.getSubject().getIdSubject());
            current.getStudentTestPK().setIdCurrentStudent(current.getCurrentStudent().getIdCurrentStudent());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage("Marks updated");
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Destroys the selected studenttest entity, and deletes it from the
     * database
     *
     * @return
     */
    public String destroy() {
        current = (StudentTest) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    /**
     *
     * @param e
     */
    public BigDecimal testMarksCalc(CurrentStudent item) {
        double total = 0, minQ1 = 0;
        if (item.getQ1a() != null && item.getQ1b() != null && item.getQ1c() != null && item.getQ1d() != null && item.getQ1e() != null && item.getQ1f() != null && item.getQ2a() != null && item.getQ2b() != null && item.getQ3a() != null && item.getQ3b() != null) {

            minQ1 = Math.min(Math.min(Math.min(item.getQ1a().doubleValue(), item.getQ1b().doubleValue()), Math.min(item.getQ1c().doubleValue(), item.getQ1d().doubleValue())), Math.min(item.getQ1e().doubleValue(), item.getQ1f().doubleValue()));

            total += item.getQ2a().doubleValue() > item.getQ2b().doubleValue() ? item.getQ2a().doubleValue() : item.getQ2b().doubleValue();
            total += item.getQ3a().doubleValue() > item.getQ3b().doubleValue() ? item.getQ3a().doubleValue() : item.getQ3b().doubleValue();

            total += item.getQ1a().doubleValue() + item.getQ1b().doubleValue() + item.getQ1c().doubleValue() + item.getQ1d().doubleValue() + item.getQ1e().doubleValue() + item.getQ1f().doubleValue();
            total -= minQ1;

        }

        BigDecimal Total = new BigDecimal(total);
        item.setMarks(Total);
        return Total;
    }

    public void testMarksChangedQ1A(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ1a(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ1a(t.getQ1a());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ1a(t.getQ1a());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ1B(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ1b(new BigDecimal(e.getNewValue().toString()));
          t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ1b(t.getQ1b());
             temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ1b(t.getQ1b());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    /**
     *
     * @param e
     */
    public void testMarksChangedQ1C(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ1c(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ1c(t.getQ1c());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ1c(t.getQ1c());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ1D(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ1d(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ1d(t.getQ1d());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ1d(t.getQ1d());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ1E(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ1e(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ1e(t.getQ1e());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ1e(t.getQ1e());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ1F(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ1f(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ1f(t.getQ1f());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ1f(t.getQ1f());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ2A(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ2a(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ2a(t.getQ2a());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ2a(t.getQ2a());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ2B(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ2b(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ2b(t.getQ2b());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ2b(t.getQ2b());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ3A(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ3a(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ3a(t.getQ3a());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ3a(t.getQ3a());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void testMarksChangedQ3B(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setQ3b(new BigDecimal(e.getNewValue().toString()));
        t.setMarks(testMarksCalc(t));
        if (temp != null) {
            temp.setQ3b(t.getQ3b());
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setQ3b(t.getQ3b());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    public void testMarksChanged(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 1);

        t.setMarks(new BigDecimal(e.getNewValue().toString()));
        if (temp != null) {
            temp.setMarks(t.getMarks());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setMarks(t.getMarks());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 1);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @param e
     */
    public void test2MarksChanged(ValueChangeEvent e) {
        //assign new value to localeCode
        UIData data = (UIData) e.getComponent().findComponent("test");
        CurrentStudent t = (CurrentStudent) data.getRowData();
        StudentTest temp = getFacade().getStudentTestMarks(t, facSub.getIdSubject(), (short) 2);

        t.setMarks2(new BigDecimal(e.getNewValue().toString()));
        if (temp != null) {
            temp.setMarks(t.getMarks2());
            current = temp;
            update();

        } else {
            current = new StudentTest();
            current.setCurrentStudent(t);
            current.setSubject(facSub.getIdSubject());
            current.setMarks(t.getMarks2());
            StudentTestPK spk = new StudentTestPK();
            spk.setTest((short) 2);
            current.setStudentTestPK(spk);
            create();

        }
    }

    /**
     *
     * @return @throws Exception
     */
    public List<CurrentStudent> getTestDetails() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController csc = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
        List<CurrentStudent> l = csc.getAttendanceByDiv(getFacSub());
        Map<Integer, Short> hm = new HashMap<Integer, Short>();
        List<StudentTest> t = new ArrayList<StudentTest>();
        for (CurrentStudent item : l) {
            StudentTest temp = getFacade().getStudentTestMarks(item, facSub.getIdSubject(), (short) 1);

            if (temp != null) {
                t.add(temp);
                item.setMarks(temp.getMarks());
                item.setQ1a(temp.getQ1a());
                item.setQ1b(temp.getQ1b());
                item.setQ1c(temp.getQ1c());
                item.setQ1d(temp.getQ1d());
                item.setQ1e(temp.getQ1e());
                item.setQ1f(temp.getQ1f());
                item.setQ2a(temp.getQ2a());
                item.setQ2b(temp.getQ2b());
                item.setQ3a(temp.getQ3a());
                item.setQ3b(temp.getQ3b());


            }
            StudentTest temp2 = getFacade().getStudentTestMarks(item, facSub.getIdSubject(), (short) 2);

            if (temp2 != null) {
                t.add(temp2);
                item.setMarks2(temp2.getMarks());

            }
        }

        return l;
    }

    /**
     *
     * @param facSub
     * @return
     * @throws Exception
     */
    public List<CurrentStudent> getTestDetails(FacultySubject facSub) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController csc = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
        List<CurrentStudent> l = csc.getAttendanceByDiv(facSub);
        Map<Integer, Short> hm = new HashMap<Integer, Short>();
        List<StudentTest> t = new ArrayList<StudentTest>();
        for (CurrentStudent item : l) {
            StudentTest temp = getFacade().getStudentTestMarks(item, facSub.getIdSubject(), (short) 1);

            if (temp != null) {
                t.add(temp);
                item.setMarks(temp.getMarks());

            }
            StudentTest temp2 = getFacade().getStudentTestMarks(item, facSub.getIdSubject(), (short) 2);

            if (temp2 != null) {
                t.add(temp2);
                item.setMarks2(temp2.getMarks());

            }
        }

        return l;
    }

    /**
     *
     * @param event
     */
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);

            FacesContext context = FacesContext.getCurrentInstance();
            CurrentStudentController csc = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
            List<CurrentStudent> l = csc.getAttendanceByDiv(getFacSub());
            List<StudentTest> t = new ArrayList<StudentTest>();
            for (CurrentStudent item : l) {

                StudentTest temp = getFacade().getStudentTestMarks(item, facSub.getIdSubject(), (short) 1);
                if (temp != null) {

                    temp.setMarks((BigDecimal) newValue);
                    getFacade().edit(temp);
                } else {
                    getFacade().create(temp);
                }


            }

        }
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("StudentTestDeleted"));
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
     * Gets All studenttest entities as few items one at a time
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
     * Gets list of all studenttest entities to be able to select many from it
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     * Gets list of all studenttest entities to be able to select one from it
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public StudentTest getStudentTest(entities.subject.faculty.StudentTestPK id) {
        return ejbFacade.find(id);
    }

    /**
     * Converter Class for studenttest Entity
     */
    @FacesConverter(forClass = StudentTest.class)
    public static class StudentTestControllerConverter implements Converter {

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
            StudentTestController controller = (StudentTestController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "studentTestController");
            return controller.getStudentTest(getKey(value));
        }

        entities.subject.faculty.StudentTestPK getKey(String value) {
            entities.subject.faculty.StudentTestPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.subject.faculty.StudentTestPK();
            key.setIdCurrentStudent(Integer.parseInt(values[0]));
            key.setIdSubject(Integer.parseInt(values[1]));
            key.setTest(Short.parseShort(values[2]));
            return key;
        }

        String getStringKey(entities.subject.faculty.StudentTestPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdCurrentStudent());
            sb.append(SEPARATOR);
            sb.append(value.getIdSubject());
            sb.append(SEPARATOR);
            sb.append(value.getTest());
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
            if (object instanceof StudentTest) {
                StudentTest o = (StudentTest) object;
                return getStringKey(o.getStudentTestPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + StudentTest.class.getName());
            }
        }
    }
}
