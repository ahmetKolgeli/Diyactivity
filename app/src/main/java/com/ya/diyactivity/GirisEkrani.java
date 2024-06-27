package com.ya.diyactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class GirisEkrani extends AppCompatActivity {

    Button btnGiris;
    EditText etMailGiris,etSifreGiris;

    public String mailGiris="";
    public String sifreGiris="";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_ekrani);
        btnGiris=findViewById(R.id.btnGiris);
        etMailGiris=findViewById(R.id.etMailGiris);
        etSifreGiris=findViewById(R.id.etSifreGiris);

        mAuth=FirebaseAuth.getInstance();
        mFirestore=FirebaseFirestore.getInstance();
        //mUser = mAuth.getCurrentUser();




        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mailGiris=etMailGiris.getText().toString();
                sifreGiris=etSifreGiris.getText().toString();

                if (!TextUtils.isEmpty(mailGiris) && !TextUtils.isEmpty(sifreGiris)){
                    mAuth.signInWithEmailAndPassword(mailGiris,sifreGiris)
                            .addOnSuccessListener(GirisEkrani.this, new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    mUser = mAuth.getCurrentUser();
                                    System.out.println("Mail: " +mUser.getEmail());
                                    System.out.println("User ID: " +mUser.getUid());
                                    Intent intent = new Intent(GirisEkrani.this,AnaEkran.class);
                                    startActivity(intent);



                                }
                            }).addOnFailureListener(GirisEkrani.this, new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(GirisEkrani.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });


                }
                else{
                    Toast.makeText(GirisEkrani.this, "Lütfen tüm gerekli alanları doldurunuz.", Toast.LENGTH_SHORT).show();
                }

            }
        });








    }
}