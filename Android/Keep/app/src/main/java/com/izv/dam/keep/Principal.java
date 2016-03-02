package com.izv.dam.keep;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.izv.dam.keep.adapter.ClaseAdaptador;
import com.izv.dam.keep.gestion.GestionKeep;
import com.izv.dam.keep.pojo.Keep;
import com.izv.dam.keep.pojo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    private Usuario user;
    private List<Keep> listaNotas;
    private GestionKeep gk;
    private ClaseAdaptador cl;
    private boolean online =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        user = getIntent().getParcelableExtra("usuario");
        if(user != null){
            Toast.makeText(this, user.getEmail(), Toast.LENGTH_LONG).show();
            online = true;
        }

        init();
    }

    public  void init(){
        listaNotas = new ArrayList<>();

        for (int i = 0; i < 10 ; i++) {
            if( i % 2 == 0) {
                listaNotas.add(new Keep(i, "Mensaje " + i, false));
            }else {
                listaNotas.add(new Keep(i, "Mensaje " + i, true));
            }
        }

        final ListView lv = (ListView) findViewById(R.id.listView);

        cl = new ClaseAdaptador(this, R.layout.item, listaNotas);
        lv.setAdapter(cl);
        lv.setTag(listaNotas);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(Principal.this, "Posicion: " + position, Toast.LENGTH_LONG).show();

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
        if (id == R.id.action_add) {
            listaNotas.add(new Keep(listaNotas.size(), "Mensaje " + listaNotas.size(), false));


            cl.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
