package com.example.huseyin.stoktakip.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.Urun;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.example.huseyin.stoktakip.utils.TouchInterceptorLayout;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class UrunGuncelleActivity extends AppCompatActivity {

    EditText barkodNoTxt;
    EditText urunAdiTxt;
    CurrencyEditText alisFiyatiTxt;
    CurrencyEditText satisFiyatiTxt;
    Button urunGuncelleBtn;
    TouchInterceptorLayout til;
    MediaPlayer mp;
    EditText karOrani;
    EditText vergiOrani;

    ImageView urunResmi;  //-----------
    Button resimSec; //------------
    byte[] bytesImage; //-----------------
    int SELECT_PICTURE = 200; //----------
    EditText alisFiyatiTxt2;  //--------------------
    EditText satisFiyatiTxt2; //---------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urun_guncelle);

        barkodNoTxt = findViewById(R.id.txt_barkod);
        urunAdiTxt = findViewById(R.id.txt_urun_adi);
        karOrani = findViewById(R.id.txt_kar_orani2);
        vergiOrani = findViewById(R.id.txt_vergi_oranı2);
        urunGuncelleBtn = findViewById(R.id.btn_urun_guncelle);
        til = findViewById(R.id.interceptorLayout);
        mp = MediaPlayer.create(this, R.raw.scan_sound);
        urunResmi = findViewById(R.id.img_urun2);
        resimSec = findViewById(R.id.btn_resim_sec);
        urunResmi.setImageResource(R.drawable.box);


        // Barkod numarası güncellenemeyeceği için TextView devre dışı bırakılıyor
        barkodNoTxt.setEnabled(false);
        Intent intent = getIntent();
        barkodNoTxt.setText(intent.getStringExtra("barkod"));
        urunAdiTxt.setText(intent.getStringExtra("urun_adi"));
        vergiOrani.setText(intent.getStringExtra("vergi_orani"));
        String aaa= intent.getStringExtra("vergi_orani");
        karOrani.setText(intent.getStringExtra("kar_orani"));
        bytesImage = intent.getByteArrayExtra("urun_resmi");

        if(bytesImage != null){
            InputStream inputStream  = new ByteArrayInputStream(bytesImage);//----------------------
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);//-------------------
            urunResmi.setImageBitmap(bitmap);//---------------------
        }
        else{
            Bitmap bitmap = ((BitmapDrawable) urunResmi.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bytesImage = baos.toByteArray();
            bytesImage = imagemTratada(bytesImage);
        }

        urunGuncelleBtn.setOnClickListener(e -> urunGuncelle());
        // Barkod güncellenmek için tıklanırsa kullanıcıya hata veriyor
        til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GlideToast.makeToast(UrunGuncelleActivity.this, "Ürünün barkod numarası değiştirilemez.",
                        GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
            }
        });


        resimSec.setOnClickListener(e -> resimGuncelle());
    }

    private void urunGuncelle() {
        String barkod = barkodNoTxt.getText().toString();
        String ad = urunAdiTxt.getText().toString();
        // Alış ve satış fiyatları kuruş şeklinde long değer olarak geliyor. Floata çevrilmesi gerekiyor
        //float alis = alisFiyatiTxt.getRawValue() / (float) 100;
        //float satis = satisFiyatiTxt.getRawValue() / (float) 100;
        //float alis = Float.valueOf(alisFiyatiTxt2.getText().toString());
        //float satis = Float.valueOf(satisFiyatiTxt2.getText().toString());
        String vergioranı = vergiOrani.getText().toString();
        String karoranı = karOrani.getText().toString();


        Bitmap bitmap = ((BitmapDrawable) urunResmi.getDrawable()).getBitmap();//--------------------------
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bytesImage = baos.toByteArray();//------------------------------------
        bytesImage = imagemTratada(bytesImage); // resim boyutunu kontrol et

        // Alanlardan herhangi biri boşsa hata ver
        if (ad.equals("") || vergioranı.equals("") || vergioranı.equals(".") || karoranı.equals("") || karoranı.equals(".")) {
            new GlideToast.makeToast(UrunGuncelleActivity.this, "Lütfen bütün alanları doldurun.",
                    GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
            return;
        }
        float vergioranif = Float.valueOf(vergiOrani.getText().toString());
        float karoranif = Float.valueOf(karOrani.getText().toString());

        Urun urun = new Urun(barkod, ad, bytesImage, vergioranif, karoranif);

        VeritabaniIslemleri vti = new VeritabaniIslemleri(this);

        // Ürün eklenemediyse hata ver
        if (vti.urunGuncelle(urun) != 1) {
            new GlideToast.makeToast(UrunGuncelleActivity.this, "Ürün güncellenemedi.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Ürün eklendiyse giriş alanlarını sil ve ürünün eklendiğini kullanıcıya bildir
        alanlariBosalt();
        new GlideToast.makeToast(UrunGuncelleActivity.this, "Ürün güncellendi.",
                GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        // UrunGuncelleActivity'yi kapatmadan önce GlideToast'ın ekranda kalma süresi kadar beklenilip çıkılmazsa hata veriyor
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, GlideToast.LENGTHTOOLONG);
    }

    private void alanlariBosalt() {
        barkodNoTxt.setText(null);
        urunAdiTxt.setText(null);
        //alisFiyatiTxt.setValue(0);
        //satisFiyatiTxt.setValue(0);
        urunResmi.setImageResource(R.drawable.box);
        //urunResmi.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
        //alisFiyatiTxt2.setText("");
        //satisFiyatiTxt2.setText("");
        karOrani.setText(null);
        vergiOrani.setText(null);
    }

    public void resimGuncelle(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
