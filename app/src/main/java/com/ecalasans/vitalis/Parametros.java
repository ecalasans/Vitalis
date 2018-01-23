package com.ecalasans.vitalis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericcalasans on 23/11/16.
 */

public class Parametros implements Parcelable {

    private int tec, pac, fluxo, fi, pinsp, peep, fr;
    private String data;
    private int fisio, asp, decubito, higOral, higOcular;
    private float ie, ti, te;

    public Parametros(int pac, int tec, String data, int fisio,int asp,
                      int decubito, int higOral, int higOcular, float ie,
                      int fluxo, float ti, float te, int fi, int fr, int pinsp, int peep) {
        this.tec = tec;
        this.pac = pac;
        this.fluxo = fluxo;
        this.fi = fi;
        this.pinsp = pinsp;
        this.peep = peep;
        this.fr = fr;
        this.data = data;
        this.fisio = fisio;
        this.asp = asp;
        this.decubito = decubito;
        this.higOral = higOral;
        this.higOcular = higOcular;
        this.ie = ie;
        this.ti = ti;
        this.te = te;
    }

    protected Parametros(Parcel in) {

        this.tec = in.readInt();
        this.pac = in.readInt();
        this.fluxo = in.readInt();
        this.fi = in.readInt();
        this.pinsp = in.readInt();
        this.peep = in.readInt();
        this.fr = in.readInt();
        this.data = in.readString();
        this.fisio = in.readByte();
        this.asp = in.readByte();
        this.decubito = in.readByte();
        this.higOral = in.readByte();
        this.higOcular = in.readByte();
        this.ie = in.readFloat();
        this.ti = in.readFloat();
        this.te = in.readFloat();
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(this.tec);
        parcel.writeInt(this.pac);
        parcel.writeInt(this.fluxo);
        parcel.writeInt(this.fi);
        parcel.writeInt(this.pinsp);
        parcel.writeInt(this.peep);
        parcel.writeInt(this.fr);
        parcel.writeString(this.data);
        parcel.writeInt(this.fisio);
        parcel.writeInt(this.asp);
        parcel.writeInt(this.decubito);
        parcel.writeInt(this.higOral);
        parcel.writeInt(this.higOcular);
        parcel.writeFloat(this.ie);
        parcel.writeFloat(this.ti);
        parcel.writeFloat(this.te);
    }

    public static final Creator<Parametros> CREATOR = new Creator<Parametros>() {
        @Override
        public Parametros createFromParcel(Parcel in) {
            return new Parametros(in);
        }

        @Override
        public Parametros[] newArray(int size) {
            return new Parametros[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public int getTec() {
        return tec;
    }

    public void setTec(int tec) {
        this.tec = tec;
    }

    public int getPac() {
        return pac;
    }

    public void setPac(int pac) {
        this.pac = pac;
    }

    public int getFluxo() {
        return fluxo;
    }

    public void setFluxo(int fluxo) {
        this.fluxo = fluxo;
    }

    public int getFi() {
        return fi;
    }

    public void setFi(int fi) {
        this.fi = fi;
    }

    public int getPinsp() {
        return pinsp;
    }

    public void setPinsp(int pinsp) {
        this.pinsp = pinsp;
    }

    public int getPeep() {
        return peep;
    }

    public void setPeep(int peep) {
        this.peep = peep;
    }

    public int getFr() {
        return fr;
    }

    public void setFr(int fr) {
        this.fr = fr;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getFisio() {
        return fisio;
    }

    public void setFisio(int fisio) {
        this.fisio = fisio;
    }

    public int getAsp() {
        return asp;
    }

    public void setAsp(int asp) {
        this.asp = asp;
    }

    public int getDecubito() {
        return decubito;
    }

    public void setDecubito(int decubito) {
        this.decubito = decubito;
    }

    public int getHigOral() {
        return higOral;
    }

    public void setHigOral(int higOral) {
        this.higOral = higOral;
    }

    public int getHigOcular() {
        return higOcular;
    }

    public void setHigOcular(int higOcular) {
        this.higOcular = higOcular;
    }

    public float getIe() {
        return ie;
    }

    public void setIe(float ie) {
        this.ie = ie;
    }

    public float getTi() {
        return ti;
    }

    public void setTi(float ti) {
        this.ti = ti;
    }

    public float getTe() {
        return te;
    }

    public void setTe(float te) {
        this.te = te;
    }
}
