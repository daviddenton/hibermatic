package org.hibermatic;

import java.util.List;

public class Domain {

    public static class A {
        int aid;
        String name;
        List<B> b;
        int cid;
    }

    public static class B {
        int bid;
    }

    public static class C {
        int cid;
        String function;
    }
}
