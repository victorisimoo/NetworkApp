/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.controllers;

import com.application.beans.Friends;
import com.application.beans.Index;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ayalr
 */
public class AddFriendsToGroupController implements Initializable {

    private Principal escenarioPrincipal;
    @FXML private TextField txtAddFriend;
    @FXML private TextField txtGroup;
    @FXML private TextField txtDeleteFriend;
    @FXML private TextField txtDeleteGroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Principal geteEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    int initialD = 0;
    int registers = 0;

    // <editor-fold defaultstate="collapsed" desc="Add friends to group">
    public boolean ifExist(String user, String group, String userFriend) throws IOException {
        File usuario = new File("C:\\MEIA\\grupo_amigos_n.txt");
        if (usuario.exists() == true) {
            FileReader LecturaArchivo;
            LecturaArchivo = new FileReader(usuario);
            BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
            String Linea = "";
            Linea = LeerArchivo.readLine();
            while (Linea != null) {
                if (Linea.split("|")[0].equalsIgnoreCase(user) && Linea.split("|")[1].equalsIgnoreCase(group) && Linea.split("|")[2].equalsIgnoreCase(userFriend)) {
                    return true;
                }
                Linea = LeerArchivo.readLine();
            }
            LecturaArchivo.close();
            LeerArchivo.close();

        }
        return false;
    }

    public void addNewFriend() throws IOException {
        Friends newFriend = new Friends();
        newFriend.setUser(Storage.Instance().actualUser.getUsername());
        newFriend.setDateTrans(java.time.LocalDateTime.now());
        newFriend.setStatus(1);
        newFriend.setUserFriend(txtAddFriend.getText());
        newFriend.setGroup(txtGroup.getText());

        if (ifExist(Storage.Instance().actualUser.getUsername(), txtGroup.getText(), txtAddFriend.getText())) {
            JOptionPane.showMessageDialog(null, "El grupo ingresado no corresponde a un grupo creado por el usuario.", "Error al añadir amigo", JOptionPane.OK_OPTION);
        } else if (addFriend(newFriend)) {
            addToIndex(newFriend);
            escenarioPrincipal.ventanaNormalUser();
        }

    }

    public boolean addFriend(Friends newFriend) {
        try {
            File MEIA = new File("C:\\MEIA");
            if (!MEIA.exists()) {
                MEIA.mkdir();
            }
            File usuario = new File("C:\\MEIA\\grupo_amigos_n.txt");
            if (!usuario.exists()) {
                usuario.createNewFile();
            }
            if (usuario.exists() == true && getCompleteUser(newFriend.getUserFriend()) != null) {
                FileReader LecturaArchivo;
                LecturaArchivo = new FileReader(usuario);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea = "";

                Linea = LeerArchivo.readLine();
                String[] split;
                FileWriter fw = new FileWriter(usuario, true);
                BufferedWriter bw = new BufferedWriter(fw);

                while (Linea != null) {
                    split = Linea.split("|");
                    if (split[0] == newFriend.getUser() && split[1] == newFriend.getGroup() && split[2] == newFriend.getUserFriend()) {
                        return false;
                    }
                    Linea = LeerArchivo.readLine();
                }

                bw.write(newFriend.toString());
                bw.newLine();
                bw.close();
                LecturaArchivo.close();
                LeerArchivo.close();
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public void addToIndex(Friends newFriend) throws IOException {
        File index = new File("C:\\MEIA\\indice.txt");
        File descIndice = new File("C:\\MEIA\\desc_indice_grupo.txt");
        File descGrupo = new File("C:\\MEIA\\desc_grupo_amigos.txt");
        if (!index.exists()) {
            index.createNewFile();
        }
        if (!descIndice.exists()) {
            descIndice.createNewFile();
            Index[] info = new Index[registers];
            writeInDescIndex(descIndice, Storage.Instance().actualUser.getUsername(), 1, info);
        }
        if (!descGrupo.exists()) {
            descGrupo.createNewFile();
            Index[] info = new Index[registers];
            writeDescGroupF(descGrupo, Storage.Instance().actualUser.getUsername(), info);
        }
        if (index.exists()) {
            Index newIndex = new Index();
            readDescIndex();
            FileReader LecturaArchivo;
            LecturaArchivo = new FileReader(index);
            BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
            String Linea = "";
            Index[] info = new Index[registers + 1];

            newIndex.setUser(newFriend.getUser());
            newIndex.setGroup(newFriend.getGroup());
            newIndex.setUserFriend(newFriend.getUserFriend());
            newIndex.setStatus(1);
            String[] split;
            Linea = LeerArchivo.readLine();
            FileWriter fw = new FileWriter(index, false);
            BufferedWriter bw = new BufferedWriter(fw);
            int counter = 0;
            while (Linea != null) {
                info[counter] = new Index();
                AddInInfo(info[counter], Linea);
                counter++;
                Linea = LeerArchivo.readLine();
            }
            int size = registers + 1;
            registers++;
            sortIndex(newIndex, info, initialD - 1, initialD - 1, size, 0);
            writeInDescIndex(descIndice, Storage.Instance().actualUser.getUsername(), initialD, info);
            writeDescGroupF(descGrupo, Storage.Instance().actualUser.getUsername(), info);
            
            for (int i = 0; i < info.length; i++) {
                bw.write(info[i].toString());
                bw.newLine();
            }

            bw.close();
            LecturaArchivo.close();
            LeerArchivo.close();

        }
    }
    
    public void writeDescGroupF(File descIndice, String userName, Index[] info) throws IOException {
        FileReader DescInit;
        DescInit = new FileReader(descIndice);
        BufferedReader LeerArchivo = new BufferedReader(DescInit);
        String Line = "";
        Line = LeerArchivo.readLine();
        FileWriter fw = new FileWriter(descIndice, false);
        BufferedWriter bw = new BufferedWriter(fw);
        String[] information = new String[10];
        Integer counter = 0;
        while (Line != null) {
            information[counter] = Line;
            counter++;
            Line = LeerArchivo.readLine();
        }
        if (counter == 0) {
            information[3] = "fecha_modificacion:" + java.time.LocalDateTime.now();
            information[4] = "usuario_modificacion:" + userName;
            information[5] = "#_registros:0";
            information[6] = "registros_activos:0";
            information[7] = "registros_inactivos:0";
        } else {
            Integer[] activeUser = new Integer[2];
            activeUsers(activeUser, info);
            information[0] = "fecha_modificacion:" + java.time.LocalDateTime.now();
            information[1] = "usuario_modificacion:" + userName;
            information[2] = "#_registros:" + registers;
            information[3] = "registros_activos:" + activeUser[1];
            information[4] = "registros_inactivos:" + activeUser[0];
        }
        for (int i = 0; i < 5; i++) {
            bw.write(information[i]);
            bw.newLine();
        }

        bw.close();
        DescInit.close();
        LeerArchivo.close();
    }
    
    public void AddInInfo(Index info, String Linea){
        String[] split = Linea.split("[|]");
        info.setRegister(Integer.parseInt(split[0]));
        info.setPosition(split[1]);
        info.setUser(split[2]);
        info.setGroup(split[3]);
        info.setUserFriend(split[4]);
        info.setNextPosition(Integer.parseInt(split[5]));
        info.setStatus(Integer.parseInt(split[6]));
    }

    public void sortIndex(Index newIndex, Index[] indexI, int position, int initial, int size, int prev) {
        if (indexI[0] == null) {
            newIndex.setRegister(size);
            newIndex.setNextPosition(position);
            newIndex.setPosition("1." + registers);
            indexI[size - 1] = newIndex;
        } else {
            boolean inserted = false;
            String a = "";
            String b = "";
            do {

                if (newIndex.getUser().equals(indexI[position].getUser())) {
                    if (newIndex.getGroup().equals(indexI[position].getGroup())) {
                        if (newIndex.getUserFriend().equals(indexI[position].getUserFriend())) {
                            return;
                        } else {
                            a = indexI[position].getUserFriend();
                            b = newIndex.getUserFriend();
                            if (b.compareTo(a) < 0 && indexI[position].getStatus() == 1) {
                                if (position == initial) {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(indexI[position].getRegister());
                                    position = newIndex.getRegister();
                                    initialD = position;
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                } else {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(indexI[prev].getNextPosition());
                                    indexI[prev].setNextPosition(newIndex.getRegister());
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                }
                            } else if (b.compareTo(a) > 0 && indexI[position].getStatus() == 1) {
                                if ((indexI[position].getNextPosition()) == 0) {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(0);
                                    indexI[position].setNextPosition(newIndex.getRegister());
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                } else {
                                    prev = indexI[position].getRegister() - 1;
                                    int aux = indexI[position].getNextPosition();
                                    position = aux - 1;
                                }

                            }
                        }
                    } else {
                        a = indexI[position].getGroup();
                        b = newIndex.getGroup();
                            if (b.compareTo(a) < 0 && indexI[position].getStatus() == 1) {
                                if (position == initial ) {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(indexI[position].getRegister());
                                    position = newIndex.getRegister();
                                    initialD = position;
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                } else {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(indexI[prev].getNextPosition());
                                    indexI[prev].setNextPosition(newIndex.getRegister());
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                }
                            } else if (b.compareTo(a) > 0 && indexI[position].getStatus() == 1) {
                                if ((indexI[position].getNextPosition()) == 0) {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(0);
                                    indexI[position].setNextPosition(newIndex.getRegister());
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                } else {
                                    prev = indexI[position].getRegister() - 1;
                                    int aux = indexI[position].getNextPosition();
                                    position = aux - 1;
                                }
                            }
                    }
                } else {
                    a = indexI[position].getUser();
                    b = newIndex.getUser();
                    if (b.compareTo(a) < 0 && indexI[position].getStatus() == 1) {
                                if (position == initial) {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(indexI[position].getRegister());
                                    position = newIndex.getRegister();
                                    initialD = position;
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                } else {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(indexI[prev].getNextPosition());
                                    indexI[prev].setNextPosition(newIndex.getRegister());
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                }
                            } else if (b.compareTo(a) > 0 && indexI[position].getStatus() == 1) {
                                if ((indexI[position].getNextPosition()) == 0) {
                                    newIndex.setRegister(size);
                                    newIndex.setNextPosition(0);
                                    indexI[position].setNextPosition(newIndex.getRegister());
                                    newIndex.setPosition("1." + registers);
                                    indexI[size - 1] = newIndex;
                                    inserted = true;
                                } else {
                                    prev = indexI[position].getRegister() - 1;
                                    int aux = indexI[position].getNextPosition();
                                    position = aux - 1;
                                }

                            }
                }
            } while (inserted == false);

        }
    }

    
    public void readDescIndex() throws FileNotFoundException, IOException {
        File MEIA = new File("C:\\MEIA");
        if (!MEIA.exists()) {
            MEIA.mkdir();
        }
        File usuario = new File("C:\\MEIA\\desc_indice_grupo.txt");
        if (usuario.exists() == true) {
            FileReader LecturaArchivo;
            LecturaArchivo = new FileReader(usuario);
            BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
            String Linea = "";
            Integer counter = 0;
            Linea = LeerArchivo.readLine();
            while (Linea != null && counter < 10) {
                if (counter == 5) {
                    registers = Integer.parseInt(Linea.split(":")[1]);
                }
                if (counter == 8) {
                    initialD = Integer.parseInt(Linea.split(":")[1]);
                }
                if(counter == 6){
                    activeReg = Integer.parseInt(Linea.split(":")[1]);
                }
                counter++;
                Linea = LeerArchivo.readLine();
            }
            LecturaArchivo.close();
            LeerArchivo.close();
        }
    }

    //Write the index description
    public void writeInDescIndex(File descIndice, String userName, Integer Initial, Index[] info) throws IOException {
        FileReader DescInit;
        DescInit = new FileReader(descIndice);
        BufferedReader LeerArchivo = new BufferedReader(DescInit);
        String Line = "";
        Line = LeerArchivo.readLine();
        FileWriter fw = new FileWriter(descIndice, false);
        BufferedWriter bw = new BufferedWriter(fw);
        String[] information = new String[10];
        Integer counter = 0;
        while (Line != null) {
            information[counter] = Line;
            counter++;
            Line = LeerArchivo.readLine();
        }
        if (counter == 0) {
            information[0] = "nombre_simbolico:desc_indice_grupo";
            information[1] = "fecha_creacion:" + java.time.LocalDateTime.now();
            information[2] = "usuario_creacion:" + userName;
            information[3] = "fecha_modificacion:" + java.time.LocalDateTime.now();
            information[4] = "usuario_modificacion:" + userName;
            information[5] = "#_registros:0";
            information[6] = "registros_activos:1";
            information[7] = "registros_inactivos:0";
            information[8] = "registro_inicial:" + Initial;
            information[9] = "no_bloques:1";
        } else {
            Integer[] activeUser = new Integer[2];
            activeUsers(activeUser, info);
            information[3] = "fecha_modificacion:" + java.time.LocalDateTime.now();
            information[4] = "usuario_modificacion:" + userName;
            information[5] = "#_registros:" + registers;
            information[6] = "registros_activos:" + activeUser[1];
            information[7] = "registros_inactivos:" + activeUser[0];
            information[8] = "registro_inicial:" + Initial;
        }
        for (int i = 0; i < 10; i++) {
            bw.write(information[i]);
            bw.newLine();
        }

        bw.close();
        DescInit.close();
        LeerArchivo.close();
    }

    //Count the active and inactive users
    public void activeUsers(Integer[] activeUser, Index[] info) throws IOException {
        activeUser[0] = 0;
        activeUser[1] = 0;
        for (int i = 0; i < info.length; i++) {
            if(info[i].getStatus() == 0){
                activeUser[0]++;
            }
            else if(info[i].getStatus() == 1){
                activeUser[1]++;
            }
        }
    }

    private UserBean getCompleteUser(String username) throws FileNotFoundException, IOException {
        File file = new File("C:\\MEIA\\usuarios.txt");
        UserBean userNew = new UserBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while ((lineReader = bufferReader.readLine()) != null) {
            String parts[] = lineReader.split("\\|");
            if (parts[0].equals(username)) {
                userNew.setUsername(parts[0]);
                userNew.setName(parts[1]);
                userNew.setLastName(parts[2]);
                userNew.setPassword(parts[3]);
                userNew.setBirth(parts[4]);
                userNew.setMail(parts[5]);
                userNew.setPhone(parts[6]);
                userNew.setPathPhoto(parts[7]);
                userNew.setStatus(Integer.parseInt(parts[8]));
                userNew.setRolUser(Integer.parseInt(parts[9]));
                return userNew;
            }
        }
        return null;
    }
    
    // </editor-fold>
    int deletePos = 0;
    public void deleteToGroup() throws IOException{
        readDescIndex();
        Index[] info = new Index[registers];
        
        if(friendExist(txtDeleteFriend.getText(), txtDeleteGroup.getText(), info)){
            changeStatus(info, deletePos - 1, initialD - 1);
            writeInFile(info);
            changeBlock(false);
            escenarioPrincipal.ventanaNormalUser();
        }
    }
    
    public boolean friendExist(String user, String group, Index[] info) throws IOException {
        File usuario = new File("C:\\MEIA\\indice.txt");
        if (usuario.exists() == true) {
            FileReader LecturaArchivo;
            LecturaArchivo = new FileReader(usuario);
            BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
            String Linea = "";
            Linea = LeerArchivo.readLine();
            int counter = 0;
            while (Linea != null) {
                if (Linea.split("[|]")[4].equalsIgnoreCase(user) && Linea.split("[|]")[3].equalsIgnoreCase(group)) {
                    deletePos = Integer.parseInt(Linea.split("[|]")[0]);
                }
                info[counter] = new Index();
                AddInInfo(info[counter], Linea);
                counter++;
                Linea = LeerArchivo.readLine();
            }
            LecturaArchivo.close();
            LeerArchivo.close();
            return true;
        }
        return false;
    }
    
    public void changeStatus(Index[] info, int position, int initial){
        boolean delete = false;
        do {
            if (info[initial].getNextPosition() == (position + 1)) {
                info[initial].setNextPosition(info[position].getNextPosition());
                info[position].setNextPosition(0);
                info[position].setStatus(0);
                delete = true;
            }
            else if(info[position].getRegister()== initialD){
                initialD = info[position].getNextPosition();
                info[position].setNextPosition(0);
                info[position].setStatus(0);
                delete = true;
            }
            else{
                int aux = info[initial].getNextPosition();
                initial = aux - 1;
            }
        } while (delete == false);
    }
    
    public void writeInFile(Index[]info) throws IOException{
        File index = new File("C:\\MEIA\\indice.txt");
        File descIndice = new File("C:\\MEIA\\desc_indice_grupo.txt");
        if (index.exists()) {
            writeInDescIndex(descIndice, Storage.Instance().actualUser.getUsername(), initialD, info);
            FileWriter fw = new FileWriter(index, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < info.length; i++) {
                bw.write(info[i].toString());
                bw.newLine();
            }
            bw.close();
        }
    }
    
    public void changeBlock(boolean option) throws IOException {
        File usuario = new File("C:\\MEIA\\grupo_amigos_n.txt");
        if (usuario.exists() == true) {
            FileReader LecturaArchivo;
            LecturaArchivo = new FileReader(usuario);
            BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
            String Linea = "";
            String[] info = new String[registers];
            if (option == true) {
                info = new String[activeReg];
            }
            Linea = LeerArchivo.readLine();
            int counter = 0;
            while (Linea != null) {
                if(option == true){
                    int x = Linea.length();
                    if (!"0".equals(Linea.split("[|]")[4])) {
                        info[counter] = new String();
                        info[counter] = Linea;
                        counter++;
                    }
                    
                    Linea = LeerArchivo.readLine();
                }
                else{
                    info[counter] = Linea;
                    counter++;
                    Linea = LeerArchivo.readLine();
                }
            }
            FileWriter fw = new FileWriter(usuario, false);
            BufferedWriter bw = new BufferedWriter(fw);
            if (option == false) {
                String newLine = "";
                int size = info[deletePos - 1].length();
                newLine = info[deletePos - 1].substring(0, size - 1) + "0";
                info[deletePos - 1] = newLine;
            }
            
            for (int i = 0; i < info.length; i++) {
                bw.write(info[i]);
                bw.newLine();
            }
            bw.close();
            LecturaArchivo.close();
            LeerArchivo.close();
        }
    }
    int activeReg = 0;
    public void reorganize() throws IOException{
        readDescIndex();
        changeBlock(true);
        Index[] info = new Index[registers];
        friendExist("", "", info);
        newOrder(info);
    }
    
    public void newOrder(Index[] info) throws IOException{
        Index[] newInfo = new Index[activeReg];
        int initial = 0;
        for (int i = 0; i < info.length; i++) {
            if (info[i].getStatus() == 1) {
                info[i].setNextPosition(0);
                info[i].setRegister(0);
                info[i].setPosition("");
                sortIndex(info[i], newInfo, 0, initial, activeReg,0);
            }
        }
        writeInFile(newInfo);
    }
    
}
