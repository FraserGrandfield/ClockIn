package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Stores database instance.
 *
 * @author Fraser Grandfield
 * @version  1.0
 * @since 18/06/2020
 */
public class DataBase {

    private final String CONNECTION_URL = "jdbc:mysql://localhost:3306/companies?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String CONNECTION_DRIVER = "com.mysql.cj.jdbc.Driver";
    private final File file = new File("C:\\Users\\fraser\\Documents\\A Documents\\ClockInSens\\ClockInSens.txt");

    private DataBase(){
        try {
            Scanner reader = new Scanner(file);
            String connectionUsername = reader.nextLine();
            String connectionPassword = reader.nextLine();

            Class.forName(CONNECTION_DRIVER);
            Connection connection = DriverManager.getConnection(CONNECTION_URL, connectionUsername, connectionPassword);
        } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static DataBase database = new DataBase();

    /**
     *
     * @return database instance.
     */
    static DataBase getDataBaseInstance() {
        return database;
    }
}
