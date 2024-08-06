package com.musicplaylist.spring.soap.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.example.playlist.orderplaylist.OrderPlaylistRequest;
import com.example.playlist.orderplaylist.OrderPlaylistResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class orderPlaylistService {

	public OrderPlaylistResponse getOrderPlaylistRequest(OrderPlaylistRequest request) {
		OrderPlaylistResponse orderPlaylistResponse = new OrderPlaylistResponse();
		String name = request.getName();
		try {
			String jsonFilePath = "C:\\Users\\Tiana\\eclipse-workspace\\musicplaylist-rest\\src\\main\\resources\\playlists.json";
			String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			boolean playlistExists = false;
			PlaylistList playlistList = gson.fromJson(json, PlaylistList.class);
			for(Playlist playlist : playlistList.getPlaylists()) {
				if(playlist.getName().equals(name)) {
					List<Song> songs = playlist.getSongs();
					List<Double> durations = new ArrayList<>();
					for(Song song : songs) {
						durations.add(song.getDuration());
					}
					Collections.sort(durations);
					List<Song> songs2 = playlist.getSongs();
					List<Song> sortedSongs = new ArrayList<>();
		            for (Double duration : durations) {
		                for (Song song : songs2) {
		                    if (song.getDuration() == duration) {
		                        sortedSongs.add(song);
		                        break;
		                    }
		                }
		            }
		            playlist.setSongs(sortedSongs);
					playlistExists = true;
					break;
				}
			}
			if(playlistExists) {
				String updatedJson = gson.toJson(playlistList);
				Files.write(Paths.get(jsonFilePath), updatedJson.getBytes());
				orderPlaylistResponse.setMessage("The playlist is ordered.");
				return orderPlaylistResponse;
			}
		}catch(IOException e) {
			e.printStackTrace();
			orderPlaylistResponse.setMessage("The playlist is not ordered.");
			return orderPlaylistResponse;
		}
		orderPlaylistResponse.setMessage("The playlist is not ordered.");
		return orderPlaylistResponse;
	}
}
