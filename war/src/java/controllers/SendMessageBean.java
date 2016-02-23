/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import services.MessageService;
import services.UserService;
import servicesSecondaire.UserService2;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "sendMessageBean")
@ManagedBean
@RequestScoped
public class SendMessageBean {

    private String message;
    private List<String> targets;

    @EJB
    UserService2 userService;

    @EJB
    MessageService messageService;

    /**
     * Creates a new instance of MessageBean
     */
    public SendMessageBean() {
    }

    public void send() {
        messageService.sendToMails(this.message, "osef", SessionBean.getUserId(), targets ); 
    }

    public List<UserEntity> completeText(String query) {
        List<UserEntity> usersFound = userService.search(query);

        return usersFound;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getTargets() {
        return targets;
    }

    public void setTargets(List<String> targets) {
        this.targets = targets;
    }

}
