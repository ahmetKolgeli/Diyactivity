package com.ya.diyactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProgramOlustur extends AppCompatActivity {

    RadioGroup rgCinsiyet,rgHedef,rgZorluk,rgBeslenme;
    RadioButton rbKadin,rbErkek, rbHedefKiloAlma,rbHedefKiloVerme,rbHedefFitness,rbZorlukKolay,rbZorlukOrta,rbZorlukZor,rbBeslenmeNormal,rbBeslenmeVejeteryan;
    Button buttonProgramOlustur;
    EditText etYas,etBoy,etKilo;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private FirebaseFirestore mFirestore;
    private DatabaseReference mReference;

    private HashMap<String,Object> mData;
    private DocumentReference docReference;


    public String adSoyad,txtGelenAd;
    private String txtCinsiyet,txtHedefSecimi,txtZorlukSecimi,txtBeslenmeSecimi,txtYasSecim;
    private  int txtYas,txtBoy,txtKilo;

    public String adSoyadGetir(){

        mUser = mAuth.getCurrentUser();
        docReference = mFirestore.collection("Kullanicilar").document(mUser.getUid());
        docReference.get()
                .addOnSuccessListener(ProgramOlustur.this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        adSoyad= (String) documentSnapshot.getData().get("adSoyad");
                        Toast.makeText(ProgramOlustur.this, adSoyad, Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(ProgramOlustur.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProgramOlustur.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        /*docReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                task.getResult();
                DocumentSnapshot documentSnapshot= task.getResult();
                if (documentSnapshot.exists()){

                    String adSoyadGelen = documentSnapshot.getString("adSoyad");
                    Toast.makeText(ProgramOlustur.this, adSoyadGelen, Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProgramOlustur.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/


        return adSoyad;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_olustur);

        rgCinsiyet=findViewById(R.id.rgCinsiyet);
        rgHedef=findViewById(R.id.rgHedef);
        rgZorluk=findViewById(R.id.rgZorluk);
        rgBeslenme=findViewById(R.id.rgBeslenme);
        rbKadin=findViewById(R.id.rbKadin);
        rbErkek=findViewById(R.id.rbErkek);
        rbHedefKiloAlma=findViewById(R.id.rbHedefKiloAlma);
        rbHedefKiloVerme=findViewById(R.id.rbHedefKiloVerme);
        rbHedefFitness=findViewById(R.id.rbHedefFitness);
        rbZorlukKolay=findViewById(R.id.rbZorlukKolay);
        rbZorlukOrta=findViewById(R.id.rbZorlukOrta);
        rbZorlukZor=findViewById(R.id.rbZorlukZor);
        rbBeslenmeNormal=findViewById(R.id.rbBeslenmeNormal);
        rbBeslenmeVejeteryan=findViewById(R.id.rbBeslenmeVejeteryan);
        buttonProgramOlustur=findViewById(R.id.btnProgramiOlustur);
        etYas=findViewById(R.id.etYas);
        etBoy=findViewById(R.id.etBoy);
        etKilo=findViewById(R.id.etKilo);

        mAuth=FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference();
        mFirestore= FirebaseFirestore.getInstance();

        rgCinsiyet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rbKadin){
                    txtCinsiyet="1";
                    //Toast.makeText(ProgramOlustur.this, txtCinsiyet, Toast.LENGTH_SHORT).show();
                }
                if (i==R.id.rbErkek){
                    txtCinsiyet="2";
                    //Toast.makeText(ProgramOlustur.this, txtCinsiyet, Toast.LENGTH_SHORT).show();
                }
            }
        });

        rgHedef.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rbHedefKiloAlma){
                    txtHedefSecimi="1";
                    //Toast.makeText(ProgramOlustur.this, txtHedefSecimi, Toast.LENGTH_SHORT).show();

                }
                if (i==R.id.rbHedefKiloVerme){
                    txtHedefSecimi="2";
                    //Toast.makeText(ProgramOlustur.this, txtHedefSecimi, Toast.LENGTH_SHORT).show();

                }
                if (i==R.id.rbHedefFitness){
                    txtHedefSecimi="3";
                    //Toast.makeText(ProgramOlustur.this, txtHedefSecimi, Toast.LENGTH_SHORT).show();

                }
            }
        });

        rgZorluk.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rbZorlukKolay){
                    txtZorlukSecimi="1";
                    //Toast.makeText(ProgramOlustur.this, txtZorlukSecimi, Toast.LENGTH_SHORT).show();
                }
                if (i==R.id.rbZorlukOrta){
                    txtZorlukSecimi="2";
                    //Toast.makeText(ProgramOlustur.this, txtZorlukSecimi, Toast.LENGTH_SHORT).show();

                }
                if (i==R.id.rbZorlukZor){
                    txtZorlukSecimi="3";
                    //Toast.makeText(ProgramOlustur.this, txtZorlukSecimi, Toast.LENGTH_SHORT).show();

                }
            }
        });

        rgBeslenme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.rbBeslenmeNormal){
                    txtBeslenmeSecimi="1";
                    //Toast.makeText(ProgramOlustur.this, txtBeslenmeSecimi, Toast.LENGTH_SHORT).show();
                }
                if (i==R.id.rbBeslenmeVejeteryan){
                    txtBeslenmeSecimi="2";
                    //Toast.makeText(ProgramOlustur.this, txtBeslenmeSecimi, Toast.LENGTH_SHORT).show();

                }

            }
        });

        buttonProgramOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtGelenAd=adSoyadGetir();
                System.out.println(txtGelenAd);

                txtYas= Integer.parseInt(etYas.getText().toString());
                System.out.println("Yaş: "+txtYas);
                txtBoy= Integer.parseInt(etBoy.getText().toString());
                System.out.println("Boy: "+txtBoy);
                txtKilo= Integer.parseInt(etKilo.getText().toString());
                System.out.println("Kilo: "+txtKilo);

                if (txtYas<30){
                    txtYasSecim="1";
                }
                else if (txtYas>=30){
                    txtYasSecim="2";
                }

                StringBuilder prgKoduBuilder = new StringBuilder();
                prgKoduBuilder.delete(0, prgKoduBuilder.length());
                prgKoduBuilder.append(txtCinsiyet);
                prgKoduBuilder.append(txtYasSecim);
                prgKoduBuilder.append(txtHedefSecimi);
                prgKoduBuilder.append(txtZorlukSecimi);
                prgKoduBuilder.append(txtBeslenmeSecimi);

                String txtPrgKodu=prgKoduBuilder.toString();
                Toast.makeText(ProgramOlustur.this, txtPrgKodu, Toast.LENGTH_SHORT).show();

                mUser=mAuth.getCurrentUser();
                mData = new HashMap<>();
                mData.put("kullaniciId", mUser.getUid());
                mData.put("yas", txtYas);
                mData.put("boy", txtBoy);
                mData.put("kilo", txtKilo);
                mData.put("cinsiyet",txtCinsiyet);
                mData.put("aktiviteZorlugu",txtZorlukSecimi);
                mData.put("beslenmeTuru",txtBeslenmeSecimi);
                mData.put("hedef",txtHedefSecimi);
                mData.put("programId",txtPrgKodu);

                mUser = mAuth.getCurrentUser();
                docReference = mFirestore.collection("Kullanicilar").document(mUser.getUid());
                docReference.get()
                        .addOnSuccessListener(ProgramOlustur.this, new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                adSoyad= (String) documentSnapshot.getData().get("adSoyad");
                                mUser=mAuth.getCurrentUser();
                                mData = new HashMap<>();
                                mData.put("kullaniciId", mUser.getUid());
                                mData.put("adSoyad", adSoyad);
                                mData.put("yas", txtYas);
                                mData.put("boy", txtBoy);
                                mData.put("kilo", txtKilo);
                                mData.put("cinsiyet",txtCinsiyet);
                                mData.put("aktiviteZorlugu",txtZorlukSecimi);
                                mData.put("beslenmeTuru",txtBeslenmeSecimi);
                                mData.put("hedef",txtHedefSecimi);
                                mData.put("programId",txtPrgKodu);
                                mFirestore.collection("KullaniciProgramBilgileri").document(mUser.getUid())
                                        .set(mData)
                                        .addOnCompleteListener(ProgramOlustur.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(ProgramOlustur.this, "Programınız Oluşturuldu", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(ProgramOlustur.this,AnaEkran.class);
                                                    startActivity(intent);
                                                }
                                                else{
                                                    Toast.makeText(ProgramOlustur.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                            }
                        }).addOnFailureListener(ProgramOlustur.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProgramOlustur.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


                /*mFirestore.collection("KullaniciProgramBilgileri").document(mUser.getUid())
                        .set(mData)
                        .addOnCompleteListener(ProgramOlustur.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ProgramOlustur.this, "Programınız Oluşturuldu", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ProgramOlustur.this,ProfilEkrani.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(ProgramOlustur.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });*/


            }
        });



    }
}