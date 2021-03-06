/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.AlbumEntity;
import dao.PostEntity;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import servicesSecondaire.PostService2;
import services.ProfileService;

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
    private Map<String, InputStream> files;

    private List<PostEntity> photoList;

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

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private PostEntity postComment;

    // private static final String realPath = "/home/SP2MI/zdiawara/Bureau/images";
    //private static final String realPath = "/home/zakaridia/Documents/Depot_Git/File/images";
    private static final String realPath = "C:/Users/Karl Lauret/AppData/Roaming/NetBeans/8.1/config/GF_4.1.1/domain1/applications/images";


    /**
     * Creates a new instance of PostsBean
     */
    public PostsBean() {

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

        /*ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
         .getExternalContext().getContext();
         String realPath = ctx.getContextPath(); */ // CAUTION DO NOT USE REAL PATH 
        //String realPath = "/home/SP2MI/zdiawara/Bureau/images";
        // String realPath = "/home/zakaridia/Documents/Depot_Git/File/image";
        postService.createNews(this.title, this.message, file, realPath, authorID, authorID);
        //return navigationBean.home();

    }

    public void saveNews(String targetUsername) {

        String authorUsername = SessionBean.getUsername();
        if (authorUsername == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED
        }

        /*ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
         .getExternalContext().getContext();
         String realPath = ctx.getContextPath(); */ // CAUTION DO NOT USE REAL PATH 
        //String realPath = "/home/SP2MI/zdiawara/Bureau/images";
        // String realPath = "/home/zakaridia/Documents/Depot_Git/File/image";
        postService.createNews(this.title, this.message, file, realPath, authorUsername, targetUsername);
        //return navigationBean.home();

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

    public void saveFiles(FileUploadEvent event) {
        if (this.files == null) {
            this.files = new HashMap<>();
        }

        try {
            UploadedFile uploadedFile = event.getFile();
            files.put(uploadedFile.getFileName(), uploadedFile.getInputstream());
        } catch (IOException ex) {
        }

    }

    public void addPhotoToAlbum(FileUploadEvent event) {
        if (SessionBean.isConnect()) {
            UploadedFile f = event.getFile();
            try {
                if (this.photoList == null) {
                    this.photoList = new ArrayList<>();
                }
                this.photoList.add(postService.addPhotoToAlbum(SessionBean.getUsername(), f.getFileName(), f.getInputstream(), realPath, targetID));
            } catch (IOException ex) {
            }
        }

    }

    public String createAlbum() {

        Long authorId = SessionBean.getUserId();
        if (authorId == null) {//TODO GO TO ERROR PAGE, NOT CONNECTED
            return null;
        }
        if (this.files != null && !files.isEmpty()) {
            postService.createAlbum(title, message, localisation, authorId, files, realPath);
            return navigationBean.album(SessionBean.getUsername()); // change it
        }
        //return null; //
        return navigationBean.album(SessionBean.getUsername());
    }

    public List<PostEntity> loadAllAlbums(String username) {
        List<PostEntity> post = postService2.findByUsernameAndType(username, "album");
        return post;
    }

    public List<PostEntity> loadMedias(Long albumId) {
        this.photoList = postService2.loadMedias(albumId);
        return this.photoList;
    }

    public List<PostEntity> loadMediasForUser(String username) {
        return  postService2.loadMedias(username);
    }

    public String searchDefaultAlbum() {
        if (SessionBean.isConnect()) {
            AlbumEntity album = postService2.findAlbum(SessionBean.getUserId(), "DefaultAlbum");
            if (album != null) {
                return navigationBean.displayAlbum(SessionBean.getUsername(), album.getId());
            }
        }
        return ""; //Error page
    }
    /*public String addPhotoToAlbum() {
     if (SessionBean.isConnect()) {
     postService.addPhotoToAlbum(SessionBean.getUsername(), file, realPath, targetID);
     return navigationBean.displayAlbum(SessionBean.getUsername(), this.targetID);
     }
     return ""; // Error page
     }*/
    //TODO LATER WITH A REAL targetID

    public void onPostLoad(String targetUsername) {
        String authorUsername = SessionBean.getUsername();
        if (authorUsername == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED 
        }
        if (targetUsername == null || targetUsername.isEmpty()) {
            targetUsername = authorUsername;
        }
        System.err.println(targetUsername + " targetusername");
        this.canComment = Objects.equals(authorUsername, targetUsername) || (userService.isFriend(authorUsername, targetUsername));
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
        return ""; //to reset the input in all the field
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

    public boolean getCanModify(String username) {
        return SessionBean.isConnect() && SessionBean.getUsername().equals(username);

    }

    public boolean getCanCommentTheTarget(String targetUsername) {
        if (!SessionBean.isConnect()) {
            return false;
        }
        String authorUsername = SessionBean.getUsername();
        if (authorUsername == null) {
            //TODO GO TO ERROR PAGE
            //NOT CONNECTED 
        }
        if (targetUsername == null || targetUsername.isEmpty()) {
            targetUsername = authorUsername;
        }
        System.err.println(targetUsername + " targetusername");
        return Objects.equals(authorUsername, targetUsername) || (userService.isFriend(authorUsername, targetUsername));

    }

    public boolean getCanRecommend(String targetUsername) {
        System.err.println(targetUsername + " targetusername getCanRecommend");
        if (SessionBean.isConnect()) {
            return userService.isFriend(SessionBean.getUsername(), targetUsername);

        } else {
            return userService.isFriend(null, targetUsername);

        }
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
        return ""; //to reset the input in all the field
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

    public List<PostEntity> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<PostEntity> photoList) {
        this.photoList = photoList;
    } 

}
