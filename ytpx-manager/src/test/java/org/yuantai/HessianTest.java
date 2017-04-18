package org.yuantai;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

import net.ozsofts.wechat.handler.MessageHandler;
import net.ozsofts.wechat.message.req.RequestMessage;
import net.ozsofts.wechat.message.req.TextMessage;

public class HessianTest {  
	  
    /** 
     * @param args 
     */  
    public static void main(String[] args) {  
    	
        String url = "http://localhost:8080/hessian/messageHandler";  
         HessianProxyFactory factory = new HessianProxyFactory();    
         try {
        	 MessageHandler messageHandler=(MessageHandler) factory.create(MessageHandler.class, url);
        	 TextMessage message =new TextMessage();
    		 message.setMsgId("1001");
    		 message.setContent("15918729474");
    		 message.setMsgType("text");
    		 message.setSysId("yuantai");
    		 message.setFromUserName("o5JoVwEDzv8Ty-OneE8ddLgefoNE");
			try {
				messageHandler.handle(message);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
    	
    }  
  
}  