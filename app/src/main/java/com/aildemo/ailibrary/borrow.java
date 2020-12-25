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

public class borrow extends AppCompatActivity {


    Button btnBorrow, btnBack;
    EditText inBookID;
    DatabaseReference rootRef, childRefBook, childRefStd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        btnBorrow = (Button) findViewById(R.id.btnBorrow);
        btnBack = (Button) findViewById(R.id.btnBack);
        rootRef = FirebaseDatabase.getInstance().getReference();
        inBookID = (EditText) findViewById(R.id.inputID);
        childRefBook = rootRef.child("Books");
        childRefStd = rootRef.child("Students");
        final String sessionId = getIntent().getStringExtra("LoggedSTDID");
        final String issuedNo = getIntent().getStringExtra("IssuedBookNo");

        btnBorrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Book is " + issueInt, Toast.LENGTH_LONG).show();
                String inputBook = null;
                try {
                    inputBook = inBookID.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final String finalInputBook = inputBook;
                childRefBook.child(inputBook).child("Status").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String dbStatus = dataSnapshot.getValue(String.class);
                        //final int tmpNo = Integer.parseInt(dbStatus);
                        if(dbStatus.equals("Available")){
                            try{
                                //update book status
                                childRefBook.child(finalInputBook).child("Status").setValue("Issued to " + sessionId.toString());
                                //

                                childRefStd.child(sessionId).child("BookIssued").setValue(issuedNo);

                                rootRef.child("Issued").child(finalInputBook).child("BookID").setValue(finalInputBook.toString());
                                rootRef.child("Issued").child(finalInputBook).child("StudentID").setValue(sessionId.toString());

                                rootRef.child("IssuedTo").child(sessionId).child(finalInputBook).child("BookID").setValue(finalInputBook.toString());

                                Toast.makeText(getApplicationContext(), "Book has been issued", Toast.LENGTH_LONG).show();

                                btnBorrow.setEnabled(false);
                                inBookID.setText("Issued, Press Back Button");
                                inBookID.setEnabled(false);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // Toast.makeText(getApplicationContext(), "Available", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Book is " + dbStatus, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(borrow.this, homePage.class);
                i.putExtra("LoggedSTDID", sessionId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });



    }
}
