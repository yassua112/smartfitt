package com.yosua.projectskripsi.Model;

public class ComplateProfile {



    private int id, Vtinggi,Vberat,id_user;
    private String Jeniskelamin,KetGizi,PersonalName,Years,aktivitas;
    private double kalori,IMT;


    public String getAktivitas() {
        return aktivitas;
    }

    public void setAktivitas(String aktivitas) {
        this.aktivitas = aktivitas;
    }

    public double getIMT() {
        return IMT;
    }

    public void setIMT(double IMT) {
        this.IMT = IMT;
    }

    public int getId_user() {
        return id_user;
    }

    public String getPersonalName() {
        return PersonalName;
    }

    public void setPersonalName(String personalName) {
        PersonalName = personalName;
    }


    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVtinggi() {
        return Vtinggi;
    }

    public void setVtinggi(int vtinggi) {
        Vtinggi = vtinggi;
    }

    public int getVberat() {
        return Vberat;
    }

    public void setVberat(int vberat) {
        Vberat = vberat;
    }

    public String getYears() {
        return Years;
    }

    public double getKalori() {
        return kalori;
    }

    public void setKalori(double kalori) {
        this.kalori = kalori;
    }

    public void setYears(String years) {
        Years = years;
    }

    public String getJeniskelamin() {
        return Jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        Jeniskelamin = jeniskelamin;
    }

    public String getKetGizi() {
        return KetGizi;
    }

    public void setKetGizi(String statusGizi) {
        KetGizi = statusGizi;
    }
}
