package com.application.controllers;

import com.application.beans.FriendRequest;
import com.application.beans.UserBean;
import com.application.system.Principal;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * @author victo
 */
public class UserSearchController implements Initializable {
    private Principal escenarioPrincipal;
    @FXML private TextField txtSearchUser;
    @FXML private Label lblError;
    @FXML private Label lblUsername;
    @FXML private Label lblName;
    @FXML private Label lblLastName;
    @FXML private Pane pnUser;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.pnUser.setVisible(false);
        lblError.setVisible(false);

    }    
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
    this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void search() throws IOException{
        LoginController loginController = new LoginController();
        UserBean user = loginController.getCompleteUser(txtSearchUser.getText());
        if(user != null && user != Storage.Instance().actualUser){
            pnUser.setVisible(true);
            lblUsername.setText(user.getUsername());
            lblName.setText(user.getName());
            lblLastName.setText(user.getLastName());
        }else {
            lblError.setVisible(true);
            txtSearchUser.setText("");
        }
    }
    
    public void addFriend() throws IOException{
        File file = new File("C:\\MEIA\\bitacora_amigo.txt");
        if(file.exists()){
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setUser(Storage.Instance().actualUser.getUsername());
            friendRequest.setUserFriend(txtSearchUser.getText());
            friendRequest.setResponse(0);
            friendRequest.setDateRequest();
            friendRequest.setUserRequest(Storage.Instance().actualUser.getUsername());
            friendRequest.setStatus(1);
            if(cantLinesFile() < 3){
                writeFriend(file, friendRequest);
                updateDescriptorFriends();
                JOptionPane.showMessageDialog(null, "La solicitud fue enviada con éxito", "Solicitud exitosa", JOptionPane.CLOSED_OPTION);
                addNewUser();
            }else {
                //Reorganización
            }
        }else {
            JOptionPane.showMessageDialog(null, "Solicitud de seguimiento fallida, intente nuevamente", "Solicitud fallida", JOptionPane.OK_OPTION);
            addNewUser();
        }
    }
    
    public void cancel(){
        escenarioPrincipal.ventanaNormalUser();
    }
    
    public void addNewUser(){
        escenarioPrincipal.ventanaSearch();
    }
    
    public void updateDescriptorFriends() throws IOException{
        File file_descriptor = new File("C:\\MEIA\\desc_bitacora_amigo.txt");
        FileWriter fileWriter = new FileWriter(file_descriptor);
        PrintWriter printLine = new PrintWriter(fileWriter);
       
        printLine.print("nombre_simbolico: desc_bitacora_amigo");
        printLine.print("fecha_creacion: " + dateNow());
        printLine.print("usuario_creacion: "+Storage.Instance().actualUser.getUsername());
        printLine.print("#_registros: " + cantLinesFile());
        printLine.print("max_reorganización: " + 3);
    }
    
    public void writeFriend(File file, FriendRequest friend) throws IOException{
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printLine = new PrintWriter(fileWriter);
        printLine.print(friend.toString() + '\n');
        printLine.close();
        fileWriter.close();
    }
    
    public void readFriend(){
        
    }
    
    
    public String dateNow(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public int cantLinesFile() throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_amigo.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        int cantLines = (int) bf.lines().count();
        bf.close();
        return cantLines;
    }
    
    
    
}
