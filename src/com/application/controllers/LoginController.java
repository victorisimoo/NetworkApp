package com.application.controllers;

import com.application.system.Principal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
/**
 * @author victorisimo
 */
public class LoginController implements Initializable {
    private Principal escenarioPrincipal;
    private final String secretKey = "KEYSECRET1659" ;
    @FXML private TextField txtUser;
    @FXML private PasswordField txtPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public Principal geteEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void createAccount(){
        escenarioPrincipal.ventanaRegister();
    }
    
    public void loginUser() throws FileNotFoundException, 
        IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, 
        NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, 
        IllegalBlockSizeException, BadPaddingException{
        if(analyzeUser(txtUser.getText(), String.valueOf(txtPassword.getText()))){
            System.out.println("Login perfecto mi guapo");
        }else {
             System.out.println("Nada");
        }
    }
    
    private Boolean analyzeUser (String username, String password) throws FileNotFoundException, 
        IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, 
        NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, 
        IllegalBlockSizeException, BadPaddingException{
        
        EncryptionController encrypt = null;
        
        File directory = new File("C:\\MEIA\\");
        if(!directory.exists()){
            directory.mkdir();
            return false;
        }else {
            File file = new File("C:\\MEIA\\usuarios.txt");
            if(file.exists()){
                FileReader reader = new FileReader(file);
                BufferedReader bffer = new BufferedReader(reader);
                String lineReader;
                while((lineReader = bffer.readLine()) != null){
                    String parts[] = lineReader.split("\\|");
                    if(parts[0].equals(username) && parts[3].equals(encrypt.encrypt(password, secretKey))){
                        return true;
                    }
                }
            }else {
                return false;
            }
        }
        return false;
    }
}
