/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.feedback;

import entities.subject.faculty.FacultySubject;
import entities.feedback.Feedback2013Question;
import entities.feedback.FeedbackType;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.HorizontalBarChartModel;

/**
 *
 * @author Ashish
 */
@Named("feedbackChartController")
@SessionScoped
public class FeedbackChartController implements Serializable {

    @EJB
    private beans.feedback.Feedback2013Facade ejbFacade;
    private HorizontalBarChartModel barModel;
    private FeedbackType feedbackType;
    private Feedback2013Question feedbackQuestion;

    @PostConstruct
    public void init() {
        createBarModel();

    }

    public FeedbackType getType() {
        return feedbackType;
    }

    public void setType(FeedbackType fType) {
        this.feedbackType = fType;
    }

    public Feedback2013Question getQuestion() {
        return feedbackQuestion;
    }

    public void setQuestion(Feedback2013Question fQuestion) {
        this.feedbackQuestion = fQuestion;
    }

    private void createBarModel() {
        barModel = initBarModel(130, 5);

        barModel.setTitle("I would like to have the teacher for any advanced course.");
        barModel.setLegendPosition("ne");
        barModel.setStacked(false);

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Faculty Subject");

        Axis xxAxis = barModel.getAxis(AxisType.X);
        xxAxis.setLabel("Rating");
    }

    private HorizontalBarChartModel initBarModel(int qid, int type) {
        HorizontalBarChartModel model = new HorizontalBarChartModel();

        ChartSeries sd = new ChartSeries();
        sd.setLabel("Disagree -> 5*SD + 2.5*DD");
        ChartSeries sa = new ChartSeries();
        sa.setLabel("Agree -> 5*SA + 2.5*AA");
        ChartSeries net = new ChartSeries();
        net.setLabel("NET -> 5*SA + 2.5*AA - (5*SD + 2.5*DD)");

        List<Object[]> l = getFeedbackByQuestion(qid, type);

        for (Object[] item : l) {

            //System.out.println(item);
            switch ((Short) item[2]) {
                case 1:
                    Long x = (Long) sd.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x - (10 * (Long) item[3])/2;
                    sd.set((FacultySubject) item[0], x);
                    x = (Long) net.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x - (10 * (Long) item[3])/2;
                    net.set((FacultySubject) item[0], x);
                    break;
                case 2:
                    x = (Long) sd.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x - (5 * (Long) item[3])/2;
                    sd.set((FacultySubject) item[0], x);
                    x = (Long) net.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x - (5*(Long) item[3])/2;
                    net.set((FacultySubject) item[0], x);
                    break;
                case 3:
                    break;
                case 4:
                    x = (Long) sa.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x + (5*(Long) item[3])/2;
                    sa.set((FacultySubject) item[0], x);
                    x = (Long) net.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x + (5*(Long) item[3])/2;
                    net.set((FacultySubject) item[0], x);
                    break;
                case 5:
                    x = (Long) sa.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x + (10 * (Long) item[3])/2;
                    sa.set((FacultySubject) item[0], x);
                    x = (Long) net.getData().get((FacultySubject) item[0]);
                    if (x == null) {
                        x = 0l;
                    }
                    x = x + (10 * (Long) item[3])/2;
                    net.set((FacultySubject) item[0], x);
                    break;
            }
        }

        //model.addSeries(sd);
        //model.addSeries(sa);
        model.addSeries(net);

        return model;
    }

    public void rereateBarModel() {
        barModel = initBarModel(feedbackQuestion.getQid(), feedbackType.getIdFeedbackType());
        barModel.setTitle(feedbackQuestion.getQtext());
    }

    private List<Object[]> getFeedbackByQuestion(int qid, int type) {
        return getFacade().getFeedbackByQuestion(qid, type);
    }

    public HorizontalBarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(HorizontalBarChartModel barModel) {
        this.barModel = barModel;
    }

    private beans.feedback.Feedback2013Facade getFacade() {
        return ejbFacade;
    }
}
