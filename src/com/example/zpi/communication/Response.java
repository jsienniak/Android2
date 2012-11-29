package com.example.zpi.communication;

import java.security.PublicKey;

public class Response {

    public static int GET = 0;
    public static int SET = 1;

    private int type =-1;

    private String message = null;

    private String value = null;

    private int module = -1;

    private int port = -1;

    private boolean ERROR =false;

    @Override
    public String toString() {
        return "Response{" +
                "type=" + type +
                ", message='" + message + '\'' +
                ", value='" + value + '\'' +
                ", module=" + module +
                ", port=" + port +
                '}';
    }

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


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isERROR() {
        return ERROR;
    }

    public void setERROR(boolean ERROR) {
        this.ERROR = ERROR;
    }
}
