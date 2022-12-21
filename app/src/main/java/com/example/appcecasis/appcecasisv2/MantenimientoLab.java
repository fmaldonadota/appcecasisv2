package com.example.appcecasis.appcecasisv2;

/**
 * Created by Fabricio Maldonado on 15/3/2018.
 */

public class MantenimientoLab {


    private  int id_lab;
    private int idlab;
    private String nombre_lab;
    private int maquinas_lab;
    private String r_maquinas;
    private String observacion_lab;

    public MantenimientoLab(){

    }

    public MantenimientoLab(int id_lab,int idlab,String nombre_lab,int maquinas_lab,String r_maquinas,String observacion_lab){
        this.id_lab = id_lab;
        this.idlab = idlab;
        this.nombre_lab = nombre_lab;
        this.maquinas_lab = maquinas_lab;
        this.r_maquinas = r_maquinas;
        this.observacion_lab = observacion_lab;
    }

    public void setid_lab (int id_lab){
        this.id_lab = id_lab;
    }
    public void setidlab (int idlab){
        this.idlab = idlab;
    }
    public void setnombre_lab (String nombre_lab){
        this.nombre_lab = nombre_lab;
    }
    public void setmaquinas_lab (int maquinas_lab){
        this.maquinas_lab = maquinas_lab;
    }
    public void setr_maquinas (String r_maquinas){
        this.r_maquinas = r_maquinas;
    }
    public void setobservacion_lab (String observacion_lab){
        this.observacion_lab = observacion_lab;
    }

    public String toString(){
        return nombre_lab;
    }
}
