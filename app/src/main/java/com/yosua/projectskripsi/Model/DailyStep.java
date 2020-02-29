package com.yosua.projectskripsi.Model;

public class DailyStep {
    private  long total;
    private Float kalstep;
    private Float jarak;
//
//    public DailyStep(long total , float kkkalstep , float jarak){
//
//        this.total = total;
//        this.kalstep=kkkalstep;
//        this.jarak = jarak;
//
//    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Float getKalstep() {
        return kalstep;
    }

    public void setKalstep(Float kalstep) {
        this.kalstep = kalstep;
    }

    public Float getJarak() {
        return jarak;
    }

    public void setJarak(Float jarak) {
        this.jarak = jarak;
    }
}
