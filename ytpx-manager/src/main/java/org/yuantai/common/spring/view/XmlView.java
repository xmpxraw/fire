package org.yuantai.common.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * SpringMVC 自定义的jsonp view类,
 * @ClassName: JsonpView
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2014年11月21日 下午5:50:04
 */
@Component
public class XmlView extends AbstractView {

	public static final String VIEW_NAME="xml";
	public static final String PARAMETERS_KEY="XmlView.PARAMETERS_KEY";
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String xml=(String)request.getAttribute(PARAMETERS_KEY);
		response.setContentType("text/xml; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
		response.setDateHeader("Expires",-1);
		response.getWriter().print(xml);
		response.getWriter().flush();
	}
}
