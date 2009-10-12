package org.hibermatic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

public class SomeHypersonicTest {


    private InMemoryDatabase database;

    @Before
    public void setUp() throws Exception {
        database = new InMemoryDatabase();
    }

    @Test
    public void test() throws Exception {
        database.getSessionFactory().getCurrentSession().save(new Domain.A());
    }

    @After
    public void tearDown() throws SQLException {
        database.stop();
    }
}   