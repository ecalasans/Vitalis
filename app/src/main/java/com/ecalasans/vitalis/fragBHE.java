package com.ecalasans.vitalis;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ericcalasans on 21/11/16.
 */

public class fragBHE extends Fragment {

    View view;

    Eliminado e;

    TextView txtElPac, txtEDataHora;

    EditText etEDiurese, etESNG, etEVomitos, etESangue, etEDreno, etEOutros;

    RatingBar rtFezes;

    int pac=0, tec=0 ;

    float diurese = 0, dreno = 0, outros = 0, fezes=0, vomitos=0, sng=0;

    float sangue = 0;

    String data;

    SharedPreferences shPref;

    Context context;

    public fragBHE() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Localizador", "Está no onCreateView");
        view = inflater.inflate(R.layout.layout_bhe,null);

        txtElPac = (TextView) view.findViewById(R.id.txtElPac);
        txtEDataHora = (TextView) view.findViewById(R.id.txtEDataHora);

        etEDiurese = (EditText) view.findViewById(R.id.etEDiurese);
        etESNG = (EditText) view.findViewById(R.id.etESNG);
        etEVomitos = (EditText) view.findViewById(R.id.etEVomitos);
        etESangue = (EditText) view.findViewById(R.id.etESangue);
        etEDreno = (EditText) view.findViewById(R.id.etEDreno);
        etEOutros = (EditText) view.findViewById(R.id.etEOutros);

        rtFezes = (RatingBar) view.findViewById(R.id.rtEFezes);

        txtElPac.setText(getArguments().getString("letreiro"));

        SimpleDateFormat dh = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        String dataHora = dh.format(Calendar.getInstance().getTime());
        txtEDataHora.setText(dataHora);

        context = getActivity();

        return(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("Localizador", "Está no onResume");


        etEOutros.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b) {
                    AlertDialog.Builder consAlerta = new AlertDialog.Builder(context);
                    consAlerta.setMessage("Confirma os dados?");
                    consAlerta.setCancelable(true);
                    consAlerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            eliminadoSharedPref();
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

    public void eliminadoSharedPref(){
        //Seta as variáveis que serão escritas no XML
        pac = getArguments().getInt("idPac");
        tec = getArguments().getInt("idTec");

        diurese = Float.parseFloat(etEDiurese.getText().toString());
        dreno = Float.parseFloat(etEDreno.getText().toString());
        outros = Float.parseFloat(etEOutros.getText().toString());

        fezes = rtFezes.getRating();

        vomitos = Float.parseFloat(etEVomitos.getText().toString());
        sangue = Float.parseFloat(etESangue.getText().toString());
        sng = Float.parseFloat(etESNG.getText().toString());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data = df.format(Calendar.getInstance().getTime());

        //Escreve no XML
        shPref = context.getSharedPreferences("eliminado", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = shPref.edit();

        ed.putInt("ePac", pac);
        ed.putFloat("eDiurese", diurese);
        ed.putFloat("eFezes", fezes);
        ed.putFloat("eVomitos", vomitos);
        ed.putFloat("eDreno", dreno);
        ed.putFloat("eSng", sng);
        ed.putFloat("eSangue", sangue);
        ed.putFloat("eOutros", outros);
        ed.putInt("eTec", tec);
        ed.putString("eData", data);

        ed.commit();

    }

}
