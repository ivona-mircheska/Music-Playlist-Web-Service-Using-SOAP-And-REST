package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.playlistinfo.PlaylistInfoRequest;
import com.example.playlist.playlistinfo.PlaylistInfoResponse;
import com.musicplaylist.spring.soap.api.service.playlistInfoService;

@Endpoint
public class PlaylistInfoEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/playlistInfo";
	@Autowired
	private playlistInfoService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "playlistInfoRequest")
	@ResponsePayload
	public PlaylistInfoResponse getPlaylistInfoStatus(@RequestPayload PlaylistInfoRequest request) {
		return service.getPlaylistInfoRequest(request);
	}
}
