package com.ecalasans.vitalis;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.Calendar;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.os.PersistableBundle;

/**
 * Created by ericcalasans on 08/01/17.
 */

public class Leitos extends AsyncTask<JobParameters, Void, String> {
    String login, senha, tec, data, endereco, turno, verTurno;

    JobParameters paramsScheduler;

    SharedPreferences spTurno;

    URL url;

    private MonitorAtribuicao ma;

    public Leitos(MonitorAtribuicao ma) {
        this.ma = ma;
    }

    @Override
    protected String doInBackground(JobParameters... parametros) {

        Log.i("Vitalis", "Está na AsyncTask");

        PersistableBundle dados = parametros[0].getExtras();

        paramsScheduler = parametros[0];

        login = dados.getString("login");
        senha = dados.getString("senha");
        endereco = dados.getString("endereco");

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
        super.onPostExecute(s);

        //Array de Pacientes
        ArrayList<Paciente> p = new ArrayList<Paciente>();

        try{
            //Decodifica o JSON
            JSONObject o = new JSONObject(s);
            String tecnico = o.getString("NomeTec");
            String existe = o.getString("existe");
            int idTec = o.getInt("idTec");
            turno = o.getString("turno");
            JSONArray a = o.getJSONArray("pacientes");

            //Memoriza o turn nas preferências do celular
            spTurno = ma.getSharedPreferences("turno", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = spTurno.edit();
            ed.putString("turno", turno);
            ed.commit();


            //Se existir pacientes
            //Transforma os dados do JSON em objetos da classe Paciente
            if(existe.equals("sim")){
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

                //Ajusta os alarmes dos pacientes
                setaAlarmes(idTec, p);

                ma.jobFinished(paramsScheduler, true);

            //Se não existir, desativa os alarmes que já existem para os pacientes anteriores
            } else {
                AlarmManager am = (AlarmManager) ma.getSystemService(Context.ALARM_SERVICE);
                Intent apagarAlarmes = new Intent("LOGADO");

                boolean ativado;

                PendingIntent pendencia;

                for(int i = 0; i < p.size(); i++){
                    ativado = (PendingIntent.getBroadcast(ma,i,apagarAlarmes,
                            PendingIntent.FLAG_NO_CREATE) != null);

                    pendencia = PendingIntent.getBroadcast(ma, i, apagarAlarmes, 0);

                    if(ativado){
                        am.cancel(pendencia);
                        Log.i("Vitalis", "Limpando alarmes...");
                    }

                }
                
            }
            //Se não houver pacientes atribuidos dispara o tratamento de exceção,
            //a saber, notifica que não há pacientes
        } catch (JSONException e) {

        }
    }

    public void setaAlarmes(int idTec, ArrayList<Paciente> p){

        Log.i("Vitalis", "Começou a ajustar os alarmes...");

        long inAlarme = 0;
        Intent setarAlarmes = new Intent("LOGADO");


        SharedPreferences spVerTurno = ma.getSharedPreferences("verTurno", Context.MODE_PRIVATE);
        verTurno = spVerTurno.getString("verTurno", "");


        //Se o turno foi mudado, ajusta os alarmes
        if(!verTurno.equals(turno)) {
            ArrayList<PendingIntent> pendentes = new ArrayList<PendingIntent>();

            AlarmManager am = (AlarmManager) ma.getSystemService(Context.ALARM_SERVICE);

            //Ajusta os alarmes
            for (int i = 0; i < p.size(); i++) {

                //Recebe os parâmetros para enviar para Notificação
                setarAlarmes.putExtra("idTec", idTec);
                setarAlarmes.putExtra("paciente", p.get(i));


                //Adiciona um PendingIntent a pendentes
                pendentes.add(PendingIntent.getBroadcast(ma, i, setarAlarmes,
                        PendingIntent.FLAG_UPDATE_CURRENT));

                inAlarme = inicioAlarme(p.get(i).getIntervalo());
                long intervAlarme = p.get(i).getIntervalo() * (60 * 60 * 1000);
                am.setRepeating(AlarmManager.RTC_WAKEUP, inAlarme, intervAlarme,
                            pendentes.get(i));

                Log.i("Vitalis", "Provavelmente conseguiu ajustar os alarmes para "
                        + p.get(i).getNome() + "!");

            }

            //Guarda verTurno na memória do celular
            SharedPreferences.Editor edVerTurno = spVerTurno.edit();
            edVerTurno.putString("verTurno", turno);
            edVerTurno.commit();

        } else {
            Log.i("Vitalis", "Alarmes já foram ajustados para este turno!");
        }
    }

    //Função para calcular a hora de início do alarme retornando em milissegundos
    protected long inicioAlarme(int periodo){

        Log.i("Vitalis", "Calculando hora do alarme...");

        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());

        int horaAlarme = c.get(Calendar.HOUR_OF_DAY);
        int horaDisparo = 0;
        int incremento = 0;

        //Testa se a hora é múltipla do período e ajusta a o início para uma valor de hora
        //que seja múltipla
        if(Math.abs(horaAlarme) % periodo != 0){
            int quoc = horaAlarme/periodo;
            horaDisparo = (quoc + 1) * periodo;
            incremento = horaDisparo - horaAlarme;
            Log.i("Vitalis", "Hora de disparo:  " + horaDisparo);
        } else {
            horaDisparo = horaAlarme + periodo;
            incremento = horaDisparo - horaAlarme;
            Log.i("Vitalis", "Hora de disparo:  " + horaDisparo);
        }

        c.add(Calendar.HOUR_OF_DAY, incremento);

        return c.getTimeInMillis();

    }
}

