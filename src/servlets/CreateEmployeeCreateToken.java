package servlets;

import database.SQLQueryDelete;
import database.SQLQueryInsert;
import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

/**
 * Creates a token for the employee to use to create an account with the company.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 21/06/20
 */
public class CreateEmployeeCreateToken extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO make this a method in create company servlet
        try {
            HttpSession session = request.getSession(false);
            String companyEmail = (String) session.getAttribute("email");
            if (SQLQuerySelect.doesCompanyHaveCreateEmployeeToken(companyEmail)) {
                SQLQueryDelete.deleteOldCompanyCreateEmployeeToken(companyEmail);
            }
            String token = generateToken();
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.plusWeeks(1);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataTimeStr = dateTimeFormatter.format(dateTime);
            SQLQueryInsert.addCompanyCreateEmployeeToken(companyEmail, token, dataTimeStr);
            PrintWriter out = response.getWriter();
            out.println(token);
            out.close();
            out.flush();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
        response.sendError(HttpServletResponse.SC_OK);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

}
