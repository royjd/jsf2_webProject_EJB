/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validators;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import servicesSecondaire.UserService2;
/**
 *
 * @author zdiawara
 */
@ManagedBean(name="verifyUsername")
@RequestScoped
public class UsernameAlreadyUsed implements Validator{

    private static final String MESSAGE = "Username already used";
    
    @EJB
    private UserService2 userService;
        
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String username = (String)value;
        if(userService.findByUsername(username)!=null){
            throw new ValidatorException(new FacesMessage( FacesMessage.SEVERITY_ERROR, MESSAGE, null ) );
        }
    }
    
}
