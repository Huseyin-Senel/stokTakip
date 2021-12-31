package com.example.huseyin.stoktakip.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.huseyin.stoktakip.fragments.AyarlarFragment;
import com.example.huseyin.stoktakip.fragments.AzalanUrunlerFragment;
import com.example.huseyin.stoktakip.fragments.IadeListesiFragment;
import com.example.huseyin.stoktakip.fragments.SepetGecmisiFragment;
import com.example.huseyin.stoktakip.fragments.IstatistiklerFragment;
import com.example.huseyin.stoktakip.R;
import com.example.huseyin.stoktakip.fragments.StokListesiFragment;
import com.example.huseyin.stoktakip.fragments.UrunAlFragment;
import com.example.huseyin.stoktakip.fragments.UrunSatFragment;
import com.example.huseyin.stoktakip.fragments.VeritabaniFragment;
import com.example.huseyin.stoktakip.fragments.IadelerFragment;
import com.example.huseyin.stoktakip.models.VeritabaniIslemleri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AnasayfaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String kadi;
    TextView kadiTxt;
    ImageView kullaniciResim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // GirisActivityden gelen kullanıcı adını header layout'a çekiyor
        Intent intent = getIntent();
        kadi = intent.getStringExtra("kadi");
        View headerLayout = navigationView.getHeaderView(0);
        kadiTxt = headerLayout.findViewById(R.id.txt_kadi);
        kullaniciResim = headerLayout.findViewById(R.id.img_kullanici3);
        kadiTxt.setText(kadi);

        Bundle kid = new Bundle();
        kid.putString("kadi", kadi);
        StokListesiFragment SLF = new StokListesiFragment();
        SLF.setArguments(kid);
        getSupportActionBar().setTitle(R.string.nav_stoklistesi_title);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, SLF).commit();

        VeritabaniIslemleri vti = new VeritabaniIslemleri(this);
        byte[] bytesImage = vti.kullaniciAdinaGoreKullaniciyiGetir(kadi).getResim();
        InputStream inputStream  = new ByteArrayInputStream(bytesImage);
        Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
        kullaniciResim.setImageBitmap(bitmap);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        // Eğer uygulamada geri tuşuna basılırsa çıkışı onaylayan AlertDialog açılır
        else {
            new AlertDialog.Builder(AnasayfaActivity.this)
                    .setTitle("Uygulamayı Kapat")
                    .setMessage("Çıkmak istediğinizden emin misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton("İptal", null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.anasayfa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cikis_yap) {
            finish();
            Intent intent = new Intent(this, GirisYapActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // GirişYapActivity'den gelen kullanici adı burada bundle objesine aktarılıyor.
        // Bu bundle UrunSatFragment, UrunAlFragment ve AyarlarFragment'ta kullanılacak.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        bundle.putString("kadi", kadi);

        if (id == R.id.nav_stoklistesi) {
            getSupportActionBar().setTitle(R.string.nav_stoklistesi_title);
            StokListesiFragment fragment = new StokListesiFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else if (id == R.id.nav_urunsat) {
            getSupportActionBar().setTitle(R.string.nav_urunsat_title);
            UrunSatFragment fragment = new UrunSatFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else if (id == R.id.nav_urunal) {
            getSupportActionBar().setTitle(R.string.nav_urunal_title);
            UrunAlFragment fragment = new UrunAlFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else if (id == R.id.nav_azalanurunler) {
            getSupportActionBar().setTitle(R.string.nav_azalanurunler_title);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AzalanUrunlerFragment()).commit();
        } else if (id == R.id.nav_islemgecmisi) {
            getSupportActionBar().setTitle(R.string.nav_islemgecmisi_title);
            SepetGecmisiFragment fragment = new SepetGecmisiFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }else if (id == R.id.nav_iadeler) {
            getSupportActionBar().setTitle(R.string.nav_iadeler_title);
            IadelerFragment fragment = new IadelerFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else if (id == R.id.nav_istatistikler) {
            getSupportActionBar().setTitle(R.string.nav_istatistikler_title);
            IstatistiklerFragment fragment = new IstatistiklerFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else if (id == R.id.nav_veritabani) {
            getSupportActionBar().setTitle(R.string.nav_veritabani_title);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new VeritabaniFragment()).commit();
        } else if (id == R.id.nav_ayarlar) {
            getSupportActionBar().setTitle(R.string.nav_ayarlar_title);
            AyarlarFragment fragment = new AyarlarFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        } else if (id == R.id.nav_cikis_yap) {
            finish();
            Intent intent = new Intent(this, GirisYapActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_iadestoklistesi) {
            getSupportActionBar().setTitle(R.string.nav_iade_stoklistesi_title);
            IadeListesiFragment fragment = new IadeListesiFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
