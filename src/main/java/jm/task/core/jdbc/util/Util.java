package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class Util {
    public static Connection getConnection() throws SQLException, IOException {

        String url = "jdbc:mysql://localhost:3306/pre_project_db";
        String username = "root";
        String password = "ivan2002";

        return DriverManager.getConnection(url, username, password);
    }
}
