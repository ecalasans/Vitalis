package com.ecalasans.vitalis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericcalasans on 23/11/16.
 */

public class Eliminado implements Parcelable {

    private int pac, tec;
    private String data;
    private float diurese, dreno, fezes, outros, vomitos, sng, sangue;

    protected Eliminado(Parcel in) {

        this.pac = in.readInt();
        this.tec = in.readInt();
        this.fezes = in.readFloat();
        this.vomitos = in.readFloat();
        this.sng = in.readFloat();
        this.sangue = in.readFloat();
        this.data = in.readString();
        this.diurese = in.readFloat();
        this.dreno = in.readFloat();
        this.outros = in.readFloat();

    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.pac);
        parcel.writeInt(this.tec);
        parcel.writeFloat(this.fezes);
        parcel.writeFloat(this.vomitos);
        parcel.writeFloat(this.sangue);
        parcel.writeString(this.data);
        parcel.writeFloat(this.diurese);
        parcel.writeFloat(this.dreno);
        parcel.writeFloat(this.outros);
    }

    public Eliminado(int pac, float diurese, float fezes, float vomitos, float dreno,float sng,
                     float sangue, float outros, int tec, String data) {

        this.pac = pac;
        this.tec = tec;
        this.fezes = fezes;
        this.vomitos = vomitos;
        this.sng = sng;
        this.sangue = sangue;
        this.data = data;
        this.diurese = diurese;
        this.dreno = dreno;
        this.outros = outros;
    }

    public static final Creator<Eliminado> CREATOR = new Creator<Eliminado>() {
        @Override
        public Eliminado createFromParcel(Parcel in) {
            return new Eliminado(in);
        }

        @Override
        public Eliminado[] newArray(int size) {
            return new Eliminado[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
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

    public float getFezes() {
        return fezes;
    }

    public void setFezes(float fezes) {
        this.fezes = fezes;
    }

    public float getVomitos() {
        return vomitos;
    }

    public void setVomitos(float vomitos) {
        this.vomitos = vomitos;
    }

    public float getSng() {
        return sng;
    }

    public void setSng(float sng) {
        this.sng = sng;
    }

    public float getSangue() {
        return sangue;
    }

    public void setSangue(float sangue) {
        this.sangue = sangue;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getDiurese() {
        return diurese;
    }

    public void setDiurese(float diurese) {
        this.diurese = diurese;
    }

    public float getDreno() {
        return dreno;
    }

    public void setDreno(float dreno) {
        this.dreno = dreno;
    }

    public float getOutros() {
        return outros;
    }

    public void setOutros(float outros) {
        this.outros = outros;
    }
}
