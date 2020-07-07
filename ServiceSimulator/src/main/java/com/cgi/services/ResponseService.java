package com.cgi.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cgi.dto.ResponseDTO;
import com.cgi.dto.jaxbDto.Response;
import com.cgi.dto.jaxbDto.ResponseMapping;
import com.cgi.dto.jaxbDto.WebAppObject;

public class ResponseService {

	/**
	 * Récupère la réponse à renvoyer au client.
	 * 
	 * @param request
	 * @return un objet {@link ResponseDTO}
	 */
	public ResponseDTO getResponse(String request) {

		ResponseDTO responseDTO = new ResponseDTO();
		Response jaxBResponse = new Response();
		String filePath = null;
		String response = null;
		String type = null;
		int code = 404;

		// Récupération de l'objet Reponse jaxB
		jaxBResponse = this.getResponsePathFromDispatcher(request);

		// Ajout du type (html, xml, soap ..)
		type = jaxBResponse.getType();
		responseDTO.setType(type);

		// Ajout du corps de la réponse
		filePath = jaxBResponse.getFile();
		response = this.readFile(filePath);
		responseDTO.setResponse(response);

		// Ajout du code (404 si la réponse est vide, 200 sinon).
		if (response != null && !response.equals("")) {
			code = 200;
			responseDTO.setCode(code);
		}

		return responseDTO;
	}

	/**
	 * Regarde dans le dispatcher.xml et récupère la réponse de type {@link Response} correspondante à la
	 * requète http.
	 * 
	 * @param request
	 * @return String - le path de la réponse
	 * @throws Exception
	 */
	private Response getResponsePathFromDispatcher(String request) {

		String query = null;
		String responseName = null;
		File xmlFile = null;
		JAXBContext jaxbContext = null;
		Unmarshaller jaxbUnmarshaller = null;
		WebAppObject webAppObj = null;
		List<ResponseMapping> reponseMappingList = new ArrayList<ResponseMapping>();
		List<Response> responseList = new ArrayList<Response>();
		Response jaxBResponse = null;

		try {

			query = this.handleRequest(request);

			xmlFile = new File(System.getProperty("user.dir") + "/src/main/resources/xml/dispatcher.xml");

			// On récupère le dispatcher.xml dans l'objet 'WebAppObject'
			jaxbContext = JAXBContext.newInstance(WebAppObject.class);
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			webAppObj = (WebAppObject) jaxbUnmarshaller.unmarshal(xmlFile);

			// Puis on parcourt les 'ResponseMapping' pour retrouver la bonne réponse
			reponseMappingList = webAppObj.getResponseMappings();
			responseList = webAppObj.getResponses();

			for (ResponseMapping rMap : reponseMappingList) {
				if (query.equals(rMap.getUrlPattern())) {
					responseName = rMap.getResponseName();
				}
			}

			if (responseName != null && responseName != "") {
				for (Response s : responseList) {
					if (responseName.equals(s.getResponseName())) {
						jaxBResponse = s;
					}
				}
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return jaxBResponse;
	}

	/**
	 * Permet de transformer la requête si besoin.<br>
	 * Par exemple, transforme "/toto?nom=nom" en "/toto"
	 * 
	 * @param request
	 * @return la query au format /toto
	 */
	private String handleRequest(String request) {

		StringTokenizer tokenizer = new StringTokenizer(request);
		String query = null;

		query = tokenizer.nextToken();
		while (!query.startsWith("/")) {
			query = tokenizer.nextToken();
		}

		if (query.contains("?")) {
			int index = query.indexOf("?");
			query = query.substring(0, index);
		}

		int sIndex = query.indexOf("/", 1);
		if (sIndex > 0) {
			query = query.substring(0, sIndex);
		}

		return query;
	}

	/**
	 * Permet de récupérer le contenu d'un fichier
	 * 
	 * @param filePath
	 * @return String - contenu du fichier
	 * @throws Exception
	 */
	private String readFile(String filePath) {
		StringBuilder contentBuilder = new StringBuilder();
		FileReader fr = null;
		String sCurrentLine = null;

		try {

			// Tout d'abord on récupère le content du fichier
			fr = new FileReader(System.getProperty("user.dir") + filePath);
			BufferedReader br = new BufferedReader(fr);

			while ((sCurrentLine = br.readLine()) != null) {
				contentBuilder.append(sCurrentLine).append("\n");
			}

			fr.close();
			br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return contentBuilder.toString();
	}

}
