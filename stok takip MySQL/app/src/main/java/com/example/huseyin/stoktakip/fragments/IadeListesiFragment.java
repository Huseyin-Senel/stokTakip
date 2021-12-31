package com.example.huseyin.stoktakip.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.adapters.IadeStokListesiAdapter;
import com.example.huseyin.stoktakip.models.IadeStok;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;

import java.util.ArrayList;

public class IadeListesiFragment extends Fragment {

    SearchView search;
    ListView liste;

    IadeStokListesiAdapter adapter;
    VeritabaniIslemleri vti;

    ArrayList<IadeStok> iadeler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_iade_stok_listesi, container, false);

        search = v.findViewById(R.id.search_view);
        liste = v.findViewById(R.id.liste);

        vti = new VeritabaniIslemleri(getContext());
        iadeler = vti.butunIadeStoklarıGetir();
        adapter = new IadeStokListesiAdapter(getContext(), R.layout.liste_elemani_iade_stok_listesi, iadeler, this);
        liste.setAdapter(adapter);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {/*
                Iade iade = iadeler.get(position);
                Urun urun = vti.barkodaGoreUrunGetir(iade.getBarkodNo());

                Bundle kid = getArguments();
                String kullanıcıId = kid.getString("kadi");

                Bundle degerler = new Bundle();
                degerler.putString("urun_adi", urun.getAd());
                degerler.putString("barkod", urun.getBarkodNo());
                degerler.putFloat("vergi_orani", urun.getVergiOrani());
                degerler.putFloat("kar_orani", urun.getKarOrani());
                degerler.putInt("adet", iade.getAdet());
                degerler.putFloat("ort_alis_fiyati", iade.getOrtOdenenFiyat());
                degerler.putFloat("adet_ort_alis_fiyati", iade.getAdetOrtOdenenFiyat());
                degerler.putByteArray("urun_resmi",urun.getResim());
                degerler.putString("kadi",kullanıcıId);

                DialogFragment dialog = new UrunBilgileriDialog();
                dialog.setArguments(degerler);
                dialog.show(getActivity().getSupportFragmentManager(), "Ürün Bilgileri");*/
            }
        });

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        iadeler.clear();
        iadeler = vti.butunIadeStoklarıGetir();
        adapter = new IadeStokListesiAdapter(getContext(), R.layout.liste_elemani_iade_stok_listesi, iadeler, this);
        liste.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public String kullaniciAdiGetir(){
        Bundle bundle = this.getArguments();
        return bundle.getString("kadi");
    }

}