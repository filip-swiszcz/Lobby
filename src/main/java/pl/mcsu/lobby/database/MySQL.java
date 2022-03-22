package pl.mcsu.lobby.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import pl.mcsu.lobby.Main;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    private final HikariDataSource hikariDataSource = new HikariDataSource(getHikariConfig());

    private HikariConfig getHikariConfig() {
        String name = Main.getInstance().getConfig().getString("Database.name");
        String user = Main.getInstance().getConfig().getString("Database.user");
        String password = Main.getInstance().getConfig().getString("Database.password");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/" + name);
        //hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:8889/" + name);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        return hikariConfig;
    }

    private java.sql.Connection connection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private void close(java.sql.Connection connection) throws SQLException {
        connection.close();
    }

    public void execute(String sql, Object[] objects) throws SQLException {
        java.sql.Connection connection = connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = 1;
        for (Object object : objects) {
            preparedStatement.setObject(i, object);
            i++;
        }
        preparedStatement.executeUpdate();
        close(connection);
    }

    public CachedRowSet select(String sql, Object[] objects) throws SQLException {
        java.sql.Connection connection = connection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int i = 1;
        for (Object object : objects) {
            preparedStatement.setObject(i, object);
            i++;
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        RowSetFactory rowSetFactory = RowSetProvider.newFactory();
        CachedRowSet cachedRowSet = rowSetFactory.createCachedRowSet();
        cachedRowSet.populate(resultSet);
        close(connection);
        return cachedRowSet;
    }

}
