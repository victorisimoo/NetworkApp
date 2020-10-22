/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.controllers;

import com.application.beans.Friends;
import com.application.beans.Index;
import com.application.constructor.UserConstructor;
import com.application.system.Principal;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author ayalr
 */
public class AddFriendsGroupController implements Initializable {
    private Principal escenarioPrincipal;
    @FXML private TextField txtDeleteFriend;
    @FXML private TextField txtAddFriend;
    @FXML private TextField txtGroup;
    
    int initialD = 0;
    int registers = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void addNewFriend(){
        
        try {
            Friends newFriend = new Friends();
            newFriend.setUser(Storage.Instance().actualUser.getUsername());
            newFriend.setDateTrans(java.time.LocalDateTime.now());
            newFriend.setStatus(1);
            newFriend.setUserFriend(txtAddFriend.getText());
            newFriend.setGroup(txtGroup.getText());
            String strError= "";
            addFriend(strError, newFriend);
            addToIndex(newFriend);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(AddFriendsGroupController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }  
    public void addFriend(String strError, Friends newFriend){
        try{
            UserConstructor userC = new UserConstructor();
            File MEIA = new File("C:\\MEIA");
            if (!MEIA.exists()) {
                MEIA.mkdir();
            }
            File usuario = new File("C:\\MEIA\\grupo_amigos_n.txt");
            if (!usuario.exists()) {
                usuario.createNewFile();
            }
            if(usuario.exists()== true && userC.getUserByUsername(newFriend.getUserFriend(), "")!= null) {
            FileReader LecturaArchivo;
                LecturaArchivo = new FileReader(usuario);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";

                    Linea=LeerArchivo.readLine();
                    String[] split;
                    FileWriter fw = new FileWriter(usuario, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    
                    while(Linea != null) {
                        
                        Linea=LeerArchivo.readLine();
                        split = Linea.split("|");
                        if (split[0] == newFriend.getUser() && split[1] == newFriend.getGroup() && split[2] == newFriend.getUserFriend()) {
                            strError="El usuario que desea agregar ya existe en el grupo.";
                            return;
                        }
                    }
                    bw.write(newFriend.toString());
                    bw.newLine();
                    bw.close();
                    LecturaArchivo.close();
                    LeerArchivo.close();
                    
                    strError="";
        }
        else {
            strError="El usuario que desea agregar no existe.";
        }
        }
        catch(Exception ex){
            strError= ex.getMessage();
        }
    }

    public void addToIndex(Friends newFriend) throws IOException{
        File index = new File("C:\\MEIA\\indice.txt");
        File descIndice = new File("C:\\MEIA\\desc_indice_grupo.txt");
            if (!index.exists()) {
                index.createNewFile();
            }
            if(!descIndice.exists()){
                descIndice.createNewFile();
                writeInDescIndex(descIndice,Storage.Instance().actualUser.getUsername(),1);
            }
            if(index.exists()== true) {
           
                Index newIndex = new Index();
                readDescIndex();
                FileReader LecturaArchivo;
                LecturaArchivo = new FileReader(index);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                Index[] info = new Index[registers];
                
                newIndex.setUser(newFriend.getUser());
                newIndex.setGroup(newFriend.getGroup());
                newIndex.setUserFriend(newFriend.getUserFriend());
                newIndex.setRegister(registers);
                newIndex.setStatus(1);
                
                Linea=LeerArchivo.readLine();
                FileWriter fw = new FileWriter(index, true);
                BufferedWriter bw = new BufferedWriter(fw);
                Integer counter = 0;
                while(Linea != null) {
                    Linea=LeerArchivo.readLine();
                    info[counter].setRegister(counter+1);
                    info[counter].setPosition(Linea.split("|")[1]);
                    info[counter].setUser(Linea.split("|")[2]);
                    info[counter].setGroup(Linea.split("|")[3]);
                    info[counter].setUserFriend(Linea.split("|")[4]);
                    info[counter].setNextPosition(Integer.parseInt(Linea.split("|")[5]));
                    info[counter].setStatus(Integer.parseInt(Linea.split("|")[6]));
                }
                
                bw.write(newIndex.toString());
                bw.newLine();
                bw.close();
                LecturaArchivo.close();
                LeerArchivo.close();
            
        }
    }
    public void addIndex(Index newIndex, Index[] indexI, Integer initial,Integer prevInit, Integer size){
        Integer newInitial = indexI[initial].getNextPosition();
        if(newIndex.getUser().equals(indexI[initial].getUser())){
            if(newIndex.getGroup().equals(indexI[initial].getGroup())){
                if (newIndex.getUserFriend().equals(indexI[initial].getUserFriend())) {
                   return;
                }
                else if (newIndex.getGroup().compareTo(indexI[initial].getUser()) < 0 && indexI[initial].getNextPosition()!= 0) {
                    addIndex(newIndex,indexI, newInitial, indexI[initial].getRegister(), size);
                }
                else{
                    indexI[initial].setNextPosition(prevInit);
                    newIndex.setNextPosition(initial);
                    indexI[size] = newIndex;
                }
            }
            else if (newIndex.getGroup().compareTo(indexI[initial].getUser()) < 0 && indexI[initial].getNextPosition()!= 0) {
                addIndex(newIndex,indexI, newInitial, indexI[initial].getRegister(), size);
            }
            else{
                    indexI[initial].setNextPosition(prevInit);
                    newIndex.setNextPosition(initial);
                    indexI[size] = newIndex;
                }
        }
        else if (newIndex.getGroup().compareTo(indexI[initial].getUser()) < 0 && indexI[initial].getNextPosition()!= 0) {
            addIndex(newIndex,indexI, newInitial,indexI[initial].getRegister(), size);
        }
        else{
            indexI[initial].setNextPosition(prevInit);
            newIndex.setNextPosition(initial);
            indexI[size] = newIndex;
        }
    }
    
    public void readDescIndex(){
        UserConstructor userC = new UserConstructor();
            File MEIA = new File("C:\\MEIA");
            if (!MEIA.exists()) {
                MEIA.mkdir();
            }
            File usuario = new File("C:\\MEIA\\desc_indice_grupo.txt");
            if(usuario.exists()== true) {
               
            try {
                FileReader LecturaArchivo;
                LecturaArchivo = new FileReader(usuario);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                Integer counter = 0;
                Linea=LeerArchivo.readLine();
                while(Linea != null && counter < 10) {
                    Linea=LeerArchivo.readLine();
                    if(counter == 8){
                        initialD = Integer.parseInt(Linea.split(":")[1]);
                    }
                    counter++;
                }
                
                LecturaArchivo.close();
                LeerArchivo.close();
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(AddFriendsGroupController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(AddFriendsGroupController.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
                
        }
    }
    //Write the index description
    public void writeInDescIndex(File descIndice, String userName,Integer Initial) throws IOException{
                FileReader DescInit;
                DescInit = new FileReader(descIndice);
                BufferedReader LeerArchivo = new BufferedReader(DescInit);
                String Line = "";
                Line = LeerArchivo.readLine();
                FileWriter fw = new FileWriter(descIndice, false);
                BufferedWriter bw = new BufferedWriter(fw);
                String[] information = new String[10];
                Integer counter = 0;
                while(Line != null) {
                    Line=LeerArchivo.readLine();
                    information[counter] = Line;
                    counter++;
                }
                if(counter == 0){
                    information[0] = "nombre_simbolico:desc_indice_grupo";
                    information[1] = "fecha_creacion:"+java.time.LocalDateTime.now();
                    information[2] = "usuario_creacion:"+userName;
                    information[3] = "fecha_modificacion:"+java.time.LocalDateTime.now();
                    information[4] = "usuario_modificacion:"+userName;
                    information[5] = "#_registros:1";
                    information[6] = "registros_activos:1";
                    information[7] = "registros_inactivos:0";
                    information[8] = "registro_inicial:"+Initial;
                    information[9] = "no_bloques:1"; 
                }
                else{
                    Integer[] activeUser = new Integer[2];
                    activeUsers(activeUser);
                    information[3] = "fecha_modificacion:"+java.time.LocalDateTime.now();
                    information[4] = "usuario_modificacion:"+userName;
                    information[5] = "#_registros:"+registers;
                    information[6] = "registros_activos:"+activeUser[1];
                    information[7] = "registros_inactivos:"+activeUser[0];
                    information[8] = "registro_inicial:"+Initial; 
                }
                for (int i = 0; i < information.length; i++) {
                    bw.write(information[i]);
                    bw.newLine();
                }
                
                bw.newLine();
                bw.close();
                DescInit.close();
                LeerArchivo.close();
    }
    //Count the active and inactive users
    public void activeUsers(Integer[] activeUser) throws IOException{
            UserConstructor userC = new UserConstructor();
            File MEIA = new File("C:\\MEIA");
            if (!MEIA.exists()) {
                MEIA.mkdir();
            }
            File usuario = new File("C:\\MEIA\\grupo_amigos_n.txt");
            if(usuario.exists()== true) {
                    FileReader LecturaArchivo;
                    LecturaArchivo = new FileReader(usuario);
                    BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                    String Linea="";
                    activeUser[0]=0;
                    activeUser[1]=0;
                    activeUser[2]=0;
                    Linea=LeerArchivo.readLine();
                    while(Linea != null) {
                        Linea=LeerArchivo.readLine();
                        if (Linea.substring(Linea.length()-1,1)== "0") {
                            activeUser[0]++;
                        }
                        else{
                            activeUser[1]++;
                        }
                        registers++;
                    }
                    LecturaArchivo.close();
                    LeerArchivo.close();
        }
        
    }
    
}

