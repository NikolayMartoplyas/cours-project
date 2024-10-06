package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final String DB_USERNAME = System.getProperty("db.user");
    private static final String DB_PASSWORD = System.getProperty("db.password");
    private static final String DB_URL = System.getProperty("db.url");

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection, "DELETE FROM credit_request_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM order_entity");
        QUERY_RUNNER.execute(connection, "DELETE FROM payment_entity");
    }

    @SneakyThrows
    public static Integer getPaymentAmount() {
        var conection = getConn();
        var requestSql = "SELECT amount FROM payment_entity ORDER BY created DESC";
        return QUERY_RUNNER.query(conection, requestSql, new ScalarHandler<>());
    }

    @SneakyThrows
    public static String getPaymentStatus() {
        var conection = getConn();
        var requestSql = "SELECT status FROM payment_entity ORDER BY created DESC";
        return QUERY_RUNNER.query(conection, requestSql, new ScalarHandler<>());
    }

}
