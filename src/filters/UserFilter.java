package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(false);
        if(session == null) {
            filterChain.doFilter(request, response);
        } else {
            String userNo = (String) session.getAttribute("user");
            System.out.println(userNo);
            if (userNo.equals("1") && (uri.endsWith("home.jsp") || uri.endsWith("Calendar.jsp"))) {
                System.out.println("Unauthorized access request");
                response.sendRedirect("DashBoard.jsp");
            } else if (userNo.equals("0") && (uri.endsWith("DashBoard.jsp") || uri.endsWith("Employees.jsp"))){
                System.out.println("Unauthorized access request");
                response.sendRedirect("home.jsp");
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
