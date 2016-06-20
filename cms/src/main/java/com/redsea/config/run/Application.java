package com.redsea.config.run;

import com.jfinal.core.JFinal;

/**
 * 开发模式启动项目
 * @author Rocky
 * email: 464193096@qq.com
 * site:http://www.hr-soft.cn/
 * @date 2015年7月8日下午9:24:49
 */
public class Application {

	// 项目启动
	public static void main(String[] args) {
		String userDir = System.getProperty("user.dir");
		// 适应测试阶段的jetty，日志写入目录，tomcat写入tomcat/logs下
		String catalinaBase = System.getProperty("catalina.base", userDir);
		System.setProperty("catalina.base", catalinaBase);

		JFinal.start("src/main/webapp", 8082, "/", 10);
	}

}