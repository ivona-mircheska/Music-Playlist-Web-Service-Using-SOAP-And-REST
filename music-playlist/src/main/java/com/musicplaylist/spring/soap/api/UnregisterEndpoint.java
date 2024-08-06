package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.unregistering.UnregisterRequest;
import com.example.playlist.unregistering.UnregisterResponse;
import com.musicplaylist.spring.soap.api.service.UnregisterService;

@Endpoint
public class UnregisterEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/unregistering";
	@Autowired
	private UnregisterService service;
	
	@PayloadRoot(namespace = NAMESPACE, localPart = "UnregisterRequest")
	@ResponsePayload
	public UnregisterResponse getRegisterStatus(@RequestPayload UnregisterRequest request) {
		return service.getUnregisteringRequest(request);
	}
}
