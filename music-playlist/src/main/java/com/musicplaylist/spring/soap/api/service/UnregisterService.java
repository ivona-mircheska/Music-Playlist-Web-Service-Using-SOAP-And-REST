package com.musicplaylist.spring.soap.api.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.playlist.unregistering.UnregisterRequest;
import com.example.playlist.unregistering.UnregisterResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;

@Service
public class UnregisterService {

	public UnregisterResponse getUnregisteringRequest(UnregisterRequest request) {
		UnregisterResponse unregisterResponse = new UnregisterResponse();
		String username = request.getUsername();
		String password = request.getPassword();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("src/main/resources/users.json");
			UserList userList = objectMapper.readValue(file, UserList.class);
			//da se najde korisnikot koj treba da se otstrani
			User userToRemove = null;
			for(User user : userList.getUsers()) {
				if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
					userToRemove = user;
					break;
				}
			}
			if(userToRemove!=null) {
				//pred da se izbrishe samiot korisnik da se trgnat site info
				//od loggedUsers.json i playlists.json
				//brishenje na site plej listi od korisnikot
				JsonNode jsonNode = objectMapper.readTree(new File("C:\\Users\\Tiana\\eclipse-workspace\\musicplaylist-rest\\src\\main\\resources\\playlists.json"));
				ArrayNode playlistsNode = (ArrayNode) jsonNode.get("playlists");
				for (int i = 0; i < playlistsNode.size(); i++) {
		            JsonNode playlistNode = playlistsNode.get(i);
		            String user = playlistNode.get("user").asText();
		            if (user.equals(username)) {
		                playlistsNode.remove(i);
		            }
		        }
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("C:\\Users\\Tiana\\eclipse-workspace\\musicplaylist-rest\\src\\main\\resources\\playlists.json"), jsonNode);
				//brishenje na korisnikot od listata na logirani
				JsonNode jsonNode2 = objectMapper.readTree(new File("src/main/resources/loggedUsers.json"));
				ArrayNode loggedUsersNode = (ArrayNode) jsonNode2.get("loggedUsers");
				for(int i = 0; i < loggedUsersNode.size(); i++) {
					JsonNode loggedUserNode = loggedUsersNode.get(i);
					String user2 = loggedUserNode.get("username").asText();
					if(user2.equals(username)) {
						loggedUsersNode.remove(i);
					}
				}
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/main/resources/loggedUsers.json"), jsonNode2);
				//brishenje na korisnikot od users.json
				userList.getUsers().remove(userToRemove);
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, userList);
				unregisterResponse.setSuccess(true);
				unregisterResponse.setMessage("User successfully unregistered.");
				return unregisterResponse;
			}else {
				unregisterResponse.setSuccess(false);
				unregisterResponse.setMessage("User not found.");
				return unregisterResponse;
			}
		}catch(IOException e) {
			e.printStackTrace();
			unregisterResponse.setSuccess(false);
			unregisterResponse.setMessage("Failed to unregister.");
		}
		return unregisterResponse;
	}
}
