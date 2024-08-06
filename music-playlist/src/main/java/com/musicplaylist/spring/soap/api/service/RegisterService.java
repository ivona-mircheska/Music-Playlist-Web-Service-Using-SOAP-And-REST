package com.musicplaylist.spring.soap.api.service;

import com.example.playlist.registering.RegisterRequest;
import com.example.playlist.registering.RegisterResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

@Service
public class RegisterService {

	public RegisterResponse getRegisteringRequest(RegisterRequest request){
		RegisterResponse registerResponse = new RegisterResponse();
		String username = request.getUsername();
		String password = request.getPassword();
		String email = request.getEmail();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("src/main/resources/users.json");
			UserList userList = objectMapper.readValue(file, UserList.class);
			User newUser = new User();
			if(username!="   " && password!="   " && email!="   " && username.length() > 2
					&& password.length() > 2 && email.length() > 2) {
				for(User user : userList.getUsers()) {
					if(user.getUsername().equals(username)) {
						registerResponse.setSuccess(false);
						registerResponse.setMessage("Username already taken.");
						return registerResponse;
					}
				}
				newUser.setUsername(username);
				newUser.setPassword(password);
				userList.getUsers().add(newUser);
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, userList);
				registerResponse.setSuccess(true);
				registerResponse.setMessage("User successfully registered.");
				return registerResponse;
			}
		}catch(IOException e){
			e.printStackTrace();
			registerResponse.setSuccess(false);
			registerResponse.setMessage("Failed to register.");
		}
		return registerResponse;
	}
}
