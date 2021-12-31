package com.example.mert.stoktakip.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.adapters.IadeListesiAdapter;
import com.example.mert.stoktakip.adapters.StokListesiAdapter;
import com.example.mert.stoktakip.models.Alis;
import com.example.mert.stoktakip.models.BozukUrun;
import com.example.mert.stoktakip.models.Iade;
import com.example.mert.stoktakip.models.Satis;
import com.example.mert.stoktakip.models.Sepet;
import com.example.mert.stoktakip.models.Stok;
import com.example.mert.stoktakip.models.Urun;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.example.mert.stoktakip.utils.ZamanFormatlayici;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTAutoFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilterColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
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
