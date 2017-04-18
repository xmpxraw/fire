package org.yuantai;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试 启动spring容器
 * @author zhangle
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-*.xml")
public class StartSpringTest {

	@Autowired private ApplicationContext context;
	
	@Test
	public void test() {
		System.out.println("Hello spring");
		Assert.assertNotNull(context);
	}
}
