package controllers.feedback;

import controllers.users.FacultyController;
import entities.feedback.Feedback2013;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.feedback.Feedback2013Facade;
import controllers.subject.faculty.FacultySubjectController;
import entities.users.Faculty;
import entities.subject.faculty.FacultySubject;
import entities.feedback.FeedbackType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;


@Named("feedback2013Controller")
@SessionScoped
public class Feedback2013Controller implements Serializable {

    private Feedback2013 current;
    private DataModel items = null;
    @EJB
    private beans.feedback.Feedback2013Facade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private int ra[] = new int[6];
    private double performanceIndex;
    private List<Feedback2013> feedback2013List;
    private FacultySubject idFacultySubject;
    private DefaultMenuModel model;
    private Faculty selectedFaculty;

    public Faculty getSelectedFaculty() {
        return selectedFaculty;
    }

    public void setSelectedFaculty(Faculty selectedFaculty) {
        this.selectedFaculty = selectedFaculty;
    }

    public DefaultMenuModel getModel() {
        return model;
    }

    public void setModel(DefaultMenuModel model) {
        this.model = model;
    }

    @PostConstruct
    public void init() {
        model = new DefaultMenuModel();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        String userName = facesContext.getExternalContext().getRemoteUser();

        FeedbackTypeController ftController = findBean("feedbackTypeController");
        FacultySubjectController fsController = findBean("facultySubjectController");
        FacultyController fController = findBean("facultyController");
        Faculty loggedUser = fController.getFaculty(userName);
        for (FeedbackType item : ftController.getItems()) {
            DefaultSubMenu tempSubmenu = new DefaultSubMenu(item.getDescr());
            for (FacultySubject fs : fsController.getItemsByYear(loggedUser, item.getYr(), item.getEven())) {
                DefaultMenuItem menuItem = new DefaultMenuItem(fs.getIdSubject().getSubjectCode() + "/" + fs.getDivision() + "/" + fs.getBatchDetail());
                menuItem.setCommand("#{feedback2013Controller.getByUserName(facultySubjectController.getIdFacSub(" + fs.getIdFacultySubject() + "),feedbackTypeController.getFeedbackType(" + item.getIdFeedbackType() + "))}");
                menuItem.setUpdate(":layoutPanel:fbDetails :layoutPanel:fbComments");
                tempSubmenu.addElement(menuItem);
            }
            model.addElement(tempSubmenu);
        }
    }
    
    public void recreateMenuModel() {

        model = new DefaultMenuModel();
        FeedbackTypeController ftController = findBean("feedbackTypeController");
        FacultySubjectController fsController = findBean("facultySubjectController");
        Feedback2013CommentsController fcController = findBean("feedback2013CommentsController");
        if (feedback2013List != null) {
            feedback2013List.clear();
        }
        if (fcController.getFeedback2013CommentsList() != null) {
            fcController.getFeedback2013CommentsList().clear();
        }
        performanceIndex = 0;
        for (FeedbackType item : ftController.getItems()) {
            DefaultSubMenu tempSubmenu = new DefaultSubMenu(item.getDescr());
            for (FacultySubject fs : fsController.getItemsByYear(selectedFaculty, item.getYr(), item.getEven())) {
                DefaultMenuItem menuItem = new DefaultMenuItem(fs.getIdSubject().getSubjectCode() + "/" + fs.getDivision() + "/" + fs.getBatchDetail());
                menuItem.setCommand("#{feedback2013Controller.getByUserName(facultySubjectController.getIdFacSub(" + fs.getIdFacultySubject() + "),feedbackTypeController.getFeedbackType(" + item.getIdFeedbackType() + "))}");
                menuItem.setUpdate(":layoutPanel:fbDetails :layoutPanel:fbComments");
                tempSubmenu.addElement(menuItem);
            }
            model.addElement(tempSubmenu);
        }
    }

    public Feedback2013Controller() {
    }

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
        current = (Feedback2013) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Feedback2013();
        selectedItemIndex = -1;
        return "Create";
    }

    public double getPerformanceIndex() {
        return performanceIndex;
    }

    public void setPerformanceIndex(double performanceIndex) {
        this.performanceIndex = performanceIndex;
    }

    public int[] getRa() {
        return ra;
    }

    public void setRa(int[] ra) {
        this.ra = ra;
    }

    public List<Feedback2013> getFeedback2013List() {
        return feedback2013List;
    }

    public void setFeedback2013List(List<Feedback2013> feedback2013List) {
        this.feedback2013List = feedback2013List;
    }

    public FacultySubject getIdFacultySubject() {
        return idFacultySubject;
    }

    public void getByUserName(FacultySubject idFacultySubject, FeedbackType fType) {

        this.idFacultySubject = idFacultySubject;

        List<Feedback2013> temp = getFacade().getByUserName(idFacultySubject, fType);
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
        ra[0] = ra[1] = ra[2] = ra[3] = ra[4] = ra[5] = 0;

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
            ra[0] = ra[0] + x;

            x = ra1.get(item.getQid().getQid());
            item.setRa1(x);
            ra[1] = ra[1] + x;

            x = ra2.get(item.getQid().getQid());
            item.setRa2(x);
            ra[2] = ra[2] + x;

            x = ra3.get(item.getQid().getQid());
            item.setRa3(x);
            ra[3] = ra[3] + x;

            x = ra4.get(item.getQid().getQid());
            item.setRa4(x);
            ra[4] = ra[4] + x;

            x = ra5.get(item.getQid().getQid());
            item.setRa5(x);
            ra[5] = ra[5] + x;

        }

        performanceIndex = (5.0 * ra[5] + 3.0 * ra[4] + 1.0 * ra[3] - 3.0 * ra[2] - 5.0 * ra[1]) / (ra[0] + ra[1] + ra[2] + ra[3] + ra[4] + ra[5]);

        Feedback2013CommentsController fcController = findBean("feedback2013CommentsController");
        fcController.getByUserName(idFacultySubject, fType);

    }

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

    public String prepareEdit() {
        current = (Feedback2013) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

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

    public String destroy() {
        current = (Feedback2013) getItems().getRowData();
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

    public Feedback2013 getFeedback2013(java.lang.Long id) {
        return ejbFacade.find(id);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findBean(String beanName) {
        FacesContext context = FacesContext.getCurrentInstance();
        return (T) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
    }

    @FacesConverter(forClass = Feedback2013.class)
    public static class Feedback2013ControllerConverter implements Converter {

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