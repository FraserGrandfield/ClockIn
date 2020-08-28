package servlets;

import database.SQLQuerySelect;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GetShifts extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        String email= (String) session.getAttribute("email");
        String firstDate = request.getParameter("firstDate");
        String secondDate = request.getParameter("secondDate");
        firstDate += " 00:00";
        secondDate += " 00:00";
        response.setContentType("text/plain;charset=UTF-8");
        try {
            ArrayList<ArrayList<String>> list = SQLQuerySelect.getShifts(email, firstDate, secondDate);
            float pay = SQLQuerySelect.getEmployeePay(email);
            DecimalFormat df = new DecimalFormat(".##");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            float total = 0;
            for (int i = 0; i < list.size(); i++) {
                LocalDateTime dateTime1 = LocalDateTime.parse(list.get(i).get(0), dtf);
                LocalDateTime dateTime2 = LocalDateTime.parse(list.get(i).get(1), dtf);
                Duration dur = Duration.between(dateTime1, dateTime2);
                float difference = dur.toHours();
                total += difference * pay;
            }
            total = Float.parseFloat(df.format(total));
            String out = "";
            for (int i = 0; i < list.size(); i++) {
                out += list.get(i).get(0) + " ";
                out += list.get(i).get(1) + "\n";
            }
            out += "Pay: " + total;
            PrintWriter writer = response.getWriter();
            System.out.println(out);
            writer.print(out);
            writer.flush();
            writer.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
