package com.example.mert.stoktakip.models;

/**
 * {@code UrunIslemi} sınıfı StokTakip veritabanının urun_islemi tablosunu temsil ediyor
 */

public class Satis {
    private int id;
    private int sepetId;
    private String barkodNo;
    private int adet;
    private float satisFiyati;
    private float vergi;
    private float vergiOran;
    private float kargo;
    private float kargo_vergi;
    private float kargo_vergi_oran;
    private float adetAlisFiyati;


    public Satis() {
    }

    public Satis(int id, int sepetId, String barkodNo, int adet, float satisFiyati, float vergi, float vergiOran, float kargo, float kargo_vergi, float kargo_vergi_oran, float adetAlisFiyati) {
        this.id = id;
        this.sepetId = sepetId;
        this.barkodNo = barkodNo;
        this.adet = adet;
        this.satisFiyati = satisFiyati;
        this.vergi = vergi;
        this.vergiOran = vergiOran;
        this.kargo = kargo;
        this.kargo_vergi = kargo_vergi;
        this.kargo_vergi_oran = kargo_vergi_oran;
        this.adetAlisFiyati = adetAlisFiyati;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBarkodNo() {
        return barkodNo;
    }

    public void setBarkodNo(String barkodNo) {
        this.barkodNo = barkodNo;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public float getSatisFiyati() {
        return satisFiyati;
    }

    public void setSatisFiyati(float satisFiyati) {
        this.satisFiyati = satisFiyati;
    }

    public float getVergi() {
        return vergi;
    }

    public void setVergi(float vergi) {
        this.vergi = vergi;
    }

    public float getKargo() {
        return this.kargo;
    }

    public void setKargo(float kargo) {
        this.kargo = kargo;
    }

    public int getSepetId() {
        return sepetId;
    }

    public void setSepetId(int sepetId) {
        this.sepetId = sepetId;
    }

    public float getVergiOran() {
        return vergiOran;
    }

    public void setVergiOran(float vergiOran) {
        this.vergiOran = vergiOran;
    }

    public float getKargo_vergi() {
        return kargo_vergi;
    }

    public void setKargo_vergi(float kargo_vergi) {
        this.kargo_vergi = kargo_vergi;
    }

    public float getKargo_vergi_oran() {
        return kargo_vergi_oran;
    }

    public void setKargo_vergi_oran(float kargo_vergi_oran) {
        this.kargo_vergi_oran = kargo_vergi_oran;
    }

    public float getAdetAlisFiyati() {
        return adetAlisFiyati;
    }

    public void setAdetAlisFiyati(float adetAlisFiyati) {
        this.adetAlisFiyati = adetAlisFiyati;
    }
}
