package org.hibermatic.filters;

import org.hibernate.Criteria;

import java.util.List;

interface SearchExecutor {
    List search(Criteria criteria);
}
