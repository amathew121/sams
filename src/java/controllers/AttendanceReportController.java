package controllers;

import entities.AttendanceReport;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.AttendanceReportFacade;
import entities.Course;
import entities.CurrentStudent;
import entities.ProgramCourse;
import java.io.IOException;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Named("attendanceReportController")
@SessionScoped
public class AttendanceReportController implements Serializable {

    private AttendanceReport current;
    private DataModel items = null;
    @EJB
    private beans.AttendanceReportFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idFacSub;

    public AttendanceReportController() {
    }

    public AttendanceReport getSelected() {
        if (current == null) {
            current = new AttendanceReport();
            selectedItemIndex = -1;
        }
        return current;
    }

    private AttendanceReportFacade getFacade() {
        return ejbFacade;
    }

    public int getIdFacSub() {
        return idFacSub;
    }

    public void setIdFacSub(int idFacSub) {
        this.idFacSub = idFacSub;
    }

    public void prepareListAttendanceReport() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/ReportAllNew.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        current = (AttendanceReport) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new AttendanceReport();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareViewWithId(int i) {
        idFacSub = i;
        recreateModel();
        return "Student?faces-redirect=true";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceReportCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (AttendanceReport) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public List<Object[]> getStudentAttendanceByIdSubjectSemDiv(ProgramCourse programCourse, short semester, String division, int idSubject) {
        return getFacade().getStudentAttendanceBySubDivSem(programCourse, division, semester, idSubject);
    }
    public List<Object[]> getStudentAttendanceByIdSubjectSemDiv(ProgramCourse programCourse, short semester, String division, int idSubject, short batch) {
        return getFacade().getStudentAttendanceBySubDivSem(programCourse, division, semester, idSubject, batch);
    }

//    public List<Integer> getStudentAttendanceCountByIdSubjectSemDiv(Course course, short semester, String division, int idSubject) {
//        return getFacade().getStudentAttendanceCountByFS(course, division, semester, idSubject);
//    }

    public List<CurrentStudent> getStudentAttendanceByFS(int idFacSub) {
        List<Object[]> l = getFacade().getStudentAttendanceByFS(idFacSub);
        List<AttendanceReport> ls = new ArrayList();
        for (Object[] c : l) {
            ((AttendanceReport) c[0]).setCount(((Number) c[1]).intValue());
            ls.add((AttendanceReport) c[0]);

        }

        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController csc = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
        FacultySubjectController fsc = (FacultySubjectController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "facultySubjectController");
        StudentTestController stc = (StudentTestController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "studentTestController");

        Map<Integer, Integer> hm = new HashMap<Integer, Integer>();
        Map<Integer, BigDecimal> hn = new HashMap<Integer, BigDecimal>();
        Map<Integer, BigDecimal> hn2 = new HashMap<Integer, BigDecimal>();

        List<CurrentStudent> lcs = csc.getAttendanceByDiv(fsc.getIdFacSub(idFacSub));
        for (CurrentStudent item : lcs) {
            hm.put(item.getIdCurrentStudent(), 0);
            hn.put(item.getIdCurrentStudent(), new BigDecimal(0));
            hn2.put(item.getIdCurrentStudent(), new BigDecimal(0));

        }

        for (AttendanceReport item : ls) {
            hm.put(item.getIdCurrentStudent(), item.getCount());
        }
        try {
            List<CurrentStudent> st = stc.getTestDetails(fsc.getIdFacSub(idFacSub));
            for (CurrentStudent item : st) {
                hn.put(item.getIdCurrentStudent(), item.getMarks());
                hn2.put(item.getIdCurrentStudent(), item.getMarks2());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        for (CurrentStudent item : lcs) {
            item.setCount(hm.get(item.getIdCurrentStudent()));
            item.setMarks(hn.get(item.getIdCurrentStudent()));
            item.setMarks2(hn2.get(item.getIdCurrentStudent()));

        }

        return lcs;
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceReportUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (AttendanceReport) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceReportDeleted"));
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

    public AttendanceReport getAttendanceReport(long id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = AttendanceReport.class)
    public static class AttendanceReportControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AttendanceReportController controller = (AttendanceReportController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "attendanceReportController");
            return controller.getAttendanceReport(getKey(value));
        }

        long getKey(String value) {
            long key;
            key = Long.parseLong(value);
            return key;
        }

        String getStringKey(long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof AttendanceReport) {
                AttendanceReport o = (AttendanceReport) object;
                return getStringKey(o.getIdAttendance());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + AttendanceReport.class.getName());
            }
        }
    }
}
