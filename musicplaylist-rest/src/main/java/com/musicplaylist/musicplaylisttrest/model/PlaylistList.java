package com.musicplaylist.musicplaylistrest.model;

import java.util.List;


public class PlaylistList {

	private List<Playlist> playlists;
	
	public List<Playlist> getPlaylists() {
        return playlists;
    }
	
	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}
}
