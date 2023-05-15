package com.example.fotodeperfil.request;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.fotodeperfil.model.Usuario;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class ApiClient {

    public static void guardar(Context context, Usuario usuario){
        File directorio= context.getFilesDir();
        File archivo= new File(directorio, "cliente.dat");
        if(!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos= new FileOutputStream(archivo);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(usuario);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardarFoto(Context context, byte [] foto){
        File archivo =new File(context.getFilesDir(),"foto1.png");
        if(archivo.exists()){
            archivo.delete();
        }
        try {
            FileOutputStream fo=new FileOutputStream(archivo);
            BufferedOutputStream bo=new BufferedOutputStream(fo);
            bo.write(foto);
            bo.flush();
            bo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Usuario leer(Context context){
        Usuario usuario= new Usuario();
        File directorio= context.getFilesDir();
        File archivo= new File(directorio, "cliente.dat");
        try {
            FileInputStream fis= new FileInputStream(archivo);
            ObjectInputStream ois= new ObjectInputStream(fis);
            usuario= (Usuario) ois.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    public static Bitmap cargarFoto(Context context) {
        Bitmap imageBitmap;
        File directorio= context.getFilesDir();
        File archivo =new File(directorio,"foto1.png");
        imageBitmap= BitmapFactory.decodeFile(archivo.getAbsolutePath());
        return imageBitmap;
    }

    public static Usuario login(Context context,String mail, String pass){
        Usuario usuario= new Usuario();
        File directorio= context.getFilesDir();
        File archivo= new File(directorio, "cliente.dat");
        try {
            FileInputStream fis= new FileInputStream(archivo);
            ObjectInputStream ois= new ObjectInputStream(fis);
            Usuario usuario1= (Usuario) ois.readObject();
            String email= usuario1.getMail();
            String password= usuario1.getPassword();
            if(email.equals(mail) && password.equals(pass)) {
                usuario = usuario1;
            }else {
                usuario=null;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usuario;
    }

}
