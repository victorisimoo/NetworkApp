package com.application.controllers;

import com.application.system.Principal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import com.application.beans.GroupBean;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;
import java.util.*;

/**
 * @author josed
 */
public class CreateGropusController implements Initializable {

    private Principal escenarioPrincipal;
    
    @FXML private TextField txtType;
    @FXML private TextField txtDescription;
    @FXML private Label lblUser;
    @FXML private Label lblAvailability;
    
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
    
    //crear el grupo
    public void Create() throws IOException{
        //crear base grupo vacio
        GroupBean newGroup = new GroupBean();
        
        //insertar informacion al grupo
        newGroup.setUser(lblUser.getText());
        newGroup.setType(txtType.getText());
        newGroup.setDescription(txtDescription.getText());
        newGroup.setCero("0");
        newGroup.setBirth(""+java.time.LocalDateTime.now());
        newGroup.setStatus("1");
        
        //aparicion de los message box
        if (writeGroup(newGroup)) {
            //impresion bitacora grupos
            PrintBinnacle(lblUser.getText());
            JOptionPane.showMessageDialog(null, "grupo creado correctamente", "Grupo creado", JOptionPane.OK_OPTION);
        }
        else{
            JOptionPane.showMessageDialog(null, "Grupo no creado", "Fallo", JOptionPane.OK_OPTION);
        }
        
    }
    
    //impresion bitacora
    private void PrintBinnacle(String user) throws IOException{
        File file = new File("C:\\MEIA\\desc_bitacora_grupo.txt");
        if (!file.exists()) {
                file.createNewFile();
        }
        ArrayList<String> ValueList = new ArrayList<>();
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        FileWriter fileWriter = new FileWriter(file, false);
        String line = bufferedReader.readLine();
        
        if (line != null) {
            while(line != null){
                String[] arrayTemp = line.split(":");
                ValueList.add(arrayTemp[1]);
                line = bufferedReader.readLine();
            }
            fileWriter.write("nombre_simbolico:desc_bitacora_grupo" + '\n');
            fileWriter.write("fecha_creacion:" + ValueList.toArray()[1] + '\n');
            fileWriter.write("usuario_creacion:" + ValueList.toArray()[2] + '\n');
            fileWriter.write("fecha_modificacion:" + java.time.LocalDateTime.now() + '\n');
            fileWriter.write("usuario_modificacion" + user + '\n');
            fileWriter.write("#_registros:" + Integer.toString(Integer.parseInt(ValueList.toArray()[5].toString())+1) + '\n');
            fileWriter.write("#_registros:" + Integer.toString(Integer.parseInt(ValueList.toArray()[6].toString())+1) + '\n');
            fileWriter.write("usuario_creacion:" + ValueList.toArray()[7] + '\n');
            fileWriter.write("usuario_creacion:" + ValueList.toArray()[8] + '\n');
        }
        else{
            fileWriter.write("nombre_simbolico:desc_bitacora_grupo" + '\n');
            fileWriter.write("fecha_creacion:" + java.time.LocalDateTime.now() + '\n');
            fileWriter.write("nombre_simbolico:" + user + '\n');
            fileWriter.write("fecha_modificacion:" + java.time.LocalDateTime.now() + '\n');
            fileWriter.write("usuario_modificacion" + user + '\n');
            fileWriter.write("#_registros:1" + '\n');
            fileWriter.write("registros_activos:1" + '\n');
            fileWriter.write("registros_activos:0" + '\n');
            fileWriter.write("max_reorganizar:6" + '\n');
        }
    }
    
    //mandar a escribir el grupo
    public Boolean writeGroup(GroupBean newGroup){
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
            while(line != null){
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
            if(file.exists()){
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter printLine = new PrintWriter(fileWriter);
                printLine.print(newGroup.toString() + '\n');
                printLine.close();
                fileWriter.close();
                return true;
            }else{
                return false;
            }
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }
    
    //traspasar de un lado a otro los grupos
    public void migrateGroups(ArrayList<GroupBean> groupsList) throws IOException{
        File file = new File("C:\\MEIA\\grupo.txt");
        
        //revision si el archivo existe o no
        if (!file.exists()) {
                file.createNewFile();
            }
        FileWriter fileWriter = new FileWriter(file, true);
        PrintWriter printLine = new PrintWriter(fileWriter);
        //ordenar la lista de grupos
        Collections.sort(groupsList);
        
        //limpiar el archivo de texto
        clearTheFile("C:\\MEIA\\grupo.txt");
        
        //recorrer y validar la lista de los grupos
        for(var item : groupsList){
            //eliminar los grupos que estan inactivos
            if (item.getStatus() == "0") {
                groupsList.remove(item);
            }
            //agrega los grupos activos
            else{
                printLine.print(item.toString() + '\n');
                printLine.close();
                fileWriter.close();
            }
        }
        
    }
    
    //metodo para el label
    public void labelAvailability(){
        validateAvailability("C:\\MEIA\\bitacora_grupo.txt");
        validateAvailability("C:\\MEIA\\grupo.txt");
    }
    
    //validar si esta o no el grupo
    public void validateAvailability(String directory){
        try {
            String text = lblUser.getText() + txtType.getText(), availability = "El grupo esta disponible";
            File file = new File(directory);
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            
            //revision si esta vacio el archivo
            if (line != null) {
                //revision de si existe o no el grupo
                while(line != null){
                    String[] arrayGroup = line.split("[|]");
                    if (text == (arrayGroup[0] + arrayGroup[1]) && arrayGroup[6] == "1") {
                        availability = "El grupo ya existe";
                    }
                    line = bufferedReader.readLine();
                }
            }
            //impresion en el label
            lblAvailability.setText(availability);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CreateGropusController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateGropusController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //limpiar un archivo
    public void clearTheFile(String file) throws IOException {
        FileWriter fileWriter = new FileWriter(file, false); 
        PrintWriter printWriter = new PrintWriter(fileWriter, false);
        printWriter.flush();
        printWriter.close();
    }
}
