package com.ecalasans.vitalis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import me.tatarka.support.job.JobScheduler;


public class Menu extends Activity implements AdapterView.OnItemClickListener {

    String tecPlantao, existe, login, senha, endereco, turno;
    ArrayList<Paciente> lista;
    int idTec;

    Context contexto = this;

    JobScheduler jsConsulta;

    TextView txtTec;
    ListView lstLeitos;


    Switch swAtivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Log.i("Vitalis", "Chegou em Menu com sucesso!");
        txtTec = (TextView) findViewById(R.id.txtTec);
        lstLeitos = (ListView) findViewById(R.id.lstLeitos);
        swAtivo = (Switch) findViewById(R.id.swAtivo);

        //Recebe os dados
        Intent atribuicao = getIntent();
        tecPlantao = atribuicao.getStringExtra("tecnico");
        idTec = atribuicao.getIntExtra("idTec",0);
        lista = atribuicao.getParcelableArrayListExtra("pacientes");
        existe = atribuicao.getStringExtra("existe");
        login = atribuicao.getStringExtra("login");
        senha = atribuicao.getStringExtra("senha");
        turno = atribuicao.getStringExtra("turno");
        endereco = atribuicao.getStringExtra("endereco");

        PacienteAdapter adapter = new PacienteAdapter(this,lista);

        //Seta os valores dos controles
        txtTec.setText(tecPlantao);
        lstLeitos.setAdapter(adapter);

        lstLeitos.setOnItemClickListener(this);

        swAtivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b){
                    lstLeitos.setVisibility(View.INVISIBLE);
                } else {
                    lstLeitos.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Paciente p = lista.get(i);

        /*ArrayList<Paciente> paciente = new ArrayList<Paciente>();
        paciente.add(p);*/

        Intent intent = new Intent(getBaseContext(),Pages.class);
        intent.putExtra("idTec", idTec);
        intent.putExtra("paciente", p);

        startActivity(intent);

    }





}
