package database;

import java.sql.SQLException;
import java.sql.Statement;

/**
 * SQL query class for inserting into the database.
 * @author Fraser Grandfield
 * @version 1.0
 * @since 24/06/20
 */
public class SQLQueryInsert extends SQLQuery {

    /**
     * Add a company to the database.
     * @param companyEmail email of company
     * @param compPassword password for company account
     * @throws SQLException
     */
    public synchronized static void addCompany(String companyEmail, String companyName, String compPassword) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO " + databaseName + "." + tableCompany + " VALUES ('%s', '%s', '%s');", companyEmail, companyName, compPassword));
    }

    /**
     * Add an employee token field to the database.
     * @param email employees Id.
     * @param token the token.
     * @param timeStamp current timestamp.
     * @throws SQLException
     */
    public synchronized static void addEmployeeToken(String email, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO " + databaseName + "." + tableToken + " VALUES ('%s', '%s', '%s');", email, token, timeStamp));
    }

    /**
     * Add an employee to the employee table.
     * @param email employees email.
     * @param firstName employees first name.
     * @param secondName employees second name.
     * @param password password.
     * @param companyEmail company name that the employee is with.
     * @throws SQLException
     */
    public synchronized static void addEmployee(String email, String firstName, String secondName, String password, String companyEmail, String hourlyPay) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO " + databaseName + "." + tableEmployees + " VALUES ('%s', '%s', '%s', '%s', '%s', '%s');", email, firstName, secondName, password, companyEmail, hourlyPay));
    }

    /**
     * Adds a company token.
     * @param companyEmail company name.
     * @param token token.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public synchronized static void addCompanyToken(String companyEmail, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO " + databaseName + "." + tableTokenCompany + " VALUES ('%s','%s', '%s');", companyEmail, token, timeStamp));
    }

    /**
     * Adds token that employees use to create an account.
     * @param companyEmail company name.
     * @param token token.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public synchronized static void addCompanyCreateEmployeeToken(String companyEmail, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO " + databaseName + "." + tableCreateEmployeeToken + " VALUES ('%s','%s', '%s');", companyEmail, token, timeStamp));
    }

    /**
     * Adds a clock in time stamp to the timestamp table.
     * @param timeStampId time stamp Id.
     * @param email email.
     * @param timeStamp time stamp.
     * @throws SQLException
     */
    public synchronized static void addClockInTimeStamp(String timeStampId, String email, String timeStamp) throws SQLException{
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO " + databaseName + "." + tableTimeStamps + " VALUES ('%s', '%s', '%s', %s);", timeStampId, email, timeStamp, null));
    }
}
