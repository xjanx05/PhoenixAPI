package de.codingphoenix.phoenixapi.other;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class MariaDB {
    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;
    private final HikariConfig hikariConfig;
    private HikariDataSource dataSource;

    public MariaDB(String host, int port, String database, String user, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;
        this.hikariConfig = new HikariConfig();

        this.connect();
        //this.query("CREATE TABLE IF NOT EXISTS tablename(`name` varchar(64), `name2` varchar(64)");
    }

    public void connect() {
        this.hikariConfig.setJdbcUrl("jdbc:mariadb://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true");
        this.hikariConfig.setUsername(this.user);
        this.hikariConfig.setPassword(this.password);
        this.hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        this.hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        this.hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        this.dataSource = new HikariDataSource(this.hikariConfig);
    }

    public void query(final String query) {
        CompletableFuture.runAsync(() -> {
            try {
                Connection connection;
                PreparedStatement preparedStatement;
                connection = this.dataSource.getConnection();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void update(final String table, final String toUpdate, final String value, final String condition1, final String condition2) {
        CompletableFuture.runAsync(() -> {
            Connection connection;
            PreparedStatement preparedStatement;
            try {
                connection = this.dataSource.getConnection();
                preparedStatement = connection.prepareStatement("UPDATE " + table + " SET " + toUpdate + " = ? WHERE " + condition1 + " = ?");
                preparedStatement.setString(1, value);
                preparedStatement.setString(2, condition2);
                preparedStatement.executeUpdate();
                preparedStatement.close();
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getDatabase() {
        return this.database;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }

    public HikariConfig getHikariConfig() {
        return this.hikariConfig;
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }
}
