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

public class DiyetEkrani extends AppCompatActivity {

    private TextView tvSabah,tvOgle,tvAksam;
    private String txtProgramId, txtSabah,txtOgle,txtAksam;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseFirestore mFirestore;
    private DocumentReference docReferenceKPB;
    private HashMap<String, Object> mData;

    //private Button btnPzt, btnSali, btnCar, btnPers, btnCuma, btnCmt, btnPazar;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ana,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_ana:
                Intent intent = new Intent(DiyetEkrani.this,AnaEkran.class);
                startActivity(intent);
                break;
            case R.id.menu_item_spor:
                Intent intentSpor = new Intent(DiyetEkrani.this,SporEkrani.class);
                startActivity(intentSpor);
                break;
            case R.id.menu_item_diyet:
                Intent intentDiyet = new Intent(DiyetEkrani.this,DiyetEkrani.class);
                startActivity(intentDiyet);
                break;
            case R.id.menu_item_profil:
                Intent intent1 = new Intent(DiyetEkrani.this,ProfilEkrani.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diyet_ekrani);

        tvSabah=findViewById(R.id.tvSabah);
        tvOgle=findViewById(R.id.tvOgle);
        tvAksam=findViewById(R.id.tvAksam);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        docReferenceKPB =mFirestore.collection("KullaniciProgramBilgileri").document(mUser.getUid());
        docReferenceKPB.get()
                .addOnSuccessListener(DiyetEkrani.this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            txtProgramId=(String) documentSnapshot.getData().get("programId");
                            mFirestore.collection("DiyetProgramlari").document(txtProgramId)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            txtSabah=(String) documentSnapshot.getData().get("sabah");
                                            txtOgle=(String) documentSnapshot.getData().get("ogle");
                                            txtAksam=(String) documentSnapshot.getData().get("aksam");
                                            //txtIcerik=txtIcerik.replaceAll("\n","\\n");
                                            tvSabah.setText(txtSabah);
                                            tvOgle.setText(txtOgle);
                                            tvAksam.setText(txtAksam);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(DiyetEkrani.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });


                            /*mData= (HashMap<String, Object>) documentSnapshot.getData();
                            for(Map.Entry data: mData.entrySet()){
                                System.out.println(data.getKey() + " = "+data.getValue());
                            }*/
                        }
                    }
                }).addOnFailureListener(DiyetEkrani.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DiyetEkrani.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

        /*btnPzt=findViewById(R.id.btnPzt);
        btnSali=findViewById(R.id.btnSali);
        btnCar=findViewById(R.id.btnCar);
        btnPers=findViewById(R.id.btnPers);
        btnCuma=findViewById(R.id.btnCuma);
        btnCmt=findViewById(R.id.btnCmt);
        btnPazar=findViewById(R.id.btnPazar);*/

        /*btnPzt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(DiyetEkrani.this,DiyetPzt.class);
                startActivity(intentAna);
            }
        });

        btnSali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(DiyetEkrani.this,DiyetSali.class);
                startActivity(intentAna);
            }
        });

        btnCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(DiyetEkrani.this,DiyetCarsamba.class);
                startActivity(intentAna);
            }
        });

        btnPers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(DiyetEkrani.this,DiyetPers.class);
                startActivity(intentAna);
            }
        });

        btnCuma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(DiyetEkrani.this,DiyetCuma.class);
                startActivity(intentAna);
            }
        });

        btnCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(DiyetEkrani.this,DiyetCmt.class);
                startActivity(intentAna);
            }
        });

        btnPazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAna = new Intent(DiyetEkrani.this,DiyetPazar.class);
                startActivity(intentAna);
            }
        });*/




    }
}