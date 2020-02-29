package com.yosua.projectskripsi.Model;

public class HistoriOlahraga {
    private String nm_olahraga,date;
    private float totalKal;
    private int waktu;

    public String getNm_olahraga() {
        return nm_olahraga;
    }

    public void setNm_olahraga(String nm_olahraga) {
        this.nm_olahraga = nm_olahraga;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getTotalKal() {
        return totalKal;
    }

    public void setTotalKal(float totalKal) {
        this.totalKal = totalKal;
    }

    public int getWaktu() {
        return waktu;
    }

    public void setWaktu(int waktu) {
        this.waktu = waktu;
    }
}
