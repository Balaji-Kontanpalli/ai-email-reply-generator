package com.email.writer.app;

//import lombok.Data;

//@Data  //automatically creates POJO class 
public class EmailRequest {
	private String emailContent;
	private String tone;
	
	public EmailRequest() {
	}
	
	
	
	public EmailRequest(String emailContent, String tone) {
		super();
		this.emailContent = emailContent;
		this.tone = tone;
	}


	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getTone() {
		return tone;
	}
	public void setTone(String tone) {
		this.tone = tone;
	}
	
	@Override
	public String toString() {
		return "EmailRequest [emailContent=" + emailContent + ", tone=" + tone + "]";
	}
	
	
	
}
