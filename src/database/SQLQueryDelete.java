package database;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL query class for deleting from the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class SQLQueryDelete extends SQLQuery {

    /**
     * Delete a token that employees use to create accounts with.
     * @param companyEmail company email.
     * @throws SQLException
     */
    public synchronized static void deleteOldCompanyCreateEmployeeToken(String companyEmail) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM " + databaseName + "." + tableCreateEmployeeToken + " WHERE " +
                createEmployeeCompanyEmailPKFK + " = '%s';", companyEmail));
    }

    /**
     * Delete an employee from the database.
     * @param email employee email.
     * @throws SQLException
     */
    public synchronized static void deleteEmployee(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM " + databaseName + "." + tableTimeStamps + " WHERE " +
                timeStampEmployeeEmailFK + " = '%s';", email));
        statement.execute(String.format("DELETE FROM " + databaseName + "." + tableEmployees + " WHERE " +
                employeeEmailPK + " = '%s';", email));
    }

    /**
     * Delete a the latest clock in time.
     * @param email employee email
     * @throws SQLException
     */
    public synchronized static void deleteClockIn(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM " + databaseName + "." + tableTimeStamps + " WHERE " +
                employeeEmailPK + " = '%s' AND "+ clockOut + " IS NULL;", email));
    }
}
