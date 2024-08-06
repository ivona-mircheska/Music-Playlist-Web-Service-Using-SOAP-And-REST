package com.musicplaylist.musicplaylistrest.model;

import java.util.List;

public class Playlist {

	private String name;
    private String user;
    private List<Song> songs;


	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
