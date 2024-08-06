package com.musicplaylist.spring.soap.api.service;

import java.util.List;

public class SharePlaylistList {

	private List<SharePlaylist> sharePlaylists;
	
	public List<SharePlaylist> getSharePlaylists() {
        return sharePlaylists;
    }

    public void setSharePlaylists(List<SharePlaylist> sharePlaylists) {
        this.sharePlaylists = sharePlaylists;
    }
}
