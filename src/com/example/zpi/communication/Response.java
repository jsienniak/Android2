package com.example.zpi.communication;

public class Response {
	
	private String message = null;

    private String value = null;
	
	public Response(){
	}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMessage(){
		return message;
	}

    public void setMessage(String message) {
        this.message = message;
    }



}
