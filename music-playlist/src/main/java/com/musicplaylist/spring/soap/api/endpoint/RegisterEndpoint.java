package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.registering.RegisterRequest;
import com.example.playlist.registering.RegisterResponse;
import com.musicplaylist.spring.soap.api.service.RegisterService;

@Endpoint
public class RegisterEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/registering";
	@Autowired
	private RegisterService service;
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "RegisterRequest")
	@ResponsePayload
	public RegisterResponse getRegisterStatus(@RequestPayload RegisterRequest request) {
		return service.getRegisteringRequest(request);
	}
}
