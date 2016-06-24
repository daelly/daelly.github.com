package org.daelly.oj.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapUtils {

	public static Object getValue(Map<Object,Object> map,Object key){
		Object obj = map.get(key);
		if(obj == null)
			return obj;
		if(obj.getClass().isArray()){
			Object[] objs = (Object[]) obj;
			if(objs.length == 1)
				return objs[0];
			else{
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < objs.length; i++) {
					if(i > 0)
						sb.append(",");
					sb.append(objs[i]);
				}
				return sb.toString();
			}
		}
		return obj;
	}
	
	public static String getValue(Map<String,String[]> map,String key){
		String[] value = map.get(key);
		if(value == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < value.length; i++) {
			if(i > 0)
				sb.append(",");
			sb.append(value[i]);
		}
		return sb.toString();
	}
	
	public static Map<String,String> getSampleMap(Map<String,String[]> map){
		Set<String> keySet = map.keySet();
		Map<String,String> resMap = new HashMap<String, String>();
		for (String key : keySet) {
			String value = (String) getValue(map, key);
			resMap.put(key, value);
		}
		return resMap;
	}
}
