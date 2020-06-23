package database;

import java.sql.SQLException;
import java.sql.Statement;

public class SQLQueryDelete {

    /**
     * Delete an employee token
     * @param email employees email.
     * @throws SQLException
     */
    public synchronized static void deleteOldEmployeeToken(String email) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.token WHERE email = '%s';", email));
    }

    /**
     * Deletes a companies token from the database.
     * @param companyName company name.
     * @throws SQLException
     */
    public synchronized static void deleteOldCompanyToken(String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.tokencompany WHERE companyname = '%s';", companyName));
    }

    public synchronized static void deleteOldCompanyCreateEmployeeToken(String companyName) throws SQLException {
        Statement statement = DataBase.getConnection().createStatement();
        statement.execute(String.format("DELETE FROM companies.createemployeetoken WHERE companyName = '%s';", companyName));
    }


}
