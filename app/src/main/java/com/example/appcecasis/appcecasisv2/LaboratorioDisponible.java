package com.example.appcecasis.appcecasisv2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class LaboratorioDisponible extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    String s;

    private ListView listView;

    ArrayList id = new ArrayList();

    ArrayList nombre = new ArrayList();
    ArrayList maquina = new ArrayList();
    //ArrayList disponible= new ArrayList();

    TextView tbtitulofin,tbdescripcionfin,tvdisponible;

    String laboratorio,maq,fecha;


    String ip=ConfigConexión.conec;

    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null,cedula_usuariov=null;


    TextView tbtitulo,tbdescripcion,idDetalle,detalleHora,detalleFecha,detalleSoftware,detalleNombre;

    String horae;

    String fechae;

    String filtroe;


    List Reserva;

    String cedula_usuario;

    String numeroMaquinase;

    String d;

    static String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorio_disponible);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        Bundle bundle=getIntent().getExtras();
        fechae = bundle.getString("fechae");
        horae = bundle.getString("horae");
         numeroMaquinase = bundle.getString("numeroMaquinase");
        filtroe = bundle.getString("filtroe");

       // Log.d("Error",fechae+" "+horae+" "+numeroMaquinase);

        View hView = navigationView.getHeaderView(0);
        TextView correo = (TextView) hView.findViewById(R.id.nombreUsuario);
        TextView contra = (TextView) hView.findViewById(R.id.rolUsuario);

        //Bundle bundle=getIntent().getExtras();
        //String datos = bundle.getString("cod");

        SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
        s = prefernece.getString("nombre",nombrecompleto);

        // Log.d("nombre usuario", s+"");
        d = prefernece.getString("nombrerol",nombre_rol);
        //String datos2 = bundle.getString("contra");
        correo.setText(d);
        contra.setText(s);
        cedula_usuario = prefernece.getString("cedula_usuario",cedula_usuariov);
        key = prefernece.getString("key",key);
     //   Log.d("Error",cedula_usuario+"");


        listView=(ListView)findViewById(R.id.disponiblelabo);
        descargarlab();



        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {

                //Object o = listView.getItemAtPosition(position);
                // Realiza lo que deseas, al recibir clic en el elemento de tu listView determinado por su posicion.
                //  Log.i("Click", "click en el elemento " + position + " de mi ListView");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LaboratorioDisponible.this);
                mBuilder.setTitle("Solicitud de Reserva");

                View mView = getLayoutInflater().inflate(R.layout.activity_laboratorio_reservapop,null);



                idDetalle=(TextView)mView.findViewById(R.id.detallelabs);
                detalleHora=(TextView)mView.findViewById(R.id.detalleHora);
                detalleFecha=(TextView)mView.findViewById(R.id.detalleFecha);
                detalleSoftware=(TextView)mView.findViewById(R.id.datelleSoftware);
                detalleNombre=(TextView)mView.findViewById(R.id.detalleNombre);
                //   tvdisponible=(TextView)viewGroup.findViewById(R.id.tvdisponible);

                //String urlfinal="http://172.16.24.223:8081/serv/img/"+disponible.get(position).toString();
                // Rect rect =new Rect (smartimageview.getLeft(), smartimageview.getTop(),smartimageview.getRight(),smartimageview.getBottom());
                // smartimageview.setImageUrl(urlfinal,rect);

                // tvdisponible.setText("Disponibles: " + disponible.get(position).toString());
              //  String id=id.get(position).toString();
                idDetalle.setText(nombre.get(position).toString());
                detalleHora.setText(horae.toString());
                detalleFecha.setText(fechae.toString());
                detalleSoftware.setText(filtroe.toString());
                detalleNombre.setText(s.toString());
               // tbdescripcion.setText("Número de Maquinas: " + maquina.get(position).toString());

               // tbtitulo.setText(nombre.get(position).toString());

                mBuilder.setView(mView);
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.Reservar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //insertar en la base reservar
                        // Notification();
/*/
                        View mView = getLayoutInflater().inflate(R.layout.activity_dispo_lab_est2,null);

                        tbtitulofin=(TextView)mView.findViewById(R.id.tvtitulo);
                        tbdescripcionfin=(TextView)mView.findViewById(R.id.tvdescrip);

                        laboratorio=(nombre.get(position).toString());
                        maq=("Número de Maquinas: " + maquina.get(position).toString());

//insert

                        Thread tr=new Thread(){
                            @Override
                            public void run() {
                                final String resultado=enviarDatosGet(laboratorio,maq);
                                runOnUiThread(new Runnable() { //para trabajar con la interface
                                    @Override
                                    public void run() {
                                        int r=ObtenerDatosJSON(resultado);
                                        if (r>0){
                                            Intent i=new Intent(getApplicationContext(),inicioEst.class);
                                            startActivity(i);
                                            //finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Usuario o contrasena incorrectos",Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });

                            }
                        };
                        tr.start();

/*/



                        if(d.equals("ESTUDIANTE")){

                            insertar();

                            NotificationCompat.Builder mBuilder;
                            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                            int icono = R.mipmap.ic_launcher;
                            Intent intent = new Intent(LaboratorioDisponible.this, LaboratorioDispoNotificacion.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(LaboratorioDisponible.this, 0,intent, 0);




                            mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(icono)
                                    .setContentTitle("Solicitud de Reserva")
                                    .setContentText("El estudiante: "+s+" solicito una reserva")
                                    .setVibrate(new long[] {100, 250, 100, 500})
                                    .setAutoCancel(true);

                            mNotifyMgr.notify(1, mBuilder.build());


                            Intent intentReg = new Intent(LaboratorioDisponible.this, LaboratorioInicio.class);
                            LaboratorioDisponible.this.startActivity(intentReg);

                        }

                        if(d.equals("DOCENTE")){


                            insertar2();

                            NotificationCompat.Builder mBuilder;
                            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                            int icono = R.mipmap.ic_launcher;
                            Intent intent = new Intent(LaboratorioDisponible.this, LaboratorioDispoNotificacion.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(LaboratorioDisponible.this, 0,intent, 0);




                            mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(icono)
                                    .setContentTitle("Solicitud de Reserva")
                                    .setContentText("El Docente: "+s+" hizo una reserva")
                                    .setVibrate(new long[] {100, 250, 100, 500})
                                    .setAutoCancel(true);

                            mNotifyMgr.notify(1, mBuilder.build());

                            Intent intentReg = new Intent(LaboratorioDisponible.this, LaboratorioInicio.class);
                            LaboratorioDisponible.this.startActivity(intentReg);


                        }

                        if(d.equals("SECRETARIA")){


                            insertar2();

                            NotificationCompat.Builder mBuilder;
                            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                            int icono = R.mipmap.ic_launcher;
                            Intent intent = new Intent(LaboratorioDisponible.this, LaboratorioDispoNotificacion.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(LaboratorioDisponible.this, 0,intent, 0);




                            mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(icono)
                                    .setContentTitle("Solicitud de Reserva")
                                    .setContentText("SECRETARIA: "+s+" hizo una reserva")
                                    .setVibrate(new long[] {100, 250, 100, 500})
                                    .setAutoCancel(true);

                            mNotifyMgr.notify(1, mBuilder.build());

                            Intent intentReg = new Intent(LaboratorioDisponible.this, LaboratorioInicio.class);
                            LaboratorioDisponible.this.startActivity(intentReg);

                        }

                        if(d.equals("TECNICO")){

                            insertar2();

                            NotificationCompat.Builder mBuilder;
                            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                            int icono = R.mipmap.ic_launcher;
                            Intent intent = new Intent(LaboratorioDisponible.this, LaboratorioDispoNotificacion.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(LaboratorioDisponible.this, 0,intent, 0);




                            mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(icono)
                                    .setContentTitle("Solicitud de Reserva")
                                    .setContentText("Tecnico: "+s+" hizo una reserva")
                                    .setVibrate(new long[] {100, 250, 100, 500})
                                    .setAutoCancel(true);

                            mNotifyMgr.notify(1, mBuilder.build());

                            Intent intentReg = new Intent(LaboratorioDisponible.this, LaboratorioInicio.class);
                            LaboratorioDisponible.this.startActivity(intentReg);

                        }

                        if(d.equals("CORDINADOR")){

                            insertar2();

                            NotificationCompat.Builder mBuilder;
                            NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                            int icono = R.mipmap.ic_launcher;
                            Intent intent = new Intent(LaboratorioDisponible.this, LaboratorioDispoNotificacion.class);
                            PendingIntent pendingIntent = PendingIntent.getActivity(LaboratorioDisponible.this, 0,intent, 0);




                            mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                    .setContentIntent(pendingIntent)
                                    .setSmallIcon(icono)
                                    .setContentTitle("Solicitud de Reserva")
                                    .setContentText("Cordinador: "+s+" hizo una reserva")
                                    .setVibrate(new long[] {100, 250, 100, 500})
                                    .setAutoCancel(true);

                            mNotifyMgr.notify(1, mBuilder.build());

                            Intent intentReg = new Intent(LaboratorioDisponible.this, LaboratorioInicio.class);
                            LaboratorioDisponible.this.startActivity(intentReg);

                        }






                    }
                }).setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });


                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });


    }


    String valor= "2";

    private void insertar() {


        AsyncHttpClient client = new AsyncHttpClient();


        RequestParams params = new RequestParams();

        params.put("id", "2");
        params.put("idHora", horae);
        params.put("idEstado", "Pendiente");
        params.put("idSoftware", filtroe);
        params.put("idDia", "Viernes");
        params.put("idLab", idDetalle.getText() );
        params.put("cedulaLogin",cedula_usuario);
        params.put("cedulaReserva", cedula_usuario);
        params.put("cedulaEncargado", cedula_usuario);
        params.put("fecha",fechae);
        params.put("key",key);


        Log.d("key",key+"");

        client.post("http://"+ip+"/serv/insertareserva.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Toast.makeText(LaboratorioDisponible.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Toast.makeText(LaboratorioDisponible.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertar2() {


        AsyncHttpClient client = new AsyncHttpClient();


        RequestParams params = new RequestParams();

        params.put("id", "2");
        params.put("idHora", horae);
        params.put("idEstado", "Reservado");
        params.put("idSoftware", filtroe);
        params.put("idDia", "Viernes");
        params.put("idLab", idDetalle.getText() );
        params.put("cedulaLogin",cedula_usuario);
        params.put("cedulaReserva", cedula_usuario);
        params.put("cedulaEncargado", cedula_usuario);
        params.put("fecha",fechae);
        params.put("key",key);

        client.post("http://"+ip+"/serv/insertareserva.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Toast.makeText(LaboratorioDisponible.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Toast.makeText(LaboratorioDisponible.this, "Informacion correcta guardada", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void descargarlab() {
        nombre.clear();
        maquina.clear();
        //disponible.clear();

        final ProgressDialog progressDialog = new ProgressDialog(LaboratorioDisponible.this);
        progressDialog.setMessage("Cargar Datos....");
        progressDialog.show();


        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ip+"/serv/labfiltro.php?fechae="+fechae+"&horae="+horae+"&numeroMaquinase="+numeroMaquinase+"&key="+key+"", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray =new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){

                            id.add(jsonArray.getJSONObject(i).getString("id_lab"));
                            nombre.add(jsonArray.getJSONObject(i).getString("nombre_lab"));
                            maquina.add(jsonArray.getJSONObject(i).getString("maquinas_lab"));
                            //disponible.add(jsonArray.getJSONObject(i).getString("disponiblidad"));

                        }
                        listView.setAdapter(new ImageAdapter(getApplicationContext()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }


            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }

    private class ImageAdapter extends BaseAdapter {


        Context ctxs;
        LayoutInflater layoutinflaters;

        public ImageAdapter(Context applicationContext) {

            this.ctxs=applicationContext;
            layoutinflaters=(LayoutInflater)ctxs.getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getCount() {
            return maquina.size();
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

            ViewGroup viewGroupsd=(ViewGroup)layoutinflaters.inflate(R.layout.activity_laboratorio_disponible2,null);


            tbtitulo=(TextView)viewGroupsd.findViewById(R.id.tvtitulo);
            tbdescripcion=(TextView)viewGroupsd.findViewById(R.id.tvdescrip);
            //   tvdisponible=(TextView)viewGroup.findViewById(R.id.tvdisponible);

            //String urlfinal="http://172.16.24.223:8081/serv/img/"+disponible.get(position).toString();
            // Rect rect =new Rect (smartimageview.getLeft(), smartimageview.getTop(),smartimageview.getRight(),smartimageview.getBottom());
            // smartimageview.setImageUrl(urlfinal,rect);

            // tvdisponible.setText("Disponibles: " + disponible.get(position).toString());
            tbtitulo.setText(nombre.get(position).toString());
            tbdescripcion.setText("Número de Maquinas: " + maquina.get(position).toString());

            return viewGroupsd;
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
        getMenuInflater().inflate(R.menu.laboratorio_disponible, menu);
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

            Intent intentReg = new Intent(LaboratorioDisponible.this, InicioNoticias.class);
            LaboratorioDisponible.this.startActivity(intentReg);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent intentReg = new Intent(LaboratorioDisponible.this, LaboratorioInicio.class);
            LaboratorioDisponible.this.startActivity(intentReg);

        } else if (id == R.id.nav_slideshow) {

            Intent intentReg = new Intent(LaboratorioDisponible.this, MaterialesInicio.class);
            LaboratorioDisponible.this.startActivity(intentReg);


        } /*/else if (id == R.id.nav_manage) {

        } /*/else if (id == R.id.nav_share) {

            Intent intentReg = new Intent(LaboratorioDisponible.this, MantenimientoInicio.class);
            LaboratorioDisponible.this.startActivity(intentReg);

        } else if (id == R.id.nav_send) {

            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();

            //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().clear().commit();

            Intent intentReg = new Intent(LaboratorioDisponible.this,MainActivity.class);
            LaboratorioDisponible.this.startActivity(intentReg);

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
