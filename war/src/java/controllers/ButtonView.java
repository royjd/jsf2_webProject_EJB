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

    public String getPage() {
        return page;
    }

    public void doSomething(String p){
        this.page = p;
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
}