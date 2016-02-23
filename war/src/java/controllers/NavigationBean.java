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

    public String message() {
        String username = this.getUsername();
        System.err.println("Username = " + username);
        if (username != null) {
            return "message?faces-redirect=true&u=" + username + "&p=default";
        }
        return "message?faces-redirect=true";
    }

    public String notification() {
        String username = this.getUsername();
        System.err.println("Username = " + username);
        if (username != null) {
            return "notification?faces-redirect=true&u=" + username + "&p=default";
        }
        return "notification?faces-redirect=true";
    }

    public String wall(String username) {
        System.err.println("Wall pad with username : " + username);
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
    private String wallPage(String page, String username) {

        if (username != null) {
            return "wall?faces-redirect=true&p=" + page + "&u=" + username;
        }
        return "wall?faces-redirect=true&p=default";
    }

    private String wallPage(String page, String souspage, String username) {
        if (username != null) {
            return "wall?faces-redirect=true&p=" + page + "&sp=" + souspage + "&u=" + username;
        }
        //return "wall?faces-redirect=true";
        return "wall.xhtml?faces-redirect=true&p=" + page + "&sp=" + souspage + "&u=ooo";
    }

    private String wallSousPage(String page, String souspage) {
        String username = this.getUsername();
        return this.wallPage(page, souspage, username);
    }

    private String wallSousPage(String page, String souspage, Long id) {
        String username = this.getUsername();
        if (username != null) {
            return "wall?faces-redirect=true&p=" + page + "&sp=" + souspage + "&id=" + id + "&u=" + username;
        }
        //return "wall?faces-redirect=true";
        return "wall.xhtml?faces-redirect=true&p=" + page + "&sp=" + souspage + "&u=ooo";
    }

    private String getUsername() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("u");
    }

    private Long getIdFromUrl() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String string = params.get("id");
        if (string == null) {
            return 0L;
        }
        return Long.parseLong(params.get("id"));
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
     * @param username
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

    public String friend(String targetUsername) {
        return this.wallPage("friend", targetUsername);
    }

    public String media() {
        String username = this.getUsername();
        if (username != null) {
            return "wall.xhtml?faces-redirect=true&p=media" + "&u=" + username;
        }
        return "wall?faces-redirect=true"; //
    }

    public String createAlbum() {
        return this.wallSousPage("media", "createAlbum");
    }

    public String displayAlbum() {
        return this.wallSousPage("media", "displayAlbum");
    }

    public String profile() {
        String username = this.getUsername();
        if (username != null) {
            return "wall.xhtml?faces-redirect=true&p=profile" + "&u=" + username;
        }
        return "";
    }

    public String profile(String username) {
        if (username != null) {
            return "wall.xhtml?faces-redirect=true&p=profile" + "&u=" + username;
        }
        return "";
    }

    public String editProfile() {
        return this.wallSousPage("profile", "editProfile");
    }

    public String experience() {
        return this.wallSousPage("profile", "displayExperiences");
    }

    public String experience(String username) {
        return this.wallPage("profile", "displayExperiences", username);
    }

    public String manageExperience() {
        Long id = this.getIdFromUrl();
        if (id == null) {
            id = 0L;
        }
        return this.wallSousPage("profile", "manageExperience", id);
    }

}
