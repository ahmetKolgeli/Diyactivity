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

public class DiyetPers extends AppCompatActivity {

    private TextView tvDiyetPers;
    private String txtProgramId, txtPersIcerik;
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
                Intent intentAna = new Intent(DiyetPers.this,AnaEkran.class);
                startActivity(intentAna);
                break;
            case R.id.menu_item_spor:
                Intent intentSpor = new Intent(DiyetPers.this,SporEkrani.class);
                startActivity(intentSpor);
                break;
            case R.id.menu_item_diyet:
                Intent intentDiyet = new Intent(DiyetPers.this,DiyetEkrani.class);
                startActivity(intentDiyet);
                break;
            case R.id.menu_item_profil:
                Intent intentProfil = new Intent(DiyetPers.this,ProfilEkrani.class);
                startActivity(intentProfil);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diyet_pers);

        tvDiyetPers=findViewById(R.id.tvDiyetPers);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();



        docReferenceKPB =mFirestore.collection("KullaniciProgramBilgileri").document(mUser.getUid());
        docReferenceKPB.get()
                .addOnSuccessListener(DiyetPers.this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            txtProgramId=(String) documentSnapshot.getData().get("programId");
                            mFirestore.collection("DiyetProgramlari").document(txtProgramId)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            txtPersIcerik=(String) documentSnapshot.getData().get("persembe");
                                            tvDiyetPers.setText(txtPersIcerik);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DiyetPers.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });


                            /*mData= (HashMap<String, Object>) documentSnapshot.getData();
                            for(Map.Entry data: mData.entrySet()){
                                System.out.println(data.getKey() + " = "+data.getValue());
                            }*/
                        }
                    }
                }).addOnFailureListener(DiyetPers.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DiyetPers.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


    }
}