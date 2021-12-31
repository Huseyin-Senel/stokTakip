package com.example.huseyin.stoktakip.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.models.Alis;
import com.example.huseyin.stoktakip.models.BozukUrun;
import com.example.huseyin.stoktakip.models.Iade;
import com.example.huseyin.stoktakip.models.IadeStok;
import com.example.huseyin.stoktakip.models.Satis;
import com.example.huseyin.stoktakip.models.Sepet;
import com.example.huseyin.stoktakip.models.Stok;
import com.example.huseyin.stoktakip.models.Urun;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;
import com.example.huseyin.stoktakip.utils.ZamanFormatlayici;
import com.jeevandeshmukh.glidetoastlib.GlideToast;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTAutoFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTFilterColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class VeritabaniFragment extends Fragment {

    private static String EXCEL_SHEET_NAME = "Sayfa 1";

    Button alislar;
    Button satislar;
    Button sepetler;
    Button stoklar;
    Button urunler;
    Button iadeler;
    Button iadeStoklari;
    Button bozukUrunler;
    Button istatistikler;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_veritabani, container, false);

        alislar = view.findViewById(R.id.btn_alislar);
        alislar.setOnClickListener(e -> alislarExcel());

        satislar = view.findViewById(R.id.btn_satislar);
        satislar.setOnClickListener(e-> satislarExcel());

        sepetler = view.findViewById(R.id.btn_sepet);
        sepetler.setOnClickListener(e-> sepetlerExcel());

        stoklar = view.findViewById(R.id.btn_stok);
        stoklar.setOnClickListener(e-> stoklarExcel());

        urunler = view.findViewById(R.id.btn_urun);
        urunler.setOnClickListener(e-> urunlerExcel());

        iadeler = view.findViewById(R.id.btn_iade);
        iadeler.setOnClickListener(e-> iadelerExcel());

        iadeStoklari = view.findViewById(R.id.btn_iade_stok);
        iadeStoklari.setOnClickListener(e-> iadestoklarExcel());

        bozukUrunler = view.findViewById(R.id.btn_bozuk_urun1);
        bozukUrunler.setOnClickListener(e-> bozukUrunlerExcel());

        istatistikler = view.findViewById(R.id.btn_istatistik);
        istatistikler.setOnClickListener(e-> istatistiklerExcel());

        return  view;
    }



    public void alislarExcel(){

        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<Alis> alislar = vti.butunAlislariGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        //CreateAlisHeaderRow(workbook, sheet);
        String[] sutunIsimleri = {"Alış Numarası","Sepet Numarası","Ürün Adı","Adet","Birim Fiyatı","Toplam Tutar","KDV Oranı","KDV","Kargo Fiyatı","KDV Oranı (Kargo)","KDV (Kargo)","Genel Toplam (Vergiler Dahil)","Tarih"};
        CreateTable(workbook,"Tablo",1,alislar.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillAlisDataIntoExcel(workbook,sheet,alislar,newTableDataCellStyle(workbook),vti);

        if(storeExcelInStorage(getContext(),"Alışlar.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/Alışlar.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    public void  satislarExcel(){
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<Satis> satislar = vti.butunSatislariGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        String[] sutunIsimleri = {"Satış Numarası","Sepet Numarası","Ürün Adı","Adet","Birim Fiyatı","Toplam Tutar","KDV Oranı","KDV","Kargo Fiyatı","KDV Oranı (Kargo)","KDV (Kargo)","Genel Toplam (Vergi Dahil)","Tarih"};
        CreateTable(workbook,"Tablo",1,satislar.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillSatisDataIntoExcel(workbook,sheet,satislar,newTableDataCellStyle(workbook),vti);

        if(storeExcelInStorage(getContext(),"Satışlar.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/Satışlar.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    public void sepetlerExcel(){
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<Sepet> sepetler = vti.butunSepetleriGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        String[] sutunIsimleri = {"Sepet Numarası","Kullanıcı Adı","İşlem Türü","Toplam Ürün Adeti","Kargo Fiyatı (Vergi Dahil)","Komisyon","Genel Toplam (Vergi Dahil)","Sepet Açıklaması","Tarih"};
        CreateTable(workbook,"Tablo",1,sepetler.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillSepetDataIntoExcel(workbook,sheet,sepetler,newTableDataCellStyle(workbook));

        if(storeExcelInStorage(getContext(),"Sepetler.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/Sepetler.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    public void stoklarExcel(){
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<Stok> stoklar = vti.butunStoklarıGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        String[] sutunIsimleri = {"Sepet Numarası","Ürün Adı","Ürün Adeti","Ortalama Ödenen fiyat","Adet Başına Ort. Ödenen Fiyat"};
        CreateTable(workbook,"Tablo",1,stoklar.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillStoktDataIntoExcel(workbook,sheet,stoklar,newTableDataCellStyle(workbook),vti);

        if(storeExcelInStorage(getContext(),"Stoklar.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/Stoklar.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    public void urunlerExcel(){
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<Urun> urunler = vti.butunUrunleriGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        String[] sutunIsimleri = {"Barkod Numarası","Ürün Adı","Ürün Vergi Oranı (KDV)","İstenen kar Oranı","Ürün Resmi"};
        CreateTable(workbook,"Tablo",1,urunler.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillUrunDataIntoExcel(workbook,sheet,urunler,newTableDataCellStyle(workbook));

        if(storeExcelInStorage(getContext(),"Urunler.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/Urunler.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    public void bozukUrunlerExcel(){
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<BozukUrun> urunler = vti.butunBozukUrunleriGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        String[] sutunIsimleri = {"Bozuk Ürün Numarası","Ekleyen Kullanıcı Adı","Ürün Adı","Adet","Açıklama","Tarih"};
        CreateTable(workbook,"Tablo",1,urunler.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillBozukUrunDataIntoExcel(workbook,sheet,urunler,newTableDataCellStyle(workbook),vti);

        if(storeExcelInStorage(getContext(),"Bozuk_Ürünler.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/Bozuk_Ürünler.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    public void iadelerExcel(){
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<Iade> iadeler = vti.butunIadeleriGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        String[] sutunIsimleri = {"İade Numarası","İade Yönü","Kullanıcı Adı","Sepet Numarası","İşlem Numarası","Ürün Adı","Adet","İade Tutarı","İade Açıklaması","Tarih"};
        CreateTable(workbook,"Tablo",1,iadeler.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillIadeDataIntoExcel(workbook,sheet,iadeler,newTableDataCellStyle(workbook),vti);

        if(storeExcelInStorage(getContext(),"İadeler.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/İadeler.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    public void iadestoklarExcel(){
        VeritabaniIslemleri vti = new VeritabaniIslemleri(getContext());
        ArrayList<IadeStok> stoklar = vti.butunIadeStoklarıGetir();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(EXCEL_SHEET_NAME);

        String[] sutunIsimleri = {"Stok Numarası","İade Numarası","Ürün Adı","Ürün Adeti","Adet Alış fiyatı"};
        CreateTable(workbook,"Tablo",1,stoklar.size()+1,sutunIsimleri,newTableHeaderCellStyle(workbook),"TableStyleLight1");
        fillIadeStoktDataIntoExcel(workbook,sheet,stoklar,newTableDataCellStyle(workbook),vti);

        if(storeExcelInStorage(getContext(),"İade_Stokları.xlsx", workbook)){
            new GlideToast.makeToast(getActivity(), "Documents/Stok Takip/İade_Stokları.xlsx.", GlideToast.LENGTHTOOLONG, GlideToast.SUCCESSTOAST).show();
        }else{
            new GlideToast.makeToast(getActivity(), "Dosya oluşturulamadı.", GlideToast.LENGTHTOOLONG, GlideToast.FAILTOAST).show();
        }
    }

    private static void fillAlisDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<Alis> alis, CellStyle cellStyle, VeritabaniIslemleri vti) {
        for (int i = 0; i < alis.size(); i++) {

            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(alis.get(i).getId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            cell.setCellValue(alis.get(i).getSepetId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            cell.setCellValue(vti.barkodaGoreUrunGetir(alis.get(i).getBarkodNo()).getAd());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(alis.get(i).getAdet());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(4);
            DecimalFormat df = new DecimalFormat("#.##");
            cell.setCellValue(df.format((alis.get(i).getAlisFiyati()/alis.get(i).getAdet())) + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(5);
            cell.setCellValue(alis.get(i).getAlisFiyati() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(6);
            cell.setCellValue(alis.get(i).getVergiOran() + " %");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(7);
            cell.setCellValue(alis.get(i).getVergi() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(8);
            cell.setCellValue(alis.get(i).getKargo() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(9);
            cell.setCellValue(alis.get(i).getKargo_vergi_oran() + " %");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(10);
            cell.setCellValue(alis.get(i).getKargo_vergi() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(11);
            cell.setCellValue((alis.get(i).getKargo_vergi()+alis.get(i).getKargo()+alis.get(i).getAlisFiyati()+alis.get(i).getVergi())+ " ₺");
            cell.setCellStyle(cellStyle);

            ZamanFormatlayici zf = new ZamanFormatlayici();
            String tarih = zf.zamanFormatla(vti.sepetIdyeGoreSepetGetir(alis.get(i).getSepetId()).getIslemTarihi(),"yyyy-MM-dd HH:mm:ss", "HH:mm - dd.MM.yyyy");

            cell = rowData.createCell(12);
            cell.setCellValue(tarih);
            cell.setCellStyle(cellStyle);
        }
    }

    private static void fillSatisDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<Satis> satis, CellStyle cellStyle, VeritabaniIslemleri vti) {
        for (int i = 0; i < satis.size(); i++) {

            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(satis.get(i).getId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            cell.setCellValue(satis.get(i).getSepetId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            cell.setCellValue(vti.barkodaGoreUrunGetir(satis.get(i).getBarkodNo()).getAd());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(satis.get(i).getAdet());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(4);
            DecimalFormat df = new DecimalFormat("#.##");
            cell.setCellValue(df.format((satis.get(i).getSatisFiyati()/satis.get(i).getAdet())) + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(5);
            cell.setCellValue(satis.get(i).getSatisFiyati() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(6);
            cell.setCellValue(satis.get(i).getVergiOran() + " %");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(7);
            cell.setCellValue(satis.get(i).getVergi() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(8);
            cell.setCellValue(satis.get(i).getKargo() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(9);
            cell.setCellValue(satis.get(i).getKargo_vergi_oran() + " %");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(10);
            cell.setCellValue(satis.get(i).getKargo_vergi() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(11);
            cell.setCellValue((satis.get(i).getKargo_vergi()+satis.get(i).getKargo()+satis.get(i).getSatisFiyati()+satis.get(i).getVergi())+ " ₺");
            cell.setCellStyle(cellStyle);

            ZamanFormatlayici zf = new ZamanFormatlayici();
            String tarih = zf.zamanFormatla(vti.sepetIdyeGoreSepetGetir(satis.get(i).getSepetId()).getIslemTarihi(),"yyyy-MM-dd HH:mm:ss", "HH:mm - dd.MM.yyyy");

            cell = rowData.createCell(12);
            cell.setCellValue(tarih);
            cell.setCellStyle(cellStyle);
        }
    }

    private static void fillSepetDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<Sepet> sepet, CellStyle cellStyle) {
        for (int i = 0; i < sepet.size(); i++) {

            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(sepet.get(i).getSepetId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            cell.setCellValue(sepet.get(i).getKid());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            if(sepet.get(i).getIslemTuru().equalsIgnoreCase("in")){
                cell.setCellValue("Alım");
            }else{
                cell.setCellValue("Satım");
            }
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(sepet.get(i).getAdet());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(4);
            cell.setCellValue(sepet.get(i).getToplamKargo() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(5);
            cell.setCellValue(sepet.get(i).getKomisyon() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(6);
            cell.setCellValue(sepet.get(i).getToplamFiyat() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(7);
            cell.setCellValue(sepet.get(i).getAciklama());
            cell.setCellStyle(cellStyle);

            ZamanFormatlayici zf = new ZamanFormatlayici();
            String tarih = zf.zamanFormatla(sepet.get(i).getIslemTarihi(),"yyyy-MM-dd HH:mm:ss", "HH:mm - dd.MM.yyyy");
            cell = rowData.createCell(8);
            cell.setCellValue(tarih);
            cell.setCellStyle(cellStyle);
        }
    }

    private static void fillStoktDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<Stok> stok, CellStyle cellStyle, VeritabaniIslemleri vti) {
        for (int i = 0; i < stok.size(); i++) {
            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(stok.get(i).getStokId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            cell.setCellValue(vti.barkodaGoreUrunGetir(stok.get(i).getBarkodNo()).getAd());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            cell.setCellValue(stok.get(i).getAdet());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(stok.get(i).getOrtOdenenFiyat() + " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(4);
            cell.setCellValue(stok.get(i).getAdetOrtOdenenFiyat() + " ₺");
            cell.setCellStyle(cellStyle);

        }
    }

    private static void fillIadeStoktDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<IadeStok> stok, CellStyle cellStyle, VeritabaniIslemleri vti) {
        for (int i = 0; i < stok.size(); i++) {
            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(stok.get(i).getId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            cell.setCellValue(stok.get(i).getIadeId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            cell.setCellValue(vti.barkodaGoreUrunGetir(stok.get(i).getBarkodNo()).getAd());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(stok.get(i).getAdet());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(4);
            cell.setCellValue(stok.get(i).getAdetAlisFiyati() + " ₺");
            cell.setCellStyle(cellStyle);

        }
    }

    private static void fillUrunDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<Urun> urun, CellStyle cellStyle) {
        for (int i = 0; i < urun.size(); i++) {

            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(urun.get(i).getBarkodNo());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            cell.setCellValue(urun.get(i).getAd());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            cell.setCellValue(urun.get(i).getVergiOrani() + " %");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(urun.get(i).getKarOrani()+ " %");
            cell.setCellStyle(cellStyle);



            int pictureIdx = workbook.addPicture(urun.get(i).getResim(), Workbook.PICTURE_TYPE_PNG);
            CreationHelper helper = workbook.getCreationHelper();
            ClientAnchor anchor = helper.createClientAnchor();
            Drawing drawing = sheet.createDrawingPatriarch();

            anchor.setCol1(4); //Column E
            anchor.setRow1(i+1); //Row 3
            anchor.setCol2(5); //Column F
            anchor.setRow2(i+2); //Row 4

            Picture pict = drawing.createPicture(anchor, pictureIdx);

            cell = rowData.createCell(4);
            //cell.setCellValue(urun.get(i).getResim());
            //cell.setCellStyle(cellStyle);
            cell.getRow().setHeight((short)(256*10));

        }
    }

    private static void fillBozukUrunDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<BozukUrun> urun, CellStyle cellStyle, VeritabaniIslemleri vti) {
        for (int i = 0; i < urun.size(); i++) {

            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(urun.get(i).getBozukUrunId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            cell.setCellValue(urun.get(i).getKullanıcıId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            cell.setCellValue(vti.barkodaGoreUrunGetir(urun.get(i).getBarkodNo()).getAd());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(urun.get(i).getAdet());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(4);
            cell.setCellValue(urun.get(i).getAciklama());
            cell.setCellStyle(cellStyle);


            ZamanFormatlayici zf = new ZamanFormatlayici();
            String tarih = zf.zamanFormatla(urun.get(i).getTarih(),"yyyy-MM-dd HH:mm:ss", "HH:mm - dd.MM.yyyy");
            cell = rowData.createCell(5);
            cell.setCellValue(tarih);
            cell.setCellStyle(cellStyle);

        }
    }

    private static void fillIadeDataIntoExcel(Workbook workbook, Sheet sheet , ArrayList<Iade> iade, CellStyle cellStyle, VeritabaniIslemleri vti) {
        for (int i = 0; i < iade.size(); i++) {
            Row rowData = sheet.createRow(i + 1);

            Cell cell = rowData.createCell(0);
            cell.setCellValue(iade.get(i).getIadeId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(1);
            if(iade.get(i).getIadeYonu().equalsIgnoreCase("out")){
                cell.setCellValue("Alım İadesi");
            }else{
                cell.setCellValue("Satım İadesi");
            }
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(2);
            cell.setCellValue(iade.get(i).getKullanıcıId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(3);
            cell.setCellValue(iade.get(i).getSepetId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(4);
            cell.setCellValue(iade.get(i).getAlisSatisId());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(5);
            cell.setCellValue(vti.barkodaGoreUrunGetir(iade.get(i).getBarkodNo()).getAd());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(6);
            cell.setCellValue(iade.get(i).getAdet());
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(7);
            cell.setCellValue(iade.get(i).getIadeTutarı()+ " ₺");
            cell.setCellStyle(cellStyle);

            cell = rowData.createCell(8);
            cell.setCellValue(iade.get(i).getAciklama());
            cell.setCellStyle(cellStyle);

            ZamanFormatlayici zf = new ZamanFormatlayici();
            String tarih = zf.zamanFormatla(iade.get(i).getTarih(),"yyyy-MM-dd HH:mm:ss", "HH:mm - dd.MM.yyyy");
            cell = rowData.createCell(9);
            cell.setCellValue(tarih);
            cell.setCellStyle(cellStyle);
        }
    }


    public void istatistiklerExcel(){

    }



    /*private static void CreateAlisHeaderRow(Workbook workbook, Sheet sheet) {
        CellStyle HeaderCellStyle = newHeaderCellStyle(workbook);

        Row headerRow = sheet.createRow(0);

        Cell cell = headerRow.createCell(0);
        cell.setCellValue("Alış Numarası");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(0, (15 * 256));

        cell = headerRow.createCell(1);
        cell.setCellValue("Sepet Numarası");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(1, (15 * 256));

        cell = headerRow.createCell(2);
        cell.setCellValue("Ürün Adı");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(2, (15 * 256));

        cell = headerRow.createCell(3);
        cell.setCellValue("Adet");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(3, (15 * 256));

        cell = headerRow.createCell(4);
        cell.setCellValue("Birim Fiyatı");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(4, (15 * 256));

        cell = headerRow.createCell(5);
        cell.setCellValue("Toplam Tutar");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(5, (15 * 256));

        cell = headerRow.createCell(6);
        cell.setCellValue("KDV Oranı");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(6, (15 * 256));

        cell = headerRow.createCell(7);
        cell.setCellValue("Vergi");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(7, (15 * 256));

        cell = headerRow.createCell(8);
        cell.setCellValue("Kargo Fiyatı");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(8, (15 * 256));

        cell = headerRow.createCell(9);
        cell.setCellValue("Genel Toplam (Vergi Dahil)");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(9, (15 * 256));

        cell = headerRow.createCell(10);
        cell.setCellValue("Tarih");
        cell.setCellStyle(HeaderCellStyle);
        sheet.setColumnWidth(10, (15 * 256));
    }*/

    //"A1:C11" Max 20 sütun
    private static void CreateTable(XSSFWorkbook workbook,String tableName,int tableId,int satirSayisi, String[] sutunIsimleri, CellStyle headerStyle, String tableStyle){
        int sutunSayısı = sutunIsimleri.length;
        if(satirSayisi <= 1){satirSayisi=2;}


        String tabloBoyutu = tabloBoyutlandirici(sutunSayısı,satirSayisi);
        XSSFSheet sheet = workbook.getSheetAt(0);

        XSSFTable table = sheet.createTable(null);

        CTTable cttable = table.getCTTable();

        cttable.setDisplayName(tableName);//tablonun görünür ismi
        cttable.setId(tableId);//tablonun id si
        cttable.setName(tableName);//tablonun ismi
        cttable.setRef(tabloBoyutu);//tablo büyüklüğü referansı (Referans kadar sütün ve satır gerek)
        cttable.setTotalsRowShown(false);

        CTTableStyleInfo styleInfo = cttable.addNewTableStyleInfo();
        styleInfo.setName(tableStyle);//Tablo stili
        styleInfo.setShowColumnStripes(false);//Çizgili sütunlar kapalı
        styleInfo.setShowRowStripes(true);//Çizgili satırlar açık, beyaz+mavi+beyaz....

        CTTableColumns columns = cttable.addNewTableColumns();
        CTAutoFilter autofilter = cttable.addNewAutoFilter();

        columns.setCount(sutunSayısı);
        for (int i = 0; i < columns.getCount(); i++) {
            CTTableColumn column = columns.addNewTableColumn();//Tabloya sütun ekliyor
            column.setId(i + 1); //Sütun ıd si
            column.setName("Column" + i);//Sütun ismi

            CTFilterColumn filter = autofilter.addNewFilterColumn();//Sütuna filtre oku ekliyor
            filter.setColId(i + 1); //filtre sütun id si
            filter.setShowButton(true); //filtre oku gösteriliyor
        }


        XSSFRow row = sheet.createRow(0);
        for(int c = 0; c < columns.getCount(); c++) {
            XSSFCell cell = row.createCell(c);
            cell.setCellValue(sutunIsimleri[c]); //content **must** be here for table column names
            cell.setCellStyle(headerStyle);
        }

        for(int i = 0; i < columns.getCount(); i++) {
            sheet.setColumnWidth(i, (20 * 256));//15*256
            //sheet.autoSizeColumn(i);//i. sütünu otomatik boyutlandırıyor
            sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);//i. sütua filtre oku kadar daha boyutunu büyütüyor
        }


    }
    //sutun max 20
    private static String tabloBoyutlandirici(int SutunSayisi, int SatirSayisi){
        String sutun;
        switch(SutunSayisi) {
            case 1:
                sutun = "A";
                break;
            case 2:
                sutun = "B";
                break;
            case 3:
                sutun = "C";
                break;
            case 4:
                sutun = "D";
                break;
            case 5:
                sutun = "E";
                break;
            case 6:
                sutun = "F";
                break;
            case 7:
                sutun = "G";
                break;
            case 8:
                sutun = "H";
                break;
            case 9:
                sutun = "I";
                break;
            case 10:
                sutun = "J";
                break;
            case 11:
                sutun = "K";
                break;
            case 12:
                sutun = "L";
                break;
            case 13:
                sutun = "M";
                break;
            case 14:
                sutun = "N";
                break;
            case 15:
                sutun = "O";
                break;
            case 16:
                sutun = "P";
                break;
            case 17:
                sutun = "Q";
                break;
            case 18:
                sutun = "R";
                break;
            case 19:
                sutun = "S";
                break;
            case 20:
                sutun = "T";
                break;
            default:
                sutun = "A";
        }
        return "A1:" + sutun + SatirSayisi;
    }

    /*private static CellStyle newHeaderCellStyle(Workbook workbook) {
        CellStyle headerCellStyle  = workbook.createCellStyle();

        FillPatternType aa = FillPatternType.SOLID_FOREGROUND;
        HorizontalAlignment bb = HorizontalAlignment.CENTER;

        headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        headerCellStyle.setFillPattern(aa);
        headerCellStyle.setAlignment(bb);
        return headerCellStyle;
    }*/

    /*private static CellStyle newDataCellStyle(Workbook workbook) {
        CellStyle dataCellStyle  = workbook.createCellStyle();

        dataCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);//HSSFColor.WHITE.index
        dataCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        return dataCellStyle;
    }*/

    private static CellStyle newTableHeaderCellStyle(Workbook workbook) {
        CellStyle dataCellStyle  = workbook.createCellStyle();
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        return dataCellStyle;
    }

    private static CellStyle newTableDataCellStyle(Workbook workbook) {
        CellStyle dataCellStyle  = workbook.createCellStyle();

        //dataCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);//HSSFColor.WHITE.index
        //dataCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        dataCellStyle.setAlignment(HorizontalAlignment.CENTER);
        dataCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return dataCellStyle;
    }

    private static boolean storeExcelInStorage(Context context, String fileName, Workbook workbook) {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.v("HA", "Storage not available or read only");
            return false;
        }


        boolean isSuccess = false;

        File folder  = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/Stok Takip/");//context.getExternalFilesDir(null)
        if (!folder .exists()) {
            isSuccess = folder .mkdirs();
            if (!isSuccess) {
                return isSuccess;
            }
        }


        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/Stok Takip/", fileName);//context.getExternalFilesDir(null)
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            Log.v("HA", "Writing file" + file);
            isSuccess = true;
        } catch (IOException e) {
            Log.v("HA", "Error writing Exception: ", e);
            isSuccess = false;
        } catch (Exception e) {
            Log.v("HA", "Failed to save file due to Exception: ", e);
            isSuccess = false;
        } finally {
            try {
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return isSuccess;
    }

    private static boolean isExternalStorageReadOnly() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(externalStorageState);
    }

    private static boolean isExternalStorageAvailable() {
        String externalStorageState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(externalStorageState);
    }

    /*private static Object resizeArray (Object oldArray, int newSize) {
        int oldSize = java.lang.reflect.Array.getLength(oldArray);
        Class elementType = oldArray.getClass().getComponentType();
        Object newArray = java.lang.reflect.Array.newInstance(elementType, newSize);
        int preserveLength = Math.min(oldSize, newSize);
        if (preserveLength > 0)
            System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
        return newArray;
    }*/

}
