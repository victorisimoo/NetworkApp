
package com.application.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author victorisimoo
 */
public class FriendRequest {
    
    private String user;
    private String  userFriend;
    private int response;
    private String dateRequest;
    private String userRequest;
    private int status;
    
    public FriendRequest(){}

    public FriendRequest(String user, String userFriend, int response, String dateRequest, String userRequest, int status) {
        this.user = user;
        this.userFriend = userFriend;
        this.response = response;
        this.dateRequest = dateRequest;
        this.userRequest = userRequest;
        this.status = status;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserFriend() {
        return userFriend;
    }

    public void setUserFriend(String userFriend) {
        this.userFriend = userFriend;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public String getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest() {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        this.dateRequest = formatter.format(date);
    }

    public String getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(String userRequest) {
        this.userRequest = userRequest;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return user + "|" + userFriend + "|" + response + "|" + dateRequest + "|" + userRequest + "|" + status;
    }
    
    
    
    
}
