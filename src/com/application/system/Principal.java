package com.application.system;

import com.application.controllers.AdminUserController;
import com.application.controllers.CreateGropusController;
import com.application.controllers.DeleteGroupsController;
import com.application.controllers.GroupsController;
import com.application.controllers.LoginController;
import com.application.controllers.NormalUserController;
import com.application.controllers.RegisterController;
import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author victorisimo
 */
public class Principal extends Application {
    
    private final String PAQUETE_VISTAS = "/com/application/views/"; 
    private Stage escenarioPrincipal;
    private Scene escena;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
        escenarioPrincipal.setTitle("Network App");
        ventanaLogin();
        escenarioPrincipal.show();
    }
    
    public void ventanaLogin(){
        try {
            LoginController loginController = (LoginController) cambioEscena("Login.fxml", 500, 400);
            loginController.setEscenarioPrincipal(this);
        } catch (Exception e){
           e.printStackTrace();
        }
    }
    
    public void ventanaNormalUser(){
        try {
            NormalUserController normalUser = (NormalUserController) cambioEscena("NormalUser.fxml", 500, 400);
            normalUser.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaAdminUser(){
        try {
            AdminUserController adminUser = (AdminUserController) cambioEscena("AdminUser.fxml", 500, 400);
            adminUser.setEscenarioPrincipal(this);
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
    
    public void ventanaRegister(){
        try {
            RegisterController registerController = (RegisterController) cambioEscena("Register.fxml", 500, 400);
            registerController.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaGroups(){
        try {
            GroupsController GroupsController = (GroupsController) cambioEscena("Groups.fxml", 500, 400);
            GroupsController.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCreateGroups(){
        try {
            CreateGropusController CreateGropusController = (CreateGropusController) cambioEscena("CreateGropus.fxml", 500, 400);
            CreateGropusController.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDeleteGroups(){
        try {
            DeleteGroupsController DeleteGroupsController = (DeleteGroupsController) cambioEscena("DeleteGroups.fxml", 500, 400);
            DeleteGroupsController.setEscenarioPrincipal(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public Initializable cambioEscena(String fxml, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTAS + fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        String ruta = PAQUETE_VISTAS + fxml;
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTAS + fxml));
        escena = new Scene ((AnchorPane) cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargadorFXML.getController();
        return resultado;      
    }
    
}
