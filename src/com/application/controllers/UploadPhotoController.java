
package com.application.controllers;
import com.application.beans.BinaryTree;
import com.application.beans.Images;
import com.application.system.Principal;
import com.application.system.Storage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JOptionPane;


/**
 * FXML Controller class
 *
 * @author ayalr
 */
public class UploadPhotoController implements Initializable {
    private Principal escenarioPrincipal;
    private BinaryTree photosTree = new BinaryTree();
    List<String> Files;
    @FXML private TextField TFRoute;
    @FXML private ImageView imageView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Files = new ArrayList<>();
        Files.add("*.PNG");
        Files.add("*.jpg");
        Files.add("*.jpeg");

    }    

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public Principal geteEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    @FXML
    public void uploadPicture(){
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Images", Files)); 
        File route = fc.showOpenDialog(null);
        if (route != null) {
            TFRoute.setText(route.getAbsolutePath());
        }
    }
    
    public void insertImagen() throws IOException{
        
        Images imagen = new Images();
        imagen.setRegister(photosTree.sizeTree());
        imagen.setLeft(-1);
        imagen.setRight(-1);
        imagen.setPath(TFRoute.getText());
        imagen.setDate("" + java.time.LocalDateTime.now());
        imagen.setStatus(1);
        imagen.setUser(Storage.Instance().actualUser.getUsername());
        if (photosTree.insertInFile(imagen)) {
            copyImagen(TFRoute.getText());
            File file = new File(TFRoute.getText());
            Image image = new Image(file.toURI().toString(),300, 300, false, false);
            imageView.setImage(image);
            TFRoute.setText("");
            JOptionPane.showMessageDialog(null, "La imagen se ha cargado exitosamente.", "Éxito", JOptionPane.PLAIN_MESSAGE);
        }
        else{
            TFRoute.setText("");
            imageView.setImage(null);
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error, intentelo nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void copyImagen(String path) throws FileNotFoundException, IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            File MEIA = new File("C:\\MEIA\\imagenes");
            if (!MEIA.exists()) {
                MEIA.mkdir();
            }
            File fil = new File(path);
            is = new FileInputStream(new File(path));
            os = new FileOutputStream(new File("C:\\MEIA\\imagenes\\"+fil.getName()));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
    
    public void back() { escenarioPrincipal.ventanaNormalUser(); }
}
