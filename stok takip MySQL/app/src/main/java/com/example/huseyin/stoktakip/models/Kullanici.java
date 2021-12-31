package com.example.huseyin.stoktakip.models;

/**
 * {@code Kullanici} sınıfı StokTakip veritabanının kullanici tablosunu temsil ediyor
 */

public class Kullanici {
    private String kadi;
    private String sifre;
    private byte[] resim;

    public Kullanici() {
    }

    public Kullanici(String kadi, String sifre, byte[]resim) {
        this.kadi = kadi;
        this.sifre = sifre;
        this.resim = resim;
    }

    public String getKadi() {
        return kadi;
    }

    public void setKadi(String kadi) {
        this.kadi = kadi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public byte[] getResim() {
        return resim;
    }

    public void setResim(byte[] resim) {
        this.resim = resim;
    }
}
