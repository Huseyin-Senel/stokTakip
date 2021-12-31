package com.example.mert.stoktakip.models;

public class BozukUrun {
    private int bozukUrunId;
    private String kullanıcıId;
    private String barkodNo;
    private int adet;
    private String aciklama;
    private String tarih;

    public BozukUrun(){

    }

    public BozukUrun(int bozukUrunId, String kullanıcıId, String barkodNo, int adet, String aciklama, String tarih) {
        this.bozukUrunId = bozukUrunId;
        this.kullanıcıId = kullanıcıId;
        this.barkodNo = barkodNo;
        this.adet = adet;
        this.aciklama = aciklama;
        this.tarih = tarih;
    }

    public int getBozukUrunId() {
        return bozukUrunId;
    }

    public void setBozukUrunId(int bozukUrunId) {
        this.bozukUrunId = bozukUrunId;
    }

    public String getKullanıcıId() {
        return kullanıcıId;
    }

    public void setKullanıcıId(String kullanıcıId) {
        this.kullanıcıId = kullanıcıId;
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
