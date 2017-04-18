package org.yuantai.system.pojo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 域模型基础类
 * @author zhangle
 */
public abstract class Pojo implements Serializable {

	public String toString() {
		return ReflectionToStringBuilder.toString(this,ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public int hashCode() {
		return this.getId().hashCode();
	}

	public boolean equals(Object pojo) {
		if(pojo==null) return false;
		if(this.getClass()!=pojo.getClass()) return false;
		if(!(pojo instanceof Pojo)) return false;
		return this.getId().equals(((Pojo)pojo).getId());
	}
	
	public abstract void setId(String id);
	public abstract String getId();
	
}
