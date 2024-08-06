package com.musicplaylist.spring.soap.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.example.playlist.logging.LoginRequest;
import com.example.playlist.logging.LoginResponse;


@Service
public class SoapClient {

	@Autowired
	private Jaxb2Marshaller marshaller;

	private WebServiceTemplate template;

	public LoginResponse getLoginStatus(LoginRequest request) {
		template = new WebServiceTemplate(marshaller);
		LoginResponse logResponse = (LoginResponse) template.marshalSendAndReceive("http://localhost:8080/ws",
				request);
		return logResponse;
	}

}
