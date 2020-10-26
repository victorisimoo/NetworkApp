
package com.application.beans;

/**
 * @author josed
 */
public class GroupBean implements Comparable<GroupBean>{
    
    private String user;
    private String type;
    private String description;
    private String cero;
    private String birth;
    private String status;
    private String key;
    
    public GroupBean(){}
    
    public GroupBean(String user, String type, String description, String cero, String birth, String status){
        this.user = user;
        this.type = type;
        this.description = description;
        this.cero = cero;
        this.birth = birth;
        this.status = status;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCero() {
        return cero;
    }

    public void setCero(String cero) {
        this.cero = cero;
    }
    
    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getKey() {
        return user + type;
    }
    
    @Override
    public String toString() {
        return user + "|" + type + "|" + description + "|" + cero + "|" + birth + "|" + user + "|" + status;
    }

    @Override
    public int compareTo(GroupBean arg0) {
        String comparage = ((GroupBean) arg0).getKey();
        return this.getKey().compareTo(comparage);
    }
}
