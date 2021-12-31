package com.example.huseyin.stoktakip.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.Iade;
import com.example.huseyin.stoktakip.models.IadeStok;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.jeevandeshmukh.glidetoastlib.GlideToast;

public class IadeDialog extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_iade, null);

        dialog.setView(view).setTitle("İade Detayı");

        EditText aciklama = view.findViewById(R.id.aciklama_edt);
        EditText iadeTutar = view.findViewById(R.id.iade_tutar_edt);
        com.travijuu.numberpicker.library.NumberPicker nump = view.findViewById(R.id.number_picker);

        Button onayBtn = view.findViewById(R.id.onay_btn);
        Button iptalBtn = view.findViewById(R.id.iptal_btn);

        Bundle degerler1 = getArguments();
        String barkodNo = degerler1.getString("barkod");
        String kullaniciId = degerler1.getString("kadi");
        int Sepetadet = degerler1.getInt("SepetAdet");
        int sepetId = degerler1.getInt("sepetId");
        int alisSatisId = degerler1.getInt("alisSatisId");
        float adetAlisFiyati = degerler1.getFloat("adetAlisFiyati");

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getActivity());


        onayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nump.getValue()>Sepetadet){
                    new GlideToast.makeToast(getActivity(), "Sepette yeterli sayıda ürün yok!", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }

                if(nump.getValue()>Sepetadet-vti.iadeAdetiniGetir(sepetId,alisSatisId)){
                    new GlideToast.makeToast(getActivity(), "Daha önce " + vti.iadeAdetiniGetir(sepetId,alisSatisId) + " Adet ürün iade edilmiş", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }

                if(aciklama.getText().toString().trim().length() == 0){
                    new GlideToast.makeToast(getActivity(), "Açıklama boş bırakılamaz!", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }

                if(iadeTutar.getText().toString().equals("") || iadeTutar.getText().toString().equals(".")){
                    new GlideToast.makeToast(getActivity(), "Lütfen geçerli bir tutar girin.", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }

                String qq = String.valueOf(vti.sepetIdyeGoreSepetGetir(sepetId).getIslemTuru());
                if(vti.stokBul(barkodNo).getAdet()<nump.getValue() && qq.equalsIgnoreCase("in")){
                    new GlideToast.makeToast(getActivity(), "Stokta yeterli ürün yok.", GlideToast.LENGTHTOOLONG, GlideToast.INFOTOAST).show();
                    return;
                }

                new AlertDialog.Builder(getActivity())
                        .setTitle("İadeyi onayla")
                        .setMessage("İadeyi onaylıyor musunuz?")
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Iade iade = new Iade();
                                String aa = String.valueOf(vti.sepetIdyeGoreSepetGetir(sepetId).getIslemTuru());
                                if(aa.equalsIgnoreCase("in")){
                                    iade.setIadeYonu("out");
                                }else{
                                    iade.setIadeYonu("in");
                                }
                                iade.setKullanıcıId(kullaniciId);
                                iade.setSepetId(sepetId);
                                iade.setAlisSatisId(alisSatisId);
                                iade.setBarkodNo(barkodNo);
                                iade.setAdet(nump.getValue());
                                iade.setIadeTutarı(Float.valueOf(iadeTutar.getText().toString()));
                                iade.setAciklama(aciklama.getText().toString().trim());

                                int iadeId = vti.iadeEkle(iade);
                                if(iadeId ==-1){
                                    new GlideToast.makeToast(getActivity(), "HATA: iade oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                                    return;
                                }

                                if(iade.getIadeYonu()=="out"){
                                    if(vti.stokAzalt(barkodNo,iade.getAdet()) ==-1){

                                        new GlideToast.makeToast(getActivity(), "HATA: stok azaltılamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();

                                        if(vti.iadeIdyeGoreIadeyiSil(iadeId)==-1){

                                            new GlideToast.makeToast(getActivity(), "KRİTİK HATA: iade silinemedi", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                                        }
                                        return;
                                    }
                                }else{
                                    IadeStok iadeStok = new IadeStok();
                                    iadeStok.setIadeId(iadeId);
                                    iadeStok.setBarkodNo(barkodNo);
                                    iadeStok.setAdet(nump.getValue());
                                    iadeStok.setAdetAlisFiyati(adetAlisFiyati);

                                    if(vti.iadeStokOlustur(iadeStok) ==-1){

                                        new GlideToast.makeToast(getActivity(), "HATA: İade Stoğu Oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();

                                        if(vti.iadeIdyeGoreIadeyiSil(iadeId)==-1){

                                            new GlideToast.makeToast(getActivity(), "KRİTİK HATA: iade silinemedi", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
                                        }
                                        return;
                                    }
                                }
                                dismiss();
                                fragmentYenile();
                                new GlideToast.makeToast(getActivity(), "Ürünler iade edildi.", GlideToast.LENGTHTOOLONG,
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
    }

}
