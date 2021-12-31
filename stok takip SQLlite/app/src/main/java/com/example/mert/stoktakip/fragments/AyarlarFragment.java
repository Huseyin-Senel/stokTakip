package com.example.mert.stoktakip.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.activities.AnasayfaActivity;
import com.example.mert.stoktakip.activities.KullaniciGuncelleActivity;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

import me.himanshusoni.quantityview.QuantityView;

public class AyarlarFragment extends Fragment {

    QuantityView quantityView;
    Button kaydetBtn;
    Button kullaniciAyarlariBtn;
    EditText kargoVergiOranıTxt;
    ImageView kullanıcıResimAnasayfa;

    int varsayilanEsik = 10;
    float varsayilanKargoVergiOrani = 18;
    String kadi;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ayarlar, container, false);


        quantityView = v.findViewById(R.id.quantity_view);
        kaydetBtn = v.findViewById(R.id.btn_kaydet);
        kullaniciAyarlariBtn = v.findViewById(R.id.btn_kullanici_guncelle);
        kargoVergiOranıTxt = v.findViewById(R.id.txt_kargo_oran);

        Bundle bundle = getArguments();
        kadi = bundle.getString("kadi");

        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = preferences.edit();

        quantityView.setQuantity(preferences.getInt("esik", varsayilanEsik));
        kargoVergiOranıTxt.setText(String.format(Locale.US, "%.2f",preferences.getFloat("kargoVergiOran",varsayilanKargoVergiOrani)));

        kaydetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int esik = quantityView.getQuantity();
                if (esik != preferences.getInt("esik", varsayilanEsik)) {
                    editor.putInt("esik", esik);
                    editor.apply();
                }
                float kargoVO = Float.valueOf(new DecimalFormat("#.##").format(Float.valueOf(kargoVergiOranıTxt.getText().toString())));
                if (kargoVO != preferences.getFloat("kargoVergiOran", varsayilanKargoVergiOrani)) {
                    editor.putFloat("kargoVergiOran", kargoVO);
                    editor.apply();
                }

                new GlideToast.makeToast(getActivity(), "Ayarlar güncellendi.",
                        GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
            }
        });

        kullaniciAyarlariBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KullaniciGuncelleActivity.class);
                intent.putExtra("kadi", kadi);
                startActivityForResult(intent, 10);
            }
        });

        return v;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 10) {
            kullanıcıResimAnasayfa =  (ImageView) ((Activity) getContext()).findViewById(R.id.img_kullanici3);

            VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
            byte[] bytesImage = vti.kullaniciAdinaGoreKullaniciyiGetir(kadi).getResim();
            InputStream inputStream  = new ByteArrayInputStream(bytesImage);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            kullanıcıResimAnasayfa.setImageBitmap(bitmap);

            if (resultCode == CommonStatusCodes.SUCCESS) {

            }
        }
    }

}
