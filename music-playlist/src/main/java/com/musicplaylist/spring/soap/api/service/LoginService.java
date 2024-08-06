package com.musicplaylist.spring.soap.api.service;

import org.springframework.stereotype.Service;

import com.example.playlist.logging.LoginRequest;
import com.example.playlist.logging.LoginResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


@Service
public class LoginService {

	public LoginResponse getLoggingRequest(LoginRequest request) {
		LoginResponse loginResponse = new LoginResponse();
		String username = request.getUsername();
		String password = request.getPassword();
		// da se napravi proverka dali korisnichkoto ime i lozinkata gi ima vo baza
		// odnosno korisnikot e registriran
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("src/main/resources/users.json");
			UserList userList = objectMapper.readValue(file, UserList.class);
			File file2 = new File("src/main/resources/loggedUsers.json");
			loggedUserList logUserList = objectMapper.readValue(file2, loggedUserList.class);
			
			for(User user : userList.getUsers()) {
				if(user.getUsername().equals(username) && user.getPassword().equals(password)){
					Random random = new Random();
					int sessionID = random.nextInt(10001); // generira od 0 do 10000
					String sessionIDString = String.valueOf(sessionID);
					//da se zapishe logiraniot korisnik vo loggedUsers.json
					//ako vekje go ima vo loggedUsers.json se prezapishuva sessionID
					String jsonFilePath = "src/main/resources/loggedUsers.json";
					String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					boolean userLogged = false;
					loggedUserList loggedUsers = gson.fromJson(json, loggedUserList.class);
					for(LoggedUser logUser : loggedUsers.getLoggedUsers()) {
						if(logUser.getUsername().equals(username)) {
							logUser.setSessionID(sessionIDString);
							userLogged = true;
							break;
						}
					}
					if(userLogged) {
						String updatedJson = gson.toJson(loggedUsers);
						Files.write(Paths.get(jsonFilePath), updatedJson.getBytes());
					}else {
						LoggedUser newLoggedUser = new LoggedUser();
						newLoggedUser.setUsername(username);
						newLoggedUser.setSessionID(sessionIDString);
						logUserList.getLoggedUsers().add(newLoggedUser);
						objectMapper.writerWithDefaultPrettyPrinter().writeValue(file2, logUserList);
					}
					loginResponse.setSessionId(sessionIDString);
					loginResponse.setMessage("Successfully logged in.");
					return loginResponse;
					}
				else {
					loginResponse.setSessionId("x");
					loginResponse.setMessage("Failed to log in.");
					return loginResponse;
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			loginResponse.setSessionId("x");
			loginResponse.setMessage("Failed to log in.");
		}
		return loginResponse;
	}
}

