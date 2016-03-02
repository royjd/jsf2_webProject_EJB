/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.SessionBean.getSession;
import java.io.IOException;
import java.util.Arrays;
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

        try {
            request.getSession(false).getAttribute("username").toString();
        } catch (NullPointerException e) {
            this.filterNotConnected(request, response);
        }
        //this.verifyUrl(request,response);
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }

    private void filterNotConnected(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String contextPath = request.getContextPath();
        String pages[] = {
            contextPath + "/faces/message.xhtml",
            contextPath + "/faces/notification.xhtml",
            contextPath + "/faces/home.xhtml",
            contextPath + "/faces/wall.xhtml"
        };

        String s = request.getRequestURI();

        if (s.equals(pages[0]) || 
                s.equals(pages[1]) ||
                s.equals(pages[2]) ||
                (s.equals(pages[3]) && containManagePages(request.getParameter("sp")))){
            response.sendRedirect(request.getContextPath() + "/faces/index.xhtml");
        } 

    }

    private boolean containManagePages(String sp) {
        if (sp == null) {
            return false;
        }

        String sousPages[] = {
            "editProfile",
            "manageExperience",
            "createAlbum"
        };

        return (sp.equals(sousPages[0]) || sp.equals(sousPages[1]) || sp.equals(sousPages[2]));
            
    }

    /*private void verifyUrl(HttpServletRequest request, HttpServletResponse response) throws IOException{
     if(request.getParameter("u")==null || (request.getParameter("sp")!=null && request.getParameter("p")==null)){
     response.sendRedirect(request.getContextPath() + "/faces/index.xhtml");
     }
     }*/
}
