package com.cgi.dto;

public class ResponseDTO {

	private String response;
	
	private int code;
	
	private String type;

	public ResponseDTO(String response, int code, String type) {
		this.response = response;
		this.code = code;
		this.type = type;
	}
	
	public ResponseDTO() {
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
