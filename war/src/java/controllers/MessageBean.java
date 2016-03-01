/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.MessageUserEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import services.MessageService;
import servicesSecondaire.MessageElementaire;
import servicesSecondaire.UserService2;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "messageBean")
@ManagedBean
@ViewScoped
public class MessageBean implements Serializable {

    private String selectedGroupName;

    private Integer nbOfNewMessagesTotal;

    @EJB
    UserService2 userService;

    @EJB
    MessageService messageService;

    @EJB
    MessageElementaire messageElementaire;

    /**
     * Creates a new instance of MessageBean
     */
    public MessageBean() {
    }

    @PostConstruct
    public void init() {

    }

    public List<String> getGroupMessage() {

        return this.messageElementaire.findGroupMessageByUserID(SessionBean.getUserId());

    }

    public Integer getNbOfNewMessage(String groupName) {

        return this.messageElementaire.findNbOfNewMessageForGroupName(SessionBean.getUserId(), groupName);

    }

    public Integer getNbOfNewMessage() {
        if (this.nbOfNewMessagesTotal == null) {
            this.nbOfNewMessagesTotal = this.messageElementaire.findNbOfNewMessageForUserID(SessionBean.getUserId());
        }
        return this.nbOfNewMessagesTotal;
    }

    public List<String> getListOfTarget(String groupName) {
        return this.messageElementaire.findTargetsOfMessagesByGroupName(groupName);
    }

    public List<MessageUserEntity> getMessageForGroup() {
        System.err.println("GETMESSAGES : " + this.selectedGroupName);
        //TODO NEED TO MARK THEN AS READ 
        if (this.selectedGroupName != null) {
            this.messageElementaire.messageRead(SessionBean.getUserId(), this.selectedGroupName);
            return messageElementaire.findMessageUserByGroupName(SessionBean.getUserId(), this.selectedGroupName);
        } else {
            return null;
        }
    }

    public String getSelectedGroupName() {
        return selectedGroupName;
    }

    public void setSelectedGroupName(String selectedGroupName) {
        this.selectedGroupName = selectedGroupName;
    }

    public void onRowSelect(SelectEvent event) {
        String tmp = ((String) event.getObject());
        //tmp.setNbNewsMessages(0);
        this.selectedGroupName = tmp; 
        if(this.nbOfNewMessagesTotal!=0){
          this.nbOfNewMessagesTotal = this.messageElementaire.findNbOfNewMessageForUserID(SessionBean.getUserId());
  
        }
        
    }

}
