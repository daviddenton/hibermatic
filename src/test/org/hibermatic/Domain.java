package org.hibermatic;

import java.util.List;

public class Domain {
    public static class A {
        Long aid;
        String name;
        List<B> b;
        C c;
    }

    public static class B {
    Long bid;
}

    public static class C {
    Long cid;
    String function;
}
}
