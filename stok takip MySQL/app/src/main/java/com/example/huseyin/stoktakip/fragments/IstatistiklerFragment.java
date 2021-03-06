package com.example.huseyin.stoktakip.fragments;

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

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
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
        aliinanUrunSayisiTxt = v.findViewById(R.id.txt_alinan_??r??n_sayisi);
        satilanUrunSayisiTxt = v.findViewById(R.id.txt_satilan_??r??n_sayisi);
        iadeAlinanUrunSayisiTxt = v.findViewById(R.id.txt_iade_alinan_??r??n_sayisi);
        iadeVerilenUrunSayisiTxt = v.findViewById(R.id.txt_iade_verilen_??r??n_sayisi);
        kalanUrunSayisiTxt = v.findViewById(R.id.txt_kalan_??r??n_sayisi);
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

        String[] xLabels = { getString(R.string.odenenkargoV),getString(R.string.al??mVergi),getString(R.string.al??nankargoV), getString(R.string.sat??mVergi)};
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

        String[] xLabels = { getString(R.string.odenenkargoV),getString(R.string.al??mVergi),getString(R.string.al??nankargoV), getString(R.string.sat??mVergi)};
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

        kullaniciGetirileriDataSet.setSliceSpace(2f);// Dilimlerin aras??ndaki bo??lu??u ayarlar
        kullaniciGetirileriDataSet.setSelectionShift(6f);// Dilim se??ilince ne kadar d????ar?? ????kaca????n?? ayarlar
        kullaniciGetirileriDataSet.setUsingSliceColorAsValueLineColor(true);//de??er ??izgisi ile dilim rengi ayni olur
        kullaniciGetirileriDataSet.setValueLineWidth(2f);//??izginin kal??nl??????
        kullaniciGetirileriDataSet.setValueLineVariableLength(true);//d??i??ken ??izgi uzunlu??u, sadece outside iken

        kullaniciGetirileriDataSet.setValueLinePart1OffsetPercentage(90.f);
        kullaniciGetirileriDataSet.setValueLinePart1Length(0.5f);
        kullaniciGetirileriDataSet.setValueLinePart2Length(.2f);
        //kullaniciGetirileriDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        kullaniciGetirileriDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        kullaniciGetirileriDataSet.setValueFormatter(new PercentFormatter());

        PieData data = new PieData(kullaniciGetirileriDataSet);
        data.setValueTextSize(10f);// De??erlerin yaz?? b??y??kl??????
        data.setValueTextColor(Color.WHITE);// De??erlerin yaz?? rengi
        data.setDrawValues(true); //De??erlerin ????kmas??n?? sa??lar
        data.setValueFormatter(new PercentFormatter(kullaniciGetirisiChart));//% i??areti ekler
        kullaniciGetirisiChart.setData(data);


        kullaniciGetirisiChart.setNoDataText(getResources().getString(R.string.noData));
        kullaniciGetirisiChart.setMinOffset(20f);//Padding
        kullaniciGetirisiChart.setExtraLeftOffset(60f);


        kullaniciGetirisiChart.getLegend().setTextColor(Color.WHITE);// Dataset label'??n??n rengi
        kullaniciGetirisiChart.getLegend().setOrientation(Legend.LegendOrientation.VERTICAL);// Dataset label'??n??n oryantasyonu
        kullaniciGetirisiChart.getLegend().setDrawInside(false);// Dataset label'??n??n garfi??in d??????nda olmas??
        kullaniciGetirisiChart.getLegend().setYOffset(15f);// Dataset label'??n??n alttan y??ksekli??i
        kullaniciGetirisiChart.getLegend().setXOffset(10f);// Dataset label'??n??n soldan y??ksekli??i

        LegendEntry[] entry =  kullaniciGetirisiChart.getLegend().getEntries();//orjinal labelleri kaydeder
        entry = Arrays.copyOf(entry, entry.length-1);//Legend Labelini siler

        for(PieEntry i :kullaniciGetirileriListe){
            i.setLabel(String.valueOf(i.getValue()) +" "+ getString(R.string.tl));
        }
        kullaniciGetirisiChart.getLegend().setCustom(entry);//labelleri yazd??r??r

        kullaniciGetirisiChart.setEntryLabelColor(Color.WHITE); // Etiketlerin yaz?? rengi
        kullaniciGetirisiChart.getDescription().setEnabled(true);// Grafi??in sa?? alt??ndaki a????klamay?? gizler
        Description description = new Description();
        description.setText(aciklama);
        description.setTextColor(Color.WHITE);
        description.setTextSize(12f);
        description.setTextAlign(Paint.Align.LEFT);
        kullaniciGetirisiChart.setDescription(description);
        //kullaniciGetirisiChart.getDescription().setPosition(370f,50f);//Pozisyon
        kullaniciGetirisiChart.getDescription().setXOffset(350f);//padding X
        kullaniciGetirisiChart.getDescription().setYOffset(190f);//padding Y
        kullaniciGetirisiChart.setHoleColor(getResources().getColor(R.color.Transparent));//Deli??in rengini ayarlar
        kullaniciGetirisiChart.setHoleRadius(50f);//Deli??in boyutu
        kullaniciGetirisiChart.setTransparentCircleRadius(45f);//Saydam Dairenin boyutu
        kullaniciGetirisiChart.setTransparentCircleAlpha(100);//??effaf dairenin ??effafl??????n?? ayarlar
        kullaniciGetirisiChart.setDrawHoleEnabled(true);// Grafi??in i?? taraf??n??n bo?? olmas??n?? sa??lar
        kullaniciGetirisiChart.setDrawCenterText(true);//Grafi??in ortas??ndaki yaz??y?? a??ar
        kullaniciGetirisiChart.setUsePercentValues(true);//Y??zdelik dilimleri a??ar
        kullaniciGetirisiChart.setDrawSlicesUnderHole(false);//Deli??in alt??nda kalan alanlar?? kapat??r
        kullaniciGetirisiChart.setMinAngleForSlices(20f);//Minumum dilim a????s??n?? ayarlar

        kullaniciGetirisiChart.setRotationAngle(90);
        kullaniciGetirisiChart.setRotationEnabled(true);

        float toplam = data.getYValueSum();//verilerin toplami
        toplam = round(toplam,2);

        kullaniciGetirisiChart.setCenterText(toplam+" "+getString(R.string.tl));//Grafi??in ortas??ndaki yaz??y?? ayarlar
        kullaniciGetirisiChart.setCenterTextColor(Color.WHITE);//Grafi??in ortas??ndaki yaz??n??n rengini ayarlar
        kullaniciGetirisiChart.setCenterTextSize(15f);//Grafi??in ortas??ndaki yaz??n??n boyutunu ayarlar
        //kullaniciGetirisiChart.setDrawEntryLabels(false);//Labelleri grafi??in i??inden kald??r??r
        kullaniciGetirisiChart.spin(1000, 0f, 360f, Easing.EaseInOutQuad);// D??nme animasyonu

        kullaniciGetirisiChart.invalidate();// Grafi??i yeniler
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
        // Barlar??n ??st??nde ????kan de??erlerin yaz?? boyutu
        vergilerBarDataSet.setValueTextSize(8);
        vergilerBarDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                DecimalFormat df = new DecimalFormat("#.##");
                return df.format(barEntry.getY()) + " " + getString(R.string.tl);
            }
        });


        BarData vergilerBarData = new BarData(vergilerBarDataSet);
        // Barlar??n kal??nl??????n?? ayarlar
        vergilerBarData.setBarWidth(0.15f);
        // Barlar??n ??st??nde ????kan de??erlerin rengi
        vergilerBarData.setValueTextColor(Color.WHITE);
        barChart.setData(vergilerBarData);

        XAxis xEkseniMultipleBar = barChart.getXAxis();
        // X eksenindeki de??erlerin a??a????da g??z??kmesini sa??lar
        xEkseniMultipleBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        // X ekseni i??in ??zgara ??izgilerini gizler
        xEkseniMultipleBar.setDrawGridLines(false);
        //Barlar aras?? maximum bo??luk
        xEkseniMultipleBar.setSpaceMax(0.8f);
        // Yak??nla??t??r??l??nca x ekseninde fazladan de??er ????kmas??n?? engeller
        xEkseniMultipleBar.setGranularity(1f);
        // X ekseninin ba??lang??c??nda bo??luk b??rak??r
        //xEkseniMultipleBar.setAxisMinimum(vergilerBarData.getXMin() - 0.2f);
        // X ekseninin sonunda bo??luk b??rak??r
        //xEkseniMultipleBar.setAxisMaximum(vergilerBarData.getXMax() + 0.2f);
        // De??er formatlay??c??y?? ayarlar
        //xEkseniMultipleBar.setValueFormatter(new IndexAxisValueFormatter(zamanlarListe));
        xEkseniMultipleBar.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xLabels[(int) value];
            }
        });
        // X eksenindeki yaz??lar??n rengi
        xEkseniMultipleBar.setTextColor(Color.WHITE);



        YAxis yEkseniSolMultipleBar = barChart.getAxisLeft();
        // sol Y ekseninde g??z??kecek de??er say??s??n?? ayarlar
        yEkseniSolMultipleBar.setLabelCount(5, false);
        // sol Y ekseninin g??sterece??i minimum de??eri ayarlar
        yEkseniSolMultipleBar.setAxisMinimum(0f);
        // sol Y eksenindeki yaz??lar??n rengi
        yEkseniSolMultipleBar.setTextColor(Color.WHITE);

        YAxis yEkseniSagMultipleBar = barChart.getAxisRight();
        // sa?? Y ekseninde g??z??kecek de??er say??s??n?? ayarlar
        yEkseniSagMultipleBar.setLabelCount(5, false);
        // sa?? Y ekseninin g??sterece??i minimum de??eri ayarlar
        yEkseniSagMultipleBar.setAxisMinimum(0f);
        // sa?? Y ekseni i??in ??zgara ??izgilerini gizler ????nk?? sol Y ekseni zaten g??steriyor
        yEkseniSagMultipleBar.setDrawGridLines(false);
        // sa?? Y ekseninin ??izgisini gizler
        yEkseniSagMultipleBar.setDrawAxisLine(false);
        // sa?? Y eksenindeki yaz??lar??n rengi
        yEkseniSagMultipleBar.setTextColor(Color.WHITE);

        barChart.setExtraOffsets(0f,40f,0f,12f); //padding
        //de??erler ??ubu??un i??inde g??sterilmesin
        barChart.setDrawValueAboveBar(true);
        // Dataset label'??n??n rengi
        barChart.getLegend().setTextColor(Color.WHITE);
        // X eksenindeki sadece 7 de??erin g??z??kmesini sa??lar
        barChart.setVisibleXRangeMaximum(7);
        // Tablonun en sondaki de??erlerin g??z??kmesini sa??lar
        //karCiroChart.moveViewToX(xIndeksleriKarCiro.length);
        // Grafi??e ??ift t??kla yak??nla??t??rmay?? kapat??r
        barChart.setDoubleTapToZoomEnabled(false);
        // Y eksenine animasyon uygulayarak grafi??i 1000 milisaniyede ??izer
        barChart.animateY(1000);
        // PinchZoom aktif edilir. false ayarlan??rsa X ve Y ekseni i??in ayr?? ayr?? yak??nla??t??r??l??r
        barChart.setPinchZoom(true);
        barChart.getLegend().setEnabled(false);

        // Grafi??in sa?? alt??ndaki a????klamay?? a??ar
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

        // Birden fazla bar varsa ba??lang???? noktas??n??, barlar aras?? uzakl??????
        // ve bar gruplar?? aras?? uzakl?????? belirler
        //barChart.groupBars(-0.5f, 0.14f, 0.08f);
        // Barlara odaklanma ??zelli??ini kapat??r
        vergilerBarData.setHighlightEnabled(false);
        // Grafi??i yeniler
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

        BarDataSet giderlerBarDataSet = new BarDataSet(giderlerBarEntries, "G??nl??k Gider (???)");
        // Bar rengi
        giderlerBarDataSet.setColor(getResources().getColor(R.color.giderRenk));
        // Barlar??n ??st??nde ????kan de??erlerin yaz?? boyutu
        giderlerBarDataSet.setValueTextSize(8);


        BarDataSet karlarBarDataSet = new BarDataSet(karlarBarEntries, "G??nl??k Kar (???)");
        // Bar rengi
        karlarBarDataSet.setColor(getResources().getColor(R.color.karRenk));
        // Barlar??n ??st??nde ????kan de??erlerin yaz?? boyutu
        karlarBarDataSet.setValueTextSize(8);


        BarDataSet cirolarBarDataSet = new BarDataSet(cirolarBarEntries, "G??nl??k Ciro (???)");
        // Bar rengi
        cirolarBarDataSet.setColor(getResources().getColor(R.color.ciroRenk));
        // Barlar??n ??st??nde ????kan de??erlerin yaz?? boyutu
        cirolarBarDataSet.setValueTextSize(8);


        List<IBarDataSet> karCiroDataSets = new ArrayList<>();
        karCiroDataSets.add(giderlerBarDataSet);
        karCiroDataSets.add(cirolarBarDataSet);
        karCiroDataSets.add(karlarBarDataSet);




        BarData karCiroBarData = new BarData(karCiroDataSets);
        // Barlar??n kal??nl??????n?? ayarlar
        karCiroBarData.setBarWidth(0.35f);
        // Barlar??n ??st??nde ????kan de??erlerin rengi
        karCiroBarData.setValueTextColor(Color.WHITE);
        karCiroChart.setData(karCiroBarData);

        XAxis xEkseniMultipleBar = karCiroChart.getXAxis();
        // X eksenindeki de??erlerin a??a????da g??z??kmesini sa??lar
        xEkseniMultipleBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        // X ekseni i??in ??zgara ??izgilerini gizler
        xEkseniMultipleBar.setDrawGridLines(false);
        // Yak??nla??t??r??l??nca x ekseninde fazladan de??er ????kmas??n?? engeller
        xEkseniMultipleBar.setGranularity(1f);
        // X ekseninin ba??lang??c??nda bo??luk b??rak??r
        xEkseniMultipleBar.setAxisMinimum(karCiroBarData.getXMin() - 0.5f);
        // X ekseninin sonunda bo??luk b??rak??r
        xEkseniMultipleBar.setAxisMaximum(karCiroBarData.getXMax() + 0.5f);
        // De??er formatlay??c??y?? ayarlar
        xEkseniMultipleBar.setValueFormatter(new IndexAxisValueFormatter(zamanlarListe));
        // X eksenindeki yaz??lar??n rengi
        xEkseniMultipleBar.setTextColor(Color.WHITE);

        YAxis yEkseniSolMultipleBar = karCiroChart.getAxisLeft();
        // sol Y ekseninde g??z??kecek de??er say??s??n?? ayarlar
        yEkseniSolMultipleBar.setLabelCount(5, false);
        // sol Y ekseninin g??sterece??i minimum de??eri ayarlar
        yEkseniSolMultipleBar.setAxisMinimum(0f);
        // sol Y eksenindeki yaz??lar??n rengi
        yEkseniSolMultipleBar.setTextColor(Color.WHITE);

        YAxis yEkseniSagMultipleBar = karCiroChart.getAxisRight();
        // sa?? Y ekseninde g??z??kecek de??er say??s??n?? ayarlar
        yEkseniSagMultipleBar.setLabelCount(5, false);
        // sa?? Y ekseninin g??sterece??i minimum de??eri ayarlar
        yEkseniSagMultipleBar.setAxisMinimum(0f);
        // sa?? Y ekseni i??in ??zgara ??izgilerini gizler ????nk?? sol Y ekseni zaten g??steriyor
        yEkseniSagMultipleBar.setDrawGridLines(false);
        // sa?? Y ekseninin ??izgisini gizler
        yEkseniSagMultipleBar.setDrawAxisLine(false);
        // sa?? Y eksenindeki yaz??lar??n rengi
        yEkseniSagMultipleBar.setTextColor(Color.WHITE);

        // Dataset label'??n??n rengi
        karCiroChart.getLegend().setTextColor(Color.WHITE);
        // X eksenindeki sadece 7 de??erin g??z??kmesini sa??lar
        karCiroChart.setVisibleXRangeMaximum(7);
        // Tablonun en sondaki de??erlerin g??z??kmesini sa??lar
        karCiroChart.moveViewToX(xIndeksleriKarCiro.length);
        // Grafi??e ??ift t??kla yak??nla??t??rmay?? kapat??r
        karCiroChart.setDoubleTapToZoomEnabled(false);
        // Y eksenine animasyon uygulayarak grafi??i 1000 milisaniyede ??izer
        karCiroChart.animateY(1000);
        // PinchZoom aktif edilir. false ayarlan??rsa X ve Y ekseni i??in ayr?? ayr?? yak??nla??t??r??l??r
        karCiroChart.setPinchZoom(true);
        // Grafi??in sa?? alt??ndaki a????klamay?? siler
        karCiroChart.getDescription().setEnabled(false);
        // Birden fazla bar varsa ba??lang???? noktas??n??, barlar aras?? uzakl??????
        // ve bar gruplar?? aras?? uzakl?????? belirler
        karCiroChart.groupBars(-0.5f, 0.14f, 0.08f);
        // Barlara odaklanma ??zelli??ini kapat??r
        karCiroBarData.setHighlightEnabled(false);
        // Grafi??i yeniler
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

        BarDataSet barDataSet = new BarDataSet(getirilerBarEntries, "??r??n Getirileri (???)");
        // Bar rengi
        barDataSet.setColor(getResources().getColor(R.color.urunGetirisi));
        // Barlar??n ??zerinde de??erlerin ????kmas??n?? sa??lar
        barDataSet.setDrawValues(true);
        // Dokunmayla noktalara odaklan??lmas??n?? engeller
        barDataSet.setHighlightEnabled(false);

        BarData urunGetirisiBarData = new BarData(barDataSet);
        // Bar kal??nl??????
        urunGetirisiBarData.setBarWidth(0.5f);
        // Barlar??n ??st??nde ????kan de??erlerin rengi
        urunGetirisiBarData.setValueTextColor(Color.WHITE);
        // Barlar??n ??st??nde ????kan de??erlerin yaz?? boyutu
        urunGetirisiBarData.setValueTextSize(8);
        urunGetirisiChart.setData(urunGetirisiBarData);

        XAxis xEkseniBar = urunGetirisiChart.getXAxis();
        // X eksenindeki de??erlerin a??a????da g??z??kmesini sa??lar
        xEkseniBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        // X ekseni i??in ??zgara ??izgilerini gizler
        xEkseniBar.setDrawGridLines(false);
        // De??er formatlay??c??y?? ayarlar
        xEkseniBar.setValueFormatter(new IndexAxisValueFormatter(urunAdlariListe));
        // Yak??nla??t??r??l??nca x ekseninde fazladan de??er ????kmas??n?? engeller
        xEkseniBar.setGranularity(1f);
        // X ekseninin ba??lang??c??nda bo??luk b??rak??r
        xEkseniBar.setAxisMinimum(urunGetirisiBarData.getXMin() - 0.5f);
        // X ekseninin sonunda bo??luk b??rak??r
        xEkseniBar.setAxisMaximum(urunGetirisiBarData.getXMax() + 0.5f);
        // X eksenindeki yaz??lar??n rengi
        xEkseniBar.setTextColor(Color.WHITE);

        YAxis yEkseniSolBar = urunGetirisiChart.getAxisLeft();
        yEkseniSolBar.setEnabled(false);
        // sol Y ekseninin g??sterece??i minimum de??eri ayarlar
        yEkseniSolBar.setAxisMinimum(0f);

        YAxis yEkseniSagBar = urunGetirisiChart.getAxisRight();
        // sa?? Y ekseninde g??z??kecek de??er say??s??n?? ayarlar
        yEkseniSagBar.setLabelCount(5, false);
        // sa?? Y ekseninin g??sterece??i minimum de??eri ayarlar
        yEkseniSagBar.setAxisMinimum(0f);
        // sa?? Y eksenindeki yaz??lar??n rengi
        yEkseniSagBar.setTextColor(Color.WHITE);

        // Dataset label'??n??n rengi
        urunGetirisiChart.getLegend().setTextColor(Color.WHITE);
        // X eksenindeki sadece 7 de??erin g??z??kmesini sa??lar
        urunGetirisiChart.setVisibleXRangeMaximum(7);
        // Tablonun en ??stteki de??erlerin g??z??kmesini sa??lar
        urunGetirisiChart.moveViewTo(0f, 0f, YAxis.AxisDependency.LEFT);
        // ??ift t??klay??nca yak??nla??t??rmay?? kapat??r
        urunGetirisiChart.setDoubleTapToZoomEnabled(false);
        // X eksenine animasyon uygulayarak grafi??i 1000 milisaniyede ??izer
        urunGetirisiChart.animateY(1000);
        // PinchZoom aktif edilir. false ayarlan??rsa X ve Y ekseni i??in ayr?? ayr?? yak??nla??t??r??l??r
        urunGetirisiChart.setPinchZoom(true);
        // Grafi??in sa?? alt??ndaki a????klamay?? gizler
        urunGetirisiChart.getDescription().setEnabled(false);
        // Grafi??i yeniler
        urunGetirisiChart.invalidate();
    }*/


    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }


}
