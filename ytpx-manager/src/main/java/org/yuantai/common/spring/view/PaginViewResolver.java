package org.yuantai.common.spring.view;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

/**
 * 分页器的view的解析器
 * @ClassName: PaginViewResolver
 * @author zhangle
 * @email  zhanngle@163.com
 * @date 2015年4月4日 下午4:29:40
 */
@Component
public class PaginViewResolver extends AbstractCachingViewResolver implements Ordered {

    @Autowired private PaginView paginView;
    
    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        return viewName.equals(PaginView.VIEW_NAME)?paginView:null;
    }

	@Override
	public int getOrder() {
		return 0;
	}
	
}
