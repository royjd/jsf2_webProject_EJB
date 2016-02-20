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

    private String sp;

    public NavigationBean() {
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

    public String home() {
        //this.pageContent = "home";
        return "home?faces-redirect=true";
    }

    public String wall() {
        String username = this.getUsername();
        System.err.println("Wall is call");
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

    private String wallSousPage(String page, String souspage) {
        String username = this.getUsername();
        if (username != null) {
            return "wall.xhtml?faces-redirect=true&p=" + page + "&sp=" + souspage + "&u=" + username;
        }
        //return "wall?faces-redirect=true";
        return "wall.xhtml?faces-redirect=true&p=" + page + "&sp=" + souspage + "&u=ooo";
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
        String username = this.getUsername();
        if (username != null) {
            return "wall.xhtml?faces-redirect=true&p=media" + "&u=" + username;
        }
        return "wall?faces-redirect=true"; //
    }

    public String createAlbum() {
        System.err.println(" +++++");
        this.sp = "createAlbum";
        return this.wallSousPage("media", "createAlbum");

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
        return "";
    }

    public String getSp() {
        return sp;
    }

    public void setP(String sp) {
        this.sp = sp;
    }

}
