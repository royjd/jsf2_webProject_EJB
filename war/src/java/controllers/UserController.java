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
import services.UserService;

/**
 *
 * @author Karl Lauret
 */
@ManagedBean
@RequestScoped//needed for the ajax request Option that cost the least I think
public class UserController {
    
    private UserEntity user;
    
    /**
     * Creates a new instance of UserController
     */
    public UserController() {
        user = new UserEntity();
    }

    public void singIn(){
        
    }
    
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    
}
