package com.izv.dam.keep.gestores;

/**
 * Created by marco on 22/02/2016.
 */


import android.content.Context;
import android.util.Log;

import com.izv.dam.keep.pojo.Keep;
import com.izv.dam.keep.pojo.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GestionWeb {

    private GestorBD gestorBD;
    private String urlDestino = "http://192.168.1.3:8080/Keep/go";
    //private String urlDestino = "http://192.168.1.107:8080/Keep/go";

    public GestionWeb(Context context) {
        this.gestorBD = new GestorBD(context);
    }

    public GestionWeb() {
    }

    public List<Keep> getNotas(Usuario u) {
        List<Keep> keeps = new ArrayList<>();
        URL url = null;
        BufferedReader in = null;
        String res = "";
        String login;
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
            String destino = urlDestino + "?tabla=keep&op=read&login=" + login + "&origen=android&accion=";
            url = new URL(destino);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String linea;
            while ((linea = in.readLine()) != null) {
                res += linea;
            }
            in.close();
            JSONObject obj = new JSONObject(res);
            Log.v("Objeto JSON", obj.get("r") + "");
            JSONArray array = (JSONArray) obj.get("r");

            for (int i = 0; i < array.length(); i++) {
                Log.v("Elemento "+i, "" + array.get(i));
                JSONObject o = (JSONObject) array.get(i);

                Keep keep;
                if(o.getString("est").contains("inestable")){
                     keep = new Keep(o.getInt("ida"), o.getString("cont"), false,o.getInt("id"));
                }else {
                     keep = new Keep(o.getInt("ida"), o.getString("cont"), true,o.getInt("id"));
                }

                keeps.add(keep);
                Log.v("ARRAY", "" + keep.toString());
            }

        } catch (MalformedURLException e) {
            Log.v("xxx2", e.toString());
        } catch (IOException e) {
            Log.v("xxx3", e.toString());
        } catch (JSONException e) {
            Log.v("xxx4", e.toString());
        }
        return keeps;
    }

    public long getNextAndroidId(List<Keep> l) {
        long next = -1;
        for (Keep k : l) {
            if (k.getId() > next) {
                next = k.getId();
            }
        }
        return next+1;
    }

    public List<Keep> uploadKeeps(List<Keep> l, Usuario u) {
        gestorBD.open();
        List<Keep> d= new ArrayList<>();
        URL url = null;
        BufferedReader in = null;
        String res = "";
        String login;
        List<Keep> uKeep= getNotas(u);
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
            for (Keep k : l) {
                if(!k.getEstado()) {
                    if(uKeep.contains(k)){
                        String destinor = urlDestino + "?tabla=keep&op=delete&login=" + login + "&origen=android&idAndroid=" + k.getId() + "&accion="+ "&contenido=" + k.getContenido();
                        url = new URL(destinor);
                        in = new BufferedReader(new InputStreamReader(url.openStream()));
                    }
                    String destino = urlDestino + "?tabla=keep&op=create&login=" + login + "&origen=android&idAndroid=" + k.getId() + "&accion="+ "&contenido=" + k.getContenido() ;
                    url = new URL(destino);
                    in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String linea;
                    while ((linea = in.readLine()) != null) {
                        res += linea;
                    }
                    in.close();
                    k.setEstado(true);
                    gestorBD.changeState(k);
                }

                d.add(k);

            }
            gestorBD.close();
            return d;

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }

        gestorBD.close();
        return null;

    }


    public void updateIdKeep(List<Keep> l, Usuario u) {

        gestorBD.open();
        URL url = null;
        BufferedReader in = null;
        String res = "";
        String login;

        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
            for (Keep k : l) {
                    String destino = urlDestino + "?tabla=keep&op=update&login=" + login + "&origen=android&id="+k.getIdWeb()+"&idAndroid=" + k.getId() + "&accion=" ;
                    Log.v("UPDATE URL",destino);
                    url = new URL(destino);
                    in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String linea;
                    while ((linea = in.readLine()) != null) {
                        res += linea;
                    }
                    in.close();
                    k.setEstado(true);
                    gestorBD.changeState(k);

            }
            gestorBD.close();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }

        gestorBD.close();

    }

    public void deleteKeep(Keep k, Usuario u){
        URL url = null;
        BufferedReader in = null;
        String res = "";
        String login;
        try {
            login = URLEncoder.encode(u.getEmail(), "UTF-8");
            String destinor = urlDestino + "?tabla=keep&op=delete&login=" + login + "&origen=android&idAndroid=" + k.getId() +"&accion="+ "&contenido=" + k.getContenido() ;

            url = new URL(destinor);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }

    }
}
