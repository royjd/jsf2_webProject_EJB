package controllers;


 
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
 
@ManagedBean
@ViewScoped
public class ButtonView implements Serializable{ 
     
    private String page;
    private String targetUsername;
    public String getPage() {
        return page;
    }
 
    public void doSomething(String p,String u){
        if(p!=null && !p.isEmpty()){
            this.page = p;
            
        }
        if(u!=null && !u.isEmpty()){
            this.targetUsername = u;
        } 
    }
    public void setPage(String page) {
        this.page = page;
    }
    
    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Welcome to Primefaces!!");
    }
     
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public String getTargetUsername() {
        return targetUsername;
    }

    public void setTargetUsername(String targetUsername) {
        this.targetUsername = targetUsername;
    }

}