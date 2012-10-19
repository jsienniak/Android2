package com.example.zpi.communication;

public class Response {
	
	private String message = null;
	
	public Response(String msg){
		message = msg;
	}
	
	public String getMessage(){
		return message;
	}
	

}
