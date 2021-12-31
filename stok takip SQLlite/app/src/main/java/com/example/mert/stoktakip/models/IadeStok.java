package com.example.mert.stoktakip.models;

public class IadeStok {
    private int id;
    private int iadeId;
    private String barkodNo;
    private int adet;
    private float adetAlisFiyati;

    public IadeStok(){}

    public IadeStok(int id, int iadeId, String barkodNo, int adet, float adetAlisFiyati) {
        this.id = id;
        this.iadeId = iadeId;
        this.barkodNo = barkodNo;
        this.adet = adet;
        this.adetAlisFiyati = adetAlisFiyati;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIadeId() {
        return iadeId;
    }

    public void setIadeId(int iadeId) {
        this.iadeId = iadeId;
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

    public float getAdetAlisFiyati() {
        return adetAlisFiyati;
    }

    public void setAdetAlisFiyati(float adetAlisFiyati) {
        this.adetAlisFiyati = adetAlisFiyati;
    }
}
