package com.example.mert.stoktakip.adapters;

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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.Stok;
import com.example.mert.stoktakip.models.Urun;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class StokListesiAdapter extends ArrayAdapter<Stok> {

    private Context context;
    private int resource;
    private int lastPosition = -1;

    private ArrayList<Stok> original;
    private ArrayList<Stok> fitems;
    private Filter filter;

    static class ViewHolder {
        TextView urunAdiTxt;
        TextView barkodNoTxt;
        TextView urunAdetiTxt;
        TextView istenilenSatisFiyatiTxt;
        TextView istenilenAdetSatisFiyatiTxt;
        TextView vergiOrani;
        TextView karOrani;
        TextView adetOrtAlisFiyatiTxt;
        TextView ortAlisFiyatıTxt;
        ImageView urunResmiImg;
    }

    public StokListesiAdapter(@NonNull Context context, int resource, ArrayList<Stok> stoklar) {
        super(context, resource, stoklar);
        this.context = context;
        this.resource = resource;
        this.original = new ArrayList<>(stoklar);
        this.fitems = new ArrayList<>(stoklar);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        Urun urun = vti.barkodaGoreUrunGetir(getItem(position).getBarkodNo());

        int stokId = getItem(position).getStokId();
        String barkodNo = getItem(position).getBarkodNo();
        String urunAdi = urun.getAd();
        int urunAdeti = getItem(position).getAdet();

        float ortAlisFiyati = getItem(position).getOrtOdenenFiyat();
        float adetOrtAlisFiyati = getItem(position).getAdetOrtOdenenFiyat();

        byte[] urunResmiByt = urun.getResim();

        Stok stok = new Stok(stokId, barkodNo,urunAdeti,ortAlisFiyati,adetOrtAlisFiyati);

        final View result;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_elemani_stok_listesi, parent, false);

            holder = new ViewHolder();

            holder.urunAdiTxt = convertView.findViewById(R.id.txt_urun_adi);
            holder.barkodNoTxt = convertView.findViewById(R.id.txt_barkod_no);
            holder.urunAdetiTxt = convertView.findViewById(R.id.txt_urun_adeti);

            holder.istenilenSatisFiyatiTxt = convertView.findViewById(R.id.txt_istenilen_satis_fiyati);
            holder.istenilenAdetSatisFiyatiTxt = convertView.findViewById(R.id.txt_adet_istenilen_satis_fiyati);

            holder.ortAlisFiyatıTxt = convertView.findViewById(R.id.txt_ort_alis_fiyati);
            holder.adetOrtAlisFiyatiTxt = convertView.findViewById(R.id.txt_adet_ort_alis_fiyati);

            holder.vergiOrani = convertView.findViewById(R.id.txt_vergi_orani);
            holder.karOrani = convertView.findViewById(R.id.txt_kar_orani);

            holder.urunResmiImg = convertView.findViewById(R.id.img_urun_resmi);

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

        holder.urunAdiTxt.setText(urun.getAd());
        holder.barkodNoTxt.setText(urun.getBarkodNo());
        holder.urunAdetiTxt.setText(String.valueOf(stok.getAdet()));
        // Alış ve satış fiyatlarının noktadan sonraki iki basamağı gösterilmesi için (13.20 tl gibi)
        // formatlama işlemi yapılıyor

        holder.istenilenAdetSatisFiyatiTxt.setText(String.format(Locale.US, "%.2f", ((stok.getAdetOrtOdenenFiyat()*urun.getKarOrani()/100)+stok.getAdetOrtOdenenFiyat())));
        holder.istenilenSatisFiyatiTxt.setText(String.format(Locale.US, "%.2f", ((stok.getOrtOdenenFiyat()*urun.getKarOrani()/100)+stok.getOrtOdenenFiyat())));

        holder.adetOrtAlisFiyatiTxt.setText(String.format(Locale.US, "%.2f", stok.getAdetOrtOdenenFiyat()));//getDefault()
        holder.ortAlisFiyatıTxt.setText(String.format(Locale.US, "%.2f", stok.getOrtOdenenFiyat()));

        holder.vergiOrani.setText(String.format(Locale.US, "%.2f", urun.getVergiOrani()));
        holder.karOrani.setText(String.format(Locale.US, "%.2f", urun.getKarOrani()));

        holder.urunResmiImg.setImageResource(R.drawable.box);

        if(urun.getResim()!= null){
            InputStream inputStream  = new ByteArrayInputStream(urun.getResim());
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            holder.urunResmiImg.setImageBitmap(bitmap);
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new StokFilter();

        return filter;
    }

    private class StokFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String kelime = constraint.toString().toLowerCase();

            if (kelime.equals("") || kelime.length() == 0) {
                ArrayList<Stok> list = new ArrayList<>(original);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<Stok> list = new ArrayList<>(original);
                final ArrayList<Stok> nlist = new ArrayList<>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final Stok stok = list.get(i);

                    VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
                    Urun urun = vti.barkodaGoreUrunGetir(list.get(i).getBarkodNo());

                    final String valueAd = urun.getAd().toLowerCase();
                    final String valueBarkod = stok.getBarkodNo().toLowerCase();

                    if (valueAd.toLowerCase().
                            contains(kelime.toLowerCase()) ||
                            valueBarkod.contains(kelime)) {
                        nlist.add(stok);
                    }
                }
                results.values = nlist;
                results.count = nlist.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            fitems = (ArrayList<Stok>) results.values;

            clear();
            int count = fitems.size();
            for (int i = 0; i < count; i++) {
                Stok stok = fitems.get(i);
                add(stok);
            }
        }
    }
}
