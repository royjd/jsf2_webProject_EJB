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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author zdiawara
 */
@ManagedBean(name = "navigationBean")
@ApplicationScoped
public class NavigationBean implements Serializable {

    private static final long serialVersionUID = 1L;

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
        //this.pageContent = "home";
        return "home?faces-redirect=true";
    }

    public String wall() {
        String username = this.getUsername();
        System.err.println("Username = " + username);
        if (username != null) {
            return "wall.xhtml?faces-redirect=true&u=" + username;
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
            return "wall.xhtml?faces-redirect=true&p=" + page + "&u=" + username;
        }
        return "wall?faces-redirect=true";
    }

    private String wallPage(String page, String souspage) {
        String username = this.getUsername();
        if (username != null) {
            return "wall.xhtml?faces-redirect=true&p=" + page + "&sp="+souspage+"&u=" + username;
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
    public String friend() {
        return this.wallPage("friend");
    }

    public String media() {
        return this.wallPage("media" , "photo");
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
