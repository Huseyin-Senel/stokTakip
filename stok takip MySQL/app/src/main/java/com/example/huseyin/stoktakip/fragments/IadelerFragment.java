package com.example.huseyin.stoktakip.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.adapters.IadeListesiAdapter;
import com.example.huseyin.stoktakip.models.Iade;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;

import java.util.ArrayList;

public class IadelerFragment extends Fragment {

    SearchView search;
    ListView liste;
    IadeListesiAdapter adapter;
    ArrayList<Iade> iadeler;
    VeritabaniIslemleri vti;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_iadeler, container, false);

        liste = view.findViewById(R.id.liste);
        search = view.findViewById(R.id.search_view);


        vti = new VeritabaniIslemleri(getContext());
        iadeler = vti.butunIadeleriGetir();
        adapter = new IadeListesiAdapter(getContext(), R.layout.liste_elemani_iade_listesi, iadeler);
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

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        iadeler.clear();
        iadeler = vti.butunIadeleriGetir();
        adapter = new IadeListesiAdapter(getContext(), R.layout.liste_elemani_iade_listesi, iadeler);
        liste.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
