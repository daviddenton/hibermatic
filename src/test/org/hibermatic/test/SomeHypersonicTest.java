package org.hibermatic.test;

import org.hibernate.classic.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import static java.util.Collections.*;

public class SomeHypersonicTest {

    private InMemoryDatabase database;

    @Before
    public void setUp() throws Exception {
        database = new InMemoryDatabase();
    }

    @Test
    public void test() throws Exception {
        Session session = database.getSessionFactory().openSession();
        session.save(new A(1, "name", singleton(new B(1)), new C(1, "function")));
    }

    @After
    public void tearDown() throws SQLException {
        database.stop();
    }
}   