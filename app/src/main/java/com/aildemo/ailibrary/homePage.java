package com.aildemo.ailibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class homePage extends AppCompatActivity {

    Button btnTrack, btnBorrow, btnUpdate, btnHistory, btnFeedback, btnLogout;
    DatabaseReference rootRef, childRef;
    TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnTrack = (Button) findViewById(R.id.btnTrack);
        btnBorrow = (Button) findViewById(R.id.btnBorrow);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        btnLogout = (Button) findViewById(R.id.btnLogOut);
        welcomeText = (TextView)findViewById(R.id.tvWelcome);
        rootRef = FirebaseDatabase.getInstance().getReference();
        childRef = rootRef.child("Students");

        final String sessionId = getIntent().getStringExtra("LoggedSTDID");


        childRef.child(sessionId).child("Name").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String dbname = dataSnapshot.getValue(String.class);
                String tmp = dbname.concat("(" + sessionId + ")");
                welcomeText.setText("Welcome : " + tmp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePage.this, trackBook.class);
                i.putExtra("LoggedSTDID", sessionId);
                startActivity(i);
            }
        });

        btnBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                childRef.child(sessionId).child("BookIssued").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final String dbNoIssued = dataSnapshot.getValue(String.class);
                        try{
                            if(dbNoIssued.equals("e")){
                                btnBorrow.setText("Book Limit Exceeded");
                                btnBorrow.setEnabled(false);
                                //btnBorrow.setBackgroundColor(Color.CYAN);
                            }else{

                                String tmp2 = null;
                                if (dbNoIssued.equals("a")){
                                    tmp2 = "b";
                                }
                                else if (dbNoIssued.equals("b")){
                                    tmp2 = "c";
                                }
                                else if (dbNoIssued.equals("c")){
                                    tmp2 = "d";
                                }
                                else if (dbNoIssued.equals("d")){
                                    tmp2 = "e";
                                }
                                else if (dbNoIssued.equals("xx")){
                                    tmp2 = "a";
                                }
                                Intent i = new Intent(homePage.this, borrow.class);
                                i.putExtra("LoggedSTDID", sessionId);
                                i.putExtra("IssuedBookNo", tmp2);
                                startActivity(i);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePage.this, update.class);
                i.putExtra("LoggedSTDID", sessionId);
                startActivity(i);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePage.this, transaction.class);
                i.putExtra("LoggedSTDID", sessionId);
                startActivity(i);
            }
        });

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Under Construction", Toast.LENGTH_LONG).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }
    private void logout(){
        Intent i = new Intent(homePage.this, MainActivity.class);
        i.setFlags(i.FLAG_ACTIVITY_NEW_TASK | i.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }
}

