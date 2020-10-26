package com.application.controllers;

import com.application.system.Principal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import com.application.beans.GroupBean;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import java.util.*;

/**
 * @author josed
 */
public class CreateGropusController implements Initializable {

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtType;
    @FXML
    private TextField txtDescription;
    @FXML
    private Label lblUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblUser.setText(Storage.Instance().actualUser.getUsername());
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Principal geteEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    //crear el grupo
    public void Create() throws IOException {
        //crear base grupo vacio
        GroupBean newGroup = new GroupBean();

        //insertar informacion al grupo
        newGroup.setUser(lblUser.getText());
        newGroup.setType(txtType.getText());
        newGroup.setDescription(txtDescription.getText());
        newGroup.setCero("0");
        newGroup.setBirth("" + java.time.LocalDateTime.now());
        newGroup.setStatus("1");

        if (Availability(txtType.getText()) && descriptionReturn(txtDescription.getText()) && descriptionReturn(txtType.getText())) {
            //aparicion de los message box
            if (writeGroup(newGroup)) {
                //impresion bitacora grupos
                PrintBinnacle(lblUser.getText());
                JOptionPane.showMessageDialog(null, "grupo creado correctamente", "Grupo creado", JOptionPane.OK_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "Grupo no creado", "Fallo", JOptionPane.OK_OPTION);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Grupo no creado", "Fallo", JOptionPane.OK_OPTION);
        }
    }

    private Boolean descriptionReturn(String description) {
        String empty = "";
        if (description.equals(empty)) {
            return false;
        } else {
            return true;
        }
    }

    //impresion bitacora
    private void PrintBinnacle(String user) throws IOException {

        File file = new File("C:\\MEIA\\desc_bitacora_grupo.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        ArrayList<String> ValueList = new ArrayList<>();
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] arrayTemp = line.split(":");
            ValueList.add(arrayTemp[1]);
            line = bufferedReader.readLine();
        }
        reader.close();
        FileWriter fileWriter = new FileWriter(file, false);
        if (ValueList.toArray().length == 0) {

            fileWriter.write("nombre_simbolico:desc_bitacora_grupo" + '\n');
            fileWriter.write("fecha_creacion:" + java.time.LocalDateTime.now() + '\n');
            fileWriter.write("nombre_simbolico:" + user + '\n');
            fileWriter.write("fecha_modificacion:" + java.time.LocalDateTime.now() + '\n');
            fileWriter.write("usuario_modificacion:" + user + '\n');
            fileWriter.write("#_registros:1" + '\n');
            fileWriter.write("registros_activos:1" + '\n');
            fileWriter.write("registros_activos:0" + '\n');
            fileWriter.write("max_reorganizar:6" + '\n');
        } else {
            fileWriter.write("nombre_simbolico:desc_bitacora_grupo" + '\n');
            fileWriter.write("fecha_creacion:" + ValueList.toArray()[1] + '\n');
            fileWriter.write("usuario_creacion:" + ValueList.toArray()[2] + '\n');
            fileWriter.write("fecha_modificacion:" + java.time.LocalDateTime.now() + '\n');
            fileWriter.write("usuario_modificacion:" + user + '\n');
            fileWriter.write("#_registros:" + Integer.toString(Integer.parseInt(ValueList.toArray()[5].toString()) + 1) + '\n');
            fileWriter.write("registros_activos:" + Integer.toString(Integer.parseInt(ValueList.toArray()[6].toString()) + 1) + '\n');
            fileWriter.write("registros_activos:" + ValueList.toArray()[7] + '\n');
            fileWriter.write("max_reorganizar:" + ValueList.toArray()[8] + '\n');
        }
        fileWriter.close();
    }

    //mandar a escribir el grupo
    public Boolean writeGroup(GroupBean newGroup) {
        try {
            File file = new File("C:\\MEIA\\bitacora_grupo.txt");

            //chequeo si el file existe
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();

            //creacion lista de tipo grupo
            ArrayList<GroupBean> ListGroups = new ArrayList<>();

            //llenado de la lista de grupos
            while (line != null) {
                GroupBean tempGroup = new GroupBean();
                String[] tempArray = line.split("[|]");
                tempGroup.setUser(tempArray[0]);
                tempGroup.setType(tempArray[1]);
                tempGroup.setDescription(tempArray[2]);
                tempGroup.setCero(tempArray[3]);
                tempGroup.setBirth(tempArray[4]);
                tempGroup.setStatus(tempArray[6]);
                ListGroups.add(tempGroup);
                line = bufferedReader.readLine();
            }
            reader.close();

            //revision si la cantidad maxima de grupos esta en la lista
            if (ListGroups.toArray().length == 6) {
                migrateGroups(ListGroups);
            }

            //escritura del grupo nuevo
            if (file.exists()) {
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter printLine = new PrintWriter(fileWriter);
                printLine.print(newGroup.toString() + '\n');
                printLine.close();
                fileWriter.close();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void reorganizationExit() throws IOException{
        File file = new File("C:\\MEIA\\bitacora_grupo.txt");

            //chequeo si el file existe
            if (!file.exists()) {
                file.createNewFile();
            }
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();

            //creacion lista de tipo grupo
            ArrayList<GroupBean> ListGroups = new ArrayList<>();

            //llenado de la lista de grupos
            while (line != null) {
                GroupBean tempGroup = new GroupBean();
                String[] tempArray = line.split("[|]");
                tempGroup.setUser(tempArray[0]);
                tempGroup.setType(tempArray[1]);
                tempGroup.setDescription(tempArray[2]);
                tempGroup.setCero(tempArray[3]);
                tempGroup.setBirth(tempArray[4]);
                tempGroup.setStatus(tempArray[6]);
                ListGroups.add(tempGroup);
                line = bufferedReader.readLine();
            }
            reader.close();
            
            if (ListGroups.toArray().length != 0) {
                migrateGroups(ListGroups);
            }
    }

    //traspasar de un lado a otro los grupos
    public void migrateGroups(ArrayList<GroupBean> groupsList) throws IOException {
        File file = new File("C:\\MEIA\\grupo.txt");

        //revision si el archivo existe o no
        if (!file.exists()) {
            file.createNewFile();
        }
        
        //creacion objetos lectura
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        
        while (line != null) {
            GroupBean tempGroup = new GroupBean();
            String[] tempArray = line.split("[|]");
            tempGroup.setUser(tempArray[0]);
            tempGroup.setType(tempArray[1]);
            tempGroup.setDescription(tempArray[2]);
            tempGroup.setCero(tempArray[3]);
            tempGroup.setBirth(tempArray[4]);
            tempGroup.setStatus(tempArray[6]);
            groupsList.add(tempGroup);
            line = bufferedReader.readLine();
        }
        //cerrar los objetos de lectura
        reader.close();
        bufferedReader.close();
        
        //limpiar el archivo de texto
        clearTheFile("C:\\MEIA\\bitacora_grupo.txt");
        clearTheFile("C:\\MEIA\\grupo.txt");
        
        //ordenar la lista de grupos
        Collections.sort(groupsList);
        
        //creacion objetos escritura
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printLine = new PrintWriter(fileWriter);
        
        //recorrer y validar la lista de los grupos
        for (var item : groupsList) {
            //eliminar los grupos que estan inactivos
            if (item.getStatus() == "0") {
                groupsList.remove(item);
            } //agrega los grupos activos
            else {
                printLine.print(item.toString() + '\n');
            }
        }
        printLine.close();
        fileWriter.close();
    }

    //metodo para el textbox
    public Boolean Availability(String text) throws IOException {
        Boolean Action;
        Action = validateAvailability(text);
        if (Action) {
            Action = validateAvailability2(text);
        }
        return Action;
    }

    //validar si esta o no el grupo
    public Boolean validateAvailability(String textBox) throws IOException {

        String text = lblUser.getText() + textBox, availability = "El grupo esta disponible", one = "1";
        Boolean Activar = true;
        File file = new File("C:\\MEIA\\bitacora_grupo.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();

        //revision si esta vacio el archivo
        if (line != null) {
            //revision de si existe o no el grupo
            while (line != null) {
                String[] arrayGroup = line.split("[|]");
                String nameTemp = arrayGroup[0] + arrayGroup[1], available = arrayGroup[6];
                if (text.equals(nameTemp) && one.equals(available)) {
                    //grupo si existe
                    Activar = false;
                }
                line = bufferedReader.readLine();
            }
        }
        return Activar;
    }

    public Boolean validateAvailability2(String textBox) throws IOException {

        String text = lblUser.getText() + textBox, availability = "El grupo esta disponible", one = "1";
        Boolean Activar = true;
        File file = new File("C:\\MEIA\\grupo.txt");

        if (!file.exists()) {
            file.createNewFile();
        }

        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();

        ///revision si esta vacio el archivo
        if (line != null) {
            //revision de si existe o no el grupo
            while (line != null) {
                String[] arrayGroup = line.split("[|]");
                String nameTemp = arrayGroup[0] + arrayGroup[1], available = arrayGroup[6];
                if (text.equals(nameTemp) && one.equals(available)) {
                    //grupo si existe
                    Activar = false;
                }
                line = bufferedReader.readLine();
            }
        }
        return Activar;
    }

    //limpiar un archivo
    public void clearTheFile(String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file, false);
        PrintWriter printWriter = new PrintWriter(fileWriter, false);
        printWriter.flush();
        printWriter.close();
    }
}
