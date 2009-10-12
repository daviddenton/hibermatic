package org.hibermatic;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class InMemoryDatabase {

    private Connection connection;

    public InMemoryDatabase() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        connection = DriverManager.getConnection("jdbc:hsqldb:mem:hibermatic", "sa", "");

        connection.prepareStatement("create table TABLE_A ( aid INTEGER, name VARCHAR, cid INTEGER);").execute();
        connection.prepareStatement("create table TABLE_B ( bid INTEGER, aid INTEGER);").execute();
        connection.prepareStatement("create table TABLE_C ( cid INTEGER, function VARCHAR);").execute();

        connection.prepareStatement("insert into TABLE_A values (1,'NAME',1)");
        connection.prepareStatement("insert into TABLE_B values (1,1)");
        connection.prepareStatement("insert into TABLE_B values (2,1)");
        connection.prepareStatement("insert into TABLE_B values (3,2)");
        connection.prepareStatement("insert into TABLE_C values (1,'DOOBREY')");
        connection.commit();
    }

    public void stop() throws SQLException {
        connection.close();
    }

    public SessionFactory getSessionFactory() {
        return new Configuration().
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
                setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
                setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:hibermatic").
                setProperty("hibernate.connection.username", "sa").
                setProperty("hibernate.connection.password", "").
                setProperty("hibernate.connection.pool_size", "1").
                setProperty("hibernate.connection.autocommit", "true").
                setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
                setProperty("hibernate.hbm2ddl.auto", "create-drop").
                setProperty("hibernate.show_sql", "true").
                addClass(Domain.A.class).
                addClass(Domain.B.class).
                addClass(Domain.C.class).
                configure().
                buildSessionFactory();
    }
}
