package org.yuantai.common.spring.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class NumberToDateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String time) {
		System.out.println("aaaaaa:"+time);
		return new Date(Long.parseLong(time));
	}

}
