package com.redsea.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("sys_user", "userid", SysUser.class);
		arp.addMapping("tbl_login", "id", Login.class);
		arp.addMapping("tbl_people", People.class);
		arp.addMapping("tbl_folder", "id", Folder.class);
		arp.addMapping("tbl_article", "id", Article.class);
		arp.addMapping("sys_dict",SysDict.class);
		arp.addMapping("sys_code",SysCode.class);
		arp.addMapping("tbl_region_code",RegionCode.class);
		arp.addMapping("tbl_region_url",RegionUrl.class);
		arp.addMapping("tbl_tags",Tags.class);
		arp.addMapping("tbl_topic", "id", Topic.class);
		arp.addMapping("tbl_article_tags",ArticleTags.class);
		arp.addMapping("tbl_comment", Comment.class);
		arp.addMapping("tbl_spider_rule", SpiderRule.class);
		arp.addMapping("tbl_keyword", Keyword.class);
		arp.addMapping("tbl_spider_job", SpiderJob.class);
	}
}

