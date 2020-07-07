package com.cgi.runnables;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.cgi.dto.RequestDTO;
import com.cgi.dto.ResponseDTO;
import com.cgi.services.ResponseService;
import com.cgi.services.SenderService;

/**
 * Cette classe utilise un mono-thread.<br><br>
 * 
 * La tâche tourne en boucle et attend qu'un élément {@link RequestDTO} 
 * soit ajouté dans la file d'attente {@link BlockingQueue}.<br>
 * 
 * De cette requête, une réponse est ensuite récupérée, puis envoyée au client.
 *
 */
public class RequestHandler implements Runnable {

	private static RequestHandler requestHandler = new RequestHandler();

	private static SenderService senderService = new SenderService();
	
	private static ResponseService responseService = new ResponseService();

	private BlockingQueue<RequestDTO> queue = new ArrayBlockingQueue<RequestDTO>(10);

	private ExecutorService handlerTP = Executors.newFixedThreadPool(1);

	public static RequestHandler getInstance() {
		return requestHandler;
	}

	public void start() {
		this.handlerTP.submit(this);
	}

	public void publish(Socket clientSocket, String request) {
		this.queue.add(new RequestDTO(clientSocket, request));
	}
	
	public void stop() {
		try {
			this.handlerTP.shutdown();
			this.handlerTP.awaitTermination(10L, TimeUnit.SECONDS);
			this.handlerTP.shutdownNow();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		RequestDTO requestDto = null;
		ResponseDTO responseDto = null;
		String request = null;

		try {
			
			while (true) {
				
				requestDto = this.queue.take();
				
				request = requestDto.getRequest();
				
				responseDto = responseService.getResponse(request);
				
				senderService.sendResponse(requestDto.getClientSocket(), responseDto);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
