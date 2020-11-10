/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.controllers;

import com.application.beans.UserBean;
import com.application.system.Principal;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 * @author victo
 */
public class SearchController implements Initializable {
    private Principal escenarioPrincipal;
    @FXML private TextField txtSearchUser;
    @FXML private Label lblError;
    @FXML private Label lblUsername;
    @FXML private Label lblName;
    @FXML private Label lblLastName;
    @FXML private Pane pnUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pnUser.setVisible(false);
        lblError.setVisible(false);
    }    
    
        public void search() throws IOException{
        if(txtSearchUser.getText()!= null){
            LoginController loginController = new LoginController();
            UserBean user = loginController.getCompleteUser(txtSearchUser.getText());
            if((user != null) && (!user.getUsername().equals(Storage.Instance().actualUser.getUsername())) && (analyzeSearchUser(txtSearchUser.getText()))){
                lblError.setVisible(false);
                pnUser.setVisible(true);
                lblUsername.setText(user.getUsername());
                lblName.setText(user.getName());
                lblLastName.setText(user.getLastName());
                Storage.Instance().secondUser = user;
            }else {
                pnUser.setVisible(false);
                lblError.setVisible(true);
                txtSearchUser.setText("");
            }
        }else {
            pnUser.setVisible(false);
            lblError.setVisible(true);
            txtSearchUser.setText("");
        }
    }
    
    public Boolean analyzeSearchUser(String username) throws IOException{
        if(!firstAnalyze(username)){
            return false;
        }
        if(!secondAnalyze(username)){
            return false;
        }
        return true;
    }
    
    public void profileSearch(){
        escenarioPrincipal.ventanaSearchUser();
    }
    
    public Boolean firstAnalyze(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\amigos.txt");;
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[1].equals(username) && (Storage.Instance().actualUser.getUsername().equals(parts[0]))){
                return false;
            }
        }
        return true;
    }
    
    public Boolean secondAnalyze(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_amigo.txt");;
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[1].equals(username) && (Storage.Instance().actualUser.getUsername().equals(parts[0]))){
                return false;
            }
        }
        return true;
    }
    
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void openProfile(){
        escenarioPrincipal.ventanaNormalUser();
    }
    
}
