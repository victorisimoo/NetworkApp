package com.application.controllers;    

import com.application.system.Principal;
import com.application.system.Storage;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * @author victorisimo
 */
public class NormalUserController implements Initializable {
    private Principal escenarioPrincipal;
    @FXML private Label lblUser;
    @FXML private Label lblName;
    @FXML private Label lblLastName;
    @FXML private TextField txtPhone;
    @FXML private DatePicker dtpCalendar;
    @FXML private TextArea txtNotify;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblUser.setText(Storage.Instance().actualUser.getUsername());
        lblName.setText(Storage.Instance().actualUser.getName());
        lblLastName.setText(Storage.Instance().actualUser.getLastName());
        txtPhone.setText(Storage.Instance().actualUser.getPhone());
        dtpCalendar.setValue(LocalDate.now());
        txtNotify.disableProperty();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }    
    
}
