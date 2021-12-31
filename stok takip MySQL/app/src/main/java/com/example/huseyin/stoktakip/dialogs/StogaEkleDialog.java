package com.example.huseyin.stoktakip.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.fragments.IadeListesiFragment;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

public class StogaEkleDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_stoga_ekle, null);

        dialog.setView(view).setTitle("Stoğa Ekle");

        com.travijuu.numberpicker.library.NumberPicker nump = view.findViewById(R.id.number_picker);

        Button onayBtn = view.findViewById(R.id.onay_btn);
        Button iptalBtn = view.findViewById(R.id.iptal_btn);

        Bundle degerler1 = getArguments();
        String barkodNo = degerler1.getString("barkod");
        int iadeStokId = degerler1.getInt("iadeStokId");
        int stokAdet = degerler1.getInt("stokAdet");
        float adetAlisFiyati = degerler1.getFloat("adetAlisFiyati");



        nump.setMax(stokAdet);
        nump.setMin(1);

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getActivity());


        onayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getActivity())
                        .setTitle("Stoğa Ekle")
                        .setMessage("Stoğa eklemeyi onaylıyor musunuz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(vti.iadeStokAzalt(iadeStokId,nump.getValue()) == -1){//Veritabanında 0 ise otomatik siliyor.
                                    new GlideToast.makeToast(getActivity(), "HATA: İade stoğu azaltılamadı.", GlideToast.LENGTHTOOLONG,
                                            GlideToast.FAILTOAST).show();
                                    return;
                                }

                                if(vti.stokArttır(barkodNo,nump.getValue(),adetAlisFiyati*nump.getValue())==-1){
                                    new GlideToast.makeToast(getActivity(), "HATA: Stok arttırılamadı.", GlideToast.LENGTHTOOLONG,
                                            GlideToast.FAILTOAST).show();

                                    if(vti.iadeStokArttır(iadeStokId,nump.getValue())==-1){
                                        new GlideToast.makeToast(getActivity(), "KRİTİK HATA: İade Stoğu arttırılamadı.", GlideToast.LENGTHTOOLONG,
                                                GlideToast.FAILTOAST).show();
                                    }
                                    return;
                                }

                                dismiss();
                                fragmentYenile();
                                new GlideToast.makeToast(getActivity(), "Ürünler Stoğa Eklendi.", GlideToast.LENGTHTOOLONG,
                                        GlideToast.SUCCESSTOAST).show();
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dismiss();
                                fragmentYenile();
                            }
                        })
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
        dismiss();
        Fragment currentFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment instanceof IadeListesiFragment) {
            FragmentTransaction fragTransaction = (getActivity()).getSupportFragmentManager().beginTransaction();
            fragTransaction.detach(currentFragment);
            fragTransaction.attach(currentFragment);
            fragTransaction.commit();
        }
    }

}
