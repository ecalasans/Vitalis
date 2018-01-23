package com.ecalasans.vitalis;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by ericcalasans on 22/11/16.
 */

public class Sinais implements Parcelable {

    private int pac, tec, saturacao, fc, fr, hgt, pas, pam, pad;
    private float temperatura;
    private String data;

    public Sinais(int pac, int tec, int saturacao, int fc, int fr, int hgt, int pas,
                  int pam, int pad, float temperatura, String data) {

        this.pac = pac;
        this.tec = tec;
        this.saturacao = saturacao;
        this.fc = fc;
        this.fr = fr;
        this.hgt = hgt;
        this.pas = pas;
        this.pam = pam;
        this.pad = pad;
        this.temperatura = temperatura;
        this.data = data;
    }

    protected Sinais(Parcel in) {

        this.pac = in.readInt();
        this.tec = in.readInt();
        this.saturacao = in.readInt();
        this.fc = in.readInt();
        this.fr = in.readInt();
        this.hgt = in.readInt();
        this.pas = in.readInt();
        this.pam = in.readInt();
        this.pad = in.readInt();
        this.temperatura = in.readFloat();
        this.data = in.readString();

    }

    public static final Creator<Sinais> CREATOR = new Creator<Sinais>() {
        @Override
        public Sinais createFromParcel(Parcel in) {
            return new Sinais(in);
        }

        @Override
        public Sinais[] newArray(int size) {
            return new Sinais[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.pac);
        parcel.writeInt(this.tec);
        parcel.writeInt(this.saturacao);
        parcel.writeInt(this.fc);
        parcel.writeInt(this.fr);
        parcel.writeInt(this.hgt);
        parcel.writeInt(this.pas);
        parcel.writeInt(this.pam);
        parcel.writeInt(this.pad);
        parcel.writeFloat(this.temperatura);
        parcel.writeString(this.data);
    }



    public int getPac() {
        return pac;
    }

    public void setPac(int pac) {
        this.pac = pac;
    }

    public int getTec() {
        return tec;
    }

    public void setTec(int tec) {
        this.tec = tec;
    }

    public int getSaturacao() {
        return saturacao;
    }

    public void setSaturacao(int saturacao) {
        this.saturacao = saturacao;
    }

    public int getFc() {
        return fc;
    }

    public void setFc(int fc) {
        this.fc = fc;
    }

    public int getFr() {
        return fr;
    }

    public void setFr(int fr) {
        this.fr = fr;
    }

    public int getHgt() {
        return hgt;
    }

    public void setHgt(int hgt) {
        this.hgt = hgt;
    }

    public int getPas() {
        return pas;
    }

    public void setPas(int pas) {
        this.pas = pas;
    }

    public int getPam() {
        return pam;
    }

    public void setPam(int pam) {
        this.pam = pam;
    }

    public int getPad() {
        return pad;
    }

    public void setPad(int pad) {
        this.pad = pad;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
