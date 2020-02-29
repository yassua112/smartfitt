package com.yosua.projectskripsi.Model;

public class HistoriMakanan {
    private String Menupagi,MenuSel1,MenuSiang,MenuSel2,MenuMalam,date;
    private int kkal ;


    public String getMenupagi() {
        return Menupagi;
    }

    public void setMenupagi(String menupagi) {
        Menupagi = menupagi;
    }

    public String getMenuSel1() {
        return MenuSel1;
    }

    public void setMenuSel1(String menuSel1) {
        MenuSel1 = menuSel1;
    }

    public String getMenuSiang() {
        return MenuSiang;
    }

    public void setMenuSiang(String menuSiang) {
        MenuSiang = menuSiang;
    }

    public String getMenuSel2() {
        return MenuSel2;
    }

    public void setMenuSel2(String menuSel2) {
        MenuSel2 = menuSel2;
    }

    public String getMenuMalam() {
        return MenuMalam;
    }

    public void setMenuMalam(String menuMalam) {
        MenuMalam = menuMalam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getKkal() {
        return kkal;
    }

    public void setKkal(int kkal) {
        this.kkal = kkal;
    }
}
