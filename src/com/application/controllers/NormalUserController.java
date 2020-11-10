package com.application.controllers;    

import com.application.beans.FriendRequest;
import com.application.beans.MessageBean;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;

/**
 * @author victorisimo
 */
public class NormalUserController implements Initializable {
    
    private Principal escenarioPrincipal;
    ArrayList<FriendRequest> requests = new ArrayList<>();
    ArrayList<MessageBean> publicMessages = new ArrayList<>();
    ArrayList<MessageBean> privateMessages = new ArrayList<>();
    @FXML private Label lblUser;
    @FXML private Label lblName;
    @FXML private Label lblLastName;
    @FXML private Label lblDate;   
    @FXML private TextArea txtPublicMessages;
    @FXML private TextArea txtPrivateMessages;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getFriendRequestBitacora(Storage.Instance().actualUser.getUsername());
            getFriendRequestFriendFile(Storage.Instance().actualUser.getUsername());
            obtenerMensajesPublicos(Storage.Instance().actualUser.getUsername());
            obtenerMensajesPublicosSegundo(Storage.Instance().actualUser.getUsername());
            setTextAreaMessages();
            obtenerMensajesPrivados(Storage.Instance().actualUser.getUsername());
            obtenerMensajesPrivadosSegundo(Storage.Instance().actualUser.getUsername());
            setTextAreaPrivateMessages();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        lblUser.setText(Storage.Instance().actualUser.getUsername());
        lblName.setText(Storage.Instance().actualUser.getName());
        lblLastName.setText(Storage.Instance().actualUser.getLastName());
        lblDate.setText(Storage.Instance().actualUser.getBirth());
        txtPublicMessages.setDisable(true);
        txtPrivateMessages.setDisable(true);
        try {
            analyzeFriendsRequest();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }    
    
    public void searchUserVentana(){
        escenarioPrincipal.ventanaSearch();
    }
    
    public void openGroups(){
        escenarioPrincipal.ventanaGroups();
    }
    
    public void openSearchProfile(){
        escenarioPrincipal.ventanaSearchProfile();
    }
    
    public void analyzeFriendsRequest() throws IOException{
        if(requests.size() > 0){
            for (FriendRequest request : requests) {
                int reply = JOptionPane.showConfirmDialog(null, request.getUser() + " te ha enviado una solicitud de amistad", "Nueva solicitud de amistad", JOptionPane.YES_NO_OPTION);
                if( reply == JOptionPane.YES_OPTION){
                    if(request.getDocument() == 1){
                        File file = new File("C:\\MEIA\\amigos.txt");
                        changeRegisterInFile(request, file, 1, obtenerRegistros());
                    }else {
                        File file = new File("C:\\MEIA\\bitacora_amigo.txt");
                        changeRegisterInFile(request, file, 1, obtenerRegistrosTwo());
                    }
                }else {
                    if(request.getDocument() == 1){
                        File file = new File("C:\\MEIA\\amigos.txt");
                        changeRegisterInFile(request, file, 0, obtenerRegistros());
                    }else {
                        File file = new File("C:\\MEIA\\bitacora_amigo.txt");
                        changeRegisterInFile(request, file, 0, obtenerRegistrosTwo());
                    } 
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
        reader.close();
        bufferReader.close();
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
        reader.close();
        bufferReader.close();
    }
    
    public void changeRegisterInFile(FriendRequest friend, File file, int response, ArrayList<FriendRequest> friends) throws FileNotFoundException, IOException{
        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printLine = new PrintWriter(fileWriter);
        for (FriendRequest friendRequest : friends) {
            if(friendRequest.getKeyRequest().equals(friend.getKeyRequest())){
                friendRequest.setResponse(response);
                friendRequest.setStatus(1);
                printLine.print(friendRequest.toString() + '\n');
            }else {
                printLine.print(friendRequest.toString() + '\n');
            }
        }
        printLine.close();
        fileWriter.close();
    }
    
    public ArrayList<FriendRequest> obtenerRegistros() throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\amigos.txt");
        ArrayList<FriendRequest> firstFile = new ArrayList<>();
        FriendRequest request = new FriendRequest();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");

            request.setUser(parts[0]);
            request.setUserFriend(parts[1]);
            request.setResponse(Integer.parseInt(parts[2]));
            request.setDateRequest(parts[3]);
            request.setUserRequest(parts[4]);
            request.setStatus(Integer.parseInt(parts[5]));
            firstFile.add(request);
            request = new FriendRequest();
        }
        
        reader.close();
        bufferReader.close();
        return firstFile;
    }
    
    public ArrayList<FriendRequest> obtenerRegistrosTwo() throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_amigo.txt");
        ArrayList<FriendRequest> secondFile = new ArrayList<>();
        FriendRequest request = new FriendRequest();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");

            request.setUser(parts[0]);
            request.setUserFriend(parts[1]);
            request.setResponse(Integer.parseInt(parts[2]));
            request.setDateRequest(parts[3]);
            request.setUserRequest(parts[4]);
            request.setStatus(Integer.parseInt(parts[5]));
            secondFile.add(request);
            request = new FriendRequest();
        }
        reader.close();
        bufferReader.close();
        return secondFile;
    }
    
    
    public void AddToGroup(){
        escenarioPrincipal.ventanaAddFriendsToGroup();
    }
    
    public void ventanaMessage(){
        escenarioPrincipal.ventanaMessage();
    }
    
    public void reorganize() throws IOException{
        AddFriendsToGroupController addFriendsG = new AddFriendsToGroupController();
        CreateGropusController createGropus = new CreateGropusController();
        UserSearchController userSearch = new UserSearchController();
        userSearch.readFriendsToFriendsBitacore();
        userSearch.updateDescriptorFriendsFile();
        addFriendsG.reorganize();
        createGropus.reorganizationExit();
        System.exit(0);
    }
    
    public void obtenerMensajesPublicos(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_mensajes.txt");
        MessageBean message = new MessageBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[2].equals(Storage.Instance().actualUser.getUsername())){
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
            if(parts[2].equals(Storage.Instance().actualUser.getUsername())){
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
    
    public void setTextAreaMessages(){
        String mensajes = "";
        for(MessageBean message: publicMessages){
            mensajes = mensajes + message.getUserSend() + ": "+ message.getMessage() + '\n';
        }
        txtPublicMessages.setText(mensajes);
    }
    
    
    public void obtenerMensajesPrivados(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\bitacora_mensajes.txt");
        MessageBean message = new MessageBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[2].equals(Storage.Instance().actualUser.getUsername())){
                if(Integer.parseInt(parts[6]) == 1){
                    message.setKeyMessage(parts[0]);
                    message.setUserSend(parts[1]);
                    message.setUserRecipe(parts[2]);
                    message.setMessage(parts[3]);
                    message.setDateMessage(parts[4]);
                    message.setStatus(Integer.parseInt(parts[5]));
                    message.setTypeMessage(Integer.parseInt(parts[6]));
                    privateMessages.add(message);
                    message = new MessageBean();
                }
            }
            
        }
    }
    
    public void obtenerMensajesPrivadosSegundo(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\mensajes.txt");
        MessageBean message = new MessageBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine()) != null){
            String parts[] = lineReader.split("\\|");
            if(parts[2].equals(Storage.Instance().actualUser.getUsername())){
                if(Integer.parseInt(parts[6]) == 1){
                    message.setKeyMessage(parts[0]);
                    message.setUserSend(parts[1]);
                    message.setUserRecipe(parts[2]);
                    message.setMessage(parts[3]);
                    message.setDateMessage(parts[4]);
                    message.setStatus(Integer.parseInt(parts[5]));
                    message.setTypeMessage(Integer.parseInt(parts[6]));
                    privateMessages.add(message);
                    message = new MessageBean();
                }
            }
        }
    }
    
    public void setTextAreaPrivateMessages(){
        String mensajes = "";
        for(MessageBean message: privateMessages){
            mensajes = mensajes + message.getUserSend() + ": "+ message.getMessage() + '\n';
        }
        txtPrivateMessages.setText(mensajes);
    }
}
