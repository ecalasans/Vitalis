package com.ecalasans.vitalis;

;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ericcalasans on 21/11/16.
 */

public class fragParametros extends Fragment {

    View view;


    Parametros p;

    int tec = 0, pac = 0, fluxo = 0, fi = 0, pinsp = 0, peep = 0, fr = 0;

    String data;

    byte fisio = 0, asp = 0, decubito = 0, higOral = 0, higOcular = 0;

    float ie = 0, ti = 0, te = 0;

    EditText pPinsp, pPeep, pFR, pTi, pTe, pFluxo, pIE, pFi;

    CheckBox pFisio, pAsp, pDecubito, pHigOral, pHigOcular;

    TextView pPac, pDataHora;


    SharedPreferences shPref;

    Context context;

    public fragParametros() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_parametros, null);

        pPinsp = (EditText) view.findViewById(R.id.etPinsp);
        pPeep = (EditText) view.findViewById(R.id.etPPeep);
        pFR = (EditText) view.findViewById(R.id.etPFr);
        pTi = (EditText) view.findViewById(R.id.etTi);
        pTe = (EditText) view.findViewById(R.id.etTe);
        pFluxo = (EditText) view.findViewById(R.id.etPFluxo);
        pIE = (EditText) view.findViewById(R.id.etIE);
        pFi = (EditText) view.findViewById(R.id.etPFi);

        pFisio = (CheckBox) view.findViewById(R.id.chkFisio);
        pAsp = (CheckBox) view.findViewById(R.id.chkAsp);
        pDecubito = (CheckBox) view.findViewById(R.id.chkMud);
        pHigOral = (CheckBox) view.findViewById(R.id.chkHOral);
        pHigOcular = (CheckBox) view.findViewById(R.id.chkHOcular);


        pPac = (TextView) view.findViewById(R.id.txtPPac);
        pPac.setText(getArguments().getString("letreiro"));

        pDataHora = (TextView) view.findViewById(R.id.txtPDataHora);

        SimpleDateFormat dh = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dataHora = dh.format(Calendar.getInstance().getTime());
        pDataHora.setText(dataHora);

        context = getActivity();

        return(view);
    }



    @Override
    public void onResume() {
        super.onResume();


        pFluxo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(!b) {
                    AlertDialog.Builder consAlerta = new AlertDialog.Builder(context);
                    consAlerta.setMessage("Confirma os dados?");
                    consAlerta.setCancelable(true);
                    consAlerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            parametrosSharedPref();
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
        }
        );
    }

    public void parametrosSharedPref(){
        //Seta variáveis para serem escritas no XML
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data = df.format(Calendar.getInstance().getTime());

        pac = getArguments().getInt("idPac");
        tec = getArguments().getInt("idTec");

        fluxo = Integer.parseInt(pFluxo.getText().toString());
        fi = Integer.parseInt(pFi.getText().toString());
        pinsp = Integer.parseInt(pPinsp.getText().toString());
        peep = Integer.parseInt(pPeep.getText().toString());
        fr = Integer.parseInt(pFR.getText().toString());

        if (pFisio.isChecked()) {
            fisio = 1;
        } else {
            fisio = 0;
        }

        if (pAsp.isChecked()) {
            asp = 1;
        } else {
            asp = 0;
        }

        if (pDecubito.isChecked()) {
            decubito = 1;
        } else {
            decubito = 0;
        }

        if (pHigOral.isChecked()) {
            higOral = 1;
        } else {
            higOral = 0;
        }

        if (pHigOcular.isChecked()) {
            higOcular = 1;
        } else {
            higOcular = 0;
        }

        ti = Float.parseFloat(pTi.getText().toString());
        te = Float.parseFloat(pTe.getText().toString());
        ie = Float.parseFloat(pIE.getText().toString());

        //Escreve no XML
        shPref = context.getSharedPreferences("parametros", context.MODE_PRIVATE);
        SharedPreferences.Editor ed = shPref.edit();

        ed.putInt("pPac", pac);
        ed.putInt("pTec", tec);
        ed.putString("pData", data);
        ed.putInt("pFisio", (int) fisio);
        ed.putInt("pCanula", (int) asp);
        ed.putInt("pDecubito", (int) decubito);
        ed.putInt("pHigOral", (int) higOral);
        ed.putInt("pHigOcular", (int) higOcular);
        ed.putFloat("pIe", ie);
        ed.putInt("pFluxo", fluxo);
        ed.putFloat("pTi", ti);
        ed.putFloat("pTe", te);
        ed.putInt("pFio2", fi);
        ed.putInt("pFr", fr);
        ed.putInt("pPinsp", pinsp);
        ed.putInt("pPeep", peep);

        ed.commit();

    }

}
