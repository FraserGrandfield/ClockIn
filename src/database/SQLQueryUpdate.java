package database;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL query class for updating the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class SQLQueryUpdate {

    /**
     * Update the clock out field in the timestamps table.
     * @param email employee email.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public synchronized static void updateClockOutTimeStamp(String email, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE companies.timestamps SET clockOut = '%s' WHERE email = '%s' AND clockOut IS NULL;", timeStamp, email));
    }

    /**
     * Update employees name and email.
     * @param oldEmail employees old email.
     * @param newEmail employees new email.
     * @param name employees new name.
     * @throws SQLException
     */
    public synchronized static void updateEmployeeDetails(String oldEmail, String newEmail, String name) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE companies.timestamps SET email = '%s' WHERE email = '%s';", newEmail, oldEmail));
        statement.execute(String.format("UPDATE companies.token SET email = '%s' WHERE email = '%s';", newEmail, oldEmail));
        statement.execute(String.format("UPDATE companies.employees SET email = '%s', name = '%s' WHERE email = '%s';", newEmail, name, oldEmail));
    }

    /**
     * Update employees password.
     * @param email employees email.
     * @param newPassword employees new password.
     * @throws SQLException
     */
    public synchronized static void updateEmployeePassword(String email, String newPassword) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("UPDATE companies.employees SET password = '%s' WHERE email = '%s';", newPassword, email));
    }
}
