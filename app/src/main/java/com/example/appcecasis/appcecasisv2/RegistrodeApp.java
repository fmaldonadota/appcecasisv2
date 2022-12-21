package com.example.appcecasis.appcecasisv2;

import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class RegistrodeApp extends AppCompatActivity {


    TextView claveApp;

    Button link;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrode_app);

        String id = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);


        Log.d("clave",""+id);

        claveApp=(EditText)findViewById(R.id.clave);
        claveApp.setText(id);


        link=(Button)findViewById(R.id.linkPagina);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://172.16.24.32/serv/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });





    }
}
