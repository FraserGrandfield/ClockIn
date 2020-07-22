package servlets;

import database.SQLQueryDelete;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

/**
 * Log a company out.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 04/07/2020
 */
public class LogOutCompany extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession(false);
        session.invalidate();
        response.sendError(HttpServletResponse.SC_OK);
    }
}
