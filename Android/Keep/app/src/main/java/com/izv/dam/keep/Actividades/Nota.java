package com.izv.dam.keep.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.izv.dam.keep.R;
import com.izv.dam.keep.gestores.GestorBD;
import com.izv.dam.keep.gestores.GestionWeb;
import com.izv.dam.keep.pojo.Keep;
import com.izv.dam.keep.pojo.Usuario;

import java.util.List;

public class Nota extends AppCompatActivity {

    private EditText et;
    private boolean edit;
    private FloatingActionButton fab;


    private GestorBD gestorBD;
    private Usuario user = new Usuario();
    private GestionWeb gestionWeb = new GestionWeb(this);
    private List<Keep> listaNotas;
    private long id, idold;
    private  Keep notaB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        Bundle b = getIntent().getExtras();
        gestorBD = new GestorBD(this);
        gestorBD.open();

        et = (EditText) findViewById(R.id.etNota);


        if (b != null) {
            id = b.getLong("id");
            Log.v("Intent", id + "");
            String us = b.getString("user");
            String ps = b.getString("pass");
            user.setEmail(us);
            user.setPass(ps);
            Log.v("USER NOTA", user.toString());
            edit = b.getBoolean("edit");

            Log.v("NOTA BOOL", edit + "");

            if (b.getParcelable("keep") != null) {
                idold = b.getLong("idold");
                Log.v("Intent IDOLD", idold + "");
                notaB = b.getParcelable("keep");
                notaB.setId(idold);
                Log.v("NOTA Keep", notaB.toString());
                if (notaB != null) {
                    et.setText(notaB.getContenido().toString());
                    //listaNotas = (List) b.getParcelableArrayList("lista");
                }
            }
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et.getText().toString().isEmpty()) {
                    Log.v("NOTA  FAB BOOL", edit + "");
                    final Keep k = new Keep(id, et.getText().toString(), false, 0);
                    Log.v("NOTA", k.toString());

                    if (!edit) {
                        Log.v("ADD NOTAAAAAA", edit + "");
                        gestorBD.insert(k);

                    } else  if (edit) {
                        Log.v("UPDATE NOTAAAAAA", edit + "");
                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                gestionWeb.deleteKeep(notaB, user);
                                Log.v("DELETE NOTAAAAAA", notaB.toString());
                            }
                        };
                        Thread t = new Thread(r);
                        t.start();
                        gestorBD.delete(notaB);
                        k.setId(id);
                        gestorBD.insert(k);

                    }
                    Intent i = new Intent(getApplicationContext(), Principal.class);
                    Bundle b = new Bundle();
                    b.putString("user", user.getEmail());
                    b.putString("pass", user.getPass());
                    i.putExtras(b);
                    startActivity(i);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

        } else if (requestCode == 1) {
            edit = true;
            Keep k = data.getExtras().getParcelable("keep");
            et.setText(k.getContenido());
        }
    }
}
