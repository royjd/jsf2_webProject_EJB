package controllers;


 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
 
@ManagedBean
@ViewScoped
public class ButtonView {
     
    private String page;
    private String sousPage;

    public ButtonView() {
        this.page = "default";
    }
    
    public String getPage() {
        return page;
    }
    public void setPage(String page) {
        this.page = page;
    }

    public String getSousPage() {
        return sousPage;
    }

    public void setSousPage(String sousPage) {
        this.sousPage = sousPage;
    }
    
    
}