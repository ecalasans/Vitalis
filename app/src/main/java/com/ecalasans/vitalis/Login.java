package com.ecalasans.vitalis;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;
import me.tatarka.support.os.PersistableBundle;

/**
 * Created by ericcalasans on 09/11/16.
 */

public class Login extends AsyncTask<String, String, String> {

    public static final int ID_SERVICO = 7;

    String login, senha, tec, data, endereco;

    private ProgressDialog progress;
    private Context contexto;

    JobScheduler jsAlarmes;

    public static final long HORA = 60 * 60 * 1000;

    URL url;

    public Login(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    protected void onPreExecute() {
        progress = new ProgressDialog(contexto);
        progress.setMessage("Conectando ao sistema...");
        progress.show();
    }

    @Override
    protected String doInBackground(String... parametros) {

        endereco = parametros[0];
        login = parametros[1];
        senha = parametros[2];

        try {
            url = new URL(endereco);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection conex = (HttpURLConnection) url.openConnection();
            conex.setRequestMethod("POST");
            conex.setDoInput(true);
            conex.setDoOutput(true);

            OutputStream saida = conex.getOutputStream();
            BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(saida));

            Uri.Builder paramsLogin = new Uri.Builder();
            paramsLogin.appendQueryParameter("login", login);
            paramsLogin.appendQueryParameter("senha", senha);

            String dadosFormatatos = paramsLogin.build().getEncodedQuery().toString();

            escritor.write(dadosFormatatos);
            escritor.flush();
            escritor.close();
            saida.close();
            conex.connect();

            //Recebe os dados

            InputStream entrada = conex.getInputStream();
            BufferedReader leitor = new BufferedReader(new InputStreamReader(entrada));

            StringBuilder sb = new StringBuilder();
            String l;

            //Enquanto tiver alguma coisa no buffer de input, constrói a string
            while ((l = leitor.readLine()) != null){
                sb.append(l);
            }

            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(String s) {
        //Array de Pacientes
        ArrayList<Paciente> p = new ArrayList<Paciente>();

        //Verifica se o técnico está cadastrado no sistema

        //Se não estiver cadastrado exibe uma caixa de diálogo
        if(s.equals("naoexiste")){
            //Caixa de alerta
            AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
            alerta.setMessage("Você não está cadastrado no sistema!");
            alerta.setCancelable(true);
            alerta.setPositiveButton("Ok", new Dialog.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog naoexiste = alerta.create();

            naoexiste.show();
            progress.cancel();

        //Se estiver cadastrado no sistema, recebe os pacientes atribuídos a ele de acordo com o
        //turno
        } else {
            try{
                //Informa que está conectado e cancela o Progress
                progress.cancel();
                Toast.makeText(contexto, "Conectado", Toast.LENGTH_SHORT).show();

                //Decodifica o JSON
                JSONObject o = new JSONObject(s);
                String tecnico = o.getString("NomeTec");
                String existe = o.getString("existe");
                int idTec = o.getInt("idTec");
                String turno = o.getString("turno");
                JSONArray a = o.getJSONArray("pacientes");

                //Transforma os dados do JSON em objetos da classe Paciente
                for (int i = 0; i < a.length(); i++) {

                    try {
                        JSONObject prov = a.getJSONObject(i);

                        int idPac = prov.getInt("idPac");
                        String pac = prov.getString("pac");
                        int leito = prov.getInt("leito");
                        int intervalo = prov.getInt("intervalo");

                        Paciente temp = new Paciente(idPac, pac, leito, intervalo);

                        p.add(temp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                try{
                    constroiServico();
                } catch(Exception e){

                    Log.i("Vitalis", "Alguma coisa deu errado na construção do serviço!");
                    AlertDialog.Builder consAlerta = new AlertDialog.Builder(contexto)
                            .setTitle("Aviso")
                            .setMessage("Não foi possível setar os alarmes!\nAtenção para os horários!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog fecha = consAlerta.create();
                    fecha.show();
                }

                //Vai para a classe Menu e leva os objetos Paciente:  lá será construída a
                //lista
                Intent atribuicao = new Intent(contexto, Menu.class);
                atribuicao.putExtra("existe", existe);
                atribuicao.putExtra("tecnico", tecnico);
                atribuicao.putExtra("idTec", idTec);
                atribuicao.putExtra("login", login);
                atribuicao.putExtra("senha", senha);
                atribuicao.putExtra("turno", turno);
                atribuicao.putParcelableArrayListExtra("pacientes", p);

                //Vai para Menu
                Log.i("Vitalis", "Indo para Menu...");
                contexto.startActivity(atribuicao);

            //Se não houver pacientes atribuidos dispara o tratamento de exceção
            } catch (JSONException e) {
                //Limpa alarmes prévios
                AlarmManager am = (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);
                Intent apagarAlarmes = new Intent("LOGADO");

                boolean ativado;

                PendingIntent pendencia;

                for(int i = 0; i < 10; i++){
                    ativado = (PendingIntent.getBroadcast(contexto,i,apagarAlarmes,
                            PendingIntent.FLAG_NO_CREATE) != null);

                    pendencia = PendingIntent.getBroadcast(contexto, i, apagarAlarmes, 0);

                    if(ativado){
                        am.cancel(pendencia);
                        Log.i("Vitalis", "Limpando alarmes...");
                    }

                }
                AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
                alerta.setMessage("O sistema não atribuiu pacientes a você!\n" +
                        "Caso haja alguma dúvida procure a enfermeira de plantão.");
                alerta.setCancelable(true);
                alerta.setPositiveButton("Ok", new Dialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog naoexiste = alerta.create();

                naoexiste.show();
                progress.cancel();

            }
        }



    }

    public void constroiServico(){

        Log.i("Vitalis", "Construindo serviço...");

        //Constrói o serviço de monitorização da atribuição
        jsAlarmes = JobScheduler.getInstance(contexto);

        ComponentName cn = new ComponentName(contexto, MonitorAtribuicao.class);

        JobInfo.Builder builder = new JobInfo.Builder(40, cn);

        PersistableBundle bundle = new PersistableBundle();


        bundle.putString("endereco", endereco);
        bundle.putString("login", login);
        bundle.putString("senha", senha);        builder.setExtras(bundle)  //Coloca os parâmetros
                .setBackoffCriteria(50000, JobInfo.BACKOFF_POLICY_LINEAR) //Critério para o onStopJob
                .setPeriodic(HORA)  //Repete o serviço em múltiplos de hora
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)  //Requer conexão com a internet
                .setPersisted(true);  //Rearma o serviço durante os reboots

        jsAlarmes.schedule(builder.build());

        Log.i("Vitalis", "Serviço construído!");

    }

}
