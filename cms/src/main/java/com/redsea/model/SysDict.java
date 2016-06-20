package com.redsea.model;

import java.util.List;

import com.redsea.model.base.BaseSysDict;


/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class SysDict extends BaseSysDict<SysDict> {
	public static final SysDict dao = new SysDict();
	public List<SysDict> getSysDictListByType(String type){
		String sql="select * from "+getTableName()+" where  type= ?  and del_flag=0 ORDER BY sort DESC,create_time desc";
		return find(sql,type);
	}
}
