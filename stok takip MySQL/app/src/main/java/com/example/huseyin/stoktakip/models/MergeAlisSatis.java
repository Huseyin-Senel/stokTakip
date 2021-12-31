package com.example.huseyin.stoktakip.models;

public class MergeAlisSatis {
    private int id;
    private int sepetId;
    private String barkodNo;
    private int adet;
    private float alisSatisFiyati;
    private float vergi;
    private float vergiOran;
    private float kargo;
    private float kargo_vergi;
    private float kargo_vergi_oran;
    private float satisAdetAlisFiyati;

    public MergeAlisSatis(){

    }

    public MergeAlisSatis(Alis alis) {
        this.id = alis.getId();
        this.sepetId = alis.getSepetId();
        this.barkodNo = alis.getBarkodNo();
        this.adet = alis.getAdet();
        this.alisSatisFiyati = alis.getAlisFiyati();
        this.vergi = alis.getVergi();
        this.vergiOran = alis.getVergiOran();
        this.kargo = alis.getKargo();
        this.kargo_vergi = alis.getKargo_vergi();
        this.kargo_vergi_oran = alis.getKargo_vergi_oran();
    }

    public MergeAlisSatis(Satis satis) {
        this.id = satis.getId();
        this.sepetId = satis.getSepetId();
        this.barkodNo = satis.getBarkodNo();
        this.adet = satis.getAdet();
        this.alisSatisFiyati = satis.getSatisFiyati();
        this.vergi = satis.getVergi();
        this.vergiOran = satis.getVergiOran();
        this.kargo = satis.getKargo();
        this.kargo_vergi = satis.getKargo_vergi();
        this.kargo_vergi_oran = satis.getKargo_vergi_oran();
        this.satisAdetAlisFiyati = satis.getAdetAlisFiyati();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSepetId() {
        return sepetId;
    }

    public void setSepetId(int sepetId) {
        this.sepetId = sepetId;
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

    public float getAlisSatisFiyati() {
        return alisSatisFiyati;
    }

    public void setAlisSatisFiyati(float alisSatisFiyati) {
        this.alisSatisFiyati = alisSatisFiyati;
    }

    public float getVergi() {
        return vergi;
    }

    public void setVergi(float vergi) {
        this.vergi = vergi;
    }

    public float getKargo() {
        return kargo;
    }

    public void setKargo(float kargo) {
        this.kargo = kargo;
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

    public float getSatisAdetAlisFiyati() {
        return satisAdetAlisFiyati;
    }

    public void setSatisAdetAlisFiyati(float satisAdetAlisFiyati) {
        this.satisAdetAlisFiyati = satisAdetAlisFiyati;
    }
}
