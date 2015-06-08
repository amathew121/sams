package controllers.subject.faculty.lecture;

import entities.subject.faculty.lecture.Lecture;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.lecture.LectureFacade;
import controllers.subject.faculty.FacultySubjectController;
import controllers.extra.RandomPermutation;
import controllers.subject.faculty.FacultySubjectViewController;
import entities.subject.Course;
import entities.subject.Program;
import entities.subject.faculty.lecture.Attendance;
import entities.subject.faculty.lecture.CurrentStudent;
import entities.subject.faculty.FacultySubject;
import entities.subject.Subject;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.PhaseId;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SlideEndEvent;

@Named("lectureController")
@SessionScoped
public class LectureController implements Serializable {

    private Lecture current;
    private DataModel lectureByFS;
    private CurrentStudent[] selectList;
    private String topicsDelivered;
    private DataModel items = null;
    private List<Lecture> lectureList;
    private Date startDate;
    private Date endDate;
    private int startIndex;
    private String lectureTags;
    private Map<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
    private boolean[] selectAll;
    private String message;
    private Program program;
    private Course course;

    /**
     *
     * @return
     */
    public boolean[] getSelectAll() {
        return selectAll;
    }

    /**
     *
     * @param selectAll
     */
    public void setSelectAll(boolean[] selectAll) {
        this.selectAll = selectAll;
    }

    /**
     *
     * @return
     */
    public String getLectureTags() {
        return lectureTags;
    }

    /**
     *
     * @param lectureTags
     */
    public void setLectureTags(String lectureTags) {
        this.lectureTags = lectureTags;
    }

    /**
     *
     * @return
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     *
     * @param startIndex
     */
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    /**
     *
     * @return
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     *
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     *
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     *
     * @return
     */
    public CurrentStudent[] getSelectList() {
        return selectList;
    }

    /**
     *
     * @param selectList
     */
    public void setSelectList(CurrentStudent[] selectList) {
        this.selectList = selectList;
    }
    @EJB
    private beans.subject.faculty.lecture.LectureFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int idFacSub;
    private FacultySubject facSub;

    /**
     *
     * @return
     */
    public FacultySubject getFacSub() {
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
     * creating a backing bean
     */
    public LectureController() {
    }

    /**
     *
     * @param idLecture
     * @return
     */
    public Lecture getLectureByLecID(Integer idLecture) {
        return getFacade().getLectureByIdLecture(idLecture);
    }

    /**
     *
     * @return
     */
    public DataModel getLectureByFS() {
        lectureByFS = new ListDataModel(getFacade().getLectureByIdFaculty(facSub));
        return lectureByFS;
    }

    /**
     *
     * @param lectureByFS
     */
    public void setLectureByFS(DataModel lectureByFS) {
        this.lectureByFS = lectureByFS;
    }

    /**
     *
     * @return
     */
    public String getTopicsDelivered() {
        return topicsDelivered;
    }

    /**
     *
     * @param topicsDelivered
     */
    public void setTopicsDelivered(String topicsDelivered) {
        this.topicsDelivered = topicsDelivered;
    }

    /**
     *
     * @return
     */
    public List<Lecture> getLectureList() {
        return lectureList;
    }

    /**
     *
     * @param lectureList
     */
    public void setLectureList(List<Lecture> lectureList) {
        this.lectureList = lectureList;
    }

    /**
     * Gets the selected lecture entity
     *
     * @return
     */
    public Lecture getSelected() {
        if (current == null) {
            current = new Lecture();
            selectedItemIndex = -1;
        }
        return current;
    }

    private LectureFacade getFacade() {
        return ejbFacade;
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
     * Sets the selected lecture Entity to view more details.Navigation case to
     * View
     *
     * @return
     */
    public String prepareView() {
        current = (Lecture) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *
     * @return
     */
    public String prepareCreateWithId() {
        //prepareCreate();
        return "Create?faces-redirect=true";
    }

    /**
     *
     * @param startDate
     * @param facSub
     * @return
     */
    public String prepareCreateWithDate(Date startDate, FacultySubject facSub) {
        this.facSub = facSub;
        prepareCreate();
        current.setLectureDate(startDate);
        return "Create?faces-redirect=true";
    }

    /**
     *
     * @return @throws Exception
     */
    public String prepareMultipleRange() throws Exception {
        return "MultipleDateRange?faces-redirect=true";
    }

    public void prepareBlockedLectures() {


        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/piit/faces/admin/BlockedLectures.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(FacultySubjectViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String navList() {
        if (program != null && course != null) {
            lectureList = getLectureBlocked();
        }
        return "BlockedLectures?faces-redirect=true";
    }

    /**
     *
     * @return @throws Exception
     */
    public String prepareCreateMultipleWithId() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController currentStudentController = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");

        lectureList = getLectureByFSList(facSub);

        List<Lecture> checkedItems = new ArrayList<Lecture>();

        for (Lecture item : lectureList) {
            if (currentStudentController.getChecked().get(item.getIdLecture())) {
                checkedItems.add(item);
            }
        }
        if (!checkedItems.isEmpty()) {
            lectureList = checkedItems;
        } else if (startDate != null) {
            lectureList = getLectureByFSList(facSub, startDate);
        } else if (startDate != null && endDate != null) {
            lectureList = getLectureByFSList(facSub, startDate, endDate);
        } else {
            if (startIndex + 9 < lectureList.size()) {
                lectureList = lectureList.subList(startIndex - 1, startIndex + 9);
            } else {
                lectureList = lectureList.subList(startIndex - 1, lectureList.size());
            }

        }

        for (Lecture lec : lectureList) {
            currentStudentController.setLec(lec);
            lec.changeMap(lec.getChecked(), Boolean.FALSE);
            List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
            for (int i = 0; i < attendance.size(); i++) {
                lec.getChecked().put(attendance.get(i).getIdCurrentStudent().getIdCurrentStudent(), Boolean.TRUE);
            }
        }
        prepareCreate();
        return "CreateMultiple?faces-redirect=true";
    }

    /**
     *
     * @param i
     * @return
     */
    public String prepareViewWithId(int i) {
        idFacSub = i;
        getFacSubject(idFacSub);
        recreateModel();
        return "View?faces-redirect=true";
    }

    /**
     *
     * @param i
     * @return
     */
    public FacultySubject getFacSubject(int i) {
        facSub = getFacade().getFSById(i);
        return facSub;
    }

    /**
     * Navigation case to Create page after initializing a new lecture Entity
     *
     * @return
     */
    public String prepareCreate() {
        current = new Lecture();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *
     * @param facSub
     * @return
     */
    public List<Lecture> getLectureByFSList(FacultySubject facSub) {
        return getFacade().getLectureByIdFaculty(facSub);
    }

    public List<Lecture> getLectureByFSListTheory(FacultySubject facSub) {
        return getFacade().getLectureByIdFacultyTheory(facSub);
    }

    public List<Lecture> getLectureByFSListPracs(FacultySubject facSub, Short batch) {
        return getFacade().getLectureByIdFacultyPracs(facSub, batch);
    }

    public List<Lecture> getLectureBlocked() {
        return getFacade().getLectureBlocked(program, course);
    }

    /**
     *
     * @param facSub
     * @param startDate
     * @param endDate
     * @return
     */
    public List<Lecture> getLectureByFSList(FacultySubject facSub, Date startDate, Date endDate) {
        return getFacade().getLectureByIdFacultyDateRange(facSub, startDate, endDate);
    }

    /**
     *
     * @param facSub
     * @param startDate
     * @return
     */
    public List<Lecture> getLectureByFSList(FacultySubject facSub, Date startDate) {
        return getFacade().getLectureByIdFacultyDateRange(facSub, startDate);
    }

    /**
     *
     * @return
     */
    public int getLectureByFSListTotal() {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController csc = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");
        FacultySubjectController fsc = (FacultySubjectController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "facultySubjectController");
        int total = 0;
        for (Subject s : csc.getSubject()) {
            FacultySubject facsub = fsc.getIdFacSubYear(csc.getDivision(), s.getSemester(), (short) 0, s);
            total += getFacade().getLectureByIdFaculty(facsub).size();

        }

        return total;
    }

    /**
     * Adds new lecture tags. Accepts comma separated values.
     */
    private void setTags() {
        FacesContext context = FacesContext.getCurrentInstance();
        LectureTagsController ltc = (LectureTagsController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "lectureTagsController");
        StringTokenizer st = new StringTokenizer(lectureTags, ",");
        while (st.hasMoreTokens()) {
            ltc.prepareCreate();
            ltc.getSelected().setLecture(current);
            String temp = st.nextToken().trim();
            System.out.println(temp);
            ltc.getSelected().getLectureTagsPK().setTag(temp);
            ltc.create();
        }
        lectureTags = null;

    }

    /**
     * Creates a new Lecture, and creates lecture tags and attendance also along
     * with it.
     *
     * @return View.xhtml
     */
    public String createA() {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController currentStudentController = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");

        current.setIdFacultySubject(facSub);
        Lecture temp = current;
        try {
            getFacade().create(temp);
            context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Lecture Created", "New lecture was created successfully."));
            setTags();

            // Date validation new Date(temp.getLectureDate().getDate()-2, temp.getLectureDate().getMonth(),temp.getLectureDate().getYear())
            Date chkDate = new Date();
            //System.out.println("Chk date = "+chkDate+"\nLecture date = "+temp.getLectureDate());
            int cd, cm, cy, ld, lm, ly;
            cd = chkDate.getDate();
            cm = chkDate.getMonth();
            cy = chkDate.getYear();
            ld = temp.getLectureDate().getDate();
            lm = temp.getLectureDate().getMonth();
            ly = temp.getLectureDate().getYear();
         //   if ((ly == cy) && (lm == cm) && (ld == cd)) {
                currentStudentController.setLec(temp);
                recreateModel();
                //  return "CreateAttendance?faces-redirect=true";
                currentStudentController.createAttendance();
         //   } else {
           //     FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "BLOCKED", "Attendance NOT recorded.. Request permission from your HOD");
            //    RequestContext.getCurrentInstance().showMessageInDialog(message);


           //     current.setBlocked(true);
                update();
                //System.out.println("Lecture Blocked");

           // }
        } catch (Exception e) {
            e.printStackTrace();


        } finally {

            prepareCreate();
            return null;
        }
    }

    public String unblockLecture() {
        lectureList = getLectureBlocked();

        List<Lecture> checkedItems = new ArrayList<Lecture>();

        for (Lecture item : lectureList) {
            if (checked.get(item.getIdLecture())) {
                checkedItems.add(item);
            }
        }

        for (Lecture lec : checkedItems) {
            current = lec;
            current.setBlocked(Boolean.FALSE);
            update();
            //checked.changeMap(lec.getChecked(), Boolean.FALSE);

        }

        //prepareBlockedLectures();
        lectureList = getLectureBlocked();
        return "BlockedLectures?faces-redirect=true";
    }

    /**
     *
     * @param event
     */
    public void selectAllComponents(ValueChangeEvent event) {
        if (event.getPhaseId() != PhaseId.INVOKE_APPLICATION) {
            event.setPhaseId(PhaseId.INVOKE_APPLICATION);
            event.queue();
        } else {

            UIData data = (UIData) event.getComponent().findComponent("listComponents");
            CurrentStudent cs = (CurrentStudent) data.getRowData();
            System.out.println("Old:" + event.getOldValue().toString());
            System.out.println("New:" + event.getNewValue().toString());
            short newValue = (Short) event.getNewValue();
            if (newValue > 0) {
                cs.setLectureAttended(newValue);
                int i;
                for (i = 0; i < lectureList.size() && i < newValue; i++) {

                    lectureList.get(i).getChecked().put(cs.getIdCurrentStudent(), Boolean.TRUE);
                    System.out.println(lectureList.get(i).getChecked());

                }
                for (int j = i; j < lectureList.size(); j++) {
                    lectureList.get(j).getChecked().put(cs.getIdCurrentStudent(), Boolean.FALSE);
                    System.out.println(lectureList.get(j).getChecked());
                }

            }
        }
    }

    /**
     *
     * @param event
     */
    public void onSlideEnd(SlideEndEvent event) {

        UIData data = (UIData) event.getComponent().findComponent("listComponents");
        CurrentStudent cs = (CurrentStudent) data.getRowData();
        short newValue = (short) event.getValue();
        RandomPermutation r = new RandomPermutation(lectureList.size());
        if (newValue >= 0) {
            cs.setLectureAttended(newValue);
            int i;
            for (i = 0; i < lectureList.size() && i < newValue; i++) {

                lectureList.get(r.next() - 1).getChecked().put(cs.getIdCurrentStudent(), Boolean.TRUE);
                System.out.println(lectureList.get(i).getChecked());

            }
            for (int j = i; j < lectureList.size(); j++) {
                lectureList.get(r.next() - 1).getChecked().put(cs.getIdCurrentStudent(), Boolean.FALSE);
                System.out.println(lectureList.get(j).getChecked());
            }
        }
        FacesMessage msg = new FacesMessage("Slide Ended", "Value: " + event.getValue());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     *
     * @return @throws Exception
     */
    public String createAll() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController currentStudentController = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");

        for (Lecture lec : lectureList) {
            List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
            for (int i = 0; i < attendance.size(); i++) {
                currentStudentController.getAttendanceController().createEntry(attendance.get(i));
                currentStudentController.getAttendanceController().destroyA();
            }
            lec.setIdFacultySubject(facSub);
            Lecture temp = lec;
            currentStudentController.setChecked(temp.getChecked());
            try {
                currentStudentController.setLec(temp);
                recreateModel();
                //  return "CreateAttendance?faces-redirect=true";
                currentStudentController.createAttendance();
            } catch (Exception e) {
                e.printStackTrace();
                JsfUtil.addErrorMessage("No Students Selected! Lecture Not created");

            } finally {
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AttendanceCreated"));
                prepareCreate();
            }
        }
        return "View?faces-redirect=true";

    }

    /**
     * Sets the selected item for editing. Navigation case to Edit page.
     *
     * @return
     */
    public String prepareEdit() {
        current = (Lecture) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *
     * @param f
     * @return
     */
    public String prepareListTP(FacultySubject f) {
        facSub = f;
        recreateModel();
        return "FSLec?faces-redirect=true";
    }

    /**
     *
     * @param f
     * @return
     */
    public String prepareListRLec(FacultySubject f) {
        facSub = f;
        recreateModel();
        return "ReviewLec?faces-redirect=true";
    }

    /**
     * Updates the selected lecture entity in the database
     *
     * @return
     */
    public String update() {
        try {
            getFacade().edit(current);
            //JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     * Destroys the selected lecture entity, and deletes it from the database
     *
     * @return
     */
    public String destroy() {
        current = (Lecture) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    /**
     *
     * @param lec
     * @return
     * @throws Exception
     */
    public String destroyLectureRestrict(Lecture lec) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController currentStudentController = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");

        List<Attendance> attendance;
        try {
            attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
            for (int i = 0; i < attendance.size(); i++) {
                currentStudentController.getAttendanceController().createEntry(attendance.get(i));
                currentStudentController.getAttendanceController().destroyA();
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        current = lec;
        performDestroy();
        current = null;
        recreatePagination();
        recreateModel();
        return "View?faces-redirect=true";
    }

    /**
     *
     * @param lec
     * @return
     * @throws Exceptionller)
     * context.getELContext().getELResolver().getValue(context.getELContext(),
     */
    public String prepareUpdateLectureRestrict(Lecture lec) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController currentStudentController = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");

        if (lec != null && !lec.isBlocked()) {
            current = lec;
            currentStudentController.setLec(lec);
            currentStudentController.changeMap(currentStudentController.getChecked(), Boolean.FALSE);
            try {
                List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(lec);
                for (int i = 0; i < attendance.size(); i++) {
                    currentStudentController.getChecked().put(attendance.get(i).getIdCurrentStudent().getIdCurrentStudent(), Boolean.TRUE);
                }
            } catch (NullPointerException e) {
                System.out.println(e);
            }


            return "UpdateLecture?faces-redirect=true";
        } else {
            return null;
        }
    }

    /**
     *
     * @return @throws Exception
     */
    public String updateLectureRestrict() throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        CurrentStudentController currentStudentController = (CurrentStudentController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "currentStudentController");

        try {
            List<Attendance> attendance = currentStudentController.getAttendanceController().getAttendanceByFSLec(currentStudentController.getLec());
            for (int i = 0; i < attendance.size(); i++) {
                currentStudentController.getAttendanceController().createEntry(attendance.get(i));
                currentStudentController.getAttendanceController().destroyA();
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
        setTags();
        currentStudentController.createAttendance();
        update();

        return "View?faces-redirect=true";
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureDeleted"));
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
     * Gets All lecture entities as few items one at a time
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
        lectureByFS = null;
        lectureList = null;
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
     * Gets list of all lecture entities to be able to select many from it
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     * Gets list of all lecture entities to be able to select one from it
     *
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Map<Integer, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Integer, Boolean> checked) {
        this.checked = checked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIdFacSub() {
        return idFacSub;
    }

    public void setIdFacSub(int idFacSub) {
        this.idFacSub = idFacSub;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Converter Class for lecture Entity
     */
    @FacesConverter(forClass = Lecture.class)
    public static class LectureControllerConverter implements Converter {

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
            LectureController controller = (LectureController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lectureController");
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
            if (object instanceof Lecture) {
                Lecture o = (Lecture) object;
                return getStringKey(o.getIdLecture());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Lecture.class.getName());
            }
        }
    }
}
