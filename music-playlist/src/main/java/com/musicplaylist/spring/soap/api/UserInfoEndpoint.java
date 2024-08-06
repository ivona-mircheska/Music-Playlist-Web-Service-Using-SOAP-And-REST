package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.userinfo.UserInfoRequest;
import com.example.playlist.userinfo.UserInfoResponse;
import com.musicplaylist.spring.soap.api.service.userInfoService;

@Endpoint
public class UserInfoEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/userInfo";
	@Autowired
	private userInfoService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "userInfoRequest")
	@ResponsePayload
	public UserInfoResponse getUserInfoStatus(@RequestPayload UserInfoRequest request) {
		return service.getUserInfoRequest(request);
	}
}
