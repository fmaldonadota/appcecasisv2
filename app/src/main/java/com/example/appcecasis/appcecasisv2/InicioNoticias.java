package com.example.appcecasis.appcecasisv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
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
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class InicioNoticias extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView listView;

    ArrayList titulo = new ArrayList();
    ArrayList descrip = new ArrayList();
    ArrayList image= new ArrayList();



    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null;

    static String key;

    //dialog
    SmartImageView smartimageviewfin;
    TextView tvtitulofin,tvdescripcionfin;

    String ip=ConfigConexión.conec;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_noticias);


        // sa = (TextView) findViewById(R.id.textView3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*/   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "holaaaa", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); /*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //poner usuario en el navige

        View hView = navigationView.getHeaderView(0);
        TextView correo = (TextView) hView.findViewById(R.id.nombreUsuario);
        TextView contra = (TextView) hView.findViewById(R.id.rolUsuario);

        //Bundle bundle=getIntent().getExtras();
        //String datos = bundle.getString("cod");

        SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);




        String s = prefernece.getString("nombre",nombrecompleto);

       // Log.d("nombre usuario", s+"");
        String d = prefernece.getString("nombrerol",nombre_rol);

        key = prefernece.getString("key",key);
        //String datos2 = bundle.getString("contra");

        Log.d("token","http://"+ip+"/serv/noticias.php?key="+key+"");



        correo.setText(d);
        contra.setText(s);
        //-----------------------------------------------------

        navigationView.setNavigationItemSelectedListener(this);


        //Bundle parametros = this.getIntent().getExtras();
        //String datos = parametros.getString("cod");
        //sa.setText(datos);


        listView=(ListView)findViewById(R.id.lista);

        descargarImages();

        //dialog
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                //Object o = listView.getItemAtPosition(position);
                // Realiza lo que deseas, al recibir clic en el elemento de tu listView determinado por su posicion.
                //  Log.i("Click", "click en el elemento " + position + " de mi ListView");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(InicioNoticias.this);
                //   mBuilder.setTitle("Noticias");

                View mView = getLayoutInflater().inflate(R.layout.activity_inicio_noticiaspop,null);

                smartimageviewfin=(SmartImageView)mView.findViewById(R.id.imageView4);
                tvtitulofin=(TextView)mView.findViewById(R.id.textView6);
                tvdescripcionfin=(TextView)mView.findViewById(R.id.descrip_tx);

                String urlfinal="http://"+ip+"/serv/img/"+image.get(position).toString();
                Rect rect =new Rect (smartimageviewfin.getLeft(), smartimageviewfin.getTop(),smartimageviewfin.getRight(),smartimageviewfin.getBottom());
                smartimageviewfin.setImageUrl(urlfinal,rect);
                tvtitulofin.setText(titulo.get(position).toString());
                tvdescripcionfin.setText(descrip.get(position).toString());

                mBuilder.setView(mView);
                mBuilder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
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

    private void descargarImages() {
        titulo.clear();
        descrip.clear();
        image.clear();


        final ProgressDialog progressDialog = new ProgressDialog(InicioNoticias.this);
        progressDialog.setMessage("Cargar Datos....");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ip+"/serv/noticias.php?key="+key+"", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray =new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){
                            titulo.add(jsonArray.getJSONObject(i).getString("nombre_noti"));
                            descrip.add(jsonArray.getJSONObject(i).getString("descrip_noti"));

                            image.add(jsonArray.getJSONObject(i).getString("img"));

                        }
                        listView.setAdapter(new ImageAdapter (getApplicationContext()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(InicioNoticias.this, "No cumple el requisito del token", Toast.LENGTH_SHORT).show();
                        Log.d("errores","no lee el token");

                        //cerrar cesion en caso de error de tokens

                        SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
                        prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();
                        //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
                        prefernece.edit().clear().commit();
                        Intent intentReg = new Intent(InicioNoticias.this,MainActivity.class);
                        InicioNoticias.this.startActivity(intentReg);
                        finish();


                    }

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(InicioNoticias.this, "No cumple el requisito del token", Toast.LENGTH_SHORT).show();
                Log.d("errores","no lee el token");

            }
        });

    }

    private class ImageAdapter extends BaseAdapter {


        Context ctx;
        LayoutInflater layoutinflater;
        SmartImageView smartimageview;
        TextView tvtitulo,tvdescripcion;

        public ImageAdapter(Context applicationContext) {

            this.ctx=applicationContext;
            layoutinflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getCount() {
            return image.size();
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
            ViewGroup viewGroup=(ViewGroup)layoutinflater.inflate(R.layout.activity_inicio_noticias2,null);

            smartimageview=(SmartImageView)viewGroup.findViewById(R.id.image1);
            tvtitulo=(TextView)viewGroup.findViewById(R.id.tvtitulo);
            tvdescripcion=(TextView)viewGroup.findViewById(R.id.tvdescrip);

            String urlfinal="http://"+ip+"/serv/img/"+image.get(position).toString();
            Rect rect =new Rect (smartimageview.getLeft(), smartimageview.getTop(),smartimageview.getRight(),smartimageview.getBottom());
            smartimageview.setImageUrl(urlfinal,rect);
            tvtitulo.setText(titulo.get(position).toString());
            tvdescripcion.setText("Leer mas");


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
        getMenuInflater().inflate(R.menu.inicio_noticias, menu);
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

            Intent intentReg = new Intent(InicioNoticias.this, InicioNoticias.class);
            InicioNoticias.this.startActivity(intentReg);

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            Intent intentReg = new Intent(InicioNoticias.this, LaboratorioInicio.class);
            InicioNoticias.this.startActivity(intentReg);


        } else if (id == R.id.nav_slideshow) {

            Intent intentReg = new Intent(InicioNoticias.this, MaterialesInicio.class);
            InicioNoticias.this.startActivity(intentReg);

        } //else if (id == R.id.nav_manage) {

            //  Intent intentReg = new Intent(inicioEst.this, PendientesEst.class);
            //  inicioEst.this.startActivity(intentReg);

        //}
        else if (id == R.id.nav_share) {

            Intent intentReg = new Intent(InicioNoticias.this, MantenimientoInicio.class);
            InicioNoticias.this.startActivity(intentReg);

        } else if (id == R.id.nav_send) {

            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();

            //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().clear().commit();

            Intent intentReg = new Intent(InicioNoticias.this,MainActivity.class);
            InicioNoticias.this.startActivity(intentReg);

            finish();

        }
        else if (id == R.id.nav_view) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }

}
