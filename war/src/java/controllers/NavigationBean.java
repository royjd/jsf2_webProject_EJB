/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped ;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author zdiawara
 */
@ManagedBean(name = "navigationBean")
@SessionScoped
public class NavigationBean {
    
    private String pageContent;

    public NavigationBean() {
        this.pageContent = ""; // to inialise ??
    }
    
    public String home(){
        this.pageContent = "home";
        return "page?faces-redirect=true";
    }
    
    public String wall(){
        return "wall";
    }

    public String getPageContent() {
        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }
    
    
    
}
