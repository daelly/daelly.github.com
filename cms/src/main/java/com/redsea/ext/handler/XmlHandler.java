package com.redsea.ext.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jfinal.core.JFinal;
import com.jfinal.handler.Handler;
import com.jfinal.render.RenderFactory;

/**
 * 对sitemap，rss等xml的处理
 * @author Rocky
 * @site:www.dreamlu.net
 * 2014年4月1日 下午9:36:53
 */
public class XmlHandler extends Handler {

    @Override
    public void handle(String target, HttpServletRequest request,
            HttpServletResponse response, boolean[] isHandled) {
        if (target.endsWith(".xml")) {
            String view = target.replace(".xml", ".jsp");
            RenderFactory.me().getJspRender("/WEB-INF/view/xml".concat(view)).setContext(request, response).render();
            // 跳出
            isHandled[0] = true;
            return;
        }
        next.handle(target, request, response, isHandled);
    }
    
}
