package vtp.crm.common.utils.common;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import vtp.crm.common.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

	public static Date getCurrentTime() {
		return new Date();
	}

	public static Date getToday() {
		return DateUtils.truncate(getCurrentTime(), Calendar.DATE);
	}

	public static Date getCurrentTimeOnlyHour() {
		return DateUtils.truncate(getCurrentTime(), Calendar.HOUR_OF_DAY);
	}

	public static final Long getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	public static final String getIsoMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.ISO_MONTH_SDF_PATTERN).format(date);
	}

	public static final String getMonth(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.MONTH_SDF_PATTERN).format(date);
	}

	public static final String getDay(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.DAY_SDF_PATTERN).format(date);
	}

	public static final String getIsoDate(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.ISO_DATE_SDF_PATTERN).format(date);
	}

	public static final String getIsoTime(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.ISO_TIME_SDF_PATTERN).format(date);
	}

	public static final String getDateTimeForCdr(Date date) {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.FORMAT_DATE_CDR).format(date);
	}

	public static final Date getDateFromIsoDate(String isoDate) throws ParseException {
		if (isoDate == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.ISO_DATE_SDF_PATTERN).parse(isoDate);
	}

	public static final Date getFirstDateFromIsoDate(Date isoDate) {
		if (isoDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(isoDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static final Date getLastDateFromIsoDate(Date isoDate) {
		if (isoDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(isoDate);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}

	public static final Date getFirstDateOfYearFromIsoDate(Date isoDate) {
		if (isoDate == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(isoDate);
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMinimum(Calendar.DAY_OF_YEAR));
		return cal.getTime();
	}

	public static final Date getDateFromIsoTime(String isoTime) throws ParseException {
		if (isoTime == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.ISO_TIME_SDF_PATTERN).parse(isoTime);
	}

	public static final Date getDateFromIsoTimeTz(String isoTimeTz) throws ParseException {
		if (isoTimeTz == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.ISO_TIME_TZ_SDF_PATTERN).parse(isoTimeTz);
	}

	public static int getField(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		return calendar.get(field);
	}

	public static Date getDate(int year, int month, int date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, date, 0, 0, 0);

		return calendar.getTime();
	}

	public static boolean isSameDay(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
				&& cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
	}

	public static long getDiffDays(Date date1, Date date2) {
		long diff = date2.getTime() - date1.getTime();
		return diff / (24 * 60 * 60 * 1000);
	}

	public static Date getStartOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getBirthdayThisYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		Calendar calendarCurrentDate = Calendar.getInstance();
		calendarCurrentDate.setTime(new Date());
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendarCurrentDate.get(Calendar.YEAR));
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date getEndOfHour(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Date getEndStartHour(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	public static String formatDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, Constants.FORMAT_DATE_TIME);
	}

	public static String formatDateTimeForMobileApp(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, Constants.FORMAT_DATE_TIME_MOBILE);
	}

    public static String formatDateTimeForMobileApp(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_TIME_MOBILE).format(dateTime);
    }

	public static final Date parseDateTimeForMobileApp(String date) throws ParseException {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.FORMAT_DATE_TIME_MOBILE).parse(date);
	}

    public static LocalDateTime parseLocalDateTimeForMobileApp(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_TIME_MOBILE));
    }

	public static String formatDateTimeForReportHour(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, Constants.FORMAT_DATE_TIME_REPORT);
	}

	public static String formatDateTimeForMail(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, Constants.FORMAT_DATE_TIME_MAIL);
	}

	public static final Date parseDateForMobileApp(String date) throws ParseException {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.FORMAT_DATE_MOBILE).parse(date);
	}

    public static LocalDate parseLocalDateForMobileApp(String date) {
        if (date == null) {
            return null;
        }

        return LocalDate.parse(date, DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_MOBILE));
    }

    public static String formatDateForMobileApp(Date date) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date, Constants.FORMAT_DATE_MOBILE);
    }

    public static String formatDateForMobileApp(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return DateTimeFormatter.ofPattern(Constants.FORMAT_DATE_MOBILE).format(date);
    }

	public static String formatDateToString(Date date) {
		if (date == null) {
			return "";
		}
		return DateFormatUtils.format(date, Constants.FORMAT_DATE_MOBILE);
	}

	public static final Date parseDateTimeForImport(String date) throws ParseException {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.FORMAT_DATE_TIME_REPORT).parse(date);
	}

	public static final Date parseDateImport(String date) throws ParseException {
		if (date == null) {
			return null;
		}

		return new SimpleDateFormat(Constants.FORMAT_DATE_IMPORT).parse(date);
	}

	public static final Boolean isDateBetween(Date min, Date max, Date date) {
		if (min == null) {
			return max == null || date.compareTo(max) < 0;
		} else if (max == null) {
			return min.compareTo(date) < 0;
		} else {
			return min.compareTo(date) * date.compareTo(max) > 0;
		}
	}

	public static boolean isMatchingPattern(String input, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setLenient(false); // Set leniency to false for strict parsing
		try {
			dateFormat.parse(input);
			return true; // Parsing succeeded, input matches the pattern
		} catch (ParseException e) {
			return false; // Parsing failed, input does not match the pattern
		}
	}

}
