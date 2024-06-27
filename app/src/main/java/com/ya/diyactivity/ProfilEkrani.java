package com.ya.diyactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class ProfilEkrani extends AppCompatActivity {
    private TextView tvAdSoyad,tvUserId;
    private EditText etAdDegistir;
    private Button btnAdDegistir;
    private FirebaseFirestore mFirestore;
    private DocumentReference docReference;
    private StringBuilder sb;



    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mReference;
    private HashMap<String, Object> mData;
    private String txtAd,txtUserId,txtGuncelAd;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ana,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_ana:
                Intent intent = new Intent(ProfilEkrani.this,AnaEkran.class);
                startActivity(intent);
                break;
            case R.id.menu_item_spor:
                Intent intentAna = new Intent(ProfilEkrani.this,SporEkrani.class);
                startActivity(intentAna);
                break;
            case R.id.menu_item_diyet:
                Intent intentDiyet = new Intent(ProfilEkrani.this,DiyetEkrani.class);
                startActivity(intentDiyet);
                break;
            case R.id.menu_item_profil:
                Intent intent1 = new Intent(ProfilEkrani.this,ProfilEkrani.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_ekrani);

        tvAdSoyad=findViewById(R.id.tvAdSoyad);
        tvUserId=findViewById(R.id.tvUserId);
        etAdDegistir=findViewById(R.id.etAdDegistir);
        btnAdDegistir=findViewById(R.id.btnAdDegistir);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirestore = FirebaseFirestore.getInstance();

        docReference=mFirestore.collection("Kullanicilar").document(mUser.getUid());
        docReference.get()
                .addOnSuccessListener(ProfilEkrani.this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){

                            txtAd= (String) documentSnapshot.getData().get("adSoyad");
                            txtUserId= (String) documentSnapshot.getData().get("kullaniciId");
                            tvAdSoyad.setText("Adınız: "+txtAd);
                            tvUserId.setText("Kullanıcı Id: "+txtUserId);

                            mData= (HashMap<String, Object>) documentSnapshot.getData();
                            for(Map.Entry data: mData.entrySet()){
                                System.out.println(data.getKey() + " = "+data.getValue());
                            }
                        }
                    }
                }).addOnFailureListener(ProfilEkrani.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfilEkrani.this, e.getMessage(), Toast.LENGTH_SHORT).show();


                    }
                });

        //REALTIME DATABASE
        /*mReference= FirebaseDatabase.getInstance().getReference("Kullanicilar").child(mUser.getUid());

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.child("adSoyad").getValue());
                System.out.println(snapshot.child("kullaniciId").getValue());

                txtAd= (String) snapshot.child("adSoyad").getValue();
                txtUserId= (String) snapshot.child("kullaniciId").getValue();
                tvAdSoyad.setText("Adınız:\n"+txtAd);
                tvUserId.setText("Kullanıcı Id:\n"+txtUserId);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilEkrani.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });*/

        btnAdDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtGuncelAd=etAdDegistir.getText().toString();

                mData = new HashMap<>();
                mData.put("adSoyad",txtGuncelAd);
                veriGuncelle(mData,mUser.getUid());
            }
        });



    }
    private void veriGuncelle(HashMap<String, Object> hashMap,String uId){
        mFirestore.collection("Kullanicilar").document(uId)
                .update(hashMap)
                .addOnCompleteListener(ProfilEkrani.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(ProfilEkrani.this, "Başarıyla Güncellendi", Toast.LENGTH_SHORT).show();
                            docReference =mFirestore.collection("Kullanicilar").document(mUser.getUid());
                            docReference.get()
                                    .addOnSuccessListener(ProfilEkrani.this, new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if(documentSnapshot.exists()){

                                                txtAd= (String) documentSnapshot.getData().get("adSoyad");
                                                txtUserId= (String) documentSnapshot.getData().get("kullaniciId");
                                                tvAdSoyad.setText("Adınız: "+txtAd);
                                                tvUserId.setText("Kullanıcı Id: "+txtUserId);

                                                mData= (HashMap<String, Object>) documentSnapshot.getData();
                                                for(Map.Entry data: mData.entrySet()){
                                                    System.out.println(data.getKey() + " = "+data.getValue());
                                                }
                                            }
                                        }
                                    }).addOnFailureListener(ProfilEkrani.this, new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ProfilEkrani.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }
                    }
                });

    }
}