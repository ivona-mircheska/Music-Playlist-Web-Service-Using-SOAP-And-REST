package com.musicplaylist.spring.soap.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.playlist.logging.LoginRequest;
import com.example.playlist.logging.LoginResponse;
import com.musicplaylist.spring.soap.api.client.SoapClient;


@SpringBootApplication
@RestController
public class SpringBootSoapClient {
	
	@Autowired
	private SoapClient client;
	
	@PostMapping("/getLoginStatus")
	public LoginResponse invokeSoapClientToGetLogStatus(@RequestBody LoginRequest request) {
		return client.getLoginStatus(request);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSoapClient.class, args);
	}

}
