package org.daelly.oj.tags;

/**
 * @author badqiu
 */
class Utils {
	
	public static String OVERRIDE = "__jsp_override__";
	
	public static String APPEND = "__jsp_append__";
	
	static String getOverrideVariableName(String name) {
		return OVERRIDE + name;
	}
	
	static String getAppendedVariableName(String name) {
		return APPEND + name;
	}
	
}
