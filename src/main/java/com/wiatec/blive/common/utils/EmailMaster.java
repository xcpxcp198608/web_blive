package com.wiatec.blive.common.utils;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailMaster {

	private String emailContent = "";
	
	public boolean send (String receiverAddress){

//	String host = "mail.wiatec.com";
//	String sendAddress = "patrickxu@wiatec.com";
//	String userName = "patrickxu@wiatec.com";
//	String password ="83L6jJ";

//	String SSL_FACTORY="javax.net.ssl.SSLSocketFactory";
//	String sendAddress = "ld_reg@foxmail.com";
//	String userName = "ld_reg@foxmail.com";
//	String password ="xbfixpknxoeqdbbe";

		String sendAddress = "bactivation@legacy.direct";
		String userName = "bactivation@legacy.direct";
		String password ="Ihatespam123#";

		String emailSubject = "WELCOME TO LD";
		Properties properties;
		Session session;
		MimeMessage message;
		Transport transport = null;
		
		if(receiverAddress == null || receiverAddress.equals("")){
			System.out.println("receiverAddress error");
			return false;
		}
		properties = new Properties();
		
//		properties.put("mail.smtp.host", host);
//		properties.put("mail.smtp.auth", "true");
		
//		properties.setProperty("mail.smtp.host","smtp.qq.com") ;  
//		properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);  
//		properties.setProperty("mail.smtp.socketFactory.fallback", "false") ;  
//		properties.setProperty("mail.smtp.port","465") ;  
//		properties.put("mail.smtp.starttls.enable","true");  
//		properties.setProperty("mail.smtp.socketFactory.port","465") ;  
//		properties.setProperty("mail.smtp.auth","true") ;   
		
		properties.setProperty("mail.smtp.host","smtp.office365.com") ;  
		properties.setProperty("mail.smtp.port","587") ;  
		properties.put("mail.smtp.starttls.enable","true");  
		properties.setProperty("mail.smtp.socketFactory.port","587") ;  
		properties.setProperty("mail.smtp.auth","true") ;   
		
		session = Session.getDefaultInstance(properties, null);
		message = new MimeMessage(session);
		try {
//			System.out.println("start");
//			System.out.println(emailContent);
			message.setFrom(new InternetAddress(sendAddress));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiverAddress));
			message.setSubject(emailSubject);
			Multipart multipart = new MimeMultipart();
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(emailContent,"text/html ; charset=utf-8");
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			message.setSentDate(new Date());
			transport = session.getTransport("smtp");
			transport.connect((String)properties.get("mail.smtp.host"), userName ,password);
			transport.sendMessage(message , message.getRecipients(MimeMessage.RecipientType.TO));
			return true;
		} catch (AddressException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(transport != null){
					transport.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setEmailContent(String basePath, String userName ,String token){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Dear "+userName+":<br/>");
		stringBuilder.append("<br/>");
		stringBuilder.append("Legacy Direct"+"<br/>");
		stringBuilder.append("<a href='" + basePath + "/user/activate/"+token+"'>" +
				"ACTIVATE MY ACCOUNT</a>"+"<br/><br/>");
		this.emailContent = stringBuilder.toString();
	}

	public void setResetPasswordContent(String basePath, String userName ,String token){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Dear "+userName+":<br/>");
		stringBuilder.append("<br/>");
		stringBuilder.append("Legacy Direct"+"<br/>");
		stringBuilder.append("You have requested to reset your password.  " +
				"Please click the link below to finalize the process."+"<br/><br/>");
		stringBuilder.append("<a href='" + basePath + "/user/go/"+token+"'>" +
				"RESET PASSWORD</a>"+"<br/><br/>");
		stringBuilder.append("Legacy Direct"+"<br/>");
		this.emailContent = stringBuilder.toString();
	}

}
