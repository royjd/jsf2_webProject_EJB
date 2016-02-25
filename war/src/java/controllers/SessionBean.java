/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zdiawara
 */
public class SessionBean {

    public SessionBean() {

    }

    /**
     *
     * @return
     */
    public static boolean isConnect() {
        try{
            getUsername();
            return true;
        }catch(NullPointerException e){
            return false;
        }
        
    }

    public static HttpSession getSession(boolean etat) {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(etat);
    }

    /*public static HttpServletRequest getRequest() {
     return (HttpServletRequest) FacesContext.getCurrentInstance()
     .getExternalContext().getRequest();
     }*/
    public static String getUsername() {
        return getSession(false).getAttribute("username").toString();
    }

    public static String getEmail() {
        return getSession(false).getAttribute("email").toString();
    }

    public static Long getUserId() {
        HttpSession session = getSession(false);
        if (session == null) {
            return null;
        }
        return (Long) session.getAttribute("id");
    }

    public static void setDataUser(Long id, String usename, String email) {
        HttpSession session = getSession(true);
        session.setAttribute("username", usename);
        session.setAttribute("email", email);
        session.setAttribute("id", id);
    }

}
