package com.musicplaylist.spring.soap.api.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.playlist.sendplaylist.SendPlaylistRequest;
import com.example.playlist.sendplaylist.SendPlaylistResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class sendPlaylistService {

	public SendPlaylistResponse getSendPlaylistRequest(SendPlaylistRequest request) {
		SendPlaylistResponse sendPlaylistResponse = new SendPlaylistResponse();
		String fromUser = request.getFromUser();
		String toUser = request.getToUser();
		String name = request.getName();
		boolean fromUserFound = false;
		boolean toUserFound = false;
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("src/main/resources/loggedUsers.json");
			loggedUserList logUserList = objectMapper.readValue(file, loggedUserList.class);
			for(LoggedUser logUser : logUserList.getLoggedUsers()) {
				if(logUser.getUsername().equals(fromUser)) {
					fromUserFound = true;
				}
				if(logUser.getUsername().equals(toUser)) {
					toUserFound = true;
				}
			}
			if(fromUserFound && toUserFound) {
				File file2 = new File("C:\\Users\\Tiana\\eclipse-workspace\\musicplaylist-rest\\src\\main\\resources\\playlists.json");
				PlaylistList playlistList = objectMapper.readValue(file2, PlaylistList.class);
				for(Playlist playlist : playlistList.getPlaylists()) {
					if(playlist.getName().equals(name) && playlist.getUser().equals(fromUser)) {
						SharePlaylist sharePlaylist = new SharePlaylist();
						sharePlaylist.setFromUser(fromUser);
						sharePlaylist.setToUser(toUser);
						sharePlaylist.setName(name);
						sharePlaylist.setSongs(playlist.getSongs());
						File file3 = new File("src/main/resources/sharePlaylists.json");
						SharePlaylistList shareplaylistList = objectMapper.readValue(file3, SharePlaylistList.class);
						shareplaylistList.getSharePlaylists().add(sharePlaylist);
						objectMapper.writerWithDefaultPrettyPrinter().writeValue(file3, shareplaylistList);
						sendPlaylistResponse.setMessage("Playlist shared successfully.");
						return sendPlaylistResponse;
					}
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			sendPlaylistResponse.setMessage("Can't share playlist.");
			return sendPlaylistResponse;
		}
		sendPlaylistResponse.setMessage("Can't share playlist.");
		return sendPlaylistResponse;
	}
}
