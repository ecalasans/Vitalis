package com.ecalasans.vitalis;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * Created by ericcalasans on 21/11/16.
 */

public class fragSinais extends Fragment {

    RelativeLayout laySinais;

    View view;

    Sinais s;


    TextView nomePac, txtPAM, txtDataHora;

    EditText etFC, etFR, etSO2, etTemp, etHGT, etPAS, etPAD;

    int fc=0, fr=0, so2=0, hgt=0, pas=0, pam=0, pad=0, pac=0, tec=0;

    float temp=0;

    String data;

    SharedPreferences shPref;


    Context context;

    public fragSinais() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Localizador", "Está no onCreateView do Sinais");
        view = inflater.inflate(R.layout.layout_sinais,null);

        nomePac = (TextView) view.findViewById(R.id.txtSPac);
        txtPAM =(TextView) view.findViewById(R.id.txtPAM);
        txtDataHora = (TextView) view.findViewById(R.id.txtSDataHora);

        etFC = (EditText) view.findViewById(R.id.etFC);
        etFR = (EditText) view.findViewById(R.id.etFR);
        etTemp = (EditText) view.findViewById(R.id.etTemp);
        etSO2 = (EditText) view.findViewById(R.id.etSO2);
        etHGT = (EditText) view.findViewById(R.id.etHGT);
        etPAS = (EditText) view.findViewById(R.id.etPAS);
        etPAD = (EditText) view.findViewById(R.id.etPAD);

        nomePac.setText(getArguments().getString("letreiro"));

        SimpleDateFormat dh = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataHora = dh.format(Calendar.getInstance().getTime());
        txtDataHora.setText(dataHora);

        context = getActivity();

        return(view);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("Localizador", "Está no onResume do Sinais");

        //Manipula eventos na mudança de texto no EditText
        etPAD.addTextChangedListener(new TextWatcher() {
            int pasTemp = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pasTemp = Integer.parseInt(etPAS.getText().toString());
                int padTemp = Integer.parseInt(s.toString());
                pam = (int)(pasTemp+padTemp)/2;
                txtPAM.setText(String.valueOf(pam));
            }
        });



        etPAD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    AlertDialog.Builder consAlerta = new AlertDialog.Builder(context);
                    consAlerta.setMessage("Confirma os dados?");
                    consAlerta.setCancelable(true);
                    consAlerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            sinaisSharedPref();
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

    public void sinaisSharedPref(){
        //Seta as variáveis que irão pro arquivo XML
        fc = Integer.parseInt(etFC.getText().toString());
        fr = Integer.parseInt(etFR.getText().toString());
        so2 = Integer.parseInt(etSO2.getText().toString());
        temp = Float.parseFloat(etTemp.getText().toString());
        hgt = Integer.parseInt(etHGT.getText().toString());
        pas = Integer.parseInt(etPAS.getText().toString());
        pad = Integer.parseInt(etPAD.getText().toString());
        pam = (int) (pas+pad)/2;

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        data = df.format(Calendar.getInstance().getTime());

        pac = getArguments().getInt("idPac");
        tec = getArguments().getInt("idTec");

        txtPAM.setText(String.valueOf(pam));


        //Salva em SharedPreferences
        shPref = context.getSharedPreferences("sinais", context.MODE_PRIVATE);
        Editor ed = shPref.edit();

        ed.putInt("sPac", pac);
        ed.putInt("sTec", tec);
        ed.putString("sData", data);
        ed.putFloat("sTemp", temp);
        ed.putInt("sFc", fc);
        ed.putInt("sFr", fr);
        ed.putInt("sSat", so2);
        ed.putInt("sPas", pas);
        ed.putInt("sPad", pad);
        ed.putInt("sPam", pam);
        ed.putInt("sHgt", hgt);

        ed.commit();

    }

}
