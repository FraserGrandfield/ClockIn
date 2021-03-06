package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Log a employee out.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 04/07/2020
 */
public class LogOut extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        session.invalidate();
    }

}
