package com.ya.diyactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.transition.MaterialContainerTransform;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class KayitEkrani extends AppCompatActivity {
    EditText etAdSoyad,etSifre,etSifreOnay,etMail;
    Button btnKayit;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String, Object> mData;
    private FirebaseFirestore mFirestore;

    public String adSoyad="";
    public String sifre="";
    public String sifreOnay="";
    public String mail="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ekrani);
        etAdSoyad=findViewById(R.id.etAdSoyad);
        etSifre=findViewById(R.id.etSifre);
        etSifreOnay=findViewById(R.id.etSifreOnay);
        etMail=findViewById(R.id.etMail);
        btnKayit=findViewById(R.id.btnKayit);

        mAuth= FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference();
        mFirestore=FirebaseFirestore.getInstance();



       btnKayit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               adSoyad=etAdSoyad.getText().toString();
               sifre=etSifre.getText().toString();
               sifreOnay=etSifreOnay.getText().toString();
               mail=etMail.getText().toString();

                   if(TextUtils.isEmpty(adSoyad) || TextUtils.isEmpty(sifre) || TextUtils.isEmpty(sifreOnay) || TextUtils.isEmpty(mail)){
                       Toast.makeText(KayitEkrani.this, "Lütfen tüm gerekli alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                   }
                   else if (!sifre.equals(sifreOnay)){
                       Toast.makeText(KayitEkrani.this, "Şifreler birbiriyle uyuşmuyor.", Toast.LENGTH_SHORT).show();
                   }
                   else {
                       mAuth.createUserWithEmailAndPassword(mail,sifre)
                                       .addOnCompleteListener(KayitEkrani.this, new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                               if(task.isSuccessful()) {
                                                   mUser=mAuth.getCurrentUser();
                                                   mData = new HashMap<>();
                                                   mData.put("adSoyad",adSoyad);
                                                   mData.put("kullaniciMail",mail);
                                                   mData.put("kullaniciSifre",sifre);
                                                   mData.put("kullaniciId",mUser.getUid());

                                                   mFirestore.collection("Kullanicilar").document(mUser.getUid())
                                                           .set(mData)
                                                           .addOnCompleteListener(KayitEkrani.this, new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                   if (task.isSuccessful()){
                                                                       Toast.makeText(KayitEkrani.this, "Kayıt başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                                                                   }
                                                                   else{
                                                                       Toast.makeText(KayitEkrani.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                   }

                                                               }
                                                           });

                                                    //REALTIME DATABASE
                                                   /*mReference.child("Kullanicilar").child(mUser.getUid())
                                                           .setValue(mData)
                                                           .addOnCompleteListener(KayitEkrani.this, new OnCompleteListener<Void>() {
                                                               @Override
                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                   if (task.isSuccessful()){
                                                                       Toast.makeText(KayitEkrani.this, "Kayıt başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                                                                   }
                                                                   else {
                                                                       Toast.makeText(KayitEkrani.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                                                   }

                                                               }
                                                           });*/

                                               }

                                               else
                                                   Toast.makeText(KayitEkrani.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                           }
                                       });


                       Temizle();
                   }

           }

           private void Temizle() {
               etAdSoyad.setText(null);
               etSifre.setText(null);
               etSifreOnay.setText(null);
               etMail.setText(null);
           }
       });


    }
}