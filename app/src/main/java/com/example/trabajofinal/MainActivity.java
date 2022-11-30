package com.example.trabajofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btn_reg;
    EditText nom, valor, tipo;
    private FirebaseFirestore mfirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirestore = FirebaseFirestore.getInstance();



        btn_reg = findViewById(R.id.regis);
        nom = findViewById(R.id.et_nom);
        valor = findViewById(R.id.et_monto);
        tipo = findViewById(R.id.et_tcu);

        btn_reg.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String cuenta = nom.getText().toString().trim();
                String monto = valor.getText().toString().trim();
                String tipos = tipo.getText().toString().trim();

                if (cuenta.isEmpty()&&monto.isEmpty()&&tipos.isEmpty()){
                    Toast.makeText(getApplicationContext(),"ERROR: Rellene los campos", Toast.LENGTH_SHORT).show();
                }else{
                    postAcount(cuenta, monto, tipos);
                }


            }
        });

    }
    private void postAcount(String cuenta, String monto, String tipos){
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", cuenta);
        map.put("valor", monto);
        map.put("tip", tipos);

        mfirestore.collection("cuenta").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Creado existosamente", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"ERROR: Error al ingresar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return false;
    }
}