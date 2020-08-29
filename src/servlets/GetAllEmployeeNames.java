package servlets;

import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gets the names of all employees in a company.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 29/08/2020
 */
public class GetAllEmployeeNames extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        try {
            ArrayList<String> employeeNames = SQLQuerySelect.getEmployeeNames(email);
            PrintWriter writer = response.getWriter();
            writer.print(employeeNames);
            writer.flush();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}