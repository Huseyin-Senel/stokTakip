package com.example.mert.stoktakip.adapters;

import android.content.Context;
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

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.Sepet;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.example.mert.stoktakip.utils.ZamanFormatlayici;

import java.util.ArrayList;
import java.util.Locale;

public class SepetGecmisiAdapter extends ArrayAdapter<Sepet> {
    private Context context;
    private int resource;
    private int lastPosition = -1;

    static class ViewHolder {
        ImageView islemTuruTxt;
        TextView kadiTxt;
        TextView toplamUrunAdetiTxt;
        TextView ayTxt;
        TextView gunTxt;
        TextView saatTxt;
        TextView sepetIdTxt;
        TextView komisyonTxt;
        TextView kargoTxt;
        TextView fiyatTxt;
    }

    public SepetGecmisiAdapter(@NonNull Context context, int resource, ArrayList<Sepet> sepetler) {
        super(context, resource, sepetler);
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ZamanFormatlayici zf = new ZamanFormatlayici();


        int sepetId = getItem(position).getSepetId();
        String islemTuru = getItem(position).getIslemTuru();
        String kadi = getItem(position).getKid();
        int urunAdeti = getItem(position).getAdet();
        String aciklma = getItem(position).getAciklama();
        float toplamKargo = getItem(position).getToplamKargo();
        float toplamFiyat = getItem(position).getToplamFiyat();
        float komisyon = getItem(position).getKomisyon();
        // Eğer veritabanında barkod no'dan ürün adı bulunmadıysa ürün silinmiş demektir
        if (aciklma == null || aciklma.equals("")) {
            aciklma = "Açıklama yok.";
        }
        // Veritabanından tarih "yyyy-MM-dd HH:mm:ss" formatında geliyor
        // ZamanFormatlayici classını kullanarak tarih; ay, gün, saat ve dakika olarak parçalanıyor
        String islemTarihi = getItem(position).getIslemTarihi();
        String ay = zf.zamanFormatla(islemTarihi, "yyyy-MM-dd HH:mm:ss", "MMM");
        String gun = zf.zamanFormatla(islemTarihi, "yyyy-MM-dd HH:mm:ss", "dd");
        String saat = zf.zamanFormatla(islemTarihi, "yyyy-MM-dd HH:mm:ss", "HH:mm");

        final View result;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_elemani_sepet_gecmisi, parent, false);

            holder = new SepetGecmisiAdapter.ViewHolder();

            holder.islemTuruTxt = convertView.findViewById(R.id.img_islem_turu);
            holder.toplamUrunAdetiTxt = convertView.findViewById(R.id.txt_adet);
            holder.kadiTxt = convertView.findViewById(R.id.txt_kullanici);
            holder.ayTxt = convertView.findViewById(R.id.txt_ay);
            holder.gunTxt = convertView.findViewById(R.id.txt_gun);
            holder.saatTxt = convertView.findViewById(R.id.txt_saat);
            holder.sepetIdTxt = convertView.findViewById(R.id.txt_sepet_ıd);
            holder.kargoTxt = convertView.findViewById(R.id.kargo_txt2);
            holder.komisyonTxt = convertView.findViewById(R.id.komisyon_txt2);
            holder.fiyatTxt = convertView.findViewById(R.id.fiyat_txt2);

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

        if (islemTuru.equals("in"))
            holder.islemTuruTxt.setImageResource(R.drawable.ic_urunal_yesil);
        else
            holder.islemTuruTxt.setImageResource(R.drawable.ic_urunsat_kirmizi);


        holder.kadiTxt.setText(kadi);
        holder.toplamUrunAdetiTxt.setText(String.valueOf(urunAdeti));
        holder.ayTxt.setText(ay);
        holder.gunTxt.setText(gun);
        holder.saatTxt.setText(saat);
        holder.sepetIdTxt.setText(String.valueOf(sepetId));
        holder.kargoTxt.setText(String.format(Locale.US, "%.2f", toplamKargo));
        holder.komisyonTxt.setText(String.format(Locale.US, "%.2f", komisyon));
        holder.fiyatTxt.setText(String.format(Locale.US, "%.2f", toplamFiyat));

        return convertView;
    }
}

