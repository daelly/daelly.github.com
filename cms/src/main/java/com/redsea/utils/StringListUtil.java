package com.redsea.utils;

import java.util.ArrayList;
import java.util.List;

public class StringListUtil {
	/**
	 * 根据String转化为String集合
	 * strSplitToStringList 方法 
	 * <p>方法说明:</p> 
	 * @param str
	 * @return 
	 * @return List<String> 
	 * @author Tolk 
	 * @date Apr 2, 2013
	 */
	public static List<String> strSplitToStringList(String str){
		if(str != null && !"".equals(str)){
			List<String> list = new ArrayList<String>();
			String[] strList = str.split(",");
				try {
					for(int i =0;i<strList.length;i++){
						list.add(strList[i]);
					}
				} catch (RuntimeException e) {
					return null;
				}
				return list;
			}else{
			return null;
		}
	}
	
	/**
	 * 根据String对象转化为Int集合
	 * strSplitToIntList 方法 
	 * <p>方法说明:</p> 
	 * @param str
	 * @return 
	 * @return List<Integer> 
	 * @author Tolk 
	 * @date Apr 2, 2013
	 */
	public static List<Integer> strSplitToIntList(String str){
		if(str != null && !"".equals(str)){
			List<Integer> list = new ArrayList<Integer>();
			String[] strList = str.split(",");
			try{
				for(int i =0;i<strList.length;i++){
					list.add(Integer.valueOf(strList[i]));
				}
			}catch(NumberFormatException nfe){
				return null;
			}
			return list;
		}else{
			return null;
		}
	}
}
