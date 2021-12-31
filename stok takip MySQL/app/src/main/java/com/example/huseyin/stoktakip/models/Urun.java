package com.example.huseyin.stoktakip.models;

/**
 * {@code Urun} sınıfı StokTakip veritabanının urun tablosunu temsil ediyor
 */

public class Urun {

    private String barkodNo;
    private String ad;
    private byte[] resim;
    private float vergiOrani;
    private float karOrani;

    public Urun() {
    }

    public Urun(String barkodNo, String ad, byte[] resim, float vergiOrani, float karOrani) {
        this.barkodNo = barkodNo;
        this.ad = ad;
        this.resim = resim;
        this.vergiOrani = vergiOrani;
        this.karOrani = karOrani;
    }


    public String getBarkodNo() {
        return barkodNo;
    }

    public void setBarkodNo(String barkodNo) {
        this.barkodNo = barkodNo;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public byte[] getResim(){return resim;}

    public void setResim(byte[] resim){this.resim = resim;}

    public float getVergiOrani() {
        return vergiOrani;
    }

    public void setVergiOrani(float vergiOrani) {
        this.vergiOrani = vergiOrani;
    }

    public float getKarOrani() {
        return karOrani;
    }

    public void setKarOrani(float karOrani) {
        this.karOrani = karOrani;
    }
}
