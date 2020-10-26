package com.application.controllers;

import com.application.beans.GroupBean;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/*
 * @author josed
 */
public class DeleteGroupsController implements Initializable {

    private Principal escenarioPrincipal;
    @FXML
    private Label lblUser;
    @FXML
    private TextField txtGroup;

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
    
    public void openGroups(){
        escenarioPrincipal.ventanaGroups();
    }

    public void deleteMethod() throws IOException {
        String text = lblUser.getText() + txtGroup.getText();
        
        if (deleteGroups("C:\\MEIA\\bitacora_grupo.txt", text, lblUser.getText())) {
            JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente :D", "Proceso exitoso", JOptionPane.OK_OPTION);
        }
        else{
            if (deleteGroups("C:\\MEIA\\grupo.txt", text, lblUser.getText())) {
                JOptionPane.showMessageDialog(null, "Se ha eliminado correctamente :D", "Proceso exitoso", JOptionPane.OK_OPTION);
            }
            else{
                JOptionPane.showMessageDialog(null, "no se ha encontrado el grupo :(", "Proceso denegado", JOptionPane.OK_OPTION);
            }
            
        }
        
        
    }

    //eliminar un grupo
    public Boolean deleteGroups(String directory, String groupName, String user) throws FileNotFoundException, IOException {
        File file = new File(directory);
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        boolean exists = false;

        //creacion lista almacen de lineas
        List<String> listText = new ArrayList<String>();

        //llenado de la lista
        while (line != null) {
            listText.add(line);
            line = bufferedReader.readLine();
        }
        clearTheFile(directory);

        //recorrido de la lista, separacion elementos y encontrar el grupo
        for (int i = 0; i < listText.toArray().length; i++) {
            GroupBean TempGroup = new GroupBean();

            String Temp = listText.toArray()[i].toString();

            String[] TempArray = Temp.split("[|]");
            String name = TempArray[0] + TempArray[1];
            if (name.equals(groupName)) {
                exists = true;
                TempGroup.setUser(TempArray[0]);
                TempGroup.setType(TempArray[1]);
                TempGroup.setDescription(TempArray[2]);
                TempGroup.setCero(TempArray[3]);
                TempGroup.setBirth(TempArray[4]);
                TempGroup.setStatus("0");
                writeDeleteGroups(TempGroup, directory);
                PrintBinnacle(user);

            } else {
                TempGroup.setUser(TempArray[0]);
                TempGroup.setType(TempArray[1]);
                TempGroup.setDescription(TempArray[2]);
                TempGroup.setCero(TempArray[3]);
                TempGroup.setBirth(TempArray[4]);
                TempGroup.setStatus(TempArray[6]);
                writeDeleteGroups(TempGroup, directory);
            }
        }
        
        bufferedReader.close();
        reader.close();
        return exists;
    }

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
            fileWriter.write("registros_inactivos:0" + '\n');
            fileWriter.write("max_reorganizar:6" + '\n');
        } else {
            fileWriter.write("nombre_simbolico:desc_bitacora_grupo" + '\n');
            fileWriter.write("fecha_creacion:" + ValueList.toArray()[1] + '\n');
            fileWriter.write("usuario_creacion:" + ValueList.toArray()[2] + '\n');
            fileWriter.write("fecha_modificacion:" + java.time.LocalDateTime.now() + '\n');
            fileWriter.write("usuario_modificacion:" + user + '\n');
            fileWriter.write("#_registros:" + ValueList.toArray()[5] + '\n');
            fileWriter.write("registros_activos:" + Integer.toString(Integer.parseInt(ValueList.toArray()[6].toString()) - 1) + '\n');
            fileWriter.write("registros_inactivos:" + Integer.toString(Integer.parseInt(ValueList.toArray()[7].toString()) + 1) + '\n');
            fileWriter.write("max_reorganizar:" + ValueList.toArray()[8] + '\n');
        }
        fileWriter.close();
    }

    public void writeDeleteGroups(GroupBean newGroup, String directory) throws FileNotFoundException, IOException {
        File file = new File(directory);

        if (file.exists()) {
            FileWriter fileWriter = new FileWriter(file, true);
            PrintWriter printLine = new PrintWriter(fileWriter);
            printLine.print(newGroup.toString() + '\n');
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
    }

}
