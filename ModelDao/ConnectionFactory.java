package com.mycompany.projetofinalpizzaria.ModelDao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private static Properties properties;

    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException, IOException {
        readProperties();
        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }

    private static void readProperties() throws IOException {
        if (properties == null) {
            Properties props = new Properties();
            
            try (InputStream input = ConnectionFactory.class.getResourceAsStream("/DataBase.properties")) {
                if (input == null) {
                    throw new FileNotFoundException("Arquivo DataBase.properties não encontrado no classpath.");
                }
                props.load(input);
                properties = props;
            }
        }
    }
}