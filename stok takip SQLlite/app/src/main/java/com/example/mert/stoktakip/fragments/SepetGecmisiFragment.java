package com.example.mert.stoktakip.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.adapters.SepetGecmisiAdapter;
import com.example.mert.stoktakip.dialogs.SepetBilgileriDialog;
import com.example.mert.stoktakip.dialogs.SepetGecmisiFiltreleDialog;
import com.example.mert.stoktakip.models.Sepet;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.example.mert.stoktakip.utils.ZamanFormatlayici;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SepetGecmisiFragment extends Fragment implements SepetGecmisiFiltreleDialog.IslemGecmisiFiltreleDialogListener {

    MaterialSpinner spinner;
    ImageButton filtreleBtn;
    ListView liste;

    SepetGecmisiAdapter adapter;
    VeritabaniIslemleri vti;

    ArrayList<Sepet> sepetler = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sepet_gecmisi, container, false);

        liste = v.findViewById(R.id.liste);
        spinner = v.findViewById(R.id.spinner_filtre_araligi);
        filtreleBtn = v.findViewById(R.id.btn_filtre);

        vti = new VeritabaniIslemleri(getContext());
        adapter = new SepetGecmisiAdapter(getContext(), R.layout.liste_elemani_sepet_gecmisi, sepetler);
        liste.setAdapter(adapter);

        Bundle bundle = getArguments();
        String kullaniciId = bundle.getString("kadi");

        spinner.setItems("Son 1 Gün", "Son 1 Hafta", "Son 1 Ay", "Son 3 Ay", "Bütün Kayıtlar");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        sepetGecmisiGetir(1, calendar);

        filtreleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new SepetGecmisiFiltreleDialog();
                dialog.setTargetFragment(SepetGecmisiFragment.this, 0);
                dialog.show(getActivity().getSupportFragmentManager(), "Sepet Geçmişi Filtrele");
            }
        });

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                switch (position) {
                    case 0:
                        sepetGecmisiGetir(1, cal);
                        break;
                    case 1:
                        sepetGecmisiGetir(7, cal);
                        break;
                    case 2:
                        sepetGecmisiGetir(30, cal);
                        break;
                    case 3:
                        sepetGecmisiGetir(90, cal);
                        break;
                    case 4:
                        sepetler.clear();
                        sepetler = vti.sepetGecmisiFiltrele();
                        adapter = new SepetGecmisiAdapter(getContext(), R.layout.liste_elemani_sepet_gecmisi, sepetler);
                        liste.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        });

        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int SepetId = sepetler.get(position).getSepetId();
                Bundle degerler = new Bundle();
                degerler.putInt("sepet_id", SepetId);
                degerler.putString("kadi",kullaniciId);

                DialogFragment dialog = new SepetBilgileriDialog();
                dialog.setArguments(degerler);
                dialog.show(getActivity().getSupportFragmentManager(), "Sepet Bilgileri");
            }
        });
        return v;
    }

    // Gelen Calender objesini istenilen gün kadar geriye götürür, sonra bugün ile o Calender
    // objesinin tuttuğu tarih arasında yapılmış işlem geçmişini getirir
    private void sepetGecmisiGetir(int gunFiltre, Calendar calendar) {
        calendar.add(Calendar.DATE, (-1) * gunFiltre);
        sepetler.clear();
        sepetler = vti.sepetGecmisiFiltrele((new ZamanFormatlayici()).zamanFormatla(calendar, "yyyy-MM-dd"));
        adapter = new SepetGecmisiAdapter(getContext(), R.layout.liste_elemani_sepet_gecmisi, sepetler);
        liste.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    // IslemGecmisiFiltreleDialog'dan gelen değerleri çekip bu değerlere göre listeyi filtreler
    @Override
    public void filtreParametreleriniGetir(String baslangicTarihi, String bitisTarihi, String islemTuru) {
        sepetler.clear();
        if (islemTuru.equals("Tümü"))
            sepetler = vti.sepetGecmisiFiltrele(baslangicTarihi, bitisTarihi);
        else if (islemTuru.equals("Alım"))
            sepetler = vti.sepetGecmisiFiltrele(baslangicTarihi, bitisTarihi, "in");
        else
            sepetler = vti.sepetGecmisiFiltrele(baslangicTarihi, bitisTarihi, "out");
        adapter = new SepetGecmisiAdapter(getContext(), R.layout.liste_elemani_sepet_gecmisi, sepetler);
        liste.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
