package com.izv.dam.keep.Actividades;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.izv.dam.keep.R;
import com.izv.dam.keep.gestores.GestorBD;
import com.izv.dam.keep.gestores.GestionWeb;
import com.izv.dam.keep.lv.Adaptador;
import com.izv.dam.keep.pojo.Keep;
import com.izv.dam.keep.pojo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {



    private Usuario user = new Usuario();
    private List<Keep> listaNotas;
    private GestionWeb gestionWeb = new GestionWeb(this);

    private boolean online = false;
    private GestorBD gestorBD = new GestorBD(this);
    private Cursor cursor;
    private List<Keep> listaNotasBD;
    private Adaptador ad;
    private ListView lv;


    private ArrayList<Keep> datos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    public void init() {
        //Abrimos los gestores de cada clase
        gestorBD = new GestorBD(this);
        gestorBD.open();

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String us = b.getString("user");
            String ps = b.getString("pass");
            user.setEmail(us);
            user.setPass(ps);
            Log.v("USUARIO", user.toString());
        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nota(view);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        cursor = gestorBD.getCursor();

        lv = (ListView)findViewById(R.id.lv);

        ad = new Adaptador(this, cursor);

        registerForContextMenu(lv);

        lv.setAdapter(ad);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder b = new AlertDialog.Builder(Principal.this);
                b.setMessage("¿Borrar?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Cursor cursor = (Cursor) lv.getItemAtPosition(position);
                                final Keep k = gestorBD.getRow(cursor);
                                gestorBD.delete(k);
                                ad.changeCursor(gestorBD.getCursor());
                                tostada("Receta borrada");

                                Runnable r = new Runnable() {
                                    @Override
                                    public void run() {
                                        gestionWeb.deleteKeep(k, user);
                                    }
                                };
                                Thread t = new Thread(r);
                                t.start();
                                gestorBD.delete(k);
                                //listaNotas.remove(recView.getChildAdapterPosition(v));

                                ad.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return false;
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {

                Cursor cursor = (Cursor) lv.getItemAtPosition(position);
                Keep k = gestorBD.getRow(cursor);
                editNota(v,k);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sinc) {
            TraerDatos syncAsync = new TraerDatos();
            syncAsync.execute();

            return true;
        }
        if (id == R.id.action_subir) {

            SubirDatos ak = new SubirDatos();
            ak.execute();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gestorBD.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gestorBD.open();
    }



    private void tostada(String i){
        Toast.makeText(this, i, Toast.LENGTH_LONG).show();
    }


    public void nota(View v) {
        Intent i = new Intent(this, Nota.class);
        Bundle b = new Bundle();
        Log.v("ID", cursor.getCount() + 1 + "");
        b.putLong("id", cursor.getCount() + 1);
        b.putString("user", user.getEmail());
        b.putString("pass", user.getPass());
        b.putBoolean("edit", false);
        //b.putStringArrayList("lista", (ArrayList) listaNotasBD);
        i.putExtras(b);
        startActivityForResult(i,0);
    }


    public void editNota(View v, Keep keep) {
        Intent i = new Intent(this, Nota.class);
        Bundle b = new Bundle();
        Log.v("ID", cursor.getCount() + 1 + "");
        b.putLong("id", cursor.getCount() + 1);
        b.putLong("idold", keep.getId());
        b.putString("user", user.getEmail());
        b.putString("pass", user.getPass());
        b.putBoolean("edit", true);
        b.putParcelable("keep", keep);
        i.putExtras(b);
        startActivityForResult(i,1);
    }

    private class SubirDatos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            listaNotas = gestorBD.select();
            List<Keep> notas = new ArrayList<>();
            for (Keep k : listaNotas) {
                if (k.getEstado() == false) {
                    k.getContenido();
                    notas.add(k);
                    gestionWeb.uploadKeeps(listaNotas, user);
                    Log.v("LISTA NOTAS", listaNotas.toString());
                }

            }

            //pasarBD();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tostada("Subida Finalizada");
            ad.changeCursor(gestorBD.getCursor());


        }

    }

    private class TraerDatos extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            listaNotasBD = gestionWeb.getNotas(user);
            List<Keep> updateIdAND = new ArrayList<>();
            for (Keep k : listaNotasBD) {

                int id=0;

               if (k.getId() == 0) {
                    id = (int) gestorBD.insert(k);
                    Log.v("PASARBD", k.toString());

                }
                k.setId(id);

                updateIdAND.add(k);
                gestionWeb.updateIdKeep(updateIdAND,user);
                Log.v("UPDATEBD", k.toString());


            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            ad.changeCursor(gestorBD.getCursor());
            ad.notifyDataSetChanged();
            tostada("Sincronización Finalizada");
        }

    }








}
