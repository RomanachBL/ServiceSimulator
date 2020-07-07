package com.cgi;

import java.io.IOException;

import com.cgi.runnables.HttpReader;
import com.cgi.runnables.RequestHandler;
import com.cgi.server.Server;

public class Application extends Thread {
	
	public static void main(String[] args) throws IOException {
		Server<HttpReader> server = new Server<HttpReader>(9090, HttpReader.class);
		System.out.println("Serveur démarré !\n");
		server.start();
		
		RequestHandler handler = RequestHandler.getInstance();
		handler.start();
	}
	
}
