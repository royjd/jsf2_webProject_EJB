/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import commun.GroupListNewMessages;
import dao.CommentEntity;
import dao.MessageUserEntity;
import dao.NotificationEntity;
import dao.PostEntity;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.SelectEvent;
import services.MessageService;
import services.PostService;
import servicesSecondaire.MessageElementaire;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "notificationBean")
@ManagedBean
@ViewScoped
public class NotificationBean implements Serializable {

    private MessageUserEntity selectedNotificatin;

    @EJB
    MessageService messageService;

    @EJB
    PostService postService;
    @EJB
    MessageElementaire messageElementaire;

    /**
     * Creates a new instance of NotificationBean
     */
    public NotificationBean() {
    }

    public List<MessageUserEntity> getNotifications() {
        return messageElementaire.getNotificationByUser(SessionBean.getUserId());
    }

    public PostEntity getPostOfNotification() {
        if (this.selectedNotificatin != null) {
            this.messageElementaire.messageRead(this.selectedNotificatin.getId());
            PostEntity tmp = ((NotificationEntity) this.selectedNotificatin.getMessage()).getPost();
            if (tmp instanceof CommentEntity) {
                return ((CommentEntity) tmp).getPostMain();
            } else {
                return tmp;
            }
        } else {
            return null;
        }
    }

    public MessageUserEntity getSelectedNotificatin() {

        return this.selectedNotificatin;
    }

    public void setSelectedNotificatin(MessageUserEntity selectedNotificatin) {
        this.selectedNotificatin = selectedNotificatin;
    }

    public void onRowSelect(SelectEvent event) {
        MessageUserEntity tmp = ((MessageUserEntity) event.getObject());
        tmp.setNewMessage(false);
        this.selectedNotificatin = tmp;

    }

}
