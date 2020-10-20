package com.application.controllers;

import com.application.beans.UserBean;
import com.application.system.Principal;
import static java.awt.font.TextAttribute.WIDTH;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javax.swing.JOptionPane;

/**
 * @author victorisimo
 */
public class RegisterController implements Initializable {
    
    private Principal escenarioPrincipal;
    private final String secretKey = "KEYSECRET1659" ;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbType.setItems(TipoUsuarios);
        cmbStatus.setItems(StatusType);
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
    
    public void register(){
        UserBean newUser = new UserBean();
        newUser.setUsername(txtUser.getText());
        newUser.setName(txtName.getText());
        newUser.setLastName(txtLastName.getText());
        newUser.setPassword(EncryptionController.encrypt(txtPassword.getText(), secretKey));
        newUser.setMail(txtMail.getText());
        newUser.setBirth(cldBirth.getValue().toString());
        newUser.setPhone(txtPhone.getText());
        newUser.setPhone(txtPhoto.getText());
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
            //Ingresa correctamente.
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
                List<String> list = Files.readAllLines(Paths.get("C:\\MEIA\\usuarios.txt"));
                list.add(list.size() - 1, userBean.toString());
                Files.write(Paths.get("C:\\MEIA\\usuarios.txt"), list);
                return true;
            }else{
                return false;
            }
        }catch (Exception e ){
            e.printStackTrace();
            return false;
        }
    }
}
