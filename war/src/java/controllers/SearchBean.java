/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UserEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import services.UserService;
import java.util.HashMap;

/**
 *
 * @author Karl Lauret
 */
@ManagedBean(name = "searchBean")
@RequestScoped
public class SearchBean {

    private String param;

    private HashMap<UserEntity, Boolean> results;

    @EJB
    UserService userService;

    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
        this.results = new HashMap<>();
    }

    /**
     *
     * @return
     */
    public String search() {
        this.results = userService.search(this.param, sessionBean.getUserId());
        return "index"; // ??
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public HashMap<UserEntity, Boolean> getResults() {
        return results;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

}
