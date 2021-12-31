package com.example.huseyin.stoktakip.models;

public class Stok {

    private int stokId;
    private String barkodNo;
    private int adet;
    private float ortOdenenFiyat;
    private float  adetOrtOdenenFiyat;

    public Stok (){
    }

    public Stok(int stokId, String barkodNo, int adet, float ortOdenenFiyat, float adetOrtOdenenFiyat) {
        this.stokId = stokId;
        this.barkodNo = barkodNo;
        this.adet = adet;
        this.ortOdenenFiyat = ortOdenenFiyat;
        this.adetOrtOdenenFiyat = adetOrtOdenenFiyat;
    }

    public int getStokId() {
        return stokId;
    }

    public void setStokId(int stokId) {
        this.stokId = stokId;
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

    public float getOrtOdenenFiyat() {
        return ortOdenenFiyat;
    }

    public void setOrtOdenenFiyat(float ortOdenenFiyat) {
        this.ortOdenenFiyat = ortOdenenFiyat;
    }

    public float getAdetOrtOdenenFiyat() {
        return adetOrtOdenenFiyat;
    }

    public void setAdetOrtOdenenFiyat(float adetOrtOdenenFiyat) {
        this.adetOrtOdenenFiyat = adetOrtOdenenFiyat;
    }
}
