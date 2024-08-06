package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.logging.LoginRequest;
import com.example.playlist.logging.LoginResponse;
import com.musicplaylist.spring.soap.api.service.LoginService;

@Endpoint
public class LoginEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/logging";
	@Autowired
	private LoginService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "LoginRequest")
	@ResponsePayload
	public LoginResponse getLoginStatus(@RequestPayload LoginRequest request) {
		return service.getLoggingRequest(request);
	}

}
