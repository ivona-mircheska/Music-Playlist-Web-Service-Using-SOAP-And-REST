package com.musicplaylist.spring.soap.api.service;

import java.util.List;

public class SharePlaylist {

	private String fromUser;
	private String toUser;
	private String name;
	private List<Song> songs;
	
	public String getFromUser() {
		return fromUser;
	}
	
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	
	public String getToUser() {
		return toUser;
	}
	
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Song> getSongs() {
		return songs;
	}
	
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
}
