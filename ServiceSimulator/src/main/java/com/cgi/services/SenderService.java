package com.cgi.services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

import com.cgi.dto.ResponseDTO;
import com.cgi.enumerations.Status;

/**
 * Classe permettant de construire l'entête HTTP de la réponse,
 * puis de l'envoyer au client.
 * 
 */
public class SenderService {

	private OutputStream outClient = null;

	public void sendResponse(Socket clientSocket, ResponseDTO responseDto) {

		StringBuilder res = new StringBuilder();
		String statusLine;
		String contentType;
		int responseLength = 0;
		Date date = new Date();
		boolean closeStream = false;

		try {

			this.outClient = clientSocket.getOutputStream();
			
			if (responseDto.getCode() == 200) {
				
				if (responseDto.getType() != null && responseDto.getType().equals("xml")) {
					contentType = "Content-Type: text/xml;charset=UTF-8 \r\n";
				} else if (responseDto.getType() != null && responseDto.getType().equals("soap")) {
					contentType = "Content-Type: application/soap+xml;charset=UTF-8 \r\n";
					closeStream = true;
				} else {
					contentType = "Content-Type: text/html;charset=UTF-8 \r\n";
				}

				statusLine = Status.HTTP_200.toString() + "\r\n";
				
				responseLength = responseDto.getResponse().length();

				res.append(statusLine);
				res.append(contentType);
				res.append("Connection: close \r\n");
				res.append("Date: "+date+" \r\n");
				res.append("Last-Modified: "+date+" \r\n");
				res.append("Content-Length: " + responseLength + "\r\n");
				res.append("\r\n");
				res.append(responseDto.getResponse());
				
			} else {
				
				statusLine = Status.HTTP_404.toString() + "\r\n";
				contentType = "Content-Type: text/html;charset=UTF-8 \r\n";
				
				res.append(statusLine);
				res.append(contentType);
				res.append("\r\n");
				res.append("<h3>ERROR : 404 NOT FOUND</h3>");
			}

			outClient.write(res.toString().getBytes());
			outClient.flush();
			if(closeStream == true)
				outClient.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
