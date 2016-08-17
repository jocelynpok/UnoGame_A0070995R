/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.web;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jocelyn
 */
//public class CORSFilter {
//    @WebFilter(urlPatterns="/*", dispatcherTypes={DispatcherType.REQUEST})
//    public CORSFilter implements Filter{
//    
//    @Override
//    public void init(FilterConfig conf){}
//    
//    @Override
//    public void Destroy(){
//    }
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse resp,FilterChain chain)throws IOException.ServletException{
//        
//    }
//    
//}
//}
@WebFilter(urlPatterns="/*", dispatcherTypes={DispatcherType.REQUEST})
public class CORSFilter implements Filter{
    
   
    
    @Override
    public void init(FilterConfig conf){}
    
   

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //To change body of generated methods, choose Tools | Templates.
        HttpServletResponse httpResp = (HttpServletResponse)response;
        httpResp.addHeader("Access-Control-Allow-Origin","*");
         chain.doFilter(request, response);
         
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

}