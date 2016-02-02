/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UserEntity;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import services.UserService;

/**
 *
 * @author Karl Lauret
 */
@ManagedBean
@RequestScoped
public class LogInBean {

    private String email;
    private String password;

    @EJB
    UserService userService;

    /**
     * Creates a new instance of LogInBean
     */
    public LogInBean() {

    }

    public String login() {
        UserEntity user = userService.isValidUser(email, password);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (user != null) {
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("username", user.getUsername());
            session.setAttribute("email", user.getEmail());
            session.setAttribute("id", user.getId());
            return "page";
        }else{
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username","NO NO");
            return "index";
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
