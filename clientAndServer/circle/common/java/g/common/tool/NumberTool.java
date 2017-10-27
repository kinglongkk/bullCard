/*************************************************************
 * File: NumberTool.java
 * Copyright (c) 2014 860613.com
 * Author: Huang Shaobin
 * Email: Shaobin.Software@gmail.com Shaobin_Software@163.com 
 * Date: 2017年3月29日
 * Description: 
 ************************************************************/

package g.common.tool;

/**
 * g.common.tool.NumberTool
 * 
 * @author Shaobin.Software@gmail.com Shaobin_Software@163.com
 * @version 1.0
 */
public class NumberTool {

	private static String doFormat(String source, String replace) {
		return new StringBuilder(source.length()).append(source).replace(source.length() - replace.length(), source.length(), replace).toString();
	}

	public static String format(byte byteval) {
		return NumberTool.doFormat("00", Integer.toHexString(byteval >= 0 ? byteval : 256 + byteval));
	}

	public static String format(int intval) {
		return NumberTool.doFormat("00000000", Integer.toHexString(intval));
	}

	public static String format(long longval) {
		return NumberTool.doFormat("0000000000000000", Long.toHexString(longval));
	}

	public static String format(short shortval) {
		return NumberTool.doFormat("0000", Integer.toHexString(shortval >= 0 ? shortval : Math.abs(Short.MIN_VALUE) * 2 + shortval));
	}

}
