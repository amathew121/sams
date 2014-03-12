/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.CurrentStudent;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author phcoe
 */
class CSDataModel extends ListDataModel<CurrentStudent> implements SelectableDataModel<CurrentStudent>  {

    public CSDataModel() {
    }

    public CSDataModel(List<CurrentStudent> list) {
        super(list);
    }
    
    @Override  
    public CurrentStudent getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<CurrentStudent> cars = (List<CurrentStudent>) getWrappedData();  
          
        for(CurrentStudent car : cars) {  
            if(car.getAdmnNo().equals(rowKey))  
                return car;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(CurrentStudent car) {  
        return car.getAdmnNo();
    }  
    
}
