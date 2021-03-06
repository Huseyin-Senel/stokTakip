package com.example.huseyin.stoktakip.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.huseyin.stoktakip.models.Kullanici;
import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

public class GirisYapActivity extends AppCompatActivity {

    EditText kadiTxt;
    EditText sifreTxt;
    Button girisYapBtn;
    TextView uyeOlBtn;
    CheckBox bilgileriHatirlaCheckBox;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);//-------------------------------------

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, PackageManager.PERMISSION_GRANTED);

        kadiTxt = findViewById(R.id.txt_kadi);
        sifreTxt = findViewById(R.id.txt_sifre);
        girisYapBtn = findViewById(R.id.btn_giris_yap);
        uyeOlBtn = findViewById(R.id.btn_uye_ol);
        bilgileriHatirlaCheckBox = findViewById(R.id.checkbox_giris_bilgisi_hatirla);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        sharedPreferencesKontrol();

        girisYapBtn.setOnClickListener(e -> girisYap());
        bilgileriHatirlaCheckBox.setOnClickListener(e -> bilgileriHatirla());
        uyeOlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisYapActivity.this, UyeOlActivity.class);
                startActivity(intent);
            }
        });
    }

    // Giri?? yap butonunun click listener'??
    private void girisYap() {
        String kadi = kadiTxt.getText().toString().trim();//.toLowerCase()
        String sifre = sifreTxt.getText().toString();

        Kullanici kullanici = new Kullanici();
        kullanici.setKadi(kadi);
        kullanici.setSifre(sifre);

        VeritabaniIslemleri vti = new VeritabaniIslemleri(GirisYapActivity.this);

        // Alanlardan herhangi biri bo??sa hata ver
        if (kullanici.getKadi().equals("") || kullanici.getSifre().equals("")) {
            new GlideToast.makeToast(GirisYapActivity.this, "L??tfen b??t??n alanlar?? doldurun.",
                    GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
            return;
        }
        // E??er kullan??c?? ad?? ya da ??ifre yanl????sa hata ver
        if (!vti.girisBilgileriniKontrolEt(kullanici)) {
            new GlideToast.makeToast(GirisYapActivity.this, "Kullan??c?? ad?? ya da ??ifre yanl????.",
                    GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            sifreTxt.setText(null);
            return;
        }
        // Giri?? yap??ld??ysa anasayfaya y??nlendir
        alanlariBosalt();
        Intent intent = new Intent(GirisYapActivity.this, AnasayfaActivity.class);
        intent.putExtra("kadi", kullanici.getKadi());
        startActivity(intent);
        finish();
    }

    // Beni hat??rla checkbox'??n??n click listener'??
    private void bilgileriHatirla() {
        if (bilgileriHatirlaCheckBox.isChecked()) {
            editor.putString("checkbox", "True");
            editor.commit();

            String kadi = kadiTxt.getText().toString();
            editor.putString("kadi", kadi);
            editor.commit();

            String sifre = sifreTxt.getText().toString();
            editor.putString("sifre", sifre);
            editor.commit();
        } else {
            editor.putString("checkbox", "False");
            editor.commit();

            editor.putString("kadi", "");
            editor.commit();

            editor.putString("sifre", "");
            editor.commit();
        }
    }

    // Uygulama ilk ??al????t??????nda bu metot ??al??????yor, e??er shared preferences'da kay??tl?? kullan??c?? ad??, ??ifre veya
    // checkbox durumu varsa onlar?? kontrol edip gerekli alanlara ??ekiyor
    private void sharedPreferencesKontrol() {
        String checkBox = preferences.getString("checkbox", "False");
        String kadi = preferences.getString("kadi", "");
        String sifre = preferences.getString("sifre", "");

        kadiTxt.setText(kadi);
        sifreTxt.setText(sifre);

        if (checkBox.equals("True"))
            bilgileriHatirlaCheckBox.setChecked(true);
        else
            bilgileriHatirlaCheckBox.setChecked(false);
    }

    private void alanlariBosalt() {
        sifreTxt.setText(null);
        kadiTxt.setText(null);
    }
}
