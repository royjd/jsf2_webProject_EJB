/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.SessionBean.getSession;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author zdiawara
 */
@WebFilter("/faces/*") 
public class AuthenticationFilter implements Filter {

   
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String pageRequested = request.getRequestURI();
        
        String messageUri = request.getContextPath() + "/faces/message.xhtml"; 
        Boolean connected = false;
        
        try{
            request.getSession(false).getAttribute("username").toString();
            connected = true;
        }
        catch(NullPointerException e){
            
        }
        
        if(messageUri.equals(pageRequested) && !connected){
            response.sendRedirect(request.getContextPath() + "/faces/index.xhtml");
        }
        
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }

}
