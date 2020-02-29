package com.yosua.projectskripsi.Model;

public class Olahraga {
    String nm_olahraga;
    int kkal,id;

    public Olahraga(String nm_olahraga, int kkal, int id){
        this.nm_olahraga = nm_olahraga;
        this.kkal = kkal;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKkal() {
        return kkal;
    }

    public void setKkal(int kkal) {
        this.kkal = kkal;
    }

    public String getNm_olahraga() {
        return nm_olahraga;
    }

    public void setNm_olahraga(String nm_olahraga) {
        this.nm_olahraga = nm_olahraga;
    }
}
