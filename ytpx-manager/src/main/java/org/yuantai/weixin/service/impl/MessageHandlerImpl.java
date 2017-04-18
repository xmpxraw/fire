package org.yuantai.weixin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yuantai.school.pojo.School;
import org.yuantai.school.pojo.Student;
import org.yuantai.school.service.SchoolService;
import org.yuantai.school.service.StudentService;

import net.ozsofts.wechat.api.CustomAPI;
import net.ozsofts.wechat.handler.MessageHandler;
import net.ozsofts.wechat.message.req.RequestMessage;
import net.ozsofts.wechat.message.req.RequestType;
import net.ozsofts.wechat.message.req.TextMessage;
import net.ozsofts.wechat.message.resp.BaseMessage;

/**
 * @author zamn
 *
 */
public class MessageHandlerImpl implements MessageHandler {
	@Autowired
	private StudentService studentService;
	@Autowired
	private SchoolService schoolService;
	@Autowired
	private CustomAPI customAPI;

	/*
	 * 上行消息 用户关注公众号、保存OpenId
	 */
	@Override
	public BaseMessage handle(RequestMessage message) throws Exception {
		String msgType = message.getMsgType();
		if (msgType.equals(RequestType.TEXT)) {
			TextMessage textmessage = (TextMessage) message;
			String content = textmessage.getContent();
			String openId = message.getFromUserName();
			String schoolCode = message.getSysId();
			School school = schoolService.getSchoolCode(schoolCode);
			if (school != null) {
				List<Student> students2 = studentService.checkOpenIdByMobile(content,school.getSysCode());//根据手机号、查询			
				if (students2.isEmpty()) {
					net.ozsofts.wechat.message.resp.TextMessage tmsg = new net.ozsofts.wechat.message.resp.TextMessage();
					tmsg.setContent("尊敬的学员，您好！很抱歉，系统未查询到您的相关信息，我们将转为人工处理，学校老师会在48小时内与您联系，请保持电话畅通。详情请咨询："+school.getTelephone()+"，祝您学习愉快！");
					customAPI.sendMessage(schoolCode, openId, tmsg);
				}else{
					List<Student> students3 = studentService.checkOpenId( openId,school.getSysCode());//根据openId、查询
					if(!students3.isEmpty()){
						Student s3 =students3.get(0);
						if(s3.getMobile().equals(content)){
							net.ozsofts.wechat.message.resp.TextMessage tmsg = new net.ozsofts.wechat.message.resp.TextMessage();
							tmsg.setContent("尊敬的学员，您好！您已经绑定手机号"+s3.getMobile()+"请勿重复操作！。详情请咨询："+school.getTelephone()+"，祝您学习愉快！");
							customAPI.sendMessage(schoolCode, openId, tmsg);
						}else{
							net.ozsofts.wechat.message.resp.TextMessage tmsg = new net.ozsofts.wechat.message.resp.TextMessage();
							tmsg.setContent("尊敬的学员，您好！您的微信账号已经与其他手机（手机号："+s3.getMobile()+"）进行过绑定，不能再进行二次绑定！。详情请咨询："+school.getTelephone()+"，祝您学习愉快！");
							customAPI.sendMessage(schoolCode, openId, tmsg);
						}						
					}else{
						Student s2 = students2.get(0);
						String classeCode =s2.getClassCode();
						if(	s2.getWeixinOpenid()==null){
							s2.setWeixinOpenid(openId);
							studentService.update(s2);
							net.ozsofts.wechat.message.resp.TextMessage tmsg = new net.ozsofts.wechat.message.resp.TextMessage();
							tmsg.setContent("您已经成功绑定"+school.getSchoolName()+"微信公众号,接下来您可以通过此公众号进行在线分班。详情请咨询："+school.getTelephone()+"，祝您学习愉快！");
							customAPI.sendMessage(schoolCode, openId, tmsg);
						}else{
							net.ozsofts.wechat.message.resp.TextMessage tmsg = new net.ozsofts.wechat.message.resp.TextMessage();
							tmsg.setContent("尊敬的学员，您好！绑定失败，手机号"+s2.getMobile()+"已被其他账号绑定！。详情请咨询："+school.getTelephone()+"，祝您学习愉快！");
							customAPI.sendMessage(schoolCode, openId, tmsg);
						}						
					}
					
				}
			}
		}
		return null;
	}
	
}
