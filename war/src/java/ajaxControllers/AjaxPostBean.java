/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajaxControllers;

import dao.PostEntity;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import services.PostService;

/**
 *
 * @author Karl Lauret
 */
@Named(value = "ajaxPostBean")
@ManagedBean
@RequestScoped
public class AjaxPostBean {

    @EJB
    PostService postService;

    @ManagedProperty("#{param.id}")
    private Long id;
    
    private PostEntity post;

    /**
     * Creates a new instance of ajaxPostBean
     */
    public AjaxPostBean() {
        
        
    }

    public PostEntity getPost() {
        return  postService.findByID(this.id);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
