package com.example.mert.stoktakip.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.Kullanici;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.example.mert.stoktakip.utils.TouchInterceptorLayout;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class KullaniciGuncelleActivity extends AppCompatActivity {

    TouchInterceptorLayout til;
    EditText kadiTxt;
    EditText sifreTxt;
    EditText yeniSifreTxt;
    EditText yeniSifreTekrarTxt;
    ImageView kullanıcıResmi;

    byte[] bytesImage;
    int SELECT_PICTURE = 200;

    Button resimSecBtn;
    Button guncelleBtn;


    String kadi;

    VeritabaniIslemleri vti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_guncelle);


        til = findViewById(R.id.interceptorLayout);
        kadiTxt = findViewById(R.id.txt_kadi);
        sifreTxt = findViewById(R.id.txt_sifre);
        yeniSifreTxt = findViewById(R.id.txt_yeni_sifre);
        yeniSifreTekrarTxt = findViewById(R.id.txt_yeni_sifre_tekrari);
        kullanıcıResmi = findViewById(R.id.img_kullanici);

        resimSecBtn = findViewById(R.id.btn_resim_sec2);
        guncelleBtn = findViewById(R.id.btn_kullanici_guncelle);

        vti = new VeritabaniIslemleri(KullaniciGuncelleActivity.this);

        Intent intent = getIntent();
        kadi = intent.getStringExtra("kadi");

        kadiTxt.setEnabled(false);
        kadiTxt.setText(kadi);

        VeritabaniIslemleri vti = new VeritabaniIslemleri(this);
        bytesImage = vti.kullaniciAdinaGoreKullaniciyiGetir(kadi).getResim();
        InputStream inputStream  = new ByteArrayInputStream(bytesImage);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        kullanıcıResmi.setImageBitmap(bitmap);

        til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GlideToast.makeToast(KullaniciGuncelleActivity.this, "Kullanıcı adı değiştirilemez.",
                        GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
            }
        });
        resimSecBtn.setOnClickListener(e -> resimSec());
        guncelleBtn.setOnClickListener(e -> kullaniciGuncelle());
    }

    private void kullaniciGuncelle() {
        Bitmap bitmap = ((BitmapDrawable) kullanıcıResmi.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bytesImage = baos.toByteArray();
        bytesImage = imagemTratada(bytesImage); // resim boyutunu kontrol et

        if(bytesImage == null){
            kullanıcıResmi.setImageResource(R.drawable.profil_resmi);
            bitmap = ((BitmapDrawable) kullanıcıResmi.getDrawable()).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            bytesImage = baos.toByteArray();
            bytesImage = imagemTratada(bytesImage);
        }

        Kullanici kullanici = new Kullanici(kadi, sifreTxt.getText().toString(), bytesImage);
        Kullanici yeniKullanici = new Kullanici(kadi, yeniSifreTxt.getText().toString(), bytesImage);
        // Eğer herhangi bir alan boş bırakıldıysa hata ver
        if (sifreTxt.getText().toString().equals("") || yeniSifreTxt.getText().toString().equals("") || yeniSifreTekrarTxt.getText().toString().equals("")) {
            new GlideToast.makeToast(KullaniciGuncelleActivity.this, "Lütfen bütün alanları doldurun.",
                    GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
            return;
        }
        // Eğer kullanıcı şifresi yanlışsa hata ver
        if (!vti.girisBilgileriniKontrolEt(kullanici)) {
            new GlideToast.makeToast(KullaniciGuncelleActivity.this, "Girdiğiniz şifre yanlış.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            sifreTxt.setText(null);
            return;
        }
        // Güncellenecek şifre, şifre tekrarıyla uyuşmuyorsa hata ver
        if (!yeniSifreTxt.getText().toString().equals(yeniSifreTekrarTxt.getText().toString())) {
            new GlideToast.makeToast(KullaniciGuncelleActivity.this, "Girdiğiniz şifreler birbiriyle uyuşmuyor.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Güncelleme sonrasında veritabanında değişen satır sayısı 1den azsa hata ver
        if (vti.kullaniciGuncelle(yeniKullanici) < 1) {
            new GlideToast.makeToast(KullaniciGuncelleActivity.this, "Hata oluştu.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Hata oluşmadıysa şifrenin güncellendiğini kullanıcıya bildir
        new GlideToast.makeToast(KullaniciGuncelleActivity.this, "Bilgileriniz güncellendi.",
                GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();

        alanlariBosalt();
        // GlideToast'ın ekrandan gitmesini bekleyip activity'yi sonlandır
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, GlideToast.LENGTHTOOLONG);

    }

    private void alanlariBosalt() {
        kadiTxt.setText(null);
        sifreTxt.setText(null);
        yeniSifreTxt.setText(null);
        yeniSifreTekrarTxt.setText(null);
        kullanıcıResmi.setImageResource(R.drawable.profil_resmi);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_PICTURE) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {

                if (requestCode == SELECT_PICTURE) {

                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        kullanıcıResmi.setImageURI(selectedImageUri);
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void resimSec(){
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
