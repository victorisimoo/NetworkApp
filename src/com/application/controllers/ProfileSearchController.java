package com.application.controllers;

import com.application.beans.MessageBean;
import com.application.system.Principal;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * @author victorisimo
 */
public class ProfileSearchController implements Initializable {
    private Principal escenarioPrincipal;
    @FXML private Label lblUser;
    @FXML private Label lblName;
    @FXML private Label lblLastName;
    @FXML private Label lblDate;  
    @FXML private TextArea txtPublicMessages;
    ArrayList<MessageBean> publicMessages = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            obtenerMensajesPublicos(Storage.Instance().secondUser.getUsername());
            obtenerMensajesPublicosSegundo(Storage.Instance().secondUser.getUsername());
            setTextAreaMessages();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        lblUser.setText(Storage.Instance().secondUser.getUsername());
        lblName.setText(Storage.Instance().secondUser.getName());
        lblLastName.setText(Storage.Instance().secondUser.getLastName());
        lblDate.setText(Storage.Instance().secondUser.getBirth());
        txtPublicMessages.setDisable(true);
    }    
    
    public void setTextAreaMessages(){
        String mensajes = "";
        for(MessageBean message: publicMessages){
            mensajes = mensajes + message.getUserSend() + ": "+ message.getMessage() + '\n';
        }
        txtPublicMessages.setText(mensajes);
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }   
    
    public void returnProfile(){
        escenarioPrincipal.ventanaNormalUser();
    }
    
    public void obtenerMensajesPublicos(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_mensajes.txt");
        MessageBean message = new MessageBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[2].equals(Storage.Instance().secondUser.getUsername())){
                if(Integer.parseInt(parts[6]) == 0){
                    message.setKeyMessage(parts[0]);
                    message.setUserSend(parts[1]);
                    message.setUserRecipe(parts[2]);
                    message.setMessage(parts[3]);
                    message.setDateMessage(parts[4]);
                    message.setStatus(Integer.parseInt(parts[5]));
                    message.setTypeMessage(Integer.parseInt(parts[6]));
                    publicMessages.add(message);
                    message = new MessageBean();
                }
            }
            
        }
    }
    
    public void obtenerMensajesPublicosSegundo(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\mensajes.txt");
        MessageBean message = new MessageBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[2].equals(Storage.Instance().secondUser.getUsername())){
                if(Integer.parseInt(parts[6]) == 0){
                    message.setKeyMessage(parts[0]);
                    message.setUserSend(parts[1]);
                    message.setUserRecipe(parts[2]);
                    message.setMessage(parts[3]);
                    message.setDateMessage(parts[4]);
                    message.setStatus(Integer.parseInt(parts[5]));
                    message.setTypeMessage(Integer.parseInt(parts[6]));
                    publicMessages.add(message);
                    message = new MessageBean();
                }
            }
            
        }
    }
    
}
