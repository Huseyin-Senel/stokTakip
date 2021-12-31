package com.example.huseyin.stoktakip.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.MergeAlisSatis;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class IslemGecmisiAdapter extends ArrayAdapter<MergeAlisSatis> {

    private Context context;
    private int resource;
    private int lastPosition = -1;

    static class ViewHolder {
        TextView urunAdiTxt;
        TextView barkodTxt;
        TextView kargoTxt;
        TextView fiyatTxt;
        TextView urunAdetiTxt;
        ImageView urunResmi;
    }

    public IslemGecmisiAdapter(@NonNull Context context, int resource, ArrayList<MergeAlisSatis> islemler) {
        super(context, resource, islemler);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());

        float kargo = getItem(position).getKargo()+getItem(position).getKargo_vergi();
        float toplamFiyat = getItem(position).getAlisSatisFiyati()+getItem(position).getVergi()+kargo;
        int urunAdeti = getItem(position).getAdet();
        String barkod = getItem(position).getBarkodNo();
        String urunAdi = (vti.barkodaGoreUrunGetir(barkod)).getAd();
        // Eğer veritabanında barkod no'dan ürün adı bulunmadıysa ürün silinmiş demektir
        if (urunAdi == null || urunAdi.equals("")) {
            urunAdi = "Silinmiş ürün";
        }
        byte[] resim = (vti.barkodaGoreUrunGetir(barkod)).getResim();

        final View result;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_elemani_islem_gecmisi, parent, false);

            holder = new IslemGecmisiAdapter.ViewHolder();

            holder.barkodTxt = convertView.findViewById(R.id.txt_sepet_ıd);
            holder.urunAdiTxt = convertView.findViewById(R.id.txt_urun_adi);
            holder.urunAdetiTxt = convertView.findViewById(R.id.txt_adet);
            holder.kargoTxt = convertView.findViewById(R.id.txt_kargo2);
            holder.fiyatTxt = convertView.findViewById(R.id.txt_fiyat2);
            holder.urunResmi = convertView.findViewById(R.id.img_resim);

            result = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;



        holder.urunAdiTxt.setText(urunAdi);
        holder.barkodTxt.setText(barkod);
        holder.urunAdetiTxt.setText(String.valueOf(urunAdeti));
        holder.kargoTxt.setText(String.format(Locale.US, "%.2f", kargo));
        holder.fiyatTxt.setText(String.format(Locale.US, "%.2f", toplamFiyat));
        if(resim!= null){
            InputStream inputStream  = new ByteArrayInputStream(resim);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            holder.urunResmi.setImageBitmap(bitmap);
        }

        return convertView;
    }
}
