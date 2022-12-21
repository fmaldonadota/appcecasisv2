package com.example.appcecasis.appcecasisv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btningresar;
    EditText txtUsuario,txtPass;
    RadioButton rbsesion;

    boolean isactivaterb;

    Button btnregistro;

    String claveapp;

    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    private static final String USUARIO = "usuario";
    private static final String KEY_EMAIL = "email";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);



        if(obtenerestadobutton()){
            Intent i=new Intent(getApplicationContext(),InicioNoticias.class);
            // i.putExtra("cod",txtUsuario.getText().toString());
            // i.putExtra("contra",txtPass.getText().toString());
            startActivity(i);
            finish();

        }


        claveapp = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        txtUsuario=(EditText) findViewById(R.id.txusuario);
        txtPass=(EditText) findViewById(R.id.txpass);
        btningresar=(Button) findViewById(R.id.button2);


        btnregistro=(Button) findViewById(R.id.btnRegistro);
        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this, RegistrodeApp.class);
                MainActivity.this.startActivity(intentReg);
            }
        });


        rbsesion=(RadioButton) findViewById(R.id.radioButton);
        rbsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isactivaterb){
                    rbsesion.setChecked(false);
                }
                isactivaterb=rbsesion.isChecked();
            }

        });
        btningresar.setOnClickListener(this);
    }
    public void guardarestadoradiobutton(){
        SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
        prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,rbsesion.isChecked()).apply();
    }

    public boolean obtenerestadobutton(){
        SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
        return prefernece.getBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false);

    }
    String valorCodigo;

    String nombre_rol,nombre_usuario,apellido_usuario,nombrecompleto,cedula_usuario,key;
    public void onClick(View v) {



        guardarestadoradiobutton();
        Thread tr=new Thread(){
            @Override
            public void run() {

                final String resultado=enviarDatosGet(txtUsuario.getText().toString(),txtPass.getText().toString());
                runOnUiThread(new Runnable() { //para trabajar con la interface
                    @Override
                    public void run() {
                        int r=ObtenerDatosJSON(resultado);

                        try {
                            JSONArray json=new JSONArray(resultado);
                            JSONObject objeto = json.getJSONObject(0);
                            nombre_rol = objeto.getString("nombre_rol"); //obtiene valor Ejemplo
                            nombre_usuario = objeto.getString("nombre_usuario"); //obtiene valor Ejemplo
                            apellido_usuario = objeto.getString("apellido_usuario"); //obtiene valor Ejemplo
                            cedula_usuario = objeto.getString("cedula_usuario"); //obtiene valor Ejemplo
                            key = objeto.getString("key"); //obtiene valor Ejemplo

                            nombrecompleto=apellido_usuario+" "+nombre_usuario;

                            Log.d("tokenserv",key+"");



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (r>0){
                            Intent i=new Intent(getApplicationContext(),InicioNoticias.class);
                            // Toast.makeText(getApplicationContext(),valorCodigo,Toast.LENGTH_LONG).show();
                            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);


                            prefernece.edit().putString("nombre",nombrecompleto).apply();
                            prefernece.edit().putString("nombrerol",nombre_rol).apply();
                            prefernece.edit().putString("cedula_usuario",cedula_usuario).apply();
                            prefernece.edit().putString("key",key).apply();

                            //i.putExtra("cod",nombre_rol);
                            //i.putExtra("contra",apellido_usuario+" "+nombre_usuario);

                            //USUARIO=tipo;


                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(),"Error de Registro de APP, Usuario o contrasena incorrectos",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        };
        tr.start();
    }

    public String enviarDatosGet(String usu,String pas){
        URL url=null;
        String linea="";
        int respuesta=0;
        StringBuilder resul=null; //recibe data

        try {

            String ip=ConfigConexión.conec;

            url = new URL("http://"+ip+"/serv/inicio.php?usu="+usu+"&pas="+pas+"&claveapp="+claveapp+"");

            HttpURLConnection conection= (HttpURLConnection)url.openConnection();
            respuesta=conection.getResponseCode();

            resul=new StringBuilder();

            if(respuesta==HttpURLConnection.HTTP_OK){

                InputStream in=new BufferedInputStream(conection.getInputStream());
                BufferedReader reader =new BufferedReader(new InputStreamReader(in));

                while((linea=reader.readLine())!=null){
                    resul.append(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resul.toString();
    }

    String tipo;
    public  int ObtenerDatosJSON(String response){

        int res=0;
        try {

            JSONArray json=new JSONArray(response);
            if(json.length()>0){
                res=1;
            }

        }catch (Exception e){}


        return  res;
    }
}
