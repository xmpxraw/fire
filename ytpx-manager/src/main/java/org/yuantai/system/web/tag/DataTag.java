package org.yuantai.system.web.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yuantai.common.spring.SpringUtil;
import org.yuantai.common.util.JsonUtil;
import org.yuantai.system.pojo.Data;
import org.yuantai.system.service.DataService;

/**
 * 基础数据标签
 * 用于在页面上自动显示基础数据
 * @author zhangle
 */
public class DataTag extends TagSupport {
	
	private static final Logger logger = LogManager.getLogger(DataTag.class);
	private static Map<String, List<Data>> cacheMap=new Hashtable<String, List<Data>>();	//全局缓存

	private DataService dataService;
	
	private String code;				//数据code
	private String output;				//输出模式,取值范围.span,text,select,json,jsonArray,combobox
	private boolean cache;			//是否缓存基础数据,同时也代表是否从缓存取数据,没取到则查询数据库,并加入缓存
	
	private String selectedValue;		//选中的值
	private String options;			//追加的option.格式:key:value,key2:value2
	
	private String id;					//select标签ID
	private String name;				//select标签name
	private String onchange;			//onchange事件
	private String cssClass;			//css class
	private String style;				//css样式
	private boolean disabled;			//是否禁用此select
	
	private String dataOptions;
	
	public DataTag() {
		output="select";
		id="";
		name="";
		onchange="";
		cssClass="";
		style="";
		disabled=false;
		dataOptions="";
	}
	
	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);
		dataService=SpringUtil.getBean(DataService.class);
	}
	
	@Override
	@SuppressWarnings("all")
	public int doStartTag() throws JspException {

		Map params=new HashMap();
		params.put("id", id);
		params.put("name", name);
		params.put("onchange", onchange);
		params.put("cssClass", cssClass);
		params.put("style", style);
		params.put("disabled", disabled);
		params.put("dataOptions", dataOptions);
		
		List<Option> options=generateOptions();		//生成可选数据
		String html=OutputStyle.output(output, options, params);	//获取可选数据的html
		try {
			pageContext.getOut().print(html);		//输出html到页面
			pageContext.getOut().flush();
		} catch (IOException e) {}
		
		return SKIP_BODY;
	}
	
	/**
	 * 生成可选数据
	 * @return
	 */
	private List<Option> generateOptions() {
		
		List<Option> optionList=new ArrayList<Option>();
		
		boolean hasSelectedOption=false;	//是否选中了一个option
		//先显示option属性指示的键值对
		if(!StringUtils.isEmpty(options)) {
			String[] entry=options.split(",");
			for(int i=0;i<entry.length;i++) {
				String[] _entry=entry[i].split(":");
				String value="";
				String text="";
				if(_entry.length>=1) value=_entry[0];
				if(_entry.length>=2) text=_entry[1];
				if(selectedValue==null || !selectedValue.equals(value)) {
					addOption(optionList,value,text,false);
				} else {
					addOption(optionList,value,text,true);
					hasSelectedOption=true;
				}
			}
		}
			
		//再加载code属性指示的基础数据
		if(!StringUtils.isEmpty(code)) {
			
			List<Data> dataList=null;
			if(cache) {		//是否从缓存取数据,如果没取到,则从数据库取,并加入缓存中.
				dataList=cacheMap.get(code);
				if(dataList==null) {
					dataList=getData(code);
					cacheMap.put(code, dataList);
				}
			} else {		//否则直接查询数据库
				dataList=getData(code);
			}
		
			for(int i=0;i<dataList.size();i++) {
				Data data=dataList.get(i);
				if(selectedValue==null || !selectedValue.equals(data.getValue())) {
					addOption(optionList,data.getValue(),data.getText(),false);
				} else {
					addOption(optionList,data.getValue(),data.getText(),true);
					hasSelectedOption=true;
				}
			}
		}
		
		//如果selectedValue不在以上的option范围内,那么直接生成一个option
		if(!hasSelectedOption && !StringUtils.isEmpty(selectedValue)) {
			addOption(optionList,selectedValue,selectedValue,true);
		}

		return optionList;
	}
	
	/**
	 * 快速创建Option对象
	 * @param list
	 * @param value
	 * @param text
	 * @param selected
	 */
	private void addOption(List<Option> list,String value,String text,boolean selected) {
		Option option=new Option();
		option.setValue(value);
		option.setText(text);
		option.setSelected(selected);
		list.add(option);
	}
	
	/**
	 * 根据code,获取基础数据列表
	 * @param code
	 * @return
	 */
	private List<Data> getData(String code) {
		return dataService.getChildren(code);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOnchange() {
		return onchange;
	}

	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
	public String getDataOptions() {
		return dataOptions;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}
	
	/**
	 * option pojo
	 * @ClassName: Option
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年9月29日 下午3:11:23
	 */
	public static class Option {
		
		private String value;
		private String text;
		private boolean selected;
		
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public boolean getSelected() {
			return selected;
		}
		public void setSelected(boolean selected) {
			this.selected = selected;
		}
	}
	
	/**
	 * 输出样式抽象类及工厂类
	 * @ClassName: OutputStyle
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年9月29日 下午3:11:34
	 */
	private abstract static class OutputStyle {
		
		private static Map<String,OutputStyle> instanceMap=new HashMap<String, OutputStyle>() {
			{
				put("json",new JsonOutputStyle());
				put("jsonArray",new JsonArrayOutputStyle());
				put("select",new SelectOutputStyle());
				put("span",new SpanOutputStyle());
				put("text",new TextOutputStyle());
				put("combobox",new ComboboxOutputStyle());
			}
		};
		
		abstract String output(List<Option> options,Map params);
		
		static OutputStyle getInstance(String output) {
			return instanceMap.get(output);
		}
		
		static String output(String output,List<Option> options,Map params) {
			OutputStyle style=getInstance(output);
			return style.output(options,params);
		}
	}
	
	/**
	 * json输出类
	 * @ClassName: JsonOutputStyle
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年9月29日 下午3:12:36
	 */
	private static class JsonOutputStyle extends OutputStyle {

		@Override
		String output(List<Option> options,Map params) {
			if(options==null) return null;
			
			Map<String,Option> jsonMap=new HashMap<String,Option>();
			for(Option option:options) {
				jsonMap.put(option.getValue(), option);
			}
			return JsonUtil.toJson(jsonMap);
		}
		
	}
	
	/**
	 * json array输出类
	 * @ClassName: JsonArrayOutputStyle
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年10月24日 下午10:43:13
	 */
	private static class JsonArrayOutputStyle extends OutputStyle {

		@Override
		String output(List<Option> options,Map params) {
			if(options==null) return null;
			return JsonUtil.toJson(options);
		}
		
	}
	
	/**
	 * select标签输出类
	 * @ClassName: SelectOutputStyle
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年9月29日 下午3:12:48
	 */
	private static class SelectOutputStyle extends OutputStyle {
		@Override
		String output(List<Option> options,Map params) {
			if(options==null) return null;
			
			StringBuilder sb=new StringBuilder();
			sb.append(
					String.format(
						"<select id='%s' name='%s' onchange='%s' class='%s' style='%s' %s>",
						params.get("id"),
						params.get("name"),
						params.get("onchange"),
						params.get("cssClass"),
						params.get("style"),
						(Boolean)params.get("disabled")?"disabled":""
					)
			);
			
			for(Option option:options) {
				sb.append(output(option,params));
			}
			
			sb.append("</select>");
			return sb.toString();
		}
		
		String output(Option option,Map params) {
			return String.format("<option value='%s' %s>%s</option>", option.getValue(),option.getSelected()?"selected":"",option.getText());
		}
	}
	
	/**
	 * easyui Combobox 输出类
	 * @ClassName: SelectOutputStyle
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年9月29日 下午3:12:48
	 */
	private static class ComboboxOutputStyle extends OutputStyle {
		@Override
		String output(List<Option> options,Map params) {
			if(options==null) return null;
			
			StringBuilder sb=new StringBuilder();
			sb.append(
					String.format(
						"<select id='%s' name='%s' onchange='%s' class='easyui-combobox %s' style='%s' data-options=\"%s\" %s>",
						params.get("id"),
						params.get("name"),
						params.get("onchange"),
						params.get("cssClass"),
						params.get("style"),
						params.get("dataOptions"),
						(Boolean)params.get("disabled")?"disabled":""
					)
			);
			
			for(Option option:options) {
				sb.append(output(option,params));
			}
			
			sb.append("</select>");
			return sb.toString();
		}
		
		String output(Option option,Map params) {
			return String.format("<option value='%s' %s>%s</option>", option.getValue(),option.getSelected()?"selected":"",option.getText());
		}
	}
	
	/**
	 * span标签输出类,带有隐藏域
	 * @ClassName: SpanOutputStyle
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年9月29日 下午3:13:09
	 */
	private static class SpanOutputStyle extends OutputStyle {
		@Override
		String output(List<Option> options,Map params) {
			if(options==null) return null;
			
			for(Option option:options) {
				if(option.getSelected()) {
					return output(option,params);
				}
			}
			return null;
		}
		
		String output(Option option,Map params) {
			StringBuilder sb=new StringBuilder();
			sb.append(String.format("<span class='%s' style='%s'>%s</span>",params.get("cssClass"),params.get("style"),option.getText()));
			sb.append(String.format("<input type='hidden' id='%s' name='%s' value='%s'/>",params.get("id"),params.get("name"),option.getValue()));
			return sb.toString();
		}
	}
	
	/**
	 * 文本样式输出类,只输出选中文本
	 * @ClassName: TextOutputStyle
	 * @Description:
	 * @author zhangle
	 * @email  lezhang@isoftstone.com
	 * @date 2013年9月29日 下午3:13:24
	 */
	private static class TextOutputStyle extends OutputStyle {
		@Override
		String output(List<Option> options,Map params) {
			if(options==null) return null;
			
			for(Option option:options) {
				if(option.getSelected()) {
					return option.getText();
				}
			}
			return null;
		}
	}
}
