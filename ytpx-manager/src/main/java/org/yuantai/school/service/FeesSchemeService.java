package org.yuantai.school.service;

import java.util.List;

import org.yuantai.school.pojo.FeesScheme;
import org.yuantai.school.pojo.Term;
import org.yuantai.system.service.BaseService;

public interface FeesSchemeService extends BaseService<FeesScheme> {
	public  List<FeesScheme> findAll() ;
	public FeesScheme getFeesSchemeByName(String name) ;
	public List<FeesScheme> check(Integer schemeStatus,String sysCode);
	public List<FeesScheme> check(Integer schemeStatus);
}
