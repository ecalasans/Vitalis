package com.ecalasans.vitalis;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText usuario, senha;
    TextView txtU, txtS, limpa;
    RelativeLayout layMain;

    String url;

    Login conecta;

    Typeface helveticaNeue, helveticaNeueBold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //

        layMain = (RelativeLayout) findViewById(R.id.layMain);
        usuario = (EditText) findViewById(R.id.etUsuario);
        senha = (EditText) findViewById(R.id.etSenha);
        limpa = (TextView) findViewById(R.id.btnLimpar);

        limpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < layMain.getChildCount(); i++){
                    v = layMain.getChildAt(i);
                    if(v instanceof EditText){
                        ((EditText)v).setText("");
                    }
                }

            }
        });
    }

    public void efetuaLogin(View view){
        url = "http://vitalis.pds2016.kinghost.net/loginTec.php";
        String l = usuario.getText().toString();
        String s = senha.getText().toString();

        conecta = new Login(this);
        //Vai para Login
        conecta.execute(url, l, s);

    }


    private void alteraFont(ViewGroup grupo, Typeface tipo){
        Typeface helvetica = Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeue.ttf");

        for(int i = 0; i < grupo.getChildCount(); i++){
            View v = grupo.getChildAt(i);

            if(v instanceof TextView){
                ((TextView)v).setTypeface(helvetica);
            }

        }
    }
}
