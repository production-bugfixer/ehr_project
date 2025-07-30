package com.ehr.authenticate.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GlobalResponse<T> {
	private Boolean status;
	private String notificationMessage;
	private String message;
	private String errorCode;
	private String exceptionMessage;
	private String language;
	private String timestamp;
	private Object data;
	public GlobalResponse(Boolean status, String notificationMessage, String message, String errorCode,
			String exceptionMessage, T data) {
		this.status = status;
		this.notificationMessage = notificationMessage;
		this.message = message;
		this.errorCode = errorCode;
		this.exceptionMessage = exceptionMessage;
		this.data = data;
		this.timestamp=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	public GlobalResponse() {
		this.timestamp=LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getNotificationMessage() {
		return notificationMessage;
	}
	public void setNotificationMessage(String notificationMessage) {
		this.notificationMessage = notificationMessage;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getTimestamp() {
		return timestamp;
	}

}
