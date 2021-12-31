package com.example.huseyin.stoktakip.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.Alis;
import com.example.huseyin.stoktakip.models.Sepet;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.example.huseyin.stoktakip.utils.TouchInterceptorLayout;
import com.example.huseyin.stoktakip.models.Urun;
import com.example.huseyin.stoktakip.adapters.UrunAlSatAdapter;
import com.example.huseyin.stoktakip.activities.BarkodOkuyucuActivity;
import com.example.huseyin.stoktakip.dialogs.UrunListesiDialog;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.jeevandeshmukh.glidetoastlib.GlideToast;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

public class UrunAlFragment extends Fragment implements UrunListesiDialog.UrunListesiDialogListener {
    SearchView search;
    TouchInterceptorLayout til;
    ImageButton barkodBtn;
    TextView sepetiBosaltBtn;
    TextView sepetBosTxt;
    ExpandableHeightListView  liste;
    Button urunAlBtn;
    EditText aciklamaTxt;

    TextView toplamAdet;
    EditText komisyon;
    EditText toplamKargo;
    TextView genelToplam;

    UrunAlSatAdapter adapter;
    MediaPlayer mp;

    ArrayList<Urun> urunler = new ArrayList<>();
    //ArrayList<Integer> urunAdetleri = new ArrayList<>();
    String kadi;

    private SharedPreferences preferences;
    private float kargoVergiOran;

    int backValues[];
    int numberPickers[];
    String fiyatTxts[];
    String vergiOranTxts[];
    String vergiTxts[];
    String kargoTxts[];

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && !s.toString().equalsIgnoreCase("") && !s.toString().equalsIgnoreCase(".")){
                komisyon.removeTextChangedListener(this);
                toplamKargo.removeTextChangedListener(this);


                if(komisyon.getText().hashCode() == s.hashCode()){
                    //if(komisyon.getText().toString().equals("") || komisyon.getText().toString().equals(".")){komisyon.setText("0.00");}
                    if(toplamKargo.getText().toString().equals("") || toplamKargo.getText().toString().equals(".")){toplamKargo.setText("0.00");}

                    if(liste.getCount()>0){
                        float toplam = 0;
                        for(int i=0 ; i<liste.getCount();i++){
                            View view = liste.getChildAt(i);
                            TextView txtV = view.findViewById(R.id.txt_urun_toplam);
                            toplam += Float.valueOf(txtV.getText().toString());
                        }
                        genelToplam.setText(String.format(Locale.US, "%.2f",toplam+Float.valueOf(s.toString()) ));
                    }else{
                        s.clear();
                    }


                }else if(toplamKargo.getText().hashCode() == s.hashCode()){
                    if(komisyon.getText().toString().equals("") || komisyon.getText().toString().equals(".")){komisyon.setText("0.00");}
                    //if(toplamKargo.getText().toString().equals("") || toplamKargo.getText().toString().equals(".")){toplamKargo.setText("0.00");}

                    if(liste.getCount()>0){
                        float ort_kargo = Float.valueOf(s.toString())/liste.getCount();
                        for(int i=0 ; i<liste.getCount();i++){
                            View view = liste.getChildAt(i);
                            EditText txtV = view.findViewById(R.id.txt_kargo);
                            txtV.setText(String.format(Locale.US, "%.2f",ort_kargo));
                        }

                        float toplam = 0;
                        for(int i=0 ; i<liste.getCount();i++){
                            View view = liste.getChildAt(i);
                            TextView txtV = view.findViewById(R.id.txt_urun_toplam);
                            toplam += Float.valueOf(txtV.getText().toString());
                        }
                        genelToplam.setText(String.format(Locale.US, "%.2f",Float.valueOf(komisyon.getText().toString())+toplam ));
                    }else{
                        s.clear();
                    }


                }


                komisyon.addTextChangedListener(this);
                toplamKargo.addTextChangedListener(this);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        kargoVergiOran = preferences.getFloat("kargoVergiOran", 18);

        View v = inflater.inflate(R.layout.fragment_urun_al, container, false);

        search = v.findViewById(R.id.search_view);
        sepetBosTxt = v.findViewById(R.id.txt_bos_sepet);
        liste = v.findViewById(R.id.liste);
        barkodBtn = v.findViewById(R.id.btn_barkod);
        urunAlBtn = v.findViewById(R.id.btn_urun_al);
        sepetiBosaltBtn = v.findViewById(R.id.btn_sepeti_bosalt);
        aciklamaTxt = v.findViewById(R.id.txt_aciklama);
        til = v.findViewById(R.id.interceptorLayout);
        mp = MediaPlayer.create(v.getContext(), R.raw.scan_sound);

        toplamAdet = v.findViewById(R.id.toplam_adet_txt);
        komisyon = v.findViewById(R.id.komisyon_txt);
        toplamKargo = v.findViewById(R.id.toplam_kargo_txt);
        genelToplam = v.findViewById(R.id.genel_toplam_txt);

        Bundle bundle = this.getArguments();
        kadi = bundle.getString("kadi");

        adapter = new UrunAlSatAdapter(getActivity(), R.layout.liste_elemani_urun_al_sat, urunler);
        liste.setAdapter(adapter);
        liste.setExpanded(true);


        urunAlBtn.setOnClickListener(e -> urunAl());
        sepetiBosaltBtn.setOnClickListener(e -> sepetiBosalt());
        barkodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BarkodOkuyucuActivity.class);
                startActivityForResult(intent, 0);
            }
        });
        til.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new UrunListesiDialog();
                dialog.setTargetFragment(UrunAlFragment.this, 0);
                dialog.show(getActivity().getSupportFragmentManager(), "Urun Listesi");
            }
        });

        komisyon.addTextChangedListener(textWatcher);
        komisyon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(komisyon.getText().toString().equals("") || komisyon.getText().toString().equals(".")){komisyon.setText("0.00");}
                }
            }
        });
        toplamKargo.addTextChangedListener(textWatcher);
        toplamKargo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(toplamKargo.getText().toString().equals("") || toplamKargo.getText().toString().equals(".")){toplamKargo.setText("0.00");}
                }
            }
        });

        return v;
    }

    private void urunAl() {
        if(urunler.size()>0) {

            if(toplamKargo.getText().toString().equals("") || toplamKargo.getText().toString().equals(".")
                    || komisyon.getText().toString().equals("") || komisyon.getText().toString().equals(".")){
                new GlideToast.makeToast(getActivity(), "Lütfen boş alanları doldurun.", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                return;
            }
            for(int k = 0; k<liste.getCount();k++){
                View view = liste.getChildAt(k);
                EditText fiyatTxt = view.findViewById(R.id.txt_fiyat);
                TextView vergiOranTxt = view.findViewById(R.id.txt_vergi_oran);
                EditText kargoTxt = view.findViewById(R.id.txt_kargo);

                if(fiyatTxt.getText().toString().equals("") || fiyatTxt.getText().toString().equals(".")
                        || vergiOranTxt.getText().toString().equals("") || vergiOranTxt.getText().toString().equals(".")
                        || kargoTxt.getText().toString().equals("") || kargoTxt.getText().toString().equals(".")){
                    new GlideToast.makeToast(getActivity(), "Lütfen boş alanları doldurun.", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }
            }


            VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());

            Sepet sepet = new Sepet();
            sepet.setKid(kadi);
            sepet.setIslemTuru("in");
            sepet.setAdet(Integer.valueOf(toplamAdet.getText().toString()));
            sepet.setToplamFiyat(Float.valueOf(genelToplam.getText().toString()));
            sepet.setToplamKargo(Float.valueOf(toplamKargo.getText().toString()));
            sepet.setKomisyon(Float.valueOf(komisyon.getText().toString()));
            sepet.setAciklama(aciklamaTxt.getText().toString());

            int sepetId = vti.SepetOlustur(sepet);

            if (sepetId == -1) {
                new GlideToast.makeToast(getActivity(), "Sepet ekleme hatası.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                return;
            }

            for (int i = 0; i < urunler.size(); i++) {
                View view = liste.getChildAt(i);

                NumberPicker nump = view.findViewById(R.id.number_picker);
                int adet = nump.getValue();
                TextView vergiTxt = view.findViewById(R.id.txt_vergi);
                float vergi = Float.valueOf(vergiTxt.getText().toString());
                EditText kargoTxt = view.findViewById(R.id.txt_kargo);
                float Tkargo = Float.valueOf(kargoTxt.getText().toString());
                float kargo = (Tkargo*100)/(100+kargoVergiOran);
                float kargo_vergi = (kargo/100)*kargoVergiOran;
                kargo = round(kargo,2);
                kargo_vergi = round(kargo_vergi,2);
                EditText fiyatTxt = view.findViewById(R.id.txt_fiyat);
                float fiyat = Float.valueOf(fiyatTxt.getText().toString());
                TextView vergiOranTxt = view.findViewById(R.id.txt_vergi_oran);
                float vergiOran = Float.valueOf(vergiOranTxt.getText().toString());

                Alis alis = new Alis();
                alis.setSepetId(sepetId);
                alis.setBarkodNo(urunler.get(i).getBarkodNo());
                alis.setAdet(adet);
                alis.setAlisFiyati(fiyat);
                alis.setVergi(vergi);
                alis.setVergiOran(vergiOran);
                alis.setKargo(kargo);
                alis.setKargo_vergi(kargo_vergi);
                alis.setKargo_vergi_oran(kargoVergiOran);

                if (vti.alisEkle(alis) == -1) {
                    new GlideToast.makeToast(getActivity(), "Ürün alma hatası.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    if(vti.sepetIdyeGoreAlıslariSil(sepetId)==-1){
                        new GlideToast.makeToast(getActivity(), "Kritik hata: Alis silme hatası.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    }
                    if(vti.sepetIdyeGoreSepetiSil(sepetId)==-1){
                        new GlideToast.makeToast(getActivity(), "Kritik hata: Sepet silme hatası.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    }
                    return;
                }

                float urunfiyat = alis.getAlisFiyati()+alis.getKargo();
                if (vti.stokArttır(alis.getBarkodNo(),alis.getAdet(),urunfiyat)==-1) {
                    new GlideToast.makeToast(getActivity(), "Adet güncelleme hatası.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    //Önceki STOKLARI SİLME EKLE
                    if(vti.sepetIdyeGoreAlıslariSil(sepetId)==-1){
                        new GlideToast.makeToast(getActivity(), "Kritik hata: Alis silme hatası.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    }
                    if(vti.sepetIdyeGoreSepetiSil(sepetId)==-1){
                        new GlideToast.makeToast(getActivity(), "Kritik hata: Sepet silme hatası.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    }
                    return;
                }

                new GlideToast.makeToast(getActivity(), "Alım başarılı.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();

            }
            sepetiBosalt();
        }
    }

    // Barkod tarayıcı kapanınca gelen barkoda sahip ürünü sepete ekliyor
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("barcode");
                    VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
                    Urun urun = vti.barkodaGoreUrunGetir(barcode.displayValue);
                    // Eğer barkodu okutulan ürün veritabanında yoksa hata ver
                    if (!vti.urunTekrariKontrolEt(urun.getBarkodNo())) {
                        new GlideToast.makeToast(getActivity(), "Ürün bulunamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    }
                    // Eğer seçilen ürün zaten sepette varsa hata ver
                    else if (urunSepetteEkliMi(urun)) {
                        new GlideToast.makeToast(getActivity(), "Ürün zaten sepette ekli.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    }
                    // Eğer ürünün barkodu çekilemediyse hata ver
                    else if (urun.getBarkodNo() == null) {
                        new GlideToast.makeToast(getActivity(), "Hata.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                    } else {


                        //Önceki verileri kaydet
                        kayıt();

                        //urunAdetleriniKaydet();
                        urunler.add(urun);
                        //urunAdetleriniGetir();
                        sepetiBosaltBtn.setVisibility(View.VISIBLE);
                        sepetBosTxt.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                        mp.start();

                        //Yenile
                        liste.post(new Runnable() {
                            @Override
                            public void run() {
                                yenile();
                            }
                        });

                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean urunSepetteEkliMi(Urun urun) {
        for (int i = 0; i < urunler.size(); i++) {
            if (urunler.get(i).getBarkodNo().equals(urun.getBarkodNo()))
                return true;
        }
        return false;
    }

    // UrunListesiDialog'dan barkod bilgisini çekip sepete ekler
    @Override
    public void barkodGetir(String barkod) {
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        Urun urun = vti.barkodaGoreUrunGetir(barkod);
        // Eğer seçilen ürün zaten sepette varsa hata veriyor
        if (urunSepetteEkliMi(urun)) {
            new GlideToast.makeToast(getActivity(), "Ürün zaten sepette ekli.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
            return;
        }


        kayıt();

        //urunAdetleriniKaydet();
        urunler.add(urun);
        //urunAdetleriniGetir();
        sepetiBosaltBtn.setVisibility(View.VISIBLE);
        sepetBosTxt.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();

        liste.post(new Runnable() {
            @Override
            public void run() {
                yenile();
            }
        });

    }

    private void sepetiBosalt() {
        sepetiBosaltBtn.setVisibility(View.INVISIBLE);
        urunler.clear();
        adapter.clear();
        liste.setAdapter(adapter);
        sepetBosTxt.setVisibility(View.VISIBLE);
        aciklamaTxt.setText("");

        adapter.notifyDataSetChanged();

        liste.post(new Runnable() {
            @Override
            public void run() {
                yenile();
            }
        });
    }


    public void kayıt(){
        backValues = new int[liste.getCount()];
        numberPickers = new int[liste.getCount()];
        fiyatTxts = new String[liste.getCount()];
        vergiOranTxts = new String[liste.getCount()];
        vergiTxts = new String[liste.getCount()];
        kargoTxts = new String[liste.getCount()];
        for(int i=0 ; i<liste.getCount();i++){
            View view = liste.getChildAt(i);
            TextView bvalue = view.findViewById(R.id.back_value);
            backValues[i] = Integer.valueOf(bvalue.getText().toString());
            NumberPicker nump = view.findViewById(R.id.number_picker);
            numberPickers[i] = nump.getValue();
            EditText edt1 = view.findViewById(R.id.txt_fiyat);
            fiyatTxts[i] = edt1.getText().toString();
            TextView edt2 = view.findViewById(R.id.txt_vergi_oran);
            vergiOranTxts[i] = edt2.getText().toString();
            TextView edt3 = view.findViewById(R.id.txt_vergi);
            vergiTxts[i] = edt3.getText().toString();
            EditText edt4 = view.findViewById(R.id.txt_kargo);
            kargoTxts[i] = edt4.getText().toString();
        }
    }

    public void yenile(){
        if(toplamKargo.getText().toString().equals("") || toplamKargo.getText().toString().equals(".")){toplamKargo.setText("0.00");}
        if(komisyon.getText().toString().equals("") || komisyon.getText().toString().equals(".")){komisyon.setText("0.00");}

        if(liste.getCount()>0){
            int toplamadet = 0;
            float toplam = 0;
            float toplamkargo = 0;

            for(int i=0 ; i<liste.getCount();i++) {

                View view = liste.getChildAt(i);
                EditText fiyatTxt = view.findViewById(R.id.txt_fiyat);
                EditText kargoTxt = view.findViewById(R.id.txt_kargo);
                EditText kdvFiyat = view.findViewById(R.id.txt_kdv_fiyat);
                TextView vergiOranTxt = view.findViewById(R.id.txt_vergi_oran);
                TextView vergiTxt = view.findViewById(R.id.txt_vergi);
                TextView urunToplamTxt = view.findViewById(R.id.txt_urun_toplam);
                TextView backValue = view.findViewById(R.id.back_value);
                //TextView ortGelisTxt = view.findViewById(R.id.txt_ort_gelis);
                MaterialSpinner kargoSpinner= view.findViewById(R.id.spinner_kargo);
                NumberPicker nump = view.findViewById(R.id.number_picker);

                fiyatTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if(fiyatTxt.getText().toString().equals("") || fiyatTxt.getText().toString().equals(".")){fiyatTxt.setText("0.00");}
                        }
                    }
                });
                kargoTxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if(kargoTxt.getText().toString().equals("") || kargoTxt.getText().toString().equals(".")){kargoTxt.setText("0.00");}
                        }
                    }
                });
                kdvFiyat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
                            if(kdvFiyat.getText().toString().equals("") || kdvFiyat.getText().toString().equals(".")){kdvFiyat.setText("0.00");}
                        }
                    }
                });

                TextWatcher textWatcher1 = new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s != null && !s.toString().equalsIgnoreCase("") && !s.toString().equalsIgnoreCase(".")){
                            kdvFiyat.removeTextChangedListener(this);
                            fiyatTxt.removeTextChangedListener(this);
                            kargoTxt.removeTextChangedListener(this);

                            if(fiyatTxt.getText().hashCode() == s.hashCode()){
                                float vergif = (Float.valueOf(vergiOranTxt.getText().toString())*Float.valueOf(s.toString()))/100;
                                vergiTxt.setText(String.format(Locale.US, "%.2f", vergif));
                                kdvFiyat.setText(String.format(Locale.US, "%.2f", vergif+Float.valueOf(s.toString())));
                                urunToplamTxt.setText(String.format(Locale.US, "%.2f", Float.valueOf(s.toString())+vergif+Float.valueOf(kargoTxt.getText().toString())));
                            }
                            else if(kdvFiyat.getText().hashCode() == s.hashCode()){
                                float Tfiyat = Float.valueOf(kdvFiyat.getText().toString());
                                float fiyat = (Tfiyat*100)/(100+Float.valueOf(vergiOranTxt.getText().toString()));
                                float fiyat_vergi = (fiyat/100)*Float.valueOf(vergiOranTxt.getText().toString());

                                fiyatTxt.setText(String.format(Locale.US, "%.2f", fiyat));
                                vergiTxt.setText(String.format(Locale.US, "%.2f",fiyat_vergi));
                                urunToplamTxt.setText(String.format(Locale.US, "%.2f", Tfiyat+Float.valueOf(kargoTxt.getText().toString())));
                            }
                            else if(kargoTxt.getText().hashCode() == s.hashCode()){
                                urunToplamTxt.setText(String.format(Locale.US, "%.2f", Float.valueOf(fiyatTxt.getText().toString())+Float.valueOf(vergiTxt.getText().toString())+Float.valueOf(s.toString())));
                            }

                            float toplam = 0;
                            float toplamkargo = 0;
                            for(int i=0 ; i<liste.getCount();i++){
                                View view = liste.getChildAt(i);
                                EditText kargoTxt = view.findViewById(R.id.txt_kargo);
                                TextView urunToplamTxt = view.findViewById(R.id.txt_urun_toplam);

                                toplam += Float.valueOf(urunToplamTxt.getText().toString());
                                toplamkargo += Float.valueOf(kargoTxt.getText().toString());
                            }
                            genelToplam.setText(String.format(Locale.US, "%.2f",toplam + Float.valueOf(komisyon.getText().toString()) ));
                            if(!toplamKargo.hasFocus()){
                                toplamKargo.removeTextChangedListener(textWatcher);
                                toplamKargo.setText(String.format(Locale.US, "%.2f",toplamkargo ));
                                toplamKargo.addTextChangedListener(textWatcher);
                            }

                            kdvFiyat.addTextChangedListener(this);
                            fiyatTxt.addTextChangedListener(this);
                            kargoTxt.addTextChangedListener(this);
                        }
                    }
                };
                nump.setValueChangedListener(new ValueChangedListener(){
                    @Override
                    public void valueChanged(int value, ActionEnum action) {
                        int backvalue = Integer.valueOf(backValue.getText().toString());

                        //ortGelisTxt.setText(String.format(Locale.US, "%.2f", stok.getAdetOrtOdenenFiyat()*value));
                        float fiyatf = (Float.valueOf(fiyatTxt.getText().toString())/backvalue)*value;
                        fiyatTxt.setText(String.format(Locale.US, "%.2f", fiyatf));
                        float vergif = (Float.valueOf(vergiOranTxt.getText().toString())*fiyatf)/100;
                        vergiTxt.setText(String.format(Locale.US, "%.2f", vergif));
                        kdvFiyat.setText(String.format(Locale.US, "%.2f", vergif+fiyatf));
                        kargoTxt.setText(String.format(Locale.US, "%.2f", (Float.valueOf(kargoTxt.getText().toString())/backvalue)*value));

                        int toplamadet1 = 0;
                        float toplamkargo1 = 0;
                        for(int i=0 ; i<liste.getCount();i++){
                            View view = liste.getChildAt(i);
                            NumberPicker nump = view.findViewById(R.id.number_picker);
                            toplamadet1 += nump.getValue();

                            EditText kargoTxt = view.findViewById(R.id.txt_kargo);
                            toplamkargo1 += Float.valueOf(kargoTxt.getText().toString());
                        }
                        toplamAdet.setText(String.valueOf(toplamadet1));
                        if(toplamKargo.hasFocus()){
                            toplamKargo.removeTextChangedListener(textWatcher);
                            toplamKargo.setText(String.format(Locale.US, "%.2f",toplamkargo1));
                            toplamKargo.addTextChangedListener(textWatcher);
                        }

                        backValue.setText(String.valueOf(value));
                    }
                });

                fiyatTxt.addTextChangedListener(textWatcher1);
                kdvFiyat.addTextChangedListener(textWatcher1);
                kargoTxt.addTextChangedListener(textWatcher1);

                kargoSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        kargoTxt.setText(kargoSpinner.getItems().get(position).toString());

                        if(toplamKargo.hasFocus()){
                            float toplamkargo2 = 0;
                            for(int i=0 ; i<liste.getCount();i++){
                                View view2 = liste.getChildAt(i);
                                EditText kargoTxt = view2.findViewById(R.id.txt_kargo);
                                toplamkargo2 += Float.valueOf(kargoTxt.getText().toString());
                            }
                            toplamKargo.removeTextChangedListener(textWatcher);
                            toplamKargo.setText(String.format(Locale.US, "%.2f",toplamkargo2));
                            toplamKargo.addTextChangedListener(textWatcher);
                        }
                    }
                });

                if(i<liste.getCount()-1){
                    backValue.setText(String.valueOf(backValues[i]));
                    nump.setValue(numberPickers[i]);
                    fiyatTxt.setText(fiyatTxts[i]);
                    vergiOranTxt.setText(vergiOranTxts[i]);
                    vergiTxt.setText(vergiTxts[i]);
                    kargoTxt.setText(kargoTxts[i]);
                }
                toplamadet += nump.getValue();
                toplam += Float.valueOf(urunToplamTxt.getText().toString());
                toplamkargo += Float.valueOf(kargoTxt.getText().toString());
            }
            genelToplam.setText(String.format(Locale.US, "%.2f",toplam + Float.valueOf(komisyon.getText().toString()) ));
            toplamAdet.setText(String.valueOf(toplamadet));

            if(toplamKargo.hasFocus()){
                toplamKargo.removeTextChangedListener(textWatcher);
                toplamKargo.setText(String.format(Locale.US, "%.2f",toplamkargo ));
                toplamKargo.addTextChangedListener(textWatcher);
            }

        }else{
            toplamAdet.setText("0");
            komisyon.setText("0.00");
            toplamKargo.setText("0.00");
            genelToplam.setText("0.00");
        }
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
