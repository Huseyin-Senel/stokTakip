package com.example.mert.stoktakip.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.utils.ZamanFormatlayici;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;
import java.util.Date;

public class SepetGecmisiFiltreleDialog extends AppCompatDialogFragment
        implements DatePickerDialog.OnDateSetListener, TarihSecimiDialog.TarihSecimiDialogListener {

    Button baslangicTarihiBtn;
    Button bitisTarihiBtn;
    Button filtreleBtn;
    MaterialSpinner islemTuruSpinner;

    ZamanFormatlayici zf = new ZamanFormatlayici();
    IslemGecmisiFiltreleDialogListener listener;

    String tur;

    public interface IslemGecmisiFiltreleDialogListener {
        void filtreParametreleriniGetir(String baslangicTarihi, String bitisTarihi, String islemTuru);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_islem_gecmisi_filtre, null);

        dialog.setView(view);

        baslangicTarihiBtn = view.findViewById(R.id.btn_baslangic_tarihi);
        bitisTarihiBtn = view.findViewById(R.id.btn_bitis_tarihi);
        islemTuruSpinner = view.findViewById(R.id.spinner_kargo);
        filtreleBtn = view.findViewById(R.id.btn_filtrele);

        String bugunString = zf.zamanFormatla(new Date(), "d MMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        String dunString = zf.zamanFormatla(calendar, "d MMM yyyy");

        baslangicTarihiBtn.setText(dunString);
        bitisTarihiBtn.setText(bugunString);

        islemTuruSpinner.setItems("T??m??", "Al??m", "Sat??m");

        filtreleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baslangicTarihi = zf.zamanFormatla(baslangicTarihiBtn.getText().toString(), "d MMM yyyy", "yyyy-MM-dd");
                String bitisTarihi = zf.zamanFormatla(bitisTarihiBtn.getText().toString(), "d MMM yyyy", "yyyy-MM-dd");
                String islemTuru = islemTuruSpinner.getItems().get(islemTuruSpinner.getSelectedIndex()).toString();
                Log.i("HA", baslangicTarihi + " " + bitisTarihi + " " + islemTuru);
                listener.filtreParametreleriniGetir(baslangicTarihi, bitisTarihi, islemTuru);
                dismiss();
            }
        });

        baslangicTarihiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle degerler = new Bundle();
                // Ba??lang???? tarihi butonuna bas??l??nca a????lan tarih se??im dialoguna tur de??i??keni
                // olarak "baslangicTarihi" stringi g??nderiliyor. Daha sonra dialogdan de??er d??nd??r??rken
                // bu de??i??kene bak??larak ba??lang???? tarihi mi yoksa biti?? tarihi mi oldu??u anla????lacak
                degerler.putString("tur", "baslangicTarihi");
                degerler.putString("tarih", baslangicTarihiBtn.getText().toString());
                DialogFragment dialog = new TarihSecimiDialog();
                dialog.setArguments(degerler);
                dialog.setTargetFragment(SepetGecmisiFiltreleDialog.this, 0);
                dialog.show(getActivity().getSupportFragmentManager(), "Ba??lang???? Tarihi Se??");
            }
        });

        bitisTarihiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle degerler = new Bundle();
                // Ba??lang???? tarihi butonuna bas??l??nca a????lan tarih se??im dialoguna tur de??i??keni
                // olarak "bitisTarihi" stringi g??nderiliyor. Daha sonra dialogdan de??er d??nd??r??rken
                // bu de??i??kene bak??larak ba??lang???? tarihi mi yoksa biti?? tarihi mi oldu??u anla????lacak
                degerler.putString("tur", "bitisTarihi");
                degerler.putString("tarih", bitisTarihiBtn.getText().toString());
                DialogFragment dialog = new TarihSecimiDialog();
                dialog.setArguments(degerler);
                dialog.setTargetFragment(SepetGecmisiFiltreleDialog.this, 0);
                dialog.show(getActivity().getSupportFragmentManager(), "Biti?? Tarihi Se??");
            }
        });

        return dialog.create();
    }

    // Tarih se??iminin yap??ld?????? dialogdan d??nen tarih de??erleri buraya geliyor
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        String tarih = zf.zamanFormatla(calendar, "d MMM yyyy");

        // E??er ba??lang???? tarihi se??ildiyse ba??lang???? tarihi butonu de??i??iyor, ba??lang????
        // tarihi se??ilmemi??se biti?? tarihi se??ilmi??tir
        if (tur.equals("baslangicTarihi")) {
            baslangicTarihiBtn.setText(tarih);
        } else {
            bitisTarihiBtn.setText(tarih);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (IslemGecmisiFiltreleDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "TarihSecimiDialogListener implement etmek gerekiyor");
        }
    }

    // Tarih se??imi dialogundan t??r?? ??ekiyor
    @Override
    public void turGetir(String tur) {
        this.tur = tur;
    }
}
