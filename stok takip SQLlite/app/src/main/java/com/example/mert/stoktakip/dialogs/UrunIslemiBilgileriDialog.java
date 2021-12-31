package com.example.mert.stoktakip.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.MergeAlisSatis;
import com.example.mert.stoktakip.models.Sepet;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.example.mert.stoktakip.utils.ZamanFormatlayici;

import java.util.Locale;

public class UrunIslemiBilgileriDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_urun_islemi_bilgisi, null);

        dialog.setView(view).setTitle("Ürün Detayı");

        TextView urunAdiTxt = view.findViewById(R.id.txt_urun_adi);
        TextView barkodNoTxt = view.findViewById(R.id.txt_barkod_no);
        TextView sepetNoTxt = view.findViewById(R.id.txt_sepet_no);
        TextView islemTuruTxt = view.findViewById(R.id.txt_islem_turu);
        TextView urunAdetiTxt = view.findViewById(R.id.txt_urun_adeti);
        TextView urunFiyatiTxt = view.findViewById(R.id.txt_urun_fiyati);
        TextView kargoFiyatiTxt = view.findViewById(R.id.txt_kargo_fiyati);
        TextView kargoVergiOrani = view.findViewById(R.id.txt_kargo_vergi_orani);
        TextView kargoVergi = view.findViewById(R.id.txt_kargo_vergi);
        TextView vergiOraniTxt = view.findViewById(R.id.txt_vergi_orani3);
        TextView vergiTxt = view.findViewById(R.id.txt_vergi3);
        TextView toplamTxt = view.findViewById(R.id.txt_toplam3);
        TextView kullaniciTxt = view.findViewById(R.id.txt_kullanici);
        TextView gunTxt = view.findViewById(R.id.txt_gun);
        TextView ayTxt = view.findViewById(R.id.txt_ay);
        TextView saatTxt = view.findViewById(R.id.txt_saat);
        Button iptalBtn = view.findViewById(R.id.btn_iptal);

        Bundle degerler = getArguments();
        String barkod_no = degerler.getString("barkod_no");
        int urunSepetId = degerler.getInt("sepet_ıd");

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ZamanFormatlayici zf = new ZamanFormatlayici();

        MergeAlisSatis alisSatis = vti.sepetIdBarkodNoyaGoreAlısSatısıGetir(urunSepetId,barkod_no);
        Sepet sepet = vti.sepetIdyeGoreSepetGetir(urunSepetId);

        String urunAdi = (vti.barkodaGoreUrunGetir(alisSatis.getBarkodNo())).getAd();
        // Eğer veritabanında barkod no'dan ürün adı bulunmadıysa ürün silinmiş demektir
        if (urunAdi == null || urunAdi.equals(""))
            urunAdi = "Silinmiş Ürün";
        urunAdiTxt.setText(urunAdi);
        barkodNoTxt.setText(alisSatis.getBarkodNo());
        if (sepet.getIslemTuru().equals("in")) {
            islemTuruTxt.setText("Alım");
        } else {
            islemTuruTxt.setText("Satım");
        }
        urunAdetiTxt.setText(String.valueOf(alisSatis.getAdet()));
        kullaniciTxt.setText(sepet.getKid());
        sepetNoTxt.setText(String.valueOf(sepet.getSepetId()));
        urunFiyatiTxt.setText(String.format(Locale.US, "%.2f", alisSatis.getAlisSatisFiyati()));
        vergiOraniTxt.setText(String.format(Locale.US, "%.2f", alisSatis.getVergiOran()));
        vergiTxt.setText(String.format(Locale.US, "%.2f", alisSatis.getVergi()));
        kargoFiyatiTxt.setText(String.format(Locale.US, "%.2f", alisSatis.getKargo()));
        kargoVergiOrani.setText(String.format(Locale.US, "%.2f", alisSatis.getKargo_vergi_oran()));
        kargoVergi.setText(String.format(Locale.US, "%.2f", alisSatis.getKargo_vergi()));
        float toplam = alisSatis.getKargo_vergi()+alisSatis.getKargo()+alisSatis.getVergi()+alisSatis.getAlisSatisFiyati();
        toplamTxt.setText(String.format(Locale.US, "%.2f", toplam));
        gunTxt.setText(zf.zamanFormatla(sepet.getIslemTarihi(), "yyyy-MM-dd HH:mm:ss", "dd"));
        ayTxt.setText(zf.zamanFormatla(sepet.getIslemTarihi(), "yyyy-MM-dd HH:mm:ss", "MMM"));
        saatTxt.setText(zf.zamanFormatla(sepet.getIslemTarihi(), "yyyy-MM-dd HH:mm:ss", "HH:mm"));

        iptalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog.create();
    }
}
