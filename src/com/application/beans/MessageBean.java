/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.beans;

/**
 *
 * @author victo
 */
public class MessageBean implements Comparable<MessageBean> {
    
    private String keyMessage;
    private String userSend;
    private String userRecipe;
    private String message;
    private String dateMessage;
    private int status;
    private int typeMessage;
    
    public MessageBean(){}
    
    public MessageBean(String userSend, String userRecipe, String message, String dateMessage, int status, int typeMessage) {
        this.userSend = userSend;
        this.userRecipe = userRecipe;
        this.message = message;
        this.dateMessage = dateMessage;
        this.status = status;
        this.typeMessage = typeMessage;
    }

    public String getKeyMessage() {
        return keyMessage;
    }

    public void setKeyMessage(String keyMessage) {
        this.keyMessage = keyMessage;
    }
    
    public String getUserSend() {
        return userSend;
    }

    public void setUserSend(String userSend) {
        this.userSend = userSend;
    }

    public String getUserRecipe() {
        return userRecipe;
    }

    public void setUserRecipe(String userRecipe) {
        this.userRecipe = userRecipe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(String dateMessage) {
        this.dateMessage = dateMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(int typeMessage) {
        this.typeMessage = typeMessage;
    }

    @Override
    public String toString() {
        return keyMessage + "|" + userSend + "|" + userRecipe + "|" + message + "|" + dateMessage + "|" + status + "|" + typeMessage;
    }
    
    @Override
    public int compareTo(MessageBean arg0) {
        String comparage = ((MessageBean) arg0).getKeyMessage();
        return this.getKeyMessage().compareTo(comparage);
    }
    
}
