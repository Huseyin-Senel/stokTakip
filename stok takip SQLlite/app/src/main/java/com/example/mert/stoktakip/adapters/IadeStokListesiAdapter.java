package com.example.mert.stoktakip.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.dialogs.BozukUrunDialog;
import com.example.mert.stoktakip.dialogs.StogaEkleDialog;
import com.example.mert.stoktakip.fragments.IadeListesiFragment;
import com.example.mert.stoktakip.models.IadeStok;
import com.example.mert.stoktakip.models.Urun;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class IadeStokListesiAdapter extends ArrayAdapter<IadeStok> {

    private Context context;
    private int resource;
    private int lastPosition = -1;

    private ArrayList<IadeStok> original;
    private ArrayList<IadeStok> fitems;
    private Filter filter;
    private Fragment fragment;

    static class ViewHolder {
        TextView urunAdiTxt;
        TextView barkodNoTxt;
        TextView urunAdetiTxt;
        TextView adetAlisFiyatiTxt;
        ImageView urunResmiImg;
        Button stokEkleBtn;
        Button bozukUrunBtn;
    }

    public IadeStokListesiAdapter(@NonNull Context context, int resource, ArrayList<IadeStok> iadeler, Fragment fragment) {
        super(context, resource, iadeler);
        this.context = context;
        this.resource = resource;
        this.original = new ArrayList<>(iadeler);
        this.fitems = new ArrayList<>(iadeler);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        Urun urun = vti.barkodaGoreUrunGetir(getItem(position).getBarkodNo());

        String barkodNo = getItem(position).getBarkodNo();
        String urunAdi = urun.getAd();
        int iadeAdeti = getItem(position).getAdet();
        float adetAlisFiyati = getItem(position).getAdetAlisFiyati();
        byte[] urunResmiByt = urun.getResim();
        int iadeStokId = getItem(position).getId();


        final View result;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.liste_elemani_iade_stok_listesi, parent, false);

            holder = new ViewHolder();

            holder.urunAdiTxt = convertView.findViewById(R.id.txt_urun_adi);
            holder.barkodNoTxt = convertView.findViewById(R.id.txt_barkod_no);
            holder.urunAdetiTxt = convertView.findViewById(R.id.txt_urun_adeti);
            holder.adetAlisFiyatiTxt = convertView.findViewById(R.id.txt_iade_tutari);
            holder.urunResmiImg = convertView.findViewById(R.id.img_urun_resmi);
            holder.stokEkleBtn = convertView.findViewById(R.id.btn_stok_ekle);
            holder.bozukUrunBtn = convertView.findViewById(R.id.btn_bozuk_urun2);

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
        holder.urunAdetiTxt.setText(String.valueOf(iadeAdeti));
        holder.adetAlisFiyatiTxt.setText(String.format(Locale.US, "%.2f", adetAlisFiyati));//getDefault()

        holder.urunResmiImg.setImageResource(R.drawable.box);
        if(urunResmiByt!= null){
            InputStream inputStream  = new ByteArrayInputStream(urunResmiByt);
            Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
            holder.urunResmiImg.setImageBitmap(bitmap);
        }

        holder.stokEkleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle degerler1 = new Bundle();
                degerler1.putString("barkod", barkodNo);
                degerler1.putInt("iadeStokId", getItem(position).getId());
                degerler1.putInt("stokAdet",iadeAdeti);
                degerler1.putFloat("adetAlisFiyati",adetAlisFiyati);

                AppCompatActivity activity = (AppCompatActivity) context;
                DialogFragment dialog = new StogaEkleDialog();
                dialog.setArguments(degerler1);
                dialog.show(activity.getSupportFragmentManager(), "Stok Ekle");
            }
        });

        holder.bozukUrunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle degerler1 = new Bundle();
                degerler1.putString("barkod", barkodNo);
                degerler1.putInt("adet",iadeAdeti);
                degerler1.putBoolean("normalStok",false);
                degerler1.putInt("iadeStokId",iadeStokId);
                degerler1.putString("kadi",((IadeListesiFragment)fragment).kullaniciAdiGetir());

                AppCompatActivity activity = (AppCompatActivity) context;
                DialogFragment dialog = new BozukUrunDialog();
                dialog.setArguments(degerler1);
                dialog.show(activity.getSupportFragmentManager(), "Bozuk Ürün");
            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new IadestokFilter();

        return filter;
    }

    private class IadestokFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            String kelime = constraint.toString().toLowerCase();

            if (kelime.equals("") || kelime.length() == 0) {
                ArrayList<IadeStok> list = new ArrayList<>(original);
                results.values = list;
                results.count = list.size();
            } else {
                final ArrayList<IadeStok> list = new ArrayList<>(original);
                final ArrayList<IadeStok> nlist = new ArrayList<>();
                int count = list.size();

                for (int i = 0; i < count; i++) {
                    final IadeStok iade = list.get(i);

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
            fitems = (ArrayList<IadeStok>) results.values;

            clear();
            int count = fitems.size();
            for (int i = 0; i < count; i++) {
                IadeStok iade = fitems.get(i);
                add(iade);
            }
        }
    }
}
