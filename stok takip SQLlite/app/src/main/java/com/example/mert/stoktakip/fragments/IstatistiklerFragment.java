package com.example.mert.stoktakip.fragments;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mert.stoktakip.R;
import com.example.mert.stoktakip.models.VeritabaniIslemleri;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class IstatistiklerFragment extends Fragment {

    VeritabaniIslemleri vti;

    String kid;

    TextView toplamKarTxt;
    TextView toplamVergiTxt;
    TextView aliinanUrunSayisiTxt;
    TextView satilanUrunSayisiTxt;
    TextView iadeAlinanUrunSayisiTxt;
    TextView iadeVerilenUrunSayisiTxt;
    TextView kalanUrunSayisiTxt;
    TextView kadi2Txt;
    TextView satilanMaliyetTxt;

    PieChart gider2Chart;
    PieChart ciroChart;
    PieChart giderChart;
    PieChart getiriChart;
    BarChart butunVergiChart;
    PieChart kullaniciGideriChart;
    PieChart kullaniciGetirisiChart;
    BarChart kullaniciVergiChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_istatistikler, container, false);

        toplamKarTxt = v.findViewById(R.id.toplam_kar);
        toplamVergiTxt = v.findViewById(R.id.toplam_vergi);
        aliinanUrunSayisiTxt = v.findViewById(R.id.txt_alinan_ürün_sayisi);
        satilanUrunSayisiTxt = v.findViewById(R.id.txt_satilan_ürün_sayisi);
        iadeAlinanUrunSayisiTxt = v.findViewById(R.id.txt_iade_alinan_ürün_sayisi);
        iadeVerilenUrunSayisiTxt = v.findViewById(R.id.txt_iade_verilen_ürün_sayisi);
        kalanUrunSayisiTxt = v.findViewById(R.id.txt_kalan_ürün_sayisi);
        kadi2Txt = v.findViewById(R.id.txt_kadi2);
        satilanMaliyetTxt = v.findViewById(R.id.txt_satilan_maliyet);

        gider2Chart = v.findViewById(R.id.gider2_chart);
        ciroChart = v.findViewById(R.id.ciro_chart);
        giderChart = v.findViewById(R.id.gider_chart);
        getiriChart = v.findViewById(R.id.getiri_chart);
        butunVergiChart = v.findViewById(R.id.butun_vergi_chart);
        kullaniciGideriChart = v.findViewById(R.id.kullanici_gider_chart);
        kullaniciGetirisiChart = v.findViewById(R.id.kullanici_gelir_chart);
        kullaniciVergiChart = v.findViewById(R.id.kullanici_vergi_chart);

        vti = new VeritabaniIslemleri(getContext());

        Bundle bundle = getArguments();
        kid = bundle.getString("kadi");

        toplamVergiTxt.setText(String.valueOf(vti.toplamVergiGetir()));
        toplamKarTxt.setText(String.valueOf(vti.toplamKarGetir()));
        aliinanUrunSayisiTxt.setText(String.valueOf(vti.alinanUrunSayisiGetir()));
        satilanUrunSayisiTxt.setText(String.valueOf(vti.satilanUrunSayisiGetir()));
        iadeAlinanUrunSayisiTxt.setText(String.valueOf(vti.iadeAlinanUrunSayisiGetir()));
        iadeVerilenUrunSayisiTxt.setText(String.valueOf(vti.iadeVerilenUrunSayisiGetir()));
        kalanUrunSayisiTxt.setText(String.valueOf(vti.kalanUrunSayisiGetir()));
        kadi2Txt.setText(kid);
        satilanMaliyetTxt.setText(String.valueOf(round(vti.kullaniciSatilanMaliyetGetir(kid),2)));

        gider2ChartGrafigiCiz(gider2Chart);
        ciroChartGrafigiCiz(ciroChart);
        butunGiderlerinGrafiginiCiz(giderChart);
        butunGetirilerinGrafiginiCiz(getiriChart);
        butunVergiGrafigiCiz(butunVergiChart);
        kullaniciGideriGrafigiCiz(kullaniciGideriChart);
        kullaniciGetirisiGrafigiCiz(kullaniciGetirisiChart);
        kullaniciVergiGrafigiCiz(kullaniciVergiChart);

        return v;
    }

    private void gider2ChartGrafigiCiz(PieChart butunGiderChart){
        List<PieEntry> giderlerListe = vti.butunGiderleriGetir2();

        pieChartOlustur(butunGiderChart,giderlerListe,getString(R.string.tumgider)+ " (" + getString(R.string.tl)+")",
                getString(R.string.tumgider) +" ("+getString(R.string.tl)+")");
    }

    private void ciroChartGrafigiCiz(PieChart butunCiroChart){
        List<PieEntry> kullaniciCirolariListe = vti.butunCirolariGetir();

        pieChartOlustur(butunCiroChart,kullaniciCirolariListe,getString(R.string.tumciro)+ " (" + getString(R.string.tl)+")",
                getString(R.string.tumciro) +" ("+getString(R.string.tl)+")");
    }

    private void butunGetirilerinGrafiginiCiz(PieChart butunGetiriChart) {
        List<PieEntry> GetirilerListe = vti.butunGetirileriGetir();
        pieChartOlustur(butunGetiriChart,GetirilerListe,getString(R.string.tumgetiri) +" ("+getString(R.string.tl)+")",
                getString(R.string.tumgetiri) +" ("+getString(R.string.tl)+")");
    }

    private void butunGiderlerinGrafiginiCiz(PieChart butunGiderChart) {
        List<PieEntry> GiderlerListe = vti.butunGiderleriGetir();
        pieChartOlustur(butunGiderChart,GiderlerListe,getString(R.string.tumgiderVergi) +" ("+getString(R.string.tl)+")",
                getString(R.string.tumgiderVergi) +" ("+getString(R.string.tl)+")");
    }

    private void butunVergiGrafigiCiz(BarChart barChart){
        List<BarEntry> butunVergilerListe = vti.butunVergileriGetir();

        String[] xLabels = { getString(R.string.odenenkargoV),getString(R.string.alımVergi),getString(R.string.alınankargoV), getString(R.string.satımVergi)};
        barChartOlustur(barChart,butunVergilerListe,
                getString(R.string.tumvergi) + " ("+getString(R.string.tl)+")",xLabels,getString(R.string.tumvergi) + " ("+getString(R.string.tl)+")");
    }

    private void kullaniciGideriGrafigiCiz(PieChart kullaniciGideriChart) {
        List<PieEntry> kullaniciGetirileriListe = vti.kullaniciGiderleriniGetir(kid);
        pieChartOlustur(kullaniciGideriChart,kullaniciGetirileriListe,kid +" "+ getString(R.string.kullanicigideri) +" ("+getString(R.string.tl)+")",
                kid +" "+ getString(R.string.kullanicigideri) +" ("+getString(R.string.tl)+")");
    }

    private void kullaniciGetirisiGrafigiCiz(PieChart kullaniciGetirisiChart) {
        List<PieEntry> kullaniciGetirileriListe = vti.kullaniciGetirileriniGetir(kid);
        pieChartOlustur(kullaniciGetirisiChart,kullaniciGetirileriListe,kid +" "+getString(R.string.kullanicigetiri)+ " (" + getString(R.string.tl)+")",
                kid +" "+ getString(R.string.kullanicigetiri) +" ("+getString(R.string.tl)+")");
    }

    private void kullaniciVergiGrafigiCiz(BarChart barChart){
        List<BarEntry> kullaniciVergilerListe = vti.kullaniciVergileriniGetir(kid);

        String[] xLabels = { getString(R.string.odenenkargoV),getString(R.string.alımVergi),getString(R.string.alınankargoV), getString(R.string.satımVergi)};
        barChartOlustur(barChart,kullaniciVergilerListe,
                kid+" "+getString(R.string.kullanicivergileri) + " ("+getString(R.string.tl)+")",xLabels,kid+" "+getString(R.string.kullanicivergileri) + " ("+getString(R.string.tl)+")");
    }



    private void pieChartOlustur(PieChart kullaniciGetirisiChart, List<PieEntry> kullaniciGetirileriListe, String label, String aciklama){
        PieDataSet kullaniciGetirileriDataSet = new PieDataSet(kullaniciGetirileriListe, label);

        //tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // Dilim renkleri
        final int[] colors = {
                Color.rgb(255, 73, 75),
                Color.rgb(0, 66, 181),
                Color.rgb(79, 145, 255),
                Color.rgb(240, 152, 0),
                Color.rgb(255, 196, 94),
                Color.rgb(0,176,80),
                Color.rgb(79,129,189)
        };
        kullaniciGetirileriDataSet.setColors(colors);//ColorTemplate.MATERIAL_COLORS

        kullaniciGetirileriDataSet.setSliceSpace(2f);// Dilimlerin arasındaki boşluğu ayarlar
        kullaniciGetirileriDataSet.setSelectionShift(6f);// Dilim seçilince ne kadar dışarı çıkacağını ayarlar
        kullaniciGetirileriDataSet.setUsingSliceColorAsValueLineColor(true);//değer çizgisi ile dilim rengi ayni olur
        kullaniciGetirileriDataSet.setValueLineWidth(2f);//Çizginin kalınlığı
        kullaniciGetirileriDataSet.setValueLineVariableLength(true);//dğişken çizgi uzunluğu, sadece outside iken

        kullaniciGetirileriDataSet.setValueLinePart1OffsetPercentage(90.f);
        kullaniciGetirileriDataSet.setValueLinePart1Length(0.5f);
        kullaniciGetirileriDataSet.setValueLinePart2Length(.2f);
        //kullaniciGetirileriDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        kullaniciGetirileriDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        kullaniciGetirileriDataSet.setValueFormatter(new PercentFormatter());

        PieData data = new PieData(kullaniciGetirileriDataSet);
        data.setValueTextSize(10f);// Değerlerin yazı büyüklüğü
        data.setValueTextColor(Color.WHITE);// Değerlerin yazı rengi
        data.setDrawValues(true); //Değerlerin çıkmasını sağlar
        data.setValueFormatter(new PercentFormatter(kullaniciGetirisiChart));//% işareti ekler
        kullaniciGetirisiChart.setData(data);


        kullaniciGetirisiChart.setNoDataText(getResources().getString(R.string.noData));
        kullaniciGetirisiChart.setMinOffset(20f);//Padding
        kullaniciGetirisiChart.setExtraLeftOffset(60f);


        kullaniciGetirisiChart.getLegend().setTextColor(Color.WHITE);// Dataset label'ının rengi
        kullaniciGetirisiChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);// Dataset label'ının oryantasyonu
        kullaniciGetirisiChart.getLegend().setDrawInside(false);// Dataset label'ının garfiğin dışında olması
        kullaniciGetirisiChart.getLegend().setYOffset(15f);// Dataset label'ının alttan yüksekliği
        kullaniciGetirisiChart.getLegend().setXOffset(10f);// Dataset label'ının soldan yüksekliği

        LegendEntry[] entry =  kullaniciGetirisiChart.getLegend().getEntries();//orjinal labelleri kaydeder
        entry = Arrays.copyOf(entry, entry.length-1);//Legend Labelini siler

        for(PieEntry i :kullaniciGetirileriListe){
            i.setLabel(String.valueOf(i.getValue()) +" "+ getString(R.string.tl));
        }
        kullaniciGetirisiChart.getLegend().setCustom(entry);//labelleri yazdırır

        kullaniciGetirisiChart.setEntryLabelColor(Color.WHITE); // Etiketlerin yazı rengi
        kullaniciGetirisiChart.getDescription().setEnabled(true);// Grafiğin sağ altındaki açıklamayı gizler
        Description description = new Description();
        description.setText(aciklama);
        description.setTextColor(Color.WHITE);
        description.setTextSize(12f);
        description.setTextAlign(Paint.Align.LEFT);
        kullaniciGetirisiChart.setDescription(description);
        //kullaniciGetirisiChart.getDescription().setPosition(370f,50f);//Pozisyon
        kullaniciGetirisiChart.getDescription().setXOffset(350f);//padding X
        kullaniciGetirisiChart.getDescription().setYOffset(190f);//padding Y
        kullaniciGetirisiChart.setHoleColor(getResources().getColor(R.color.Transparent));//Deliğin rengini ayarlar
        kullaniciGetirisiChart.setHoleRadius(50f);//Deliğin boyutu
        kullaniciGetirisiChart.setTransparentCircleRadius(45f);//Saydam Dairenin boyutu
        kullaniciGetirisiChart.setTransparentCircleAlpha(100);//Şeffaf dairenin şeffaflığını ayarlar
        kullaniciGetirisiChart.setDrawHoleEnabled(true);// Grafiğin iç tarafının boş olmasını sağlar
        kullaniciGetirisiChart.setDrawCenterText(true);//Grafiğin ortasındaki yazıyı açar
        kullaniciGetirisiChart.setUsePercentValues(true);//Yüzdelik dilimleri açar
        kullaniciGetirisiChart.setDrawSlicesUnderHole(false);//Deliğin altında kalan alanları kapatır
        kullaniciGetirisiChart.setMinAngleForSlices(20f);//Minumum dilim açısını ayarlar

        kullaniciGetirisiChart.setRotationAngle(90);
        kullaniciGetirisiChart.setRotationEnabled(true);

        float toplam = data.getYValueSum();//verilerin toplami
        toplam = round(toplam,2);

        kullaniciGetirisiChart.setCenterText(toplam+" "+getString(R.string.tl));//Grafiğin ortasındaki yazıyı ayarlar
        kullaniciGetirisiChart.setCenterTextColor(Color.WHITE);//Grafiğin ortasındaki yazının rengini ayarlar
        kullaniciGetirisiChart.setCenterTextSize(15f);//Grafiğin ortasındaki yazının boyutunu ayarlar
        //kullaniciGetirisiChart.setDrawEntryLabels(false);//Labelleri grafiğin içinden kaldırır
        kullaniciGetirisiChart.spin(1000, 0f, 360f, Easing.EaseInOutQuad);// Dönme animasyonu

        kullaniciGetirisiChart.invalidate();// Grafiği yeniler
    }

    private void barChartOlustur(BarChart barChart,List<BarEntry> kullaniciVergilerListe,String label,String[] xLabels, String aciklama){
        BarDataSet vergilerBarDataSet = new BarDataSet(kullaniciVergilerListe, label);

        final int[] colors = {
                Color.rgb(255, 73, 75),
                Color.rgb(0, 66, 181),
                Color.rgb(79, 145, 255),
                Color.rgb(240, 152, 0),
                Color.rgb(255, 196, 94),
                Color.rgb(0,176,80),
                Color.rgb(79,129,189)
        };
        // Bar rengi
        vergilerBarDataSet.setColors(colors);
        // Barların üstünde çıkan değerlerin yazı boyutu
        vergilerBarDataSet.setValueTextSize(8);
        vergilerBarDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(barEntry.getY()) + " " + getString(R.string.tl);
            }
        });


        BarData vergilerBarData = new BarData(vergilerBarDataSet);
        // Barların kalınlığını ayarlar
        vergilerBarData.setBarWidth(0.15f);
        // Barların üstünde çıkan değerlerin rengi
        vergilerBarData.setValueTextColor(Color.WHITE);
        barChart.setData(vergilerBarData);

        XAxis xEkseniMultipleBar = barChart.getXAxis();
        // X eksenindeki değerlerin aşağıda gözükmesini sağlar
        xEkseniMultipleBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        // X ekseni için ızgara çizgilerini gizler
        xEkseniMultipleBar.setDrawGridLines(false);
        //Barlar arası maximum boşluk
        xEkseniMultipleBar.setSpaceMax(0.8f);
        // Yakınlaştırılınca x ekseninde fazladan değer çıkmasını engeller
        xEkseniMultipleBar.setGranularity(1f);
        // X ekseninin başlangıcında boşluk bırakır
        //xEkseniMultipleBar.setAxisMinimum(vergilerBarData.getXMin() - 0.2f);
        // X ekseninin sonunda boşluk bırakır
        //xEkseniMultipleBar.setAxisMaximum(vergilerBarData.getXMax() + 0.2f);
        // Değer formatlayıcıyı ayarlar
        //xEkseniMultipleBar.setValueFormatter(new IndexAxisValueFormatter(zamanlarListe));
        xEkseniMultipleBar.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabels[(int) value];
            }
        });
        // X eksenindeki yazıların rengi
        xEkseniMultipleBar.setTextColor(Color.WHITE);



        YAxis yEkseniSolMultipleBar = barChart.getAxisLeft();
        // sol Y ekseninde gözükecek değer sayısını ayarlar
        yEkseniSolMultipleBar.setLabelCount(5, false);
        // sol Y ekseninin göstereceği minimum değeri ayarlar
        yEkseniSolMultipleBar.setAxisMinimum(0f);
        // sol Y eksenindeki yazıların rengi
        yEkseniSolMultipleBar.setTextColor(Color.WHITE);

        YAxis yEkseniSagMultipleBar = barChart.getAxisRight();
        // sağ Y ekseninde gözükecek değer sayısını ayarlar
        yEkseniSagMultipleBar.setLabelCount(5, false);
        // sağ Y ekseninin göstereceği minimum değeri ayarlar
        yEkseniSagMultipleBar.setAxisMinimum(0f);
        // sağ Y ekseni için ızgara çizgilerini gizler çünkü sol Y ekseni zaten gösteriyor
        yEkseniSagMultipleBar.setDrawGridLines(false);
        // sağ Y ekseninin çizgisini gizler
        yEkseniSagMultipleBar.setDrawAxisLine(false);
        // sağ Y eksenindeki yazıların rengi
        yEkseniSagMultipleBar.setTextColor(Color.WHITE);

        barChart.setExtraOffsets(0f,40f,0f,12f); //padding
        //değerler çubuğun içinde gösterilmesin
        barChart.setDrawValueAboveBar(true);
        // Dataset label'ının rengi
        barChart.getLegend().setTextColor(Color.WHITE);
        // X eksenindeki sadece 7 değerin gözükmesini sağlar
        barChart.setVisibleXRangeMaximum(7);
        // Tablonun en sondaki değerlerin gözükmesini sağlar
        //karCiroChart.moveViewToX(xIndeksleriKarCiro.length);
        // Grafiğe çift tıkla yakınlaştırmayı kapatır
        barChart.setDoubleTapToZoomEnabled(false);
        // Y eksenine animasyon uygulayarak grafiği 1000 milisaniyede çizer
        barChart.animateY(1000);
        // PinchZoom aktif edilir. false ayarlanırsa X ve Y ekseni için ayrı ayrı yakınlaştırılır
        barChart.setPinchZoom(true);
        barChart.getLegend().setEnabled(false);

        // Grafiğin sağ altındaki açıklamayı açar
        barChart.getDescription().setEnabled(true);
        Description description = new Description();
        description.setText(aciklama);
        description.setTextColor(Color.WHITE);
        description.setTextSize(12f);
        description.setTextAlign(Paint.Align.LEFT);
        barChart.setDescription(description);
        //barChart.getDescription().setPosition(370f,50f);//Pozisyon
        barChart.getDescription().setXOffset(335f);//padding X
        barChart.getDescription().setYOffset(184f);//padding Y

        // Birden fazla bar varsa başlangıç noktasını, barlar arası uzaklığı
        // ve bar grupları arası uzaklığı belirler
        //barChart.groupBars(-0.5f, 0.14f, 0.08f);
        // Barlara odaklanma özelliğini kapatır
        vergilerBarData.setHighlightEnabled(false);
        // Grafiği yeniler
        barChart.invalidate();
    }




    /*private void karCiroGrafigiCiz(BarChart karCiroChart) {

        List<Float> giderlerListe = new ArrayList<>();
        List<Float> cirolarListe = new ArrayList<>();
        List<Float> karlarListe = new ArrayList<>();
        List<String> zamanlarListe = new ArrayList<>();

        ArrayList<KarCiroBilgisi> karCiroBilgileri = vti.gunlukKarCiroBilgileriniGetir();

        for (int i = 0; i < karCiroBilgileri.size(); i++) {
            giderlerListe.add(karCiroBilgileri.get(i).getGider());
            cirolarListe.add(karCiroBilgileri.get(i).getCiro());
            karlarListe.add(karCiroBilgileri.get(i).getKar());
            zamanlarListe.add(karCiroBilgileri.get(i).getZaman());
        }

        int[] xIndeksleriKarCiro = new int[karCiroBilgileri.size()];
        for (int i = 0; i < karCiroBilgileri.size(); i++) {
            xIndeksleriKarCiro[i] = i;
        }
        List<BarEntry> giderlerBarEntries = new ArrayList<>();
        List<BarEntry> cirolarBarEntries = new ArrayList<>();
        List<BarEntry> karlarBarEntries = new ArrayList<>();

        for (int i = 0; i < karCiroBilgileri.size(); i++) {
            giderlerBarEntries.add(new BarEntry(xIndeksleriKarCiro[i], giderlerListe.get(i)));
            cirolarBarEntries.add(new BarEntry(xIndeksleriKarCiro[i], cirolarListe.get(i)));
            karlarBarEntries.add(new BarEntry(xIndeksleriKarCiro[i], karlarListe.get(i)));
        }

        BarDataSet giderlerBarDataSet = new BarDataSet(giderlerBarEntries, "Günlük Gider (₺)");
        // Bar rengi
        giderlerBarDataSet.setColor(getResources().getColor(R.color.giderRenk));
        // Barların üstünde çıkan değerlerin yazı boyutu
        giderlerBarDataSet.setValueTextSize(8);


        BarDataSet karlarBarDataSet = new BarDataSet(karlarBarEntries, "Günlük Kar (₺)");
        // Bar rengi
        karlarBarDataSet.setColor(getResources().getColor(R.color.karRenk));
        // Barların üstünde çıkan değerlerin yazı boyutu
        karlarBarDataSet.setValueTextSize(8);


        BarDataSet cirolarBarDataSet = new BarDataSet(cirolarBarEntries, "Günlük Ciro (₺)");
        // Bar rengi
        cirolarBarDataSet.setColor(getResources().getColor(R.color.ciroRenk));
        // Barların üstünde çıkan değerlerin yazı boyutu
        cirolarBarDataSet.setValueTextSize(8);


        List<IBarDataSet> karCiroDataSets = new ArrayList<>();
        karCiroDataSets.add(giderlerBarDataSet);
        karCiroDataSets.add(cirolarBarDataSet);
        karCiroDataSets.add(karlarBarDataSet);




        BarData karCiroBarData = new BarData(karCiroDataSets);
        // Barların kalınlığını ayarlar
        karCiroBarData.setBarWidth(0.35f);
        // Barların üstünde çıkan değerlerin rengi
        karCiroBarData.setValueTextColor(Color.WHITE);
        karCiroChart.setData(karCiroBarData);

        XAxis xEkseniMultipleBar = karCiroChart.getXAxis();
        // X eksenindeki değerlerin aşağıda gözükmesini sağlar
        xEkseniMultipleBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        // X ekseni için ızgara çizgilerini gizler
        xEkseniMultipleBar.setDrawGridLines(false);
        // Yakınlaştırılınca x ekseninde fazladan değer çıkmasını engeller
        xEkseniMultipleBar.setGranularity(1f);
        // X ekseninin başlangıcında boşluk bırakır
        xEkseniMultipleBar.setAxisMinimum(karCiroBarData.getXMin() - 0.5f);
        // X ekseninin sonunda boşluk bırakır
        xEkseniMultipleBar.setAxisMaximum(karCiroBarData.getXMax() + 0.5f);
        // Değer formatlayıcıyı ayarlar
        xEkseniMultipleBar.setValueFormatter(new IndexAxisValueFormatter(zamanlarListe));
        // X eksenindeki yazıların rengi
        xEkseniMultipleBar.setTextColor(Color.WHITE);

        YAxis yEkseniSolMultipleBar = karCiroChart.getAxisLeft();
        // sol Y ekseninde gözükecek değer sayısını ayarlar
        yEkseniSolMultipleBar.setLabelCount(5, false);
        // sol Y ekseninin göstereceği minimum değeri ayarlar
        yEkseniSolMultipleBar.setAxisMinimum(0f);
        // sol Y eksenindeki yazıların rengi
        yEkseniSolMultipleBar.setTextColor(Color.WHITE);

        YAxis yEkseniSagMultipleBar = karCiroChart.getAxisRight();
        // sağ Y ekseninde gözükecek değer sayısını ayarlar
        yEkseniSagMultipleBar.setLabelCount(5, false);
        // sağ Y ekseninin göstereceği minimum değeri ayarlar
        yEkseniSagMultipleBar.setAxisMinimum(0f);
        // sağ Y ekseni için ızgara çizgilerini gizler çünkü sol Y ekseni zaten gösteriyor
        yEkseniSagMultipleBar.setDrawGridLines(false);
        // sağ Y ekseninin çizgisini gizler
        yEkseniSagMultipleBar.setDrawAxisLine(false);
        // sağ Y eksenindeki yazıların rengi
        yEkseniSagMultipleBar.setTextColor(Color.WHITE);

        // Dataset label'ının rengi
        karCiroChart.getLegend().setTextColor(Color.WHITE);
        // X eksenindeki sadece 7 değerin gözükmesini sağlar
        karCiroChart.setVisibleXRangeMaximum(7);
        // Tablonun en sondaki değerlerin gözükmesini sağlar
        karCiroChart.moveViewToX(xIndeksleriKarCiro.length);
        // Grafiğe çift tıkla yakınlaştırmayı kapatır
        karCiroChart.setDoubleTapToZoomEnabled(false);
        // Y eksenine animasyon uygulayarak grafiği 1000 milisaniyede çizer
        karCiroChart.animateY(1000);
        // PinchZoom aktif edilir. false ayarlanırsa X ve Y ekseni için ayrı ayrı yakınlaştırılır
        karCiroChart.setPinchZoom(true);
        // Grafiğin sağ altındaki açıklamayı siler
        karCiroChart.getDescription().setEnabled(false);
        // Birden fazla bar varsa başlangıç noktasını, barlar arası uzaklığı
        // ve bar grupları arası uzaklığı belirler
        karCiroChart.groupBars(-0.5f, 0.14f, 0.08f);
        // Barlara odaklanma özelliğini kapatır
        karCiroBarData.setHighlightEnabled(false);
        // Grafiği yeniler
        karCiroChart.invalidate();
    }*/

    /*private void urunGetiriGrafigiCiz(HorizontalBarChart urunGetirisiChart) {
        List<String> urunAdlariListe = new ArrayList<>();
        List<Float> getirilerListe = new ArrayList<>();

        List<UrunGetirisi> urunGetirileri = vti.urunGetirileriniGetir();

        for (int i = 0; i < urunGetirileri.size(); i++) {
            urunAdlariListe.add(urunGetirileri.get(i).getUrunAdi());
            getirilerListe.add(urunGetirileri.get(i).getGetiri());
        }

        int[] xIndeksleriUrunGetirileri = new int[urunGetirileri.size()];
        for (int i = 0; i < urunGetirileri.size(); i++) {
            xIndeksleriUrunGetirileri[i] = i;
        }
        List<BarEntry> getirilerBarEntries = new ArrayList<>();

        for (int i = 0; i < urunGetirileri.size(); i++) {
            getirilerBarEntries
                    .add(new BarEntry(xIndeksleriUrunGetirileri[i], getirilerListe.get(i)));
        }

        BarDataSet barDataSet = new BarDataSet(getirilerBarEntries, "Ürün Getirileri (₺)");
        // Bar rengi
        barDataSet.setColor(getResources().getColor(R.color.urunGetirisi));
        // Barların üzerinde değerlerin çıkmasını sağlar
        barDataSet.setDrawValues(true);
        // Dokunmayla noktalara odaklanılmasını engeller
        barDataSet.setHighlightEnabled(false);

        BarData urunGetirisiBarData = new BarData(barDataSet);
        // Bar kalınlığı
        urunGetirisiBarData.setBarWidth(0.5f);
        // Barların üstünde çıkan değerlerin rengi
        urunGetirisiBarData.setValueTextColor(Color.WHITE);
        // Barların üstünde çıkan değerlerin yazı boyutu
        urunGetirisiBarData.setValueTextSize(8);
        urunGetirisiChart.setData(urunGetirisiBarData);

        XAxis xEkseniBar = urunGetirisiChart.getXAxis();
        // X eksenindeki değerlerin aşağıda gözükmesini sağlar
        xEkseniBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        // X ekseni için ızgara çizgilerini gizler
        xEkseniBar.setDrawGridLines(false);
        // Değer formatlayıcıyı ayarlar
        xEkseniBar.setValueFormatter(new IndexAxisValueFormatter(urunAdlariListe));
        // Yakınlaştırılınca x ekseninde fazladan değer çıkmasını engeller
        xEkseniBar.setGranularity(1f);
        // X ekseninin başlangıcında boşluk bırakır
        xEkseniBar.setAxisMinimum(urunGetirisiBarData.getXMin() - 0.5f);
        // X ekseninin sonunda boşluk bırakır
        xEkseniBar.setAxisMaximum(urunGetirisiBarData.getXMax() + 0.5f);
        // X eksenindeki yazıların rengi
        xEkseniBar.setTextColor(Color.WHITE);

        YAxis yEkseniSolBar = urunGetirisiChart.getAxisLeft();
        yEkseniSolBar.setEnabled(false);
        // sol Y ekseninin göstereceği minimum değeri ayarlar
        yEkseniSolBar.setAxisMinimum(0f);

        YAxis yEkseniSagBar = urunGetirisiChart.getAxisRight();
        // sağ Y ekseninde gözükecek değer sayısını ayarlar
        yEkseniSagBar.setLabelCount(5, false);
        // sağ Y ekseninin göstereceği minimum değeri ayarlar
        yEkseniSagBar.setAxisMinimum(0f);
        // sağ Y eksenindeki yazıların rengi
        yEkseniSagBar.setTextColor(Color.WHITE);

        // Dataset label'ının rengi
        urunGetirisiChart.getLegend().setTextColor(Color.WHITE);
        // X eksenindeki sadece 7 değerin gözükmesini sağlar
        urunGetirisiChart.setVisibleXRangeMaximum(7);
        // Tablonun en üstteki değerlerin gözükmesini sağlar
        urunGetirisiChart.moveViewTo(0f, 0f, YAxis.AxisDependency.LEFT);
        // Çift tıklayınca yakınlaştırmayı kapatır
        urunGetirisiChart.setDoubleTapToZoomEnabled(false);
        // X eksenine animasyon uygulayarak grafiği 1000 milisaniyede çizer
        urunGetirisiChart.animateY(1000);
        // PinchZoom aktif edilir. false ayarlanırsa X ve Y ekseni için ayrı ayrı yakınlaştırılır
        urunGetirisiChart.setPinchZoom(true);
        // Grafiğin sağ altındaki açıklamayı gizler
        urunGetirisiChart.getDescription().setEnabled(false);
        // Grafiği yeniler
        urunGetirisiChart.invalidate();
    }*/


    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


}
