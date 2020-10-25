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
import java.util.List;
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
    
    //crear el grupo
    public void Create(){
        GroupBean newGroup = new GroupBean();
        
        newGroup.setUser(lblUser.getText());
        newGroup.setType(txtType.getText());
        newGroup.setDescription(txtDescription.getText());
        newGroup.setCero("0");
        newGroup.setBirth(""+java.time.LocalDateTime.now());
        newGroup.setStatus("1");
        
        if (writeUser(newGroup)) {
            JOptionPane.showMessageDialog(null, "grupo creado correctamente", "Grupo creado", JOptionPane.OK_OPTION);
        }
        else{
            JOptionPane.showMessageDialog(null, "Grupo no creado", "Fallo", JOptionPane.OK_OPTION);
        }
    }
    
    //mandar a escribir el grupo
    public Boolean writeUser(GroupBean newGroup){
        try {
            File file = new File("C:\\MEIA\\bitacora_grupo.txt");
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            
            //creacion lista almacen de lineas
            List<String> listText = new ArrayList<String>();
        
            //llenado de la lista
            while(line != null){
                listText.add(line);
                line = bufferedReader.readLine();
            }
            reader.close();
            
            if (listText.toArray().length == 6) {
                migrateGroups(listText);
            }
            
            if(file.exists()){
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter printLine = new PrintWriter(fileWriter);
                printLine.print(newGroup.printTXT() + '\n');
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
    public void migrateGroups(List<String> groupsList){
        //
        File file = new File("C:\\MEIA\\grupo.txt");
        Collections.sort(groupsList);
        
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
            if (line != null) {
                while(line != null){
                    String[] arrayGroup = line.split("|");
                    if (text == (arrayGroup[0] + arrayGroup[1])) {
                        availability = "El grupo ya existe";
                    }
                    line = bufferedReader.readLine();
                }
            }
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
        printWriter.close();
    }
}
