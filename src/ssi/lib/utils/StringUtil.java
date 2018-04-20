package ssi.lib.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Shin Sung In
 *
 */
public class StringUtil {
	
	public static String stackTrace(Throwable e) {
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		return errors.toString();
	}
	
	/**
	 * @param str
	 * @return str�� null�̸� ""�� ġȯ�ؼ� ����
	 */
	public static String nvl(String str) {
		return str == null ? "" : str;
	}
}
