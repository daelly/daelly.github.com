package com.redsea.controller.web;

import java.io.IOException;

import com.google.zxing.WriterException;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.redsea.utils.QRCodeUtils;
import com.redsea.utils.URLUtils;


/**
 * 二维码编码
 * @author Rocky
 *
 */
public class QrcodeController extends Controller {

	private static Log log = Log.getLog(QrcodeController.class);
	
	/**
	 * 二维码生成
	 */
	public void index() {
		String url = getPara("url");
		int width  = getParaToInt("width", 100);
		String code = URLUtils.decode(url);
		try {
			QRCodeUtils.encode(getResponse().getOutputStream(), code, width);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (WriterException e) {
			log.error(e.getMessage(), e);
		}
		renderNull();
	}
}
