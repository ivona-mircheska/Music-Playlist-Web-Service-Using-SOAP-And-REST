package com.musicplaylist.musicplaylistrest.model;

import java.util.List;

public class SongList {

	private List<Song> songs;
	
	public List<Song> getSongs(){
		return songs;
	}
	
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
}
