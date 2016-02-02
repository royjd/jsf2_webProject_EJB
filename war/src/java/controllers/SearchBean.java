/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "searchBean")
@Dependent
public class SearchBean {

    private String param;
    
    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
    }
    
    public String search(){
        return "page";
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
    
    
}
