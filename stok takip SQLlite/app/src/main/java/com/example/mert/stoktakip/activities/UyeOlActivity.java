package com.example.mert.stoktakip.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mert.stoktakip.models.Kullanici;
import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.ByteArrayOutputStream;

public class UyeOlActivity extends AppCompatActivity {

    EditText kadiTxt;
    EditText sifreTxt;
    EditText sifreTekrarTxt;
    ImageView kullanıcıResmi;
    byte[] bytesImage;
    int SELECT_PICTURE = 200;

    Button resimSecBtn;
    Button uyeOlBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uye_ol);

        kullanıcıResmi = findViewById(R.id.img_kullanici2);
        kadiTxt = findViewById(R.id.txt_kadi);
        sifreTxt = findViewById(R.id.txt_sifre);
        sifreTekrarTxt = findViewById(R.id.txt_sifre_tekrar);

        resimSecBtn = findViewById(R.id.btn_resim_sec3);
        uyeOlBtn = findViewById(R.id.btn_uye_ol);
        resimSecBtn.setOnClickListener(e -> resimSec());
        uyeOlBtn.setOnClickListener(e -> uyeOl());
    }

    // Uye ol butonunun click listenerı
    private void uyeOl() {
        String kadi = kadiTxt.getText().toString().trim();//toLowerCase()
        String sifre = sifreTxt.getText().toString();
        String sifreTekrar = sifreTekrarTxt.getText().toString();

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

        Kullanici kullanici = new Kullanici();
        kullanici.setKadi(kadi);
        kullanici.setSifre(sifre);
        kullanici.setResim(bytesImage);

        VeritabaniIslemleri vti = new VeritabaniIslemleri(UyeOlActivity.this);

        // Alanlardan herhangi biri boşsa hata ver
        if (kadi.equals("") || sifre.equals("") || sifreTekrar.equals("")) {
            new GlideToast.makeToast(UyeOlActivity.this, "Lütfen bütün alanları doldurun.",
                    GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
            return;
        }
        // Şifre ile şifre tekrarı aynı değilse hata ver
        else if (!sifre.equals(sifreTekrar)) {
            new GlideToast.makeToast(UyeOlActivity.this, "Girdiğiniz şifreler birbiriyle uyuşmuyor.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Kullanıcı adı daha önceden alınmışsa hata ver
        else if (vti.kullaniciAdiniKontrolEt(kadi)) {
            new GlideToast.makeToast(UyeOlActivity.this, "Böyle bir kullanıcı zaten mevcut.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Kullanıcıyı ekle. Bir hata oluştuysa kullanıcıya bildir
        if (vti.kullaniciEkle(kullanici) == -1) {
            new GlideToast.makeToast(UyeOlActivity.this, "Kayıt olurken bir hata oluştu.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }
        // Hiç bir hata oluşmadıysa kullanıcıya bildir, dolu alanları sil ve anasayfaya geç
        new GlideToast.makeToast(UyeOlActivity.this, "Kaydınız başarıyla tamamlandı!",
                GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        alanlariBosalt();
        // Direk anasayfaya geçilirse GlideToast kapanıyor, o yüzden anasayfaya geçmeden önce GlideToast'un gözükme süresi kadar bekleniyor.
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
        sifreTekrarTxt.setText(null);
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
