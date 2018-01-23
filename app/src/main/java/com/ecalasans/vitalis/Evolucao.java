package com.ecalasans.vitalis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericcalasans on 23/11/16.
 */

public class Evolucao implements Parcelable {

    private int pac, tec;
    private String data, evolucao;

    public Evolucao(String data, int tec, int pac, String evolucao) {
        this.pac = pac;
        this.tec = tec;
        this.data = data;
        this.evolucao = evolucao;
    }

    protected Evolucao(Parcel in) {
        this.pac = in.readInt();
        this.tec = in.readInt();
        this.data = in.readString();
        this.evolucao = in.readString();
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.pac);
        parcel.writeInt(this.tec);
        parcel.writeString(this.data);
        parcel.writeString(this.evolucao);
    }

    public static final Creator<Evolucao> CREATOR = new Creator<Evolucao>() {
        @Override
        public Evolucao createFromParcel(Parcel in) {
            return new Evolucao(in);
        }

        @Override
        public Evolucao[] newArray(int size) {
            return new Evolucao[size];
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEvolucao() {
        return evolucao;
    }

    public void setEvolucao(String evolucao) {
        this.evolucao = evolucao;
    }
}
