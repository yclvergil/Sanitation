package com.songming.sanitation.frameset.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 字符串操作工具包
 * 
 * @Version 1.0
 */
public class StringUtilsExt {

	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 将字符串转位日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatStr(String dateStr, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String date = sdf.format(toDate(dateStr));

		return date;

	}

	public static String formatStr(Date date, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		String datestr = sdf.format(date);
		return datestr;
	}

	// 时间戳格式转换字符串格式
	public static String formatTimeStamp(String formatStr, String dateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);

		String date = sdf.format(new Date(Long.parseLong(dateStr)));

		return date;

	}

	public static void main(String[] args) {

		String dd = formatStr("yyyy-MM-dd", "1432955471000");

		System.out.println(dd + "---------dddd----------");

		String beginDate = "1432955471000";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String sd = sdf.format(new Date(1432955471000L));

		System.out.println(sd);

	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = toDate(sdate);
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	public static String timeSec2Str(int sec) {
		// int s=12862;
		int N = sec / 3600;
		sec = sec % 3600;
		int K = sec / 60;
		sec = sec % 60;
		int M = sec;
		return N + "小时 " + K + "分钟 " + M + "秒";
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	public static String toString(String[] strs) {
		StringBuilder sb = new StringBuilder();
		for (String str : strs) {
			sb.append(str);
		}
		return sb.toString();

	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	public static String toConvertString(InputStream is) {
		StringBuffer res = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader read = new BufferedReader(isr);
		try {
			String line;
			line = read.readLine();
			while (line != null) {
				res.append(line);
				line = read.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != isr) {
					isr.close();
					isr.close();
				}
				if (null != read) {
					read.close();
					read = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
			}
		}
		return res.toString();
	}

	public static String getStringNormal(Object obj) {
		if (obj == null) {
			return "";
		}
		return String.valueOf(obj).trim();
	}

	public static String getStringText(Object obj) {
		if (obj == null) {
			return "";
		}
		return String.valueOf(obj).trim();
	}

	public static boolean isMobile(String no) {
		if (no == null || no.trim().length() == 0 || no.trim().length() != 11
				|| !no.trim().startsWith("1"))
			return false;
		else
			return true;
	}

	/**
	 * 过滤表情符及特殊字符
	 * */
	public String emojiFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字和中文//[\\pP‘’“”
		String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
		Pattern p = Pattern.compile(regEx);
		StringBuilder sb = new StringBuilder(str);

		for (int len = str.length(), i = len - 1; i >= 0; --i) {

			if (!p.matches(regEx, String.valueOf(str.charAt(i)))) {
				sb.deleteCharAt(i);
			}
		}

		return sb.toString();
	}

	/**
	 * 判断是否包含表情符及特殊字符
	 * */
	public boolean isEmoji(String str) throws PatternSyntaxException {
		// 只允许字母和数字和中文//[\\pP‘’“”
		String regEx = "^[A-Za-z\\d\\u4E00-\\u9FA5\\p{P}‘’“”]+$";
		Pattern p = Pattern.compile(regEx);
		StringBuilder sb = new StringBuilder(str);

		for (int len = str.length(), i = len - 1; i >= 0; --i) {

			if (!p.matches(regEx, String.valueOf(str.charAt(i)))) {
				return true;
			}
		}

		return false;
	}

	public static String list2str(List<String> source, String separate) {
		if (source == null)
			return null;
		StringBuffer bf = new StringBuffer(source.size());
		for (int i = 0, s = source.size(); i < s; i++) {
			if (i > 0)
				bf.append(separate);
			bf.append(source.get(i));
		}
		return bf.toString();
	}

	public static String[] list2Array(List<String> source) {
		if (source == null)
			return null;
		String[] toBeStored = source.toArray(new String[source.size()]);

		return toBeStored;
	}

	/**
	 * null为空串，否则去掉左右空格
	 * 
	 * */
	public static String null2TrimStr(String source) {
		if (source == null)
			return "";
		return source.trim();
	}

	/**
	 * null为空串，否则去掉所有空格
	 * 
	 * */
	public static String null2TrimAllStr(String source) {
		if (source == null)
			return "";
		return replaceBlank(source);
	}

	public static String replaceBlank(String str) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		return p.matcher(str).replaceAll("");

	}

	public static String set2str(Set<String> source, String separate) {

		return iterator2str(source.iterator(), separate);
	}

	public static String iterator2str(Iterator<String> source, String separate) {
		if (source == null)
			return null;
		StringBuffer bf = new StringBuffer();
		int i = 0;
		for (; source.hasNext();) {
			if (i > 0)
				bf.append(separate);
			bf.append(source.next());
		}
		return bf.toString();
	}

	public static String substring(String src, String fromString,
			String toString) {
		int fromPos = 0;
		if (fromString != null && fromString.length() > 0) {
			fromPos = src.indexOf(fromString);
			fromPos += fromString.length();
		}
		int toPos = src.indexOf(toString, fromPos);
		return src.substring(fromPos, toPos);
	}

	private static final double EARTH_RADIUS = 6378137.0;

	public static double getDistance(double longitude1, double latitude1,
			double longitude2, double latitude2) {
		double Lat1 = rad(latitude1);
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;
		double b = rad(longitude1) - rad(longitude2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(Lat1) * Math.cos(Lat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
}
