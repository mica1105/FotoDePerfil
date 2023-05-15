package com.example.fotodeperfil.ui.Registo;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fotodeperfil.model.Usuario;
import com.example.fotodeperfil.request.ApiClient;

import java.io.ByteArrayOutputStream;


public class ViewModelRegistro extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<Usuario> usuario;
    private MutableLiveData<String> mensaje;
    private MutableLiveData<Bitmap> foto;

    public ViewModelRegistro(@NonNull Application application) {
        super(application);
        context= application.getApplicationContext();
        apiClient= new ApiClient();
    }

    public LiveData<Usuario> getUsuario() {
        if (usuario == null){
            usuario= new MutableLiveData<>();
        }
        return usuario;
    }

    public LiveData<String> getMensaje() {
        if(mensaje== null){
            mensaje= new MutableLiveData<>();
        }
        return mensaje;
    }

    public LiveData<Bitmap> getFoto(){
        if(foto==null){
            foto=new MutableLiveData<>();
        }
        return foto;
    }

    public void respuestaDeCamara(int requestCode, int resultCode, @Nullable Intent data,int REQUEST_IMAGE_CAPTURE){
        Log.d("salida",requestCode+"");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Recupero los datos provenientes de la camara.
            Bundle extras = data.getExtras();
            //Casteo a bitmap lo obtenido de la camara.
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            apiClient.guardarFoto(context, b);
            Bitmap miFoto= apiClient.cargarFoto(context);
            if (miFoto != null){
                foto.setValue(miFoto);
            }
        }
    }

    public void registrar(String dni, String apellido, String nombre, String email, String pass){
            int dni1 = Integer.parseInt(dni);
            Usuario u = new Usuario(dni1, apellido, nombre, email, pass);
            apiClient.guardar(context, u);
            mensaje.setValue("El usuario fue guardado exitosamente");
    }

    public void mostrar(Usuario u){
        if( u != null) {
            u = apiClient.leer(context);
            usuario.setValue(u);
            Bitmap miFoto= apiClient.cargarFoto(context);
            foto.setValue(miFoto);
        }
    }
}
