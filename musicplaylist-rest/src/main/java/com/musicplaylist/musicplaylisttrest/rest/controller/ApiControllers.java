package com.musicplaylist.musicplaylistrest.rest.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.musicplaylist.musicplaylistrest.model.PlaylistList;
import com.musicplaylist.musicplaylistrest.model.Playlist;
import com.musicplaylist.musicplaylistrest.model.Song;
import com.musicplaylist.musicplaylistrest.model.SongList;
import com.musicplaylist.musicplaylistrest.model.User;
import com.musicplaylist.musicplaylistrest.model.UserList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@RestController
@RequestMapping("/playlists")
public class ApiControllers {
    
    //GET /playlists listanje na plej listi
	@GetMapping
	public PlaylistList getAllPlaylists() throws IOException {
        PlaylistList playlists = readPlaylistsFromFile();
        return playlists;
    }
	
	//POST /playlists?playlistName={playlistName}&userName={userName}&songTitle={songTitle} 
	//kreiranje na plej lista
	@PostMapping
	public String createPlaylist(@RequestParam("playlistName") String playlistName,
	        @RequestParam("userName") String userName, @RequestParam("songTitle") String songTitle) throws IOException {
		//proverka dali korisnikot postoi
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("C:\\Users\\Tiana\\eclipse-workspace\\music-playlist\\src\\main\\resources\\users.json");
		UserList userList = mapper.readValue(file, UserList.class);
		for(User user : userList.getUsers()) {
			if(user.getUsername().equals(userName)) {
				Playlist playlist = new Playlist();
				playlist.setName(playlistName);
				playlist.setUser(userName);
				List<Song> songs = new ArrayList<Song>();
				File file2 = new File("src/main/resources/songs.json");
				SongList songList = mapper.readValue(file2, SongList.class);
				songTitle = songTitle.replaceAll("(\\p{javaUpperCase})", " $1").trim();
				boolean songFound = false;
				for(Song song : songList.getSongs()) {
					if(song.getTitle().equals(songTitle)) {
						Song songAdd = new Song();
						songAdd.setTitle(song.getTitle());
						songAdd.setArtist(song.getArtist());
						songAdd.setGenre(song.getGenre());
						songAdd.setDuration(song.getDuration());
						songs.add(songAdd);
						playlist.setSongs(songs);
						songFound = true;
						break;
					}
				}
				if (!songFound) {
	                return "Can't create the playlist, song not in system";
	            }
				File file3 = new File("src/main/resources/playlists.json");
				PlaylistList playlistList = mapper.readValue(file3, PlaylistList.class);
				playlistList.getPlaylists().add(playlist);
				mapper.writerWithDefaultPrettyPrinter().writeValue(file3, playlistList);
				return "New playlist successfully created.";
				}
			}
		return "Can't create the playlist, user not in system";
		}
	
	//POST /playlists/songs/{songTitle}?songTitle={songTitle}&songArtist={songArtist}&songGenre={songGenre}&songDuration={songDuration}
	//dodavanje na pesna vo sistemot (songs.json)
	@PostMapping("/songs/{songTitle}")
	public String addSongInSystem(@RequestParam("songTitle") String songTitle, @RequestParam("songArtist") String songArtist, 
			@RequestParam("songGenre") String songGenre, @RequestParam("songDuration") double songDuration) throws IOException {
		Song song = new Song();
		songTitle = songTitle.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		songArtist = songArtist.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		songGenre = songGenre.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		song.setTitle(songTitle);
		song.setArtist(songArtist);
		song.setGenre(songGenre);
		song.setDuration(songDuration);
		addSong(song);
		return "Song successfully added in the system.";
	}
	
	//POST /playlists/{playlistName}/songs/{songTitle}
	//dodavanje na pesna vo plej lista (postoechka pesna) 
	@PostMapping("/{playlistName}/songs/{songTitle}")
	public String addSongInPlaylist(@PathVariable String playlistName, 
			@PathVariable String songTitle) throws IOException{
		playlistName = playlistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		songTitle = songTitle.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		// proverka dali postoi plej listata
		boolean playlistFound = false;
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName)) {
				playlistFound = true;
				break;
			}
		}
		if(!playlistFound) {
			return "Playlist not found.";
		}
		 
		// dopolnitelna proverka dali pesnata e vo sistemot (songs.json)
		File file2 = new File("src/main/resources/songs.json");
		SongList songList = mapper.readValue(file2, SongList.class);
		boolean songFound = false;
		for(Song song : songList.getSongs()) {
			if(song.getTitle().equals(songTitle)) {
				songFound = true;
				double sum = 0.0;
				double songFoundDuration = song.getDuration();
				sum = sum + songFoundDuration;
				//se zapishuva pesnata vo plej listata
				for(Playlist playlist : playlistList.getPlaylists()) {
					if(playlist.getName().equals(playlistName)) {
						//proverka dali nadminuva 100 min
						List<Song> songs = playlist.getSongs();
						songs.add(song);
						for(Song songPlaylist : songs) {
							double songDurationPlaylist = songPlaylist.getDuration();
							sum = sum + songDurationPlaylist;
						}
						if(sum<=100.0) {
						playlist.setSongs(songs);
						mapper.writerWithDefaultPrettyPrinter().writeValue(file, playlistList);
						}else {
							return "Can't add song, playlist exceeds 100 min limit.";
						}
					}
				}
					break;
				}
			}
			if(!songFound) {
				return "Song not found.";
			}
			
			return "Song successfully added in playlist";
	}
	
	//POST /playlists/{playlistName}/newsongs/{songTitle}?songArtist={songArtist}&songGenre={songGenre}&songDuration={songDuration}
	//dodavanje na pesna vo plej lista (nova pesna)
	@PostMapping("/{playlistName}/newsongs/{songTitle}")
	public String addNewSongInPlaylist(@PathVariable String playlistName, 
			@PathVariable String songTitle, @RequestParam("songArtist") String songArtist, 
			@RequestParam("songGenre") String songGenre, @RequestParam("songDuration") double songDuration) throws IOException {
		playlistName = playlistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		songTitle = songTitle.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		songArtist = songArtist.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		songGenre = songGenre.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		//proverka dali postoi plej listata
		boolean playlistFound = false;
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName)) {
				playlistFound = true;
				break;
			}
		}
		if(!playlistFound) {
			return "Playlist not found.";
		}
		//prvo dodavanje na novata pesna vo sistemot
		Song song = new Song();
		song.setTitle(songTitle);
		song.setArtist(songArtist);
		song.setGenre(songGenre);
		song.setDuration(songDuration);
		addSong(song);
		double sum = 0.0;
		double songFoundDuration = song.getDuration();
		sum = sum + songFoundDuration;
		//dodavanje na pesnata vo plej lista
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName)) {
				//proverka dali nadminuva 100 min
				List<Song> songs = playlist.getSongs();
				songs.add(song);
				for(Song songPlaylist : songs) {
					double songDurationPlaylist = songPlaylist.getDuration();
					sum = sum + songDurationPlaylist;
				}
				if(sum<=100.0) {
				playlist.setSongs(songs);
				mapper.writerWithDefaultPrettyPrinter().writeValue(file, playlistList);
				}else {
					return "Can't add song, playlist exceeds 100 min limit.";
				}
			}
		}
		
		return "Song successfully added in playlist";
	}
	
	//GET /playlists/{playlistName}/songs
	//listanje na site pesni od dadena plej lista
	@GetMapping("/{playlistName}/songs")
	public List<Song> getSongsFromPlaylist(@PathVariable String playlistName) throws IOException {
		playlistName = playlistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		//proverka dali postoi plej listata
		boolean playlistFound = false;
		Playlist playlistToAdd = new Playlist();
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName)) {
				playlistFound = true;
				playlistToAdd = playlist;
				break;
				}
		    }
		if(!playlistFound) {
			return null;
		}
		List<Song> songs = playlistToAdd.getSongs();
		return songs;
	}
	
	//GET /playlists/{playlistName}
	//listanje na plej lista preku ime
	@GetMapping("/{playlistName}")
	public Playlist getPlaylist(@PathVariable String playlistName) throws IOException {
		playlistName = playlistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName)) {
				return playlist;
			}
		}
		return null;
	}
	
	//DELETE /playlists/{playlistName}/user/{userName}
	//brishenje na plej lista od daden korisnik
	@DeleteMapping("/{playlistName}/user/{userName}")
	public String deletePlaylistFromUser(@PathVariable String playlistName, 
			@PathVariable String userName) throws IOException {
		//proverka dali postoi plej listata ili korisnikot
		playlistName = playlistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		userName = userName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		boolean playlistFound = false;
		boolean userFound = false;
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName) && playlist.getUser().equals(userName)) {
				playlistFound = true;
				userFound = true;
				break;
				}
		    }
		if(!playlistFound && !userFound) {
			return "Playlist, or user, or both not found.";
		}
		JsonNode jsonNode = mapper.readTree(new File("src/main/resources/playlists.json"));
		ArrayNode playlistsNode = (ArrayNode) jsonNode.get("playlists");
		for (int i = 0; i < playlistsNode.size(); i++) {
            JsonNode playlistNode = playlistsNode.get(i);
            String playlist = playlistNode.get("name").asText();
            String user = playlistNode.get("user").asText();
            if (playlist.equals(playlistName) && user.equals(userName)) {
                playlistsNode.remove(i);
                break;
            }
        }
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/playlists.json"), jsonNode);
		return "Playlist removed successfully.";
	}
	
	//GET /playlists/songs/{songTitle}
	//listanje na pesna po naslov
	@GetMapping("/songs/{songTitle}")
	public Song getSong(@PathVariable String songTitle) throws IOException {
		songTitle = songTitle.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/songs.json");
		SongList songList = mapper.readValue(file, SongList.class);
		for(Song song : songList.getSongs()) {
			if(song.getTitle().equals(songTitle)) {
				return song;
			}
		}
		return null;
	}
	
	//DELETE /playlists/{playlistName}/user/{userName}/song/{songTitle}
	//brishenje na pesna od dadena plej lista od daden korisnik
	@DeleteMapping("/{playlistName}/user/{userName}/song/{songTitle}")
	public String deleteSongFromPlaylist(@PathVariable String playlistName,
			@PathVariable String userName, @PathVariable String songTitle) throws IOException {
		playlistName = playlistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		userName = userName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		songTitle = songTitle.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		//proverka dali postojat plej listata i korisnikot
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		boolean playlistFound = false;
		boolean userFound = false;
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName) && playlist.getUser().equals(userName)) {
				playlistFound = true;
				userFound = true;
				break;
				}
		    }
		if(!playlistFound && !userFound) {
			return "Playlist, or user, or both not found.";
		}
		JsonNode jsonNode = mapper.readTree(new File("src/main/resources/playlists.json"));
		ArrayNode playlistsNode = (ArrayNode) jsonNode.get("playlists");
		boolean songRemoved = false;
		for (JsonNode playlistNode : playlistsNode) {
			String playlist = playlistNode.get("name").asText();
            String user = playlistNode.get("user").asText();
            if (playlist.equals(playlistName) && user.equals(userName)) {
            	ArrayNode songsNode = (ArrayNode) playlistNode.get("songs");
            	for (int i = 0; i < songsNode.size(); i++) {
            		JsonNode songNode = songsNode.get(i);
                    String song = songNode.get("title").asText();
                    if (song.equals(songTitle)) {
                    	songsNode.remove(i);
                    	songRemoved = true;
                    	break;
                    }
            	}
            }
		}
		if(!songRemoved) {
        	return "Song not found.";
        }
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/playlists.json"), jsonNode);
		return "Song from playlist removed successfully.";
	}
	
	//PUT /playlists/{playlistName}/rename/{newPlaylistName}/user/{userName}
	//preimenuvanje na plej lista od daden korisnik
	@PutMapping("/{playlistName}/rename/{newPlaylistName}/user/{userName}")
	public String renamePlaylist(@PathVariable String playlistName, 
			@PathVariable String newPlaylistName, @PathVariable String userName) throws IOException {
		//proverka dali postoi plej listata
		playlistName = playlistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		newPlaylistName = newPlaylistName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		userName = userName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		boolean playlistFound = false;
		boolean userFound = false;
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getName().equals(playlistName) && playlist.getUser().equals(userName)) {
				playlistFound = true;
				userFound = true;
				break;
				}
		    }
		if(!playlistFound && !userFound) {
			return "Playlist, or user, or both not found.";
		}
		String jsonFilePath = "src/main/resources/playlists.json";
		String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		PlaylistList playlists = gson.fromJson(json, PlaylistList.class);
		for(Playlist playlist : playlists.getPlaylists()) {
			if(playlist.getName().equals(playlistName)) {
				playlist.setName(newPlaylistName);
				break;
			}
		}
		String updatedJson = gson.toJson(playlists);
		Files.write(Paths.get(jsonFilePath), updatedJson.getBytes());
		return "Playlist name updated successfully.";
	}
	
	//GET /playlists/songs/genre/{genreType}
	//listanje na pesni po zhanr
	@GetMapping("/songs/genre/{genreType}")
	public List<Song> getSongsByGenre(@PathVariable String genreType) throws IOException {
		genreType = genreType.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/songs.json");
		SongList songList = mapper.readValue(file, SongList.class);
		List<Song> songs = new ArrayList<Song>();
		for(Song song : songList.getSongs()) {
			if(song.getGenre().equals(genreType)) {
				songs.add(song);
			}
		}
		return songs;
	}
	
	//GET /playlists/user/{userName}
	//listanje na site plej listi od daden korsnik
	@GetMapping("/user/{userName}")
	public List<Playlist> getPlaylistsFromUser(@PathVariable String userName) throws IOException {
		userName = userName.replaceAll("(\\p{javaUpperCase})", " $1").trim();
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/playlists.json");
		PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
		List<Playlist> playlists = new ArrayList<Playlist>();
		for(Playlist playlist : playlistList.getPlaylists()) {
			if(playlist.getUser().equals(userName)) {
				playlists.add(playlist);
			}
		}
		return playlists;
	}
	
	public void addSong(Song song) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("src/main/resources/songs.json");
		SongList songList = mapper.readValue(file, SongList.class);
		songList.getSongs().add(song);
		mapper.writerWithDefaultPrettyPrinter().writeValue(file, songList);
	}
		
	
	public PlaylistList readPlaylistsFromFile() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("src/main/resources/playlists.json");
        PlaylistList playlistList = mapper.readValue(file, PlaylistList.class);
        return playlistList;
    }
}
