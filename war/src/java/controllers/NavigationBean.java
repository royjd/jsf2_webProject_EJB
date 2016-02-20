/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.Serializable;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author zdiawara
 */
@Named("navigationBean")
@ManagedBean
@ApplicationScoped
public class NavigationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    private String p = "friend";

    /*private String pageContent;
     private String wallContent;*/
    public NavigationBean() {
        //pageContent = "home";
    }

    public String index() {
        return this.index(false);
    }

    public String index(boolean redirect) {
        if (redirect) {
            return "index?faces-redirect=true";
        }
        return "index";
    }

    private String page() {
        return "page";
    }

    public String home() {
        System.err.println("HOME");
        //this.pageContent = "home";
        return "home?faces-redirect=true";
    }

    public String wall() {
        String username = this.getUsername();
        System.err.println("Username = " + username);
        if (username != null) {
            return "wall?faces-redirect=true&u=" + username + "&p=default";
        }
        return "wall?faces-redirect=true";
    }

    public String wall(String username) {
        System.err.println("Username = " + username);
        if (username != null) {
            return "wall?faces-redirect=true&u=" + username + "&p=default";
        }
        return "wall?faces-redirect=true";
    }

    /**
     *
     * @param page
     * @return
     */
    private String wallPage(String page) {
        String username = this.getUsername();
        if (username != null) {
            return "wall?faces-redirect=true&p=" + page + "&u=" + username;
        }
        return "wall?faces-redirect=true&p=default";
    }

    /**
     *
     * @param page
     * @return
     */
    private String wallPage(String page,String username) {
        
        if (username != null) {
            return "wall?faces-redirect=true&p=" + page + "&u=" + username;
        }
        return "wall?faces-redirect=true&p=default";
    }

    private String wallPageSousPage(String page, String souspage) {
        String username = this.getUsername();
        if (username != null) {
            return "wall?faces-redirect=true&p=" + page + "&sp=" + souspage + "&u=" + username;
        }
        return "wall?faces-redirect=true";
    }

    private String getUsername() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("u");
    }

    /**
     *
     * @return
     */
    public String recommendation() {
        //this.pageContent = "walgFolder + "/recommendation/display";
        return this.wallPage("recommendation");
    }

    /**
     *
     * @return
     */
    public String recommendation(String username) {
        //this.pageContent = "walgFolder + "/recommendation/display";
        return this.wallPage("recommendation", username);
    }

    /**
     *
     * @return
     */
    public String friend() {
        return this.wallPage("friend");
    }

    public String media() {
        return this.wallPage("media", "photo");
    }

    public String profile() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String file = params.get("file");
        if (file != null && !file.isEmpty()) {
            //    this.wallContent = wallFolder + "/profile/"+file;
        } else {
            //    this.wallContent = wallFolder + "/profile/viewProfile";
        }

        //this.pageContent = "wall";
        return this.page();
    }

}
