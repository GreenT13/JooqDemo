package com.apon.jooq.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.JDBCUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Context {
    private static final Logger logger = LogManager.getLogger(Context.class.getName());

    private DataSource dataSource;
    private Connection connection;
    private Configuration configuration;

    /**
     * Factory method for creating a new context for testing.
     */
    public static Context createContextToDb() {
        // Initialize connection to the database.
        try {
            JdbcDataSource jdbcDataSource = new JdbcDataSource();
            jdbcDataSource.setURL("jdbc:h2:./JooqDemo-db");
            return new Context(jdbcDataSource);
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong initializing the database.", e);
        }
    }

    private Context(DataSource dataSource) throws SQLException {
        this.dataSource = dataSource;
        connection = dataSource.getConnection();
        configuration = DSL.using(connection, JDBCUtils.dialect(connection)).configuration();
    }

    public boolean commit() {
        try {
            connection.commit();
            return true;
        } catch (SQLException e) {
            logger.error("Could not commit connection.", e);
            return false;
        }
    }

    public boolean rollback() {
        try {
            connection.rollback();
            return true;
        } catch (SQLException e) {
            logger.error("Could not rollback connection.", e);
            return false;
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
}
