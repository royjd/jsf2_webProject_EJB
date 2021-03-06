/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UserEntity;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import services.UserService;
import javax.faces.bean.ViewScoped;
import org.apache.commons.codec.digest.DigestUtils;
import servicesSecondaire.UserService2;

/**
 *
 * @author zdiawara
 */
@ManagedBean(name = "userBean")
@ViewScoped //needed for the ajax request Option that cost the least I think
public class UserBean {

    // Add or Login a user
    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;

    @EJB
    UserService userService;
    @EJB
    UserService2 userElementaire;

    @ManagedProperty(name = "navigationBean", value = "#{navigationBean}")
    private NavigationBean navigationBean;

    /**
     * Creates a new instance of SessionBean
     */
    public UserBean() {

    }

    /**
     *
     * @return
     */
    public String singIn() {
        UserEntity user = userElementaire.isValidUser(email, password);
        if (user == null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("singin", "Invalid identifiant");
            return navigationBean.index();
        }
        SessionBean.setDataUser(user.getId(), user.getUsername(), user.getEmail());
        return navigationBean.home();
    }

    /**
     *
     * @return
     */
    public String singUp() {
        Long id = userService.add(email, username, password, firstName, lastName);
        if (id == null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("singup", "Fail !");
            return navigationBean.index(true);
        }
        SessionBean.setDataUser(id, username, email);
        return navigationBean.home();
    }

    /**
     *
     * @return
     */
    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return navigationBean.index();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null) {
            password = "";
        }
        this.password = DigestUtils.md5Hex(password); // Crypt password
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public NavigationBean getNavigationBean() {
        return navigationBean;
    }

    public void setNavigationBean(NavigationBean navigationBean) {
        this.navigationBean = navigationBean;
    }

}
