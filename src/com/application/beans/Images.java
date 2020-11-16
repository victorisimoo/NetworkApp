package com.application.beans;
/**
 *
 * @author ayalr
 */
public class Images {
    int register;
    int right;
    int left;
    String user;
    String path;
    String date;
    int status;
    
    //Setters
    public void setRegister(int register){ this.register = register; }
    public void setRight(int right){ this.right = right; } 
    public void setLeft(int left){ this.left = left; } 
    public void setUser(String user){ this.user = user; }
    public void setPath(String path){ this.path = path; } 
    public void setDate(String date){ this.date = date; }
    public void setStatus(int status){ this.status = status; }
    
    //Getters
    public int getRegister(){ return register; }
    public int getRight(){ return right; }
    public int getLeft(){ return left; }
    public String getUser(){ return user; }
    public String getPath(){ return path; }
    public String getDate(){ return date; }
    public int getStatus(){ return status; }
    
    
    @Override
    public String toString() {
        return register + "|" + left + "|" + right + "|" + user + "|" + path + "|" + date + "|" + status;
    }
}
