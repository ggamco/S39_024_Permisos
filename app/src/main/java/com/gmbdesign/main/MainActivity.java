package com.gmbdesign.main;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * Revisar el orden de ejecución de esta actividad. No está muy optimizada la programación. -> HECHO!!
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(comprobarPermisos()){
            obtenerCorreosUsuario();
        } else {
            pedirPermisos();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.d("TAG", "El usuario nos ha concedido el permiso");
            obtenerCorreosUsuario();
        } else {
            Log.d("TAG", "El usuario nos ha denegado el permiso");
            finish();
        }

    }

    private String[] obtenerCorreosUsuario(){
        String[] correos = null;

        AccountManager accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        Account[] listaCuentas = accountManager.getAccounts();

        for(Account cuenta : listaCuentas){

            //podemos recorrer la lista de cuentas y almacenar en el array
            //con este metodo podemos comprobar el paquete al que pertenece ("com.google") por ejemplo.
            String tipocuenta = cuenta.type;
            //con este metodo recuperamos el nombre de la cuenta.
            String nombreCuenta = cuenta.name;

            //pintamos en el log la cuenta
            Log.d("TAG", "Cuenta: " + nombreCuenta);

        }

        //este metodo no está devolviendo nada, solo estamos pintando
        return correos;
    }

    private void pedirPermisos(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.GET_ACCOUNTS)){

            //Mostrar el mensaje de peticion
            //alertDialog

        }

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS},100);

    }

    private boolean comprobarPermisos(){

        boolean tengoPermiso = false;

        int permiso = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);

        if (permiso == PackageManager.PERMISSION_GRANTED) {
            tengoPermiso = true;
        }

        return tengoPermiso;

    }
}
