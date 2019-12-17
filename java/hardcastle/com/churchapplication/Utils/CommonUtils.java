package hardcastle.com.churchapplication.Utils;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

public class CommonUtils {


    public static final String NEW_LINE = "\n";
    public static final String TEXT_SEPERATOR = ",";
    private static final long DAY_IN_MILLI = 24 * 60 * 60 * 1000;

    public static Locale locale = Locale.US;
    private static DateFormatSymbols formatter;

/*    public static Date toTime(String str) {
        if (isEmpty(str)) {
            return null;
        }
        try {
            return new SimpleDateFormat(IConstants.DEFAULT_TIME_FORMAT,
                    getAppLocale()).parse(str);
        } catch (ParseException e) {
            return null;
        }
    }*/

    public static String formatDateForDisplay(Date d, String format) {
        if (d == null) {
            return "";
        }
        return new SimpleDateFormat(format, getAppLocale()).format(d);

    }

    /**
     * @param d
     * @return dd-MMM-yyyy
     */
    public static String formatDateForDisplay(Date d) {
        return formatDateForDisplay(d, "dd-MMM-yyyy");
    }

    /**
     * @param d
     * @return
     */
    public static String formatDateYYYYMM(Date d) {
        return formatDateForDisplay(d, "yyyyMM");
    }

    /**
     * MM-YYYY
     *
     * @param d
     * @return
     */
    public static String formatDateMMYYYY(Date d) {
        return formatDateForDisplay(d, "MM-yyyy");
    }

    public static String formatDateMMMYYYY(Date d) {
        return formatDateForDisplay(d, "MMM yyyy");
    }

    public static String formatDateForBirthdayAnniversary(Date d) {
        Calendar cal2 = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.YEAR, cal2.get(Calendar.YEAR));
        return formatDateForDisplay(cal.getTime(), "dd-MMM-yyyy");
    }

    public static String formatDateForJson(Date d) {
        return formatDateForDisplay(d, "dd-MM-yyyy");
    }

    public static Locale getAppLocale() {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    public static void setAppLocale(Locale newLocale) {
        locale = newLocale;
        formatter = new DateFormatSymbols(locale);
    }
}
