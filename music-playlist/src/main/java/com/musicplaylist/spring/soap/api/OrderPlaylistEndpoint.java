package com.musicplaylist.spring.soap.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.playlist.orderplaylist.OrderPlaylistRequest;
import com.example.playlist.orderplaylist.OrderPlaylistResponse;
import com.musicplaylist.spring.soap.api.service.orderPlaylistService;


@Endpoint
public class OrderPlaylistEndpoint {

	private static final String NAMESPACE = "http://example.com/playlist/orderPlaylist";
	@Autowired
	private orderPlaylistService service;

	@PayloadRoot(namespace = NAMESPACE, localPart = "orderPlaylistRequest")
	@ResponsePayload
	public OrderPlaylistResponse getOrderPlaylistStatus(@RequestPayload OrderPlaylistRequest request) {
		return service.getOrderPlaylistRequest(request);
	}
}
