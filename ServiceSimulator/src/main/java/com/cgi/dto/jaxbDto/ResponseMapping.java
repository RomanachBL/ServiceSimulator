package com.cgi.dto.jaxbDto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response-mapping")
public class ResponseMapping {
	
	@XmlElement(name = "response-name")
	private String name;
	
	@XmlElement(name = "url-pattern")
	private String url;

	public ResponseMapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseMapping(String name, String urlPattern) {
		super();
		this.name = name;
		this.url = urlPattern;
	}

	public String getResponseName() {
		return name;
	}

	public void setResponseName(String servletName) {
		this.name = servletName;
	}

	public String getUrlPattern() {
		return url;
	}

	public void setUrlPattern(String urlPattern) {
		this.url = urlPattern;
	}

	@Override
	public String toString() {
		return "ServletMapping [ResponseName=" + name + ", urlPattern=" + url + "]";
	}

}
