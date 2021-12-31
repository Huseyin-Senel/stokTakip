package com.example.huseyin.stoktakip.models;

public class Iade {
    private int iadeId;
    private String iadeYonu;
    private String kullanıcıId;
    private int sepetId;
    private int alisSatisId;
    private String barkodNo;
    private int adet;
    private float iadeTutarı;
    private String aciklama;
    private String tarih;

    public Iade(){

    }

    public Iade(int iadeId, String iadeYonu, String kullanıcıId, int sepetId, int alisSatisId, String barkodNo, int adet, float iadeTutarı, String aciklama, String tarih) {
        this.iadeId = iadeId;
        this.iadeYonu = iadeYonu;
        this.kullanıcıId = kullanıcıId;
        this.sepetId = sepetId;
        this.alisSatisId = alisSatisId;
        this.barkodNo = barkodNo;
        this.adet = adet;
        this.iadeTutarı = iadeTutarı;
        this.aciklama = aciklama;
        this.tarih = tarih;
    }

    public int getIadeId() {
        return iadeId;
    }

    public void setIadeId(int iadeId) {
        this.iadeId = iadeId;
    }

    public String getIadeYonu() {
        return iadeYonu;
    }

    public void setIadeYonu(String iadeYonu) {
        this.iadeYonu = iadeYonu;
    }

    public String getKullanıcıId() {
        return kullanıcıId;
    }

    public void setKullanıcıId(String kullanıcıId) {
        this.kullanıcıId = kullanıcıId;
    }

    public int getSepetId() {
        return sepetId;
    }

    public void setSepetId(int sepetId) {
        this.sepetId = sepetId;
    }

    public int getAlisSatisId() {
        return alisSatisId;
    }

    public void setAlisSatisId(int alisSatisId) {
        this.alisSatisId = alisSatisId;
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

    public float getIadeTutarı() {
        return iadeTutarı;
    }

    public void setIadeTutarı(float iadeTutarı) {
        this.iadeTutarı = iadeTutarı;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
