/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import commun.FriendOrNot;
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
import servicesSecondaire.UserService2;

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

    @EJB
    UserService2 userService2;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    /**
     * Creates a new instance of FriendBean
     */
    public FriendBean() {
    }

    public void acceptFriend(Long ownerID) {
        System.err.println("acceptFriend owned by :" + ownerID);
        userService.acceptFriendship(SessionBean.getUserId(), ownerID);

    }

    public void deniedFriend(Long ownerID) {
        userService.removeFriend(SessionBean.getUserId(), ownerID);

    }

    public void removeFriend(Long friendID) {
        System.err.println("removeFriend 1");
        userService2.removeFriend(friendID);

    }

    public String removeFriend(Long userID1, Long userID2) {
        System.err.println("removeFriend 1");
        userService.removeFriend(userID1, userID2);
        return navigationBean.home();

    }

    public List<FriendEntity> findFriendsToAccept() {

        return userService2.findFriendToAccept(SessionBean.getUserId());
    }

    public List<FriendEntity> findFriends(Long id) {
        return userService2.findFriendsListFriendByUserID(id);
    }
 
    public List<FriendOrNot> findFriendsOfTarget(String username) { 
        System.err.println("findFriendsOfTarget" + SessionBean.isConnect());
        if (SessionBean.isConnect()) {
            return userService.getFriendListByUsername(SessionBean.getUsername(), username);
 
        } else {
            return userService.getFriendListByUsername(null, username);

        }
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void addFriend(Long id) {
        if (userService2.addFriend(SessionBean.getUserId(), id)) {

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
