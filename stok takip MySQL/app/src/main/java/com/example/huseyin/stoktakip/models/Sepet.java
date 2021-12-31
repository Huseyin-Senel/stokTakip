package com.example.huseyin.stoktakip.models;

public class Sepet {

    private int sepetId;
    private String kid;
    private String islemTuru;
    private int adet;
    private float toplamFiyat;
    private float toplamKargo;
    private float komisyon;
    private String aciklama;
    private String islemTarihi;

    public Sepet(){
    }

    public Sepet(int sepetId, String kid, String islemTuru, float toplamKargo, float komisyon,  int adet, float toplamFiyat, String aciklama, String islemTarihi) {
        this.sepetId = sepetId;
        this.kid = kid;
        this.islemTuru = islemTuru;
        this.toplamKargo = toplamKargo;
        this.komisyon = komisyon;
        this.adet = adet;
        this.aciklama = aciklama;
        this.islemTarihi = islemTarihi;
        this.toplamFiyat = toplamFiyat;
    }

    public Sepet(int sepetId, String islemTuru, String islemTarihi){
        this.sepetId = sepetId;
        this.islemTuru = islemTuru;
        this.islemTarihi = islemTarihi;
    }

    public int getSepetId() {
        return sepetId;
    }

    public void setSepetId(int sepetId) {
        this.sepetId = sepetId;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getIslemTuru() {
        return islemTuru;
    }

    public void setIslemTuru(String islemTuru) {
        this.islemTuru = islemTuru;
    }

    public float getToplamKargo() {
        return toplamKargo;
    }

    public void setToplamKargo(float toplamKargo) {
        this.toplamKargo = toplamKargo;
    }

    public float getKomisyon() {
        return komisyon;
    }

    public void setKomisyon(float komisyon) {
        this.komisyon = komisyon;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public float getToplamFiyat() {
        return toplamFiyat;
    }

    public void setToplamFiyat(float toplamFiyat) {
        this.toplamFiyat = toplamFiyat;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getIslemTarihi() {
        return islemTarihi;
    }

    public void setIslemTarihi(String islemTarihi) {
        this.islemTarihi = islemTarihi;
    }
}
