package com.example.huseyin.stoktakip.dialogs;

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

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.adapters.IslemGecmisiAdapter;
import com.example.huseyin.stoktakip.models.MergeAlisSatis;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;

import java.util.ArrayList;

public class IadeListesiDialog extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_iade_listesi, null);

        dialog.setView(view).setTitle("Ürün Listesi");

        ListView liste = view.findViewById(R.id.liste_iade);
        Button iptalBtn = view.findViewById(R.id.btn_iptal3);

        Bundle degerler = getArguments();
        String kullaniciId = degerler.getString("kadi");
        int sepetId = degerler.getInt("sepetId");


        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<MergeAlisSatis> alisSatislar = vti.sepetIdyeGoreAlislariSatislariGetir(sepetId);
        IslemGecmisiAdapter adapter;
        adapter = new IslemGecmisiAdapter(getContext(), R.layout.liste_elemani_islem_gecmisi, alisSatislar);
        liste.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String barkodNo = alisSatislar.get(position).getBarkodNo();
                int sepetId = alisSatislar.get(position).getSepetId();
                int sepetAdet = alisSatislar.get(position).getAdet();
                int alisSatisId = alisSatislar.get(position).getId();
                float adetalisfiyati;
                if(vti.sepetIdyeGoreSepetGetir(sepetId).getIslemTuru().equalsIgnoreCase("in")) {
                    adetalisfiyati = (alisSatislar.get(position).getAlisSatisFiyati()+alisSatislar.get(position).getVergi()+alisSatislar.get(position).getKargo()+alisSatislar.get(position).getKargo_vergi())/alisSatislar.get(position).getAdet();
                } else {
                    adetalisfiyati = alisSatislar.get(position).getSatisAdetAlisFiyati();
                }

                Bundle degerler1 = new Bundle();
                degerler1.putString("barkod", barkodNo);
                degerler1.putString("kadi",kullaniciId);
                degerler1.putInt("SepetAdet",sepetAdet);
                degerler1.putInt("sepetId",sepetId);
                degerler1.putInt("alisSatisId",alisSatisId);
                degerler1.putFloat("adetAlisFiyati", adetalisfiyati);

                DialogFragment dialog = new IadeDialog();
                dialog.setArguments(degerler1);
                dialog.show(getActivity().getSupportFragmentManager(), "İade");

            }
        });


        iptalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialog.create();
    }

}
