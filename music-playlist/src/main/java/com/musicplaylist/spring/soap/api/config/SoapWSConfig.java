package com.musicplaylist.spring.soap.api.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@Configuration
@EnableWs
public class SoapWSConfig {

	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(context);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/ws/*");
	}

	@Bean(name = "login")
	public DefaultWsdl11Definition defaultWsdl11DefinitionLogin(XsdSchema loginSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("Login");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/logging");
		defaultWsdl11Definition.setSchema(loginSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "register")
	public DefaultWsdl11Definition defaultWsdl11DefinitionReg(XsdSchema registerSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("Register");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/registering");
		defaultWsdl11Definition.setSchema(registerSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "unregister")
	public DefaultWsdl11Definition defaultWsdl11DefinitionUnreg(XsdSchema unregisterSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("Unregister");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/unregistering");
		defaultWsdl11Definition.setSchema(unregisterSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "changePassword")
	public DefaultWsdl11Definition defaultWsdl11DefinitionchangePass(XsdSchema changePassSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("ChangePassword");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/changePassword");
		defaultWsdl11Definition.setSchema(changePassSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "userInfo")
	public DefaultWsdl11Definition defaultWsdl11DefinitionuserInfo(XsdSchema userInfoSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("UserInfo");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/userInfo");
		defaultWsdl11Definition.setSchema(userInfoSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "playlistInfo")
	public DefaultWsdl11Definition defaultWsdl11DefinitionplaylistInfo(XsdSchema playlistInfoSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("PlaylistInfo");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/playlistInfo");
		defaultWsdl11Definition.setSchema(playlistInfoSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "sendPlaylist")
	public DefaultWsdl11Definition defaultWsdl11DefinitionsendPlaylist(XsdSchema sendPlaylistSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("SendPlaylist");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/sendPlaylist");
		defaultWsdl11Definition.setSchema(sendPlaylistSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "shufflePlaylist")
	public DefaultWsdl11Definition defaultWsdl11DefinitionshufflePlaylist(XsdSchema shufflePlaylistSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("ShufflePlaylist");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/shufflePlaylist");
		defaultWsdl11Definition.setSchema(shufflePlaylistSchema);
		return defaultWsdl11Definition;

	}
	
	@Bean(name = "orderPlaylist")
	public DefaultWsdl11Definition defaultWsdl11DefinitionorderPlaylist(XsdSchema orderPlaylistSchema) {
		DefaultWsdl11Definition defaultWsdl11Definition = new DefaultWsdl11Definition();
		defaultWsdl11Definition.setPortTypeName("OrderPlaylist");
		defaultWsdl11Definition.setLocationUri("/ws");
		defaultWsdl11Definition.setTargetNamespace("http://example.com/playlist/orderPlaylist");
		defaultWsdl11Definition.setSchema(orderPlaylistSchema);
		return defaultWsdl11Definition;

	}

	@Bean
	public XsdSchema loginSchema() {
		return new SimpleXsdSchema(new ClassPathResource("login.xsd"));
	}
	
	@Bean
	public XsdSchema registerSchema() {
		return new SimpleXsdSchema(new ClassPathResource("register.xsd"));
	}
	
	@Bean
	public XsdSchema unregisterSchema() {
		return new SimpleXsdSchema(new ClassPathResource("unregister.xsd"));
	}
	
	@Bean
	public XsdSchema changePassSchema() {
		return new SimpleXsdSchema(new ClassPathResource("changePassword.xsd"));
	}
	
	@Bean
	public XsdSchema userInfoSchema() {
		return new SimpleXsdSchema(new ClassPathResource("userInfo.xsd"));
	}
	
	@Bean
	public XsdSchema playlistInfoSchema() {
		return new SimpleXsdSchema(new ClassPathResource("playlistInfo.xsd"));
	}
	
	@Bean
	public XsdSchema sendPlaylistSchema() {
		return new SimpleXsdSchema(new ClassPathResource("sendPlaylist.xsd"));
	}
	
	@Bean
	public XsdSchema shufflePlaylistSchema() {
		return new SimpleXsdSchema(new ClassPathResource("shufflePlaylist.xsd"));
	}
	
	@Bean
	public XsdSchema orderPlaylistSchema() {
		return new SimpleXsdSchema(new ClassPathResource("orderPlaylist.xsd"));
	}

}
