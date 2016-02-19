/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.PostEntity;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import services.PostService;
import services.UserService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "postsBean")
@ManagedBean
@ViewScoped
public class PostsBean {

    private String title;
    private String message;
    private Part file;
    private Long targetID;
    private boolean canComment;

    @EJB
    PostService postService;

    @EJB
    UserService userService;

    /**
     * Creates a new instance of PostsBean
     */
    public PostsBean() {
        this.title = null;
        this.message = null;
        this.file = null;
        this.targetID = null;

    }

    public List<PostEntity> getHomePosts() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session.getAttribute("id") == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }
        return postService.getRecentPostFromFriendAndMe((Long) session.getAttribute("id"));
    }

    public void saveNews() {

        Long authorID = SessionBean.getUserId();
        if (authorID == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

        /*ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getContextPath(); */ // CAUTION DO NOT USE REAL PATH 
        String realPath = "/home/zakaridia/Documents/Depot_Git/File/images";
        postService.createNews(this.title, this.message, file, realPath, authorID, authorID);

    }

    public void saveComment(Long parentID, Long mainID) {
        Long authorID = SessionBean.getUserId();
        if (authorID == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }
        postService.createComment(this.message, authorID, parentID, mainID);
    }

    //TODO LATER WITH A REAL targetID
    public void onPostLoad(Long targetID) {
        Long authorID = SessionBean.getUserId();
        if (authorID == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

        this.canComment = (userService.isFriend(authorID, targetID) || Objects.equals(authorID, targetID));
    }

    public Long getTargetID() {
        return targetID;
    }

    //GETTER - SETTER
    public void setTargetID(Long targetID) {
        this.targetID = targetID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean getCanComment() {
        return canComment;
    }

    public void setCanComment(boolean canComment) {
        this.canComment = canComment;
    }

}
