package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.Date;

/**
 * FieldFilter for Dates.
 */
public class DateFieldFilter extends AbstractFieldFilter {

    public DateFieldFilter(String associationPath) {
        super(associationPath);
    }

    public DateFieldFilter(String associationPath, int joinType) {
        super(associationPath, joinType);
    }

    private boolean isValid(Date date) {
        return date != null;
    }

    /**
     * Filters between midnight and midnight of date specified inclusive
     *
     * @param date to filter on
     * @return filter
     */
    public FieldFilter onDate(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.between(getPropertyName(), startOfDay(date), startOfNextDay(date)));
        }
        return this;
    }

    /**
     * Filters to all dates on or after midnight on the date specified
     *
     * @param date to filter on
     * @return filter
     */
    public FieldFilter afterDate(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.ge(getPropertyName(), startOfNextDay(date)));
        }
        return this;
    }

    /**
     * Filters to all dates on or before midnight on the day after the date specified
     *
     * @param date to filter on
     * @return filter
     */
    public FieldFilter beforeDate(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.le(getPropertyName(), startOfDay(date)));
        }
        return this;
    }

    /**
     * Filters between midnight on the start date and midnight of date after later date
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public FieldFilter betweenDates(Date startDate, Date endDate) {
        if (isValid(startDate) && isValid(endDate)) {
            setExpression(Restrictions.between(getPropertyName(), startOfDay(startDate), startOfNextDay(endDate)));
        }
        return this;
    }

    /**
     * Filters to all dates after the date specified
     *
     * @param date to filter on
     * @return filter
     */
    public FieldFilter afterTime(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.ge(getPropertyName(), date));
        }
        return this;
    }

    /**
     * Filters to all dates before the date specified
     *
     * @param date to filter on
     * @return filter
     */
    public FieldFilter beforeTime(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.le(getPropertyName(), date));
        }
        return this;
    }

    /**
     * Filters between start date and end date inclusive
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public FieldFilter betweenTimes(Date startDate, Date endDate) {
        if (isValid(startDate) && isValid(endDate)) {
            setExpression(Restrictions.between(getPropertyName(), startDate, endDate));
        }
        return this;
    }

    private static Date startOfDay(Date date) {
        return trimTimeFromDate(date);
    }

    private static Date startOfNextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return trimTimeFromDate(cal.getTime());
    }


    /**
     * Takes a date and trims the time from it, so that just the date is left.
     *
     * @param sourceDate to trim.
     * @return the date (midnight) on the passed date
     */
    private static Date trimTimeFromDate(Date sourceDate) {
        if (sourceDate == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(sourceDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

}
