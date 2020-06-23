package database;

import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryInsert {

    /**
     * Add a company to the database.
     * @param compName name of company
     * @param compPassword password for company account
     * @throws SQLException
     */
    public synchronized static void addCompany(String compName, String compPassword) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.companies VALUES ('%s','%s');", compName, compPassword));
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
        statement.execute(String.format("INSERT INTO companies.token VALUES ('%s','%s', '%s');", email, token, timeStamp));
    }

    /**
     * Add an employee to the employee table.
     * @param email employees email.
     * @param name employees name.
     * @param password password.
     * @param companyName company name that the employee is with.
     * @throws SQLException
     */
    public synchronized static void addEmployee(String email, String name, String password, String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.employees VALUES ('%s', '%s', '%s', '%s');", email, name, password, companyName));
    }

    /**
     * Adds a company token
     * @param companyName company name.
     * @param token token.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public synchronized static void addCompanyToken(String companyName, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.tokencompany VALUES ('%s','%s', '%s');", companyName, token, timeStamp));
    }

    public synchronized static void addCompanyCreateEmployeeToken(String companyName, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.createemployeetoken VALUES ('%s','%s', '%s');", companyName, token, timeStamp));
    }


}
