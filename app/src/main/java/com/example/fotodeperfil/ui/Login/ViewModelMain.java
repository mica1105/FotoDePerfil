package com.example.fotodeperfil.ui.Login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fotodeperfil.model.Usuario;
import com.example.fotodeperfil.request.ApiClient;
import com.example.fotodeperfil.ui.Registo.RegistroActivity;


public class ViewModelMain extends AndroidViewModel {

    private Context context;
    private ApiClient apiClient;
    private MutableLiveData<String> error;

    public ViewModelMain(@NonNull Application application) {
        super(application);
        this.context= application.getApplicationContext();
        apiClient= new ApiClient();
    }

    public LiveData<String> getError() {
        if(error== null){
            error= new MutableLiveData<>();
        }
        return error;
    }

    public void autenticar(String mail, String pass){
        Usuario usuario= apiClient.login(context,mail,pass);
        if (usuario != null){
            error.setValue("");
            Intent intent= new Intent(context, RegistroActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("usuario",usuario);
            context.startActivity(intent);
        }
        else {
            error.setValue("*El email o password son incorrectos");
        }
    }

    public void registar(){
        Intent intent= new Intent(context, RegistroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
