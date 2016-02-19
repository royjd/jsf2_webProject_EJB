/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.FriendEntity;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import services.UserService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "friendBean")
@ManagedBean
@RequestScoped
public class FriendBean implements Serializable {

    @EJB
    UserService userService;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    /**
     * Creates a new instance of FriendBean
     */
    public FriendBean() {
    }

    public String acceptFriend(Long ownerID) {
        userService.acceptFriendship(SessionBean.getUserId(), ownerID);
        return navigationBean.home();

    }

    public String deniedFriend(Long ownerID) {
        userService.deniedFriendship(SessionBean.getUserId(), ownerID);
        return navigationBean.home();
 
    }

    public String removeFriend(Long friendID) {
        System.err.println("removeFriend 1");
        userService.removeFriend(friendID);
        return navigationBean.home();

    
    }

    public List<FriendEntity> findFriendsToAccept() {

        return userService.getFriendToAccept(SessionBean.getUserId());
    }

    public List<FriendEntity> findFriends(Long id) {
        return userService.getFriendsListFriendByUserID(id);
    } 

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addFriend(Long id) {
        if (userService.addFriend(SessionBean.getUserId(), id)) {

            System.err.println(id);
            addMessage("Request Sent!");

        } else {

            System.err.println("no Add Friend");
            addMessage("Request failed!");

        }
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

}
