/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.PostEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import services.PostService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "postsBean")
@ManagedBean
@RequestScoped
public class PostsBean {

    private String title;
    private String message;
    private Part file;
    private Long targetID;

    @EJB
    PostService postService;

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
        return postService.getRecentPostFromFriendAndMe(1L);
    }

    public void saveNews() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session.getAttribute("id") == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

        Long authorID = (Long) session.getAttribute("id");
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("resources/img");
        postService.createNews(this.title, this.message, file, realPath, authorID, authorID);

    }

   /* public void saveComment() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session.getAttribute("id") == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

        Long authorID = (Long) session.getAttribute("id");
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        String realPath = ctx.getRealPath("resources/img");
        postService.createComment(this.title, this.message, file, realPath, authorID, authorID);

    }*/

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

}
