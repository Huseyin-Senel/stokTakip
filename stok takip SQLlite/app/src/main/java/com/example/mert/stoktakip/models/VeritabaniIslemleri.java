package com.example.mert.stoktakip.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mert.stoktakip.R;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class VeritabaniIslemleri extends SQLiteOpenHelper {
    private Context context;

    // Veritabanı versiyonu
    private static final int VERITABANI_VERSION = 8;

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

    // Tablo oluşturma sorguları

    // kullanici tablosunun oluşturma sorgusu
    private static final String KULLANICI_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_KULLANICI + "(" +
                                                            SUTUN_KULLANICI_KADI + " TEXT PRIMARY KEY NOT NULL COLLATE NOCASE, " +
                                                            SUTUN_KULLANICI_SIFRE + " TEXT NOT NULL, " +
                                                            SUTUN_KULLANICI_RESIM + " BLOB" + ")";

    // urun tablosunun oluşturma sorgusu
    private static final String URUN_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_URUN + "(" +
                                                       SUTUN_URUN_ID + " TEXT PRIMARY KEY NOT NULL, " +
                                                       SUTUN_URUN_AD + " TEXT NOT NULL, " +
                                                       SUTUN_URUN_VERGI_ORANI + " INTEGER, " +
                                                       SUTUN_URUN_KAR_ORANI + " INTEGER, " +
                                                       SUTUN_URUN_RESIM + " BLOB" + ")";

    private static final String STOK_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_STOK + "(" +
                                                            SUTUN_STOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                            SUTUN_STOK_URUN_ID + " TEXT NOT NULL, " +
                                                            SUTUN_STOK_ADET + " INTEGER DEFAULT 0, " +
                                                            SUTUN_STOK_ORT_ODENEN_FIYAT + " INTEGER, " +
                                                            SUTUN_STOK_ADET_ORT_ODENEN_FIYAT + " INTEGER, " +
                                                            "FOREIGN KEY(" + SUTUN_STOK_URUN_ID + ") REFERENCES " + TABLO_URUN + "(" + SUTUN_URUN_ID + "))";

    private static final String SATIS_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_SATIS + "(" +
                                                              SUTUN_SATIS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                              SUTUN_SATIS_SEPET_ID + " INTEGER NOT NULL, " +
                                                              SUTUN_SATIS_URUN_ID + " TEXT NOT NULL, " +
                                                              SUTUN_SATIS_ADET + " INTEGER, " +
                                                              SUTUN_SATIS_SATIS_FIYATI + " INTEGER, " +
                                                              SUTUN_SATIS_URUN_VERGISI + " INTEGER, " +
                                                              SUTUN_SATIS_URUN_VERGI_ORANI + " INTEGER, " +
                                                              SUTUN_SATIS_KARGO + " INTEGER, " +
                                                              SUTUN_SATIS_KARGO_VERGI + " INTEGER, " +
                                                              SUTUN_SATIS_ADET_ALIS_FIYATI + " INTEGER, " +
                                                              SUTUN_SATIS_KARG0_VERGI_ORANI + " INTEGER, " +
                                                              "FOREIGN KEY(" + SUTUN_SATIS_URUN_ID + ") REFERENCES " + TABLO_URUN + "(" + SUTUN_URUN_ID + "), " +
                                                              "FOREIGN KEY(" + SUTUN_SATIS_SEPET_ID + ") REFERENCES " + TABLO_SEPET + "(" + SUTUN_SEPET_ID + "))";

    private static final String ALIS_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_ALIS + "(" +
                                                             SUTUN_ALIS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                             SUTUN_ALIS_SEPET_ID + " INTEGER NOT NULL, " +
                                                             SUTUN_ALIS_URUN_ID + " TEXT NOT NULL, " +
                                                             SUTUN_ALIS_ADET + " INTEGER, " +
                                                             SUTUN_ALIS_ALIS_FIYATI + " INTEGER, " +
                                                             SUTUN_ALIS_URUN_VERGISI + " INTEGER, " +
                                                             SUTUN_ALIS_URUN_VERGI_ORANI + " INTEGER, " +
                                                             SUTUN_ALIS_KARGO + " INTEGER, " +
                                                             SUTUN_ALIS_KARGO_VERGI + " INTEGER, " +
                                                             SUTUN_ALIS_KARG0_VERGI_ORANI + " INTEGER, " +
                                                             "FOREIGN KEY(" + SUTUN_ALIS_URUN_ID + ") REFERENCES " + TABLO_URUN + "(" + SUTUN_URUN_ID + "), " +
                                                             "FOREIGN KEY(" + SUTUN_ALIS_SEPET_ID + ") REFERENCES " + TABLO_SEPET + "(" + SUTUN_SEPET_ID + "))";

    private static final String SEPET_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_SEPET + "(" +
                                                              SUTUN_SEPET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                              SUTUN_SEPET_KULLANICI_ID + " TEXT NOT NULL, " +
                                                              SUTUN_SEPET_ISLEM_TURU + " TEXT NOT NULL, " +
                                                              SUTUN_SEPET_ADET + " INTEGER NOT NULL, " +
                                                              SUTUN_SEPET_FIYAT + " INTEGER, " +
                                                              SUTUN_SEPET_TOPLAM_KARGO + " INTEGER, " +
                                                              SUTUN_SEPET_KOMISYON + " INTEGER, " +
                                                              SUTUN_SEPET_ACIKLAMA + " TEXT, " +
                                                              SUTUN_SEPET_ISLEM_TARIHI + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                                                              "FOREIGN KEY(" + SUTUN_SEPET_KULLANICI_ID + ") REFERENCES " + TABLO_KULLANICI + "(" + SUTUN_KULLANICI_KADI + "))";

    private static final String IADE_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_IADE + "(" +
                                                            SUTUN_IADE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                            SUTUN_IADE_YONU + " TEXT NOT NULL, " +
                                                            SUTUN_IADE_KULLANICI_ID + " TEXT NOT NULL, " +
                                                            SUTUN_IADE_SEPET_ID + " INTEGER NOT NULL, " +
                                                            SUTUN_IADE_ALIS_SATIS_ID + " INTEGER NOT NULL, " +         //FOREIGN KEYİ YOK
                                                            SUTUN_IADE_URUN_ID + " TEXT NOT NULL, " +
                                                            SUTUN_IADE_ADET + " INTEGER, " +
                                                            SUTUN_IADE_TUTARI + " INTEGER, " +
                                                            SUTUN_IADE_ACIKLAMA + " TEXT, " +
                                                            SUTUN_IADE_TARIHI + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                                                            "FOREIGN KEY(" + SUTUN_IADE_KULLANICI_ID + ") REFERENCES " + TABLO_KULLANICI + "(" + SUTUN_KULLANICI_KADI + "), " +
                                                            "FOREIGN KEY(" + SUTUN_IADE_SEPET_ID + ") REFERENCES " + TABLO_SEPET + "(" + SUTUN_SEPET_ID + "), " +
                                                            "FOREIGN KEY(" + SUTUN_IADE_URUN_ID + ") REFERENCES " + TABLO_URUN + "(" + SUTUN_URUN_ID + "))";

    private static final String BOZUK_URUN_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_BOZUK_URUN + "(" +
                                                            SUTUN_BOZUK_URUN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                            SUTUN_BOZUK_URUN_KULLANICI_ID + " TEXT NOT NULL, " +
                                                            SUTUN_BOZUK_URUN_URUN_ID + " TEXT NOT NULL, " +
                                                            SUTUN_BOZUK_URUN_ADET + " INTEGER, " +
                                                            SUTUN_BOZUK_URUN_ACIKLAMA + " TEXT, " +
                                                            SUTUN_BOZUK_URUN_TARIH + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                                                            "FOREIGN KEY(" + SUTUN_BOZUK_URUN_KULLANICI_ID + ") REFERENCES " + TABLO_KULLANICI + "(" + SUTUN_KULLANICI_KADI + "), " +
                                                            "FOREIGN KEY(" + SUTUN_BOZUK_URUN_URUN_ID + ") REFERENCES " + TABLO_URUN + "(" + SUTUN_URUN_ID + "))";

    private static final String IADE_STOK_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_IADE_STOK + "(" +
            SUTUN_IADE_STOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SUTUN_IADE_STOK_IADE_ID + " INTEGER NOT NULL, " +
            SUTUN_IADE_STOK_URUN_ID + " TEXT NOT NULL, " +
            SUTUN_IADE_STOK_ADET + " INTEGER DEFAULT 0, " +
            SUTUN_IADE_STOK_ADET_ALIS_FIYATI + " INTEGER, " +
            "FOREIGN KEY(" + SUTUN_IADE_STOK_URUN_ID + ") REFERENCES " + TABLO_URUN + "(" + SUTUN_URUN_ID + "), " +
            "FOREIGN KEY(" + SUTUN_IADE_STOK_IADE_ID + ") REFERENCES " + TABLO_IADE + "(" + SUTUN_IADE_ID + "))";


    // kullanici tablosunun silme sorgusu
    private static final String KULLANICI_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_KULLANICI;

    // urun tablosunun silme sorgusu
    private static final String URUN_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_URUN;

    // stok tablosunun silme sorgusu
    private static final String STOK_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_STOK;

    // satıs tablosunun silme sorgusu
    private static final String SATIS_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_SATIS;

    // alis tablosunun silme sorgusu
    private static final String ALIS_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_ALIS;

    //sepet tablosunun silme sorgusu
    private static final String SEPET_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_SEPET;

    // iade tablosunun silme sorgusu
    private static final String IADE_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_IADE;

    // bozuk ürün tablosunun silme sorgusu
    private static final String BOZUK_URUN_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_BOZUK_URUN;

    // iade stok tablosunun silme sorgusu
    private static final String IADE_STOK_TABLOSU_SIL = "DROP TABLE IF EXISTS " + TABLO_IADE_STOK;

    // Yapıcı Metot
    public VeritabaniIslemleri(Context context) {
        super(context, VERITABANI_ADI, null, VERITABANI_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KULLANICI_TABLOSU_OLUSTUR);
        db.execSQL(URUN_TABLOSU_OLUSTUR);
        db.execSQL(STOK_TABLOSU_OLUSTUR);
        db.execSQL(SATIS_TABLOSU_OLUSTUR);
        db.execSQL(ALIS_TABLOSU_OLUSTUR);
        db.execSQL(SEPET_TABLOSU_OLUSTUR);
        db.execSQL(IADE_TABLOSU_OLUSTUR);
        db.execSQL(BOZUK_URUN_TABLOSU_OLUSTUR);
        db.execSQL(IADE_STOK_TABLOSU_OLUSTUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(KULLANICI_TABLOSU_SIL);
        db.execSQL(URUN_TABLOSU_SIL);
        db.execSQL(STOK_TABLOSU_SIL);
        db.execSQL(SATIS_TABLOSU_SIL);
        db.execSQL(ALIS_TABLOSU_SIL);
        db.execSQL(SEPET_TABLOSU_SIL);
        db.execSQL(IADE_TABLOSU_SIL);
        db.execSQL(BOZUK_URUN_TABLOSU_SIL);
        db.execSQL(IADE_STOK_TABLOSU_SIL);
        onCreate(db);
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
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_KULLANICI_KADI, kullanici.getKadi());
        values.put(SUTUN_KULLANICI_SIFRE, kullanici.getSifre());
        values.put(SUTUN_KULLANICI_RESIM, kullanici.getResim());

        // degisenSatir -1 ise hata oluşmuştur.
        long hataKontrolu = db.insert(TABLO_KULLANICI, null, values);
        db.close();
        return hataKontrolu;
    }

    // Gelen kullanıcıyı siler
    public void kullaniciSil(Kullanici kullanici){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLO_KULLANICI, SUTUN_KULLANICI_KADI + " = ?", new String[]{kullanici.getKadi()});
        db.close();
    }

    // Gelen kullanıcıyı günceller
    public int kullaniciGuncelle(Kullanici kullanici){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_KULLANICI_SIFRE, kullanici.getSifre());
        values.put(SUTUN_KULLANICI_RESIM, kullanici.getResim());

        int degisenSatir = db.update(TABLO_KULLANICI, values, SUTUN_KULLANICI_KADI + " = ?",
                new String[]{kullanici.getKadi()});
        db.close();
        Log.i("mert", degisenSatir + " ");
        return degisenSatir;
    }

    // Kullanıcı adının var olup olmadığını kontrol eder
    public boolean kullaniciAdiniKontrolEt(String kadi){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {SUTUN_KULLANICI_KADI};
        String secim = SUTUN_KULLANICI_KADI + " = ?";
        String[] secimOlcutleri = {kadi};

        Cursor cursor = db.query(TABLO_KULLANICI,       // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);

        int cursorSayisi = cursor.getCount();
        cursor.close();
        db.close();

        return cursorSayisi > 0;
    }

    // Kullanıcı adı ve şifrenin doğruluğunu kontrol eder
    public boolean girisBilgileriniKontrolEt(Kullanici kullanici) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {SUTUN_KULLANICI_KADI};
        String  secim = SUTUN_KULLANICI_KADI + " = ?" + " AND " + SUTUN_KULLANICI_SIFRE + " = ?";
        String[] secimOlcutleri = {kullanici.getKadi(), kullanici.getSifre()};

        Cursor cursor = db.query(TABLO_KULLANICI,       // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);

        int cursorSayisi = cursor.getCount();
        cursor.close();
        db.close();

        return cursorSayisi > 0;
    }

    public Kullanici kullaniciAdinaGoreKullaniciyiGetir(String kadi){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {"*"};
        String secim = SUTUN_KULLANICI_KADI + " = ?";
        String[] secimOlcutleri = {kadi};

        Kullanici kullanici = new Kullanici();

        Cursor cursor = db.query(TABLO_KULLANICI,            // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            kullanici.setKadi(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_KADI)));
            kullanici.setSifre(cursor.getString(cursor.getColumnIndex(SUTUN_KULLANICI_SIFRE)));
            kullanici.setResim(cursor.getBlob(cursor.getColumnIndex(SUTUN_KULLANICI_RESIM)));
        }
        cursor.close();
        db.close();
        return kullanici;
    }

    public ArrayList<Kullanici> butunKullanicilariGetir(){
            SQLiteDatabase db = this.getReadableDatabase();

            ArrayList<Kullanici> kullanicis = new ArrayList<>();
            String query = "SELECT * FROM " + TABLO_KULLANICI;

            Cursor c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                do {
                    Kullanici kullanici = new Kullanici();
                    kullanici.setKadi(c.getString(c.getColumnIndex(SUTUN_KULLANICI_KADI)));
                    kullanici.setSifre(c.getString(c.getColumnIndex(SUTUN_KULLANICI_SIFRE)));
                    kullanici.setResim(c.getBlob(c.getColumnIndex(SUTUN_KULLANICI_RESIM)));
                    kullanicis.add(kullanici);
                } while (c.moveToNext());
            }
            c.close();
            db.close();
            return kullanicis;
    }

    /**
     *
     * ürün tablosuyla ilgili metotlar
     *
     */

    //yeni ürün ve 0 stok ekler
    public long urunEkle (Urun urun){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_URUN_ID, urun.getBarkodNo());
        values.put(SUTUN_URUN_AD, urun.getAd());
        values.put(SUTUN_URUN_RESIM , urun.getResim());
        values.put(SUTUN_URUN_VERGI_ORANI, Math.round(urun.getVergiOrani()*100));
        values.put(SUTUN_URUN_KAR_ORANI, Math.round(urun.getKarOrani()*100));

        // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_URUN, null, values);
        db.close();

        if(degisenSatir != -1){
            if(stokOlustur(urun.getBarkodNo()) == -1){
                degisenSatir = -2;
            }
        }
        return degisenSatir;
    }

    // Gelen ürünü siler
    public int urunSil(String barkod){
        SQLiteDatabase db = this.getWritableDatabase();

        // sonuc -1 ise hata oluşmuştur
        int sonuc = db.delete(TABLO_URUN, SUTUN_URUN_ID + " = ?", new String[]{String.valueOf(barkod)});
        db.close();
        return sonuc;
    }

    // Gelen ürünü günceller
    public int urunGuncelle(Urun urun){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_URUN_AD, urun.getAd());
        values.put(SUTUN_URUN_RESIM , urun.getResim());
        values.put(SUTUN_URUN_VERGI_ORANI, Math.round(urun.getVergiOrani()*100));
        values.put(SUTUN_URUN_KAR_ORANI, Math.round(urun.getKarOrani()*100));

        int degisenSatir = db.update(TABLO_URUN, values, SUTUN_URUN_ID + " = ?",
                new String[]{String.valueOf(urun.getBarkodNo())});
        db.close();
        return degisenSatir;
    }

    // Bütün ürünleri getirir
    public ArrayList<Urun> butunUrunleriGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Urun> urunler = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_URUN;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Urun urun = new Urun();
                urun.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_URUN_ID)));
                urun.setAd(c.getString(c.getColumnIndex(SUTUN_URUN_AD)));
                urun.setResim(c.getBlob(c.getColumnIndex(SUTUN_URUN_RESIM)));
                int vergiOraniInt = c.getInt(c.getColumnIndex(SUTUN_URUN_VERGI_ORANI));
                int karOraniInt = c.getInt(c.getColumnIndex(SUTUN_URUN_KAR_ORANI));
                urun.setVergiOrani(((float)vergiOraniInt)/100);
                urun.setKarOrani(((float)karOraniInt)/100);

                urunler.add(urun);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return urunler;
    }

    // Gelen barkodun veritabanında ekli olup olmadığını kontrol eder
    public boolean urunTekrariKontrolEt(String barkod){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {SUTUN_URUN_ID};
        String secim = SUTUN_URUN_ID + " = ?";
        String[] secimOlcutleri = {barkod};

        int cursorSayisi = 0;

        if(barkod != null) {
            Cursor cursor = db.query(TABLO_URUN,            // işlem için kullanılacak tablo
                    sutunlar,                               // geri dönecek sütunlar
                    secim,                                  // WHERE için sütunlar
                    secimOlcutleri,                         // WHERE için değerler
                    null,
                    null,
                    null);

            cursorSayisi = cursor.getCount();
            cursor.close();
            db.close();
        }

        return cursorSayisi > 0;
    }

    // Gelen barkoda göre ürün bilgilerini getirir
    public Urun barkodaGoreUrunGetir(String barkod){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {"*"};
        String secim = SUTUN_URUN_ID + " = ?";
        String[] secimOlcutleri = {barkod};

        Urun urun = new Urun();

        Cursor cursor = db.query(TABLO_URUN,            // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            urun.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_URUN_ID)));
            urun.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_URUN_AD)));
            urun.setResim(cursor.getBlob(cursor.getColumnIndex(SUTUN_URUN_RESIM)));
            int vergiOraniInt = cursor.getInt(cursor.getColumnIndex(SUTUN_URUN_VERGI_ORANI));
            int karOraniInt = cursor.getInt(cursor.getColumnIndex(SUTUN_URUN_KAR_ORANI));
            urun.setVergiOrani(((float)vergiOraniInt)/100);
            urun.setKarOrani(((float)karOraniInt)/100);
        }
        cursor.close();
        db.close();
        return urun;
    }

    // Gelen isime göre ürün bilgilerini getirir
    public Urun isimeGoreUrunGetir(String isim){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {"*"};
        String secim = SUTUN_URUN_AD + " = ?";
        String[] secimOlcutleri = {isim};

        Urun urun = new Urun();

        Cursor cursor = db.query(TABLO_URUN,            // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            urun.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_URUN_ID)));
            urun.setAd(cursor.getString(cursor.getColumnIndex(SUTUN_URUN_AD)));
            urun.setResim(cursor.getBlob(cursor.getColumnIndex(SUTUN_URUN_RESIM)));
            int vergiOraniInt = cursor.getInt(cursor.getColumnIndex(SUTUN_URUN_VERGI_ORANI));
            int karOraniInt = cursor.getInt(cursor.getColumnIndex(SUTUN_URUN_KAR_ORANI));
            urun.setVergiOrani(((float)vergiOraniInt)/100);
            urun.setKarOrani(((float)karOraniInt)/100);
        }
        cursor.close();
        db.close();
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

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_STOK_URUN_ID, barkodNo);
        values.put(SUTUN_STOK_ADET, 0);
        values.put(SUTUN_STOK_ORT_ODENEN_FIYAT, 0);
        values.put(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT, 0);

                // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_STOK, null, values);
        db.close();
        return degisenSatir;
    }

    public int stokSil(String barkod){
        SQLiteDatabase db = this.getWritableDatabase();

        // sonuc -1 ise hata oluşmuştur
        int sonuc = db.delete(TABLO_STOK, SUTUN_STOK_URUN_ID + " = ?", new String[]{barkod});
        db.close();
        return sonuc;
    }

    public boolean stokTekrariKontrolEt(String barkod){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {SUTUN_STOK_URUN_ID};
        String secim = SUTUN_STOK_URUN_ID + " = ?";
        String[] secimOlcutleri = {barkod};

        int cursorSayisi = 0;

        if(barkod != null) {
            Cursor cursor = db.query(TABLO_STOK,            // işlem için kullanılacak tablo
                    sutunlar,                               // geri dönecek sütunlar
                    secim,                                  // WHERE için sütunlar
                    secimOlcutleri,                         // WHERE için değerler
                    null,
                    null,
                    null);

            cursorSayisi = cursor.getCount();
            cursor.close();
            db.close();
        }

        return cursorSayisi > 0;
    }

    public int stokArttır(String barkodNo, int adet, float fiyat ){

        Stok stok = stokBul(barkodNo);

        if(stok == null ){
            return -1;
        }

        stok.setAdet(stok.getAdet()+adet);
        stok.setOrtOdenenFiyat(stok.getOrtOdenenFiyat()+fiyat);
        stok.setAdetOrtOdenenFiyat(stok.getOrtOdenenFiyat()/stok.getAdet());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_STOK_ADET, stok.getAdet());
        values.put(SUTUN_STOK_ORT_ODENEN_FIYAT, Math.round(stok.getOrtOdenenFiyat()*100));
        values.put(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT, Math.round(stok.getAdetOrtOdenenFiyat()*100));

        int degisenSatir = db.update(TABLO_STOK, values, SUTUN_STOK_URUN_ID + " = ?",
                new String[]{String.valueOf(stok.getBarkodNo())});
        db.close();
        return degisenSatir;
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


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_STOK_ADET, stok.getAdet());
        values.put(SUTUN_STOK_ORT_ODENEN_FIYAT, Math.round(stok.getOrtOdenenFiyat()*100));
        values.put(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT, Math.round(stok.getAdetOrtOdenenFiyat()*100));

        int degisenSatir = db.update(TABLO_STOK, values, SUTUN_STOK_URUN_ID + " = ?",
                new String[]{String.valueOf(stok.getBarkodNo())});
        db.close();
        return degisenSatir;
    }

    //Kullanım dışı
    public int stokIadeAzalt(String barkodNo, int adet, float fiyat ){
        Stok stok = stokBul(barkodNo);

        if(stok == null ){
            return -1;
        }

        stok.setAdet(stok.getAdet()-adet);
        stok.setOrtOdenenFiyat(stok.getOrtOdenenFiyat()-fiyat);
        if(stok.getAdet() !=0){
            stok.setAdetOrtOdenenFiyat(stok.getOrtOdenenFiyat()/stok.getAdet());
        }
        else{
            stok.setAdetOrtOdenenFiyat(stok.getOrtOdenenFiyat());
        }


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_STOK_ADET, stok.getAdet());
        values.put(SUTUN_STOK_ORT_ODENEN_FIYAT, Math.round(stok.getOrtOdenenFiyat()*100));
        values.put(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT, Math.round(stok.getAdetOrtOdenenFiyat()*100));

        int degisenSatir = db.update(TABLO_STOK, values, SUTUN_STOK_URUN_ID + " = ?",
                new String[]{String.valueOf(stok.getBarkodNo())});
        db.close();
        return degisenSatir;
    }

    public int stokBozukUrunAzalt(String barkodNo, int adet){

        Stok stok = stokBul(barkodNo);

        if(stok == null ){
            return -1;
        }

        stok.setAdet(stok.getAdet()-adet);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUTUN_STOK_ADET, stok.getAdet());

        int degisenSatir = db.update(TABLO_STOK, values, SUTUN_STOK_URUN_ID + " = ?",
                new String[]{String.valueOf(stok.getBarkodNo())});
        db.close();
        return degisenSatir;
    }

    public Stok stokBul(String barkodNo){
        Stok stok = new Stok();

        if(!stokTekrariKontrolEt(barkodNo)){
            return stok;
        }

        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {"*"};
        String secim = SUTUN_STOK_URUN_ID + " = ?";
        String[] secimOlcutleri = {barkodNo};

        Cursor cursor = db.query(TABLO_STOK,            // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            stok.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_STOK_URUN_ID)));
            stok.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_STOK_ADET)));
            int ort_fiyat = cursor.getInt(cursor.getColumnIndex(SUTUN_STOK_ORT_ODENEN_FIYAT));
            int adet_ort_fiyat = cursor.getInt(cursor.getColumnIndex(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT));
            stok.setOrtOdenenFiyat(((float)ort_fiyat)/100);
            stok.setAdetOrtOdenenFiyat(((float)adet_ort_fiyat)/100);
        }
        cursor.close();
        db.close();
        return stok;
    }

    public ArrayList<Stok> azalanUrunGetir(int esik){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Stok> stoklar = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_STOK + " WHERE " + SUTUN_STOK_ADET + " <= " + esik;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Stok stok = new Stok();
                stok.setStokId(c.getInt(c.getColumnIndex(SUTUN_STOK_ID)));
                stok.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_STOK_URUN_ID)));
                stok.setAdet(c.getInt(c.getColumnIndex(SUTUN_STOK_ADET)));
                int ortAlisFiyatiInt = c.getInt(c.getColumnIndex(SUTUN_STOK_ORT_ODENEN_FIYAT));
                int adetOrtAlisFiyatiInt = c.getInt(c.getColumnIndex(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT));
                stok.setOrtOdenenFiyat(((float)ortAlisFiyatiInt)/100);
                stok.setAdetOrtOdenenFiyat(((float)adetOrtAlisFiyatiInt)/100);

                stoklar.add(stok);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return stoklar;
    }

    //bütün stokları getir
    public ArrayList<Stok> butunStoklarıGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Stok> stoklar = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_STOK;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Stok stok = new Stok();
                stok.setStokId(c.getInt(c.getColumnIndex(SUTUN_STOK_ID)));
                stok.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_STOK_URUN_ID)));
                stok.setAdet(c.getInt(c.getColumnIndex(SUTUN_STOK_ADET)));
                int ortAlisFiyatiInt = c.getInt(c.getColumnIndex(SUTUN_STOK_ORT_ODENEN_FIYAT));
                int adetOrtAlisFiyatiInt = c.getInt(c.getColumnIndex(SUTUN_STOK_ADET_ORT_ODENEN_FIYAT));
                stok.setOrtOdenenFiyat(((float)ortAlisFiyatiInt)/100);
                stok.setAdetOrtOdenenFiyat(((float)adetOrtAlisFiyatiInt)/100);

                stoklar.add(stok);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return stoklar;
    }


    /**
     *
     * sepet tablosuyla ilgili metotlar
     *
     */

    public int SepetOlustur( Sepet sepet){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_SEPET_KULLANICI_ID, sepet.getKid());
        values.put(SUTUN_SEPET_ISLEM_TURU, sepet.getIslemTuru());
        values.put(SUTUN_SEPET_ADET, sepet.getAdet());
        values.put(SUTUN_SEPET_FIYAT, Math.round(sepet.getToplamFiyat()*100));
        values.put(SUTUN_SEPET_TOPLAM_KARGO, Math.round(sepet.getToplamKargo()*100));
        values.put(SUTUN_SEPET_KOMISYON, Math.round(sepet.getKomisyon()*100));
        values.put(SUTUN_SEPET_ACIKLAMA, sepet.getAciklama());


        // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_SEPET, null, values);
        if(degisenSatir == -1){
            return -1;
        }

        String queryLastRowInserted = "select last_insert_rowid()";
        final Cursor cursor = db.rawQuery(queryLastRowInserted, null);
        int idLastInsertedRow = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    idLastInsertedRow = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return idLastInsertedRow;
    }

    public long sepetIdyeGoreSepetiSil(int sepetId){
        SQLiteDatabase db = this.getWritableDatabase();

        long degisenSatir = db.delete(TABLO_SEPET, SUTUN_SEPET_ID + " = ?", new String[]{String.valueOf(sepetId)});
        db.close();
        return  degisenSatir;
    }

    public Sepet sepetIdyeGoreSepetGetir(int sepetId){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {SUTUN_SEPET_ID, SUTUN_SEPET_ISLEM_TURU, "datetime(" +
                SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')",
                SUTUN_SEPET_ACIKLAMA, SUTUN_SEPET_ADET,
                SUTUN_SEPET_KULLANICI_ID, SUTUN_SEPET_KOMISYON,
                SUTUN_SEPET_FIYAT, SUTUN_SEPET_TOPLAM_KARGO};
        String secim = SUTUN_SEPET_ID + " = ?";
        String[] secimOlcutleri = {String.valueOf(sepetId)};

        Sepet sepet = new Sepet();

        Cursor cursor = db.query(TABLO_SEPET,            // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            sepet.setSepetId(cursor.getInt(cursor.getColumnIndex(SUTUN_SEPET_ID)));
            sepet.setIslemTuru(cursor.getString(cursor.getColumnIndex(SUTUN_SEPET_ISLEM_TURU)));
            sepet.setIslemTarihi(cursor.getString(cursor.getColumnIndex("datetime(" +
                    SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')")));
            sepet.setAciklama(cursor.getString(cursor.getColumnIndex(SUTUN_SEPET_ACIKLAMA)));
            sepet.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_SEPET_ADET)));
            sepet.setKid(cursor.getString(cursor.getColumnIndex(SUTUN_SEPET_KULLANICI_ID)));
            sepet.setKomisyon((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SEPET_KOMISYON))) / 100);
            sepet.setToplamFiyat((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SEPET_FIYAT))) / 100);
            sepet.setToplamKargo((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SEPET_TOPLAM_KARGO))) / 100);
        }
        cursor.close();
        db.close();
        return sepet;
    }

    public  ArrayList<Sepet> kullaniciIdyeGoreSepetleriGetir(String kullaniciId){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Sepet> sepetler = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_SEPET + " WHERE " + SUTUN_SEPET_KULLANICI_ID + " = '" + kullaniciId+"'";

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Sepet sepet = new Sepet();

                sepet.setSepetId(c.getInt(c.getColumnIndex(SUTUN_SEPET_ID)));
                sepet.setAdet(c.getInt(c.getColumnIndex(SUTUN_SEPET_ADET)));
                sepet.setKid(c.getString(c.getColumnIndex(SUTUN_SEPET_KULLANICI_ID)));
                sepet.setAciklama(c.getString(c.getColumnIndex(SUTUN_SEPET_ACIKLAMA)));
                sepet.setIslemTuru(c.getString(c.getColumnIndex(SUTUN_SEPET_ISLEM_TURU)));
                sepet.setIslemTarihi(c.getString(c.getColumnIndex(SUTUN_SEPET_ISLEM_TARIHI)));
                sepet.setToplamFiyat((float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_FIYAT)))/100);
                sepet.setKomisyon((float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_KOMISYON)))/100);
                sepet.setToplamKargo((float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_TOPLAM_KARGO)))/100);

                sepetler.add(sepet);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
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
        SQLiteDatabase db = this.getReadableDatabase();

        //ArrayList<MergeAlisSatis> islemler = new ArrayList<>();
        ArrayList<Sepet> sepetler = new ArrayList<>();

        baslangicTarihi += " 00:00:00";
        bitisTarihi += " 23:59:59";

        String query1 = "SELECT " + SUTUN_SEPET_ID + ", " +
                SUTUN_SEPET_TOPLAM_KARGO + ", " +
                SUTUN_SEPET_KULLANICI_ID + ", " +
                SUTUN_SEPET_ACIKLAMA + ", " +
                SUTUN_SEPET_KOMISYON + ", " +
                SUTUN_SEPET_ADET + ", " +
                SUTUN_SEPET_FIYAT + ", " +
                SUTUN_SEPET_ISLEM_TURU + ", datetime(" +
                SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime') " +
                " FROM " + TABLO_SEPET +
                " WHERE " + SUTUN_SEPET_ISLEM_TARIHI + " BETWEEN '" +
                baslangicTarihi + "' AND '" + bitisTarihi + "' AND " +
                SUTUN_SEPET_ISLEM_TURU + " LIKE '" + islemTuru +
                "' ORDER BY datetime(" + SUTUN_SEPET_ISLEM_TARIHI + ") DESC" ;

        Log.d("HA", query1);

        Cursor c = db.rawQuery(query1, null);
        if(c.moveToFirst()){
            do{
                int sepetId = c.getInt(c.getColumnIndex(SUTUN_SEPET_ID));
                int adet = c.getInt(c.getColumnIndex(SUTUN_SEPET_ADET));
                String kullaniciId = c.getString(c.getColumnIndex(SUTUN_SEPET_KULLANICI_ID));
                String aciklama = c.getString(c.getColumnIndex(SUTUN_SEPET_ACIKLAMA));
                String tur = c.getString(c.getColumnIndex(SUTUN_SEPET_ISLEM_TURU));
                String tarih = c.getString(c.getColumnIndex("datetime(" + SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')"));
                float fiyat = (float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_FIYAT)))/100;
                float komisyon = (float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_KOMISYON)))/100;
                float kargo = (float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_TOPLAM_KARGO)))/100;

                Sepet sepet = new Sepet(sepetId,kullaniciId,tur,kargo,komisyon,adet,fiyat,aciklama,tarih);
                sepetler.add(sepet);

            } while(c.moveToNext());
        }
        c.close();
        db.close();

        return sepetler;
    }

    public ArrayList<Sepet> butunSepetleriGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Sepet> sepetler = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_SEPET;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Sepet sepet = new Sepet();

                sepet.setSepetId(c.getInt(c.getColumnIndex(SUTUN_SEPET_ID)));
                sepet.setAdet(c.getInt(c.getColumnIndex(SUTUN_SEPET_ADET)));
                sepet.setKid(c.getString(c.getColumnIndex(SUTUN_SEPET_KULLANICI_ID)));
                sepet.setAciklama(c.getString(c.getColumnIndex(SUTUN_SEPET_ACIKLAMA)));
                sepet.setIslemTuru(c.getString(c.getColumnIndex(SUTUN_SEPET_ISLEM_TURU)));
                sepet.setIslemTarihi(c.getString(c.getColumnIndex(SUTUN_SEPET_ISLEM_TARIHI)));
                sepet.setToplamFiyat((float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_FIYAT)))/100);
                sepet.setKomisyon((float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_KOMISYON)))/100);
                sepet.setToplamKargo((float)(c.getInt(c.getColumnIndex(SUTUN_SEPET_TOPLAM_KARGO)))/100);

                sepetler.add(sepet);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return sepetler;
    }

    /**
     *
     * alis satis tablolarıyla ilgili metotlar
     *
     */

    public long alisEkle(Alis alis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_ALIS_SEPET_ID, alis.getSepetId());
        values.put(SUTUN_ALIS_URUN_ID, alis.getBarkodNo());
        values.put(SUTUN_ALIS_ADET, alis.getAdet());
        values.put(SUTUN_ALIS_ALIS_FIYATI,Math.round(alis.getAlisFiyati()*100));
        values.put(SUTUN_ALIS_URUN_VERGISI,Math.round(alis.getVergi()*100));
        values.put(SUTUN_ALIS_URUN_VERGI_ORANI,Math.round(alis.getVergiOran()*100));
        values.put(SUTUN_ALIS_KARGO,Math.round(alis.getKargo()*100));
        values.put(SUTUN_ALIS_KARGO_VERGI,Math.round(alis.getKargo_vergi()*100));
        values.put(SUTUN_ALIS_KARG0_VERGI_ORANI,Math.round(alis.getKargo_vergi_oran()*100));

        // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_ALIS, null, values);
        db.close();
        return degisenSatir;
    }

    public long satisEkle(Satis satis){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_SATIS_SEPET_ID, satis.getSepetId());
        values.put(SUTUN_SATIS_URUN_ID, satis.getBarkodNo());
        values.put(SUTUN_SATIS_ADET, satis.getAdet());
        values.put(SUTUN_SATIS_SATIS_FIYATI,Math.round(satis.getSatisFiyati()*100));
        values.put(SUTUN_SATIS_URUN_VERGISI,Math.round(satis.getVergi()*100));
        values.put(SUTUN_SATIS_URUN_VERGI_ORANI,Math.round(satis.getVergiOran()*100));
        values.put(SUTUN_SATIS_KARGO,Math.round(satis.getKargo()*100));
        values.put(SUTUN_SATIS_KARGO_VERGI,Math.round(satis.getKargo_vergi()*100));
        values.put(SUTUN_SATIS_KARG0_VERGI_ORANI,Math.round(satis.getKargo_vergi_oran()*100));
        values.put(SUTUN_SATIS_ADET_ALIS_FIYATI,Math.round(satis.getAdetAlisFiyati()*100));

        // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_SATIS, null, values);
        db.close();
        return degisenSatir;
    }

    public int sepetIdyeGoreAlıslariSil(int sepetId){
        SQLiteDatabase db = this.getWritableDatabase();
        // sonuc -1 ise hata oluşmuştur
        int sonuc = db.delete(TABLO_ALIS, SUTUN_ALIS_SEPET_ID + " = ?", new String[]{String.valueOf(sepetId)});
        db.close();
        return sonuc;
    }

    public int sepetIdyeGoreSatislariSil(int sepetId){
        SQLiteDatabase db = this.getWritableDatabase();
        // sonuc -1 ise hata oluşmuştur
        int sonuc = db.delete(TABLO_SATIS, SUTUN_SATIS_SEPET_ID + " = ?", new String[]{String.valueOf(sepetId)});
        db.close();
        return sonuc;
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
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<MergeAlisSatis> islemler = new ArrayList<>();
        ArrayList<Sepet> sepetler = new ArrayList<>();

        baslangicTarihi += " 00:00:00";
        bitisTarihi += " 23:59:59";

        String query1 = "SELECT " + SUTUN_SEPET_ID + ", " +
                SUTUN_SEPET_ISLEM_TURU + ", datetime(" +
                SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime') " +
                " FROM " + TABLO_SEPET +
                " WHERE " + SUTUN_SEPET_ISLEM_TARIHI + " BETWEEN '" +
                baslangicTarihi + "' AND '" + bitisTarihi + "' AND " +
                SUTUN_SEPET_ISLEM_TURU + " LIKE '" + islemTuru +
                "' ORDER BY datetime(" + SUTUN_SEPET_ISLEM_TARIHI + ") DESC" ;

        Log.d("HA", query1);

        Cursor c = db.rawQuery(query1, null);
        if(c.moveToFirst()){
            do{
                int sepetId = c.getInt(c.getColumnIndex(SUTUN_SEPET_ID));
                String tur = c.getString(c.getColumnIndex(SUTUN_SEPET_ISLEM_TURU));
                String tarih = c.getString(c.getColumnIndex("datetime(" + SUTUN_SEPET_ISLEM_TARIHI + ", 'localtime')"));

                Sepet sepet = new Sepet(sepetId, tur, tarih);
                sepetler.add(sepet);

            } while(c.moveToNext());
        }
        c.close();
        db.close();

        //ArrayList<Alis> alislar = new ArrayList<>();
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
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Alis> alislar = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_ALIS + " WHERE " + SUTUN_ALIS_SEPET_ID + " = " + sepetId;


        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Alis alis = new Alis();
                alis.setId(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ID)));
                alis.setSepetId(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_SEPET_ID)));
                alis.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_ALIS_URUN_ID)));
                alis.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ADET)));
                alis.setAlisFiyati((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ALIS_FIYATI))) / 100);
                alis.setKargo((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARGO))) / 100);
                alis.setKargo_vergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARGO_VERGI))) / 100);
                alis.setKargo_vergi_oran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARG0_VERGI_ORANI))) / 100);
                alis.setVergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_URUN_VERGISI))) / 100);
                alis.setVergiOran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_URUN_VERGI_ORANI))) / 100);

                alislar.add(alis);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return alislar;
    }

    public ArrayList<Satis> sepetIdyeGoreSatislariGetir(int sepetId){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Satis> satislar = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_SATIS + " WHERE " + SUTUN_SATIS_SEPET_ID + " = " + sepetId;


        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Satis satis = new Satis();
                satis.setId(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ID)));
                satis.setSepetId(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_SEPET_ID)));
                satis.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_SATIS_URUN_ID)));
                satis.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ADET)));
                satis.setSatisFiyati((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_SATIS_FIYATI))) / 100);
                satis.setKargo((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARGO))) / 100);
                satis.setKargo_vergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARGO_VERGI))) / 100);
                satis.setKargo_vergi_oran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARG0_VERGI_ORANI))) / 100);
                satis.setVergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_URUN_VERGISI))) / 100);
                satis.setVergiOran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_URUN_VERGI_ORANI))) / 100);
                satis.setAdetAlisFiyati((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ADET_ALIS_FIYATI))) / 100);

                satislar.add(satis);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return satislar;
    }

    public MergeAlisSatis sepetIdBarkodNoyaGoreAlısSatısıGetir(int sepetId, String barkodNo){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLO_SATIS + " WHERE " + SUTUN_SATIS_SEPET_ID + " = " + sepetId + " AND " + SUTUN_SATIS_URUN_ID + " = " + barkodNo;

        MergeAlisSatis alısSatis = new MergeAlisSatis();

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            alısSatis.setSepetId(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_SEPET_ID)));
            alısSatis.setId(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ID)));
            alısSatis.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_SATIS_URUN_ID)));
            alısSatis.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ADET)));
            alısSatis.setAlisSatisFiyati((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_SATIS_FIYATI))) / 100);
            alısSatis.setKargo((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARGO))) / 100);
            alısSatis.setKargo_vergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARGO_VERGI))) / 100);
            alısSatis.setKargo_vergi_oran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARG0_VERGI_ORANI))) / 100);
            alısSatis.setVergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_URUN_VERGISI))) / 100);
            alısSatis.setVergiOran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_URUN_VERGI_ORANI))) / 100);
            alısSatis.setSatisAdetAlisFiyati((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ADET_ALIS_FIYATI))) / 100);
        }else{
            query = "SELECT * FROM " + TABLO_ALIS + " WHERE " + SUTUN_ALIS_SEPET_ID + " = " + sepetId + " AND " + SUTUN_ALIS_URUN_ID + " = " + barkodNo;
            cursor = db.rawQuery(query, null);
            if(cursor.moveToFirst() && cursor.getCount() == 1){
                alısSatis.setSepetId(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_SEPET_ID)));
                alısSatis.setId(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ID)));
                alısSatis.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_ALIS_URUN_ID)));
                alısSatis.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ADET)));
                alısSatis.setAlisSatisFiyati((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ALIS_FIYATI))) / 100);
                alısSatis.setKargo((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARGO))) / 100);
                alısSatis.setKargo_vergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARGO_VERGI))) / 100);
                alısSatis.setKargo_vergi_oran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARG0_VERGI_ORANI))) / 100);
                alısSatis.setVergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_URUN_VERGISI))) / 100);
                alısSatis.setVergiOran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_URUN_VERGI_ORANI))) / 100);
            }
        }
        cursor.close();
        db.close();

        return alısSatis;
    }

    public ArrayList<MergeAlisSatis> sepetIdyeGoreAlislariSatislariGetir(int sepetId){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<MergeAlisSatis> alısSatislar = new ArrayList<>();

        String query = "SELECT * FROM " + TABLO_SATIS + " WHERE " + SUTUN_SATIS_SEPET_ID + " = " + sepetId;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                MergeAlisSatis alısSatis = new MergeAlisSatis();

                alısSatis.setSepetId(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_SEPET_ID)));
                alısSatis.setId(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ID)));
                alısSatis.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_SATIS_URUN_ID)));
                alısSatis.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ADET)));
                alısSatis.setAlisSatisFiyati((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_SATIS_FIYATI))) / 100);
                alısSatis.setKargo((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARGO))) / 100);
                alısSatis.setKargo_vergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARGO_VERGI))) / 100);
                alısSatis.setKargo_vergi_oran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_KARG0_VERGI_ORANI))) / 100);
                alısSatis.setVergi((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_URUN_VERGISI))) / 100);
                alısSatis.setVergiOran((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_URUN_VERGI_ORANI))) / 100);
                alısSatis.setSatisAdetAlisFiyati((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_SATIS_ADET_ALIS_FIYATI))) / 100);

                alısSatislar.add(alısSatis);

            }while (cursor.moveToNext());
        }
        cursor.close();

        query = "SELECT * FROM " + TABLO_ALIS + " WHERE " + SUTUN_ALIS_SEPET_ID + " = " + sepetId;
        cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                MergeAlisSatis alısSatis = new MergeAlisSatis();

                alısSatis.setSepetId(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_SEPET_ID)));
                alısSatis.setId(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ID)));
                alısSatis.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_ALIS_URUN_ID)));
                alısSatis.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ADET)));
                alısSatis.setAlisSatisFiyati((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_ALIS_FIYATI))) / 100);
                alısSatis.setKargo((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARGO))) / 100);
                alısSatis.setKargo_vergi((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARGO_VERGI))) / 100);
                alısSatis.setKargo_vergi_oran((float)(cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_KARG0_VERGI_ORANI))) / 100);
                alısSatis.setVergi((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_URUN_VERGISI))) / 100);
                alısSatis.setVergiOran((float) (cursor.getInt(cursor.getColumnIndex(SUTUN_ALIS_URUN_VERGI_ORANI))) / 100);

                alısSatislar.add(alısSatis);

            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return alısSatislar;
    }

    public ArrayList<Alis> butunAlislariGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Alis> alislar = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_ALIS;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Alis alis = new Alis();

                alis.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_ALIS_URUN_ID)));
                alis.setId(c.getInt(c.getColumnIndex(SUTUN_ALIS_ID)));
                alis.setKargo((float)c.getInt(c.getColumnIndex(SUTUN_ALIS_KARGO))/100);
                alis.setKargo_vergi((float)(c.getInt(c.getColumnIndex(SUTUN_ALIS_KARGO_VERGI))) / 100);
                alis.setKargo_vergi_oran((float)(c.getInt(c.getColumnIndex(SUTUN_ALIS_KARG0_VERGI_ORANI))) / 100);
                alis.setSepetId(c.getInt(c.getColumnIndex(SUTUN_ALIS_SEPET_ID)));
                alis.setAdet(c.getInt(c.getColumnIndex(SUTUN_ALIS_ADET)));
                alis.setAlisFiyati((float)c.getInt(c.getColumnIndex(SUTUN_ALIS_ALIS_FIYATI))/100);
                alis.setVergiOran((float)c.getInt(c.getColumnIndex(SUTUN_ALIS_URUN_VERGI_ORANI))/100);
                alis.setVergi((float)c.getInt(c.getColumnIndex(SUTUN_ALIS_URUN_VERGISI))/100);

                alislar.add(alis);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return alislar;
    }

    public ArrayList<Satis> butunSatislariGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Satis> satislar = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_SATIS;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Satis satis = new Satis();

                satis.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_SATIS_URUN_ID)));
                satis.setId(c.getInt(c.getColumnIndex(SUTUN_SATIS_ID)));
                satis.setKargo((float)c.getInt(c.getColumnIndex(SUTUN_SATIS_KARGO))/100);
                satis.setKargo_vergi((float)(c.getInt(c.getColumnIndex(SUTUN_SATIS_KARGO_VERGI))) / 100);
                satis.setKargo_vergi_oran((float)(c.getInt(c.getColumnIndex(SUTUN_SATIS_KARG0_VERGI_ORANI))) / 100);
                satis.setSepetId(c.getInt(c.getColumnIndex(SUTUN_SATIS_SEPET_ID)));
                satis.setAdet(c.getInt(c.getColumnIndex(SUTUN_SATIS_ADET)));
                satis.setSatisFiyati((float)c.getInt(c.getColumnIndex(SUTUN_SATIS_SATIS_FIYATI))/100);
                satis.setVergiOran((float)c.getInt(c.getColumnIndex(SUTUN_SATIS_URUN_VERGI_ORANI))/100);
                satis.setVergi((float)c.getInt(c.getColumnIndex(SUTUN_SATIS_URUN_VERGISI))/100);
                satis.setAdetAlisFiyati((float)(c.getInt(c.getColumnIndex(SUTUN_SATIS_ADET_ALIS_FIYATI))) / 100);

                satislar.add(satis);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return satislar;
    }


    /**
     *
     * bozuk ürün tablosuyla ilgili metotlar
     *
     */
    public int bozukUrunEkle(BozukUrun bozukUrun){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_BOZUK_URUN_KULLANICI_ID, bozukUrun.getKullanıcıId());
        values.put(SUTUN_BOZUK_URUN_URUN_ID, bozukUrun.getBarkodNo());
        values.put(SUTUN_BOZUK_URUN_ADET, bozukUrun.getAdet());
        values.put(SUTUN_BOZUK_URUN_ACIKLAMA, bozukUrun.getAciklama());

        // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_BOZUK_URUN, null, values);
        if(degisenSatir == -1){
            return -1;
        }

        String queryLastRowInserted = "select last_insert_rowid()";
        final Cursor cursor = db.rawQuery(queryLastRowInserted, null);
        int idLastInsertedRow = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    idLastInsertedRow = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return idLastInsertedRow;
    }

    public int bozukUrunIdyeGoreBozukUrunSil(int bozukUrunId){
        SQLiteDatabase db = this.getWritableDatabase();
        // sonuc -1 ise hata oluşmuştur
        int sonuc = db.delete(TABLO_BOZUK_URUN, SUTUN_BOZUK_URUN_ID + " = ?", new String[]{String.valueOf(bozukUrunId)});
        db.close();
        return sonuc;
    }

    public ArrayList<BozukUrun> butunBozukUrunleriGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<BozukUrun> urunler = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_BOZUK_URUN;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                BozukUrun bozukUrun = new BozukUrun();

                bozukUrun.setBozukUrunId(c.getInt(c.getColumnIndex(SUTUN_BOZUK_URUN_ID)));
                bozukUrun.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_BOZUK_URUN_URUN_ID)));
                bozukUrun.setKullanıcıId(c.getString(c.getColumnIndex(SUTUN_BOZUK_URUN_KULLANICI_ID)));
                bozukUrun.setAdet(c.getInt(c.getColumnIndex(SUTUN_BOZUK_URUN_ADET)));
                bozukUrun.setAciklama(c.getString(c.getColumnIndex(SUTUN_BOZUK_URUN_ACIKLAMA)));
                bozukUrun.setTarih(c.getString(c.getColumnIndex("datetime(" + SUTUN_BOZUK_URUN_TARIH + ", 'localtime')")));

                urunler.add(bozukUrun);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return urunler;
    }

    /**
     *
     * iade tablosuyla ilgili metotlar
     *
     */

    public int iadeEkle(Iade iade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_IADE_YONU, iade.getIadeYonu());
        values.put(SUTUN_IADE_KULLANICI_ID, iade.getKullanıcıId());
        values.put(SUTUN_IADE_SEPET_ID, iade.getSepetId());
        values.put(SUTUN_IADE_ALIS_SATIS_ID, iade.getAlisSatisId());
        values.put(SUTUN_IADE_URUN_ID, iade.getBarkodNo());
        values.put(SUTUN_IADE_ADET, iade.getAdet());
        values.put(SUTUN_IADE_TUTARI, Math.round(iade.getIadeTutarı()*100));
        values.put(SUTUN_IADE_ACIKLAMA,iade.getAciklama());

        // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_IADE, null, values);
        if(degisenSatir == -1){
            return -1;
        }

        String queryLastRowInserted = "select last_insert_rowid()";
        final Cursor cursor = db.rawQuery(queryLastRowInserted, null);
        int idLastInsertedRow = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    idLastInsertedRow = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        db.close();
        return idLastInsertedRow;
    }

    public ArrayList<Iade> butunIadeleriGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Iade> iadeler = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_IADE;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Iade iade = new Iade();

                iade.setIadeId(c.getInt(c.getColumnIndex(SUTUN_IADE_ID)));
                iade.setIadeYonu(c.getString(c.getColumnIndex(SUTUN_IADE_YONU)));
                iade.setKullanıcıId(c.getString(c.getColumnIndex(SUTUN_IADE_KULLANICI_ID)));
                iade.setSepetId(c.getInt(c.getColumnIndex(SUTUN_IADE_SEPET_ID)));
                iade.setAlisSatisId(c.getInt(c.getColumnIndex(SUTUN_IADE_ALIS_SATIS_ID)));
                iade.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_IADE_URUN_ID)));
                iade.setAdet(c.getInt(c.getColumnIndex(SUTUN_IADE_ADET)));
                iade.setIadeTutarı((float)c.getInt(c.getColumnIndex(SUTUN_IADE_TUTARI))/100);
                iade.setAciklama(c.getString(c.getColumnIndex(SUTUN_IADE_ACIKLAMA)));
                iade.setTarih(c.getString(c.getColumnIndex(SUTUN_IADE_TARIHI)));

                iadeler.add(iade);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return iadeler;
    }

    public int iadeIdyeGoreIadeyiSil(int iadeId){
        SQLiteDatabase db = this.getWritableDatabase();
        // sonuc -1 ise hata oluşmuştur
        int sonuc = db.delete(TABLO_IADE, SUTUN_IADE_ID + " = ?", new String[]{String.valueOf(iadeId)});
        db.close();
        return sonuc;
    }

    public ArrayList<Iade> iadeleriSepetIdIslemIdyeGoreGetir(int sepetId, int alisSatisId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLO_IADE + " WHERE " + SUTUN_IADE_SEPET_ID + " = " + sepetId + " AND " + SUTUN_IADE_ALIS_SATIS_ID + " = " + alisSatisId;
        ArrayList<Iade> iadeler = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Iade iade = new Iade();

                iade.setIadeId(c.getInt(c.getColumnIndex(SUTUN_IADE_ID)));
                iade.setIadeYonu(c.getString(c.getColumnIndex(SUTUN_IADE_YONU)));
                iade.setKullanıcıId(c.getString(c.getColumnIndex(SUTUN_IADE_KULLANICI_ID)));
                iade.setSepetId(c.getInt(c.getColumnIndex(SUTUN_IADE_SEPET_ID)));
                iade.setAlisSatisId(c.getInt(c.getColumnIndex(SUTUN_IADE_ALIS_SATIS_ID)));
                iade.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_IADE_URUN_ID)));
                iade.setAdet(c.getInt(c.getColumnIndex(SUTUN_IADE_ADET)));
                iade.setIadeTutarı((float)c.getInt(c.getColumnIndex(SUTUN_IADE_TUTARI))/100);
                iade.setTarih(c.getString(c.getColumnIndex(SUTUN_IADE_TARIHI)));
                iade.setAciklama(c.getString(c.getColumnIndex(SUTUN_IADE_ACIKLAMA)));


                iadeler.add(iade);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return iadeler;
    }

    public ArrayList<Iade> iadeleriKullaniciIdyeGoreGetir(String kullaniciId){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLO_IADE + " WHERE " + SUTUN_IADE_KULLANICI_ID + " = '" + kullaniciId +"'";
        ArrayList<Iade> iadeler = new ArrayList<>();

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                Iade iade = new Iade();

                iade.setIadeId(c.getInt(c.getColumnIndex(SUTUN_IADE_ID)));
                iade.setIadeYonu(c.getString(c.getColumnIndex(SUTUN_IADE_YONU)));
                iade.setKullanıcıId(c.getString(c.getColumnIndex(SUTUN_IADE_KULLANICI_ID)));
                iade.setSepetId(c.getInt(c.getColumnIndex(SUTUN_IADE_SEPET_ID)));
                iade.setAlisSatisId(c.getInt(c.getColumnIndex(SUTUN_IADE_ALIS_SATIS_ID)));
                iade.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_IADE_URUN_ID)));
                iade.setAdet(c.getInt(c.getColumnIndex(SUTUN_IADE_ADET)));
                iade.setIadeTutarı((float)c.getInt(c.getColumnIndex(SUTUN_IADE_TUTARI))/100);
                iade.setTarih(c.getString(c.getColumnIndex(SUTUN_IADE_TARIHI)));
                iade.setAciklama(c.getString(c.getColumnIndex(SUTUN_IADE_ACIKLAMA)));


                iadeler.add(iade);
            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return iadeler;
    }

    public int iadeAdetiniGetir(int sepetId, int alisSatisId){
        ArrayList<Iade> iadeler = iadeleriSepetIdIslemIdyeGoreGetir(sepetId,alisSatisId);
        int adet = 0;

        if(iadeler != null){
            for(int i = 0; i<iadeler.size(); i++){
                adet += iadeler.get(i).getAdet();
            }
        }

        return adet;
    }

    /**
     *
     * iade_sok tablosuyla ilgili metotlar
     *
     */

    public long iadeStokOlustur(IadeStok iadeStok){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_IADE_STOK_URUN_ID, iadeStok.getBarkodNo());
        values.put(SUTUN_IADE_STOK_ADET, iadeStok.getAdet());
        values.put(SUTUN_IADE_STOK_IADE_ID, iadeStok.getIadeId());
        values.put(SUTUN_IADE_STOK_ADET_ALIS_FIYATI,Math.round(iadeStok.getAdetAlisFiyati()*100));

        // degisenSatir -1 ise hata oluşmuştur
        long degisenSatir = db.insert(TABLO_IADE_STOK, null, values);
        db.close();
        return degisenSatir;
    }

    public int iadeStokAzalt(int IadeStokId,int adet){
        IadeStok iadeStok = iadeStokBul(IadeStokId);

        if(iadeStok == null ){
            return -1;
        }

        iadeStok.setAdet(iadeStok.getAdet()-adet);
        SQLiteDatabase db = this.getWritableDatabase();
        int degisenSatir = -1;

        if(iadeStok.getAdet()>0){
            ContentValues values = new ContentValues();
            values.put(SUTUN_IADE_STOK_ADET, iadeStok.getAdet());
            degisenSatir = db.update(TABLO_IADE_STOK, values, SUTUN_IADE_STOK_ID + " = ?",
                    new String[]{String.valueOf(iadeStok.getId())});
        }else{
            degisenSatir = db.delete(TABLO_IADE_STOK, SUTUN_IADE_STOK_ID + " = ?", new String[]{String.valueOf(IadeStokId)});
        }


        db.close();
        return degisenSatir;
    }

    public int iadeStokArttır(int IadeStokId,int adet){
        IadeStok iadeStok = iadeStokBul(IadeStokId);

        if(iadeStok == null ){
            return -1;
        }

        iadeStok.setAdet(iadeStok.getAdet()+adet);
        SQLiteDatabase db = this.getWritableDatabase();
        int degisenSatir = -1;

        if(iadeStok.getAdet()>0){
            ContentValues values = new ContentValues();
            values.put(SUTUN_IADE_STOK_ADET, iadeStok.getAdet());
            degisenSatir = db.update(TABLO_IADE_STOK, values, SUTUN_IADE_STOK_ID + " = ?",
                    new String[]{String.valueOf(iadeStok.getId())});
        }else{
            degisenSatir = db.delete(TABLO_IADE_STOK, SUTUN_IADE_STOK_ID + " = ?", new String[]{String.valueOf(IadeStokId)});
        }


        db.close();
        return degisenSatir;
    }

    public IadeStok iadeStokBul(int IadeStokId){
        IadeStok stok = new IadeStok();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] sutunlar = {"*"};
        String secim = SUTUN_IADE_STOK_ID + " = ?";
        String[] secimOlcutleri = {String.valueOf(IadeStokId)};

        Cursor cursor = db.query(TABLO_IADE_STOK,            // işlem için kullanılacak tablo
                sutunlar,                               // geri dönecek sütunlar
                secim,                                  // WHERE için sütunlar
                secimOlcutleri,                         // WHERE için değerler
                null,
                null,
                null);
        if (cursor.moveToFirst() && cursor.getCount() == 1) {
            stok.setId(cursor.getInt(cursor.getColumnIndex(SUTUN_IADE_STOK_ID)));
            stok.setIadeId(cursor.getInt(cursor.getColumnIndex(SUTUN_IADE_STOK_IADE_ID)));
            stok.setBarkodNo(cursor.getString(cursor.getColumnIndex(SUTUN_IADE_STOK_URUN_ID)));
            stok.setAdet(cursor.getInt(cursor.getColumnIndex(SUTUN_IADE_STOK_ADET)));
            int adet_alis_fiyat = cursor.getInt(cursor.getColumnIndex(SUTUN_IADE_STOK_ADET_ALIS_FIYATI));
            stok.setAdetAlisFiyati(((float)adet_alis_fiyat)/100);
        }
        cursor.close();
        db.close();
        return stok;
    }

    public ArrayList<IadeStok> butunIadeStoklarıGetir(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<IadeStok> stoklar = new ArrayList<>();
        String query = "SELECT * FROM " + TABLO_IADE_STOK;

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                IadeStok stok = new IadeStok();
                stok.setId(c.getInt(c.getColumnIndex(SUTUN_IADE_STOK_ID)));
                stok.setIadeId(c.getInt(c.getColumnIndex(SUTUN_IADE_STOK_IADE_ID)));
                stok.setBarkodNo(c.getString(c.getColumnIndex(SUTUN_IADE_STOK_URUN_ID)));
                stok.setAdet(c.getInt(c.getColumnIndex(SUTUN_IADE_STOK_ADET)));

                int adetAlisFiyati = c.getInt(c.getColumnIndex(SUTUN_IADE_STOK_ADET_ALIS_FIYATI));
                stok.setAdetAlisFiyati(((float)adetAlisFiyati)/100);

                stoklar.add(stok);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
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
