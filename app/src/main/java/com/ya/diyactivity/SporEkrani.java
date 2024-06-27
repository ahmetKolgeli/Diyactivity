package com.ya.diyactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class SporEkrani extends AppCompatActivity {

    private Button btnGun1, btnGun2, btnGun3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ana,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_ana:
                Intent intentAna = new Intent(SporEkrani.this,AnaEkran.class);
                startActivity(intentAna);
                break;
            case R.id.menu_item_spor:
                Intent intentSpor = new Intent(SporEkrani.this,SporEkrani.class);
                startActivity(intentSpor);
                break;
            case R.id.menu_item_diyet:
                Intent intentDiyet = new Intent(SporEkrani.this,DiyetEkrani.class);
                startActivity(intentDiyet);
                break;
            case R.id.menu_item_profil:
                Intent intentProfil = new Intent(SporEkrani.this,ProfilEkrani.class);
                startActivity(intentProfil);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spor_ekrani);

        btnGun1=findViewById(R.id.btnGun1);
        btnGun2=findViewById(R.id.btnGun2);
        btnGun3=findViewById(R.id.btnGun3);

        btnGun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(SporEkrani.this,SporGun1.class);
                startActivity(intentAna);
            }
        });

        btnGun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(SporEkrani.this,SporGun2.class);
                startActivity(intentAna);
            }
        });

        btnGun3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(SporEkrani.this,SporGun3.class);
                startActivity(intentAna);
            }
        });
    }
}