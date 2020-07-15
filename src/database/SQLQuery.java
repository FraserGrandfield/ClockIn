package database;

public class SQLQuery {

    //Database name
    protected static String databaseName = "companies";
    //Table names
    protected static String tableCompany = "company";
    protected static String tableCreateEmployeeToken = "createemployeetoken";
    protected static String tableEmployees = "employees";
    protected static String tableTimeStamps = "timestamps";
    protected static String tableToken = "token";
    protected static String tableTokenCompany = "tokencompany";
    //Table Company
    protected static String companyEmailPK = "companyEmail";
    protected static String companyName = "companyName";
    protected static String companyPassword = "companyPassword";
    //Table createemployeetoken
    protected static String createEmployeeCompanyEmailPKFK = "companyEmail";
    protected static String createEmployeeToken = "token";
    protected static String createEmployeeTokenTimeStamp = "timestamp";
    //Table employees
    protected static String employeeEmailPK = "email";
    protected static String firstName = "firstName";
    protected static String secondName = "secondName";
    protected static String employeePassword = "password";
    protected static String employeeCompanyEmail = "companyEmail";
    protected static String employeeHourlyPay = "hourlyPay";
    //Table timestamps
    protected static String timestampIDPK = "timestampID";
    protected static String timeStampEmployeeEmailFK = "email";
    protected static String clockIn = "clockIn";
    protected static String clockOut = "clockOut";
    //Table token
    protected static String tokenEmployeeEmailPKFK = "email";
    protected static String employeeToken = "token";
    protected static String employeeTokenTimestamp = "timestamp";
    //Table tokencompany
    protected static String tokenCompanyEmailPKFK = "companyEmail";
    protected static String companyToken = "token";
    protected static String companyTimeStamp = "timestamp";
}
