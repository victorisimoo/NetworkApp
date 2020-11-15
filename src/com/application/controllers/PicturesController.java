/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.application.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author josed
 */
public class PicturesController implements Initializable {

    @FXML private ImageView pbxImage;
    @FXML private TextField txtUser;
    ArrayList<String> valueList;
    int counterPicture = 0;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void establishPicture() throws IOException{
        valueList = findUserPictures(txtUser.getText());
        String[] pathArray = new String[valueList.toArray().length];
        int counter = 0;
        if (valueList != null) {
            for(String string : valueList) {
                String[] arrayTemp = string.split("[|]");
                pathArray[counter] = arrayTemp[4];
                ++counter;
            }
            Image image1 = new Image(new FileInputStream(pathArray[0]));
            pbxImage.setImage(image1);
        }
        else{
            JOptionPane.showMessageDialog(null, "El usuario seleccionado no tiene fotos", "Fallo", JOptionPane.OK_OPTION);
        }
    }
    
    public ArrayList<String> findUserPictures(String name) throws IOException{
        File file = new File("C:\\MEIA\\imagen_usuario.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        ArrayList<String> valueList = new ArrayList<>();
        FileReader reader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        while (line != null) {
            String[] arrayTemp = line.split("[|]");
            if (arrayTemp[3].equals(name)) {
                valueList.add(line);
            }
            line = bufferedReader.readLine();
        }
        
        return valueList;
    }
    
    public void nextPicture() throws FileNotFoundException{
        if (counterPicture <= valueList.toArray().length) {
            String[] pathArray = new String[valueList.toArray().length];
        
            for(String string : valueList) {
                String[] arrayTemp = string.split("[|]");
                pathArray[counterPicture] = arrayTemp[4];
                ++counterPicture;
            }
        
            Image image1 = new Image(new FileInputStream(pathArray[counterPicture]));
            pbxImage.setImage(image1);
        }
        else{
            
        }
    }
    
    public void previousPicture() throws FileNotFoundException{
        if (counterPicture >= 0) {
            String[] pathArray = new String[valueList.toArray().length];
        
            for(String string : valueList) {
                String[] arrayTemp = string.split("[|]");
                pathArray[counterPicture] = arrayTemp[4];
                --counterPicture;
            }
        
            Image image1 = new Image(new FileInputStream(pathArray[counterPicture]));
            pbxImage.setImage(image1);
        }
        else{
            
        }
    }
    
}
