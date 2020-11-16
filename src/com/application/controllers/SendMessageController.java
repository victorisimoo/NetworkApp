package com.application.controllers;

import com.application.beans.FriendRequest;
import com.application.beans.MessageBean;
import com.application.beans.UserBean;
import com.application.system.Principal;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;

/**
 * @author victorisimo
 */
public class SendMessageController implements Initializable {

    private ObservableList<String> messageType = FXCollections.observableArrayList("Privado","Público");
    private Principal escenarioPrincipal;
    private int organization = 0;
    @FXML private TextField txtSearchUser;
    @FXML private TextField txtMessage;
    @FXML private Label lblError;
    @FXML private Label lblUsername;
    @FXML private Label lblName;
    @FXML private Label lblLastName;
    @FXML private Pane pnUser;
    @FXML private ComboBox cmbMessage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.organization = 3;
        cmbMessage.setItems(messageType);
        this.pnUser.setVisible(false);
        this.lblError.setVisible(false);
        this.cmbMessage.setDisable(true);
        this.txtMessage.setDisable(true);
    }    
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void returnProfile(){
        this.escenarioPrincipal.ventanaNormalUser();
    }
    
    public void search() throws IOException{
        if(txtSearchUser.getText()!= null){
            LoginController loginController = new LoginController();
            UserBean user = loginController.getCompleteUser(txtSearchUser.getText());
            if((user != null) && (!user.getUsername().equals(Storage.Instance().actualUser.getUsername())) && (!analyzeSearchUser(txtSearchUser.getText()))){
                lblError.setVisible(false);
                pnUser.setVisible(true);
                txtMessage.setDisable(false);
                cmbMessage.setDisable(false);
                lblUsername.setText(user.getUsername());
                lblName.setText(user.getName());
                lblLastName.setText(user.getLastName());
                Storage.Instance().secondUser = user;
            }else {
                pnUser.setVisible(false);
                lblError.setVisible(true);
                txtSearchUser.setText("");
                txtMessage.setDisable(false);
                cmbMessage.setDisable(false);
            }
        }else {
            pnUser.setVisible(false);
            lblError.setVisible(true);
            txtSearchUser.setText("");
            txtMessage.setDisable(false);
            cmbMessage.setDisable(false);
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
    
    public Boolean firstAnalyze(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\amigos.txt");;
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[1].equals(username) && (Storage.Instance().actualUser.getUsername().equals(parts[0])) && parts[2].equals("1")){
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
            if(parts[1].equals(username) && (Storage.Instance().actualUser.getUsername().equals(parts[0]) && parts[2].equals("1"))){
                return false;
            }
        }
        return true;
    }
    
    public void sendMessage() throws IOException{
        File file = new File("C:\\MEIA\\bitacora_mensajes.txt");
        if(file.exists()){
            MessageBean message = new MessageBean();
            message.setKeyMessage(Storage.Instance().actualUser.getUsername()+ "," + Storage.Instance().secondUser.getUsername()+ ","+ dateNow());
            message.setUserSend(Storage.Instance().actualUser.getUsername());
            message.setUserRecipe(Storage.Instance().secondUser.getUsername());
            message.setDateMessage(dateNow());
            message.setMessage(txtMessage.getText());
            message.setStatus(0);
            if(cmbMessage.getSelectionModel().getSelectedItem().equals("Privado")){
                message.setTypeMessage(1);
            }else {
                message.setTypeMessage(0);
            }
            if(cantLinesFile() + 1 <= organization){
                writeMessage(file, message);
                updateDescMessages();
                JOptionPane.showMessageDialog(null, "El mensaje fue enviado exitosamente", "mensaje exitoso", JOptionPane.CLOSED_OPTION);
                messageDialog();
            }else {
                readMessageToMessageBitacore();
                writeMessage(file, message);
                updateDescBitMessages();
                JOptionPane.showMessageDialog(null, "El mensaje fue enviado exitosamente", "Reorganización", JOptionPane.CLOSED_OPTION);
                messageDialog();
            }
            
        }else {
            JOptionPane.showMessageDialog(null, "Error en el mensaje enviado, intente nuevamente", "Envío fallido", JOptionPane.OK_OPTION);
            messageDialog();
        }
    }
    
    public void messageDialog(){
        escenarioPrincipal.ventanaMessage();
    }
    
    public String dateNow(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    
    public void writeMessage(File file, MessageBean message) throws IOException {
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printLine = new PrintWriter(fileWriter);
        printLine.print(message.toString() + '\n');
        printLine.close();
        fileWriter.close();   
    }
    
    
    public void updateDescMessages() throws IOException{
        File file_descriptor = new File("C:\\MEIA\\desc_mensajes.txt");
        FileWriter fileWriter = new FileWriter(file_descriptor);
        PrintWriter printLine = new PrintWriter(fileWriter);
       
        printLine.print("nombre_simbolico: desc_mensajes"+ '\n');
        printLine.print("fecha_creacion: " + dateNow()+ '\n');
        printLine.print("usuario_creacion: "+Storage.Instance().actualUser.getUsername()+ '\n');
        printLine.print("#_registros: " + cantLinesMessagesFile()+ '\n');
        printLine.print("max_reorganización: " + organization + '\n');
        printLine.close();
        fileWriter.close();
    }
    
    public void updateDescBitMessages() throws IOException{
        File file_descriptor = new File("C:\\MEIA\\desc_bitacora_mensajes.txt");
        FileWriter fileWriter = new FileWriter(file_descriptor);
        PrintWriter printLine = new PrintWriter(fileWriter);
        
        printLine.print("nombre_simbolico: desc_bitacora_mensajes"+ '\n');
        printLine.print("fecha_creacion: " + dateNow()+ '\n');
        printLine.print("usuario_creacion: "+Storage.Instance().actualUser.getUsername()+ '\n');
        printLine.print("#_registros: " + cantLinesFile()+ '\n');
        printLine.close();
        fileWriter.close();
    }
    
    public int cantLinesMessagesFile() throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\mensajes.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        int cantLines = (int) bf.lines().count();
        bf.close();
        fr.close();
        return cantLines;
    }
    
    public int cantLinesFile() throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_mensajes.txt");
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        int cantLines = (int) bf.lines().count();
        bf.close();
        fr.close();
        return cantLines;
    }
    
    public void readMessageToMessageBitacore() throws FileNotFoundException, IOException{
        ArrayList<MessageBean> messageSends = new ArrayList<>();
        File file = new File("C:\\MEIA\\bitacora_mensajes.txt");
        MessageBean message = new MessageBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            message.setKeyMessage(parts[0]);
            message.setUserSend(parts[1]);
            message.setUserRecipe(parts[2]);
            message.setMessage(parts[3]);
            message.setDateMessage(parts[4]);
            message.setStatus(Integer.parseInt(parts[5]));
            message.setTypeMessage(Integer.parseInt(parts[6]));
            messageSends.add(message);
            message = new MessageBean();
        }
        clearFileFriendBitacore(file);
        addMessageToMessagesFile(messageSends);
    }
    
    public void addMessageToMessagesFile(ArrayList<MessageBean> messages) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\mensajes.txt");
        MessageBean message = new MessageBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null ){
            String parts[] = lineReader.split("\\|");
            message.setKeyMessage(parts[0]);
            message.setUserSend(parts[1]);
            message.setUserRecipe(parts[2]);
            message.setMessage(parts[3]);
            message.setDateMessage(parts[4]);
            message.setStatus(Integer.parseInt(parts[5]));
            message.setTypeMessage(Integer.parseInt(parts[6]));
            messages.add(message);
            message = new MessageBean();
        }
        clearFileFriendBitacore(file);
        Collections.sort(messages);
        writeMessagesToMessagesFile(messages);
    }
    
    public void writeMessagesToMessagesFile(ArrayList<MessageBean> messages) throws IOException {
        File file = new File("C:\\MEIA\\mensajes.txt");
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printLine = new PrintWriter(fileWriter);
        for(MessageBean message : messages){
            if(message.getStatus() != 1){
                printLine.print(message.toString() + '\n');
            }
        }
        printLine.close();
        fileWriter.close();
    }
    
    public void clearFileFriendBitacore(File file) throws IOException{
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("");
        bw.close();
    }
}
