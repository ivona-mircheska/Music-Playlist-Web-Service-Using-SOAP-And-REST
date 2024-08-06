package com.musicplaylist.spring.soap.api.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.example.playlist.userinfo.UserInfoRequest;
import com.example.playlist.userinfo.UserInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class userInfoService {

	public UserInfoResponse getUserInfoRequest(UserInfoRequest request) {
		UserInfoResponse userInfoResponse = new UserInfoResponse();
		String username = request.getUsername();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			File file = new File("src/main/resources/loggedUsers.json");
			loggedUserList logUserList = objectMapper.readValue(file, loggedUserList.class);
			for(LoggedUser logUser : logUserList.getLoggedUsers()) {
				if(logUser.getUsername().equals(username)) {
					userInfoResponse.setUsername(logUser.getUsername());
					userInfoResponse.setSessionId(logUser.getSessionID());
					return userInfoResponse;
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
			userInfoResponse.setUsername(null);
			userInfoResponse.setSessionId(null);
			return userInfoResponse;
		}
		userInfoResponse.setUsername(null);
		userInfoResponse.setSessionId(null);
		return userInfoResponse;
	}
}
