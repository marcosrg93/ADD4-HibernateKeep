package com.izv.dam.keep.gestores;

/**
 * Created by marco on 22/02/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.izv.dam.keep.bd.Ayudante;
import com.izv.dam.keep.bd.Contrato;
import com.izv.dam.keep.pojo.Keep;

import java.util.ArrayList;
import java.util.List;

public class GestorBD {

    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorBD(Context c) {
        abd = new Ayudante(c);
    }

    public void open() {
        bd = abd.getWritableDatabase();
    }

    public void openRead() {
        bd = abd.getReadableDatabase();
    }

    public void close() {
        abd.close();
    }

    public long insert(Keep ag) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaKeep.CONTENIDO,
                ag.getContenido());
        valores.put(Contrato.TablaKeep.ESTADO,
                ag.getEstado());
        long id = bd.insert(Contrato.TablaKeep.TABLA,
                null, valores);
        return id;

        // Devuelve -1 si falla.
    }

    public int delete(Keep ag) {

        return deleteId(ag.getId());
    }

    public int deleteId(long id) {
        String condicion = Contrato.TablaKeep._ID + " = ?";
        String[] argumentos = { id + "" };
        int cuenta = bd.delete(
                Contrato.TablaKeep.TABLA, condicion, argumentos);
        return cuenta;
    }

    public List<Keep> select(String condicion) {
        List<Keep> la = new ArrayList<>();
        Cursor cursor = bd.query(Contrato.TablaKeep.TABLA, null,
                condicion, null, null, null, null);
        cursor.moveToFirst();
        Keep ag;
        while (!cursor.isAfterLast()) {
            ag = getRow(cursor);
            la.add(ag);
            cursor.moveToNext();
        }
        cursor.close();
        return la;
    }


    public  Keep getRow(Cursor c) {
        Keep r = new  Keep();
        r.setId(c.getLong(c.getColumnIndex(Contrato.TablaKeep._ID)));
        r.setContenido(c.getString(c.getColumnIndex(Contrato.TablaKeep.CONTENIDO)));

        if(c.getInt(c.getColumnIndex(Contrato.TablaKeep.ESTADO))==1){
            r.setEstado(true);
        }else {
            r.setEstado(false);
        }

        return r;
    }



    public void changeState(Keep ag){

        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaKeep.CONTENIDO, ag.getContenido());
        valores.put(Contrato.TablaKeep.ESTADO, 1);
        String condicion = Contrato.TablaKeep._ID + " = ?";
        String[] argumentos = { ag.getId() + "" };
        bd.update(Contrato.TablaKeep.TABLA, valores,
                condicion, argumentos);
    }




    public Cursor getCursor(){
        return getCursor(null, null);
    }



    public Cursor getCursor(String condicion, String[] parametros) {
        Cursor cursor = bd.query(
                Contrato.TablaKeep.TABLA, null, condicion, parametros, null,
                null, Contrato.TablaKeep.CONTENIDO+", "+Contrato.TablaKeep.ESTADO);
        return cursor;
    }

    public List<Keep> select() {
        return select(null,null);
    }
    public List<Keep> select(String condicion, String[] parametros) {
        List<Keep> la = new ArrayList<>();
        Cursor cursor = getCursor(condicion, parametros);
        Keep p;
        while (cursor.moveToNext()) {
            p = getRow(cursor);
            la.add(p);
        }
        cursor.close();
        return la;
    }
}
