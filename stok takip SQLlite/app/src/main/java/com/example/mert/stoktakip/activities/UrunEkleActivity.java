package com.example.mert.stoktakip.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.Urun;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import android.net.Uri; //----------

import java.io.ByteArrayOutputStream;

public class UrunEkleActivity extends AppCompatActivity {

    EditText barkodNoTxt;
    EditText urunAdiTxt;
    ImageButton barkodOkuyucuImgBtn;
    Button urunEkleBtn;
    EditText vergiOranı;
    EditText karOranı;


    ImageView urunResmi;  //-----------
    Button resimEkle; //------------
    byte[] bytesImage; //-----------------
    int SELECT_PICTURE = 200; //----------

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_ekle);

        barkodNoTxt = findViewById(R.id.txt_barkod);
        urunAdiTxt = findViewById(R.id.txt_urun_adi);
        barkodOkuyucuImgBtn = findViewById(R.id.btn_barkod);
        urunEkleBtn = findViewById(R.id.btn_urun_ekle);
        mp = MediaPlayer.create(this, R.raw.scan_sound);
        vergiOranı = findViewById(R.id.txt_vergi_oranı);
        karOranı = findViewById(R.id.txt_kar_oranı);
        urunResmi = findViewById(R.id.img_urun);           //--------------
        resimEkle = findViewById(R.id.btn_resim_ekle);    //----------------

        //urunResmi.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
        urunResmi.setImageResource(R.drawable.box);

        urunEkleBtn.setOnClickListener(e -> urunEkle());
        barkodOkuyucuImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UrunEkleActivity.this, BarkodOkuyucuActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void urunEkle() {
        String barkod = barkodNoTxt.getText().toString();
        String ad = urunAdiTxt.getText().toString();
        String vergioranı = vergiOranı.getText().toString();
        String karoranı = karOranı.getText().toString();
        // Alış ve satış fiyatları kuruş şeklinde long değer olarak geliyor. Floata çevrilmesi gerekiyor
        //float alis = alisFiyatiTxt.getRawValue() / (float) 100;
        //float satis = satisFiyatiTxt.getRawValue() / (float) 100;
        //float alis =  Float.valueOf(alisFiyatiTxt2.getText().toString());  //--------------
        //float satis = Float.valueOf(satisFiyatiTxt2.getText().toString()); //--------------

        Bitmap bitmap = ((BitmapDrawable) urunResmi.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bytesImage = baos.toByteArray();
        bytesImage = imagemTratada(bytesImage); // resim boyutunu kontrol et

        // Alanlardan herhangi biri boşsa hata ver
        if (barkod.equals("") || ad.equals("") || vergioranı.equals("") || vergioranı.equals(".") || karoranı.equals("") || karoranı.equals(".") || bytesImage == null || bytesImage.length <= 0) {
            new GlideToast.makeToast(UrunEkleActivity.this, "Lütfen bütün alanları doldurun." ,
                    GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
            return;
        }
        float vergioranif = Float.valueOf(vergiOranı.getText().toString());
        float karoranif = Float.valueOf(karOranı.getText().toString());

        Urun urun = new Urun(barkod, ad, bytesImage, vergioranif ,karoranif);
        VeritabaniIslemleri vti = new VeritabaniIslemleri(this);

        // Eğer ürün barkodu veritabanında bulunuyorsa hata ver
        if (vti.urunTekrariKontrolEt(urun.getBarkodNo())) {
            new GlideToast.makeToast(UrunEkleActivity.this, "Ürün zaten ekli.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Ürün eklenemediyse hata ver
        if (vti.urunEkle(urun) == -1) {
            new GlideToast.makeToast(UrunEkleActivity.this, "Ürün eklenemedi.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        if (vti.urunEkle(urun) == -2) {
            new GlideToast.makeToast(UrunEkleActivity.this, "Ürün eklendi stok eklenemedi.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Ürün eklendiyse giriş alanlarını sil ve ürünün eklendiğini kullanıcıya bildir
        alanlariBosalt();
        new GlideToast.makeToast(UrunEkleActivity.this, "Ürün eklendi.",
                GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
    }

    // Barkod tarayıcı kapanınca gelen değeri barkodno edittext'ine geçiriyor ve düzenlemeyi kapatıyor
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    mp.start();
                    barkodNoTxt.setText(barcode.displayValue);
                    barkodNoTxt.setEnabled(false);
                    urunAdiTxt.requestFocus();
                } else {
                    new GlideToast.makeToast(UrunEkleActivity.this, "Barkod eklenemedi.",
                            GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                }
            }
        } else if(requestCode == SELECT_PICTURE) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {

                // compare the resultCode with the
                // SELECT_PICTURE constant
                if (requestCode == SELECT_PICTURE) {
                    // Get the url of the image from data
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        // update the preview image in the layout
                        urunResmi.setImageURI(selectedImageUri);


                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alanlariBosalt() {
        barkodNoTxt.setText(null);
        urunAdiTxt.setText(null);
        barkodNoTxt.setEnabled(true);
        urunResmi.setImageResource(R.drawable.box);
        //urunResmi.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
        karOranı.setText(null);
        vergiOranı.setText(null);
    }

    public void resimEkle(View view){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }


    private byte[] imagemTratada(byte[] imagem_img){

        while (imagem_img.length > 500000){
            Bitmap bitmap = BitmapFactory.decodeByteArray(imagem_img, 0, imagem_img.length);
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imagem_img = stream.toByteArray();
        }
        return imagem_img;

    }
}
