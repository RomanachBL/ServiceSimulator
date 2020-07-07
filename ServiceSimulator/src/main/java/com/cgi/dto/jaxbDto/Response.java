package com.cgi.dto.jaxbDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response {

	@XmlElement(name = "response-name")
	private String name;
	
	@XmlElement(name = "type")
	private String responseType;
	
	@XmlElement(name = "file")
	private String path;

	public Response() {
		super();
	}
	
	public Response(String name, String responseType, String path) {
		super();
		this.name = name;
		this.responseType = responseType;
		this.path = path;
	}

	public String getResponseName() {
		return name;
	}

	public void setResponseName(String name) {
		this.name = name;
	}

	public String getFile() {
		return path;
	}

	public void setFile(String path) {
		this.path = path;
	}
	
	public String getType() {
		return responseType;
	}

	public void setType(String responseType) {
		this.responseType = responseType;
	}

	@Override
	public String toString() {
		return "Response [name=" + name + "type=" + responseType + ", path=" + path + "]";
	}

}
