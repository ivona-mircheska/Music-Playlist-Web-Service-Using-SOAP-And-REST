package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.sendplaylist.SendPlaylistRequest;
import com.example.playlist.sendplaylist.SendPlaylistResponse;
import com.musicplaylist.spring.soap.api.service.sendPlaylistService;


@Endpoint
public class SendPlaylistEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/sendPlaylist";
	@Autowired
	private sendPlaylistService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "sendPlaylistRequest")
	@ResponsePayload
	public SendPlaylistResponse getSendPlaylistStatus(@RequestPayload SendPlaylistRequest request) {
		return service.getSendPlaylistRequest(request);
	}
}
