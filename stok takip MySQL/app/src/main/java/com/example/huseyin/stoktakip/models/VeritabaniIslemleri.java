package com.example.huseyin.stoktakip.models;

import android.content.Context;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.utils.ConnectionHelper;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VeritabaniIslemleri {
    private Context context;

    // Veritabanı ismi
    private static final String VERITABANI_ADI = "StokTakip.db";

    // Tablo isimleri
    private static final String TABLO_KULLANICI = "kullanici";
    private static final String TABLO_URUN = "urun";
    private static final String TABLO_STOK = "stok";
    private static final String TABLO_SATIS = "satis";
    private static final String TABLO_ALIS ="alis";
    private static final String TABLO_SEPET = "sepet";
    private static final String TABLO_IADE = "iade";
    private static final String TABLO_BOZUK_URUN = "bozuk_urun";
    private static final String TABLO_IADE_STOK = "iade_stok";


    // kullanici tablosu sütun isimleri
    private static final String SUTUN_KULLANICI_KADI = "kadi";
    private static final String SUTUN_KULLANICI_SIFRE = "sifre";
    private static final String SUTUN_KULLANICI_RESIM = "resim";

    // urun tablosu sütun isimleri
    private static final String SUTUN_URUN_ID = "barkod_no";
    private static final String SUTUN_URUN_AD = "ad";
    private static final String SUTUN_URUN_RESIM = "resim";
    private static final String SUTUN_URUN_VERGI_ORANI = "vergi_yuzde";
    private static final String SUTUN_URUN_KAR_ORANI = "kar_orani";

    // stok tablosu sütun isimleri
    private static final String SUTUN_STOK_ID = "stok_id";
    private static final String SUTUN_STOK_URUN_ID = "barkod_no";
    private static final String SUTUN_STOK_ADET = "adet";
    private static final String SUTUN_STOK_ORT_ODENEN_FIYAT = "ort_odenen_fiyat";
    private static final String SUTUN_STOK_ADET_ORT_ODENEN_FIYAT = "adet_ort_odenen_fiyat";

    // satis tablosu sütun isimleri
    private static final String SUTUN_SATIS_ID = "satis_id";
    private static final String SUTUN_SATIS_SEPET_ID = "sepet_id";
    private static final String SUTUN_SATIS_URUN_ID = "barkod_no";
    private static final String SUTUN_SATIS_ADET = "adet";
    private static final String SUTUN_SATIS_SATIS_FIYATI = "satis_fiyati";
    private static final String SUTUN_SATIS_URUN_VERGISI = "urun_vergisi";
    private static final String SUTUN_SATIS_URUN_VERGI_ORANI = "urun_vergi_orani";
    private static final String SUTUN_SATIS_KARGO = "kargo";
    private static final String SUTUN_SATIS_KARGO_VERGI = "kargo_vergisi";
    private static final String SUTUN_SATIS_KARG0_VERGI_ORANI = "kargo_vergi_orani";
    private static final String SUTUN_SATIS_ADET_ALIS_FIYATI = "adet_alis_fiyati";

    // alis tablosu sütun isimleri
    private static final String SUTUN_ALIS_ID = "alis_id";
    private static final String SUTUN_ALIS_SEPET_ID = "sepet_id";
    private static final String SUTUN_ALIS_URUN_ID = "barkod_no";
    private static final String SUTUN_ALIS_ADET = "adet";
    private static final String SUTUN_ALIS_ALIS_FIYATI = "alis_fiyati";
    private static final String SUTUN_ALIS_URUN_VERGISI = "urun_vergisi";
    private static final String SUTUN_ALIS_URUN_VERGI_ORANI = "urun_vergi_orani";
    private static final String SUTUN_ALIS_KARGO = "kargo";
    private static final String SUTUN_ALIS_KARGO_VERGI = "kargo_vergisi";
    private static final String SUTUN_ALIS_KARG0_VERGI_ORANI = "kargo_vergi_orani";

    // sepet tablosu sütun isimleri
    private static final String SUTUN_SEPET_ID = "sepet_id";
    private static final String SUTUN_SEPET_KULLANICI_ID = "kullanici_id";
    private static final String SUTUN_SEPET_ISLEM_TURU = "sepet_islem_turu";
    private static final String SUTUN_SEPET_ADET = "toplam_urun_adeti";
    private static final String SUTUN_SEPET_FIYAT = "toplam_fiyat";
    private static final String SUTUN_SEPET_TOPLAM_KARGO = "toplam_kargo";
    private static final String SUTUN_SEPET_KOMISYON = "komisyon";
    private static final String SUTUN_SEPET_ACIKLAMA = "aciklama";
    private static final String SUTUN_SEPET_ISLEM_TARIHI = "islem_tarihi";

    // iade tablosu sütun isimleri
    private static final String SUTUN_IADE_ID = "iade_id";
    private static final String SUTUN_IADE_YONU = "iade_yonu";
    private static final String SUTUN_IADE_KULLANICI_ID = "kullanıcı_id";
    private static final String SUTUN_IADE_SEPET_ID = "sepet_id";
    private static final String SUTUN_IADE_ALIS_SATIS_ID = "alis_satis_id";
    private static final String SUTUN_IADE_URUN_ID = "barkod_no";
    private static final String SUTUN_IADE_ADET = "adet";
    private static final String SUTUN_IADE_TUTARI = "iade_tutari";
    private static final String SUTUN_IADE_ACIKLAMA = "aciklama";
    private static final String SUTUN_IADE_TARIHI = "islem_tarihi";

    // bozuk urun tablosu sütun isimleri
    private static final String SUTUN_BOZUK_URUN_ID = "id";
    private static final String SUTUN_BOZUK_URUN_KULLANICI_ID = "kullanıcı_id";
    private static final String SUTUN_BOZUK_URUN_URUN_ID = "urun_id";
    private static final String SUTUN_BOZUK_URUN_ADET = "adet";
    private static final String SUTUN_BOZUK_URUN_ACIKLAMA = "aciklama";
    private static final String SUTUN_BOZUK_URUN_TARIH = "islem_tarih";

    //iade stok sütün isimleri
    private static final String SUTUN_IADE_STOK_ID = "id";
    private static final String SUTUN_IADE_STOK_IADE_ID = "iade_id";
    private static final String SUTUN_IADE_STOK_URUN_ID = "barkod_no";
    private static final String SUTUN_IADE_STOK_ADET = "adet";
    private static final String SUTUN_IADE_STOK_ADET_ALIS_FIYATI = "adet_alis_fiyati";

    //Constructer
    public VeritabaniIslemleri(Context context){
        this.context = context;
    }

    //Yuvarlama metodu
    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     *
     * kullanici tablosuyla ilgili metotlar
     *
     */

    // Yeni kullanıcı ekler
    public long kullaniciEkle(Kullanici kullanici){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.imageQuery("INSERT INTO "+TABLO_KULLANICI+" ("+SUTUN_KULLANICI_SIFRE+","+SUTUN_KULLANICI_RESIM+","+SUTUN_KULLANICI_KADI+") VALUES ('"+kullanici.getSifre()+"', ? ,'"+kullanici.getKadi()+"');",kullanici.getResim());
        return k;
    }

    // Gelen kullanıcıyı siler
    public void kullaniciSil(Kullanici kullanici){
        ConnectionHelper a = new ConnectionHelper();
        a.intQuery("DELETE FROM "+TABLO_KULLANICI+" WHERE "+SUTUN_KULLANICI_KADI+"='"+kullanici.getKadi()+"';");
    }

    // Gelen kullanıcıyı günceller
    public int kullaniciGuncelle(Kullanici kullanici){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.imageQuery("UPDATE "+TABLO_KULLANICI+" SET "+SUTUN_KULLANICI_SIFRE+" = '"+kullanici.getSifre()+"', "+SUTUN_KULLANICI_RESIM+" = ? WHERE "+SUTUN_KULLANICI_KADI+"= '"+kullanici.getKadi()+"';",kullanici.getResim());
        return k;
    }

    // Kullanıcı adının var olup olmadığını kontrol eder
    public boolean kullaniciAdiniKontrolEt(String kadi){
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("select * from "+TABLO_KULLANICI+" where "+SUTUN_KULLANICI_KADI+"='"+kadi+"';");
        return table.size()>0;
    }

    // Kullanıcı adı ve şifrenin doğruluğunu kontrol eder
    public boolean girisBilgileriniKontrolEt(Kullanici kullanici) {

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("select * from "+TABLO_KULLANICI+" where "+SUTUN_KULLANICI_KADI+"='"+kullanici.getKadi()+"' and "+SUTUN_KULLANICI_SIFRE+"="+kullanici.getSifre()+";");
        if (table.size()>0){
            return true;
        }

        return false;
    }

    public Kullanici kullaniciAdinaGoreKullaniciyiGetir(String kadi){
        Kullanici kullanici = new Kullanici();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("select * from "+TABLO_KULLANICI+" where "+SUTUN_KULLANICI_KADI+"='"+kadi+"';");
        kullanici.setResim((byte[])table.get(0).get(SUTUN_KULLANICI_RESIM));
        kullanici.setKadi((String)table.get(0).get(SUTUN_KULLANICI_KADI));
        kullanici.setSifre((String)table.get(0).get(SUTUN_KULLANICI_SIFRE));

        return kullanici;
    }

    public ArrayList<Kullanici> butunKullanicilariGetir(){
        ArrayList<Kullanici> kullanicis = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_KULLANICI+" ;");

        for(int i =0 ; i<table.size();i++){
            Kullanici kullanici = new Kullanici();
            kullanici.setResim((byte[])table.get(i).get(SUTUN_KULLANICI_RESIM));
            kullanici.setKadi((String)table.get(i).get(SUTUN_KULLANICI_KADI));
            kullanici.setSifre((String)table.get(i).get(SUTUN_KULLANICI_SIFRE));
            kullanicis.add(kullanici);
        }
            return kullanicis;
    }

    /**
     *
     * ürün tablosuyla ilgili metotlar
     *
     */

    //yeni ürün ve 0 stok ekler
    public long urunEkle (Urun urun){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.imageQuery("INSERT INTO "+TABLO_URUN+"("+SUTUN_URUN_ID+","+SUTUN_URUN_AD+","+SUTUN_URUN_RESIM+","+SUTUN_URUN_VERGI_ORANI+","+SUTUN_URUN_KAR_ORANI+") VALUES ('"+urun.getBarkodNo()+"','"+urun.getAd()+"',?,"+urun.getVergiOrani()+","+urun.getKarOrani()+");",urun.getResim());
        if(k != -1){
            if(stokOlustur(urun.getBarkodNo()) == -1){
                k = -2;
            }
        }
        return k;
    }

    // Gelen ürünü siler
    public int urunSil(String barkod){
        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("DELETE FROM "+TABLO_URUN+" WHERE "+SUTUN_URUN_ID+" = '"+barkod+"';");
        return k;
    }

    // Gelen ürünü günceller
    public int urunGuncelle(Urun urun){
        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.imageQuery("UPDATE "+TABLO_URUN+" SET " +
                ""+SUTUN_URUN_AD+"='"+urun.getAd()+"', " +
                ""+SUTUN_URUN_RESIM+"=?, " +
                ""+SUTUN_URUN_VERGI_ORANI+"="+urun.getVergiOrani()+", "+
                ""+SUTUN_URUN_KAR_ORANI+"="+urun.getKarOrani()+", "+
                "WHERE "+SUTUN_URUN_ID+" = '"+urun.getBarkodNo()+"';",urun.getResim());
        return k;
    }

    // Bütün ürünleri getirir
    public ArrayList<Urun> butunUrunleriGetir(){
        ArrayList<Urun> urunler = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_URUN+" ;");

        for(int i =0 ; i<table.size();i++){
            Urun urun = new Urun();
            urun.setAd((String) table.get(i).get(SUTUN_URUN_AD));
            urun.setBarkodNo((String)table.get(i).get(SUTUN_URUN_ID));
            urun.setResim((byte[])table.get(i).get(SUTUN_URUN_RESIM));
            urun.setKarOrani((float)table.get(i).get(SUTUN_URUN_KAR_ORANI));
            urun.setVergiOrani((float)table.get(i).get(SUTUN_URUN_VERGI_ORANI));
            urunler.add(urun);

        }
        return urunler;
    }

    // Gelen barkodun veritabanında ekli olup olmadığını kontrol eder
    public boolean urunTekrariKontrolEt(String barkod){
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_URUN+" WHERE "+SUTUN_URUN_ID+" = '"+barkod+"';");

        return table.size() >0;
    }

    // Gelen barkoda göre ürün bilgilerini getirir
    public Urun barkodaGoreUrunGetir(String barkod){
        Urun urun = new Urun();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_URUN+" WHERE "+SUTUN_URUN_ID+" = '"+barkod+"'");
        urun.setBarkodNo((String)table.get(0).get(SUTUN_URUN_ID));
        urun.setAd((String)table.get(0).get(SUTUN_URUN_AD));
        urun.setResim((byte[])table.get(0).get(SUTUN_URUN_RESIM));
        urun.setKarOrani((float)table.get(0).get(SUTUN_URUN_KAR_ORANI));
        urun.setVergiOrani((float)table.get(0).get(SUTUN_URUN_VERGI_ORANI));
        return urun;
    }

    // Gelen isime göre ürün bilgilerini getirir
    public Urun isimeGoreUrunGetir(String isim){
        Urun urun = new Urun();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_URUN+" WHERE "+SUTUN_URUN_AD+" = '"+isim+"'");
        urun.setBarkodNo((String)table.get(0).get(SUTUN_URUN_ID));
        urun.setAd((String)table.get(0).get(SUTUN_URUN_AD));
        urun.setResim((byte[])table.get(0).get(SUTUN_URUN_RESIM));
        urun.setKarOrani((float)table.get(0).get(SUTUN_URUN_KAR_ORANI));
        urun.setVergiOrani((float)table.get(0).get(SUTUN_URUN_VERGI_ORANI));
        return urun;
    }

    /**
     *
     * stok tablosuyla ilgili metotlar
     *
     */
    public long stokOlustur(String barkodNo){

        if(stokTekrariKontrolEt(barkodNo)){
            return -2;
        }
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("INSERT INTO "+TABLO_STOK+"("+SUTUN_STOK_URUN_ID+","+SUTUN_STOK_ADET+","+SUTUN_STOK_ORT_ODENEN_FIYAT+","+SUTUN_STOK_ADET_ORT_ODENEN_FIYAT+") VALUES ('"+barkodNo+"',0,0,0);");

        return k;
    }

    public int stokSil(String barkod){
        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("DELETE FROM "+TABLO_STOK+" WHERE "+SUTUN_STOK_URUN_ID+" = '"+barkod+"';");
        return k;
    }

    public boolean stokTekrariKontrolEt(String barkod){
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_STOK+" WHERE "+SUTUN_STOK_URUN_ID+" = '"+barkod+"';");

        return table.size() >0;
    }

    public int stokArttır(String barkodNo, int adet, float fiyat ){

        Stok stok = stokBul(barkodNo);

        if(stok == null ){
            return -1;
        }

        stok.setAdet(stok.getAdet()+adet);
        stok.setOrtOdenenFiyat(stok.getOrtOdenenFiyat()+fiyat);
        stok.setAdetOrtOdenenFiyat(stok.getOrtOdenenFiyat()/stok.getAdet());

        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("UPDATE "+TABLO_STOK+" SET "+SUTUN_STOK_ADET+"="+stok.getAdet()+", "+SUTUN_STOK_ORT_ODENEN_FIYAT+"="+stok.getOrtOdenenFiyat()+", "+
                SUTUN_STOK_ADET_ORT_ODENEN_FIYAT+"="+stok.getAdetOrtOdenenFiyat()+" WHERE "+SUTUN_STOK_URUN_ID+" = '"+stok.getBarkodNo()+"';");
        return k;
    }

    public int stokAzalt(String barkodNo, int adet){
        Stok stok = stokBul(barkodNo);

        if(stok == null ){
            return -1;
        }

        stok.setAdet(stok.getAdet()-adet);
        if(stok.getAdet() != 0){
            stok.setOrtOdenenFiyat(stok.getOrtOdenenFiyat()-(adet*stok.getAdetOrtOdenenFiyat()));
            stok.setAdetOrtOdenenFiyat(stok.getOrtOdenenFiyat()/stok.getAdet());
        }else{
            stok.setOrtOdenenFiyat(0);
            stok.setAdetOrtOdenenFiyat(stok.getOrtOdenenFiyat());
        }

        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("UPDATE "+TABLO_STOK+" SET " +
                ""+SUTUN_STOK_ADET+"="+stok.getAdet()+", " +
                ""+SUTUN_STOK_ORT_ODENEN_FIYAT+"="+stok.getOrtOdenenFiyat()+", " +
                ""+SUTUN_STOK_ADET_ORT_ODENEN_FIYAT+"="+stok.getAdetOrtOdenenFiyat()+" "+
                "WHERE "+SUTUN_STOK_URUN_ID+" = '"+stok.getBarkodNo()+"';");
        return k;
    }

    public int stokBozukUrunAzalt(String barkodNo, int adet){

        Stok stok = stokBul(barkodNo);

        if(stok == null ){
            return -1;
        }

        stok.setAdet(stok.getAdet()-adet);

        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("UPDATE "+TABLO_STOK+" SET " +
                ""+SUTUN_STOK_ADET+"="+stok.getAdet()+", " +
                "WHERE "+SUTUN_STOK_URUN_ID+" = '"+stok.getBarkodNo()+"';");
        return k;
    }

    public Stok stokBul(String barkodNo){
        Stok stok = new Stok();

        if(!stokTekrariKontrolEt(barkodNo)){
            return stok;
        }

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_STOK+" WHERE "+SUTUN_STOK_URUN_ID+" = '"+barkodNo+"';");
        stok.setStokId((int)table.get(0).get(SUTUN_STOK_ID));
        stok.setBarkodNo((String)table.get(0).get(SUTUN_STOK_URUN_ID));
        stok.setAdet((int)table.get(0).get(SUTUN_STOK_ADET));
        stok.setOrtOdenenFiyat((float)table.get(0).get(SUTUN_STOK_ORT_ODENEN_FIYAT));
        stok.setAdetOrtOdenenFiyat((float)table.get(0).get(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT));
        return stok;
    }

    public ArrayList<Stok> azalanUrunGetir(int esik){
        ArrayList<Stok> stoklar = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_STOK+" WHERE "+SUTUN_STOK_ADET+" <= "+esik+";");

        for(int i =0 ; i<table.size();i++){
            Stok stok = new Stok();
            stok.setStokId((int)table.get(i).get(SUTUN_STOK_ID));
            stok.setBarkodNo((String)table.get(i).get(SUTUN_STOK_URUN_ID));
            stok.setAdet((int)table.get(i).get(SUTUN_STOK_ADET));
            stok.setOrtOdenenFiyat((float)table.get(i).get(SUTUN_STOK_ORT_ODENEN_FIYAT));
            stok.setAdetOrtOdenenFiyat((float)table.get(i).get(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT));
            stoklar.add(stok);

        }
        return  stoklar;
    }

    //bütün stokları getir
    public ArrayList<Stok> butunStoklarıGetir(){
        ArrayList<Stok> stoklar = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_STOK+" ;");

        for(int i =0 ; i<table.size();i++){
            Stok stok = new Stok();
            stok.setStokId((int) table.get(i).get(SUTUN_STOK_ID));
            stok.setBarkodNo((String)table.get(i).get(SUTUN_STOK_URUN_ID));
            stok.setAdet((int)table.get(i).get(SUTUN_STOK_ADET));
            stok.setOrtOdenenFiyat((float)table.get(i).get(SUTUN_STOK_ORT_ODENEN_FIYAT));
            stok.setAdetOrtOdenenFiyat((float)table.get(i).get(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT));
            stoklar.add(stok);

        }

        return stoklar;
    }


    /**
     *
     * sepet tablosuyla ilgili metotlar
     *
     */

    public int SepetOlustur( Sepet sepet){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("INSERT INTO "+TABLO_SEPET+"("+SUTUN_SEPET_KULLANICI_ID+","+SUTUN_SEPET_ISLEM_TURU+","+SUTUN_SEPET_ADET+","+SUTUN_SEPET_FIYAT+","+SUTUN_SEPET_TOPLAM_KARGO+","+SUTUN_SEPET_KOMISYON+","+SUTUN_SEPET_ACIKLAMA+") " +
                "VALUES ('"+sepet.getKid()+"','"+sepet.getIslemTuru()+"',"+sepet.getAdet()+","+sepet.getToplamFiyat()+","+sepet.getToplamKargo()+","+sepet.getKomisyon()+",'"+sepet.getAciklama()+"');");
        if(k == -1){
            return -1;
        }
        List<Map<String, Object>> table = a.tableQuery("SELECT LAST_INSERT_ID();");
        return ((BigInteger)table.get(0).get("LAST_INSERT_ID()")).intValue();
    }

    public long sepetIdyeGoreSepetiSil(int sepetId){
        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("DELETE FROM "+TABLO_SEPET+" WHERE "+SUTUN_SEPET_ID+" = "+sepetId+";");
        return k;
    }

    public Sepet sepetIdyeGoreSepetGetir(int sepetId){
        Sepet sepet = new Sepet();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_SEPET+" WHERE "+SUTUN_SEPET_ID+" = "+sepetId+";");

        sepet.setSepetId((int)table.get(0).get(SUTUN_SEPET_ID));
        sepet.setIslemTuru((String)table.get(0).get(SUTUN_SEPET_ISLEM_TURU));
        //sepet.setIslemTarihi((String)table.get(0).get("datetime("+SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')"));
        sepet.setIslemTarihi(table.get(0).get(SUTUN_SEPET_ISLEM_TARIHI).toString());
        sepet.setAciklama((String)table.get(0).get(SUTUN_SEPET_ACIKLAMA));
        sepet.setAdet((int)table.get(0).get(SUTUN_SEPET_ADET));
        sepet.setKid((String)table.get(0).get(SUTUN_SEPET_KULLANICI_ID));
        sepet.setKomisyon((float)table.get(0).get(SUTUN_SEPET_KOMISYON));
        sepet.setToplamFiyat((float)table.get(0).get(SUTUN_SEPET_FIYAT));
        sepet.setToplamKargo((float)table.get(0).get(SUTUN_SEPET_TOPLAM_KARGO));
        return sepet;
    }

    public  ArrayList<Sepet> kullaniciIdyeGoreSepetleriGetir(String kullaniciId){
        ArrayList<Sepet> sepetler = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SEPET + " WHERE " + SUTUN_SEPET_KULLANICI_ID + " = '" + kullaniciId+"'");

        for(int i =0 ; i<table.size();i++){
            Sepet sepet = new Sepet();
            sepet.setSepetId((int)table.get(i).get(SUTUN_SEPET_ID));
            sepet.setIslemTuru((String)table.get(i).get(SUTUN_SEPET_ISLEM_TURU));
            //sepet.setIslemTarihi((String)table.get(i).get("datetime("+SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')"));
            sepet.setIslemTarihi(table.get(i).get(SUTUN_SEPET_ISLEM_TARIHI).toString());
            sepet.setAciklama((String)table.get(i).get(SUTUN_SEPET_ACIKLAMA));
            sepet.setAdet((int)table.get(i).get(SUTUN_SEPET_ADET));
            sepet.setKid((String)table.get(i).get(SUTUN_SEPET_KULLANICI_ID));
            sepet.setKomisyon((float)table.get(i).get(SUTUN_SEPET_KOMISYON));
            sepet.setToplamFiyat((float)table.get(i).get(SUTUN_SEPET_FIYAT));
            sepet.setToplamKargo((float)table.get(i).get(SUTUN_SEPET_TOPLAM_KARGO));
            sepetler.add(sepet);

        }

        return sepetler;
    }

    // Filtre olmadan bütün işlemleri getirir
    public ArrayList<Sepet> sepetGecmisiFiltrele() {
        // Başlangıç tarihi verilmediği için 1 Ocak 2019 olarak kabul edilir
        String baslangicTarihi = "2019-01-01";
        // Bitiş tarihi verilmediği için o günkü tarih olarak kabul edilir
        String bitisTarihi = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        return sepetleriGetir(baslangicTarihi, bitisTarihi, "%");
    }

    // Sadece başlangıç tarihi bilinmesi durumunda, verilen tarihle şuanki tarih arasda yapılan işlemleri getirir
    public ArrayList<Sepet> sepetGecmisiFiltrele(String baslangicTarihi){
        // Bitiş tarihi verilmediği için o günkü tarih olarak kabul edilir
        String bitisTarihi = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        return sepetleriGetir(baslangicTarihi, bitisTarihi, "%");
    }

    // Başlangıç ve bitiş tarihleri arasındaki işlemleri getirir
    public ArrayList<Sepet> sepetGecmisiFiltrele(String baslangicTarihi, String bitisTarihi){
        return sepetleriGetir(baslangicTarihi, bitisTarihi, "%");
    }

    // Başlangıç ve bitiş tarihleri arasında yapılmış sadece alım ya da satım işlemlerini getirir
    public ArrayList<Sepet> sepetGecmisiFiltrele(String baslangicTarihi, String bitisTarihi, String islemTuru){
        return sepetleriGetir(baslangicTarihi, bitisTarihi, islemTuru);
    }


    // Başlangıç tarihi, bitiş tarihi ve işlem türüne göre alis satis işlemlerini getirir.
    public ArrayList<Sepet> sepetleriGetir(String baslangicTarihi, String bitisTarihi, String islemTuru){
        baslangicTarihi += " 00:00:00";
        bitisTarihi += " 23:59:59";

        ArrayList<Sepet> sepetler = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SEPET + " WHERE " + SUTUN_SEPET_ISLEM_TARIHI + " BETWEEN '" + baslangicTarihi+"' AND '"+bitisTarihi+"' AND "
                +SUTUN_SEPET_ISLEM_TURU+" LIKE '"+islemTuru+"' ORDER BY DATE("+SUTUN_SEPET_ISLEM_TARIHI+") DESC;");

        for(int i =0 ; i<table.size();i++){
            Sepet sepet = new Sepet();
            sepet.setSepetId((int)table.get(i).get(SUTUN_SEPET_ID));
            sepet.setIslemTuru((String)table.get(i).get(SUTUN_SEPET_ISLEM_TURU));
            //sepet.setIslemTarihi((String)table.get(i).get("datetime("+SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')"));
            sepet.setIslemTarihi(table.get(i).get(SUTUN_SEPET_ISLEM_TARIHI).toString());
            sepet.setAciklama((String)table.get(i).get(SUTUN_SEPET_ACIKLAMA));
            sepet.setAdet((int)table.get(i).get(SUTUN_SEPET_ADET));
            sepet.setKid((String)table.get(i).get(SUTUN_SEPET_KULLANICI_ID));
            sepet.setKomisyon((float)table.get(i).get(SUTUN_SEPET_KOMISYON));
            sepet.setToplamFiyat((float)table.get(i).get(SUTUN_SEPET_FIYAT));
            sepet.setToplamKargo((float)table.get(i).get(SUTUN_SEPET_TOPLAM_KARGO));
            sepetler.add(sepet);

        }

        return sepetler;
    }

    public ArrayList<Sepet> butunSepetleriGetir(){
        ArrayList<Sepet> sepetler = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SEPET+";");

        for(int i =0 ; i<table.size();i++){
            Sepet sepet = new Sepet();
            sepet.setSepetId((int)table.get(i).get(SUTUN_SEPET_ID));
            sepet.setIslemTuru((String)table.get(i).get(SUTUN_SEPET_ISLEM_TURU));
            //sepet.setIslemTarihi((String)table.get(0).get("datetime("+SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')"));
            sepet.setIslemTarihi(table.get(i).get(SUTUN_SEPET_ISLEM_TARIHI).toString());
            sepet.setAciklama((String)table.get(i).get(SUTUN_SEPET_ACIKLAMA));
            sepet.setAdet((int)table.get(i).get(SUTUN_SEPET_ADET));
            sepet.setKid((String)table.get(i).get(SUTUN_SEPET_KULLANICI_ID));
            sepet.setKomisyon((float)table.get(i).get(SUTUN_SEPET_KOMISYON));
            sepet.setToplamFiyat((float)table.get(i).get(SUTUN_SEPET_FIYAT));
            sepet.setToplamKargo((float)table.get(i).get(SUTUN_SEPET_TOPLAM_KARGO));
            sepetler.add(sepet);

        }

        return sepetler;
    }

    /**
     *
     * alis satis tablolarıyla ilgili metotlar
     *
     */

    public long alisEkle(Alis alis){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("INSERT INTO "+TABLO_ALIS+"("+SUTUN_ALIS_SEPET_ID+","+SUTUN_ALIS_URUN_ID+","+SUTUN_ALIS_ADET+","+SUTUN_ALIS_ALIS_FIYATI+","
                +SUTUN_ALIS_URUN_VERGISI+","+SUTUN_ALIS_URUN_VERGI_ORANI+","+SUTUN_ALIS_KARGO+","+SUTUN_ALIS_KARGO_VERGI+","+SUTUN_ALIS_KARG0_VERGI_ORANI+")" +
                " VALUES ("+alis.getSepetId()+",'"+alis.getBarkodNo()+"',"+alis.getAdet()+","+alis.getAlisFiyati()+","+alis.getVergi()+","+alis.getVergiOran()+","+alis.getKargo()+","+alis.getKargo_vergi()+","+alis.getKargo_vergi_oran()+");");

        return k;
    }

    public long satisEkle(Satis satis){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("INSERT INTO "+TABLO_SATIS+"("+SUTUN_SATIS_SEPET_ID+","+SUTUN_SATIS_URUN_ID+","+SUTUN_SATIS_ADET+","+SUTUN_SATIS_SATIS_FIYATI+","
                +SUTUN_SATIS_URUN_VERGISI+","+SUTUN_SATIS_URUN_VERGI_ORANI+","+SUTUN_SATIS_KARGO+","+SUTUN_SATIS_KARGO_VERGI+","+SUTUN_SATIS_KARG0_VERGI_ORANI+","+SUTUN_SATIS_ADET_ALIS_FIYATI+")" +
                " VALUES ("+satis.getSepetId()+",'"+satis.getBarkodNo()+"',"+satis.getAdet()+","+satis.getSatisFiyati()+","+satis.getVergi()+","+satis.getVergiOran()+","+satis.getKargo()+","
                +satis.getKargo_vergi()+","+satis.getKargo_vergi_oran()+","+satis.getAdetAlisFiyati()+");");

        return k;
    }

    public int sepetIdyeGoreAlıslariSil(int sepetId){
        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("DELETE FROM "+TABLO_ALIS+" WHERE "+SUTUN_ALIS_SEPET_ID+" = "+sepetId+";");
        return k;
    }

    public int sepetIdyeGoreSatislariSil(int sepetId){
        ConnectionHelper a = new ConnectionHelper();
        int k = -1;
        k = a.intQuery("DELETE FROM "+TABLO_SATIS+" WHERE "+SUTUN_SATIS_SEPET_ID+" = "+sepetId+";");
        return k;
    }

    // Filtre olmadan bütün işlemleri getirir
    public ArrayList<MergeAlisSatis> alisSatisGecmisiFiltrele() {
        // Başlangıç tarihi verilmediği için 1 Ocak 2019 olarak kabul edilir
        String baslangicTarihi = "2019-01-01";
        // Bitiş tarihi verilmediği için o günkü tarih olarak kabul edilir
        String bitisTarihi = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        return alisSatisUrunleriniGetir(baslangicTarihi, bitisTarihi, "%");
    }

    // Sadece başlangıç tarihi bilinmesi durumunda, verilen tarihle şuanki tarih arasda yapılan işlemleri getirir
    public ArrayList<MergeAlisSatis> alisSatisGecmisiFiltrele(String baslangicTarihi){
        // Bitiş tarihi verilmediği için o günkü tarih olarak kabul edilir
        String bitisTarihi = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Calendar.getInstance().getTime());
        return alisSatisUrunleriniGetir(baslangicTarihi, bitisTarihi, "%");
    }

    // Başlangıç ve bitiş tarihleri arasındaki işlemleri getirir
    public ArrayList<MergeAlisSatis> alisSatisGecmisiFiltrele(String baslangicTarihi, String bitisTarihi){
        return alisSatisUrunleriniGetir(baslangicTarihi, bitisTarihi, "%");
    }

    // Başlangıç ve bitiş tarihleri arasında yapılmış sadece alım ya da satım işlemlerini getirir
    public ArrayList<MergeAlisSatis> alisSatisGecmisiFiltrele(String baslangicTarihi, String bitisTarihi, String islemTuru){
        return alisSatisUrunleriniGetir(baslangicTarihi, bitisTarihi, islemTuru);
    }


    // Başlangıç tarihi, bitiş tarihi ve işlem türüne göre alis satis işlemlerini getirir.
    public ArrayList<MergeAlisSatis> alisSatisUrunleriniGetir(String baslangicTarihi, String bitisTarihi, String islemTuru){
        baslangicTarihi += " 00:00:00";
        bitisTarihi += " 23:59:59";
        ArrayList<MergeAlisSatis> islemler = new ArrayList<>();
        ArrayList<Sepet> sepetler = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SEPET + " WHERE " + SUTUN_SEPET_ISLEM_TARIHI + " BETWEEN '" + baslangicTarihi+"' AND '"+bitisTarihi+"' AND "
                +SUTUN_SEPET_ISLEM_TURU+" LIKE '"+islemTuru+"' ORDER BY DATE("+SUTUN_SEPET_ISLEM_TARIHI+") DESC;");

        for(int i =0 ; i<table.size();i++){
            Sepet sepet = new Sepet();
            sepet.setSepetId((int)table.get(i).get(SUTUN_SEPET_ID));
            sepet.setIslemTuru((String)table.get(i).get(SUTUN_SEPET_ISLEM_TURU));
            //sepet.setIslemTarihi((String)table.get(i).get("datetime("+SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')"));
            sepet.setIslemTarihi(table.get(i).get(SUTUN_SEPET_ISLEM_TARIHI).toString());
            sepet.setAciklama((String)table.get(i).get(SUTUN_SEPET_ACIKLAMA));
            sepet.setAdet((int)table.get(i).get(SUTUN_SEPET_ADET));
            sepet.setKid((String)table.get(i).get(SUTUN_SEPET_KULLANICI_ID));
            sepet.setKomisyon((float)table.get(i).get(SUTUN_SEPET_KOMISYON));
            sepet.setToplamFiyat((float)table.get(i).get(SUTUN_SEPET_FIYAT));
            sepet.setToplamKargo((float)table.get(i).get(SUTUN_SEPET_TOPLAM_KARGO));
            sepetler.add(sepet);

        }


        for(int i = 0; i < sepetler.size(); i++) {
            for(int k = 0; k < sepetIdyeGoreAlislariGetir(sepetler.get(i).getSepetId()).size(); k++) {
                MergeAlisSatis alis = new MergeAlisSatis(sepetIdyeGoreAlislariGetir(sepetler.get(i).getSepetId()).get(k));
                islemler.add(alis);
            }
        }

        for(int i = 0; i < sepetler.size(); i++) {
            for(int k = 0; k < sepetIdyeGoreSatislariGetir(sepetler.get(i).getSepetId()).size(); k++) {
                MergeAlisSatis satis = new MergeAlisSatis(sepetIdyeGoreSatislariGetir(sepetler.get(i).getSepetId()).get(k));
                islemler.add(satis);
            }
        }

        return islemler;
    }


    public ArrayList<Alis> sepetIdyeGoreAlislariGetir(int sepetId){
        ArrayList<Alis> alislar = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_ALIS + " WHERE " + SUTUN_ALIS_SEPET_ID + " = " + sepetId+";");

        for(int i =0 ; i<table.size();i++){
            Alis alis = new Alis();

            alis.setId((int)table.get(i).get(SUTUN_ALIS_ID));
            alis.setSepetId((int)table.get(i).get(SUTUN_ALIS_SEPET_ID));
            alis.setBarkodNo((String)table.get(i).get(SUTUN_ALIS_URUN_ID));
            alis.setAdet((int)table.get(i).get(SUTUN_ALIS_ADET));
            alis.setAlisFiyati((float)table.get(i).get(SUTUN_ALIS_ALIS_FIYATI));
            alis.setKargo((float)table.get(i).get(SUTUN_ALIS_KARGO));
            alis.setKargo_vergi((float)table.get(i).get(SUTUN_ALIS_KARGO_VERGI));
            alis.setKargo_vergi_oran((float)table.get(i).get(SUTUN_ALIS_KARG0_VERGI_ORANI));
            alis.setVergi((float)table.get(i).get(SUTUN_ALIS_URUN_VERGISI));
            alis.setVergiOran((float)table.get(i).get(SUTUN_ALIS_URUN_VERGI_ORANI));
            alislar.add(alis);

        }
        return  alislar;
    }

    public ArrayList<Satis> sepetIdyeGoreSatislariGetir(int sepetId){
        ArrayList<Satis> satislar = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SATIS + " WHERE " + SUTUN_SATIS_SEPET_ID + " = " + sepetId+";");

        for(int i =0 ; i<table.size();i++){
            Satis satis = new Satis();

            satis.setId((int)table.get(i).get(SUTUN_SATIS_ID));
            satis.setSepetId((int)table.get(i).get(SUTUN_SATIS_SEPET_ID));
            satis.setBarkodNo((String)table.get(i).get(SUTUN_SATIS_URUN_ID));
            satis.setAdet((int)table.get(i).get(SUTUN_SATIS_ADET));
            satis.setSatisFiyati((float)table.get(i).get(SUTUN_SATIS_SATIS_FIYATI));
            satis.setKargo((float)table.get(i).get(SUTUN_SATIS_KARGO));
            satis.setKargo_vergi((float)table.get(i).get(SUTUN_SATIS_KARGO_VERGI));
            satis.setKargo_vergi_oran((float)table.get(i).get(SUTUN_SATIS_KARG0_VERGI_ORANI));
            satis.setVergi((float)table.get(i).get(SUTUN_SATIS_URUN_VERGISI));
            satis.setVergiOran((float)table.get(i).get(SUTUN_SATIS_URUN_VERGI_ORANI));
            satis.setAdetAlisFiyati((float)table.get(i).get(SUTUN_SATIS_ADET_ALIS_FIYATI));
            satislar.add(satis);

        }
        return  satislar;
    }

    public MergeAlisSatis sepetIdBarkodNoyaGoreAlısSatısıGetir(int sepetId, String barkodNo){
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SATIS + " WHERE " + SUTUN_SATIS_SEPET_ID + " = " + sepetId + " AND " + SUTUN_SATIS_URUN_ID + " = " + barkodNo+";");
        MergeAlisSatis alısSatis = new MergeAlisSatis();
        if(table.size()>0){
            alısSatis.setId((int)table.get(0).get(SUTUN_SATIS_ID));
            alısSatis.setSepetId((int)table.get(0).get(SUTUN_SATIS_SEPET_ID));
            alısSatis.setBarkodNo((String)table.get(0).get(SUTUN_SATIS_URUN_ID));
            alısSatis.setAdet((int)table.get(0).get(SUTUN_SATIS_ADET));
            alısSatis.setAlisSatisFiyati((float)table.get(0).get(SUTUN_SATIS_SATIS_FIYATI));
            alısSatis.setKargo((float)table.get(0).get(SUTUN_SATIS_KARGO));
            alısSatis.setKargo_vergi((float)table.get(0).get(SUTUN_SATIS_KARGO_VERGI));
            alısSatis.setKargo_vergi_oran((float)table.get(0).get(SUTUN_SATIS_KARG0_VERGI_ORANI));
            alısSatis.setVergi((float)table.get(0).get(SUTUN_SATIS_URUN_VERGISI));
            alısSatis.setVergiOran((float)table.get(0).get(SUTUN_SATIS_URUN_VERGI_ORANI));
            alısSatis.setSatisAdetAlisFiyati((float)table.get(0).get(SUTUN_SATIS_ADET_ALIS_FIYATI));
        }else{
            table.clear();
            table = a.tableQuery("SELECT * FROM " + TABLO_ALIS + " WHERE " + SUTUN_ALIS_SEPET_ID + " = " + sepetId + " AND " + SUTUN_ALIS_URUN_ID + " = " + barkodNo+";");
            alısSatis.setId((int)table.get(0).get(SUTUN_ALIS_ID));
            alısSatis.setSepetId((int)table.get(0).get(SUTUN_ALIS_SEPET_ID));
            alısSatis.setBarkodNo((String)table.get(0).get(SUTUN_ALIS_URUN_ID));
            alısSatis.setAdet((int)table.get(0).get(SUTUN_ALIS_ADET));
            alısSatis.setAlisSatisFiyati((float)table.get(0).get(SUTUN_ALIS_ALIS_FIYATI));
            alısSatis.setKargo((float)table.get(0).get(SUTUN_ALIS_KARGO));
            alısSatis.setKargo_vergi((float)table.get(0).get(SUTUN_ALIS_KARGO_VERGI));
            alısSatis.setKargo_vergi_oran((float)table.get(0).get(SUTUN_ALIS_KARG0_VERGI_ORANI));
            alısSatis.setVergi((float)table.get(0).get(SUTUN_ALIS_URUN_VERGISI));
            alısSatis.setVergiOran((float)table.get(0).get(SUTUN_ALIS_URUN_VERGI_ORANI));
        }

        return alısSatis;
    }

    public ArrayList<MergeAlisSatis> sepetIdyeGoreAlislariSatislariGetir(int sepetId){
        ArrayList<MergeAlisSatis> alısSatislar = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SATIS + " WHERE " + SUTUN_SATIS_SEPET_ID + " = " + sepetId+";");
        for(int i =0 ; i<table.size();i++){
            MergeAlisSatis alısSatis = new MergeAlisSatis();

            alısSatis.setId((int)table.get(i).get(SUTUN_SATIS_ID));
            alısSatis.setSepetId((int)table.get(i).get(SUTUN_SATIS_SEPET_ID));
            alısSatis.setBarkodNo((String)table.get(i).get(SUTUN_SATIS_URUN_ID));
            alısSatis.setAdet((int)table.get(i).get(SUTUN_SATIS_ADET));
            alısSatis.setAlisSatisFiyati((float)table.get(i).get(SUTUN_SATIS_SATIS_FIYATI));
            alısSatis.setKargo((float)table.get(i).get(SUTUN_SATIS_KARGO));
            alısSatis.setKargo_vergi((float)table.get(i).get(SUTUN_SATIS_KARGO_VERGI));
            alısSatis.setKargo_vergi_oran((float)table.get(i).get(SUTUN_SATIS_KARG0_VERGI_ORANI));
            alısSatis.setVergi((float)table.get(i).get(SUTUN_SATIS_URUN_VERGISI));
            alısSatis.setVergiOran((float)table.get(i).get(SUTUN_SATIS_URUN_VERGI_ORANI));
            alısSatis.setSatisAdetAlisFiyati((float)table.get(i).get(SUTUN_SATIS_ADET_ALIS_FIYATI));
            alısSatislar.add(alısSatis);

        }
        table.clear();
        table = a.tableQuery("SELECT * FROM " + TABLO_ALIS + " WHERE " + SUTUN_ALIS_SEPET_ID + " = " + sepetId+";");
        for(int i =0 ; i<table.size();i++){
            MergeAlisSatis alısSatis = new MergeAlisSatis();

            alısSatis.setId((int)table.get(i).get(SUTUN_ALIS_ID));
            alısSatis.setSepetId((int)table.get(i).get(SUTUN_ALIS_SEPET_ID));
            alısSatis.setBarkodNo((String)table.get(i).get(SUTUN_ALIS_URUN_ID));
            alısSatis.setAdet((int)table.get(i).get(SUTUN_ALIS_ADET));
            alısSatis.setAlisSatisFiyati((float)table.get(i).get(SUTUN_ALIS_ALIS_FIYATI));
            alısSatis.setKargo((float)table.get(i).get(SUTUN_ALIS_KARGO));
            alısSatis.setKargo_vergi((float)table.get(i).get(SUTUN_ALIS_KARGO_VERGI));
            alısSatis.setKargo_vergi_oran((float)table.get(i).get(SUTUN_ALIS_KARG0_VERGI_ORANI));
            alısSatis.setVergi((float)table.get(i).get(SUTUN_ALIS_URUN_VERGISI));
            alısSatis.setVergiOran((float)table.get(i).get(SUTUN_ALIS_URUN_VERGI_ORANI));
            alısSatislar.add(alısSatis);

        }

        return alısSatislar;
    }

    public ArrayList<Alis> butunAlislariGetir(){
        ArrayList<Alis> alislar = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_ALIS+";");
        for(int i =0 ; i<table.size();i++){
            Alis alis = new Alis();

            alis.setId((int)table.get(i).get(SUTUN_ALIS_ID));
            alis.setSepetId((int)table.get(i).get(SUTUN_ALIS_SEPET_ID));
            alis.setBarkodNo((String)table.get(i).get(SUTUN_ALIS_URUN_ID));
            alis.setAdet((int)table.get(i).get(SUTUN_ALIS_ADET));
            alis.setAlisFiyati((float)table.get(i).get(SUTUN_ALIS_ALIS_FIYATI));
            alis.setKargo((float)table.get(i).get(SUTUN_ALIS_KARGO));
            alis.setKargo_vergi((float)table.get(i).get(SUTUN_ALIS_KARGO_VERGI));
            alis.setKargo_vergi_oran((float)table.get(i).get(SUTUN_ALIS_KARG0_VERGI_ORANI));
            alis.setVergi((float)table.get(i).get(SUTUN_ALIS_URUN_VERGISI));
            alis.setVergiOran((float)table.get(i).get(SUTUN_ALIS_URUN_VERGI_ORANI));
            alislar.add(alis);

        }
        return alislar;
    }

    public ArrayList<Satis> butunSatislariGetir(){

        ArrayList<Satis> satislar = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_SATIS+";");

        for(int i =0 ; i<table.size();i++){
            Satis satis = new Satis();

            satis.setId((int)table.get(i).get(SUTUN_SATIS_ID));
            satis.setSepetId((int)table.get(i).get(SUTUN_SATIS_SEPET_ID));
            satis.setBarkodNo((String)table.get(i).get(SUTUN_SATIS_URUN_ID));
            satis.setAdet((int)table.get(i).get(SUTUN_SATIS_ADET));
            satis.setSatisFiyati((float)table.get(i).get(SUTUN_SATIS_SATIS_FIYATI));
            satis.setKargo((float)table.get(i).get(SUTUN_SATIS_KARGO));
            satis.setKargo_vergi((float)table.get(i).get(SUTUN_SATIS_KARGO_VERGI));
            satis.setKargo_vergi_oran((float)table.get(i).get(SUTUN_SATIS_KARG0_VERGI_ORANI));
            satis.setVergi((float)table.get(i).get(SUTUN_SATIS_URUN_VERGISI));
            satis.setVergiOran((float)table.get(i).get(SUTUN_SATIS_URUN_VERGI_ORANI));
            satis.setAdetAlisFiyati((float)table.get(i).get(SUTUN_SATIS_ADET_ALIS_FIYATI));
            satislar.add(satis);

        }
        return  satislar;
    }


    /**
     *
     * bozuk ürün tablosuyla ilgili metotlar
     *
     */
    public int bozukUrunEkle(BozukUrun bozukUrun){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("INSERT INTO "+TABLO_BOZUK_URUN+"("+SUTUN_BOZUK_URUN_KULLANICI_ID+","+SUTUN_BOZUK_URUN_URUN_ID+","+SUTUN_BOZUK_URUN_ADET+","+SUTUN_BOZUK_URUN_ACIKLAMA+") " +
                "VALUES ('"+bozukUrun.getKullanıcıId()+"','"+bozukUrun.getBarkodNo()+"',"+bozukUrun.getAdet()+",'"+bozukUrun.getAciklama()+"');");
        if(k == -1){
            return -1;
        }
        List<Map<String, Object>> table = a.tableQuery("SELECT LAST_INSERT_ID();");
        return ((BigInteger)table.get(0).get("LAST_INSERT_ID()")).intValue();
    }

    public int bozukUrunIdyeGoreBozukUrunSil(int bozukUrunId){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("DELETE FROM "+TABLO_BOZUK_URUN+" WHERE "+SUTUN_BOZUK_URUN_ID+" = "+bozukUrunId+";");
        return k;
    }

    public ArrayList<BozukUrun> butunBozukUrunleriGetir(){
        ArrayList<BozukUrun> urunler = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_BOZUK_URUN+";");
        for(int i =0 ; i<table.size();i++){
            BozukUrun bozukUrun = new BozukUrun();

            bozukUrun.setBozukUrunId((int)table.get(i).get(SUTUN_BOZUK_URUN_ID));
            bozukUrun.setBarkodNo((String)table.get(i).get(SUTUN_BOZUK_URUN_URUN_ID));
            bozukUrun.setKullanıcıId((String)table.get(i).get(SUTUN_BOZUK_URUN_KULLANICI_ID));
            bozukUrun.setAdet((int)table.get(i).get(SUTUN_BOZUK_URUN_ADET));
            bozukUrun.setAciklama((String)table.get(i).get(SUTUN_BOZUK_URUN_ACIKLAMA));
            bozukUrun.setTarih(table.get(i).get(SUTUN_BOZUK_URUN_TARIH).toString());

            urunler.add(bozukUrun);
        }
        return urunler;
    }

    /**
     *
     * iade tablosuyla ilgili metotlar
     *
     */

    public int iadeEkle(Iade iade){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("INSERT INTO "+TABLO_IADE+"("+SUTUN_IADE_YONU+","+SUTUN_IADE_KULLANICI_ID+","+SUTUN_IADE_SEPET_ID+","+SUTUN_IADE_ALIS_SATIS_ID+","+SUTUN_IADE_URUN_ID+","+SUTUN_IADE_ADET+","+SUTUN_IADE_TUTARI+","+SUTUN_IADE_ACIKLAMA+") " +
                "VALUES ('"+iade.getIadeYonu()+"','"+iade.getKullanıcıId()+"',"+iade.getSepetId()+","+iade.getAlisSatisId()+",'"+iade.getBarkodNo()+"',"+iade.getAdet()+","+iade.getIadeTutarı()+",'"+iade.getAciklama()+"');");
        if(k == -1){
            return -1;
        }
        List<Map<String, Object>> table = a.tableQuery("SELECT LAST_INSERT_ID();");
        return ((BigInteger)table.get(0).get("LAST_INSERT_ID()")).intValue();
    }

    public ArrayList<Iade> butunIadeleriGetir(){
        ArrayList<Iade> iadeler = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_IADE+";");
        for(int i =0 ; i<table.size();i++){
            Iade iade = new Iade();

            iade.setIadeId((int)table.get(i).get(SUTUN_IADE_ID));
            iade.setIadeYonu((String) table.get(i).get(SUTUN_IADE_YONU));
            iade.setKullanıcıId((String) table.get(i).get(SUTUN_IADE_KULLANICI_ID));
            iade.setSepetId((int)table.get(i).get(SUTUN_IADE_SEPET_ID));
            iade.setAlisSatisId((int)table.get(i).get(SUTUN_IADE_ALIS_SATIS_ID));
            iade.setBarkodNo((String)table.get(i).get(SUTUN_IADE_URUN_ID));
            iade.setAdet((int)table.get(i).get(SUTUN_IADE_ADET));
            iade.setIadeTutarı((float)table.get(i).get(SUTUN_IADE_TUTARI));
            iade.setAciklama((String)table.get(i).get(SUTUN_IADE_ACIKLAMA));
            iade.setTarih(table.get(i).get(SUTUN_IADE_TARIHI).toString());

            iadeler.add(iade);
        }
        return iadeler;
    }

    public int iadeIdyeGoreIadeyiSil(int iadeId){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("DELETE FROM "+TABLO_IADE+" WHERE "+SUTUN_IADE_ID+" = "+iadeId+";");
        return k;
    }

    public ArrayList<Iade> iadeleriSepetIdIslemIdyeGoreGetir(int sepetId, int alisSatisId){
        ArrayList<Iade> iadeler = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_IADE + " WHERE " + SUTUN_IADE_SEPET_ID + " = " + sepetId + " AND " + SUTUN_IADE_ALIS_SATIS_ID + " = " + alisSatisId+";");
        for(int i =0 ; i<table.size();i++){
            Iade iade = new Iade();

            iade.setIadeId((int)table.get(i).get(SUTUN_IADE_ID));
            iade.setIadeYonu((String) table.get(i).get(SUTUN_IADE_YONU));
            iade.setKullanıcıId((String) table.get(i).get(SUTUN_IADE_KULLANICI_ID));
            iade.setSepetId((int)table.get(i).get(SUTUN_IADE_SEPET_ID));
            iade.setAlisSatisId((int)table.get(i).get(SUTUN_IADE_ALIS_SATIS_ID));
            iade.setBarkodNo((String)table.get(i).get(SUTUN_IADE_URUN_ID));
            iade.setAdet((int)table.get(i).get(SUTUN_IADE_ADET));
            iade.setIadeTutarı((float)table.get(i).get(SUTUN_IADE_TUTARI));
            iade.setAciklama((String)table.get(i).get(SUTUN_IADE_ACIKLAMA));
            iade.setTarih(table.get(i).get(SUTUN_IADE_TARIHI).toString());

            iadeler.add(iade);
        }
        return iadeler;
    }

    public ArrayList<Iade> iadeleriKullaniciIdyeGoreGetir(String kullaniciId){
        ArrayList<Iade> iadeler = new ArrayList<>();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_IADE + " WHERE " + SUTUN_IADE_KULLANICI_ID + " = '" + kullaniciId +"';");
        for(int i =0 ; i<table.size();i++){
            Iade iade = new Iade();

            iade.setIadeId((int)table.get(i).get(SUTUN_IADE_ID));
            iade.setIadeYonu((String) table.get(i).get(SUTUN_IADE_YONU));
            iade.setKullanıcıId((String) table.get(i).get(SUTUN_IADE_KULLANICI_ID));
            iade.setSepetId((int)table.get(i).get(SUTUN_IADE_SEPET_ID));
            iade.setAlisSatisId((int)table.get(i).get(SUTUN_IADE_ALIS_SATIS_ID));
            iade.setBarkodNo((String)table.get(i).get(SUTUN_IADE_URUN_ID));
            iade.setAdet((int)table.get(i).get(SUTUN_IADE_ADET));
            iade.setIadeTutarı((float)table.get(i).get(SUTUN_IADE_TUTARI));
            iade.setAciklama((String)table.get(i).get(SUTUN_IADE_ACIKLAMA));
            iade.setTarih(table.get(i).get(SUTUN_IADE_TARIHI).toString());

            iadeler.add(iade);
        }
        return iadeler;
    }

    public int iadeAdetiniGetir(int sepetId, int alisSatisId){

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT SUM("+SUTUN_IADE_ADET+") AS Toplam FROM " + TABLO_IADE + " WHERE " + SUTUN_IADE_SEPET_ID + " = " + sepetId + " AND " + SUTUN_IADE_ALIS_SATIS_ID + " = " + alisSatisId+";");
        int adet =0;
        adet=(int)table.get(0).get("Toplam");
        return adet;

    }

    /**
     *
     * iade_sok tablosuyla ilgili metotlar
     *
     */

    public long iadeStokOlustur(IadeStok iadeStok){
        ConnectionHelper a = new ConnectionHelper();
        int k =-1;
        k = a.intQuery("INSERT INTO "+TABLO_IADE_STOK+"("+SUTUN_IADE_STOK_URUN_ID+","+SUTUN_IADE_STOK_ADET+","+SUTUN_IADE_STOK_IADE_ID+","+SUTUN_IADE_STOK_ADET_ALIS_FIYATI+")" +
                " VALUES ('"+iadeStok.getBarkodNo()+"',"+iadeStok.getAdet()+","+iadeStok.getIadeId()+","+iadeStok.getAdetAlisFiyati()+");");
        return k;
    }

    public int iadeStokAzalt(int IadeStokId,int adet){
        IadeStok iadeStok = iadeStokBul(IadeStokId);

        if(iadeStok == null ){
            return -1;
        }

        iadeStok.setAdet(iadeStok.getAdet()-adet);
        int k = -1;
        ConnectionHelper a = new ConnectionHelper();

        if(iadeStok.getAdet()>0){
            k = a.intQuery("UPDATE "+TABLO_IADE_STOK+" SET " +SUTUN_IADE_STOK_ADET+"="+iadeStok.getAdet()+"  WHERE "+SUTUN_IADE_STOK_ID+" = '"+iadeStok.getId()+"';");
            return k;
        }else{
            k = a.intQuery("DELETE FROM "+TABLO_IADE_STOK+" WHERE "+SUTUN_IADE_STOK_ID+" = "+IadeStokId+";");
        }

        return k;
    }

    public int iadeStokArttır(int IadeStokId,int adet){
        IadeStok iadeStok = iadeStokBul(IadeStokId);

        if(iadeStok == null ){
            return -1;
        }

        iadeStok.setAdet(iadeStok.getAdet()+adet);
        int k = -1;
        ConnectionHelper a = new ConnectionHelper();

        if(iadeStok.getAdet()>0){
            k = a.intQuery("UPDATE "+TABLO_IADE_STOK+" SET " +SUTUN_IADE_STOK_ADET+"="+iadeStok.getAdet()+"  WHERE "+SUTUN_IADE_STOK_ID+" = '"+iadeStok.getId()+"';");
            return k;
        }else{
            k = a.intQuery("DELETE FROM "+TABLO_IADE_STOK+" WHERE "+SUTUN_IADE_STOK_ID+" = "+IadeStokId+";");
        }

        return k;
    }

    public IadeStok iadeStokBul(int IadeStokId){
        IadeStok stok = new IadeStok();

        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM "+TABLO_IADE_STOK+" WHERE "+SUTUN_IADE_STOK_ID+" = "+IadeStokId+";");

        stok.setId((int)table.get(0).get(SUTUN_IADE_STOK_ID));
        stok.setIadeId((int)table.get(0).get(SUTUN_IADE_STOK_IADE_ID));
        stok.setBarkodNo((String) table.get(0).get(SUTUN_IADE_STOK_URUN_ID));
        stok.setAdet((int)table.get(0).get(SUTUN_IADE_STOK_ADET));
        stok.setAdetAlisFiyati((float)table.get(0).get(SUTUN_IADE_STOK_ADET_ALIS_FIYATI));
        return stok;
    }

    public ArrayList<IadeStok> butunIadeStoklarıGetir(){
        ArrayList<IadeStok> stoklar = new ArrayList<>();
        ConnectionHelper a = new ConnectionHelper();
        List<Map<String, Object>> table = a.tableQuery("SELECT * FROM " + TABLO_IADE_STOK+";");

        for(int i =0 ; i<table.size();i++){
            IadeStok stok = new IadeStok();
            stok.setId((int)table.get(i).get(SUTUN_IADE_STOK_ID));
            stok.setIadeId((int)table.get(i).get(SUTUN_IADE_STOK_IADE_ID));
            stok.setBarkodNo((String) table.get(i).get(SUTUN_IADE_STOK_URUN_ID));
            stok.setAdet((int)table.get(i).get(SUTUN_IADE_STOK_ADET));
            stok.setAdetAlisFiyati((float)table.get(i).get(SUTUN_IADE_STOK_ADET_ALIS_FIYATI));
            stoklar.add(stok);

        }
        return stoklar;
    }

    /**
     *
     * istatistikler ile ilgili metotlar
     *
     */

    public float toplamVergiGetir(){
        ArrayList<Alis> alislar = butunAlislariGetir();
        ArrayList<Satis> satislar = butunSatislariGetir();
        ArrayList<Iade> iadeler = butunIadeleriGetir();


        float alınanKargoVergisi = 0;
        float odenenKargoVergisi = 0;
        float satisVergisi =0;
        float alisVergisi =0;
        for (Satis i :satislar){

            satisVergisi += i.getVergi();
            alınanKargoVergisi +=i.getKargo_vergi();
        }
        for (Alis i :alislar){
            alisVergisi += i.getVergi();
            odenenKargoVergisi += i.getKargo_vergi();
        }

        //iadelerin vergilerini çıkar
        for(Iade i: iadeler){
            MergeAlisSatis alisSatis = sepetIdBarkodNoyaGoreAlısSatısıGetir(i.getSepetId(),i.getBarkodNo());
            int adet = alisSatis.getAdet();
            int iadet = i.getAdet();
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                satisVergisi -= ((alisSatis.getVergi())/adet)*iadet;
            }else{
                alisVergisi -= ((alisSatis.getVergi())/adet)*iadet;
            }
        }

        alınanKargoVergisi = round(alınanKargoVergisi,2);
        odenenKargoVergisi = round(odenenKargoVergisi,2);
        satisVergisi = round(satisVergisi,2);
        alisVergisi = round(alisVergisi,2);

        return (alınanKargoVergisi+satisVergisi)-(odenenKargoVergisi+alisVergisi);
    }

    public float giderler(){
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        ArrayList<Satis> satislar = butunSatislariGetir();
        ArrayList<Alis> alimlar = butunAlislariGetir();
        ArrayList<Sepet> sepetler =butunSepetleriGetir();

        List<PieEntry> gelirlerPieEntry = new ArrayList<>();


        float iade = 0;
        float kargo = 0;
        float alim = 0;
        float komisyon =0;
        for (Satis i :satislar){
            kargo += i.getKargo();
        }
        for (Alis i :alimlar){
            alim += i.getAlisFiyati();
            kargo += i.getKargo();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                iade += i.getIadeTutarı();
            }
        }
        for(Sepet i : sepetler){
            komisyon += i.getKomisyon();
        }


        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        alim = round(alim,2);
        iade = round(iade,2);

        return (komisyon+kargo+alim+iade);
    }

    public float gelirler(){
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        ArrayList<Satis> satislar = butunSatislariGetir();
        ArrayList<Stok> stoklar = butunStoklarıGetir();
        ArrayList<Sepet> sepetler = butunSepetleriGetir();

        List<PieEntry> gelirlerPieEntry = new ArrayList<>();


        float iade = 0;
        float satim = 0;
        float stokDegeri =0;
        float kargo = 0;
        float komisyon = 0;
        for (Satis i :satislar){
            satim += i.getSatisFiyati();
            kargo += i.getKargo();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("out")){
                iade += i.getIadeTutarı();
            }
        }
        for(Stok i :stoklar){
            stokDegeri += i.getOrtOdenenFiyat();
        }
        for(Sepet i :sepetler){
            if(i.getIslemTuru().equalsIgnoreCase("out")){
                komisyon += i.getKomisyon();
            }
        }

        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        satim = round(satim,2);
        stokDegeri = round(stokDegeri,2);
        iade = round(iade,2);

        return (komisyon+kargo+satim+stokDegeri+iade);
    }

    public float toplamKarGetir(){
        return round(gelirler()-giderler(),2) ;
    }

    public int alinanUrunSayisiGetir(){
        ArrayList<Alis> alimlar = butunAlislariGetir();
        int sayi = 0;
        for(Alis i : alimlar){
            sayi+=i.getAdet();
        }
        return sayi;
    }

    public int satilanUrunSayisiGetir(){
        ArrayList<Satis> satislar = butunSatislariGetir();
        int sayi = 0;
        for(Satis i : satislar){
            sayi+=i.getAdet();
        }
        return sayi;
    }

    public int iadeAlinanUrunSayisiGetir(){
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        int sayi = 0;
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                sayi+=i.getAdet();
            }
        }
        return sayi;
    }

    public int iadeVerilenUrunSayisiGetir(){
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        int sayi = 0;
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("out")){
                sayi+=i.getAdet();
            }
        }
        return sayi;
    }

    public int kalanUrunSayisiGetir(){
        ArrayList<Stok> stoklar = butunStoklarıGetir();
        int sayi = 0;
        for(Stok i : stoklar){
            sayi+=i.getAdet();
        }
        return sayi;
    }

    public float kullaniciSatilanMaliyetGetir(String kullaniciId){
        ArrayList<Alis> alislar = butunAlislariGetir();
        ArrayList<Satis> satislar = butunSatislariGetir();
        float fiyat = 0;


        for(Iterator<Satis> iterator0 = satislar.iterator(); iterator0.hasNext(); ){
            Satis s = iterator0.next();

            for(Iterator<Alis> iterator = alislar.iterator(); iterator.hasNext(); ){
                Alis a = iterator.next();

                if(s.getBarkodNo().equalsIgnoreCase(a.getBarkodNo())){
                    if(a.getAdet()==s.getAdet()){
                        if(sepetIdyeGoreSepetGetir(a.getSepetId()).getKid().equalsIgnoreCase(kullaniciId)){
                            fiyat += s.getAdet()*s.getAdetAlisFiyati();

                            iterator.remove();
                            iterator0.remove();
                        }

                    }
                    else if(a.getAdet()<s.getAdet()){
                        if(sepetIdyeGoreSepetGetir(a.getSepetId()).getKid().equalsIgnoreCase(kullaniciId)){
                            fiyat += a.getAdet()*s.getAdetAlisFiyati();

                            s.setAdet(s.getAdet()-a.getAdet());
                            iterator.remove();
                        }

                    }else{
                        if(sepetIdyeGoreSepetGetir(a.getSepetId()).getKid().equalsIgnoreCase(kullaniciId)){
                            fiyat += s.getAdet()*s.getAdetAlisFiyati();

                            a.setAdet(a.getAdet()-s.getAdet());
                            iterator0.remove();
                        }
                    }
                }
            }
        }


        return fiyat;
    }



    public List<PieEntry> butunGiderleriGetir2() {
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        ArrayList<Satis> satislar = butunSatislariGetir();
        ArrayList<Alis> alimlar = butunAlislariGetir();
        ArrayList<Sepet> sepetler =butunSepetleriGetir();

        List<PieEntry> gelirlerPieEntry = new ArrayList<>();


        float iade = 0;
        float kargo = 0;
        float alim = 0;
        float komisyon =0;
        for (Satis i :satislar){
            kargo += i.getKargo();
        }
        for (Alis i :alimlar){
            alim += i.getAlisFiyati();
            kargo += i.getKargo();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                iade += i.getIadeTutarı();
            }
        }
        for(Sepet i : sepetler){
            komisyon += i.getKomisyon();
        }


        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        alim = round(alim,2);
        iade = round(iade,2);
        if(alim != 0){
            PieEntry pieEntry = new PieEntry(alim, context.getString(R.string.alim));
            gelirlerPieEntry.add(pieEntry);
        }
        if(iade != 0){
            PieEntry pieEntry = new PieEntry(iade, context.getString(R.string.iade));
            gelirlerPieEntry.add(pieEntry);
        }
        if(kargo != 0){
            PieEntry pieEntry = new PieEntry(kargo, context.getString(R.string.kargo2));
            gelirlerPieEntry.add(pieEntry);
        }
        if(komisyon != 0){
            PieEntry pieEntry = new PieEntry(komisyon, context.getString(R.string.komisyon));
            gelirlerPieEntry.add(pieEntry);
        }


        return gelirlerPieEntry;
    }

    public List<PieEntry> butunCirolariGetir() {
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        ArrayList<Satis> satislar = butunSatislariGetir();
        ArrayList<Stok> stoklar = butunStoklarıGetir();
        ArrayList<Sepet> sepetler = butunSepetleriGetir();

        List<PieEntry> gelirlerPieEntry = new ArrayList<>();


        float iade = 0;
        float satim = 0;
        float stokDegeri =0;
        float kargo = 0;
        float komisyon = 0;
        for (Satis i :satislar){
            satim += i.getSatisFiyati();
            kargo += i.getKargo();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("out")){
                iade += i.getIadeTutarı();
            }
        }
        for(Stok i :stoklar){
            stokDegeri += i.getOrtOdenenFiyat();
        }
        for(Sepet i :sepetler){
            if(i.getIslemTuru().equalsIgnoreCase("out")){
                komisyon += i.getKomisyon();
            }
        }

        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        satim = round(satim,2);
        stokDegeri = round(stokDegeri,2);
        iade = round(iade,2);
        if(komisyon !=0){
            PieEntry pieEntry = new PieEntry(komisyon, context.getString(R.string.komisyon));
            gelirlerPieEntry.add(pieEntry);
        }
        if(satim != 0){
            PieEntry pieEntry = new PieEntry(satim, context.getString(R.string.satım));
            gelirlerPieEntry.add(pieEntry);
        }
        if(iade != 0){
            PieEntry pieEntry = new PieEntry(iade, context.getString(R.string.iade));
            gelirlerPieEntry.add(pieEntry);
        }
        if(stokDegeri !=0){
            PieEntry pieEntry = new PieEntry(stokDegeri, context.getString(R.string.stokdegeri));
            gelirlerPieEntry.add(pieEntry);
        }
        if(kargo !=0){
            PieEntry pieEntry = new PieEntry(kargo, context.getString(R.string.kargo2));
            gelirlerPieEntry.add(pieEntry);
        }

        return gelirlerPieEntry;
    }

    public List<PieEntry> butunGiderleriGetir() {
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        ArrayList<Sepet> sepetler = butunSepetleriGetir();
        ArrayList<Alis> alislar = new ArrayList<>();
        ArrayList<Satis> satislar = new ArrayList<>();

        List<PieEntry> giderlerPieEntry = new ArrayList<>();

        float komisyon = 0;
        float iade = 0;
        float alim = 0;
        float alimVergi = 0;
        float kargo = 0;
        float kargoVergi = 0;
        for(Sepet i: sepetler){
            alislar.addAll(sepetIdyeGoreAlislariGetir(i.getSepetId()));
            satislar.addAll(sepetIdyeGoreSatislariGetir(i.getSepetId()));
            komisyon += i.getKomisyon();
        }
        for (Alis i :alislar){
            alim += i.getAlisFiyati();
            alimVergi += i.getVergi();
            kargoVergi += i.getKargo_vergi();
            kargo += i.getKargo();
        }
        for (Satis i :satislar){
            kargoVergi +=i.getKargo_vergi();
            kargo += i.getKargo();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                iade += i.getIadeTutarı();
            }
        }


        alim = round(alim,2);
        alimVergi = round(alimVergi,2);
        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        kargoVergi = round(kargoVergi,2);
        iade = round(iade,2);
        if(komisyon != 0){
            PieEntry pieEntry = new PieEntry(komisyon ,context.getString(R.string.komisyon));
            giderlerPieEntry.add(pieEntry);
        }
        if(alim != 0){
            PieEntry pieEntry = new PieEntry(alim, context.getString(R.string.alim));
            giderlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(alimVergi, context.getString(R.string.alımVergi));
            giderlerPieEntry.add(pieEntry);
        }
        if(kargo != 0){
            PieEntry pieEntry = new PieEntry(kargo, context.getString(R.string.kargo2));
            giderlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(kargoVergi, context.getString(R.string.kargoVergi));
            giderlerPieEntry.add(pieEntry);
        }
        if(iade != 0){
            PieEntry pieEntry = new PieEntry(iade, context.getString(R.string.iade));
            giderlerPieEntry.add(pieEntry);
        }

        return giderlerPieEntry;
    }

    public List<PieEntry> butunGetirileriGetir() {
        ArrayList<Iade> iadeler = butunIadeleriGetir();
        ArrayList<Sepet> sepetler = butunSepetleriGetir();
        //ArrayList<Alis> alislar = new ArrayList<>();
        ArrayList<Satis> satislar = new ArrayList<>();

        List<PieEntry> gelirlerPieEntry = new ArrayList<>();

        float komisyon =0;
        float kargo =0;
        float kargo_vergi=0;
        float iade = 0;
        float satim = 0;
        float satimVergi = 0;
        for(Sepet i: sepetler){
            //alislar.addAll(sepetIdyeGoreAlislariGetir(i.getSepetId()));
            satislar.addAll(sepetIdyeGoreSatislariGetir(i.getSepetId()));
            if(i.getIslemTuru().equalsIgnoreCase("out")){
                komisyon += i.getKomisyon();
            }
        }
        for (Satis i :satislar){
            satimVergi +=i.getVergi();
            satim += i.getSatisFiyati();
            kargo +=i.getKargo();
            kargo_vergi+=i.getKargo_vergi();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("out")){
                iade += i.getIadeTutarı();
            }
        }



        satim = round(satim,2);
        satimVergi = round(satimVergi,2);
        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        kargo_vergi = round(kargo_vergi,2);
        iade = round(iade,2);
        if(komisyon !=0){
            PieEntry pieEntry = new PieEntry(komisyon, context.getString(R.string.komisyon));
            gelirlerPieEntry.add(pieEntry);
        }
        if(satim != 0){
            PieEntry pieEntry = new PieEntry(satim, context.getString(R.string.satım));
            gelirlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(satimVergi, context.getString(R.string.satımVergi));
            gelirlerPieEntry.add(pieEntry);
        }
        if(kargo !=0){
            PieEntry pieEntry = new PieEntry(kargo, context.getString(R.string.kargo2));
            gelirlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(kargo_vergi, context.getString(R.string.kargoVergi));
            gelirlerPieEntry.add(pieEntry);
        }
        if(iade != 0){
            PieEntry pieEntry = new PieEntry(iade, context.getString(R.string.iade));
            gelirlerPieEntry.add(pieEntry);
        }

        return gelirlerPieEntry;
    }

    public List<BarEntry> butunVergileriGetir(){
        ArrayList<Alis> alislar = butunAlislariGetir();
        ArrayList<Satis> satislar = butunSatislariGetir();
        ArrayList<Iade> iadeler = butunIadeleriGetir();

        List<BarEntry> VergilerBarEntry = new ArrayList<>();


        float alınanKargoVergisi = 0;
        float odenenKargoVergisi = 0;
        float satisVergisi =0;
        float alisVergisi =0;
        for (Satis i :satislar){

            satisVergisi += i.getVergi();
            alınanKargoVergisi +=i.getKargo_vergi();
        }
        for (Alis i :alislar){
            alisVergisi += i.getVergi();
            odenenKargoVergisi += i.getKargo_vergi();
        }

        //iadelerin vergilerini çıkar
        for(Iade i: iadeler){
            MergeAlisSatis alisSatis = sepetIdBarkodNoyaGoreAlısSatısıGetir(i.getSepetId(),i.getBarkodNo());
            int adet = alisSatis.getAdet();
            int iadet = i.getAdet();
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                satisVergisi -= ((alisSatis.getVergi())/adet)*iadet;
            }else{
                alisVergisi -= ((alisSatis.getVergi())/adet)*iadet;
            }
        }


        alınanKargoVergisi = round(alınanKargoVergisi,2);
        odenenKargoVergisi = round(odenenKargoVergisi,2);
        satisVergisi = round(satisVergisi,2);
        alisVergisi = round(alisVergisi,2);
        if(odenenKargoVergisi !=0 ){
            BarEntry barEntry = new BarEntry(0,odenenKargoVergisi );
            VergilerBarEntry.add(barEntry);
        }
        if(alisVergisi != 0){
            BarEntry barEntry = new BarEntry(1,alisVergisi );
            VergilerBarEntry.add(barEntry);
        }
        if(alınanKargoVergisi != 0){
            BarEntry barEntry = new BarEntry( 2,alınanKargoVergisi);
            VergilerBarEntry.add(barEntry);
        }
        if(satisVergisi != 0){
            BarEntry barEntry = new BarEntry(3,satisVergisi );
            VergilerBarEntry.add(barEntry);
        }

        return VergilerBarEntry;
    }

    public List<PieEntry> kullaniciGiderleriniGetir(String kullaniciId) {
        ArrayList<Iade> iadeler = iadeleriKullaniciIdyeGoreGetir(kullaniciId);
        ArrayList<Sepet> sepetler = kullaniciIdyeGoreSepetleriGetir(kullaniciId);
        ArrayList<Alis> alislar = new ArrayList<>();
        ArrayList<Satis> satislar = new ArrayList<>();

        List<PieEntry> giderlerPieEntry = new ArrayList<>();

        float komisyon = 0;
        float iade = 0;
        float alim = 0;
        float alimVergi = 0;
        float kargo = 0;
        float kargoVergi = 0;
        for(Sepet i: sepetler){
            alislar.addAll(sepetIdyeGoreAlislariGetir(i.getSepetId()));
            satislar.addAll(sepetIdyeGoreSatislariGetir(i.getSepetId()));
            komisyon += i.getKomisyon();
        }
        for (Alis i :alislar){
            alim += i.getAlisFiyati();
            alimVergi += i.getVergi();
            kargoVergi += i.getKargo_vergi();
            kargo += i.getKargo();
        }
        for (Satis i :satislar){
            kargoVergi +=i.getKargo_vergi();
            kargo += i.getKargo();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                iade += i.getIadeTutarı();
            }
        }


        alim = round(alim,2);
        alimVergi = round(alimVergi,2);
        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        kargoVergi = round(kargoVergi,2);
        iade = round(iade,2);
        if(komisyon != 0){
            PieEntry pieEntry = new PieEntry(komisyon ,context.getString(R.string.komisyon));
            giderlerPieEntry.add(pieEntry);
        }
        if(alim != 0){
            PieEntry pieEntry = new PieEntry(alim, context.getString(R.string.alim));
            giderlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(alimVergi, context.getString(R.string.alımVergi));
            giderlerPieEntry.add(pieEntry);
        }
        if(kargo != 0){
            PieEntry pieEntry = new PieEntry(kargo, context.getString(R.string.kargo2));
            giderlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(kargoVergi, context.getString(R.string.kargoVergi));
            giderlerPieEntry.add(pieEntry);
        }
        if(iade != 0){
            PieEntry pieEntry = new PieEntry(iade, context.getString(R.string.iade));
            giderlerPieEntry.add(pieEntry);
        }

        return giderlerPieEntry;
    }

    public List<PieEntry> kullaniciGetirileriniGetir(String kullaniciId) {
        ArrayList<Iade> iadeler = iadeleriKullaniciIdyeGoreGetir(kullaniciId);
        ArrayList<Sepet> sepetler = kullaniciIdyeGoreSepetleriGetir(kullaniciId);
        //ArrayList<Alis> alislar = new ArrayList<>();
        ArrayList<Satis> satislar = new ArrayList<>();

        List<PieEntry> gelirlerPieEntry = new ArrayList<>();

        float komisyon =0;
        float kargo =0;
        float kargo_vergi=0;
        float iade = 0;
        float satim = 0;
        float satimVergi = 0;
        for(Sepet i: sepetler){
            //alislar.addAll(sepetIdyeGoreAlislariGetir(i.getSepetId()));
            satislar.addAll(sepetIdyeGoreSatislariGetir(i.getSepetId()));
            if(i.getIslemTuru().equalsIgnoreCase("out")){
                komisyon += i.getKomisyon();
            }
        }
        for (Satis i :satislar){
            satimVergi +=i.getVergi();
            satim += i.getSatisFiyati();
            kargo +=i.getKargo();
            kargo_vergi+=i.getKargo_vergi();
        }
        for(Iade i : iadeler){
            if(i.getIadeYonu().equalsIgnoreCase("out")){
                iade += i.getIadeTutarı();
            }
        }



        satim = round(satim,2);
        satimVergi = round(satimVergi,2);
        komisyon = round(komisyon,2);
        kargo = round(kargo,2);
        kargo_vergi = round(kargo_vergi,2);
        iade = round(iade,2);
        if(komisyon !=0){
            PieEntry pieEntry = new PieEntry(komisyon, context.getString(R.string.komisyon));
            gelirlerPieEntry.add(pieEntry);
        }
        if(satim != 0){
            PieEntry pieEntry = new PieEntry(satim, context.getString(R.string.satım));
            gelirlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(satimVergi, context.getString(R.string.satımVergi));
            gelirlerPieEntry.add(pieEntry);
        }
        if(kargo !=0){
            PieEntry pieEntry = new PieEntry(kargo, context.getString(R.string.kargo2));
            gelirlerPieEntry.add(pieEntry);
            pieEntry = new PieEntry(kargo_vergi, context.getString(R.string.kargoVergi));
            gelirlerPieEntry.add(pieEntry);
        }
        if(iade != 0){
            PieEntry pieEntry = new PieEntry(iade, context.getString(R.string.iade));
            gelirlerPieEntry.add(pieEntry);
        }

        return gelirlerPieEntry;
    }

    public List<BarEntry> kullaniciVergileriniGetir(String kullaniciId){
        ArrayList<Sepet> sepetler = kullaniciIdyeGoreSepetleriGetir(kullaniciId);
        ArrayList<Iade> iadeler = iadeleriKullaniciIdyeGoreGetir(kullaniciId);
        ArrayList<Alis> alislar = new ArrayList<>();
        ArrayList<Satis> satislar = new ArrayList<>();

        List<BarEntry> VergilerBarEntry = new ArrayList<>();

        for(Sepet i: sepetler){
            alislar.addAll(sepetIdyeGoreAlislariGetir(i.getSepetId()));
            satislar.addAll(sepetIdyeGoreSatislariGetir(i.getSepetId()));
        }

        float alınanKargoVergisi = 0;
        float odenenKargoVergisi = 0;
        float satisVergisi =0;
        float alisVergisi =0;
        for (Satis i :satislar){

            satisVergisi += i.getVergi();
            alınanKargoVergisi +=i.getKargo_vergi();
        }
        for (Alis i :alislar){
            alisVergisi += i.getVergi();
            odenenKargoVergisi += i.getKargo_vergi();
        }

        //iadelerin vergilerini çıkar
        for(Iade i: iadeler){
            MergeAlisSatis alisSatis = sepetIdBarkodNoyaGoreAlısSatısıGetir(i.getSepetId(),i.getBarkodNo());
            int adet = alisSatis.getAdet();
            int iadet = i.getAdet();
            if(i.getIadeYonu().equalsIgnoreCase("in")){
                satisVergisi -= ((alisSatis.getVergi())/adet)*iadet;
            }else{
                alisVergisi -= ((alisSatis.getVergi())/adet)*iadet;
            }
        }


        odenenKargoVergisi = round(odenenKargoVergisi,2);
        alisVergisi = round(alisVergisi,2);
        alınanKargoVergisi = round(alınanKargoVergisi,2);
        satisVergisi = round(satisVergisi,2);
        if(odenenKargoVergisi !=0 ){
            BarEntry barEntry = new BarEntry(0,odenenKargoVergisi );
            VergilerBarEntry.add(barEntry);
        }
        if(alisVergisi != 0){
            BarEntry barEntry = new BarEntry(1,alisVergisi );
            VergilerBarEntry.add(barEntry);
        }
        if(alınanKargoVergisi != 0){
            BarEntry barEntry = new BarEntry( 2,alınanKargoVergisi);
            VergilerBarEntry.add(barEntry);
        }
        if(satisVergisi != 0){
            BarEntry barEntry = new BarEntry(3,satisVergisi );
            VergilerBarEntry.add(barEntry);
        }



        return VergilerBarEntry;
    }

}
