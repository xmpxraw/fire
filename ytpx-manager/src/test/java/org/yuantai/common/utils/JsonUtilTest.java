package org.yuantai.common.utils;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.yuantai.common.util.JsonUtil;
import org.yuantai.system.pojo.Logs;

public class JsonUtilTest {

	@Test
	public void test() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		System.out.println(TimeZone.getDefault().getID());
		Logs log=new Logs();
		log.setCreateTime(new Date());
		System.out.println(JsonUtil.toJson(log));
	}
}
