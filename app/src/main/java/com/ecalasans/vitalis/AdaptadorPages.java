package com.ecalasans.vitalis;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ericcalasans on 21/11/16.
 */

public class AdaptadorPages extends FragmentPagerAdapter {

    private String nomePac;
    private int idPac;
    private int idTec;

    public AdaptadorPages(FragmentManager fm) {
        super(fm);
    }

    public AdaptadorPages(FragmentManager fm, int idPac, int idTec, String nomePac) {
        super(fm);
        this.idPac = idPac;
        this.idTec = idTec;
        this.nomePac = nomePac;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment temp = null;
        Bundle args = new Bundle();

        args.putString("letreiro", nomePac);
        args.putInt("idPac", idPac);
        args.putInt("idTec", idTec);

        if(position == 0){
            temp = new fragSinais();

            temp.setArguments(args);
        }

        if(position == 1){
            temp = new fragBHI();
            temp.setArguments(args);
        }

        if(position == 2){
            temp = new fragBHE();
            temp.setArguments(args);
        }

        if(position == 3){
            temp = new fragParametros();
            temp.setArguments(args);
        }

        if(position == 4){
            temp = new fragEvolucao();
            temp.setArguments(args);
        }
        return temp;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String titulo = "";
        if(position == 0) titulo =  "Sinais";
        if(position == 1) titulo =  "Infundido";
        if(position == 2) titulo =  "Eliminado";
        if(position == 3) titulo =  "Parâmetros";
        if(position == 4) titulo =  "Evolução";
        return titulo;
    }
}
