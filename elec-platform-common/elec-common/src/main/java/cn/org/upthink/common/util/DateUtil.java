package cn.org.upthink.common.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description: 日期时间工具类
 */
public class DateUtil {

	public static final String FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddhhmmssSSS";
	public static final String FORMAT_YYYYMMDDHHMMSS = "yyyyMMddhhmmss";

	/** 锁对象 */
	private static final Object lockObj = new Object();

	/** 存放不同的日期模板格式的sdf的Map */
	private static ConcurrentMap<String, ThreadLocal<SimpleDateFormat>> sdfMap = new ConcurrentHashMap<String, ThreadLocal<SimpleDateFormat>>();

	/**
	 * 返回一个ThreadLocal的sdf,每个线程只会new一次sdf
	 *
	 * @param pattern
	 * @return
	 */
	private static SimpleDateFormat getSdf(final String pattern) {
		ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
		// 此处的双重判断和同步是为了防止sdfMap这个单例被多次put重复的sdf
		if (tl == null) {
			synchronized (lockObj) {
				tl = sdfMap.get(pattern);
				if (tl == null) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected SimpleDateFormat initialValue() {
							return new SimpleDateFormat(pattern);
						}
					};
					sdfMap.put(pattern, tl);
				}
			}
		}
		return tl.get();
	}

	/**
	 * 是用ThreadLocal<SimpleDateFormat>来获取SimpleDateFormat,这样每个线程只会有一个SimpleDateFormat
	 *
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return getSdf(pattern).format(date);
	}

	public static Date parse(String dateStr, String pattern) throws ParseException {
		return getSdf(pattern).parse(dateStr);
	}

	public static String formatFull(){
		return format(new Date(), DateUtils.DEFAULT_CONVERT_PATTERN);
	}

	public static String getCurrentDate() {
		String formatPattern_Short = "yyyyMMddhhmmss";
		SimpleDateFormat format = new SimpleDateFormat(formatPattern_Short);
		return format.format(new Date());
	}

	public static String getSeqString() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMddHHmmss"); // "yyyyMMdd G
		return fm.format(new Date());
	}

	public static String getNewDate() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd"); // "yyyyMMdd G
		return fm.format(new Date());
	}

	public static Timestamp getCurrentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取当前时间，格式为 yyyyMMddHHmmss
	 *
	 * @return
	 */
	public static String getCurrentTimeStr(String format) {
		format = StringUtils.isBlank(format) ? FORMAT_YYYY_MM_DD_HH_MM_SS : format;
		Date now = new Date();
		return date2Str(now, format);
	}

	public static String date2Str(Date date) {
		return date2Str(date, FORMAT_YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 时间转换成 Date 类型
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if ((format == null) || "".equals(format)) {
			format = FORMAT_YYYY_MM_DD_HH_MM_SS;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (date != null) {
			return sdf.format(date);
		}
		return "";
	}

	/**
	 * 获取批量付款预约时间
	 * @return
	 */
	public static String getRevTime() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		String dateString = new SimpleDateFormat(DateUtil.FORMAT_YYYYMMDDHHMMSS).format(cal.getTime());
		System.out.println(dateString);
		return dateString;
	}

	/**
	 * 时间比较
	 * @param date1
	 * @param date2
	 * @return DATE1>DATE2返回1，DATE1<DATE2返回-1,等于返回0
	 */
	public static int compareDate(String date1, String date2, String format) {
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date dt1 = df.parse(date1);
			Date dt2 = (date2==null || "".equals(date2))?new Date():df.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				return 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}

	/**
	 * 把给定的时间减掉给定的分钟数
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date minusDateByMinute(Date date, int minute) {
		Date newDate = new Date(date.getTime() - (minute * 60 * 1000));
		return newDate;
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*60*1000);
	}

	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = System.currentTimeMillis()-date.getTime();
		return t/(60*1000);
	}


	public static String getOldMonthDate() {
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd"); // "yyyyMMdd G
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, -1);
		Date m = c.getTime();
		String mon = fm.format(m);
		return mon;
	}


	public static void main(String[] args) {

		System.out.println("============>"+getOldMonthDate());

	}

}
