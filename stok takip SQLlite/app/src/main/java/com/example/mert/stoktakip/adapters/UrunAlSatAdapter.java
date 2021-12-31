package com.example.mert.stoktakip.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.Stok;
import com.example.mert.stoktakip.models.Urun;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.travijuu.numberpicker.library.Enums.ActionEnum;
import com.travijuu.numberpicker.library.Interface.ValueChangedListener;
import com.travijuu.numberpicker.library.NumberPicker;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class UrunAlSatAdapter extends ArrayAdapter<Urun> {

    TextView urunAdiTxt;
    TextView barkodNoTxt;
    ImageView urunResmi;
    TextView ortGelisTxt;
    TextView karOranTxt;
    EditText fiyatTxt;
    EditText kargoTxt;
    TextView vergiTxt;
    TextView vergiOranTxt;
    TextView kdvFiyatTxt;
    TextView urunToplamTxt;
    NumberPicker numberPicker;
    TextView backValue;
    MaterialSpinner kargoSpinner;


    private Context context;
    private int resource;

    public UrunAlSatAdapter(@NonNull Context context, int resource, ArrayList<Urun> urun) {
        super(context, resource, urun);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(resource, parent, false);

        urunAdiTxt = convertView.findViewById(R.id.txt_urun_adi);
        barkodNoTxt = convertView.findViewById(R.id.txt_barkod_no);
        urunResmi = convertView.findViewById(R.id.urun_resmi);
        ortGelisTxt = convertView.findViewById(R.id.txt_ort_gelis);
        karOranTxt = convertView.findViewById(R.id.txt_kar_oran);
        fiyatTxt = convertView.findViewById(R.id.txt_fiyat);
        kargoTxt = convertView.findViewById(R.id.txt_kargo);
        vergiTxt = convertView.findViewById(R.id.txt_vergi);
        vergiOranTxt = convertView.findViewById(R.id.txt_vergi_oran);
        kdvFiyatTxt = convertView.findViewById(R.id.txt_kdv_fiyat);
        urunToplamTxt = convertView.findViewById(R.id.txt_urun_toplam);
        numberPicker = (NumberPicker) convertView.findViewById(R.id.number_picker);
        backValue = convertView.findViewById(R.id.back_value);
        kargoSpinner = convertView.findViewById(R.id.spinner_kargo);

        numberPicker.setValue(1);
        backValue.setText("1");

        String barkodNo = getItem(position).getBarkodNo();
        String urunAdi = getItem(position).getAd();

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        Stok stok = vti.stokBul(barkodNo);

        urunResmi.setImageResource(R.drawable.box);
        if(getItem(position).getResim()!= null){
            InputStream inputStream  = new ByteArrayInputStream(getItem(position).getResim());
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            urunResmi.setImageBitmap(bitmap);
        }

        karOranTxt.setText(String.format(Locale.US, "%.2f", getItem(position).getKarOrani()));
        vergiOranTxt.setText(String.format(Locale.US, "%.2f", getItem(position).getVergiOrani()));
        kargoTxt.setText(String.format(Locale.US, "%.2f", 0.00));
        float fiyatf = (stok.getAdetOrtOdenenFiyat()*numberPicker.getValue())+((getItem(position).getKarOrani()*(stok.getAdetOrtOdenenFiyat()*numberPicker.getValue()))/100);
        float vergif = (getItem(position).getVergiOrani()*fiyatf)/100;
        fiyatTxt.setText(String.format(Locale.US, "%.2f", fiyatf));
        ortGelisTxt.setText(String.format(Locale.US, "%.2f", stok.getAdetOrtOdenenFiyat()*numberPicker.getValue()));
        vergiTxt.setText(String.format(Locale.US, "%.2f", vergif));
        kdvFiyatTxt.setText(String.format(Locale.US, "%.2f", vergif+fiyatf));
        urunToplamTxt.setText(String.format(Locale.US, "%.2f", fiyatf+vergif+Float.valueOf(kargoTxt.getText().toString())));
        urunAdiTxt.setText(urunAdi);
        barkodNoTxt.setText(barkodNo);

        kargoSpinner.setItems("0","7", "9", "12","16");

        return convertView;
    }

}
