package com.application.beans;

/**
 * @author victorisimo
 */
public class UserBean {
    
    private String username;
    private String name;
    private String lastName;
    private String password;
    private String birth;
    private String mail;
    private String phone;
    private String pathPhoto;
    private int status;
    private int rolUser;
    
    public UserBean(){}

    public UserBean(String username, String name, String lastName, String password, String birth, String mail, String phone, String pathPhoto, int status, int rolUser) {
        this.username = username;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.birth = birth;
        this.mail = mail;
        this.phone = phone;
        this.pathPhoto = pathPhoto;
        this.status = status;
        this.rolUser = rolUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPathPhoto() {
        return pathPhoto;
    }

    public void setPathPhoto(String pathPhoto) {
        this.pathPhoto = pathPhoto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getRolUser() {
        return rolUser;
    }

    public void setRolUser(int rolUser) {
        this.rolUser = rolUser;
    }

    @Override
    public String toString() {
        return username + "|" + name + "|" + lastName + "|" + password + "|" + birth + "|" + mail + "|" + phone + "|"+ status+"|"+ rolUser;
    }
    
    
    
}
