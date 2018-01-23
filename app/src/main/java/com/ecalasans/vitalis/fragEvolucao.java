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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by ericcalasans on 21/11/16.
 */

public class fragEvolucao extends Fragment {
    View view;

    TextView evPac, evDataHora, btnEnviar;

    EditText evEvolucao;

    int tec = 0, pac = 0;

    String data, evolucao;

    Context context;

    SharedPreferences shPref;

    InclusaoSinaisVitais envio;

    public fragEvolucao() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_evolucao, null);

        evPac = (TextView) view.findViewById(R.id.txtEvPac);
        evDataHora = (TextView) view.findViewById(R.id.txtEvDataHora);
        btnEnviar = (TextView) view.findViewById(R.id.btInserir);

        evEvolucao = (EditText) view.findViewById(R.id.etEvolucao);

        evPac.setText(getArguments().getString("letreiro"));

        SimpleDateFormat dh = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String dataHora = dh.format(Calendar.getInstance().getTime());
        evDataHora.setText(dataHora);

        context = getActivity();

        return(view);
    }

    @Override
    public void onResume() {
        super.onResume();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Seta as variáveis de Evolução
                //Seta variáveis do XML
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                data = df.format(Calendar.getInstance().getTime());

                pac = getArguments().getInt("idPac");
                tec = getArguments().getInt("idTec");

                evolucao = evEvolucao.getText().toString();

                //Salva no XML
                shPref = context.getSharedPreferences("evolucao", context.MODE_PRIVATE);
                SharedPreferences.Editor ed = shPref.edit();

                ed.putString("evData", data);
                ed.putInt("evTec", tec);
                ed.putInt("evPac", pac);
                ed.putString("evEvol", evolucao);

                ed.commit();


                //Cria os JSONObjects
                JSONObject jsSinais = new JSONObject();
                try {
                    jsSinais.put("sinais", recebeSinais(getSinaisSP()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsInf = new JSONObject();
                try {
                    jsInf.put("infundido", recebeInf(getInfSP()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsElim = new JSONObject();
                try {
                    jsElim.put("eliminado", recebeEl(getElimSP()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsParam = new JSONObject();
                try {
                    jsParam.put("parametros", recebeParam(getParamsSP()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JSONObject jsEvol = new JSONObject();
                try {
                    jsEvol.put("evolucao", recebeEv(getEvolSP()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                final String strSinais = jsSinais.toString();
                final String strInf = jsInf.toString();
                final String strElim = jsElim.toString();
                final String strParam = jsParam.toString();
                final String strEvol = jsEvol.toString();

                //URL de envio
                final String url = "http://vitalis.pds2016.kinghost.net/incluiDados.php";

                //Instancia InclusaoSinaisVitais
                envio = new InclusaoSinaisVitais(context);

                //Executa enviando os parâmetros acima
                AlertDialog.Builder consAlerta = new AlertDialog.Builder(context);
                consAlerta.setMessage("Confirma os dados?");
                consAlerta.setCancelable(true);
                consAlerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        envio.execute(url,strSinais,strInf,strElim,strParam,strEvol);
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
        });

    }


    public Sinais getSinaisSP(){
        //Objeto Shared Preferences para acessar variávies do arquivo temporário
        SharedPreferences temp = context.getSharedPreferences("sinais", context.MODE_PRIVATE);

        //Variáveis locais que irão receber os dados e serão usadas na construção do objeto Sinais
        int pac = temp.getInt("sPac", 0);
        int tec = temp.getInt("sTec", 0);
        String data = temp.getString("sData", "0000-00-00");
        float temperatura = temp.getFloat("sTemp", 0);
        int fc = temp.getInt("sFc", 0);
        int fr = temp.getInt("sFr", 0);
        int sat = temp.getInt("sSat", 0);
        int pas = temp.getInt("sPas", 0);
        int pad = temp.getInt("sPad", 0);
        int pam = temp.getInt("sPam", 0);
        int hgt = temp.getInt("sHgt", 0);

        Sinais s = new Sinais(pac,tec,sat,fc,fr,hgt,pas,pam,pad,temperatura,data);

        return s;
    }

    public Infundido getInfSP(){
        SharedPreferences temp = context.getSharedPreferences("infundido", context.MODE_PRIVATE);

        int pac = temp.getInt("iPac", 0);
        float drogas = temp.getFloat("iDrogas", 0);
        int oral = temp.getInt("iOral", 0);
        int sng = temp.getInt("iSng", 0);
        float soro = temp.getFloat("iSoro", 0);
        float medic = temp.getFloat("iMedic", 0);
        float npt = temp.getFloat("iNpt", 0);
        float sangue = temp.getFloat("iSangue", 0);
        float outros = temp.getFloat("iOutros", 0);
        int tec = temp.getInt("iTec", 0);
        String data = temp.getString("iData", "0000-00-00");

        Infundido i = new Infundido(pac,drogas,oral,sng,soro,medic,npt,sangue,outros,tec,data);

        return i;
    }

    public Eliminado getElimSP(){
        SharedPreferences temp = context.getSharedPreferences("eliminado", context.MODE_PRIVATE);

        int pac = temp.getInt("ePac", 0);
        float diurese = temp.getFloat("eDiurese", 0);
        float fezes = temp.getFloat("eDiurese", 0);
        float vomitos = temp.getFloat("eVomitos", 0);
        float dreno = temp.getFloat("eDreno", 0);
        float sng = temp.getFloat("eSng", 0);
        float sangue = temp.getFloat("eSangue", 0);
        float outros = temp.getFloat("eOutros", 0);
        int tec = temp.getInt("eTec", 0);
        String data = temp.getString("eData", "0000-00-00");

        Eliminado el = new Eliminado(pac,diurese,fezes,vomitos,dreno,sng,sangue,outros,tec,data);

        return el;
    }

    public Parametros getParamsSP(){
        SharedPreferences temp = context.getSharedPreferences("parametros", context.MODE_PRIVATE);

        int pac = temp.getInt("pPac", 0);
        int tec = temp.getInt("pTec", 0);
        String data = temp.getString("pData", "0000-00-00");
        int fisio = temp.getInt("pFisio", 0);
        int asp = temp.getInt("pCanula", 0);
        int decubito = temp.getInt("pDecubito", 0);
        int higOral = temp.getInt("pHigOral", 0);
        int higOcular = temp.getInt("pHigOcular", 0);
        float ie = temp.getFloat("pIe", 0);
        int fluxo = temp.getInt("pFluxo", 0);
        float ti = temp.getFloat("pTi", 0);
        float te = temp.getFloat("pTe", 0);
        int fi = temp.getInt("pFio2", 0);
        int fr = temp.getInt("pFr", 0);
        int pinsp = temp.getInt("pPinsp", 0);
        int peep = temp.getInt("pPeep", 0);

        Parametros p = new Parametros(pac,tec,data,fisio,asp,decubito,higOral,higOcular,ie,fluxo,ti
                        ,te,fi,fr,pinsp,peep);

        return p;
    }

    public Evolucao getEvolSP(){
        SharedPreferences temp = context.getSharedPreferences("evolucao", context.MODE_PRIVATE);

        String data = temp.getString("evData", "0000-00-00");
        int tec = temp.getInt("evTec", 0);
        int pac = temp.getInt("evPac", 0);
        String evol = temp.getString("evEvol", " ");

        Evolucao ev = new Evolucao(data,tec,pac,evol);

        return ev;
    }

    public JSONObject recebeSinais(Sinais s){
        JSONObject jSinais = new JSONObject();

        try {
            jSinais.put("sPac", s.getPac());
            jSinais.put("sTec", s.getTec());
            jSinais.put("sData", s.getData());
            jSinais.put("sTemp", s.getTemperatura());
            jSinais.put("sFc", s.getFc());
            jSinais.put("sFr", s.getFr());
            jSinais.put("sSat", s.getSaturacao());
            jSinais.put("sPas", s.getPas());
            jSinais.put("sPad", s.getPad());
            jSinais.put("sPam", s.getPam());
            jSinais.put("sHgt", s.getHgt());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jSinais;
    }

    public JSONObject recebeInf(Infundido i){
        JSONObject jInf = new JSONObject();

        try {
            jInf.put("iPac", i.getPac());
            jInf.put("iDrogas", i.getDrogasV());
            jInf.put("iOral", i.getOral());
            jInf.put("iSng", i.getSng());
            jInf.put("iSoro", i.getSoro());
            jInf.put("iMedic", i.getMedic());
            jInf.put("iNpt", i.getNpt());
            jInf.put("iSangue", i.getSangue());
            jInf.put("iOutros", i.getOutros());
            jInf.put("iTec", i.getTec());
            jInf.put("iData", i.getData());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jInf;
    }

    public JSONObject recebeEl(Eliminado e){
        JSONObject jEl = new JSONObject();

        try {
            jEl.put("ePac", e.getPac());
            jEl.put("eDiurese", e.getDiurese());
            jEl.put("eFezes", e.getFezes());
            jEl.put("eVomitos", e.getVomitos());
            jEl.put("eDreno", e.getDreno());
            jEl.put("eSng", e.getSng());
            jEl.put("eSangue", e.getSangue());
            jEl.put("eOutros", e.getOutros());
            jEl.put("eTec", e.getTec());
            jEl.put("eData", e.getData());
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        return jEl;
    }

    public JSONObject recebeParam(Parametros p){
        JSONObject jParam = new JSONObject();

        try {
            jParam.put("pPac", p.getPac());
            jParam.put("pTec", p.getTec());
            jParam.put("pData", p.getData());
            jParam.put("pFisio", p.getFisio());
            jParam.put("pCanula", p.getAsp());
            jParam.put("pDecubito", p.getDecubito());
            jParam.put("pHigOral", p.getHigOral());
            jParam.put("pHigOcular", p.getHigOcular());
            jParam.put("pIe", p.getIe());
            jParam.put("pFluxo", p.getFluxo());
            jParam.put("pTi", p.getTi());
            jParam.put("pTe", p.getTe());
            jParam.put("pFio2", p.getFi());
            jParam.put("pFr", p.getFr());
            jParam.put("pPinsp", p.getPinsp());
            jParam.put("pPeep", p.getPeep());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jParam;
    }

    public JSONObject recebeEv(Evolucao ev){
        JSONObject jEv = new JSONObject();

        try {
            jEv.put("evData", ev.getData());
            jEv.put("evTec", ev.getTec());
            jEv.put("evPac", ev.getPac());
            jEv.put("evEvol", ev.getEvolucao());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jEv;
    }

}
