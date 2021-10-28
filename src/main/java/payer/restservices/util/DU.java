package payer.restservices.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import payer.restservices.util.Constants.FormatDates;

public class DU {

	public static Date parse(String dateString, String format) {
		if (format != null && format.equals("dd-MM-yyyy") && dateString != null && !dateString.contains("-")) {
			format = FormatDates.DATEFORMAT;//FormatDates.DATEFORMAT will return "dd/MM/yyyy" in format variable
		}
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(format);
		try {
			
			System.out.println("Parsed Date = "+sdf.parse(dateString));
			
			return sdf.parse(dateString);/*parse() method converts string into date using java.text.SimpleDateFormat 
			class*/
			
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String formatStr(Date date) {
		return format(date, "dd-MM-yyyy");
	}
	
	public static String formatStrYMD(Date date) {
		return format(date, "yyyy-MM-dd");
		//return format(date, "yyyy-dd-MM");
	}

	public static String format(Date date, String format) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat(format);
		
		System.out.println("Formatted date = "+sdf.format(date));
		
		return sdf.format(date);
	}

	public static boolean isToday(Date otherDate) {
		Calendar otherCal = Calendar.getInstance();
		otherCal.setTime(otherDate);
		Calendar nowCal = Calendar.getInstance();

		if (otherCal.get(Calendar.DATE) == nowCal.get(Calendar.DATE)
				&& otherCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH)
				&& otherCal.get(Calendar.YEAR) == nowCal.get(Calendar.YEAR)) {
			return true;
		} else {
			return false;
		}
	}

	public static int getDifferenceDays(Timestamp day1, Timestamp day2) {
		if (day1 == null || day2 == null) {
			return 0;
		}
		int diff = 0;
		diff = (int) ((day1.getTime() - day2.getTime()) / (1000 * 60 * 60 * 24));
		return diff;
	}

	public static int getDifferenceDays(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return 0;
		}
		int diff = 0;
		diff = (int) ((startDate.getTime() - endDate.getTime()) / (1000 * 60 * 60 * 24));
		return diff;
	}

	private static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();

	public static Timestamp now() {
		Calendar zoneCalendar = Calendar.getInstance(DEFAULT_TIME_ZONE);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, zoneCalendar.get(Calendar.DATE));
		calendar.set(Calendar.MONTH, zoneCalendar.get(Calendar.MONTH));
		calendar.set(Calendar.YEAR, zoneCalendar.get(Calendar.YEAR));
		calendar.set(Calendar.HOUR, zoneCalendar.get(Calendar.HOUR));
		calendar.set(Calendar.HOUR_OF_DAY, zoneCalendar.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.AM_PM, zoneCalendar.get(Calendar.AM_PM));
		calendar.set(Calendar.MINUTE, zoneCalendar.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, zoneCalendar.get(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, zoneCalendar.get(Calendar.MILLISECOND));
		return new Timestamp(calendar.getTime().getTime());
	}

	public static Date addMinutes(Date date1, int minBefore) {
		Calendar now = Calendar.getInstance();
		now.setTime(date1);
		now.add(Calendar.MINUTE, minBefore);
		return now.getTime();
	}

	public static Date addDaysAndHours(Date date, int days, int hours) {
		return addDaysAndHours(date, days, hours, DEFAULT_TIME_ZONE);
	}

	public static Date addDaysAndHours(Date date, int days, int hours, TimeZone zone) {
		Calendar newDate = new GregorianCalendar(zone);
		newDate.setTime(date);
		newDate.add(Calendar.DATE, days);
		newDate.add(Calendar.HOUR, hours);
		return new Timestamp(newDate.getTime().getTime());
	}

	public static Date today() {
		Timestamp now = now();
		return now;
	}
	
	public static Date asDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	  }
}
