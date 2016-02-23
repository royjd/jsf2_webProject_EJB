/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import commun.GroupListNewMessages;
import dao.MessageUserEntity;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import services.MessageService;
import servicesSecondaire.UserService2;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "messageBean")
@ManagedBean
@ViewScoped
public class MessageBean implements Serializable {

    private GroupListNewMessages selectedGroupName;

    @EJB
    UserService2 userService;

    @EJB
    MessageService messageService;

    /**
     * Creates a new instance of MessageBean
     */
    public MessageBean() {
    }

    @PostConstruct
    public void init() {

    }

    public List<GroupListNewMessages> getGroupMessage() {
       
        return this.messageService.findGroupMessageByUserID(SessionBean.getUserId());

    }

    public List<MessageUserEntity> getMessageForGroup() {
        System.err.println("GETMESSAGES : " + this.selectedGroupName);
        //TODO NEED TO MARK THEN AS READ
        if(this.selectedGroupName!=null){
            this.messageService.messageRead(SessionBean.getUserId(), this.selectedGroupName.getGroupName());
            return messageService.findMessageUserByGroupName(SessionBean.getUserId(), this.selectedGroupName.getGroupName());
        }else{
            return null; 
        }
    }

    public GroupListNewMessages getSelectedGroupName() {
        return selectedGroupName;
    }

    public void setSelectedGroupName(GroupListNewMessages selectedGroupName) {
        this.selectedGroupName = selectedGroupName;
    }

    public void onRowSelect(SelectEvent event) {
        GroupListNewMessages tmp = ((GroupListNewMessages) event.getObject());
        tmp.setNbNewsMessages(0);
        this.selectedGroupName = tmp;

    }

}
