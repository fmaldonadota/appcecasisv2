package com.example.appcecasis.appcecasisv2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
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

public class MaterialesBusqueda extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ListView listView;
    private ListView listView2;

    ArrayList titulo = new ArrayList();
    ArrayList descrip = new ArrayList();
    ArrayList disponible= new ArrayList();

    ArrayList todo= new ArrayList();

    String busq[]={"gonzalo","gerardo","pablo","jose"} ;


    private EditText buscador;
    ArrayAdapter<String> adapter;

    TextView tvtitulof;
    TextView tvdescripcionf;
    TextView tvdisponiblef;

    String x;
    int y;

    String ip=ConfigConexión.conec;

    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null,cedula_usuariov=null;

    static String key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materiales_busqueda);
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
        navigationView.setNavigationItemSelectedListener(this);



        listView=(ListView)findViewById(R.id.listmateriales);


        buscador=(EditText)findViewById(R.id.busqmateriales);


        descargarmateriales();

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,titulo);
        listView.setAdapter(adapter);

        buscador.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.getFilter().filter(charSequence);



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {


                //x=adapter.getPosition();//adapter.getAutofillOptions()


                x=(disponible.get(position).toString());
                y= Integer.parseInt(x);


                //Object o = listView.getItemAtPosition(position);
                // Realiza lo que deseas, al recibir clic en el elemento de tu listView determinado por su posicion.
                //  Log.i("Click", "click en el elemento " + position + " de mi ListView");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MaterialesBusqueda.this);
                //   mBuilder.setTitle("Noticias");

                View mView = getLayoutInflater().inflate(R.layout.activity_materiales_busqueda2,null);


                //display toast with position of cardview in recyclerview list upon clic
                tvtitulof=(TextView)mView.findViewById(R.id.tvtitulo);
                tvdescripcionf=(TextView)mView.findViewById(R.id.tvdescrip);
                tvdisponiblef=(TextView)mView.findViewById(R.id.tvdisponible);

                //String urlfinal="http://172.16.24.223:8081/serv/img/"+disponible.get(position).toString();
                // Rect rect =new Rect (smartimageview.getLeft(), smartimageview.getTop(),smartimageview.getRight(),smartimageview.getBottom());
                // smartimageview.setImageUrl(urlfinal,rect);

                tvdisponiblef.setText("Disponibles: "+disponible.get(y-1).toString());
                tvtitulof.setText(titulo.get(y-1).toString());
                tvdescripcionf.setText(descrip.get(y-1).toString());
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


    private void descargarmateriales() {
        titulo.clear();
        descrip.clear();
        disponible.clear();

        final ProgressDialog progressDialog = new ProgressDialog(MaterialesBusqueda.this);
        progressDialog.setMessage("Cargar Datos....");
        progressDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://"+ip+"/serv/materiales.php?key="+key+"", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    progressDialog.dismiss();
                    try {
                        JSONArray jsonArray =new JSONArray(new String(responseBody));
                        for (int i=0;i<jsonArray.length();i++){
                            titulo.add(jsonArray.getJSONObject(i).getString("nombre_tipoArticulo"));
                            descrip.add(jsonArray.getJSONObject(i).getString("codigo_tipoArticulo"));
                            disponible.add(jsonArray.getJSONObject(i).getString("id_tipoArticulo"));

                            todo.add((jsonArray.getJSONObject(i).getString("id_tipoArticulo"))+","+(jsonArray.getJSONObject(i).getString("codigo_tipoArticulo"))+","+(jsonArray.getJSONObject(i).getString("id_tipoArticulo")));

                        }

                        //listView.setAdapter(new ImageAdapter(getApplicationContext()));
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
        TextView tvtitulo,tvdescripcion,tvdisponible;


        public ImageAdapter(Context applicationContext) {

            this.ctx=applicationContext;
            layoutinflater=(LayoutInflater)ctx.getSystemService(LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public int getCount() {
            return disponible.size();
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

            ViewGroup viewGroup=(ViewGroup)layoutinflater.inflate(R.layout.activity_materiales_busqueda2,null);


            tvtitulo=(TextView)viewGroup.findViewById(R.id.tvtitulo);
            tvdescripcion=(TextView)viewGroup.findViewById(R.id.tvdescrip);
            tvdisponible=(TextView)viewGroup.findViewById(R.id.tvdisponible);

            //String urlfinal="http://172.16.24.223:8081/serv/img/"+disponible.get(position).toString();
            // Rect rect =new Rect (smartimageview.getLeft(), smartimageview.getTop(),smartimageview.getRight(),smartimageview.getBottom());
            // smartimageview.setImageUrl(urlfinal,rect);

            tvdisponible.setText("Disponibles: " + disponible.get(position).toString());
            tvtitulo.setText(titulo.get(position).toString());
            tvdescripcion.setText(descrip.get(position).toString());


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
        getMenuInflater().inflate(R.menu.materiales_busqueda, menu);
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
            Intent intentReg = new Intent(MaterialesBusqueda.this, InicioNoticias.class);
            MaterialesBusqueda.this.startActivity(intentReg);
        } else if (id == R.id.nav_gallery) {
            Intent intentReg = new Intent(MaterialesBusqueda.this, LaboratorioInicio.class);
            MaterialesBusqueda.this.startActivity(intentReg);

        } else if (id == R.id.nav_slideshow) {

            Intent intentReg = new Intent(MaterialesBusqueda.this, MaterialesInicio.class);
            MaterialesBusqueda.this.startActivity(intentReg);

        } /*/else if (id == R.id.nav_manage) {

        } /*/else if (id == R.id.nav_share) {

            Intent intentReg = new Intent(MaterialesBusqueda.this, MantenimientoInicio.class);
            MaterialesBusqueda.this.startActivity(intentReg);


        } else if (id == R.id.nav_send) {

            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();

            //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().clear().commit();

            Intent intentReg = new Intent(MaterialesBusqueda.this,MainActivity.class);
            MaterialesBusqueda.this.startActivity(intentReg);

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
