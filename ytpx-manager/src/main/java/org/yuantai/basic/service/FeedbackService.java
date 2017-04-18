package org.yuantai.basic.service;

import java.util.List;

import org.yuantai.basic.pojo.Feedback;
import org.yuantai.system.service.BaseService;

public interface FeedbackService extends BaseService<Feedback> {

	public void sort(Feedback[] feedbacks);
	
	public List<Feedback> findForHome(String pid,int startrow, int pagesize);
}
