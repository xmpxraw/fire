package org.yuantai.common.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * Runtime工具类
 * @ClassName: RuntimeUtil
 * @Description:
 * @author zhangle
 * @email  lezhang@isoftstone.com
 * @date 2013年10月5日 下午4:32:25
 */
public class RuntimeUtil {

	public static void exec(String cmd) {
		
		try {
			Runtime r = Runtime.getRuntime();
			Process process = r.exec(cmd);
			InputStreamReader is = new InputStreamReader(process.getErrorStream());
			LineNumberReader reader = new LineNumberReader(is);
			while (true) {
				String line=reader.readLine();
				if(line==null) break;
				System.out.println(line);
			}
			process.waitFor();
		} catch (Exception e) {
			throw new RuntimeException("执行外部命令失败! cmd:"+cmd,e);
		}
	}
}
