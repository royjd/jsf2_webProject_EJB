/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author zdiawara
 */
@ManagedBean(name="sessionBean")
@RequestScoped
public class SessionBean {

       
    public SessionBean(){
        
    }
    
    /**
     *
     * @return
     */
    public boolean isConnect() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return session != null;
    }
    
    public Long getUserId(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        if(session==null)
            return null;
        return (Long)session.getAttribute("id");
    }
}
