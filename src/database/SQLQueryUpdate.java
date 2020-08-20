package database;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL query class for updating the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class SQLQueryUpdate extends SQLQuery{

    /**
     * Update the clock out field in the timestamps table.
     * @param email employee email.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public synchronized static void updateClockOutTimeStamp(String email, String timeStamp, String timestampOut) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE " + databaseName + "." + tableTimeStamps + " SET " + clockIn + " = '%s' WHERE " + timeStampEmployeeEmailFK + " = '%s' AND " + clockOut + " IS NULL;", timeStamp, email));
        statement.execute(String.format("UPDATE " + databaseName + "." + tableTimeStamps + " SET " + clockOut + " = '%s' WHERE " + timeStampEmployeeEmailFK + " = '%s' AND " + clockOut + " IS NULL;", timestampOut, email));
    }

    /**
     * Update employees name and email.
     * @param oldEmail employees old email.
     * @param newEmail employees new email.
     * @param fName employees new first name.
     * @param sName employees new second name.
     * @throws SQLException
     */
    public synchronized static void updateEmployeeDetails(String oldEmail, String newEmail, String fName, String sName, String hourlyPay) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE " + databaseName + "." + tableTimeStamps + " SET " + timeStampEmployeeEmailFK + " = '%s' WHERE " + timeStampEmployeeEmailFK + " = '%s';", newEmail, oldEmail));
        statement.execute(String.format("UPDATE " + databaseName + "." + tableEmployees + " SET " + employeeEmailPK + " = '%s', " + firstName + " = '%s', " + secondName + " = '%s', " + employeeHourlyPay + " = '%s' WHERE " + employeeEmailPK + " = '%s';", newEmail, fName, sName, hourlyPay, oldEmail));
    }

    /**
     * Update employees password.
     * @param email employees email.
     * @param newPassword employees new password.
     * @throws SQLException
     */
    public synchronized static void updateEmployeePassword(String email, String newPassword) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE " + databaseName + "." + tableEmployees + " SET " + employeePassword + " = '%s' WHERE " + employeeEmailPK + " = '%s';", newPassword, email));
    }

    /**
     * Update companies password.
     * @param companyEmail company name.
     * @param newPassword companies new password.
     * @throws SQLException
     */
    public synchronized static void updateCompanyPassword(String companyEmail, String newPassword) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE " + databaseName + "." + tableCompany + " SET " + companyPassword + " = '%s' WHERE " + companyEmailPK + " = '%s';", newPassword, companyEmail));
    }
}
