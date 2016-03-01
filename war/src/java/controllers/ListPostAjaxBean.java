/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.CommentEntity;
import dao.PostEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import services.PostService;
import servicesTertiaire.PostService2;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "listPostAjaxBean")
@ManagedBean
@ViewScoped
public class ListPostAjaxBean implements java.io.Serializable {

    @EJB
    PostService postService;

    @EJB
    PostService2 postService2;

    private String targetUsername;

    private String postType;

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    private boolean moreData = true;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    List<PostEntity> list;

    public List<PostEntity> getPhilBlogs() {
        if (list == null) {
            list = getList("", null);
        }
        return list;

    }

    public String getComments(PostEntity p, String comments) {
        if (p.getComments() == null && p.getComments().isEmpty()) {
            return comments;
        }
        for (PostEntity po : p.getComments()) {
            comments += po.getBody();
            comments += "=>" + this.getComments(po, comments);
        }
        return comments;
    }

    public List<PostEntity> wall(String username) {
        if (list == null) {
            list = getList("default", username);
        }
        return list;

    }

    public List<PostEntity> recommendation(String username) {
        System.err.println("recommendation => Username = " + username);
        if (list == null) {
            list = this.getList("recommendation", username);
        }
        return list;

    }

    public List<PostEntity> notification(String username, Long postID) {
        System.err.println("notification => Username = " + username + " , postID => " + postID);

        PostEntity tmp = postService2.findByID(postID);

        if (tmp != null) {
            list = new ArrayList<>();
            list.add(tmp); 
        }else{
            this.list = null;
        }

        return list;

    }

    private List<PostEntity> getList(String page, String username) {
        System.err.println("getList username =" + username + " page : " + page);
        if (username == null) {
            return postService.getRecentPostFromFriendAndMe(SessionBean.getUserId());
        } else if (page.equals("default") && !username.isEmpty()) {
            return postService.getRecentPostFromMe(username);
        } else if (page.equals("recommendation")) {
            return postService.getRecentRecommendationFromUserID(username);
        } else {
            return null;
        }
    }

    public void loadMore(String page, String username) {
        System.err.println("loadMore====================================================================");
        List<PostEntity> listtmp = new ArrayList<>();
        this.targetUsername = username;
        System.err.println("loadMore username + " + this.targetUsername);
        System.err.println("loadMore = + " + this.moreData);
        if (this.list != null &&  this.list.size() >= 5 && moreData) {
            if (this.targetUsername == null || this.targetUsername.isEmpty()) {

                listtmp = postService.getNextPostFromFriendAndMe(SessionBean.getUserId(), this.list.get(this.list.size() - 1).getId());

            } else if (page.equals("default")) {

                listtmp = postService.getNextPostFromUserID(this.targetUsername, this.list.get(this.list.size() - 1).getId());

            } else if (page.equals("recommendation")) {

                listtmp = (List<PostEntity>) postService.getNextRecommendationFromUserID(this.targetUsername, this.list.get(this.list.size() - 1).getId());
            }
            System.err.println(listtmp.size() + " size of load more list");
            moreData = listtmp.size() == 5;
            this.list.addAll(listtmp);

        } else {
            moreData = false;
        }
        System.err.println("loadMore = + " + this.moreData);
    }

    public void refresh(String componentID, Long id) {
        System.err.println("refresh");
        PostEntity postModified = postService2.findByID(id);
        System.err.println(postModified.getId());
        int index = 0;
        for (PostEntity p : this.list) {
            if (p.getId().equals(postModified.getId())) {
                index = this.list.indexOf(p);
            }
        }

        this.list.set(index, postModified);
        RequestContext.getCurrentInstance().update("philblog" + componentID);
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

    public boolean isMoreData() {
        return moreData;
    }

    public void setMoreData(boolean moreData) {
        this.moreData = moreData;
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

}
