package servlets;

import database.SQLQuery;

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
 * Create a token for a company
 * @author Fraser Grandfield
 * @version 1.0
 * @since 19/06/20
 */
public class CreateCompanyToken  extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader("authorization");
        String encodedAuth = authHeader.substring(authHeader.indexOf(' ') + 1);
        String companyName = new String(Base64.getDecoder().decode(encodedAuth));

        try {
            if (SQLQuery.doesCompanyHaveToken(companyName)) {
                SQLQuery.deleteOldCompanyToken(companyName);
            }
            String token = generateToken();
            LocalDateTime dateTime = LocalDateTime.now();
            dateTime = dateTime.plusMinutes(20);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataTimeStr = dateTimeFormatter.format(dateTime);
            SQLQuery.addCompanyToken(companyName, token, dataTimeStr);
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