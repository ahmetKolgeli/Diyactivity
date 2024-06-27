package com.ya.diyactivity;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class AcilisAnimasyonu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_acilis_animasyonu);
        Thread acilisEkrani=new Thread(){
        public void run(){
            try {
                sleep(2000);
                Intent anaEkran=new Intent(AcilisAnimasyonu.this,MainActivity.class);
                startActivity(anaEkran);
            }
            catch (Exception e){}
        }
        };
        acilisEkrani.start();
    }
}