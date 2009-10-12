package org.hibermatic;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SomeHypersonicTest {


    @Test
    public void test() throws Exception {
        Class.forName("org.hsqldb.jdbcDriver").newInstance();
        Connection c = DriverManager.getConnection("jdbc:hsqldb:mem:aname", "sa", "");

        c.setAutoCommit(true);
        c.prepareStatement("create table TABLE_A ( aid INTEGER, name VARCHAR, cid INTEGER);").execute();
        c.prepareStatement("create table TABLE_B ( bid INTEGER, aid INTEGER);").execute();
        c.prepareStatement("create table TABLE_C ( cid INTEGER, function VARCHAR);").execute();

        c.prepareStatement("insert into TABLE_A values (1,'NAME',1)");
        c.prepareStatement("insert into TABLE_B values (1,1)");
        c.prepareStatement("insert into TABLE_B values (2,1)");
        c.prepareStatement("insert into TABLE_B values (3,2)");
        c.prepareStatement("insert into TABLE_C values (1,'DOOBREY')");

        final ResultSet resultSet = c.createStatement().executeQuery("SELECT * AS CNT FROM TABLE_B");
//        while(!resultSet.isLast()) {
//            System.out.println("SomeHypersonicTest.test");
//            resultSet.next();
//        }
        resultSet.close();

        c.close();
    }
}
