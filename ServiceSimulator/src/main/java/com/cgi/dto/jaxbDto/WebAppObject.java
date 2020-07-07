package com.cgi.dto.jaxbDto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "web-app")
public class WebAppObject {

	@XmlElement(name = "response")
	private List<Response> responseList;
	
	@XmlElement(name = "response-mapping")
	private List<ResponseMapping> responseMappingList;

	public WebAppObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WebAppObject(List<Response> servletList, List<ResponseMapping> servletMappingList) {
		super();
		this.responseList = servletList;
		this.responseMappingList = servletMappingList;
	}
	
	public List<Response> getResponses() {
		return responseList;
	}

	public void setResponses(List<Response> responses) {
		this.responseList = responses;
	}

	public List<ResponseMapping> getResponseMappings() {
		return responseMappingList;
	}

	public void setResponseMappings(List<ResponseMapping> responseMappings) {
		this.responseMappingList = responseMappings;
	}

	@Override
	public String toString() {
		return "WebAppObject [servletList=" + responseList + ", servletMappingList=" + responseMappingList + "]";
	}
	
}
