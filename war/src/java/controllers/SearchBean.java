/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import commun.FriendOrNot;
import dao.UserEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import services.UserService;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Karl Lauret
 */
@ManagedBean(name = "searchBean")
@RequestScoped 
public class SearchBean {

    private String param;

    private List<FriendOrNot> results;

    @EJB
    UserService userService;

    /**
     * Creates a new instance of SearchBean
     */
    public SearchBean() {
    }

    /**
     *
     * @return
     */
    public String search() { 
        return "search.xhtml";//  ?? 
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    public List<FriendOrNot> searchPram(String param) {
        return userService.search(param, SessionBean.getUserId());
    }

}
