/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.beans;

/**
 *
 * @author ayalr
 */
public class Index {
    private Integer register;
    private String position;
    private String user;
    private String group;
    private String userFriend;
    private Integer nextPosition;
    private Integer status;
    
    
    public Index(){}
    
    public Index(Integer register, String position, String user, String userFriend, String group, Integer nextPosition, Integer status){
        this.register = register;
        this.position = position;
        this.user = user;
        this.group = group;
        this.userFriend = userFriend;
        this.nextPosition = nextPosition;
        this.status = status;
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
    
    public Integer getRegister(){
        return register;
    }
    public void setRegister(Integer register){
        this.register = register;
    }
    
    public String getPosition(){
        return position;
    }
    public void setPosition(String position){
        this.position = position;
    }
    
    public String getUser(){
        return user;
    }
    public void setUser(String user){
        this.user = user;
    }
    
    public Integer getNextPosition(){
        return nextPosition;
    }
    public void setNextPosition(Integer nextPosition){
        this.nextPosition = nextPosition;
    }
    
    public Integer getStatus(){
        return status;
    }
    public void setStatus(Integer status){
        this.status = status;
    }
    @Override
    public String toString() {
        return register + "|" + position + "|" + user + "|" + group + "|" + userFriend + "|" + nextPosition + "|" + status;
    }
}

