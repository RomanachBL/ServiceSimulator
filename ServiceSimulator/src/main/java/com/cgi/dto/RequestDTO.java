package com.cgi.dto;

import java.net.Socket;

public class RequestDTO {
	private Socket clientSocket;
	private String request;

	public RequestDTO(Socket clientSocket, String request) {
		this.clientSocket = clientSocket;
		this.request = request;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}
	
}