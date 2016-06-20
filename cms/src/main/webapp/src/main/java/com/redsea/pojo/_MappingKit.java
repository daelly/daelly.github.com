package com.redsea.pojo;

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
		arp.addMapping("sys_code", "id", SysCode.class);
		arp.addMapping("sys_dict", "id", SysDict.class);
		arp.addMapping("sys_user", "userid", SysUser.class);
		arp.addMapping("tbl_article", "id", Article.class);
		arp.addMapping("tbl_article_region", "id", ArticleRegion.class);
		arp.addMapping("tbl_article_tags", "id", ArticleTags.class);
		arp.addMapping("tbl_article_topic", "id", ArticleTopic.class);
		arp.addMapping("tbl_comment", "post_id", Comment.class);
		arp.addMapping("tbl_folder", "id", Folder.class);
		arp.addMapping("tbl_keyword", "id", Keyword.class);
		arp.addMapping("tbl_login", "id", Login.class);
		arp.addMapping("tbl_people", "id", People.class);
		arp.addMapping("tbl_region_code", "region_code", RegionCode.class);
		arp.addMapping("tbl_region_url", "id", RegionUrl.class);
		arp.addMapping("tbl_spider_job", "id", SpiderJob.class);
		arp.addMapping("tbl_spider_rule", "id", SpiderRule.class);
		arp.addMapping("tbl_tags", "id", Tags.class);
		arp.addMapping("tbl_topic", "id", Topic.class);
	}
}

