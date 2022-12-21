package com.example.appcecasis.appcecasisv2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.client.HttpClient;

public class LaboratoriosReserva extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button bfecha,bhora,bverif,filtros;
    EditText dfecha,dhora;

    private int dia,mes,ano,hora,minuto;

    DateFormat formatDateTime = DateFormat.getDateTimeInstance();
    Calendar dateTime = Calendar.getInstance();


    //multiple choice list dialog

    Button mOrder;
    TextView mItemSelected;
    String[] listItems;
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    AsyncHttpClient cliente;
    Spinner spinner,horas,numeroLaboratorios;


    String fechae;
    String horae;
    String numeroMaquinase;
    String filtroe;


    String valor;

    private static final String STRING_PREFERENCE = "appcecasis.MainActivity";
    private static final String PREFERENCE_ESTADO_BUTTON_SECION = "estado.button.sesion";

    String nombrecompleto = null,nombre_rol=null;


    boolean validacion =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios_reserva);
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


        bfecha= (Button) findViewById(R.id.button6);
        dfecha= (EditText) findViewById(R.id.editText7);

        bhora= (Button) findViewById(R.id.button7);
        //dhora= (EditText) findViewById(R.id.editText8);
       // dhora.setEnabled(false);

        bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             fecha();

            }
        });

        /*/
        bhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // hora();

            }
        });


        bfecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDate();
            }
        });

        bhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTime();
            }
        });

/*/



        bverif=(Button) findViewById(R.id.verificar);

        bverif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fechae = dfecha.getText().toString().trim();
                horae = horas.getSelectedItem().toString();
                numeroMaquinase = numeroLaboratorios.getSelectedItem().toString();
                filtroe = mItemSelected.getText().toString().trim();



                Calendar calendar = Calendar.getInstance();
                int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

Log.d("hora",fechae+"");

                if(fechae.equals("2/7/2018")){


                    if(hourOfDay>=7 && horae.equals("07H00-09H00")){
                        Toast.makeText(LaboratoriosReserva.this,"Ya paso la hora seleccionada", Toast.LENGTH_SHORT).show();
                        validacion=false;
                    }
                    if(hourOfDay>=9 && horae.equals("09H00-11H00")){
                        Toast.makeText(LaboratoriosReserva.this,"Ya paso la hora seleccionada", Toast.LENGTH_SHORT).show();
                        validacion=false;
                    }
                    if(hourOfDay>=11 && horae.equals("11H00-13H00")){
                        Toast.makeText(LaboratoriosReserva.this,"Ya paso la hora seleccionada", Toast.LENGTH_SHORT).show();
                        validacion=false;
                    }
                    if(hourOfDay>=14 && horae.equals("14H00-16H00")){
                        Toast.makeText(LaboratoriosReserva.this,"Ya paso la hora seleccionada", Toast.LENGTH_SHORT).show();
                        validacion=false;
                    }


                    validacion=true;

                }


                    if (!fechae.isEmpty() && !horae.isEmpty() && !numeroMaquinase.isEmpty() && !filtroe.isEmpty() &&validacion==true) {
                        //       Log.d("fecha", filtroe+"");

                        Intent intentReg = new Intent(LaboratoriosReserva.this, LaboratorioDisponible.class);
                        intentReg.putExtra("fechae", fechae);
                        intentReg.putExtra("horae", horae);
                        intentReg.putExtra("numeroMaquinase", numeroMaquinase);
                        intentReg.putExtra("filtroe", filtroe);
                        LaboratoriosReserva.this.startActivity(intentReg);

                    } else {

                        Toast.makeText(LaboratoriosReserva.this, "Completar los datos Por favor", Toast.LENGTH_SHORT).show();
                    }









            }
        });


        mOrder = (Button) findViewById(R.id.btnOrder);
        mItemSelected = (TextView) findViewById(R.id.tvItemSelected);





        listItems = getResources().getStringArray(R.array.shopping_item);
        checkedItems = new boolean[listItems.length];





        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LaboratoriosReserva.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }


                        if(isChecked){


                            if(position==0){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("Eclipse Indigo")
                                        .setItems(R.array.version_eclipse, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }
                            if(position==1){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("Microsoft Office")
                                        .setItems(R.array.version_office, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                              // valor = (version_office(which)+"");


                                               // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }
                            if(position==2){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("Microsoft Visual Studio")
                                        .setItems(R.array.version_visual, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }
                            if(position==3){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("AutoCAD")
                                        .setItems(R.array.version_autocad, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }
                            if(position==4){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("Netbeans")
                                        .setItems(R.array.version_netbeans, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }
                            if(position==5){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("Matlab")
                                        .setItems(R.array.version_matlab, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }
                            if(position==6){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("Pselnt")
                                        .setItems(R.array.version_presint, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }
                            if(position==7){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("Centos")
                                        .setItems(R.array.version_centos, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }

                            if(position==8){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("PostgreSQL")
                                        .setItems(R.array.version_postgres, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }

                            if(position==9){
                                AlertDialog.Builder builders = new AlertDialog.Builder(LaboratoriosReserva.this);
                                builders.setTitle("MySQL Server")
                                        .setItems(R.array.version_mysql, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                                // valor = (version_office(which)+"");


                                                // Log.d("array",valor+"");


                                            }
                                        });

                                AlertDialog mDialogs = builders.create();
                                mDialogs.show();
                            }






                            mUserItems.add(position);



                        }else{
                            mUserItems.remove((Integer.valueOf(position)));
                        }
                    }
                });

                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {


                        String item = "";
                        for (int i = 0; i < mUserItems.size(); i++) {
                            item = item + listItems[mUserItems.get(i)];
                            if (i != mUserItems.size() - 1) {
                                item = item +", ";
                            }
                        }
                        mItemSelected.setText(item);
                    }
                });

                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mUserItems.clear();
                            mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        horas = (Spinner) findViewById(R.id.horas);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.horas , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        horas.setAdapter(spinner_adapter);

        numeroLaboratorios = (Spinner) findViewById(R.id.spinner);

        // spiiner automatico

/*/

        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
/*/

    }

    public void fecha(){

    dia = dateTime.get(Calendar.DAY_OF_MONTH);
    mes = dateTime.get(Calendar.MONTH);
    ano = dateTime.get(Calendar.YEAR);


    DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            dfecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
        }
    },dateTime.get(Calendar.YEAR),mes, dateTime.get(Calendar.DAY_OF_MONTH));
    datePickerDialog.getDatePicker().setMinDate(dateTime.getTimeInMillis());// TODO: used to hide previous date,month and year
    //dateTime.add(Calendar.YEAR, 0);
    // datePickerDialog.getDatePicker().setMaxDate(dateTime.getTimeInMillis());// TODO: used to hide future date,month and year
    dateTime.set(dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH), dateTime.get(Calendar.DAY_OF_MONTH)+2);
    datePickerDialog.getDatePicker().setMaxDate(dateTime.getTimeInMillis());

    datePickerDialog.show();

}
    final boolean[] mIgnoreEvent = {false};
public void hora(){
    final Calendar c= Calendar.getInstance();
    hora=c.get(Calendar.HOUR_OF_DAY);
    minuto=c.get(Calendar.MINUTE);


    final int TIME_PICKER_INTERVAL = 60;

    TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


            if (mIgnoreEvent[0])
                return;
            if (minute%TIME_PICKER_INTERVAL!=0){

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(LaboratoriosReserva.this);

                mBuilder.setTitle("Error de Fecha");
                mBuilder.setIcon(R.drawable.ic_menu_gallery);
                mBuilder.setMessage("Fecha Mal puesta");

                mBuilder.setNegativeButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

                int minuteFloor=minute-(minute%TIME_PICKER_INTERVAL);
                minute=minuteFloor + (minute==minuteFloor+1 ? TIME_PICKER_INTERVAL : 0);
                if (minute==60)
                    minute=0;
                mIgnoreEvent[0] =true;
                view.setCurrentMinute(minute);

                mIgnoreEvent[0] =false;
            }

            dhora.setText(hourOfDay+":"+minute);

        }
    },hora,minuto,false);
    // datePickerDialog.getDatePicker().setMinDate(dateTime.getTimeInMillis());// TODO: used to hide previous date,month and year
    // timePickerDialog.getTimePicker().setCurrentMinute(c.get(Calendar.MINUTE)/5);
    //  datePickerDialog.getDatePicker().setMaxDate(dateTime.getTimeInMillis());

    //TimePickerDialog.getTimePicker().timePickerDialog.setMinTime(10, 0, 0); // MIN: hours, minute, secconds

    timePickerDialog.show();

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
        getMenuInflater().inflate(R.menu.laboratorios_reserva, menu);
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

            Intent intentReg = new Intent(LaboratoriosReserva.this, InicioNoticias.class);
            LaboratoriosReserva.this.startActivity(intentReg);

        } else if (id == R.id.nav_gallery) {

            Intent intentReg = new Intent(LaboratoriosReserva.this, LaboratorioInicio.class);
            LaboratoriosReserva.this.startActivity(intentReg);

        } else if (id == R.id.nav_slideshow) {

            Intent intentReg = new Intent(LaboratoriosReserva.this, MaterialesInicio.class);
            LaboratoriosReserva.this.startActivity(intentReg);

        }
        //else if (id == R.id.nav_manage) {

        //}
        else if (id == R.id.nav_share) {

            Intent intentReg = new Intent(LaboratoriosReserva.this, MantenimientoInicio.class);
            LaboratoriosReserva.this.startActivity(intentReg);

        } else if (id == R.id.nav_send) {

            SharedPreferences prefernece= getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().putBoolean(PREFERENCE_ESTADO_BUTTON_SECION,false).apply();

            //SharedPreferences settings = getSharedPreferences(STRING_PREFERENCE,MODE_PRIVATE);
            prefernece.edit().clear().commit();

            Intent intentReg = new Intent(LaboratoriosReserva.this,MainActivity.class);
            LaboratoriosReserva.this.startActivity(intentReg);

            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
