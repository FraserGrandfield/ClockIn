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
        PrintWriter writer = response.getWriter();
        try {
            String name = request.getParameter("companyName");
            String firstPassword = request.getParameter("firstPassword");
            String secondPassword = request.getParameter("secondPassword");
            String compEmail = request.getParameter("email");
            EmailValidator validator = EmailValidator.getInstance();
            if (!(validator.isValid(compEmail))) {
                WriteError("Error: Email is invalid.", writer);
                return;
            } else if (!firstPassword.equals(secondPassword)) {
                WriteError("Error: Passwords do not match.", writer);
                return;
            } else if (firstPassword.length() < 6) {
                WriteError("Error: Password is too short.", writer);
                return;
            } else if (SQLQuerySelect.doesCompanyEmailExist(compEmail)) {
                WriteError("Error: Email is already registered.", writer);
                return;
            }
            String hashedPassword = BCrypt.hashpw(firstPassword, BCrypt.gensalt(12));
            SQLQueryInsert.addCompany(compEmail, name, hashedPassword);
            generateToken(compEmail);
            response.sendRedirect("index.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            WriteError("Error: Currently having issues communicating to the server.", writer);
            return;
        }
    }

    private static void WriteError(String error, PrintWriter writer) {
        writer.println("<script type=\"text/javascript\">");
        writer.println("alert('" + error + "');");
        writer.println("location='index.jsp';");
        writer.println("</script>");
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
