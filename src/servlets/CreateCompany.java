package servlets;

import core.BCrypt;
import database.SQLQueryDelete;
import database.SQLQueryInsert;
import database.SQLQuerySelect;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;


/**
 * Servlet to add a company to the database.
 * Needs check that the company name doesn't include the character '|'.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 18/06/20
 */
public class CreateCompany extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String name = request.getParameter("companyName");
            String firstPassword = request.getParameter("firstPassword");
            String secondPassword = request.getParameter("secondPassword");
            String compEmail = request.getParameter("email");
            EmailValidator validator = EmailValidator.getInstance();
            if (name == "" || compEmail == "" || firstPassword == "" || secondPassword == "") {
                response.setStatus(475);
                return;
            } else if (!(validator.isValid(compEmail))) {
                response.setStatus(470);
                return;
            } else if (!firstPassword.equals(secondPassword)) {
                response.setStatus(471);
                return;
            } else if (firstPassword.length() < 6) {
                response.setStatus(472);
                return;
            } else if (SQLQuerySelect.doesCompanyEmailExist(compEmail)) {
                response.setStatus(474);
                return;
            }
            String hashedPassword = BCrypt.hashpw(firstPassword, BCrypt.gensalt(12));
            SQLQueryInsert.addCompany(compEmail, name, hashedPassword);
            generateToken(compEmail);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }

    private static void generateToken(String companyEmail) throws SQLException {
        if (SQLQuerySelect.doesCompanyHaveCreateEmployeeToken(companyEmail)) {
            SQLQueryDelete.deleteOldCompanyCreateEmployeeToken(companyEmail);
        }
        String token = UUID.randomUUID().toString();
        LocalDateTime dateTime = LocalDateTime.now();
        dateTime = dateTime.plusWeeks(1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dataTimeStr = dateTimeFormatter.format(dateTime);
        SQLQueryInsert.addCompanyCreateEmployeeToken(companyEmail, token, dataTimeStr);
    }
}
