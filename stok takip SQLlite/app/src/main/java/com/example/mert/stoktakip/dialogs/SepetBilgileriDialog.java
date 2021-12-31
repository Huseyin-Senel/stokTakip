package com.example.mert.stoktakip.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.adapters.IslemGecmisiAdapter;
import com.example.mert.stoktakip.models.MergeAlisSatis;
import com.example.mert.stoktakip.models.Sepet;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.example.mert.stoktakip.utils.ZamanFormatlayici;

import java.util.ArrayList;
import java.util.Locale;

public class SepetBilgileriDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_sepet_bilgisi, null);

        dialog.setView(view).setTitle("Sepet Detayı");

        TextView sepetNoTxt = view.findViewById(R.id.txt_sepet_id);
        TextView islemTuruTxt = view.findViewById(R.id.txt_islem_turu2);
        TextView kullaniciTxt = view.findViewById(R.id.txt_kullanici2);
        TextView tarih = view.findViewById(R.id.txt_tarih);
        TextView aciklamaTxt = view.findViewById(R.id.txt_aciklama);
        TextView toplamAdet = view.findViewById(R.id.txt_adet2);
        TextView komisyon = view.findViewById(R.id.txt_komisyon);
        TextView toplamKargo = view.findViewById(R.id.txt_toplam_kargo);
        TextView genelToplam = view.findViewById(R.id.txt_genel_toplam);
        ListView liste = view.findViewById(R.id.liste);

        Button iptalBtn = view.findViewById(R.id.btn_iptal2);
        Button iadeBtn = view.findViewById(R.id.btn_iade2);

        IslemGecmisiAdapter adapter;


        Bundle degerler = getArguments();
        int SepetId = degerler.getInt("sepet_id");
        String kadi= degerler.getString("kadi");

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());

        Sepet sepet = vti.sepetIdyeGoreSepetGetir(SepetId);
        ArrayList<MergeAlisSatis> alisSatislar = vti.sepetIdyeGoreAlislariSatislariGetir(SepetId);

        sepetNoTxt.setText(String.valueOf(sepet.getSepetId()));
        if (sepet.getIslemTuru().equals("in")) {
            islemTuruTxt.setText("Alım");
            islemTuruTxt.setTextColor(0xFF00FF00);
        } else {
            islemTuruTxt.setText("Satım");
            islemTuruTxt.setTextColor(0xFFFF0000);
        }
        kullaniciTxt.setText(sepet.getKid());
        tarih.setText(sepet.getIslemTarihi());
        aciklamaTxt.setText(sepet.getAciklama());
        toplamAdet.setText(String.valueOf(sepet.getAdet()));
        komisyon.setText(String.format(Locale.US, "%.2f", sepet.getKomisyon()));
        toplamKargo.setText(String.format(Locale.US, "%.2f", sepet.getToplamKargo()));
        genelToplam.setText(String.format(Locale.US, "%.2f", sepet.getToplamFiyat()));


        adapter = new IslemGecmisiAdapter(getContext(), R.layout.liste_elemani_islem_gecmisi, alisSatislar);
        liste.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        iptalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        iadeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle degerler = new Bundle();
                degerler.putInt("sepetId", SepetId);
                degerler.putString("kadi",kadi);


                DialogFragment dialog = new IadeListesiDialog();
                dialog.setArguments(degerler);
                dialog.show(getActivity().getSupportFragmentManager(), "İade");

            }
        });


        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String barkodNo = alisSatislar.get(position).getBarkodNo();
                int sepetId = alisSatislar.get(position).getSepetId();
                Bundle degerler = new Bundle();
                degerler.putString("barkod_no", barkodNo);
                degerler.putInt("sepet_ıd",sepetId);

                DialogFragment dialog = new UrunIslemiBilgileriDialog();
                dialog.setArguments(degerler);
                dialog.show(getActivity().getSupportFragmentManager(), "Ürün Bilgileri");
            }
        });

        return dialog.create();
    }
}
