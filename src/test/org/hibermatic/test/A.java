package org.hibermatic.test;

import java.util.Set;

public class A {
    Long aid;
    String name;
    Set<B> b;
    C c;

    public A(long aid, String name, Set<B> b, C c) {
        this.aid = aid;
        this.name = name;
        this.b = b;
        this.c = c;
    }
}
