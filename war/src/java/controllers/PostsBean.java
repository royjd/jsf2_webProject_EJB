/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AlbumEntity;
import dao.PostEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import services.PostService;
import services.UserService;
import servicesSecondaire.PhotoService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "postsBean")
@ManagedBean
@ViewScoped
public class PostsBean implements Serializable {

    private String title;
    private String message;
    private String comment;
    private Part file;
    private Long targetID;
    private boolean canComment;
    private String localisation;

    private List<UploadedFile> uploadedFiles;

    @EJB
    PostService postService;

    @EJB
    UserService userService;

    @EJB
    PhotoService photoService;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private PostEntity postComment;

    private static final String realPath = "/home/zakaridia/Documents/Depot_Git/File/image";

    /**
     * Creates a new instance of PostsBean
     */
    public PostsBean() {

    }

    @PostConstruct
    public void init() {
        uploadedFiles = new ArrayList<>();
    }

    public void upload(FileUploadEvent event) {
        uploadedFiles.add(event.getFile());
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

    public String saveNews() {

        Long authorID = SessionBean.getUserId();
        if (authorID == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

        /*ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
         .getExternalContext().getContext();
         String realPath = ctx.getContextPath(); */ // CAUTION DO NOT USE REAL PATH 
        //String realPath = "/home/SP2MI/zdiawara/Bureau/images";
        //tSring realPath = "/home/zakaridia/Documents/Depot_Git/File/image";
        postService.createNews(this.title, this.message, file, realPath, authorID, authorID);
        return navigationBean.home();

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

    public String createAlbum() {
        
        Long authorId = SessionBean.getUserId();
        if (authorId == null) {//TODO GO TO ERROR PAGE, NOT CONNECTED
            return null;
        }
        if (this.file != null && !this.file.getName().equals("") && realPath != null) {
            AlbumEntity album = postService.createAlbum(title, message, localisation, authorId);
            PostEntity post = photoService.createPhoto(album, authorId, file, realPath, true);
            return navigationBean.home(); // change it
        }
        return null; //
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
        System.err.println(message + " <= message");
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

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public void valOfComment() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        String name1 = (String) map.get("message");
        this.comment = name1;
        System.err.println("valOf : " + name1);
    }
}
