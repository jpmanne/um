package com.sciits.devops.poc.email;

public class EmailDetails {
	private String[] toRecipients;
	private String ccRecipients;
	private String subject;
	private String messageContent;
	private String attachmentFilePath;
	
	public String[] getToRecipients() {
		return toRecipients;
	}
	public void setToRecipients(String[] toRecipients) {
		this.toRecipients = toRecipients;
	}
	public String getCcRecipients() {
		return ccRecipients;
	}
	public void setCcRecipients(String ccRecipients) {
		this.ccRecipients = ccRecipients;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getAttachmentFilePath() {
		return attachmentFilePath;
	}
	public void setAttachmentFilePath(String attachmentFilePath) {
		this.attachmentFilePath = attachmentFilePath;
	}
}
