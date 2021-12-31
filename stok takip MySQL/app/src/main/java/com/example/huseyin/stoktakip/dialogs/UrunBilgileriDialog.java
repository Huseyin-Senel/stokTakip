package com.example.huseyin.stoktakip.dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.activities.UrunGuncelleActivity;
import com.example.huseyin.stoktakip.fragments.StokListesiFragment;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

public class UrunBilgileriDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_urun_bilgisi, null);

        dialog.setView(view).setTitle("Ürün Bilgileri");

        TextView urunAdiTxt = view.findViewById(R.id.txt_urun_adi);
        TextView barkodNoTxt = view.findViewById(R.id.txt_barkod_no);
        TextView urunAdetiTxt = view.findViewById(R.id.txt_urun_adeti);
        ImageView urunResim = view.findViewById(R.id.img_urun_resmi2);
        TextView istenilenSatisFiyatiTxt = view.findViewById(R.id.txt_istenilen_satis_fiyati);
        TextView istenilenAdetSatisFiyatiTxt = view.findViewById(R.id.txt_adet_istenilen_satis_fiyati);
        TextView adetOrtAlisFiyatiTxt = view.findViewById(R.id.txt_adet_ort_alis_fiyati);
        TextView OrtAlisFiyatiTxt = view.findViewById(R.id.txt_ort_alis_fiyati);
        TextView vergiOraniTxt = view.findViewById(R.id.txt_vergi_orani);
        TextView karOraniTxt = view.findViewById(R.id.txt_kar_orani);
        Button bozuUrunBtn = view.findViewById(R.id.btn_bozuk_urun);
        Button silBtn = view.findViewById(R.id.btn_sil);
        Button guncelleBtn = view.findViewById(R.id.btn_guncelle);
        Button iptalBtn = view.findViewById(R.id.btn_iptal);

        Bundle degerler = getArguments();
        String urunAdi = degerler.getString("urun_adi");
        String barkodNo = degerler.getString("barkod");
        float vergiOrani = degerler.getFloat("vergi_orani");
        float karOrani = degerler.getFloat("kar_orani");
        int urunAdeti = degerler.getInt("adet");
        float ortAlisFiyati = degerler.getFloat("ort_alis_fiyati");
        float adetOrtAlisFiyati = degerler.getFloat("adet_ort_alis_fiyati");
        byte[] urunResmi = degerler.getByteArray("urun_resmi");
        String kullaniciId = degerler.getString("kadi");

        urunAdiTxt.setText(urunAdi);
        barkodNoTxt.setText(barkodNo);
        urunAdetiTxt.setText(String.valueOf(urunAdeti));
        vergiOraniTxt.setText(String.format(Locale.US, "%.2f", vergiOrani));
        karOraniTxt.setText(String.format(Locale.US, "%.2f", karOrani));

        InputStream inputStream  = new ByteArrayInputStream(urunResmi);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        urunResim.setImageBitmap(bitmap);

        // Alış ve satış fiyatlarının noktadan sonraki iki basamağı gösterilmesi için (13.20 tl gibi)
        // formatlama işlemi yapılıyor
        istenilenSatisFiyatiTxt.setText(String.format(Locale.US, "%.2f", ((ortAlisFiyati*karOrani/100)+ortAlisFiyati)));
        istenilenAdetSatisFiyatiTxt.setText(String.format(Locale.US, "%.2f", ((adetOrtAlisFiyati*karOrani/100)+adetOrtAlisFiyati))); //getDefault()
        OrtAlisFiyatiTxt.setText(String.format(Locale.US, "%.2f", ortAlisFiyati));
        adetOrtAlisFiyatiTxt.setText(String.format(Locale.US, "%.2f", adetOrtAlisFiyati));


        bozuUrunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle degerler1 = new Bundle();
                degerler1.putString("barkod", barkodNo);
                degerler1.putString("kadi", kullaniciId);
                degerler1.putInt("adet",urunAdeti);
                degerler1.putBoolean("normalStok",true);

                DialogFragment dialog = new BozukUrunDialog();
                dialog.setArguments(degerler1);
                dialog.show(getActivity().getSupportFragmentManager(), "Bozuk Ürün");
            }
        });
        silBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Ürün Sil")
                        .setMessage("Bu ürünü ve stoğu silmek istediğinizden emin misiniz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                VeritabaniIslemleri vti = new VeritabaniIslemleri(getActivity());
                                vti.stokSil(barkodNo);
                                vti.urunSil(barkodNo);
                                dismiss();
                                fragmentYenile();
                                new GlideToast.makeToast(getActivity(), "Ürün silindi.", GlideToast.LENGTHTOOLONG,
                                        GlideToast.SUCCESSTOAST).show();
                            }
                        })
                        .setNegativeButton("Hayır", null)
                        .show();
            }
        });
        guncelleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UrunGuncelleActivity.class);
                intent.putExtra("barkod", barkodNo);
                intent.putExtra("urun_adi", urunAdi);
                intent.putExtra("ort_alis_fiyati", String.format(Locale.US, "%.2f", ortAlisFiyati));  //getDefault()
                intent.putExtra("adet_ort_alis_fiyati", String.format(Locale.US, "%.2f", adetOrtAlisFiyati));
                intent.putExtra("urun_resmi", urunResmi);
                intent.putExtra("vergi_orani", String.format(Locale.US, "%.2f", vergiOrani));
                intent.putExtra("kar_orani", String.format(Locale.US, "%.2f", karOrani));
                startActivity(intent);
                dismiss();
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

    private void fragmentYenile() {
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof StokListesiFragment) {
            FragmentTransaction fragTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();
        }
    }

}
