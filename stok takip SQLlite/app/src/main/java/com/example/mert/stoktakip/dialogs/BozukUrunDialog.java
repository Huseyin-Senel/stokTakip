package com.example.mert.stoktakip.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.activities.AnasayfaActivity;
import com.example.mert.stoktakip.activities.UrunEkleActivity;
import com.example.mert.stoktakip.activities.UrunGuncelleActivity;
import com.example.mert.stoktakip.fragments.IadeListesiFragment;
import com.example.mert.stoktakip.fragments.StokListesiFragment;
import com.example.mert.stoktakip.models.BozukUrun;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;

public class BozukUrunDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_bozuk_urun, null);

        dialog.setView(view).setTitle("Bozuk Ürün");

        EditText aciklama = view.findViewById(R.id.aciklama_edt);
        com.travijuu.numberpicker.library.NumberPicker nump = view.findViewById(R.id.number_picker);

        Button bozuUrunBtn = view.findViewById(R.id.onay_btn);
        Button iptalBtn = view.findViewById(R.id.iptal_btn);

        Bundle degerler1 = getArguments();
        String barkodNo = degerler1.getString("barkod");
        String kullaniciId = degerler1.getString("kadi");
        int adet = degerler1.getInt("adet");

        int iadeStokId = degerler1.getInt("iadeStokId",0);
        boolean normalStok = degerler1.getBoolean("normalStok");


        bozuUrunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nump.getValue()>adet){
                    new GlideToast.makeToast(getActivity(), "Yeterli sayıda stokta ürün yok!", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }

                if(aciklama.getText().toString().trim().length() == 0){
                    new GlideToast.makeToast(getActivity(), "Açıklama boş bırakılamaz!", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }


                new AlertDialog.Builder(getActivity())
                        .setTitle("Bozuk ürünleri onayla")
                        .setMessage("Bozuk ürünleri onaylıyor musunuz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                VeritabaniIslemleri vti = new VeritabaniIslemleri(getActivity());
                                BozukUrun bozukUrun = new BozukUrun();
                                bozukUrun.setKullanıcıId(kullaniciId);
                                bozukUrun.setBarkodNo(barkodNo);
                                bozukUrun.setAdet(nump.getValue());
                                bozukUrun.setAciklama(aciklama.getText().toString().trim());

                                int bozukUrunId = vti.bozukUrunEkle(bozukUrun);
                                if(bozukUrunId ==-1){
                                    new GlideToast.makeToast(getActivity(), "HATA: bozuk ürün oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                                    return;
                                }
                                if(normalStok){
                                    if(vti.stokBozukUrunAzalt(barkodNo,nump.getValue()) ==-1){

                                        new GlideToast.makeToast(getActivity(), "HATA: stok azaltılamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();

                                        if(vti.bozukUrunIdyeGoreBozukUrunSil(bozukUrunId)==-1){

                                            new GlideToast.makeToast(getActivity(), "KRİTİK HATA: bozuk ürün silinemedi", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                                        }
                                        return;
                                    }
                                }else{
                                    if(vti.iadeStokAzalt(iadeStokId,nump.getValue()) ==-1){

                                        new GlideToast.makeToast(getActivity(), "HATA: stok azaltılamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();

                                        if(vti.bozukUrunIdyeGoreBozukUrunSil(bozukUrunId)==-1){

                                            new GlideToast.makeToast(getActivity(), "KRİTİK HATA: bozuk ürün silinemedi", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                                        }
                                        return;
                                    }
                                }


                                dismiss();
                                fragmentYenile();
                                new GlideToast.makeToast(getActivity(), "Ürünler stoktan kaldırıldı.", GlideToast.LENGTHTOOLONG,
                                        GlideToast.SUCCESSTOAST).show();
                            }
                        })
                        .setNegativeButton("Hayır", null)
                        .show();
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
        }else if(currentFragment instanceof IadeListesiFragment){
            FragmentTransaction fragTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();
        }
    }

}
