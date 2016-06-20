package com.redsea.common;

/**
 * 缓存静态常量
 * @author yeshujun
 */
public class CacheConstants {

	/**
	 * ehcache.xml文件路径
	 */
	public static final String EHCACHE_FILE_PATH= "/resources/datasource/ehcache.xml";
    /**
     * HttpSession 缓存名称，需与ehcache.xml中的名称保持一致
     */
    public static final String HTTP_SESSION_CACHE = "httpSessionCache";
    
    /**
     * 系统常量数据 缓存名称，这些数据来自数据库，设置完毕后一般不轻易变化，如系统参数、系统设置等数据
     */
    public static final String CONSTANTS_DATA_CACHE = "constantsDataCache";
    
    /**
     * Code表数据
     */
    public static final String CONSTANTS_CODE_CACHE = "constantsCodeCache";
 
    /**
     * 首页缓存数据
     */
    public static final String CONSTANTS_HOME_CACHE = "homeCache";
    
    /**
     * 频道推荐缓存数据
     */
    public static final String CONSTANTS_CHANNEE_CACHE = "channelCache";
    
    /**
     *专题缓存数据
     */
    public static final String CONSTANTS_TOPIC_CACHE = "topicCache";

    /**
     * 数据字典在缓存中的key
     */
    public static final String DATA_DICTIONARY_KEY = "dataDictionaryKey";
    
    /**
     * 系统规则在缓存中的key
     */
    public static final String SYS_RULE_SETTING_KEY = "sysRuleSettingKey";
    /**
     * 代码信息表在缓存中的key
     */
    public static final String CODE_TABLE_KEY = "codeTableKey";
    
}
