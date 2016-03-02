package com.izv.dam.keep.lv;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.izv.dam.keep.R;
import com.izv.dam.keep.pojo.Keep;


/**
 * Created by marco on 22/11/2015.
 */
public class Adaptador extends CursorAdapter{

    public Adaptador(Context co, Cursor cu) {
        super(co, cu, true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.listitem_titular, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView  tvNombre = (TextView)view.findViewById(R.id.LblTitulo);
        TextView  tvEmail = (TextView) view.findViewById(R.id.LblSubTitulo);

        Keep p = new Keep();
        p.set(cursor);
        tvNombre.setText(p.getContenido());
        tvEmail.setText(p.getEstado()+"");
    }

}
