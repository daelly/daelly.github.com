package com.redsea.common;

/**
 * 系统常量
 * @author Rocky
 * email: 464193096@qq.com
 * site:  http://www.hr-soft.cn/
 */
public class Consts {
	public static final String SESSION_USER="session_user";
	/**
	 * 缓存枚举
	 * @author Rocky
	 */
	public static enum CacheName {
		session {
			@Override
			public String get() {
				return this.name();
			}
		}, 
		halfHour {
			@Override
			public String get() {
				return this.name();
			}
		},
		hour {
			@Override
			public String get() {
				return this.name();
			}
		},
		oneDay {
			@Override
			public String get() {
				return this.name();
			}
		},
		_default {
			@Override
			public String get() {
				return this.name().substring(1);
			}
		};
		
		public abstract String get();
	}
	
	// 安装状态
	public static boolean IS_INSTALL = false;
	// 分页大小
	public static final int BLOG_PAGE_SIZE = 8;
	
}
