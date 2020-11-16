package com.application.beans;
import com.application.system.Storage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 *
 * @author ayalr
 */
public class BinaryTree {
    
    public BinaryTree(){}
    
    private static Node<Images> root = new Node<Images>();
    private static int nodesInTree;
    int sizeT = 0;
    List<Images> tree = new ArrayList<>();
    Node<Images> auxL = new Node<>();
    Node<Images> auxR = new Node<>();
    
    private void insertInTree(Images imagen){
        //Create new object 
        Node<Images> newImagen = new Node<>();
        newImagen.setValue(imagen);
        
        newImagen.setLeft(auxL);
        newImagen.setRight(auxR);
        tree.add(imagen);
        
        if (isEmpty() == false) {
            insert(newImagen, root);
            route(root);
        }
        else{
            root.setValue(imagen);
            root.setLeft(auxL);
            root.setRight(auxR);
            route(root);
        }
    }
    
    private boolean isEmpty(){
        if (root.value == null) {
            return true;
        }
        return false;
    }
    
    private void insert(Node<Images> newImagen, Node<Images> node){
        if (node.value == null) {
            node = newImagen;
        } else {
            if (comparison(newImagen.value.getUser(), newImagen.value.getPath(), node.value.user, node.value.path) == false) {
                if (node.right.value == null) {
                    node.right = newImagen;
                }
                else{ insert(newImagen, node.right); }
            } else {
                if (node.left.value == null) {
                    node.left = newImagen;
                }
                else{ insert(newImagen, node.left); }
            }
        }
    }
    
   
    private void route(Node<Images> node){
        if (node.left.value == null && node.right.value == null) {
            node.value.setLeft(-1);
            node.value.setRight(-1);
            tree.set(node.value.getRegister()-1, node.value);
        }
        else{
            if (node.left.value != null){
                node.value.setLeft(node.left.value.getRegister());
                if (node.right.value == null) { node.value.setRight(-1); } 
                else { node.value.setRight(node.right.value.getRegister()); }
                tree.set(node.value.getRegister()-1, node.value);
                route(node.left);
            }
            if (node.right.value != null) {
                node.value.setRight(node.right.value.getRegister());
                if (node.left.value == null) { node.value.setLeft(-1); } 
                else { node.value.setLeft(node.left.value.getRegister()); }
                tree.set(node.value.getRegister()-1, node.value);
                route(node.right);
            }
        }
    }
    
    //Comparison
    public boolean comparison(String user, String path, String user2, String path2){
        if(user.equals(user2)){
            //go right
            if(path.compareTo(path2) > 0) { return false; }
            //go left
            else{ return true; }
        }
        else if(user.compareTo(user2) > 0){ return false; }
        else{ return true; }
    }
    
    public boolean ifExist(String user, String user2, String path, String path2){
        if (user.equals(user2)) {
            if (path.equals(path2)) {
                return true;
            }
            else{ return false; }
        }
        else{ return false; }
    }
    
    //Write data in file
    public boolean insertInFile(Images imagen){
        try {
            File MEIA = new File("C:\\MEIA");
            if (!MEIA.exists()) {
                MEIA.mkdir();
            }
            File usuario = new File("C:\\MEIA\\imagen_usuario.txt");
            if (!usuario.exists()) {
                usuario.createNewFile();
            }
            if (usuario.exists() == true) {
                tree = new ArrayList<>();
                root = new Node<Images>();
                FileReader LecturaArchivo;
                LecturaArchivo = new FileReader(usuario);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea = "";

                Linea = LeerArchivo.readLine();
                String[] split;
                while (Linea != null) {
                    Images aux = new Images();
                    split = Linea.split("[|]");
                    aux.setRegister(Integer.parseInt(split[0]));
                    aux.setLeft(-1);
                    aux.setRight(-1);
                    aux.setUser(split[3]);
                    aux.setPath(split[4]);
                    aux.setDate(split[5]);
                    aux.setStatus(Integer.parseInt(split[6]));
                    insertInTree(aux);
                    Linea = LeerArchivo.readLine();
                }
                
                if (tree.isEmpty()) {
                    insertInTree(imagen);
                    FileWriter fw = new FileWriter(usuario, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (Images i : tree) {
                        bw.write(i.toString());
                        bw.newLine();
                    }
                    bw.close();
                    LecturaArchivo.close();
                    LeerArchivo.close();

                    writeDescriptor();
                    return true;
                }
                else{
                    for (Images i : tree) {
                        if(ifExist(i.getUser(), imagen.getUser(), i.getPath(), imagen.getPath())){
                           return false; 
                        }
                    }
                    FileWriter fw = new FileWriter(usuario, false);
                    BufferedWriter bw = new BufferedWriter(fw);
                    insertInTree(imagen);
                    for (Images j : tree) {
                        bw.write(j.toString());
                        bw.newLine();
                    }
                    bw.close();
                    LecturaArchivo.close();
                    LeerArchivo.close();

                    writeDescriptor();
                    return true;
                }
            }
            return false;

        } catch (Exception ex) {
            return false;
        }
    }

    //Write descriptor
    public void writeDescriptor() throws IOException {

        File descIndice = new File("C:\\MEIA\\desc_imagenes.txt");
        if (!descIndice.exists()) {
            descIndice.createNewFile();
        }
        if (descIndice.exists()) {
            FileReader DescInit;
            DescInit = new FileReader(descIndice);
            BufferedReader Reader = new BufferedReader(DescInit);
            String Line = "";
            Line = Reader.readLine();

            String[] information = new String[8];
            Integer counter = 0;
            while (Line != null) {
                information[counter] = new String();
                information[counter] = Line;
                counter++;
                Line = Reader.readLine();
            }
            if (counter == 0) {
                information[0] = "Nombre_simbolico:desc_imagenes.txt";
                information[1] = "fecha_creacion:" + java.time.LocalDateTime.now();
                information[2] = "usuario_creacion:" + Storage.Instance().actualUser.getUsername();
                information[3] = "fecha_modificacion:" + java.time.LocalDateTime.now();
                information[4] = "usuario_modificacion:" + Storage.Instance().actualUser.getUsername();
                information[5] = "#_registros:" + tree.size();
                information[6] = "registros_activos:" + tree.size();
                information[7] = "registros_inactivos:0";
            } else {
                information[3] = "fecha_modificacion:" + java.time.LocalDateTime.now();
                information[4] = "usuario_modificacion:" + Storage.Instance().actualUser.getUsername();
                information[5] = "#_registros:" + tree.size();
                information[6] = "registros_activos:" + tree.size();
                information[7] = "registros_inactivos:0";
            }

            FileWriter fw = new FileWriter(descIndice, false);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < 8; i++) {
                bw.write(information[i]);
                bw.newLine();
            }

            bw.close();
            DescInit.close();
            Reader.close();
        }
    }
    
    //Read descriptor
    
    public boolean readDescriptor() throws IOException {
        File usuario = new File("C:\\MEIA\\desc_imagenes.txt");
        if (usuario.exists() == true) {
            FileReader LecturaArchivo;
            LecturaArchivo = new FileReader(usuario);
            BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
            String Linea = "";
            Integer counter = 0;
            Linea = LeerArchivo.readLine();
            while (Linea != null) {
                if (counter == 5) {
                    sizeT = Integer.parseInt(Linea.split(":")[1]);
                }
                counter++;
                Linea = LeerArchivo.readLine();
            }
            LecturaArchivo.close();
            LeerArchivo.close();
            return true;
        }
        return false;
    }
    public int sizeTree() throws IOException{
        if (readDescriptor()) {
            return sizeT + 1;
        }
        return 1;
    }
}
