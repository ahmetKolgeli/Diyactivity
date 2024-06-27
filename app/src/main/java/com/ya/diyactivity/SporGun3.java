package com.ya.diyactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SporGun3 extends AppCompatActivity {

    private TextView tvSporGun3;
    private String txtProgramId, txtProgramGun3Icerik;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private DocumentReference docReferenceKPB;
    private HashMap<String, Object> mData;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ana,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_ana:
                Intent intentAna = new Intent(SporGun3.this,AnaEkran.class);
                startActivity(intentAna);
                break;
            case R.id.menu_item_spor:
                Intent intentSpor = new Intent(SporGun3.this,SporEkrani.class);
                startActivity(intentSpor);
                break;
            case R.id.menu_item_diyet:
                Intent intentDiyet = new Intent(SporGun3.this,DiyetEkrani.class);
                startActivity(intentDiyet);
                break;
            case R.id.menu_item_profil:
                Intent intentProfil = new Intent(SporGun3.this,ProfilEkrani.class);
                startActivity(intentProfil);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spor_gun3);

        tvSporGun3=findViewById(R.id.tvSporGun3);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();



        docReferenceKPB =mFirestore.collection("KullaniciProgramBilgileri").document(mUser.getUid());
        docReferenceKPB.get()
                .addOnSuccessListener(SporGun3.this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            txtProgramId=(String) documentSnapshot.getData().get("programId");
                            mFirestore.collection("SporProgramlari").document(txtProgramId)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            txtProgramGun3Icerik=(String) documentSnapshot.getData().get("gun3");
                                            tvSporGun3.setText(txtProgramGun3Icerik);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SporGun3.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });


                            /*mData= (HashMap<String, Object>) documentSnapshot.getData();
                            for(Map.Entry data: mData.entrySet()){
                                System.out.println(data.getKey() + " = "+data.getValue());
                            }*/
                        }
                    }
                }).addOnFailureListener(SporGun3.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SporGun3.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }
}