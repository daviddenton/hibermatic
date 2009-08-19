package org.hibermatic.filters;

import java.util.Date;

/**
 * Preconfigured DateFieldFilter Factories for common search types. See DateFieldFilter for details on each factory method.
 */
public abstract class DateSearch {
    public abstract FieldFilter toFilter(String associationPath);

    private DateSearch() {
    }

    public static DateSearch onDate(final Date thresholdDate) {
        return new DateSearch() {
            public FieldFilter toFilter(String associationPath) {
                return new DateFieldFilter(associationPath).onDate(thresholdDate);
            }
        };
    }

    public static DateSearch beforeDate(final Date thresholdDate) {
        return new DateSearch() {
            public FieldFilter toFilter(String associationPath) {
                return new DateFieldFilter(associationPath).beforeDate(thresholdDate);
            }
        };
    }

    public static DateSearch beforeTime(final Date thresholdDate) {
        return new DateSearch() {
            public FieldFilter toFilter(String associationPath) {
                return new DateFieldFilter(associationPath).beforeTime(thresholdDate);
            }
        };
    }

    public static DateSearch afterTime(final Date thresholdDate) {
        return new DateSearch() {
            public FieldFilter toFilter(String associationPath) {
                return new DateFieldFilter(associationPath).afterTime(thresholdDate);
            }
        };
    }

    public static DateSearch afterDate(final Date thresholdDate) {
        return new DateSearch() {
            public FieldFilter toFilter(String associationPath) {
                return new DateFieldFilter(associationPath).afterDate(thresholdDate);
            }
        };
    }

    public static DateSearch betweenDates(final Date startDate, final Date endDate) {
        return new DateSearch() {
            public FieldFilter toFilter(String associationPath) {
                return new DateFieldFilter(associationPath).betweenDates(startDate, endDate);
            }
        };
    }

    public static DateSearch betweenTimes(final Date startDate, final Date endDate) {
        return new DateSearch() {
            public FieldFilter toFilter(String associationPath) {
                return new DateFieldFilter(associationPath).betweenTimes(startDate, endDate);
            }
        };
    }

}
