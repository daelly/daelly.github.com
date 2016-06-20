package com.redsea.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileUtils {
	public static String webRootPath;
	public static String webUrl;
	public static List<String> getFileList(String strPath) {
		List<String> filelist=new ArrayList<String>();
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                    filelist.add(files[i].getAbsolutePath());
            }
        }
        return filelist;
    }
	public void traverseFolder2(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder2(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }
	
	static{
		try{
			InputStream s = FileUtils.class.getClassLoader().getResourceAsStream("config.txt");
			Properties p=new Properties();
			p.load(s);
			webRootPath = p.getProperty("upload_path");
			webUrl = p.getProperty("basePath");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println("文件上传配置加载失败！");
			throw new RuntimeException("文件上传配置加载失败！"+e);
		}
	}
	
}
