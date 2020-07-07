package com.cgi.clientManager;

import java.net.Socket;

public abstract class ClientManager implements Runnable{
	
	protected Socket client = null;
	protected String serverText = null;

	public ClientManager(Socket client, String serverText) {
		this.client = client;
		this.serverText = serverText;
	}
}
