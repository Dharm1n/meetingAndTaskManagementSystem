package com.email.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

//this is meaningfull way to create service
@Service
public class EmailService {
	
	public boolean sendEmail(String subject,String message,String to) {
		
		//this is to just check whether the mail has been sent successfully or not
		boolean flag;
		//rest fo the code
		String from="amarjeet.chaudhary.1998@gmail.com";
		
		//variable for gmail
		String host="smtp.gmail.com";
		
		//get the system properties
		Properties properties=System.getProperties();
		
		System.out.println("Properties"+properties);
		
		//setting imp imformation to properties object
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port","465");
		properties.put("mail.smtp.ssl.enable","true"); // for security
		properties.put("mail.smtp.auth","true");
		
		//Step ! to get the session object after authentication
		Session session=Session.getInstance(properties,new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
//				this need to be true then only the session object will be created
				return new PasswordAuthentication("amarjeet.chaudhary.1998@gmail.com","jyrlojcharhsihdz");
			}
			
		});
//		just to get the debug details
		session.setDebug(true);
		
//		Step2 compose the message[text,multimedia]
		
		MimeMessage m=new MimeMessage(session);
		
		//set formemial id
		try {
			m.setFrom(from);
			
			//adding receipnt to message
			
//			if have multiple mail to be sent then use array in (to)
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			//adding subject to message
			
			m.setSubject(subject);
			
			//adding text to message
			m.setText(message);
			
			//Step 3:send using transport class
			Transport.send(m);
			
			System.out.println("sent-------success");
			flag=true;
			return flag;
			
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
