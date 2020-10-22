package com.application.controllers;

import com.application.system.Principal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * @author victo
 */
public class AdminUserController implements Initializable {
    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    } 
    
}
