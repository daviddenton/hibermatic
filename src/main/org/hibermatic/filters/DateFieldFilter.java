package org.hibermatic.filters;

import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.Date;

public class DateFieldFilter extends AbstractFieldFilter {

    public DateFieldFilter(String associationPath) {
        super(associationPath);
    }

    private boolean isValid(Date date) {
        return date != null;
    }

    public DateFieldFilter onDate(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.between(getPropertyName(), startOfDay(date), startOfNextDay(date)));
        }
        return this;
    }

    public DateFieldFilter afterDate(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.ge(getPropertyName(), startOfNextDay(date)));
        }
        return this;
    }

    public DateFieldFilter beforeDate(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.le(getPropertyName(), startOfDay(date)));
        }
        return this;
    }

    public DateFieldFilter betweenDates(Date startDate, Date endDate) {
        if (isValid(startDate) && isValid(endDate)) {
            setExpression(Restrictions.between(getPropertyName(), startOfDay(startDate), startOfNextDay(endDate)));
        }
        return this;
    }

    public DateFieldFilter afterTime(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.ge(getPropertyName(), date));
        }
        return this;
    }

    public DateFieldFilter beforeTime(Date date) {
        if (isValid(date)) {
            setExpression(Restrictions.le(getPropertyName(), date));
        }
        return this;
    }

    public DateFieldFilter betweenTimes(Date startDate, Date endDate) {
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
    public static Date trimTimeFromDate(Date sourceDate) {
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
