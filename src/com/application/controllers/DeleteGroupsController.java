package com.application.controllers;

import com.application.beans.GroupBean;
import com.application.system.Principal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/*
 * @author josed
 */
public class DeleteGroupsController implements Initializable {
    
    private Principal escenarioPrincipal;
    @FXML private Label lblUser;
    @FXML private TextField txtGroup;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void deleteMethod() throws IOException{
        String text = lblUser.getText() + txtGroup.getText();
        deleteGropus("C:\\MEIA\\bitacora_grupo.txt", text);
        deleteGropus("C:\\MEIA\\grupo.txt", text);
    }
    
    //eliminar un grupo
    public void deleteGropus(String directory, String groupName) throws FileNotFoundException, IOException{
        File file = new File(directory);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        boolean exists = false;
        
        //creacion lista almacen de lineas
        List<String> listText = new ArrayList<String>();
        
        //llenado de la lista
        while(line != null){
            listText.add(line);
            line = bufferedReader.readLine();
        }
        clearTheFile(directory);
        
        //recorrido de la lista, separacion elementos y encontrar el grupo
        for (int i = 0; i < listText.toArray().length; i++) {
            GroupBean TempGroup = new GroupBean();
            
            String Temp = listText.toArray()[i].toString();
            
            String[] TempArray = Temp.split("|");
            
            if (TempArray[0]+TempArray[1] == groupName) {
                exists = true;
                TempGroup.setUser(TempArray[0]);
                TempGroup.setType(TempArray[1]);
                TempGroup.setDescription(TempArray[2]);
                TempGroup.setCero(TempArray[3]);
                TempGroup.setBirth(TempArray[4]);
                TempGroup.setStatus("0");
                writeDeleteGroups(TempGroup, directory);
            }
            else{
                TempGroup.setUser(TempArray[0]);
                TempGroup.setType(TempArray[1]);
                TempGroup.setDescription(TempArray[2]);
                TempGroup.setCero(TempArray[3]);
                TempGroup.setBirth(TempArray[4]);
                TempGroup.setStatus(TempArray[5]);
                writeDeleteGroups(TempGroup, directory);
            }
        }
    }
    
    public void writeDeleteGroups(GroupBean newGroup, String directory) throws FileNotFoundException, IOException{
            File file = new File(directory);
            
            if(file.exists()){
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter printLine = new PrintWriter(fileWriter);
                printLine.print(newGroup.printTXT() + '\n');
                printLine.close();
                fileWriter.close();
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
    
    public void deleteInactive(){
        
    }
    
}
