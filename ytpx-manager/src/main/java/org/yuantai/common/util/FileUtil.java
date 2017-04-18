package org.yuantai.common.util;

import java.io.File;

/**
 * 文件操作工具类
 */
public class FileUtil {

	/**
	 * 创建文件夹
	 * @param path
	 */
	public static boolean mkdir(String path) {
		File folder=new File(path);
		if(folder.exists()) return true;
		return folder.mkdirs();
	}
}
