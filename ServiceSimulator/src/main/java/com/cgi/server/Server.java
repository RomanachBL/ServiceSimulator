package com.cgi.server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cgi.clientManager.ClientManager;

public class Server<T extends ClientManager> implements Runnable {

	private int serverPort = 9090;
	private ServerSocket serverSocket = null;

	private ExecutorService threadPool = Executors.newFixedThreadPool(10);
	private ExecutorService serverTP = Executors.newFixedThreadPool(1);

	private final Class<T> clientManager;

	public Server(int port, Class<T> clientManager) {
		this.serverPort = port;
		this.clientManager = clientManager;
	}
	
	public void start() {
		this.serverTP.submit(this);
	}

	public void run() {

		Socket clientSocket = null;
		int clientCount = 0;
		Constructor<T> constructor = null;
		T clientManagerInstance = null;
		
		try {
			
			this.openServerSocket();
			
			constructor = clientManager.getConstructor(Socket.class, String.class);

			while (true) {

				clientCount++;

				try {
					
					clientSocket = this.serverSocket.accept();
					System.out.println(">>>> Client n°" + clientCount + " socket " + clientSocket.getRemoteSocketAddress());
					
				} catch (IOException e) {
					System.out.println("Server Stopped.");
					return;
				}
				
				clientManagerInstance = constructor.newInstance(clientSocket, "Thread Pooled Server");
				this.threadPool.submit(clientManagerInstance);
				
			}
		} catch (NoSuchMethodException e1) {
			e1.printStackTrace();
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void openServerSocket() {
		
		try {
			
			this.serverSocket = new ServerSocket(this.serverPort);
			
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port", e);
		}
	}
}
