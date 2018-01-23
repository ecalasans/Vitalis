package com.ecalasans.vitalis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericcalasans on 11/11/16.
 */

public class Paciente implements Parcelable{
    private int id;
    private String nome;
    private int leito;
    private int intervalo;

    public Paciente(int id, String nome,int leito, int intervalo) {
        this.id = id;
        this.intervalo = intervalo;
        this.leito = leito;
        this.nome = nome;
    }

    public Paciente(Parcel in){
        this.id = in.readInt();
        this.nome = in.readString();
        this.leito = in.readInt();
        this.intervalo = in.readInt();
    }

    public static final Creator<Paciente> CREATOR = new Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(int intervalo) {
        this.intervalo = intervalo;
    }

    public int getLeito() {
        return leito;
    }

    public void setLeito(int leito) {
        this.leito = leito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.nome);
        parcel.writeInt(this.leito);
        parcel.writeInt(this.intervalo);
    }


}
