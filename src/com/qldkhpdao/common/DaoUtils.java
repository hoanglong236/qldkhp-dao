package com.qldkhpdao.common;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author B1809367
 */
public class DaoUtils {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input = DaoUtils.class.getClassLoader()
                .getResourceAsStream("context.properties")) {

            if (input == null) {
                throw new IOException(
                        "'context.properties' not found in classpath.");
            }
            // Load properties from the context file
            properties.load(input);

            // Load the JDBC driver class
            Class.forName(properties.getProperty("db.driver"));

        } catch (IOException e) {
            throw new ExceptionInInitializerError(
                    "Failed to load 'context.properties': " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(
                    "Failed to load JDBC driver class: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }

    public static void throwDaoException(String msg, Throwable e)
            throws QLDKHPDaoException {

        throw new QLDKHPDaoException(msg + System.lineSeparator()
                + "Cause by: " + e.getMessage(), e);
    }
}
