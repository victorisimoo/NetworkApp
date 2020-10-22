package com.application.system;

import com.application.beans.UserBean;

/**
 *
 * @author victorisimo
 */
public class Storage {
    
    public Storage(){}
    
    private static Storage _instance = null;
    
    public static Storage Instance(){
        if(_instance == null)
            _instance = new Storage();
        return _instance;
    }
    
    public UserBean actualUser = new UserBean();
}
