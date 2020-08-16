package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationFilter implements Filter{


    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();
        //TODO log this properly
        System.out.println("Requested Resource::"+uri);

        HttpSession session = request.getSession(false);

        //TODO Change to actual login page name and check for create employee as well
        //Have array of the urls and just check if it
        if(session == null && !(uri.endsWith("createcompany") || uri.endsWith("createemployee") || uri.endsWith("employeecheckpassword") || uri.endsWith("companycheckpassword") || uri.endsWith("index.jsp"))){
            System.out.println("Unauthorized access request");
            response.sendRedirect("index.jsp");
        }else{
            chain.doFilter(request, response);
        }

    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
    }
}
