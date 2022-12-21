package com.example.appcecasis.appcecasisv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class LaboratorioVerReserva extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;

    ArrayList idreserv = new ArrayList();
    ArrayList labreserv = new ArrayList();
    ArrayList horareserv= new ArrayList();
    ArrayList diareserv= new ArrayList();
    ArrayList estadoreserv= new ArrayList();
    ArrayList nombrereserv= new ArrayList();
    ArrayList apellidoreserv= new ArrayList();

    String ip=ConfigConexión.conec;





    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null,cedula_usuario=null;


    String cedula_usuariov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_ver_reserva);
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




        cedula_usuario = prefernece.getString("cedula_usuario",cedula_usuariov);

        Log.d("cedula",cedula_usuario+"");


        listView=(ListView)findViewById(R.id.verreservalab);
        descargarmateriales();





    }


    private void descargarmateriales() {




        idreserv.clear();
        labreserv.clear();
        horareserv.clear();
        diareserv.clear();
        estadoreserv.clear();
        nombrereserv.clear();
        apellidoreserv.clear();





        final ProgressDialog progressDialog = new ProgressDialog(LaboratorioVerReserva.this);
        progressDialog.setMessage("Cargar Datos....");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ip+"/serv/reservaLab.php?ci="+cedula_usuario+"", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray =new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){
                            idreserv.add(jsonArray.getJSONObject(i).getString("id_reserva"));
                            labreserv.add(jsonArray.getJSONObject(i).getString("id_lab"));
                            horareserv.add(jsonArray.getJSONObject(i).getString("id_hora"));
                            diareserv.add(jsonArray.getJSONObject(i).getString("dfrom"));
                            estadoreserv.add(jsonArray.getJSONObject(i).getString("id_estado"));
                            nombrereserv.add(jsonArray.getJSONObject(i).getString("nombre"));
                            apellidoreserv.add(jsonArray.getJSONObject(i).getString("apellido"));

                        }
                        listView.setAdapter(new ImageAdapter(getApplicationContext()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private class ImageAdapter extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutinflater;
        TextView tvidreserv,tvlabreserv,tvhorareserv,tvdiareserv,tvestadoreserv,nombreserv;

        public ImageAdapter(Context applicationContext) {

            this.ctx=applicationContext;
            layoutinflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getCount() {
            return idreserv.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewGroup viewGroup=(ViewGroup)layoutinflater.inflate(R.layout.activity_laboratorio_ver_reserva2,null);


            tvidreserv=(TextView)viewGroup.findViewById(R.id.idreserv);
            tvlabreserv=(TextView)viewGroup.findViewById(R.id.labreserv);
            tvhorareserv=(TextView)viewGroup.findViewById(R.id.horareserv);
            tvdiareserv=(TextView)viewGroup.findViewById(R.id.diareserv);
            tvestadoreserv=(TextView)viewGroup.findViewById(R.id.estadoreserv);
            nombreserv=(TextView)viewGroup.findViewById(R.id.nombrereserv);

            //String urlfinal="http://172.16.24.223:8081/serv/img/"+disponible.get(position).toString();
            // Rect rect =new Rect (smartimageview.getLeft(), smartimageview.getTop(),smartimageview.getRight(),smartimageview.getBottom());
            // smartimageview.setImageUrl(urlfinal,rect);

            tvidreserv.setText("Reserva Número: " + idreserv.get(position).toString());
            tvlabreserv.setText("Reserva del " +labreserv.get(position).toString());
            tvhorareserv.setText("Hora: " + horareserv.get(position).toString());
            tvdiareserv.setText("Dìa: " + diareserv.get(position).toString());
            tvestadoreserv.setText("Estado: " +estadoreserv.get(position).toString());
            nombreserv.setText(apellidoreserv.get(position).toString()+" "+nombrereserv.get(position).toString());


            return viewGroup;
        }
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
        getMenuInflater().inflate(R.menu.laboratorio_ver_reserva, menu);
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
            Intent intentReg = new Intent(LaboratorioVerReserva.this, InicioNoticias.class);
            LaboratorioVerReserva.this.startActivity(intentReg);
        } else if (id == R.id.nav_gallery) {

            Intent intentReg = new Intent(LaboratorioVerReserva.this, LaboratorioInicio.class);
            LaboratorioVerReserva.this.startActivity(intentReg);

        } else if (id == R.id.nav_slideshow) {

            Intent intentReg = new Intent(LaboratorioVerReserva.this, MaterialesInicio.class);
            LaboratorioVerReserva.this.startActivity(intentReg);

        }/*/ else if (id == R.id.nav_manage) {

        }/*/ else if (id == R.id.nav_share) {

            Intent intentReg = new Intent(LaboratorioVerReserva.this, MantenimientoInicio.class);
            LaboratorioVerReserva.this.startActivity(intentReg);

        } else if (id == R.id.nav_send) {

            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();

            //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().clear().commit();

            Intent intentReg = new Intent(LaboratorioVerReserva.this,MainActivity.class);
            LaboratorioVerReserva.this.startActivity(intentReg);

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
