package com.ecalasans.vitalis;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.StringDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static com.ecalasans.vitalis.R.drawable.vitalis_logo_titulo;

/**
 * Created by ericcalasans on 30/12/16.
 */

public class Notificacao extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("Vitalis", "Enviando notificação...");

        //Pega o serviço de notificações
        NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);


        int idTec = intent.getIntExtra("idTec", 0);
        Paciente pac = intent.getParcelableExtra("paciente");

        //Constrói o Intent para abrir Pages
        Intent abrePages = new Intent(context, Pages.class);
        abrePages.putExtra("paciente", pac);
        abrePages.putExtra("idTec", idTec);

        // Constrói o PendingIntent que vai ficar esperando um clique na notificação
        PendingIntent p = PendingIntent.getActivity(context, pac.getId(), abrePages, 0);

        //Constrói o label da Notificação
        NotificationCompat.Builder constNotif = new NotificationCompat.Builder(context)
                .setTicker("Você tem pacientes para ver!")
                .setContentTitle("Leito - " + String.valueOf(pac.getLeito()))
                .setContentText("Sinais vitais de " + String.valueOf(pac.getIntervalo())
                    + " em " + String.valueOf(pac.getIntervalo()) + "h")
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        vitalis_logo_titulo))
                .setContentIntent(p);

        Notification notif = constNotif.build();
        notif.vibrate = new long[]{150, 300, 150, 600};
        nm.notify(pac.getId(), notif);

        Log.i("Vitalis", "Notificação enviada!!");

        try {
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context, som);
            toque.play();

        } catch (Exception e){

        }


    }
}
