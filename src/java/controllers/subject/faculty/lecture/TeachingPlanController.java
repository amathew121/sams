package controllers.subject.faculty.lecture;

import entities.subject.faculty.lecture.TeachingPlan;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.lecture.TeachingPlanFacade;
import entities.subject.faculty.FacultySubject;
import entities.subject.faculty.lecture.Lecture;
import entities.subject.faculty.lecture.LectureTPlan;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Named("teachingPlanController")
@SessionScoped
public class TeachingPlanController implements Serializable {

    private TeachingPlan current;
    private DataModel items = null;
    private DataModel itemsUser = null;
    private TeachingPlan[] selectedList;
    @EJB
    private beans.subject.faculty.lecture.TeachingPlanFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private FacultySubject facSub;
    private FacultySubject facSubImport;

    @PostConstruct
    public void init() {
        current = new TeachingPlan();
        facSub = new FacultySubject();
    }

    public TeachingPlanController() {
    }

    public TeachingPlan getSelected() {
        if (current == null) {
            current = new TeachingPlan();
            selectedItemIndex = -1;
        }
        return current;
    }

    public TeachingPlan[] getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(TeachingPlan[] selectedList) {
        this.selectedList = selectedList;
    }

    private TeachingPlanFacade getFacade() {
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

    public void setCurrent(TeachingPlan current) {
        this.current = current;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareListTP(FacultySubject f) {
        facSub = f;
        recreateModel();
        return "FSTP?faces-redirect=true";
    }
    
    public String prepareListRTP(FacultySubject f) {
        facSub = f;
        recreateModel();
        return "ReviewTP?faces-redirect=true";
    }

    public String prepareView() {
        current = (TeachingPlan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new TeachingPlan();
        selectedItemIndex = -1;
        return "Create";
    }

    public String prepareImport() {
        return "ImportTP?faces-redirect=true";
    }

    public String prepareCreateWithId(int f) {
        current = new TeachingPlan();
        selectedItemIndex = -1;
        facSub = getFacade().getFSById(f);
        return "CreateTPlan?faces-redirect=true";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeachingPlanCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String createTP() {
        current.setIdFacultySubject(facSub);
        create();
        recreateModel();
        return prepareCreateWithId(facSub.getIdFacultySubject());
    }

    public String importTP() {

        List<TeachingPlan> l = (List<TeachingPlan>) facSubImport.getTeachingPlanCollection();
        for (int i = 0; i < l.size(); i++) {
            current = l.get(i);
            createTP();
        }
        recreateModel();
        return prepareCreateWithId(facSub.getIdFacultySubject());
    }

    public String prepareEdit() {
        current = (TeachingPlan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeachingPlanUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareUpdateTP(TeachingPlan c) {
        current = c;
        return "UpdateTPlan?faces-redirect=true";
    }

    public String updateTP() {
        update();
        current = new TeachingPlan();
        return "CreateTPlan?faces-redirect=true";
    }

    public void teachingPlanXlsExport() {
        List<TeachingPlan> teachingPlan;
        teachingPlan = getItemsUserExport();
        Map beans = new HashMap();
        beans.put("teachingPlan", teachingPlan);
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=teachPlan"+facSub.toString()+".xls");

        XLSTransformer transformer = new XLSTransformer();
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            InputStream input = externalContext.getResourceAsStream("/resources/template.xls");
            ServletOutputStream out = response.getOutputStream();
            HSSFWorkbook workbook = transformer.transformXLS(input, beans);
            workbook.write(out);
        } catch (ParsePropertyException ex) {
            Logger.getLogger(TeachingPlanController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TeachingPlanController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public String destroy() {
        current = (TeachingPlan) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyTP(TeachingPlan c) {
        current = c;
        performDestroy();
        current = new TeachingPlan();
        selectedItemIndex = -1;
        recreateModel();
        return "CreateTPlan?faces-redirect=true";
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("TeachingPlanDeleted"));
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

    public FacultySubject getFacSub() {
        return facSub;
    }

    public FacultySubject getFacSubImport() {
        return facSubImport;
    }

    public void setFacSubImport(FacultySubject facSubImport) {
        this.facSubImport = facSubImport;
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    public DataModel getItemsUser() {
        
        List<TeachingPlan> t = getItemsUserExport();
        FacesContext context = FacesContext.getCurrentInstance();
        LectureController lc = (LectureController) context.getELContext().getELResolver().getValue(context.getELContext(), null, "lectureController");
        List<Lecture> ll = lc.getLectureByFSList(facSub);
        List<LectureTPlan> lt = new ArrayList();
        for(int i=0; i < t.size(); i++) {
            if(i< ll.size() ){
                LectureTPlan temp = new LectureTPlan(t.get(i),ll.get(i));
                lt.add(temp);
            }
            else {
                LectureTPlan temp = new LectureTPlan(t.get(i));
                lt.add(temp);
            }

        }
        
        
        itemsUser = new ListDataModel(lt);

        return itemsUser;
    }
    public DataModel getItemsUser(FacultySubject facSub) {
        this.facSub = facSub;
        return getItemsUser();
    }

    public List<TeachingPlan> getItemsUserExport() {
        return getFacade().getTeachingPlanByFS(facSub);
    }
    
    public List<TeachingPlan> getItemsUserExport(FacultySubject facSub) {
        return getFacade().getTeachingPlanByFS(facSub);
    }

    public void setFacSub(FacultySubject facSub) {
        this.facSub = facSub;
    }

    private void recreateModel() {
        items = null;
        itemsUser = null;
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

    @FacesConverter(forClass = TeachingPlan.class)
    public static class TeachingPlanControllerConverter implements Converter {

        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TeachingPlanController controller = (TeachingPlanController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "teachingPlanController");
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

        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TeachingPlan) {
                TeachingPlan o = (TeachingPlan) object;
                return getStringKey(o.getIdTeachingPlan());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + TeachingPlan.class.getName());
            }
        }
    }
}

