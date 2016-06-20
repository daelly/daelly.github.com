/**
 * 
 */
package com.redsea.ext.plugin.webmagic.multipage;

import java.util.Iterator;
import java.util.Map;

import us.codecraft.webmagic.MultiPageModel;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author liqingyang
 * @date 2016-6-12 上午11:50:47
 */
public class CNYSConsolePipeline implements Pipeline {

	/* (non-Javadoc)
	 * @see us.codecraft.webmagic.pipeline.Pipeline#process(us.codecraft.webmagic.ResultItems, us.codecraft.webmagic.Task)
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		 Map<String, Object> resultItemsAll = resultItems.getAll();
        Iterator<Map.Entry<String, Object>> iterator = resultItemsAll.entrySet().iterator();
        while (iterator.hasNext()) {
        	Map.Entry<String, Object> objectEntry = iterator.next();
            Object o = objectEntry.getValue();
            if(o instanceof MultiPageModel){
            	MultiPageModel multiPageModel = (MultiPageModel) o;
            	if("1".equals(multiPageModel.getPage())){
            		System.out.println(multiPageModel);
            	}
            }
        }
	}

}
