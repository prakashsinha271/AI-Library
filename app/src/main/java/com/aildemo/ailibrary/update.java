package com.aildemo.ailibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class update extends AppCompatActivity {

    Button btnUpdate, btnBack, btnChange;
    EditText RegID, Name, Parent, ContactNo, RollNo, Department, AdYear ;
    DatabaseReference rootRef, childRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnChange = (Button) findViewById(R.id.btnChange);
        RegID = (EditText) findViewById(R.id.txtRegID);
        Name = (EditText) findViewById(R.id.txtName);
        Parent = (EditText) findViewById(R.id.txtParent);
        ContactNo = (EditText) findViewById(R.id.txtMobile);
        RollNo = (EditText) findViewById(R.id.txtRoll);
        Department = (EditText) findViewById(R.id.txtDept);
        AdYear = (EditText) findViewById(R.id.txtAdYear);
        rootRef = FirebaseDatabase.getInstance().getReference();
        final String sessionId = getIntent().getStringExtra("LoggedSTDID");
        childRef = rootRef.child("Students").child(sessionId);

        childRef.child("StudentID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbID = dataSnapshot.getValue(String.class);
                if(dbID!= null){
                    RegID.setText(dbID);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        childRef.child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbName = dataSnapshot.getValue(String.class);
                if(dbName!= null){
                    Name.setText(dbName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        childRef.child("Parent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbParent = dataSnapshot.getValue(String.class);
                if(dbParent!= null){
                    Parent.setText(dbParent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        childRef.child("ContactNo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbContactNo = dataSnapshot.getValue(String.class);
                if(dbContactNo!= null){
                    ContactNo.setText(dbContactNo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        childRef.child("RollNo").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbRollNo = dataSnapshot.getValue(String.class);
                if(dbRollNo!= null){
                    RollNo.setText(dbRollNo);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        childRef.child("Branch").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbBranch = dataSnapshot.getValue(String.class);
                if(dbBranch!= null){
                    Department.setText(dbBranch);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        childRef.child("AdmissionYear").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbAdYear = dataSnapshot.getValue(String.class);
                if(dbAdYear!= null){
                    AdYear.setText(dbAdYear);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name.setEnabled(true);
            }
        });

        Parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parent.setEnabled(true);
            }
        });

        ContactNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactNo.setEnabled(true);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(update.this, homePage.class);
                i.putExtra("LoggedSTDID", sessionId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(update.this, password.class);
                i.putExtra("LoggedSTDID", sessionId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String upName = null;
                String upParent = null;
                String upMobile = null;
                try {
                    if(Name!=null) {
                        if(Parent!=null) {
                            if(ContactNo!=null) {
                                upName = Name.getText().toString();
                                upParent = Parent.getText().toString();
                                upMobile = ContactNo.getText().toString();

                                childRef.child("Name").setValue(upName);
                                childRef.child("Parent").setValue(upParent);
                                childRef.child("ContactNo").setValue(upMobile);

                                Intent i = new Intent(update.this, homePage.class);
                                i.putExtra("LoggedSTDID", sessionId);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);

                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Contact Number can not be null", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Guardians Name can not be null", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Name can not be null", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
