package org.yuantai.common.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.yuantai.common.util.JsonUtil;
import org.yuantai.common.util.StringUtil;

/**
 * SpringMVC 自定义的jsonp view类,
 * @ClassName: JsonpView
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2014年11月21日 下午5:50:04
 */
@Component
public class JsonpView extends AbstractView {

	private static final Logger logger=Logger.getLogger(JsonpView.class);
	public static final String PARAMETERS_KEY="JsonpView.PARAMETERS_KEY";
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String callback = request.getParameter("callback");
		
		if(StringUtil.isEmpty(callback)) {
			logger.error("JsonpView have not parameter 'callback'");
			return;
		}
		
		String json=null;
		Map<String, Object> params=(Map<String, Object>) request.getAttribute(PARAMETERS_KEY);
		if(params!=null) {
			json=JsonUtil.toJson(params);
		} else {
			json=JsonUtil.toJson(model);
		}
		
		//将数据填充到callback，并回调
		StringBuffer buf = new StringBuffer();
		buf.append(callback).append("(").append(json).append(");");
		response.setContentType("text/js; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store,max-age=0");
		response.setDateHeader("Expires",-1);
		response.getWriter().print(buf.toString());
		response.getWriter().flush();
	}
}
