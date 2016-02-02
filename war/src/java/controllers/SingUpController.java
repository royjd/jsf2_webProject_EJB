/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UserEntity;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.UserService;

/**
 *
 * @author klaure01
 */
@ManagedBean
@ViewScoped//needed for the ajax request Option that cost the least I think
public class SingUpController implements Serializable {

    private UserEntity user;

    @EJB
    UserService userService;

    /**
     * Creates a new instance of TestController
     */
    public SingUpController() {
        user = new UserEntity();
    }

    public String singUp() {
        userService.add(user);
        FacesMessage message = new FacesMessage("Success!");
        FacesContext.getCurrentInstance().addMessage(null, message);
        return "page";

    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
