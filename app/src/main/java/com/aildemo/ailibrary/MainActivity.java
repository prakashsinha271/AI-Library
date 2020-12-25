package com.aildemo.ailibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText inputID, inputPass;
    Button btnLogin;
    TextView tvRegister, tvRecover;
    DatabaseReference rootRef, childRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputID = (EditText) findViewById(R.id.logID);
        inputPass = (EditText) findViewById(R.id.logPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        tvRecover = findViewById(R.id.tvRecover);

        rootRef = FirebaseDatabase.getInstance().getReference();
        childRef = rootRef.child("Students");

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, registration.class);
                startActivity(i);
            }
        });

        tvRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, recoverPass.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inID = null;
                String inPass = null;  // tPassword.getText().toString();
                try {
                    inID = inputID.getText().toString();
                    inPass = inputPass.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logVerify(inID, inPass);
            }
        });
    }

    private void logVerify(final String inIDNo, final String inPassword) {
        childRef.child(inIDNo).child("StudentID").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String value = dataSnapshot.getValue(String.class);

                if(value != null){
                    if (value.equals(inIDNo)) {
                        //Toast.makeText(MainActivity.this, "Input ID Found", Toast.LENGTH_LONG).show();

                        childRef.child(inIDNo).child("Password").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                final String dbPass = dataSnapshot.getValue(String.class);
                                if(dbPass != null){
                                    if (dbPass.equals(inPassword)) {

                                        childRef.child(inIDNo).child("ContactNo").addListenerForSingleValueEvent(new ValueEventListener() {

                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                final String dbMob = dataSnapshot.getValue(String.class);
                                                if(dbMob != null){
                                                    if (dbMob.equals(inPassword)) {

                                                        Intent i = new Intent(MainActivity.this, password.class);
                                                        i.putExtra("LoggedSTDID", inIDNo);
                                                        startActivity(i);

                                                        //Toast.makeText(MainActivity.this, "Password Found", Toast.LENGTH_LONG).show();
                                                    }else{
                                                        Intent i = new Intent(MainActivity.this, homePage.class);
                                                        i.putExtra("LoggedSTDID", inIDNo);
                                                        startActivity(i);
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        //Toast.makeText(MainActivity.this, "Password Found", Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    Toast.makeText(MainActivity.this, "Password not found", Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }else{
                        Toast.makeText(MainActivity.this, "Registration ID not found", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(MainActivity.this, "Incorrect registration ID", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
