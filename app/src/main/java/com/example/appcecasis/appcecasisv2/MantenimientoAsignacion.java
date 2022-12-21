package com.example.appcecasis.appcecasisv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MantenimientoAsignacion extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView listView;

    ArrayList idreserv = new ArrayList();
    ArrayList labreserv = new ArrayList();
    ArrayList horareserv= new ArrayList();
    ArrayList diareserv= new ArrayList();
    ArrayList estadoreserv= new ArrayList();
    ArrayList nombrereserv= new ArrayList();
    ArrayList apellidoreserv= new ArrayList();

    ArrayList fechaasig= new ArrayList();

    String ip=ConfigConexión.conec;


    MultiAutoCompleteTextView solucion;

    TextView tvestadoreserv;
    TextView tvidreserv,tvlabreserv,tvhorareserv,tvdiareserv,nombreserv,tvfechaasig;

    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null,cedula_usuariov=null;

    String cedula_usuario,idincidente;


    Spinner estados;

    static String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_asignacion);
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
        key = prefernece.getString("key",key);

        correo.setText(d);
        contra.setText(s);

        cedula_usuario = prefernece.getString("cedula_usuario",cedula_usuariov);
        navigationView.setNavigationItemSelectedListener(this);



        listView=(ListView)findViewById(R.id.verreservalab);
        descargarmateriales();





        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {

                //Object o = listView.getItemAtPosition(position);
                // Realiza lo que deseas, al recibir clic en el elemento de tu listView determinado por su posicion.
                //  Log.i("Click", "click en el elemento " + position + " de mi ListView");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MantenimientoAsignacion.this);
                //   mBuilder.setTitle("Noticias");

                final View mView = getLayoutInflater().inflate(R.layout.activity_mantenimiento_asignacionpop,null);



                mBuilder.setView(mView);
                mBuilder.setNegativeButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        tvidreserv=mView.findViewById(R.id.idreserv);
                        idincidente=idreserv.get(position).toString();

                        solucion=mView.findViewById(R.id.detallesolucion);
                        estados=mView.findViewById(R.id.estadosolucion);


                        guardar();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });

    }


  /*/  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_mantenimiento_asignacionpop, container, false);
        solucion=rootView.findViewById(R.id.detallesolucion);
        estados=rootView.findViewById(R.id.estadosolucion);



    }/*/



    private  void guardar(){

        AsyncHttpClient client = new AsyncHttpClient();


        RequestParams params = new RequestParams();


        params.put("id",idincidente+"");
        params.put("estado",estados.getSelectedItem().toString());
        params.put("solucion",solucion.getText().toString());
        params.put("key",key);


        client.post("http://"+ip+"/serv/modificarsolucion.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Toast.makeText(MantenimientoAsignacion.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Toast.makeText(MantenimientoAsignacion.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void descargarmateriales() {

        idreserv.clear();
        labreserv.clear();
        horareserv.clear();
        diareserv.clear();
        estadoreserv.clear();
        nombrereserv.clear();
        apellidoreserv.clear();
        fechaasig.clear();

        final ProgressDialog progressDialog = new ProgressDialog(MantenimientoAsignacion.this);
        progressDialog.setMessage("Cargar Datos....");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ip+"/serv/verinsidenteasignado.php?ci="+cedula_usuario+"&key="+key+"", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray =new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){
                            idreserv.add(jsonArray.getJSONObject(i).getString("id_incidente"));
                            labreserv.add(jsonArray.getJSONObject(i).getString("laboratorio_incidente"));
                            horareserv.add(jsonArray.getJSONObject(i).getString("num_maquina"));
                            diareserv.add(jsonArray.getJSONObject(i).getString("des_incidente"));
                            estadoreserv.add(jsonArray.getJSONObject(i).getString("estado_inicidente"));

                            fechaasig.add(jsonArray.getJSONObject(i).getString("fechaincidente"));


                        }
                        listView.setAdapter(new MantenimientoAsignacion.ImageAdapter(getApplicationContext()));

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

            ViewGroup viewGroup=(ViewGroup)layoutinflater.inflate(R.layout.activity_mantenimiento_asignacion2,null);


            tvidreserv=(TextView)viewGroup.findViewById(R.id.idreserv);
            tvlabreserv=(TextView)viewGroup.findViewById(R.id.labreserv);
            tvhorareserv=(TextView)viewGroup.findViewById(R.id.horareserv);
            tvdiareserv=(TextView)viewGroup.findViewById(R.id.diareserv);
            tvestadoreserv=(TextView)viewGroup.findViewById(R.id.estadoin);

            tvfechaasig=(TextView)viewGroup.findViewById(R.id.fechaasig);


            //String urlfinal="http://172.16.24.223:8081/serv/img/"+disponible.get(position).toString();
            // Rect rect =new Rect (smartimageview.getLeft(), smartimageview.getTop(),smartimageview.getRight(),smartimageview.getBottom());
            // smartimageview.setImageUrl(urlfinal,rect);

            tvidreserv.setText("N Incidencia: " + idreserv.get(position).toString());
            tvlabreserv.setText("Inicidencia en " +labreserv.get(position).toString());
            tvhorareserv.setText("Maquina Numero: " + horareserv.get(position).toString());
            tvdiareserv.setText("Descripcion: " + diareserv.get(position).toString());
            tvestadoreserv.setText("Estado: " + estadoreserv.get(position).toString());
            //nombreserv.setText("Asignado a: " + nombrereserv.get(position).toString());

            tvfechaasig.setText("Fecha : " + fechaasig.get(position).toString());

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
        getMenuInflater().inflate(R.menu.mantenimiento_asignacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}