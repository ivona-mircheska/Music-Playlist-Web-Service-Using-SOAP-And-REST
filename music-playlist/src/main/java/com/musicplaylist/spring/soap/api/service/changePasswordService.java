package com.musicplaylist.spring.soap.api.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

import com.example.playlist.changepassword.ChangePasswordRequest;
import com.example.playlist.changepassword.ChangePasswordResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class changePasswordService {

	public ChangePasswordResponse getChangePasswordRequest(ChangePasswordRequest request) {
		ChangePasswordResponse changePassResponse = new ChangePasswordResponse();
		String username = request.getUsername();
		String oldPassword = request.getOldPassword();
		String newPassword = request.getNewPassword();
		try {
			String jsonFilePath = "src/main/resources/users.json";
			String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			boolean userRegistered = false;
			UserList users = gson.fromJson(json, UserList.class);
			for(User user : users.getUsers()) {
				if(user.getUsername().equals(username) && user.getPassword().equals(oldPassword)) {
					user.setPassword(newPassword);
					userRegistered = true;
					break;
				}
			}
			if(userRegistered) {
				String updatedJson = gson.toJson(users);
				Files.write(Paths.get(jsonFilePath), updatedJson.getBytes());
			}else {
				changePassResponse.setSuccess(false);
				changePassResponse.setMessage("Can't change password, user not found.");
				return changePassResponse;
			}
		}catch(IOException e) {
			e.printStackTrace();
			changePassResponse.setSuccess(false);
			changePassResponse.setMessage("Failed to change password.");
		}
		changePassResponse.setSuccess(true);
		changePassResponse.setMessage("Password changed successfully.");
		return changePassResponse;
	}
}
