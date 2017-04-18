package org.yuantai.common.spring.view;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

/**
 * jsonp view的解析器
 * @ClassName: JsonpViewResolver
 * @Description:
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2014年11月21日 下午5:51:30
 */
@Component
public class XmlViewResolver extends AbstractCachingViewResolver implements Ordered {
	
    @Autowired private XmlView xmlView;
    
    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        return viewName.equals(XmlView.VIEW_NAME)?xmlView:null;
    }

	@Override
	public int getOrder() {
		return 2;
	}

}
