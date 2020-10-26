/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.beans;

import java.time.LocalDateTime;

/**
 *
 * @author ayalr
 */
public class Friends implements Comparable<Friends>{
    private String user;
    private String group;
    private String userFriend;
    private String dateTrans;
    private Integer status;
    private String key;
    
    
    public Friends(){}
    
    public Friends(String user, String group, String userFriend, String dateTrans, Integer status){
        this.user = user;
        this.group = group;
        this.userFriend = userFriend;
        this.dateTrans = dateTrans;
        this.status = status;
    }
    
    public String getUser(){
        return user;
    }
    public void setUser(String user){
        this.user = user;
    }
    
    public String getGroup(){
        return group;
    }
    public void setGroup(String group){
        this.group = group;
    }
    public String getUserFriend(){
        return userFriend;
    }
    public void setUserFriend(String userFriend){
        this.userFriend = userFriend;
    }
    public String getDateTrans(){
        return dateTrans;
    }
    public void setDateTrans(String dateTrans){
        this.dateTrans = dateTrans;
    }
    
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status = status;
    } 
    
    public String getKey(){
        return user + group + userFriend;
    }
    
    @Override
    public String toString() {
        return user + "|" + group + "|" + userFriend + "|" + dateTrans + "|" + status;
    }

    @Override
    public int compareTo(Friends o) {
        String comparage = ((Friends) o).getKey();
        return this.getKey().compareTo(comparage);
    }
    
}
