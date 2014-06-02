package controllers.subject.faculty.lecture;

import entities.subject.faculty.lecture.LectureTags;
import controllers.util.JsfUtil;
import controllers.util.PaginationHelper;
import beans.subject.faculty.lecture.LectureTagsFacade;
import entities.subject.faculty.lecture.Lecture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
 *JSF Backing bean for lecturetags Entity
 * @author Administrator
 */
@Named("lectureTagsController")
@SessionScoped
public class LectureTagsController implements Serializable {

    private LectureTags current;
    private DataModel items = null;
    @EJB
    private beans.subject.faculty.lecture.LectureTagsFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    /**
     *creates the backing bean
     */
    public LectureTagsController() {
    }

    /**
     *Gets the selected lecturetags entity
     * @return
     */
    public LectureTags getSelected() {
        if (current == null) {
            current = new LectureTags();
            current.setLectureTagsPK(new entities.subject.faculty.lecture.LectureTagsPK());
            selectedItemIndex = -1;
        }
        return current;
    }

    private LectureTagsFacade getFacade() {
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
     *Sets the selected lecturetags Entity to view more details.Navigation case to View
     * @return
     */
    public String prepareView() {
        current = (LectureTags) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    /**
     *Navigation case to Create page after initializing a new lecturetags Entity
     * @return
     */
    public String prepareCreate() {
        current = new LectureTags();
        current.setLectureTagsPK(new entities.subject.faculty.lecture.LectureTagsPK());
        selectedItemIndex = -1;
        return "Create";
    }

    /**
     *Creates a new recored in the database for the selected entity
     * @return
     */
    public String create() {
        try {
            current.getLectureTagsPK().setIdLecture(current.getLecture().getIdLecture());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureTagsCreated"));
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
        current = (LectureTags) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    /**
     *Updates the selected lecturetags entity in the database
     * @return
     */
    public String update() {
        try {
            current.getLectureTagsPK().setIdLecture(current.getLecture().getIdLecture());
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureTagsUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    /**
     *Destroys the selected lecturetags entity, and deletes it from the database
     * @return
     */
    public String destroy() {
        current = (LectureTags) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }
    
    /**
     *
     * @param lectureTags
     */
    public void destroy(LectureTags lectureTags) {
        current = lectureTags;
        performDestroy();
        recreatePagination();
        recreateModel();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LectureTagsDeleted"));
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
     *Gets All lecturetags entities as few items one at a time
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
    public List<LectureTags> getTags() {
        return getFacade().findAll();
    }
    
    /**
     *
     * @param lecture
     * @return
     */
    public List<LectureTags> getTags(Lecture lecture) {
        if(lecture != null)
        return getFacade().getTags(lecture);
        else 
            return new ArrayList<LectureTags>();
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
     *Gets list of all lecturetags entities to be able to select many from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    /**
     *Gets list of all lecturetags entities to be able to select one from it
     * @return
     */
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public LectureTags getLectureTags(entities.subject.faculty.lecture.LectureTagsPK id) {
        return ejbFacade.find(id);
    }

    /**
     *Converter Class for lecturetags Entity
     */
    @FacesConverter(forClass = LectureTags.class)
    public static class LectureTagsControllerConverter implements Converter {

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
            LectureTagsController controller = (LectureTagsController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "lectureTagsController");
            return controller.getLectureTags(getKey(value));
        }

        entities.subject.faculty.lecture.LectureTagsPK getKey(String value) {
            entities.subject.faculty.lecture.LectureTagsPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new entities.subject.faculty.lecture.LectureTagsPK();
            key.setIdLecture(Integer.parseInt(values[0]));
            key.setTag(values[1]);
            return key;
        }

        String getStringKey(entities.subject.faculty.lecture.LectureTagsPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getIdLecture());
            sb.append(SEPARATOR);
            sb.append(value.getTag());
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
            if (object instanceof LectureTags) {
                LectureTags o = (LectureTags) object;
                return getStringKey(o.getLectureTagsPK());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + LectureTags.class.getName());
            }
        }
    }
}
