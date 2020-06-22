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
    public static void addCompany(String compName, String compPassword) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.companies VALUES ('%s','%s');", compName, compPassword));
    }

    /**
     * Add an employee token field to the database.
     * @param employeeId employees Id.
     * @param token the token.
     * @param timeStamp current timestamp.
     * @throws SQLException
     */
    public static void addEmployeeToken(int employeeId, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.token VALUES ('%s','%s', '%s');", employeeId, token, timeStamp));
    }

    /**
     * Add an employee to the employee table.
     * @param employeeId employees Id.
     * @param name employees name.
     * @param email employees email.
     * @param password password.
     * @param companyName company name that the employee is with.
     * @throws SQLException
     */
    public static void addEmployee(int employeeId, String name, String email, String password, String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.employees VALUES ('%s', '%s', '%s', '%s', '%s');", employeeId, name, email, password, companyName));
    }

    /**
     * Adds a company token
     * @param companyName company name.
     * @param token token.
     * @param timeStamp timestamp.
     * @throws SQLException
     */
    public static void addCompanyToken(String companyName, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.tokencompany VALUES ('%s','%s', '%s');", companyName, token, timeStamp));
    }

    public static void addCompanyCreateEmployeeToken(String companyName, String token, String timeStamp) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("INSERT INTO companies.createemployeetoken VALUES ('%s','%s', '%s');", companyName, token, timeStamp));
    }


}
