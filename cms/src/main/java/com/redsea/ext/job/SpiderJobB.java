/**
 * 
 */
package com.redsea.ext.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.redsea.utils.SiteMapUtil;

/**
 * @author chenxiaofeng
 * @date 2016-5-9 下午8:17:39
 */
public class SpiderJobB implements Job {

    @Override
    public void execute(JobExecutionContext arg) throws JobExecutionException {
    	SiteMapUtil.build();
	}

}
