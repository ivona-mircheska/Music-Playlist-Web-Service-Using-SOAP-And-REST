package com.musicplaylist.spring.soap.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.example.playlist.shuffleplaylist.ShufflePlaylistRequest;
import com.example.playlist.shuffleplaylist.ShufflePlaylistResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class shufflePlaylistService {

	public ShufflePlaylistResponse getShufflePlaylistRequest(ShufflePlaylistRequest request) {
		ShufflePlaylistResponse shufflePlaylistResponse = new ShufflePlaylistResponse();
		String name = request.getName();
		try {
			String jsonFilePath = "C:\\Users\\Tiana\\eclipse-workspace\\musicplaylist-rest\\src\\main\\resources\\playlists.json";
			String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			boolean playlistExists = false;
			PlaylistList playlistList = gson.fromJson(json, PlaylistList.class);
			for(Playlist playlist : playlistList.getPlaylists()) {
				if(playlist.getName().equals(name)) {
					Collections.shuffle(playlist.getSongs());
					playlist.setSongs(playlist.getSongs());
					playlistExists = true;
					break;
				}
			}
			if(playlistExists) {
				String updatedJson = gson.toJson(playlistList);
				Files.write(Paths.get(jsonFilePath), updatedJson.getBytes());
				shufflePlaylistResponse.setMessage("The playlist is shuffled.");
				return shufflePlaylistResponse;
			}
		}catch(IOException e) {
			e.printStackTrace();
			shufflePlaylistResponse.setMessage("The playlist can't be shuffled.");
			return shufflePlaylistResponse;
		}
		shufflePlaylistResponse.setMessage("The playlist can't be shuffled.");
		return shufflePlaylistResponse;
	}
}
