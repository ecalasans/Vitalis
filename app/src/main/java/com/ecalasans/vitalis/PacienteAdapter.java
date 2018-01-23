package com.ecalasans.vitalis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ericcalasans on 12/11/16.
 */

public class PacienteAdapter extends BaseAdapter {
    Context contexto;
    ArrayList<Paciente> pac;

    public PacienteAdapter(Context contexto, ArrayList<Paciente> pac) {
        this.contexto = contexto;
        this.pac = pac;
    }

    @Override
    public int getCount() {
        return pac.size();
    }

    @Override
    public Object getItem(int i) {
        return pac.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Paciente p = pac.get(i);

        LayoutInflater infl = (LayoutInflater)
                contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = infl.inflate(R.layout.lista_leitos,null);

        TextView pac = (TextView) layout.findViewById(R.id.itPac);
        TextView leito = (TextView) layout.findViewById(R.id.itLeito);

        pac.setText(p.getNome());
        leito.setText("Leito "+p.getLeito());

        return layout;
    }
}
