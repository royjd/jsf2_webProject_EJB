/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.CommentEntity;
import dao.PostEntity;
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

/**
 *
 * @author Karl Lauret
 */
@Named(value = "evNewsBlogsBean")
@ManagedBean
@ViewScoped
public class EvNewsBlogsBean implements java.io.Serializable {

    @EJB
    PostService postService;

    private boolean moreData = true;

    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    List<PostEntity> list;

    public List<PostEntity> getPhilBlogs() {
        if (list == null) {
            list = getList();
        }
        return list;

    }

    public String getComments(PostEntity p, String comments) {
        if (p.getComments() == null && p.getComments().isEmpty()) { 
            return comments;
        }
        for (PostEntity po : p.getComments()) {
            comments += po.getBody();  
            comments += "=>"+this.getComments(po, comments);
        }
        return comments;
    }

    public void setPhilBlogs(List<PostEntity> s) {
    }

    List<PostEntity> getList() {
        System.err.println("getList");
        return postService.getRecentPostFromFriendAndMe(SessionBean.getUserId());
    }

    public TreeNode getTree(List<CommentEntity> l, TreeNode root) {
        if (root == null) {
            root = new DefaultTreeNode("Root", null);
        }
        if (l == null || l.isEmpty()) {
            return root;
        }
        for (PostEntity li : l) {
            root.getChildren().add(new DefaultTreeNode(this.getTree(li.getComments(), new DefaultTreeNode(li, root))));

        }
        return root;
        /*root = new DefaultTreeNode("Root", null);
        TreeNode node0 = new DefaultTreeNode("Node 0", root);
        TreeNode node1 = new DefaultTreeNode("Node 1", root);

        TreeNode node00 = new DefaultTreeNode("Node 0.0", node0);
        TreeNode node01 = new DefaultTreeNode("Node 0.1", node0);

        TreeNode node10 = new DefaultTreeNode("Node 1.0", node1);

        node1.getChildren().add(new DefaultTreeNode("Node 1.1"));
        node00.getChildren().add(new DefaultTreeNode("Node 0.0.0"));
        node00.getChildren().add(new DefaultTreeNode("Node 0.0.1"));
        node01.getChildren().add(new DefaultTreeNode("Node 0.1.0"));
        node10.getChildren().add(new DefaultTreeNode("Node 1.0.0"));
        root.getChildren().add(new DefaultTreeNode("Node 2"));
        return root; */
    }

    public void loadMore() {
        System.err.println("loadMore");
        List<PostEntity> listtmp = postService.getNextPostFromFriendAndMe(SessionBean.getUserId(), this.list.get(this.list.size() - 1).getId());
        moreData = listtmp.size() == 5;
        this.list.addAll(listtmp);
    }

    public void refresh(Long id) {
        System.err.println("refresh");
        PostEntity postModified = postService.findByID(id);
        System.err.println(postModified.getId());
        int index = 0;
        for (PostEntity p : this.list) {
            if (p.getId().equals(postModified.getId())) {
                index = this.list.indexOf(p);
            }
        }

        this.list.set(index, postModified);
        RequestContext.getCurrentInstance().update("philblog");
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

}
