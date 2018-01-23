package com.ecalasans.vitalis;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.SharedPreferences.*;

/**
 * Created by ericcalasans on 21/11/16.
 */

public class fragBHI extends Fragment {

    View view;

    Infundido i;

    String data;

    int pac=0, tec=0, oral=0, sng=0;

    float drogasV=0, soro=0, medic=0, sangue=0, outros=0, npt=0;

    TextView iPac, iDataHora;

    EditText iDrogasV, iSoro, iMedic, iOral, iSNG, iSangue, iOutros, iNPT;

    SharedPreferences shPref;

    Context context;

    public fragBHI() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_bhi, null);
        iPac = (TextView) view.findViewById(R.id.txtIPac);
        iDataHora = (TextView) view.findViewById(R.id.txtIDataHora);
        iDrogasV = (EditText) view.findViewById(R.id.etIDrogas);
        iSoro = (EditText) view.findViewById(R.id.etIHV);
        iMedic = (EditText) view.findViewById(R.id.etIMed);
        iOral = (EditText) view.findViewById(R.id.etIOral);
        iSNG = (EditText) view.findViewById(R.id.etISNG);
        iSangue = (EditText) view.findViewById(R.id.etISangue);
        iOutros = (EditText) view.findViewById(R.id.etIOutros);
        iNPT = (EditText) view.findViewById(R.id.etINPT);

        iPac.setText(getArguments().getString("letreiro"));

        SimpleDateFormat dh = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dataHora = dh.format(Calendar.getInstance().getTime());
        iDataHora.setText(dataHora);

        context = getActivity();

        return(view);
    }


    @Override
    public void onResume() {
        super.onResume();


        iOutros.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b) {
                    AlertDialog.Builder consAlerta = new AlertDialog.Builder(context);
                    consAlerta.setMessage("Confirma os dados?");
                    consAlerta.setCancelable(true);
                    consAlerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            infundidoSharedPref();
                        }
                    });

                    consAlerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog fecha = consAlerta.create();
                    fecha.show();
                }
            }
        });

    }

    public void infundidoSharedPref(){
        //Seta as variáveis que serão salvas no XML
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data = df.format(Calendar.getInstance().getTime());

        pac = getArguments().getInt("idPac");
        tec = getArguments().getInt("idTec");

        oral = Integer.parseInt(iOral.getText().toString());
        sng = Integer.parseInt(iSNG.getText().toString());

        soro = Float.parseFloat(iSoro.getText().toString());
        drogasV = Float.parseFloat(iDrogasV.getText().toString());
        medic = Float.parseFloat(iMedic.getText().toString());
        sangue = Float.parseFloat(iSangue.getText().toString());
        outros = Float.parseFloat(iOutros.getText().toString());
        npt = Float.parseFloat(iNPT.getText().toString());

        //Escreve no XML
        shPref = context.getSharedPreferences("infundido", context.MODE_PRIVATE);
        Editor ed = shPref.edit();

        ed.putInt("iPac", pac);
        ed.putFloat("iDrogas", drogasV);
        ed.putInt("iOral", oral);
        ed.putInt("iSng", sng);
        ed.putFloat("iSoro", soro);
        ed.putFloat("iMedic", medic);
        ed.putFloat("iNpt", npt);
        ed.putFloat("iSangue", sangue);
        ed.putFloat("iOutros", outros);
        ed.putInt("iTec", tec);
        ed.putString("iData", data);

        ed.commit();

    }
}
