package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.changepassword.ChangePasswordRequest;
import com.example.playlist.changepassword.ChangePasswordResponse;
import com.musicplaylist.spring.soap.api.service.changePasswordService;


@Endpoint
public class ChangePasswordEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/changePassword";
	@Autowired
	private changePasswordService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "changePasswordRequest")
	@ResponsePayload
	public ChangePasswordResponse getLoginStatus(@RequestPayload ChangePasswordRequest request) {
		return service.getChangePasswordRequest(request);
	}
}
