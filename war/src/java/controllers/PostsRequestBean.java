/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.PostEntity;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import services.PostService;
import services.UserService;
import servicesSecondaire.PhotoService;
import servicesTertiaire.PostService2;
import javax.faces.bean.RequestScoped;
import services.ProfileService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "postsRequestBean")
@ManagedBean
@RequestScoped
public class PostsRequestBean implements Serializable {

    private String title;
    private String message;
    private String comment;
    private Part file;
    

    @EJB
    PostService postService;

    @EJB
    PostService2 postService2;

    @EJB
    UserService userService;

    @EJB
    PhotoService photoService;
    
    @EJB
    ProfileService profileService;


    private PostEntity postComment;
 
   // private static final String realPath = "/home/SP2MI/zdiawara/Bureau/images";
    //private static final String realPath = "/home/zakaridia/Documents/Depot_Git/File/image";
    private static final String realPath = "C:/Users/Karl Lauret/AppData/Roaming/NetBeans/8.1/config/GF_4.1.1/domain1/applications/images";

    /**
     * Creates a new instance of PostsBean
     */
    public PostsRequestBean() {

    }

  

    public List<PostEntity> getHomePosts() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

        if (session.getAttribute("id") == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }
        return postService.getRecentPostFromFriendAndMe(SessionBean.getUserId());
    }

    public void saveNews() {

        Long authorID = SessionBean.getUserId();
        if (authorID == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

     
        postService.createNews(this.title, this.message, file, realPath, authorID, authorID);


    }

    public void saveNews(String targetUsername) {

        String authorUsername = SessionBean.getUsername();
        if (authorUsername == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

        /*ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
         .getExternalContext().getContext();*/
     
        postService.createNews(this.title, this.message, file, realPath, authorUsername, targetUsername);


    }

    public void saveRecommendation(String targetUsername) {

        Long authorID = SessionBean.getUserId();
        if (authorID == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }
        postService.createRecommendation(this.title, this.message, authorID, targetUsername);

        //return navigationBean.home();
    }

    public void saveComment(Long parentID, Long mainID, boolean main) {
        Long authorID = SessionBean.getUserId();
        System.err.println("SAVE COMMENT");
        if (authorID == null) {

            System.err.println("AUTHOR NULL");
        }

        if (main) {
            postService.createComment(this.message, authorID, parentID, mainID);
        } else {
            postService.createComment(this.comment, authorID, parentID, mainID);

        }
        System.err.println("POST OK");

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
        System.err.println(message + " <= message");
        this.message = message;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public boolean getCanModify(String username) {
            return SessionBean.isConnect() && SessionBean.getUsername().equals(username);
        
    }

    public PostEntity getPostComment() {
        return postComment;
    }

    public void setPostComment(PostEntity postComment) {
        this.postComment = postComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public void valOfComment() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        String name1 = (String) map.get("message");
        this.comment = name1;
        System.err.println("valOf : " + name1);
    }


}
