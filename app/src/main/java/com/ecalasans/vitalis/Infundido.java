package com.ecalasans.vitalis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ericcalasans on 23/11/16.
 */

public class Infundido implements Parcelable {
    private int pac, tec, oral, sng;
    private String data;
    private float drogasV, soro, medic, npt, sangue, outros;

    protected Infundido(Parcel in) {

        this.pac = in.readInt();
        this.tec = in.readInt();
        this.oral = in.readInt();
        this.sng = in.readInt();
        this.data = in.readString();
        this.drogasV = in.readFloat();
        this.soro = in.readFloat();
        this.medic = in.readFloat();
        this.npt = in.readFloat();
        this.sangue = in.readFloat();
        this.outros = in.readFloat();

    }

    public Infundido(int pac,float drogasV, int oral, int sng, float soro, float medic,
                     float npt, float sangue, float outros, int tec, String data) {

        this.pac = pac;
        this.tec = tec;
        this.oral = oral;
        this.sng = sng;
        this.data = data;
        this.drogasV = drogasV;
        this.soro = soro;
        this.medic = medic;
        this.npt = npt;
        this.sangue = sangue;
        this.outros = outros;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(this.pac);
        dest.writeInt(this.tec);
        dest.writeInt(this.oral);
        dest.writeInt(this.sng);
        dest.writeString(this.data);
        dest.writeFloat(this.drogasV);
        dest.writeFloat(this.soro);
        dest.writeFloat(this.medic);
        dest.writeFloat(this.npt);
        dest.writeFloat(this.sangue);
        dest.writeFloat(this.outros);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Infundido> CREATOR = new Creator<Infundido>() {
        @Override
        public Infundido createFromParcel(Parcel in) {
            return new Infundido(in);
        }

        @Override
        public Infundido[] newArray(int size) {
            return new Infundido[size];
        }
    };

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

    public int getOral() {
        return oral;
    }

    public void setOral(int oral) {
        this.oral = oral;
    }

    public int getSng() {
        return sng;
    }

    public void setSng(int sng) {
        this.sng = sng;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getDrogasV() {
        return drogasV;
    }

    public void setDrogasV(float drogasV) {
        this.drogasV = drogasV;
    }

    public float getSoro() {
        return soro;
    }

    public void setSoro(float soro) {
        this.soro = soro;
    }

    public float getMedic() {
        return medic;
    }

    public void setMedic(float medic) {
        this.medic = medic;
    }

    public float getNpt() {
        return npt;
    }

    public void setNpt(float npt) {
        this.npt = npt;
    }

    public float getSangue() {
        return sangue;
    }

    public void setSangue(float sangue) {
        this.sangue = sangue;
    }

    public float getOutros() {
        return outros;
    }

    public void setOutros(float outros) {
        this.outros = outros;
    }
}
