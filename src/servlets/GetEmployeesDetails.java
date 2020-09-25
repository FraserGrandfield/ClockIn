package servlets;

import database.SQLQuerySelect;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import org.json.*;


public class GetEmployeesDetails extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //TODO check email is with the company
        String email = "";
        String type = request.getParameter("type");
        if (type.equals("company")) {
            email = request.getParameter("email");
        } else if (type.equals("employee")){
            email = (String) request.getSession(false).getAttribute("email");
        }
        try {
            float pay = SQLQuerySelect.getEmployeePay(email);
            String firstName = SQLQuerySelect.getEmployeeFirstName(email);
            String secondName = SQLQuerySelect.getEmployeeLastName(email);
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("firstName", firstName);
            json.put("secondName", secondName);
            json.put("pay", pay);
            String out = json.toString();
            PrintWriter writer = response.getWriter();
            writer.print(out);
            writer.flush();
            writer.close();
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
            return;
        }
    }
}
