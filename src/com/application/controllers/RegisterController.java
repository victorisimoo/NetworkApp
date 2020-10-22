package com.application.controllers;

import com.application.beans.UserBean;
import com.application.system.Principal;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

/**
 * @author victorisimo
 */
public class RegisterController implements Initializable {
    
    private Principal escenarioPrincipal;
    private final String secretKey = "KEYSECRET1659" ;
    boolean correct;
    private ObservableList<String> TipoUsuarios = FXCollections.observableArrayList("Administrador","Normal");
    private ObservableList<String> StatusType = FXCollections.observableArrayList("Activado","Desactivado");
    @FXML private TextField txtUser;
    @FXML private TextField txtLastName;
    @FXML private TextField txtName;
    @FXML private TextField txtMail;
    @FXML private TextField txtPhone;
    @FXML private TextField txtPhoto;
    @FXML private PasswordField txtPassword;
    @FXML private DatePicker cldBirth;
    @FXML private ComboBox cmbType;
    @FXML private ComboBox cmbStatus;
    @FXML private Label lblStatusPassword;
    @FXML private Button btnAdd;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbType.setItems(TipoUsuarios);
        cmbStatus.setItems(StatusType);
        btnAdd.setDisable(true);
    }    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
    public void passwordKeyPress(){
        calculateStrength(txtPassword.getText());
    }
    
    public void register(){
        UserBean newUser = new UserBean();
        newUser.setUsername(txtUser.getText());
        newUser.setName(txtName.getText());
        newUser.setLastName(txtLastName.getText());
        newUser.setPassword(EncryptionController.encrypt(txtPassword.getText(), secretKey));
        newUser.setMail(txtMail.getText());
        newUser.setBirth(cldBirth.getValue().toString());
        newUser.setPhone(txtPhone.getText());
        newUser.setPathPhoto(txtPhoto.getText());
        if(cmbType.getSelectionModel().getSelectedItem().equals("Administrador")){
            newUser.setRolUser(1);
        }else {
            newUser.setRolUser(0);
        }
        if(cmbStatus.getSelectionModel().getSelectedItem().equals("Activado")){
            newUser.setStatus(1);
        }else {
            newUser.setStatus(0);
        }
        if(writeUser(newUser)){
            escenarioPrincipal.ventanaLogin();
        }else {
            JOptionPane.showMessageDialog(null, "El usuario no fue agregado, intentelo de nuevo", "Registro fallido", JOptionPane.OK_OPTION);
        }
    }
     
    public Boolean writeUser(UserBean userBean){
        try {
            File directory = new File("C:\\MEIA");
            if(!directory.exists()){
                directory.mkdir();
            }
            File file = new File("C:\\MEIA\\usuarios.txt");
            if(file.exists()){
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter printLine = new PrintWriter(fileWriter);
                printLine.print(userBean.toString() + '\n');
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
    
    public void calculateStrength(String passwordText){
        int upperChars = 0, lowerChars = 0, numbers = 0, specialChars = 0, otherChars = 0, strengthPoints = 0;
        char c;
        
        int passwordLength = passwordText.length();
 
        if (passwordLength == 0) {
            correct = false;
            lblStatusPassword.setText("Inválida");
            lblStatusPassword.setTextFill(Color.RED);
            return;
        }
 
        //If password length is <= 5 set strengthPoints=1
        if (passwordLength <= 5) {
            strengthPoints =1;
        }
        //If password length is >5 and <= 10 set strengthPoints=2
        else if (passwordLength <= 10) {
            strengthPoints = 2;
        }
        //If password length is >10 set strengthPoints=3
        else
            strengthPoints = 3;
        // Loop through the characters of the password
        for (int i = 0; i < passwordLength; i++) {
            c = passwordText.charAt(i);
            // If password contains lowercase letters
            // then increase strengthPoints by 1
            if (c >= 'a' && c <= 'z') {
                if (lowerChars == 0) strengthPoints++;
                lowerChars = 1;
            }
            // If password contains uppercase letters
            // then increase strengthPoints by 1
            else if (c >= 'A' && c <= 'Z') {
                if (upperChars == 0) strengthPoints++;
                upperChars = 1;
            }
            // If password contains numbers
            // then increase strengthPoints by 1
            else if (c >= '0' && c <= '9') {
                if (numbers == 0) strengthPoints++;
                numbers = 1;
            }
            // If password contains _ or @
            // then increase strengthPoints by 1
            else if (c == '_' || c == '@') {
                if (specialChars == 0) strengthPoints += 1;
                specialChars = 1;
            }
            // If password contains any other special chars
            // then increase strengthPoints by 1
            else {
                if (otherChars == 0) strengthPoints += 2;
                otherChars = 1;
            }
        }
 
        if (strengthPoints <= 3) {
            correct = false;
            lblStatusPassword.setText("Baja");
            lblStatusPassword.setTextFill(Color.RED);
        }
        else if (strengthPoints <= 6) {
            correct = true;
            lblStatusPassword.setText("Media");
            lblStatusPassword.setTextFill(Color.YELLOW);
            btnAdd.setDisable(false);
        }
        else if (strengthPoints <= 9){
            correct = true;
            lblStatusPassword.setText("Alta");
            lblStatusPassword.setTextFill(Color.GREEN);
            btnAdd.setDisable(false);
        }
        
        
    }
}
