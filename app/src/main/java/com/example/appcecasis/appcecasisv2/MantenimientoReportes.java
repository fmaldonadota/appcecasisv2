package com.example.appcecasis.appcecasisv2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MantenimientoReportes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private AsyncHttpClient cliente,ins;
    private Spinner splab;

    Button insertar,camarab;

    String ip=ConfigConexión.conec;

    ImageView imagen;

    private final String CARPETA_RAIZ="misimagenes";
    private final String RUTA_IMAGE=CARPETA_RAIZ+"misFotos";

    String path;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null,cedula_usuariov=null;
    String cedula_usuario,s;


    Spinner maquina;
    MultiAutoCompleteTextView descripcion;


    static String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_reportes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        TextView correo = (TextView) hView.findViewById(R.id.nombreUsuario);
        TextView contra = (TextView) hView.findViewById(R.id.rolUsuario);

        //Bundle bundle=getIntent().getExtras();
        //String datos = bundle.getString("cod");

        SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
        s = prefernece.getString("nombre",nombrecompleto);

        // Log.d("nombre usuario", s+"");
        String d = prefernece.getString("nombrerol",nombre_rol);
        //String datos2 = bundle.getString("contra");
        key = prefernece.getString("key",key);

        correo.setText(d);
        contra.setText(s);

        cedula_usuario = prefernece.getString("cedula_usuario",cedula_usuariov);




        maquina=(Spinner) findViewById(R.id.incidentemaq);
        descripcion=(MultiAutoCompleteTextView) findViewById(R.id.descripmaq);

        cliente= new AsyncHttpClient();
        splab=(Spinner) findViewById(R.id.spinner2);
        llenarspinner();


        insertar=(Button) findViewById(R.id.button5);
        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intentReg = new Intent(MantenimientoInicio.this, MantenimientoAsignacion.class);
                //MantenimientoInicio.this.startActivity(intentReg);



                //Object o = listView.getItemAtPosition(position);
                // Realiza lo que deseas, al recibir clic en el elemento de tu listView determinado por su posicion.
                //  Log.i("Click", "click en el elemento " + position + " de mi ListView");
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MantenimientoReportes.this);
                mBuilder.setTitle("Registro de Insidentes");

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.Registro, new DialogInterface.OnClickListener() {
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

                        insertar();

                        NotificationCompat.Builder mBuilder;
                        NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);

                        int icono = R.mipmap.ic_launcher;
                        Intent intent = new Intent(MantenimientoReportes.this, LaboratorioDispoNotificacion.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(MantenimientoReportes.this, 0,intent, 0);


                        mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                .setContentIntent(pendingIntent)
                                .setSmallIcon(icono)
                                .setContentTitle("Registro de Incidente")
                                .setContentText(""+s+" registro un Incidente")
                                .setVibrate(new long[] {100, 250, 100, 500})
                                .setAutoCancel(true);

                        mNotifyMgr.notify(1, mBuilder.build());

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


        imagen=(ImageView) findViewById(R.id.imagen);

        camarab=(Button) findViewById(R.id.camara);
        camarab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                llamarintent();

            }
        });

    }


    private void llamarintent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen.setImageBitmap(imageBitmap);
        }
    }

    public void tomarFotografia(){
        File fileImagen=new File(Environment.getExternalStorageDirectory(),RUTA_IMAGE);
        boolean iscreada=fileImagen.exists();
        String nombreImagen="";

        if(iscreada==false){
            iscreada=fileImagen.mkdirs();
        }
        if(iscreada==true){
            String nombre=(System.currentTimeMillis()/100)+".jpg";
        }

        path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGE+File.separator+nombreImagen;

        File imagen=new File(path);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        startActivityForResult(intent,20);
    }

/*/

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK){


            MediaScannerConnection.scanFile(this,new String[]{path},null,
                    new MediaScannerConnection.OnScanCompletedListener(){

                        public void onScanCompleted(String s,Uri url){
                            Log.i("Ruta de almacenaminet","Patch:"+path);
                        }
                    });

            Bitmap bitmap= BitmapFactory.decodeFile(path);
            imagen.setImageBitmap(bitmap);


        }
    }/*/

    private void insertar() {


        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("laboratorio",splab.getSelectedItem().toString());
        params.put("maquina",maquina.getSelectedItem());
        params.put("descripcion",descripcion.getText().toString());
        params.put("foto", "imagen.setImageBitmap");
        params.put("cedulaLogin",cedula_usuario);
        params.put("estado","PENDIENTE");

        if(splab.getSelectedItem().toString().equals("LABORATORIO 1")||splab.getSelectedItem().toString().equals("LABORATORIO 3")||splab.getSelectedItem().toString().equals("LABORATORIO 4")||splab.getSelectedItem().toString().equals("LABORATORIO 5")||splab.getSelectedItem().toString().equals("LABORATORIO 11")||splab.getSelectedItem().toString().equals("LABORATORIO 2")||splab.getSelectedItem().toString().equals("LABORATORIO 15")){
            params.put("asignacion","1717171717");
        }else{
            params.put("asignacion","1746234987");
        }
        params.put("key",key);

        client.post("http://"+ip+"/serv/insertaincidentes.php", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                Toast.makeText(MantenimientoReportes.this, responseString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Toast.makeText(MantenimientoReportes.this, responseString, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void llenarspinner(){
        String url="http://"+ip+"/serv/lab.php?key="+key+"";
        cliente.post(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if(statusCode==200){
                    CargarSpinner(new String(responseBody));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void CargarSpinner(String respuesta){
        ArrayList<MantenimientoLab> lista = new ArrayList<MantenimientoLab>();
        try{

            JSONArray jsonArreglo = new JSONArray(respuesta);
            for(int i=0;i<jsonArreglo.length();i++){

                MantenimientoLab m = new MantenimientoLab();
                m.setnombre_lab(jsonArreglo.getJSONObject(i).getString("nombre_lab"));
                lista.add(m);

            }

            ArrayAdapter<MantenimientoLab> a = new ArrayAdapter<MantenimientoLab>(this,android.R.layout.simple_dropdown_item_1line,lista);
            splab.setAdapter(a);

        }catch(Exception e){
            e.printStackTrace();
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
        getMenuInflater().inflate(R.menu.mantenimiento_reportes, menu);
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

            Intent intentReg = new Intent(MantenimientoReportes.this, InicioNoticias.class);
            MantenimientoReportes.this.startActivity(intentReg);

        } else if (id == R.id.nav_gallery) {

            Intent intentReg = new Intent(MantenimientoReportes.this, LaboratorioInicio.class);
            MantenimientoReportes.this.startActivity(intentReg);

        } else if (id == R.id.nav_slideshow) {

            Intent intentReg = new Intent(MantenimientoReportes.this, MaterialesInicio.class);
            MantenimientoReportes.this.startActivity(intentReg);

        } /*/else if (id == R.id.nav_manage) {

        }/*/ else if (id == R.id.nav_share) {

            Intent intentReg = new Intent(MantenimientoReportes.this, MantenimientoInicio.class);
            MantenimientoReportes.this.startActivity(intentReg);

        } else if (id == R.id.nav_send) {

            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();

            //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().clear().commit();

            Intent intentReg = new Intent(MantenimientoReportes.this,MainActivity.class);
            MantenimientoReportes.this.startActivity(intentReg);

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
