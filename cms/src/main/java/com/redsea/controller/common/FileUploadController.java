/**
 * 
 */
package com.redsea.controller.common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.render.JsonRender;
import com.jfinal.upload.UploadFile;
import com.redsea.ext.UpFileRenamePolicy;
import com.redsea.interceptor.SessionInterceptor;
import com.redsea.utils.FileUtils;
import com.redsea.vo.AjaxResult;

/**
 * @author chenxiaofeng
 * @date 2016-4-18 下午12:05:13
 */
public class FileUploadController extends Controller {
		/**
		 * 首页
		 */
		public void index() {}

		/**
		 * 
		 * jquery 图片截取插件
		 * 
		 * http://code.ciaoca.com/jquery/jcrop/demo/crop.php
		 * @throws IOException 
		 */
		public void crop() throws IOException {
			// 获取 jcrop 组件拿到的参数
			Integer x = getParaToInt("x");
			Integer y = getParaToInt("y");
			Integer w = getParaToInt("w");
			Integer h = getParaToInt("h");
			UploadFile uploadFile = getFile();
			if(uploadFile != null){
				String fileName = uploadFile.getFileName();
				String savePath = FileUtils.webRootPath+UpFileRenamePolicy.getFileDir() + fileName;
				File img = new File(savePath);
				if(img!=null && img.exists()){
					BufferedImage bufferedImage = ImageIO.read(img);
					BufferedImage out = bufferedImage.getSubimage(x, y, w, h);
					ImageIO.write(out,"JPEG",img);
				}
				String accessPath = UpFileRenamePolicy.getFileDir() + fileName;
				accessPath = accessPath.replaceAll("//", "/");
				//accessPath=accessPath.substring(1,accessPath.length());
				AjaxResult obj=new AjaxResult();
				obj.setData(accessPath);
				renderJson(new AjaxResult().success(accessPath));
			}
/*//			注意：此处不支持 gif 的动态图 

//			拼接测试图片文件的目录
			String root = PathKit.getWebRootPath();

			String filePath = root + "/static/jcrop/demos/demo_files/pool.jpg";
			filePath = filePath.replace('/', File.separatorChar);

			// 调取java 相关api 截图
			BufferedImage bufferedImage = ImageIO.read(new File(filePath));
			// 核心api，java截图
			BufferedImage out = bufferedImage.getSubimage(x, y, w, h);

			ImageIO.write(out, "JPEG", getResponse().getOutputStream());
			renderNull();*/
		}

		/**
		 * ajax 图片上传
		 * 
		 * 图片上传时注意： 在校验器、拦截器中都得先getFile()
		 * 
		 */
		public void upload() {
			UploadFile uploadFile = getFile();
			if(uploadFile != null){
				String fileName = uploadFile.getFileName();
				System.out.println(uploadFile.getFile().getAbsolutePath());
				String accessPath = UpFileRenamePolicy.getFileDir() + fileName;
				accessPath = accessPath.replaceAll("//", "/");
				//accessPath=accessPath.substring(1,accessPath.length());
				AjaxResult obj=new AjaxResult();
				obj.setData(accessPath);
				renderJson(new AjaxResult().success(accessPath));
			}
		}

		/**
		 * 图片上传逻辑
		 */
		public void do_upload() {
			List<UploadFile> files = getFiles();
			String html = "upload images";
			for (UploadFile uploadFile : files) {
				if (null != uploadFile) {
					String fileName = uploadFile.getFileName();
					String imgUrl = UpFileRenamePolicy.getFileDir() + fileName;

					html += "<img src=\"" + imgUrl + "\"><br/>";
				}
			}
			renderHtml(html);
		}
		
		/****
		 * 单个文件上传
		 */
		public void ajax_upload() {
			UploadFile up = getFile("file1");
			String url = "";
			if (null != up) {
				String fileName = up.getFileName();
				url = UpFileRenamePolicy.getFileDir() + fileName;
				if(PropKit.getBoolean("open_qiniu", false)){
				//	String dir=JFinal.me().getConstants().getBaseUploadPath();
					//String path=up.getFile().getAbsolutePath().substring(0,up.getFile().getAbsolutePath().indexOf(dir))+url.replace("/", "\\");
					try {
						//QiniuKit.put(fileName,new FileInputStream(new File(path)));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			render(new JsonRender(url).forIE());
		}

		/**
		 * kindeditor编辑器
		 */
		public void kindeditor() {}

		/**
		 * 文档：http://kindeditor.net/docs/upload.html
		 * 
		 * 参数：
		 * dir=image|file|media|flash
		 * 
		 *  //成功时
			{
			        "error" : 0,
			        "url" : "http://www.example.com/path/to/file.ext"
			}
			//失败时
			{
			        "error" : 1,
			        "message" : "错误信息"
			}
		 */
		public void upload_json() {
			UploadFile uploadFile = getFile();
			if (null == uploadFile) {
				setAttr("error", 1);
				setAttr("message", "请选择要上传的图片");
				render(new JsonRender().forIE());
				return;
			}
			String fileName = uploadFile.getFileName();
			String fileExt  = UpFileRenamePolicy.getFileExt(fileName);
			// 注意安全问题，只让指定后缀的文件上次，像jsp,war,sh等危险的后缀禁止
			//定义允许上传的文件扩展名
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", ".gif,.jpg,.jpeg,.png,.bmp");
			extMap.put("flash", ".swf,.flv");
			extMap.put("media", ".swf,.flv,.mp3,.mp4,.wav,.wma,.wmv,.mid,.avi,.mpg,.asf,.rm,.rmvb");
			extMap.put("file", ".doc,.docx,.xls,.xlsx,.ppt,.htm,.html,.txt,.zip,.rar,.gz,.bz2");

			String dir = getPara("dir", "image");
			String ext = extMap.get(dir);
			if (StrKit.isBlank(ext) || ext.indexOf(fileExt) == -1) {
				setAttr("error", 1);
				setAttr("message", "上传文件的类型不允许");
				render(new JsonRender().forIE());
				return;
			}
			String url =UpFileRenamePolicy.getFileDir() + fileName;
			setAttr("error", 0);
			setAttr("url", url);
			render(new JsonRender().forIE());
		}

		/**
		 * 文件管理
		 * 
		 * 参数：
		 * path=
		 * order=NAME|SIZE|TYPE
		 * dir=image|file|media|flash
		 * 
		 */
		public void file_manager_json() {
//			result.put("moveup_dir_path", moveupDirPath);
//			result.put("current_dir_path", currentDirPath);
//			result.put("current_url", currentUrl);
//			result.put("total_count", fileList.size());
//			result.put("file_list", fileList);
		}
		@Before(SessionInterceptor.class)
		public void file_manager(){
		}
		
		public void upload_modal(){
			
		}

	}

