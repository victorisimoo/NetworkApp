package com.application.controllers;

import com.application.beans.UserBean;
import com.application.system.Principal;
import com.application.system.Storage;
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
        if(analyzeLoginUser(txtUser.getText(), String.valueOf(txtPassword.getText()))){
            Storage.Instance().actualUser = getCompleteUser(txtUser.getText());
            System.out.println("Login exitoso");
        }else {
            //Creación de usuario número uno como admin
            System.out.println("Login NO exitoso");
        }
    }
    
    private Boolean analyzeLoginUser (String username, String password) throws FileNotFoundException, 
        IOException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, 
        NoSuchPaddingException, InvalidAlgorithmParameterException, UnsupportedEncodingException, 
        IllegalBlockSizeException, BadPaddingException{
        
        File directory = new File("C:\\MEIA\\");
        File file = new File("C:\\MEIA\\usuarios.txt");
        
        if(createFileAndDirectory(directory, file)){
            return analyzeUser(username, EncryptionController.encrypt(password, secretKey), file);
        }else {
            directory.mkdir();
            file.createNewFile();
            return false; 
        }
    } 
    
    private UserBean getCompleteUser(String username) throws FileNotFoundException, IOException{
        File file = new File("C:\\MEIA\\usuarios.txt");
         UserBean userNew = new UserBean();
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine())!= null){
            String parts[] = lineReader.split("\\|");
            if(parts[0].equals(username)){
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
    
    private Boolean createFileAndDirectory(File directory, File file){
        return directory.exists() && file.exists();
    }
    
    private Boolean analyzeUser(String username, String password, File file) throws FileNotFoundException, IOException{
        FileReader reader = new FileReader(file);
        BufferedReader bufferReader = new BufferedReader(reader);
        String lineReader;
        while((lineReader = bufferReader.readLine())!= null){
            String parts[] = lineReader.split("\\|");
            if(parts[0].equals(username) && parts[3].equals(password)){
                return true;
            }
        }
        return false;
    }
}
