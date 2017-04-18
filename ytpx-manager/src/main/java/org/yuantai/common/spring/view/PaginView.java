package org.yuantai.common.spring.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.yuantai.common.Paginator;
import org.yuantai.common.util.JsonUtil;

/**
 * SpringMVC 自定义的pagin view类,
 * @ClassName: PaginView
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年4月4日 下午4:30:16
 */
@Component
public class PaginView extends AbstractView {

	public static final String VIEW_NAME="pagin";
	public static final String PARAMETERS_KEY="PaginView.PARAMETERS_KEY";
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Paginator pagin=(Paginator) request.getAttribute(PARAMETERS_KEY);
		Map<String, Object> jsonMap=new HashMap<String, Object>();
		jsonMap.put("total", pagin.getTotalCount());
		jsonMap.put("rows", pagin.getDataList());
		String json=JsonUtil.toJson(jsonMap);
		
		response.setContentType("text/js; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
		response.setDateHeader("Expires",-1);
		response.getWriter().print(json);
	//	System.out.println(json);
		response.getWriter().flush();
	}
}
