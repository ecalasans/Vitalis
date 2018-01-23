package com.ecalasans.vitalis;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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
import java.util.GregorianCalendar;
import java.util.Iterator;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;
import me.tatarka.support.os.PersistableBundle;

/**
 * Created by ericcalasans on 30/12/16.
 */

public class MonitorAtribuicao extends JobService {
    //Objetos que serão utilizados

    String login, senha, endereco;

    Leitos l;

    @Override
    public boolean onStartJob(JobParameters params) {
        //Recebe os parâmetros enviados

        Log.i("Vitalis", "Está dentro do serviço!");

        l = new Leitos(this);
        l.execute(params);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("Vitalis", "Chamou o onStopJob");
        return false;
    }


}
