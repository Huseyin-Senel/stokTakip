package com.example.huseyin.stoktakip.adapters;

import android.content.Context;
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

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.Iade;
import com.example.huseyin.stoktakip.models.Urun;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.example.huseyin.stoktakip.utils.ZamanFormatlayici;

import java.util.ArrayList;
import java.util.Locale;

public class IadeListesiAdapter extends ArrayAdapter<Iade> {

    private Context context;
    private int resource;
    private int lastPosition = -1;

    private ArrayList<Iade> original;
    private ArrayList<Iade> fitems;
    private Filter filter;

    static class ViewHolder {
        TextView urunAdiTxt;
        TextView barkodNoTxt;
        TextView iadeAdetiTxt;
        TextView iadeTutariTxt;
        TextView gunTxt;
        TextView ayTxt;
        TextView saatTxt;
        TextView kullaniciAdiTxt;
        TextView sepetNoTxt;
        TextView aciklama;
        ImageView islemturuImg;
    }

    public IadeListesiAdapter(@NonNull Context context, int resource, ArrayList<Iade> iadeler) {
        super(context, resource, iadeler);
        this.context = context;
        this.resource = resource;
        this.original = new ArrayList<>(iadeler);
        this.fitems = new ArrayList<>(iadeler);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ZamanFormatlayici zf = new ZamanFormatlayici();
        Urun urun = vti.barkodaGoreUrunGetir(getItem(position).getBarkodNo());

        String urunAdi = urun.getAd();
        String barkodNo = getItem(position).getBarkodNo();
        int iadeAdeti = getItem(position).getAdet();
        float iadeTutari = getItem(position).getIadeTutarı();
        String kullaniciAdi = getItem(position).getKullanıcıId();
        int sepetId = getItem(position).getSepetId();
        String islemYonu = getItem(position).getIadeYonu();
        String tarih = getItem(position).getTarih();
        String ay = zf.zamanFormatla(tarih, "yyyy-MM-dd HH:mm:ss", "MMM");
        String gun = zf.zamanFormatla(tarih, "yyyy-MM-dd HH:mm:ss", "dd");
        String saat = zf.zamanFormatla(tarih, "yyyy-MM-dd HH:mm:ss", "HH:mm");
        String aciklama = getItem(position).getAciklama();



        final View result;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_elemani_iade_listesi, parent, false);

            holder = new ViewHolder();

            holder.urunAdiTxt = convertView.findViewById(R.id.txt_urun_adi);
            holder.barkodNoTxt = convertView.findViewById(R.id.txt_barkod_no);
            holder.iadeAdetiTxt = convertView.findViewById(R.id.txt_adet);
            holder.iadeTutariTxt = convertView.findViewById(R.id.txt_iade_tutari);
            holder.kullaniciAdiTxt = convertView.findViewById(R.id.txt_kullanici);
            holder.sepetNoTxt = convertView.findViewById(R.id.txt_sepet_ıd);
            holder.islemturuImg = convertView.findViewById(R.id.img_islem_turu);
            holder.gunTxt = convertView.findViewById(R.id.txt_gun);
            holder.ayTxt = convertView.findViewById(R.id.txt_ay);
            holder.saatTxt = convertView.findViewById(R.id.txt_saat);
            holder.aciklama = convertView.findViewById(R.id.txt_aciklama2);


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
        holder.barkodNoTxt.setText(barkodNo);
        holder.iadeAdetiTxt.setText(String.valueOf(iadeAdeti));
        holder.iadeTutariTxt.setText(String.format(Locale.US, "%.2f", iadeTutari));//getDefault()
        holder.kullaniciAdiTxt.setText(kullaniciAdi);
        holder.sepetNoTxt.setText(String.valueOf(sepetId));
        if (islemYonu.equalsIgnoreCase("in"))
            holder.islemturuImg.setImageResource(R.drawable.ic_urunal_yesil);
        else
            holder.islemturuImg.setImageResource(R.drawable.ic_urunsat_kirmizi);
        holder.gunTxt.setText(gun);
        holder.ayTxt.setText(ay);
        holder.saatTxt.setText(saat);
        holder.aciklama.setText(aciklama);

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new IadeFilter();

        return filter;
    }

    private class IadeFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String kelime = constraint.toString().toLowerCase();

            if (kelime.equals("") || kelime.length() == 0) {
                ArrayList<Iade> list = new ArrayList<>(original);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<Iade> list = new ArrayList<>(original);
                final ArrayList<Iade> nlist = new ArrayList<>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final Iade iade = list.get(i);

                    VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
                    Urun urun = vti.barkodaGoreUrunGetir(list.get(i).getBarkodNo());

                    final String valueAd = urun.getAd().toLowerCase();
                    final String valueBarkod = iade.getBarkodNo().toLowerCase();

                    if (valueAd.toLowerCase().
                            contains(kelime.toLowerCase()) ||
                            valueBarkod.contains(kelime)) {
                        nlist.add(iade);
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
            fitems = (ArrayList<Iade>) results.values;

            clear();
            int count = fitems.size();
            for (int i = 0; i < count; i++) {
                Iade iade = fitems.get(i);
                add(iade);
            }
        }
    }
}
