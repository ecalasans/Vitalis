package com.ecalasans.vitalis;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ericcalasans on 09/12/16.
 */

public class InclusaoSinaisVitais extends AsyncTask<String,String,String> {

    private Context cont;

    int contador = 0;
    private ProgressDialog progress;

    private String[] info = {"Gravando sinais vitais...", "Gravando líquidos infundidos, dieta e outros...",
        "Gravando líquidos eliminados e outros...", "Gravando parâmetros de VM e outras informações",
        "Gravando evolução..."};

    String endereco, rSinais, rInf, rElim, rParam, rEvol;

    URL url;


    public InclusaoSinaisVitais(Context context){
        this.cont = context;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(cont);
        progress.setMessage("Enviando dados...");
        progress.show();
    }

    @Override
    protected String doInBackground(String... params) {

        endereco = params[0];
        rSinais = params[1];
        rInf = params[2];
        rElim = params[3];
        rParam = params[4];
        rEvol = params[5];

        try {
            url = new URL(endereco);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.setDoInput(true);
            conexao.setDoOutput(true);

            //Cria buffer de saída
            OutputStream saida = conexao.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(saida));

            //Cria a string de saída
            Uri.Builder strSaida = new Uri.Builder();
            strSaida.appendQueryParameter("sinais", rSinais);
            strSaida.appendQueryParameter("infundido", rInf);
            strSaida.appendQueryParameter("eliminado", rElim);
            strSaida.appendQueryParameter("parametros", rParam);
            strSaida.appendQueryParameter("evolucao", rEvol);

            String dadosFormatados = strSaida.build().getEncodedQuery().toString();


            //Descarrega o buffer
            escritor.write(dadosFormatados);
            escritor.flush();
            saida.close();
            conexao.connect();

            //Recebe resultado da inclusão
            InputStream entrada = conexao.getInputStream();
            BufferedReader leitor = new BufferedReader(new InputStreamReader(entrada));

            StringBuilder sb = new StringBuilder();
            String l;

            while ((l = leitor.readLine()) != null){
                sb.append(l);
            }

            return sb.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(String resultado) {
        progress.cancel();


        //Volta para o menu Principal
        AlertDialog.Builder consAlerta = new AlertDialog.Builder(cont);
        consAlerta.setMessage(resultado);
        consAlerta.setCancelable(true);
        consAlerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                //Limpa os dados do celular
                limpaSharedPref();
                ((Activity)cont).finish();
            }
        });

        AlertDialog fecha = consAlerta.create();
        fecha.show();

    }

    //Limpa SharedPreferences
    public void limpaSharedPref(){
        SharedPreferences shPref = null;

        shPref = cont.getSharedPreferences("sinais", cont.MODE_PRIVATE);
        shPref.edit().clear().commit();

        shPref = cont.getSharedPreferences("infundido", cont.MODE_PRIVATE);
        shPref.edit().clear().commit();

        shPref = cont.getSharedPreferences("eliminado", cont.MODE_PRIVATE);
        shPref.edit().clear().commit();

        shPref = cont.getSharedPreferences("parametros", cont.MODE_PRIVATE);
        shPref.edit().clear().commit();

        shPref = cont.getSharedPreferences("evolucao", cont.MODE_PRIVATE);
        shPref.edit().clear().commit();
    }
}
