package servlets;

import core.BCrypt;
import database.SQLQueryInsert;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import org.apache.commons.validator.routines.*;

/**
 * Servlet to create a employee and add them to the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @Since 19/06/20
 */
public class CreateEmployee extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO check pay is a float
        String firstName = request.getParameter("firstName");
        String secondName = request.getParameter("secondName");
        String email = request.getParameter("email");
        String firstPassword = request.getParameter("firstPassword");
        String secondPassword = request.getParameter("secondPassword");
        String pay = request.getParameter("pay");
        String token = request.getParameter("token");
        try {
            EmailValidator validator = EmailValidator.getInstance();
            if (firstName == "" || secondName == "" || email == "" || firstPassword == "" || secondPassword == "" ||
                pay == "" || token == "") {
                response.setStatus(475);
                return;
            } else if (!(validator.isValid(email))) {
                response.setStatus(470);
                return;
            } else if (!firstPassword.equals(secondPassword)) {
                response.setStatus(471);
                return;
            } else if (firstPassword.length() < 6){
                response.setStatus(472);
                return;
            } else if (!SQLQuerySelect.doesEmployeeHaveCreateAccountValidToken(token)) {
                response.setStatus(473);
                return;
            } else if (SQLQuerySelect.isEmailInDatabase(email)) {
                response.setStatus(474);
                return;
            }
            String companyEmail = SQLQuerySelect.getCompanyEmailFromCreateEmployeeToken(token);
            String hashedPassword = BCrypt.hashpw(firstPassword, BCrypt.gensalt(12));
            SQLQueryInsert.addEmployee(email, firstName, secondName, hashedPassword, companyEmail, pay);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
