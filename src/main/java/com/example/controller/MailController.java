package com.example.controller;

import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Mail;
import com.example.status.Response;
import com.fasterxml.jackson.databind.ObjectMapper;




@Controller
@CrossOrigin(origins="*",allowedHeaders="*")
public class MailController {
   @Autowired
   public JavaMailSender emailSender;
   
   private int status_error = 404;
   private int status_success = 200;
   private String message_success = "Email Send";
   private String message_error = "Email not send";
 
   @ResponseBody
   @CrossOrigin(origins="*",allowedHeaders="*")
   @PostMapping(value="/send")
   public Response sendSimpleEmail(@RequestBody Mail newMail) {
		 Response resp = new Response();
		 if(newMail.getFilename() ==null){
			 try {
				 SimpleMailMessage message = new SimpleMailMessage();
		         
		    	 Mail nouv = new Mail(newMail.getEnvoyeur(),newMail.getReceveur(),newMail.getObject(),newMail.getMessage());
		    	 message.setFrom(nouv.getEnvoyeur());
		         message.setTo(nouv.getReceveur());
		         message.setSubject(nouv.getObject());
		         message.setText(nouv.getMessage());
		         System.out.println("Sender" + nouv.getEnvoyeur());
		         System.out.println("Envoyeur" + nouv.getReceveur());
		         System.out.println("Object" + nouv.getObject());
		         System.out.println("Message" + nouv.getMessage());
		       
		         // Send Message!
		         this.emailSender.send(message);
		         resp = new Response(status_success,message.getSubject());
				 
			 }catch(Exception ex) {
				 System.out.println(ex.getMessage());
				 resp = new Response(status_error,message_error);
			 } 
		 }else {
			 try {
				Mail nouv = new Mail(newMail.getEnvoyeur(),newMail.getReceveur(),newMail.getObject(),newMail.getMessage());
			 	MimeMessage message = emailSender.createMimeMessage();
			 
		        boolean multipart = true;
		 
		        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
		        
		        helper.setFrom(nouv.getEnvoyeur());
		        helper.setTo(nouv.getReceveur());
		        helper.setSubject(nouv.getObject());
		        helper.setText(nouv.getMessage());
		        
		        System.out.println("Sender" + nouv.getEnvoyeur());
		        System.out.println("Envoyeur" + nouv.getReceveur());
		        System.out.println("Object" + nouv.getObject());
		        System.out.println("Message" + nouv.getMessage());
		       
		 
//		         Attachment 1
//		        for(int i=0;i<nouv.getPath().length;i++) {
//		        	 System.out.println("Path" + nouv.getPath());
//		        	 FileSystemResource file1 = new FileSystemResource(new File(nouv.getPath()));
//				     helper.addAttachment("Txt file", file1);
//		        }
		       
		       
		         // Send Message!
		         this.emailSender.send(message);
		         resp = new Response(status_success,message_success);
				 
			 }catch(Exception ex) {
				 System.out.println(ex.getMessage());
				 resp = new Response(status_error,message_error);
			 } 
		 }
		 
    	return resp;
    }
   
	   @ResponseBody
	   @CrossOrigin(origins="*",allowedHeaders="*")
	   @PostMapping(value="/sendMail")
	    public ResponseEntity<Response> receiveMail(@RequestParam("file") MultipartFile file,@RequestParam("form") String m) throws IOException {
		   	Response resp = new Response();
	    	Mail mail = new ObjectMapper().readValue(m, Mail.class);
	    	mail.setBytes(file.getBytes());
	    	mail.setFilename(file.getOriginalFilename());
	    	 try {
				 	MimeMessage message = emailSender.createMimeMessage();
				 
			        boolean multipart = true;
			 
			        MimeMessageHelper helper = new MimeMessageHelper(message, multipart);
			        
			        helper.setFrom(mail.getEnvoyeur());
			        helper.setTo(mail.getReceveur());
			        helper.setSubject(mail.getObject());
			        helper.setText(mail.getMessage());
			        
			        System.out.println("Sender" + mail.getEnvoyeur());
			        System.out.println("Receveur" + mail.getReceveur());
			        System.out.println("Object" + mail.getObject());
			        System.out.println("Message" + mail.getMessage());
	
					helper.addAttachment(mail.getFilename(), file);
			       
			       
			         // Send Message!
			         this.emailSender.send(message);
			         resp = new Response(status_success,message_success);
					 
				 }catch(Exception ex) {
					 resp = new Response(status_success,message_error);
				 } 
		
		   return new ResponseEntity<Response>(resp,HttpStatus.OK);
	    }
	   

	   
	   

}

