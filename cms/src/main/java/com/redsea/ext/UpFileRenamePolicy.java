package com.redsea.ext;

import java.io.File;
import java.util.Calendar;

import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.StrKit;
import com.oreilly.servlet.multipart.FileRenamePolicy;

/**
 * JFinal2.2文件上传重命名策略
 * 
 * @author Rocky 
 * email: rocky2171@163.com 
 * site:http://www.chenxiaofeng.wang
 * date 2015年7月10日下午11:23:25
 */
public class UpFileRenamePolicy implements FileRenamePolicy {
	@Override
	public File rename(File f) {
		StringBuilder newFileName =new StringBuilder(getDir()).append(getFileDir()).append(System.currentTimeMillis()).append(getFileExt(f.getName()));
		File dest = new File(newFileName.toString());
		// 创建目录
		File dir = dest.getParentFile();
		if (!dir.exists()) {
			dir.mkdirs();
		}
		f.renameTo(dest);
		return dest;
	}
	private static String getDir() {
		String saveDirectory = JFinal.me().getConstants().getBaseUploadPath();
		String dir;
		if (StrKit.isBlank(saveDirectory)) {
			dir = PathKit.getWebRootPath() + File.separator + "upload";
		}
		else if (isAbsolutelyPath(saveDirectory)) {
			dir = saveDirectory;
		}
		else {
			dir = PathKit.getWebRootPath() + File.separator + saveDirectory;
		}
		
		// add "/" postfix
		if (dir.endsWith("/") == false && dir.endsWith("\\") == false) {
			dir = dir + File.separator;
		}
		return dir;
	}
	
	private static boolean isAbsolutelyPath(String saveDirectory) {
		return saveDirectory.startsWith("/") || saveDirectory.indexOf(":") == 1;
	}
	/**
	 * 获取文件后缀
	 * 
	 * @param @param fileName
	 * @param @return 设定文件
	 * @return String 返回类型
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf('.'), fileName.length());
	}

	public static String getFileDir() {
		  StringBuilder newFileDir = new StringBuilder();
		  Calendar cal=Calendar.getInstance();
		  int year=cal.get(Calendar.YEAR);//得到年
		  int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
		  int day=cal.get(Calendar.DAY_OF_MONTH);//得到天
		  newFileDir.append(File.separator)
		  .append(File.separator)
		  .append(year)
		  .append(File.separator)
		  .append(month>10?month:"0"+month)
		  .append(File.separator)
		  .append(day>10?day:"0"+day)
		  .append(File.separator);
		return newFileDir.toString().replace("\\","/");
	}
}

