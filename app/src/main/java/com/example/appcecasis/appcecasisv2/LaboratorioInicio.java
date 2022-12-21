package com.example.appcecasis.appcecasisv2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class LaboratorioInicio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null;


    Button registroLab,VerLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        TextView correo = (TextView) hView.findViewById(R.id.nombreUsuario);
        TextView contra = (TextView) hView.findViewById(R.id.rolUsuario);

        //Bundle bundle=getIntent().getExtras();
        //String datos = bundle.getString("cod");

        SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
        String s = prefernece.getString("nombre",nombrecompleto);

        // Log.d("nombre usuario", s+"");
        String d = prefernece.getString("nombrerol",nombre_rol);
        //String datos2 = bundle.getString("contra");
        correo.setText(d);
        contra.setText(s);

        navigationView.setNavigationItemSelectedListener(this);

        registroLab=(Button) findViewById(R.id.resLab);
        registroLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(LaboratorioInicio.this, LaboratoriosReserva.class);
                LaboratorioInicio.this.startActivity(intentReg);

            }
        });

        VerLab=(Button) findViewById(R.id.verinicidentes);
        VerLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentReg = new Intent(LaboratorioInicio.this, LaboratorioVerReserva.class);
                LaboratorioInicio.this.startActivity(intentReg);

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.laboratorio_inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intentReg = new Intent(LaboratorioInicio.this, InicioNoticias.class);
            LaboratorioInicio.this.startActivity(intentReg);

        } else if (id == R.id.nav_gallery) {

            Intent intentReg = new Intent(LaboratorioInicio.this, LaboratorioInicio.class);
            LaboratorioInicio.this.startActivity(intentReg);



        } else if (id == R.id.nav_slideshow) {

            Intent intentReg = new Intent(LaboratorioInicio.this, MaterialesInicio.class);
            LaboratorioInicio.this.startActivity(intentReg);

        }
        //else if (id == R.id.nav_manage) {

        //}
        else if (id == R.id.nav_share) {

            Intent intentReg = new Intent(LaboratorioInicio.this, MantenimientoInicio.class);
            LaboratorioInicio.this.startActivity(intentReg);

        } else if (id == R.id.nav_send) {

            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();

            //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().clear().commit();

            Intent intentReg = new Intent(LaboratorioInicio.this,MainActivity.class);
            LaboratorioInicio.this.startActivity(intentReg);

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
