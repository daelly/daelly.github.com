package com.redsea.config.run;

import javax.servlet.http.HttpServletRequest;

import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.ModelRecordElResolver;
import com.jfinal.plugin.activerecord.SqlReporter;
import com.jfinal.plugin.activerecord.Sqls;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.druid.IDruidStatViewAuth;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.upload.OreillyCos;
import com.redsea.common.Consts;
import com.redsea.config.routes.ApiRoutes;
import com.redsea.config.routes.SystemRoutes;
import com.redsea.config.routes.WebRoutes;
import com.redsea.ext.UpFileRenamePolicy;
import com.redsea.ext.handler.FakeStaticHandler;
import com.redsea.ext.plugin.log.Slf4jLogFactory;
import com.redsea.ext.plugin.quartz.QuartzPlugin;
import com.redsea.ext.plugin.sqlinxml.SqlInXmlPlugin;
import com.redsea.model._MappingKit;

/**
 * 项目主要配置部分
 * @author Rocky
 * email: 464193096@qq.com
 * site:  http://www.hr-soft.cn/
 */
public class WebConfig extends JFinalConfig {

	private boolean isDev = false;
	 private boolean isLocal() {
	        String osName = System.getProperty("os.name");
	        return osName.indexOf("Windows") != -1;
	    }
	/**
	 * 常量配置
	 */
	@Override
	public void configConstant(Constants me) {	
	String path=PathKit.getWebRootPath();
		if(isLocal()){
			PropKit.use("config.txt"); 
		}else{
			if(path.indexOf("8500")>0){
				PropKit.use("config_8500.txt"); 
			}else if(path.indexOf("8600")>0){
				PropKit.use("config_8600.txt"); 
			}else{
				PropKit.use("config.txt"); 
			}
		}
		isDev = PropKit.getBoolean("isdev", false);
		// 开发模式
		me.setDevMode(isDev);
		// 设置Slf4日志
		me.setLogFactory(new Slf4jLogFactory());
		// 使用jsp
		me.setViewType(ViewType.JSP);
		me.setBaseViewPath("/WEB-INF/view");
		me.setUrlParaSeparator("-");
		// 401,403,404,500错误代码处理
		me.setError401View("/WEB-INF/view/error/404.jsp");
		me.setError403View("/WEB-INF/view/error/404.jsp");
		me.setError404View("/WEB-INF/view/error/404.jsp");
		me.setError500View("/WEB-INF/view/error/500.jsp");
	
		me.setBaseUploadPath(PropKit.get("upload_path"));
	}

	/**
	 * 路由配置
	 */
	@Override
	public void configRoute(Routes me) {
		me.add(new WebRoutes());
		me.add(new ApiRoutes());
		me.add(new SystemRoutes());
	}

	/**
	 * 全局拦截器
	 */
	@Override
	public void configInterceptor(Interceptors me) {
		//me.add(new AdminInterceptor());
		//me.add(new TxByMethods("save", "update", "delete"));
		//me.add(new TxByMethodRegex("(.*save.*|.*update.*|.*delete.*)"));
	}

	/**
	 * 配置处理器
	 */
	@Override
	public void configHandler(Handlers me) {
		if (isDev) {
			//me.add(new RenderingTimeHandler());
		}
		//me.add(new SessionIdHandler());
		//me.add(new URLOptimizeHandler());
		//me.add(new XmlHandler());
		// Druid监控
		DruidStatViewHandler druidViewHandler = new DruidStatViewHandler("/admin/druid", new IDruidStatViewAuth() {
			public boolean isPermitted(HttpServletRequest request) {
				if(request.getSession().getAttribute(Consts.SESSION_USER)!=null){
					return true;
				}else{
					return false;
				}
			}
		});
		me.add(druidViewHandler);
		me.add(new FakeStaticHandler());
		//me.add(new XssHandler("/admin")); // `/admin*`为排除的目录
	}
	
	/**
	 * 配置插件
	 */
	@Override
	public void configPlugin(Plugins me) {
		// 数据库信息
		String jdbcUrl  =PropKit.get("db.jdbcUrl");
		String user	 = PropKit.get("db.user");
		String password = PropKit.get("db.password");
		
		// 配置Druid数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, user, password);
		druidPlugin.setFilters("stat,wall");
		me.add(druidPlugin);

		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin).setShowSql(isDev);
		_MappingKit.mapping(arp);
		me.add(arp);

		SqlReporter.setLog(isDev);

		// EhCache
		me.add(new EhCachePlugin());
		
		me.add(new SqlInXmlPlugin());
		
		RedisPlugin redisCache = new RedisPlugin("redsea_redisCache", "192.168.101.190", "redsea");
		me.add(redisCache);
        
       me.add(new QuartzPlugin());
	}

	@Override
	public void afterJFinalStart() {
		//设置文件上传重命名策略
		OreillyCos.setFileRenamePolicy(new UpFileRenamePolicy());
		
		ModelRecordElResolver.setResolveBeanAsModel(true);

		Sqls.load("sqls.txt");
		
		/*
		 * 	String ak = PropKit.get("qiniu.ak");
			String sk = PropKit.get("qiniu.sk");
			String bucket = PropKit.get("qiniu.bucket");
			UeditorConfigKit.setFileManager(new QiniuFileManager(ak, sk, bucket));
		*/	
		/*if(CacheKit.get(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY)==null){
			CacheKit.put(CacheConstants.CONSTANTS_CODE_CACHE,CacheConstants.CODE_TABLE_KEY,SysCode.dao.findMap("paramname", "paramvalue", " isdelete = 0"));
		}
		if(CacheKit.get(CacheConstants.CONSTANTS_DATA_CACHE, CacheConstants.DATA_DICTIONARY_KEY)==null){
			String sql="select DISTINCT(type) from sys_dict";
			List<Record> list= Db.find(sql);
			Map<String,List<SysDict>> map=Maps.newHashMap();
			for(Record record:list){
				try {
					map.put(record.getStr("type"), SysDict.dao.getSysDictListByType(record.getStr("type")));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			CacheKit.put(CacheConstants.CONSTANTS_DATA_CACHE, CacheConstants.DATA_DICTIONARY_KEY, map);
		}*/
	}

}
