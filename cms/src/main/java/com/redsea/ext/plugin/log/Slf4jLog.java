package com.redsea.ext.plugin.log;

import org.slf4j.LoggerFactory;

import com.jfinal.log.Log;

public class Slf4jLog extends Log {

	private org.slf4j.Logger log;

	Slf4jLog(Class<?> clazz) {
		log = LoggerFactory.getLogger(clazz);
	}

	Slf4jLog(String name) {
		log = LoggerFactory.getLogger(name);
	}

	// (marker, this, level, msg,params, t);注意参数顺序
	public void info(String msg) {
		log.info(msg);
	}

	public void info(String msg, Throwable t) {
		log.info(msg, t);
	}

	public void debug(String msg) {
		log.debug(msg);
	}

	public void debug(String msg, Throwable t) {
		log.debug(msg, t);
	}

	public void warn(String msg) {
		log.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		log.warn(msg, t);
	}

	public void error(String msg) {
		log.error(msg);
	}

	public void error(String msg, Throwable t) {
		log.error(msg, t);
	}

	public void fatal(String msg) {
		log.error(msg);
	}

	public void fatal(String msg, Throwable t) {
		log.error(msg, t);
	}

	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}

	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}

	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}

	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}

	public boolean isFatalEnabled() {
		return log.isErrorEnabled();
	}
}