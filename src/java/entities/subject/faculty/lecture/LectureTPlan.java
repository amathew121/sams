/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.subject.faculty.lecture;

import entities.subject.faculty.lecture.Lecture;

/**
 *
 * @author Ashish Mathew
 */
public class LectureTPlan {

    private Lecture l;
    private TeachingPlan t;
    
    public LectureTPlan(TeachingPlan t, Lecture l){
        this.l=l;
        this.t=t;
    }
    public LectureTPlan(TeachingPlan t){
        this.l=new Lecture();
        this.t=t;
    }

    public Lecture getL() {
        return l;
    }

    public TeachingPlan getT() {
        return t;
    }

}
