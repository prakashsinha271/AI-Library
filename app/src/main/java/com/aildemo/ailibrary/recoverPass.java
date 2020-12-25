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

public class recoverPass extends AppCompatActivity {

    Button btnRecover, btnBack;
    EditText txtReg, txtName, txtParent, txtMobile;
    DatabaseReference rootRef, childRef;
    TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);

        btnRecover = (Button) findViewById(R.id.btnRecover);
        btnBack = (Button) findViewById(R.id.btnBack);
        txtReg = (EditText) findViewById(R.id.txtReg);
        txtName = (EditText) findViewById(R.id.txtName);
        txtParent = (EditText) findViewById(R.id.txtParent);
        txtMobile = (EditText) findViewById(R.id.txtContact);
        txtView = (TextView) findViewById(R.id.txtView);
        rootRef = FirebaseDatabase.getInstance().getReference();
        childRef = rootRef.child("Students");
        final int[] tmp = {0};

        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp[0] = 1;
                txtName.setEnabled(false);
                txtParent.setEnabled(false);
                txtMobile.setEnabled(false);
            }
        });

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp[0] = 2;
                txtReg.setEnabled(false);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(recoverPass.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inID = null;
                String inName = null;
                String inParent = null;
                String inMobile = null;
                try{

                    if(tmp[0] == 1){
                        inID = txtReg.getText().toString();
                        inName = null;
                        inParent = null;
                        inMobile = null;
                    }
                    else if(tmp[0] == 2){
                        inName = txtName.getText().toString();
                        inParent = txtParent.getText().toString();
                        inMobile = txtMobile.getText().toString();
                        inID = null;
                    }

                    if(tmp[0] == 1){
                            childRef.child(inID).child("Password").addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final String value = dataSnapshot.getValue(String.class);
                                    if (value != null) {
                                        txtView.setText("Your password is: " + value);
                                        btnRecover.setEnabled(false);
                                    } else {
                                        Toast.makeText(recoverPass.this, "Credential not found, please try again", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                    }else if(tmp[0] == 2){
                        if(inParent!=null){
                            if(inMobile!=null){

                            }else{
                                Toast.makeText(recoverPass.this, "Please provide registered mobile number", Toast.LENGTH_LONG).show();
                            }
                        }else{
                            Toast.makeText(recoverPass.this, "Please provide guardians name", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(recoverPass.this, "Please provide registration ID or personal details", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
