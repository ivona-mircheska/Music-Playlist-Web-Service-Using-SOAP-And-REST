package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.shuffleplaylist.ShufflePlaylistRequest;
import com.example.playlist.shuffleplaylist.ShufflePlaylistResponse;
import com.musicplaylist.spring.soap.api.service.shufflePlaylistService;

@Endpoint
public class ShufflePlaylistEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/shufflePlaylist";
	@Autowired
	private shufflePlaylistService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "shufflePlaylistRequest")
	@ResponsePayload
	public ShufflePlaylistResponse getShufflePlaylistStatus(@RequestPayload ShufflePlaylistRequest request) {
		return service.getShufflePlaylistRequest(request);
	}
}
