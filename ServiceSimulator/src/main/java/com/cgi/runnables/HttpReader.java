package com.cgi.runnables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

import com.cgi.clientManager.ClientManager;

/**
 * R�cup�re et lit l'ent�te HTTP du client.<br><br>
 * 
 * Regarde la m�thode (GET ou POST), r�cup�re la requ�te puis envoie 
 * les donn�es dans une file d'attente ({@link BlockingQueue}) de la classe {@link RequestHandler}.
 *
 */
public class HttpReader extends ClientManager {
	
	private RequestHandler requestHandler = RequestHandler.getInstance();
	
	private BufferedReader inClient = null;
	
	protected Socket clientSocket = null;
	protected String serverText = null;
	
	public HttpReader(Socket clientSocket, String serverText) {
		super(clientSocket, serverText);
		this.clientSocket = clientSocket;
		this.serverText = serverText;
	}
	
	@Override
	public void run() {
		
		String line = null;
		
		try {
			this.inClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			line = this.inClient.readLine();
			
			while(line != null) {
				if(line.startsWith("GET") || line.startsWith("POST")) {
					
					this.requestHandler.publish(clientSocket, line);
					
				}
				
				line = this.inClient.readLine();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
