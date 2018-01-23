package com.ecalasans.vitalis;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ericcalasans on 21/11/16.
 */

public class Pages extends FragmentActivity {

    ViewPager viewPager;

    AdaptadorPages adapt;

    //Paciente p;
    int idTec, idPac;
    String letreiro;
    Paciente pac;

    Bundle paramsRec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pages);

        Log.i("Vitalis", "Fim da linha!");

        Intent i = getIntent();

        paramsRec = i.getExtras();
        pac = paramsRec.getParcelable("paciente");

        letreiro = "RN de " + pac.getNome();
        idPac = pac.getId();
        idTec = paramsRec.getInt("idTec");

        FragmentManager fm = getSupportFragmentManager();
        adapt = new AdaptadorPages(fm, idPac, idTec, letreiro);

        viewPager = (ViewPager) findViewById(R.id.vpPages);
        viewPager.setAdapter(adapt);
    }

}
