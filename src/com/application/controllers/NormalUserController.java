package com.application.controllers;    

import com.application.beans.FriendRequest;
import com.application.system.Principal;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * @author victorisimo
 */
public class NormalUserController implements Initializable {
    
    private Principal escenarioPrincipal;
    ArrayList<FriendRequest> requests = new ArrayList<>();
    @FXML private Label lblUser;
    @FXML private Label lblName;
    @FXML private Label lblLastName;
    @FXML private TextField txtPhone;
    @FXML private DatePicker dtpCalendar;
    @FXML private TextArea txtNotify;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getFriendRequestBitacora(Storage.Instance().actualUser.getUsername());
            getFriendRequestFriendFile(Storage.Instance().actualUser.getUsername());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        lblUser.setText(Storage.Instance().actualUser.getUsername());
        lblName.setText(Storage.Instance().actualUser.getName());
        lblLastName.setText(Storage.Instance().actualUser.getLastName());
        txtPhone.setText(Storage.Instance().actualUser.getPhone());
        dtpCalendar.setValue(LocalDate.now());
        txtNotify.disableProperty();
        analyzeFriendsRequest();
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }    
    
    public void searUserVentana(){
        escenarioPrincipal.ventanaSearch();
    }
    
    public void analyzeFriendsRequest(){
        if(requests.size() > 0){
            for (FriendRequest request : requests) {
                int reply = JOptionPane.showConfirmDialog(null, request.getUser() + " te ha enviado una solicitud de amistad", "Nueva solicitud de amistad", JOptionPane.YES_NO_OPTION);
                if( reply == JOptionPane.YES_OPTION){
                    
                }else {
                    
                }
            }
        }
    }
    
    public void getFriendRequestBitacora(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_amigo.txt");
        FriendRequest request = new FriendRequest();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        int counter = 0;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            counter++;
            if(parts[1].equals(username)){
                request.setUser(parts[0]);
                request.setUserFriend(parts[1]);
                request.setResponse(Integer.parseInt(parts[2]));
                request.setDateRequest(parts[3]);
                request.setUserRequest(parts[4]);
                request.setStatus(Integer.parseInt(parts[5]));
                request.setDocument(0);
                request.setLine(counter);
                requests.add(request);
                request = new FriendRequest();
            }
        }
    }
    
    public void getFriendRequestFriendFile(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\amigos.txt");
        FriendRequest request = new FriendRequest();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        int counter = 0;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            counter++;
            if(parts[1].equals(username)){
                request.setUser(parts[0]);
                request.setUserFriend(parts[1]);
                request.setResponse(Integer.parseInt(parts[2]));
                request.setDateRequest(parts[3]);
                request.setUserRequest(parts[4]);
                request.setStatus(Integer.parseInt(parts[5]));
                request.setDocument(1);
                request.setLine(counter);
                requests.add(request);
                request = new FriendRequest();
            }
        }
    }
    
    public void changeRegisterInFile(FriendRequest friend, File file){
        
        
    }
    
    
}
