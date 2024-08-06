package com.musicplaylist.spring.soap.api.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.playlist.playlistinfo.PlaylistInfoRequest;
import com.example.playlist.playlistinfo.PlaylistInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class playlistInfoService {

	public PlaylistInfoResponse getPlaylistInfoRequest(PlaylistInfoRequest request) {
		PlaylistInfoResponse playlistInfoResponse = new PlaylistInfoResponse();
		String name = request.getName();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("C:\\Users\\Tiana\\eclipse-workspace\\musicplaylist-rest\\src\\main\\resources\\playlists.json");
			PlaylistList playlistList = objectMapper.readValue(file, PlaylistList.class);
			double sumDuration = 0.0;
			for(Playlist playlist : playlistList.getPlaylists()) {
				if(playlist.getName().equals(name)) {
					for(Song song : playlist.getSongs()) {
						sumDuration = sumDuration + song.getDuration();
					}
					playlistInfoResponse.setPlaylistDuration(sumDuration);
					return playlistInfoResponse;
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			playlistInfoResponse.setPlaylistDuration(0.0);
			return playlistInfoResponse;
		}
		playlistInfoResponse.setPlaylistDuration(0.0);
		return playlistInfoResponse;
	}
}
