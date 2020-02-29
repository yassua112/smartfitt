package com.yosua.projectskripsi.Model;

public class RekMakanan {
    String nmPauk,Lauk,Pokok,Buah,Susu;
    int kkPauk,kkLauk,kkPokok,kkBuah,kkSusu;



    public String getNmPauk() {
        return nmPauk;
    }

    public void setNmPauk(String nmPauk) {
        this.nmPauk = nmPauk;
    }

    public String getLauk() {
        return Lauk;
    }

    public void setLauk(String lauk) {
        Lauk = lauk;
    }

    public String getPokok() {
        return Pokok;
    }

    public int getKkPauk() {
        return kkPauk;
    }

    public void setKkPauk(int kkPauk) {
        this.kkPauk = kkPauk;
    }

    public void setPokok(String pokok) {
        Pokok = pokok;
    }

    public String getBuah() {
        return Buah;
    }

    public void setBuah(String buah) {
        Buah = buah;
    }

    public String getSusu() {
        return Susu;
    }

    public void setSusu(String susu) {
        Susu = susu;
    }
}

